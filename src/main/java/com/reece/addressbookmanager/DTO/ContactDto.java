package com.reece.addressbookmanager.DTO;
import lombok.Data;


@Data
public class ContactDto {
    private Long id;
    private String given;
    private String surname;
    private String phoneNumber;

    protected ContactDto() { }

    public ContactDto(String given, String surname, String phoneNumber){
        this.given = given;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        return String.format("Contact [id=%s, given=%s, surname=%s, phoneNumber=%s]", id, getGiven(),
                getSurname(), getPhoneNumber());
    }
}
