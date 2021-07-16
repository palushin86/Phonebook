import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
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
    private PhoneEntity editedRecord = null;
    private String search;

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

    public PhoneEntity getEditedRecord() {
        return editedRecord;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setEditedRecord(PhoneEntity editedRecord) {
        this.editedRecord = editedRecord;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addRecord() {
        PhoneEntity record = new PhoneEntity(
                firstName,
                middleName,
                lastName,
                workPhone,
                mobilePhone,
                homePhone
        );

        System.out.println(record);

        try {
            transaction.begin();
            entityManager.persist(record);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //todo
            try {
                transaction.rollback();
            } catch (SystemException ex) {
                ex.printStackTrace();
            }
        }

        clearInputPanel();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveRecord() {
        PhoneEntity record = entityManager.find(PhoneEntity.class, editedRecord.getId());
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
        } catch (Exception e) {
            e.printStackTrace();
            //todo
            try {
                transaction.rollback();
            } catch (SystemException ex) {
                ex.printStackTrace();
            }
        }

        editedRecord = null;

        clearInputPanel();
    }

    public void editRecord(PhoneEntity rec) {
        this.editedRecord = rec;
        this.firstName = rec.getFirstName();
        this.middleName = rec.getMiddleName();
        this.lastName = rec.getLastName();
        this.workPhone = rec.getWorkPhoneNumber();
        this.mobilePhone = rec.getMobilePhoneNumber();
        this.homePhone = rec.getHomePhoneNumber();
        refreshInputPanel();
    }

    public void cancelEditing() {
        this.editedRecord = null;
        clearInputPanel();
        refreshInputPanel();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteRecord(PhoneEntity rec) {
        try {
            transaction.begin();
            entityManager.createQuery("delete from PhoneEntity p where p.id=:id")
                    .setParameter("id", rec.getId())
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //todo
            try {
                transaction.rollback();
            } catch (SystemException ex) {
                ex.printStackTrace();
            }
        }

        refreshRecordsTable();
    }

    public List getAllRecords() {
        List list;

        if (search == null || search.equals("")) {
            list = entityManager.createQuery("from PhoneEntity").getResultList();
        } else {
            list = entityManager.createQuery("from PhoneEntity where " +
                    "lower(firstName) LIKE lower(:search) or " +
                    "lower(lastName) LIKE lower(:search) or " +
                    "lower(middleName) LIKE lower(:search) or " +
                    "lower(mobilePhoneNumber) LIKE lower(:search) or " +
                    "lower(homePhoneNumber) LIKE lower(:search) or " +
                    "lower(workPhoneNumber) LIKE lower(:search)" +
                    "")
                    .setParameter("search", "%" + search + "%")
                    .getResultList();
        }

        System.out.println(list);

        return list;
    }

    public void clearSearch() {
        this.search = "";
        refreshRecordsTable();
    }

    private void clearInputPanel() {
        this.firstName = "";
        this.middleName = "";
        this.lastName = "";
        this.workPhone = "";
        this.mobilePhone = "";
        this.homePhone = "";
    }

    private void refreshInputPanel() {
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("mainForm:inputPanel");
    }

    private void refreshRecordsTable() {
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("mainForm:recordsTable");
    }

}
