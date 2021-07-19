package ru.palushin86;

import ru.palushin86.entities.ContactDetailsEntity;
import ru.palushin86.repositories.ContactDetailsRepository;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class PhonebookMB {
    @Inject
    ContactDetailsRepository contactDetailsRepository;

    private String firstName;
    private String middleName;
    private String lastName;
    private String homePhone;
    private String mobilePhone;
    private String workPhone;
    private ContactDetailsEntity editedRecord = null;
    private String search;
    private List phones = new ArrayList();

    public void addRecord() throws Exception {
        ContactDetailsEntity record = new ContactDetailsEntity();
        record.setFirstName(firstName);
        record.setMiddleName(middleName);
        record.setLastName(lastName);
        record.setWorkPhoneNumber(workPhone);
        record.setMobilePhoneNumber(mobilePhone);
        record.setHomePhoneNumber(homePhone);
        contactDetailsRepository.add(record);
        cleanInputPanel();
    }

    public void saveRecord() throws Exception {
        ContactDetailsEntity record = contactDetailsRepository.getContactDetailBiId(editedRecord.getId());
        record.setFirstName(firstName);
        record.setLastName(lastName);
        record.setMiddleName(middleName);
        record.setHomePhoneNumber(homePhone);
        record.setMobilePhoneNumber(mobilePhone);
        record.setWorkPhoneNumber(workPhone);
        contactDetailsRepository.save(record);
        cleanEditing();
        cleanInputPanel();
    }

    public void editRecord(ContactDetailsEntity record) {
        this.editedRecord = record;
        this.firstName = record.getFirstName();
        this.middleName = record.getMiddleName();
        this.lastName = record.getLastName();
        this.workPhone = record.getWorkPhoneNumber();
        this.mobilePhone = record.getMobilePhoneNumber();
        this.homePhone = record.getHomePhoneNumber();
    }

    public void deleteRecord(ContactDetailsEntity rec) throws Exception {
        contactDetailsRepository.deleteById(rec.getId());
    }

    public void fetchAllRecords() {
        List<ContactDetailsEntity> records;

        if (search == null || search.equals("")) {
            records = contactDetailsRepository.getContactDetails();
        } else {
            records = contactDetailsRepository.getContactDetails(search);
        }

        phones.clear();
        phones.addAll(records);
    }

    public void cancelEditing() {
        cleanEditing();
        cleanInputPanel();
    }

    public void cleanSearch() {
        this.search = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public ContactDetailsEntity getEditedRecord() {
        return editedRecord;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setEditedRecord(ContactDetailsEntity editedRecord) {
        this.editedRecord = editedRecord;
    }

    public List<ContactDetailsEntity> getPhones() {
        return phones;
    }

    public void setPhones(List<ContactDetailsEntity> phones) {
        this.phones = phones;
    }

    private void cleanEditing() {
        this.editedRecord = null;
    }

    private void cleanInputPanel() {
        this.firstName = "";
        this.middleName = "";
        this.lastName = "";
        this.workPhone = "";
        this.mobilePhone = "";
        this.homePhone = "";
    }

}
