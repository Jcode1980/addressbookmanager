package com.reece.addressbookmanager.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name="contact")
public class Contact {
    @Id
    @GeneratedValue
    private Long id;
    private String given;
    private String surname;
    private String phoneNumber;

    public static final String DISPLAY_FORMAT = "Contact [id=%s, name=%s, surname=%s, phoneNumber=%s]";

    @ManyToOne
    @JoinColumn(name="address_bookID")
    private AddressBook addressbook;

    public Contact() { }

    public Contact(String given, String surname, String phoneNumber){
        this.given = given;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format(DISPLAY_FORMAT, id, getGiven(), getSurname(), getPhoneNumber());
    }

    @Override
    public boolean equals(Object o) {
        boolean returnEqualsValue;

        if (o == this) {
            returnEqualsValue = true;
        }
        else if (!(o instanceof Contact)) {
            returnEqualsValue = false;
        }
        else{
            Contact contact = (Contact)o;
            returnEqualsValue = (given.equals(contact.getGiven()) && surname.equals(contact.getSurname()) &&
                phoneNumber.equals(contact.getPhoneNumber())) ;
        }

        return returnEqualsValue;
    }

    @Override
    public int hashCode(){
        return Objects.hash(getGiven(), getSurname(), getPhoneNumber());
    }

}
