package com.reece.addressbookmanagement.DTO;

import com.reece.addressbookmanagement.model.Contact;
import lombok.Data;

import java.util.Collection;

@Data
public class AddressBookDto {
    private Collection<Contact> contacts;

}
