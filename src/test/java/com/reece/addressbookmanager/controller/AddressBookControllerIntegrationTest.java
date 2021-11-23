package com.reece.addressbookmanager.controller;

import com.reece.addressbookmanager.Application;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class AddressBookControllerIntegrationTest {
    static private final String ADDRESS_BOOK_API_URL ="http://localhost:8080/api/addressbook/";
    static private final String CONTACTS_URL = ADDRESS_BOOK_API_URL + "1/contacts";

    @Autowired
    private MockMvc mvc;

    @Test
    public void getContactsFromAddressBook_shouldReturnCorrectContacts() throws Exception {

        mvc.perform(get(CONTACTS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> log.info("getContactsFromAddressBook Response: " + result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].given", is("Kevin")))
                .andExpect(jsonPath("$[0].surname", is("Durant")))
                .andExpect(jsonPath("$[0].phoneNumber", is("0141532123")))
                .andExpect(jsonPath("$[1].given", is("Steve")))
                .andExpect(jsonPath("$[1].surname", is("Nash")))
                .andExpect(jsonPath("$[1].phoneNumber", is("11234426553")))
                .andExpect(jsonPath("$[2].given", is("Lebron")))
                .andExpect(jsonPath("$[2].surname", is("James")))
                .andExpect(jsonPath("$[2].phoneNumber", is("42343255234")));
    }

    @Test
    public void getContactsFromAddressBook_shouldThrowExceptionWhenAddressNotFound() throws Exception {
        String badContactsURL = ADDRESS_BOOK_API_URL + "123123123123/contacts";

        mvc.perform(get(badContactsURL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void getUniqueContactsFromAddressBooks_shouldOnlyReturnUniqueContacts() throws Exception{
        String uniqueContactsURL = ADDRESS_BOOK_API_URL+"contacts?addressBookIDs=1,2";

        mvc.perform(get(uniqueContactsURL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> log.info("getUniqueContactsFromAddressBooks Response: " + result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].given", is("Kevin")))
                .andExpect(jsonPath("$[0].surname", is("Durant")))
                .andExpect(jsonPath("$[0].phoneNumber", is("0141532123")))
                .andExpect(jsonPath("$[1].given", is("Steve")))
                .andExpect(jsonPath("$[1].surname", is("Nash")))
                .andExpect(jsonPath("$[1].phoneNumber", is("11234426553")))
                .andExpect(jsonPath("$[2].given", is("Lebron")))
                .andExpect(jsonPath("$[2].surname", is("James")))
                .andExpect(jsonPath("$[2].phoneNumber", is("42343255234")))
                .andExpect(jsonPath("$[3].given", is("Naruto")))
                .andExpect(jsonPath("$[3].surname", is("Ozimaki")))
                .andExpect(jsonPath("$[3].phoneNumber", is("0141542101")))
                .andExpect(jsonPath("$[4].given", is("Sasake")))
                .andExpect(jsonPath("$[4].surname", is("Uchiha")))
                .andExpect(jsonPath("$[4].phoneNumber", is("0141542101")));


    }

    @Test
    public void addContactToAddressBook_shouldAddToCorrectAddressBook() throws Exception{
        String addContactToAddressBook = ADDRESS_BOOK_API_URL+"1/addContact";

        mvc.perform(post(addContactToAddressBook)
                .content(contactJsonObject("Paul", "Pierce", "123453432").toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(result -> log.info("Response: " + result.getResponse().getContentAsString()));

        mvc.perform(get(CONTACTS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> log.info("Response: " + result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].given", is("Paul")))
                .andExpect(jsonPath("$[0].surname", is("Pierce")))
                .andExpect(jsonPath("$[0].phoneNumber", is("123453432")))
                .andExpect(jsonPath("$[1].given", is("Kevin")))
                .andExpect(jsonPath("$[1].surname", is("Durant")))
                .andExpect(jsonPath("$[1].phoneNumber", is("0141532123")))
                .andExpect(jsonPath("$[2].given", is("Steve")))
                .andExpect(jsonPath("$[2].surname", is("Nash")))
                .andExpect(jsonPath("$[2].phoneNumber", is("11234426553")))
                .andExpect(jsonPath("$[3].given", is("Lebron")))
                .andExpect(jsonPath("$[3].surname", is("James")))
                .andExpect(jsonPath("$[3].phoneNumber", is("42343255234")));
    }

    @Test
    public void addContactToAddressBook_shouldThrowExceptionWhenAddressBookIsNotFound() throws Exception {
        String addContactToAddressBook = ADDRESS_BOOK_API_URL + "10/addContact";

        mvc.perform(post(addContactToAddressBook)
                .content(contactJsonObject("Ben", "Simmons", "0531123421").toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void addContactToAddressBook_shouldThrowExceptionWhenNoGiveNameInContact() throws Exception {
        String addContactToAddressBook = ADDRESS_BOOK_API_URL + "1/addContact";

        mvc.perform(post(addContactToAddressBook)
                .content(contactJsonObject(null, "Simmons", "0531123421").toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addContactToAddressBook_shouldThrowExceptionWhenNoSurnameInContact() throws Exception {
        String addContactToAddressBook = ADDRESS_BOOK_API_URL + "1/addContact";

        mvc.perform(post(addContactToAddressBook)
                .content(contactJsonObject("Ben", null, "0531123421").toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addContactToAddressBook_shouldThrowExceptionWhenNoPhoneNumberInContact() throws Exception {
        String addContactToAddressBook = ADDRESS_BOOK_API_URL + "1/addContact";

        mvc.perform(post(addContactToAddressBook)
                .content(contactJsonObject("Ben", "Simmons", null).toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    public void deleteContact() throws Exception {
        String deleteContactFromAddressBookURL = ADDRESS_BOOK_API_URL+"1/removeContact/100001";

        mvc.perform(delete(deleteContactFromAddressBookURL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get(CONTACTS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> log.info("Response: " + result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].given", is("Steve")))
                .andExpect(jsonPath("$[0].surname", is("Nash")))
                .andExpect(jsonPath("$[0].phoneNumber", is("11234426553")))
                .andExpect(jsonPath("$[1].given", is("Lebron")))
                .andExpect(jsonPath("$[1].surname", is("James")))
                .andExpect(jsonPath("$[1].phoneNumber", is("42343255234")));
    }

    @Test
    public void deleteContact_shouldThrowExceptionWhenContactNotFound() throws Exception {
        String deleteContactFromAddressBookURL = ADDRESS_BOOK_API_URL+"1/removeContact/1001000";

        mvc.perform(delete(deleteContactFromAddressBookURL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void deleteContact_shouldThrowExceptionWhenAddressNotFound() throws Exception {
        String deleteContactFromAddressBookURL = ADDRESS_BOOK_API_URL+"3/removeContact/100001";

        mvc.perform(delete(deleteContactFromAddressBookURL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    private JSONObject contactJsonObject(String given, String surname, String phone) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("given", given);
        jsonObject.put("phoneNumber", phone);
        jsonObject.put("surname", surname);

        return jsonObject;
    }


}


