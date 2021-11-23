package com.reece.addressbookmanager.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AddressBookTest {
    private AddressBook addressBook;

    @Before
    public void setUp() {
        addressBook = new AddressBook("New Address Book");
    }

    @After
    public void tearDown() {
        addressBook = null;
    }

    @Test
    public void setName(){
        String newName = "New Address Name";
        addressBook.setName(newName);
        assertThat(addressBook.getName(), is(newName));
    }

    @Test
    public void getId(){
        assertNull(addressBook.getId());
    }

    @Test
    public void getContacts() {
        assertThat(addressBook.getContacts().size(), is(0));
    }

    @Test
    public void setContacts(){
        Contact newContact1 = new Contact("James", "Dean", "43234235323");
        Contact newContact2 = new Contact("Rob", "Harris", "64552332332");
        Contact newContact3 = new Contact("Christain", "Bale", "43242363234234");
        List<Contact> contactsList = Arrays.asList(newContact1, newContact2, newContact3);
        addressBook.setContacts(contactsList);
        assertThat(addressBook.getContacts(), is(contactsList));
    }
}