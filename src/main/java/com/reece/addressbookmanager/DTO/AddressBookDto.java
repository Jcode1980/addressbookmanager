package com.reece.addressbookmanager.DTO;

import com.reece.addressbookmanager.model.Contact;
import lombok.Data;

import java.util.Collection;

@Data
public class AddressBookDto {
    private Collection<Contact> contacts;

}
