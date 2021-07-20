package ru.palushin86.view;

import ru.palushin86.model.ContactEntity;
import ru.palushin86.services.ContactService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ContactViewBean implements Serializable {
    @Inject
    private ContactService contactService;
    private ContactEntity editedRecord = null;
    private List<ContactEntity> phones = new ArrayList();
    private String firstName;
    private String middleName;
    private String lastName;
    private String homePhone;
    private String mobilePhone;
    private String workPhone;
    private String search;

    @PostConstruct
    public void init() {
        fetchContacts();
    }

    public void addRecord() {
        contactService.addContact(
                firstName,
                middleName,
                lastName,
                workPhone,
                mobilePhone,
                homePhone
        );
        cleanInputPanel();
        fetchContacts();
    }

    public void saveRecord() {
        contactService.saveContact(
                editedRecord.getId(),
                firstName,
                middleName,
                lastName,
                workPhone,
                mobilePhone,
                homePhone
        );
        cleanEditing();
        cleanInputPanel();
        fetchContacts();
    }

    public void editRecord(ContactEntity contact) {
        this.editedRecord = contact;
        this.firstName = contact.getFirstName();
        this.middleName = contact.getMiddleName();
        this.lastName = contact.getLastName();
        this.workPhone = contact.getWorkPhoneNumber();
        this.mobilePhone = contact.getMobilePhoneNumber();
        this.homePhone = contact.getHomePhoneNumber();
    }

    public void deleteRecord(ContactEntity rec) {
        contactService.deleteById(rec.getId());
        fetchContacts();
    }

    public void cancelEditing() {
        cleanEditing();
        cleanInputPanel();
    }

    public void fetchContacts() {
        List<ContactEntity> records;

        if (search == null || search.equals("")) {
            records = contactService.getContacts();
        } else {
            records = contactService.getContacts(search);
        }

        phones.clear();
        phones.addAll(records);
    }

    public void cleanSearch() {
        this.search = "";
        this.fetchContacts();
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

    public ContactEntity getEditedRecord() {
        return editedRecord;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setEditedRecord(ContactEntity editedRecord) {
        this.editedRecord = editedRecord;
    }

    public List<ContactEntity> getPhones() {
        return phones;
    }

    public void setPhones(List<ContactEntity> phones) {
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
