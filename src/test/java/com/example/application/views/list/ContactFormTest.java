package com.example.application.views.list;

import com.example.application.data.Company;
import com.example.application.data.Contact;
import com.example.application.data.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ContactFormTest {
    private List<Company> companies;
    private List<Status> statuses;
    private Contact marcUsher;
    private Company company1;
    private Company company2;
    private Status status1;
    private Status status2;


    @BeforeEach
    void setUp() {
        companies = new ArrayList<>();
        company1 = new Company();
        company1.setName("Company 1");
        company2 = new Company();
        company2.setName("Company 2");

        companies.add(company1);
        companies.add(company2);

        statuses = new ArrayList<>();
        status1 = new Status();
        status1.setName("Status 1");

        status2 = new Status();
        status2.setName("Status 2");
        statuses.add(status1);
        statuses.add(status2);

        marcUsher = new Contact();
        marcUsher.setFirstName("Marc");
        marcUsher.setLastName("Usher");
        marcUsher.setEmail("MarcUser@example.com");
        marcUsher.setStatus(status1);
        marcUsher.setCompany(company1);
    }

    @Test
    public void fromFieldsPopulated() {
        var form = new ContactForm(companies, statuses);
        form.setContact(marcUsher);

        assertEquals("Marc", form.firstName.getValue());
        assertEquals("Usher", form.lastName.getValue());
        assertEquals("MarcUser@example.com", form.email.getValue());
        assertEquals(status1, form.status.getValue());
        assertEquals(company1, form.company.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        var form = new ContactForm(companies, statuses);
        var contact = new Contact();
        form.setContact(contact);

        form.firstName.setValue("John");
        form.lastName.setValue("Doe");
        form.email.setValue("JohnDoe@example.com");
        form.status.setValue(status2);
        form.company.setValue(company2);

        AtomicReference<Contact> savedContact = new AtomicReference<>(null);
        form.addListener(ContactForm.SaveEvent.class, e -> savedContact.set(e.getContact()));

        form.save.click();

        var saved = savedContact.get();

        assertEquals("John", saved.getFirstName());
        assertEquals("Doe", saved.getLastName());
        assertEquals("JohnDoe@example.com", saved.getEmail());
        assertEquals(status2, saved.getStatus());
        assertEquals(company2, saved.getCompany());
    }
}