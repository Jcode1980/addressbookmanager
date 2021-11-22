package com.reece.addressbookmanagement.service;

import com.reece.addressbookmanagement.model.AddressBook;
import com.reece.addressbookmanagement.model.Contact;
import com.reece.addressbookmanagement.repository.AddressBookRepository;
import com.reece.addressbookmanagement.repository.ContactRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressBookServiceTest {
    static private final Long TEST_ADDRESS_BOOK_ID = 1L;
    static private final Long TEST_ADDRESS_BOOK2_ID = 2L;
    static private final Long SAVED_CONTACT_MOCK_ID = 1000L;

    private AddressBookService addressBookService;
    @Mock
    private AddressBookRepository addressBookRepositoryMock;
    @Mock
    private ContactRepository contactRepositoryMock;
    @Mock
    private AddressBook addressBookMock;
    @Mock
    private AddressBook addressBook2Mock;
    @Mock
    private Contact newContactMock;
    @Mock
    private Contact savedContactMock;
    @Mock
    private Contact savedContact2Mock;

    @Before
    public void setUp(){

        when(addressBookRepositoryMock.findById(TEST_ADDRESS_BOOK_ID)).thenReturn(Optional.of(addressBookMock));
        when(addressBookRepositoryMock.findAllById(Arrays.asList(TEST_ADDRESS_BOOK_ID, TEST_ADDRESS_BOOK2_ID))).
                thenReturn(Arrays.asList(addressBookMock, addressBook2Mock));
        when(contactRepositoryMock.save(newContactMock)).thenReturn(savedContactMock);
        when(savedContactMock.getId()).thenReturn(SAVED_CONTACT_MOCK_ID);
        when(addressBookMock.getContacts()).thenReturn(new ArrayList<>(Collections.singletonList(savedContactMock)));
        when(addressBook2Mock.getContacts()).thenReturn(new ArrayList<>(Collections.singletonList(savedContact2Mock)));

        addressBookService = new AddressBookService(addressBookRepositoryMock, contactRepositoryMock);
    }

    @After
    public void tearDown() {
        addressBookService = null;
    }

    @Test
    public void addContactToAddressBook() {
        when(newContactMock.getGiven()).thenReturn("John");
        when(newContactMock.getSurname()).thenReturn("Doe");
        when(newContactMock.getPhoneNumber()).thenReturn("3019831212");
        addressBookService.addContactToAddressBook(TEST_ADDRESS_BOOK_ID, newContactMock);
        verify(contactRepositoryMock, times(1)).save(newContactMock);
    }

    @Test
    public void deleteContactFromAddressBook() {
        addressBookService.deleteContactFromAddressBook(TEST_ADDRESS_BOOK_ID, SAVED_CONTACT_MOCK_ID);
        verify(contactRepositoryMock, times(1)).delete(savedContactMock);
    }

    @Test
    public void retrieveAllContactsFromAddressBook() {
        List<Contact> contactsList = addressBookService.retrieveAllContactsFromAddressBook(TEST_ADDRESS_BOOK_ID);
        assertThat(contactsList.size(), is(1));
        assertThat(contactsList.get(0), is(savedContactMock));
    }

    @Test
    public void retrieveUniqieContactsFromAddressBooks() {
        List<Contact> contactsList = addressBookService.retrieveUniqieContactsFromAddressBooks(
                Arrays.asList(TEST_ADDRESS_BOOK_ID, TEST_ADDRESS_BOOK2_ID));
        assertThat(contactsList.size(), is(2));
    }
}