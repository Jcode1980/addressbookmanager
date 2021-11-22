package com.reece.addressbookmanagement.repository;

import com.reece.addressbookmanagement.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
