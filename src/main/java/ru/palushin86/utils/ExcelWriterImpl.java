package ru.palushin86.utils;

import org.apache.log4j.Logger;
import ru.palushin86.model.ContactEntity;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@ApplicationScoped
public class ExcelWriterImpl implements ExcelWriter {
    private static String[] columns = {"Фамилия", "Имя", "Отчество", "Мобильный телефон", "Рабочий телефон", "Домашний телефон"};
    private static String filePath = "C:\\Contacts.xlsx";
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public void exportPhonebook(List<ContactEntity> contacts) {
        Workbook workbook = new XSSFWorkbook();
        workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Контакты");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
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

        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            log.error("exporting to excel failed. error message: " + e.getMessage());
        }

        log.info("exporting to excel completed successfully. file created: " + filePath);
    }
}
