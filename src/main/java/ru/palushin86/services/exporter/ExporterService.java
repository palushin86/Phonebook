package ru.palushin86.services.exporter;

import ru.palushin86.model.ContactEntity;

import java.util.List;

public interface ExporterService {
    void exportContactsToXls(List<ContactEntity> contacts);
}
