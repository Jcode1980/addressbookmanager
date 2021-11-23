package com.reece.addressbookmanager.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class AddressBook  {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "addressbook", fetch = FetchType.EAGER)
    private List<Contact> contacts;

    public AddressBook(){}

    public AddressBook(String name){this.name = name;}

    public List<Contact> getContacts() {
        if(contacts == null){
            contacts = new ArrayList<>();
        }
        return contacts;
    }

}
