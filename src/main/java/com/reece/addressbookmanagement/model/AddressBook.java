package com.reece.addressbookmanagement.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class AddressBook  {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "addressbook", fetch = FetchType.EAGER)
    private List<Contact> contacts;

    public AddressBook(){}

    public AddressBook(String name){this.name = name;}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Contact> getContacts() {
        if(contacts == null){
            contacts = new ArrayList<>();
        }
        return contacts;
    }

    public Long getId(){return id;}

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }


}
