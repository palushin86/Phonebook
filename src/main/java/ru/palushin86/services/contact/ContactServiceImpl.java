package ru.palushin86.services.contact;

import ru.palushin86.model.ContactEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ContactServiceImpl implements ContactService {
    @PersistenceContext(unitName = "PersistenceUnit")
    EntityManager entityManager;

    @Transactional
    public void addContact(
            String firstName,
            String middleName,
            String lastName,
            String workPhone,
            String mobilePhone,
            String homePhone
    ) {
        ContactEntity entity = new ContactEntity();
        entity.setFirstName(firstName);
        entity.setMiddleName(middleName);
        entity.setLastName(lastName);
        entity.setWorkPhoneNumber(workPhone);
        entity.setMobilePhoneNumber(mobilePhone);
        entity.setHomePhoneNumber(homePhone);
        entityManager.persist(entity);
    }

    @Transactional
    public void saveContact(
            Integer id,
            String firstName,
            String middleName,
            String lastName,
            String workPhone,
            String mobilePhone,
            String homePhone
    ) {
        ContactEntity entity = this.getContactDetailBiId(id);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setMiddleName(middleName);
        entity.setHomePhoneNumber(homePhone);
        entity.setMobilePhoneNumber(mobilePhone);
        entity.setWorkPhoneNumber(workPhone);
        entityManager.merge(entity);
    }

    @Transactional
    public void deleteById(Integer id) {
        ContactEntity contactEntity = entityManager.find(ContactEntity.class, id);
        entityManager.remove(contactEntity);
    }

    public ContactEntity getContactDetailBiId(Integer id) {
        return entityManager.find(ContactEntity.class, id);
    }

    public List<ContactEntity> getContacts() {
        String query = "from ContactEntity";
        return entityManager.createQuery(query, ContactEntity.class).getResultList();
    }

    public List<ContactEntity> getContacts(String search) {
        String query = "from ContactEntity where " +
                "lower(firstName) LIKE lower('%" + search + "%') or " +
                "lower(lastName) LIKE lower('%" + search + "%') or " +
                "lower(middleName) LIKE lower('%" + search + "%') or " +
                "lower(mobilePhoneNumber) LIKE lower('%" + search + "%') or " +
                "lower(homePhoneNumber) LIKE lower('%" + search + "%') or " +
                "lower(workPhoneNumber) LIKE lower('%" + search + "%')";
        return entityManager.createQuery(query, ContactEntity.class).getResultList();
    }
}
