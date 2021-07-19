package ru.palushin86;

import ru.palushin86.entities.ContactDetailsEntity;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class PhonebookBean {
    @PersistenceContext(unitName = "PersistenceUnit")
    EntityManager entityManager;
    @Resource
    UserTransaction transaction;

    private String firstName;
    private String middleName;
    private String lastName;
    private String homePhone;
    private String mobilePhone;
    private String workPhone;
    private ContactDetailsEntity editedRecord = null;
    private String search;
    private List<ContactDetailsEntity> phones = new ArrayList<ContactDetailsEntity>();

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

    public void addRecord() throws Exception {
        ContactDetailsEntity record = new ContactDetailsEntity();
        record.setFirstName(firstName);
        record.setMiddleName(middleName);
        record.setLastName(lastName);
        record.setWorkPhoneNumber(workPhone);
        record.setMobilePhoneNumber(mobilePhone);
        record.setHomePhoneNumber(homePhone);

        try {
            transaction.begin();
            entityManager.persist(record);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

        cleanInputPanel();
    }

    public void saveRecord() throws Exception {
        ContactDetailsEntity record = entityManager.find(ContactDetailsEntity.class, editedRecord.getId());
        record.setFirstName(firstName);
        record.setLastName(lastName);
        record.setMiddleName(middleName);
        record.setHomePhoneNumber(homePhone);
        record.setMobilePhoneNumber(mobilePhone);
        record.setWorkPhoneNumber(workPhone);

        try {
            transaction.begin();
            entityManager.merge(record);
            transaction.commit();
            cleanEditing();
            cleanInputPanel();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

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

    public void cancelEditing() {
        cleanEditing();
        cleanInputPanel();
    }

    public void deleteRecord(ContactDetailsEntity rec) throws Exception {

        try {
            transaction.begin();
            ContactDetailsEntity contactDetailsEntity = entityManager.find(ContactDetailsEntity.class, rec.getId());
            entityManager.remove(contactDetailsEntity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }

    public void fetchAllRecords() {
        String query;

        if (search == null || search.equals("")) {
            query = "from ContactDetailsEntity";
        } else {
            query = "from ContactDetailsEntity where " +
                    "lower(firstName) LIKE lower('%" + search + "%') or " +
                    "lower(lastName) LIKE lower('%" + search + "%') or " +
                    "lower(middleName) LIKE lower('%" + search + "%') or " +
                    "lower(mobilePhoneNumber) LIKE lower('%" + search + "%') or " +
                    "lower(homePhoneNumber) LIKE lower('%" + search + "%') or " +
                    "lower(workPhoneNumber) LIKE lower('%" + search + "%')";
        }

        List<ContactDetailsEntity> records = entityManager.createQuery(query, ContactDetailsEntity.class).getResultList();
        phones.clear();
        phones.addAll(records);
    }

    public void cleanSearch() {
        this.search = "";
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
