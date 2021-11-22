package com.reece.addressbookmanagement.service;

import com.reece.addressbookmanagement.model.Contact;

import java.util.Collection;
import java.util.List;

public interface IAddressBookService {

    /**
     * Adds the contact into the address book.
     *
     * @param addressBookID - the addressbook ID (Long) for the address book
     * @param contact - the <code>Contact</code> to be added in the address book
     * @return a <code>Contact</code>, that has been added to the address book
     * @throws IllegalArgumentException if no address book is found using the addressBookID
     */
    Contact addContactToAddressBook(Long addressBookID, Contact contact);

    /**
     * Deletes the contact
     *
     * @param addressBookID - the id of the address book where the contact to be deleted resides.
     * @param contactID - the id of the contact to be deleted.
     * @throws IllegalArgumentException if no address is found with the specified addressBookID
     * @throws IllegalArgumentException if no contact is found with the specified contactID
     */
    void deleteContactFromAddressBook(Long addressBookID, Long contactID);


    /**
     * Returns all the contacts from the address book specified.
     *
     * @param addressBookID - the contact id to be looked up in the phone book.
     * @return a list of contacts that matches request's id, if such contact exists.
     * @throws IllegalArgumentException if no address is found with this id.
     */
    List<Contact> retrieveAllContactsFromAddressBook(Long addressBookID);


    /**
     * Searches for all uniques contacts specified in the query. Query string
     * should contain all the ids of address books coma seperated e.g. (1, 2)
     *
     * @param addressBookIDs - Collection of address book id's (Long)
     * @return Unique list of contacts from the address books specified in the parameter passed in
     */
    Collection<Contact> retrieveUniqieContactsFromAddressBooks(Collection<Long> addressBookIDs);
}
