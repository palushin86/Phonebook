package ru.palushin86.services.exporter;

import org.apache.log4j.Logger;
import ru.palushin86.model.ContactEntity;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.List;

@ApplicationScoped
public class ExporterServiceImpl implements ExporterService {
    private static String[] xlsPhonebookColumns = {"Фамилия", "Имя", "Отчество", "Мобильный телефон", "Рабочий телефон", "Домашний телефон"};
    private static String xlsPhonebookFilePath = "Contacts.xlsx";
    private static String xlsPhonebookSheetName = "Контакты";

    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public void exportContactsToXls(List<ContactEntity> contacts) {
        log.info("phonebook to xls exporting start");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + xlsPhonebookFilePath);
        Workbook workbook = createWorkBook(contacts);

        try {
            workbook.write(externalContext.getResponseOutputStream());
            workbook.close();
        } catch (final Exception e) {
            log.error("phonebook to xls exporting error. error message: " + e.getMessage());
        }

        facesContext.responseComplete();
    }

    private Workbook createWorkBook(List<ContactEntity> contacts) {
        log.info("phonebook to xls exporting. creating workbook");
        Workbook workbook = new XSSFWorkbook();
        workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet(xlsPhonebookSheetName);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for(int i = 0; i < xlsPhonebookColumns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(xlsPhonebookColumns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for(ContactEntity contact: contacts) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(contact.getFirstName());
            row.createCell(1).setCellValue(contact.getLastName());
            row.createCell(2).setCellValue(contact.getMiddleName());
            row.createCell(3).setCellValue(contact.getMobilePhoneNumber());
            row.createCell(4).setCellValue(contact.getWorkPhoneNumber());
            row.createCell(5).setCellValue(contact.getHomePhoneNumber());
        }

        for(int i = 0; i < xlsPhonebookColumns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }
}
