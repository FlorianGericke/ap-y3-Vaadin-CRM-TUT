package com.example.application.generator;


import com.example.application.data.Company;
import com.example.application.data.Contact;
import com.example.application.data.Status;
import com.example.application.repository.CompanyRepository;
import com.example.application.repository.ContactRepository;
import com.example.application.repository.StatusRepository;
import com.github.javafaker.Faker;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Component
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(CompanyRepository companyRepository, ContactRepository contactRepository,
                                      StatusRepository statusRepository) {
        return args -> {
            var logger = LoggerFactory.getLogger(getClass());
            if (contactRepository.count() != 0L) {
                logger.info("Using Existing Database");
                return;
            }
            Faker faker = new Faker();


            int seed = 123;

            ArrayList<Company> companies = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                Company company = new Company();
                faker.random();
                company.setName(faker.company().name());
                companies.add(company);
            }
            companyRepository.saveAll(companies);

            List<Status> statuses = statusRepository.saveAll(Stream.of("Important Lead",
                    "Contacted", "Not Contacted", "Lost").map(Status::new).toList());

            logger.info("Generated 50 Contact entities...");

            ArrayList<Contact> contacts = new ArrayList<>();

            Random r = new Random(seed);
            for (int i = 0; i < 100; i++) {
                Contact contact = new Contact();
                contact.setFirstName(faker.name().firstName());
                contact.setLastName(faker.name().lastName());
                contact.setEmail(faker.internet().emailAddress());
                contact.setCompany(companies.get(r.nextInt(companies.size())));
                contact.setStatus(statuses.get(r.nextInt(statuses.size())));
                contacts.add(contact);
            }

            contactRepository.saveAll(contacts);
            logger.info("Generated demo data");
        };
    }

    ;
}