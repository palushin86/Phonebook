package ru.palushin86.repositories;

import ru.palushin86.entities.ContactDetailsEntity;

import java.util.List;

public interface ContactDetailsRepository {
    void add(ContactDetailsEntity contactDetailsEntity) throws Exception;
    void save(ContactDetailsEntity contactDetailsEntity) throws Exception;
    void deleteById(Integer id) throws Exception;
    ContactDetailsEntity getContactDetailBiId(Integer id);
    List<ContactDetailsEntity> getContactDetails();
    List<ContactDetailsEntity> getContactDetails(String search);
}
