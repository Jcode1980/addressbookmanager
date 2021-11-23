package com.reece.addressbookmanager.controller;

import com.reece.addressbookmanager.DTO.ContactDto;
import com.reece.addressbookmanager.model.Contact;
import com.reece.addressbookmanager.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class )
@WebMvcTest(AddressBookController.class)
public class AddressBookControllerTest {
    static private final String ADDRESS_BOOK_API_URL ="http://localhost:8080/api/addressbook/";
    private List<Contact> address1Contacts;
    private Contact newContact1;
    private Contact newContact2;

    @Autowired
    @SuppressWarnings("unused")
    private MockMvc mvc;

    @MockBean
    @SuppressWarnings("unused")
    private AddressBookService addressBookService;

    @MockBean
    @SuppressWarnings("unused")
    private ModelMapper modelMapper;

    //private AddressBook addressBook;

    @Before
    public void setUp() {
        newContact1 = new Contact("john", "Doe", "123525623");
        newContact2 = new Contact("Joe", "Harris", "675423432");

        //newContact1Dto = new ContactDto("John", "Doe", "123525623");
        //newContact2Dto = new ContactDto("Joe", "Harris", "675423432");
        address1Contacts = Arrays.asList(newContact1, newContact2);

        given(modelMapper.map(newContact1, ContactDto.class)).willReturn(new ContactDto("John", "Doe", "123525623"));
        given(modelMapper.map(newContact2, ContactDto.class)).willReturn(new ContactDto("Joe", "Harris", "675423432"));

    }

    @Test
    public void getContactsFromAddressBook() throws Exception{
        String contactsURL = ADDRESS_BOOK_API_URL+"1/contacts";
        given(addressBookService.retrieveAllContactsFromAddressBook(1L)).willReturn(address1Contacts);

        mvc.perform(get(contactsURL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> log.info("Response: " + result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].given", is("John")));
    }

    @Test
    public void getUniqueContactsFromAddressBooks() throws Exception{
        String uniqueContactsURL = ADDRESS_BOOK_API_URL+"contacts?addressBookIDs=1,2";

        Contact newContact4 = new Contact("William", "Harris", "412345156");

        //ContactDto newContact3Dto = new ContactDto("John", "Doe", "123525623");
        ContactDto newContact4Dto = new ContactDto("William", "Harris", "412345156");
        List<Contact> uniqueAddressContacts = Arrays.asList(newContact1, newContact2, newContact4);

        given(addressBookService.retrieveUniqieContactsFromAddressBooks(Arrays.asList(1L, 2L))).willReturn(uniqueAddressContacts);
        given(modelMapper.map(newContact4, ContactDto.class)).willReturn(newContact4Dto);

        mvc.perform(get(uniqueContactsURL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> log.info("Response: " + result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].given", is("John")))
                .andExpect(jsonPath("$[0].surname", is("Doe")))
                .andExpect(jsonPath("$[1].given", is("Joe")))
                .andExpect(jsonPath("$[1].surname", is("Harris")))
                .andExpect(jsonPath("$[2].given", is("William")))
                .andExpect(jsonPath("$[2].surname", is("Harris")));

    }

    @Test
    public void addContactToAddressBook() throws Exception{
        String addContactToAddressBook = ADDRESS_BOOK_API_URL+"1/addContact";
        Contact newContact = new Contact("Shane", "Heal", "4323523");
        ContactDto newContactDto = new ContactDto("David", "Shepparton", "123453432");
        given(addressBookService.addContactToAddressBook(1L, newContact)).willReturn(newContact);

        //log.info("this is the request body: " + newContactDto);
        given(modelMapper.map(newContact, ContactDto.class)).willReturn(newContactDto);

        mvc.perform(post(addContactToAddressBook)
                .content(contactJsonObject().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(result -> log.info("Response: " + result.getResponse().getContentAsString()));
    }

    @Test
    public void deleteContact() throws Exception {
        String deleteContactFromAddressBookURL = ADDRESS_BOOK_API_URL+"1/removeContact/1";
        mvc.perform(delete(deleteContactFromAddressBookURL).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }


    private JSONObject contactJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("given", "David");
        jsonObject.put("surname", "Shepparton");
        jsonObject.put("phoneNumber", "123453432");

        return jsonObject;
    }

}