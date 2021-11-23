package com.reece.addressbookmanager.service;

import com.reece.addressbookmanager.model.AddressBook;
import com.reece.addressbookmanager.model.Contact;
import com.reece.addressbookmanager.repository.AddressBookRepository;
import com.reece.addressbookmanager.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AddressBookService implements IAddressBookService {
    private final AddressBookRepository addressBookRepository;
    private final ContactRepository contactRepository;

    @Autowired
    @SuppressWarnings("WeakerAccess")
    public AddressBookService(AddressBookRepository addressBookRepository, ContactRepository contactRepository){
        this.addressBookRepository = addressBookRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact addContactToAddressBook(Long addressBookID, Contact contact) {
        if(contact.getGiven()== null){throw new NullPointerException("Given must not be null");}
        if(contact.getSurname()== null){throw new NullPointerException("Surname must not be null");}
        if(contact.getPhoneNumber()== null){throw new NullPointerException("Phone Number must not be null");}

        AddressBook addressBook = addressBookRepository.findById(addressBookID).orElseThrow(()->new IllegalArgumentException("id not found"));
        contact.setAddressbook(addressBook);
        log.info("trying to save contact: " + contact);
        return contactRepository.save(contact);
    }

    @Override
    public void deleteContactFromAddressBook(Long addressBookID, Long contactID) {
        AddressBook addressBook = addressBookRepository.findById(addressBookID).
                orElseThrow(()->new IllegalArgumentException("Address with id " +addressBookID+" not found."));

        Contact foundContact = addressBook.getContacts().stream().filter(contact -> contact.getId()
                .equals(contactID)).findFirst().orElseThrow(() ->
                new IllegalArgumentException("Contact with id " +contactID+" not found in address book."));

        contactRepository.delete(foundContact);
    }

    @Override
    public List<Contact> retrieveAllContactsFromAddressBook(Long addressBookID){
        log.info("got to retrieveAllContactsFromAddressBook ");
        AddressBook addressBook = addressBookRepository.findById(addressBookID).
                orElseThrow(()->new IllegalArgumentException("Address with id " +addressBookID+" not found."));
        return addressBook.getContacts();
    }

    @Override
    public List<Contact> retrieveUniqieContactsFromAddressBooks(Collection<Long> addressBookIDs) {
        log.info("got to retrieveUniqieContactsFromAddressBooks ");
        List<AddressBook> addressBooks = addressBookRepository.findAllById(addressBookIDs);

        return allUniqueContactsForAddressBooks(addressBooks);
    }


    private List<Contact> allUniqueContactsForAddressBooks(List<AddressBook> addressBooks){
        HashSet<Contact> uniqueContacts = new HashSet<>();
        addressBooks.forEach(addressBook -> uniqueContacts.addAll(addressBook.getContacts()));
        return uniqueContacts.stream().sorted(Comparator.comparing(Contact::getId)).collect(Collectors.toList());
    }
}
