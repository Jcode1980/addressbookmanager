package com.reece.addressbookmanager.repository;

import com.reece.addressbookmanager.model.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
}
