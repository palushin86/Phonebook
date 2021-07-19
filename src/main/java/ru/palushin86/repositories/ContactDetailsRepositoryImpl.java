package ru.palushin86.repositories;

import ru.palushin86.entities.ContactDetailsEntity;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;

@ManagedBean
public class ContactDetailsRepositoryImpl implements ContactDetailsRepository {
    @PersistenceContext(unitName = "PersistenceUnit")
    EntityManager entityManager;
    @Resource
    UserTransaction transaction;

    public void add(ContactDetailsEntity contactDetailsEntity) throws Exception {
        try {
            transaction.begin();
            entityManager.persist(contactDetailsEntity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public void save(ContactDetailsEntity contactDetailsEntity) throws Exception {
        try {
            transaction.begin();
            entityManager.merge(contactDetailsEntity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public void deleteById(Integer id) throws Exception {
        try {
            transaction.begin();
            ContactDetailsEntity contactDetailsEntity = entityManager.find(ContactDetailsEntity.class, id);
            entityManager.remove(contactDetailsEntity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public ContactDetailsEntity getContactDetailBiId(Integer id) {
        return entityManager.find(ContactDetailsEntity.class, id);
    }

    public List<ContactDetailsEntity> getContactDetails() {
        String query = "from ContactDetailsEntity";
        return entityManager.createQuery(query, ContactDetailsEntity.class).getResultList();
    }

    public List<ContactDetailsEntity> getContactDetails(String search) {
        String query = "from ContactDetailsEntity where " +
                "lower(firstName) LIKE lower('%" + search + "%') or " +
                "lower(lastName) LIKE lower('%" + search + "%') or " +
                "lower(middleName) LIKE lower('%" + search + "%') or " +
                "lower(mobilePhoneNumber) LIKE lower('%" + search + "%') or " +
                "lower(homePhoneNumber) LIKE lower('%" + search + "%') or " +
                "lower(workPhoneNumber) LIKE lower('%" + search + "%')";
        return entityManager.createQuery(query, ContactDetailsEntity.class).getResultList();
    }
}
