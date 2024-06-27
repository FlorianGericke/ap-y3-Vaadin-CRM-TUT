package com.example.application.repository;

import com.example.application.data.Company;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
