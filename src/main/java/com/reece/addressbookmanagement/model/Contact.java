package com.reece.addressbookmanagement.model;

import javax.persistence.*;
import java.util.Objects;

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

    public Contact() {
        super();
    }

    public Contact(String given, String surname, String phoneNumber){
        this.given = given;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AddressBook getAddressbook() {
        return addressbook;
    }

    public void setAddressbook(AddressBook addressbook) {
        this.addressbook = addressbook;
    }

    @Override
    public String toString() {
        return String.format(DISPLAY_FORMAT, id, getGiven(), getSurname(), getPhoneNumber());
    }

    @Override
    public boolean equals(Object o) {
        Boolean returnEqualsValue;

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
