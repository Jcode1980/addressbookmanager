package com.reece.addressbookmanagement.repository;

import com.reece.addressbookmanagement.model.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {

}
