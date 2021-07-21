package ru.palushin86.utils;

import ru.palushin86.model.ContactEntity;

import java.util.List;

public interface ExcelWriter {
    void exportPhonebook(List<ContactEntity> entities);
}
