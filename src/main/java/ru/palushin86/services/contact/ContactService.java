package ru.palushin86.services.contact;

import ru.palushin86.model.ContactEntity;

import java.util.List;

public interface ContactService {
    void addContact(
            String firstName,
            String middleName,
            String lastName,
            String workPhone,
            String mobilePhone,
            String homePhone
    );
    void saveContact(
            Integer id,
            String firstName,
            String middleName,
            String lastName,
            String workPhone,
            String mobilePhone,
            String homePhone
    );
    void deleteById(Integer id);
    ContactEntity getContactDetailBiId(Integer id);
    List<ContactEntity> getContacts();
    List<ContactEntity> getContacts(String search);
}
