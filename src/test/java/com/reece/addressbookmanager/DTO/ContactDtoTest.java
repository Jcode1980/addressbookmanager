package com.reece.addressbookmanager.DTO;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDtoTest {
    static private final String GIVEN = "Micheal";
    static private final String SURNAME = "Jordan";
    static private final String PHONE_NUMBER = "322464423431";
    static private final Long ID = 1L;

    @Test
    public void getGiven() {
        ContactDto contactDto = new ContactDto();
        contactDto.setGiven(GIVEN);
        assertThat(contactDto.getGiven(), is(GIVEN));
    }

    @Test
    public void getSurname() {
        ContactDto contactDto = new ContactDto();
        contactDto.setSurname(SURNAME);
        assertThat(contactDto.getSurname(), is(SURNAME));
    }

    @Test
    public void getPhoneNumber() {
        ContactDto contactDto = new ContactDto();
        contactDto.setPhoneNumber(PHONE_NUMBER);
        assertThat(contactDto.getPhoneNumber(), is(PHONE_NUMBER));
    }


    @Test
    public void getId() {
        ContactDto contactDto = new ContactDto();
        contactDto.setId(ID);
        assertThat(contactDto.getId(), is(ID));
    }



}