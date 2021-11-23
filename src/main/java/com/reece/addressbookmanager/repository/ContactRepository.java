package com.reece.addressbookmanager.repository;

import com.reece.addressbookmanager.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
