package com.reece.addressbookmanager.controller;

import com.reece.addressbookmanager.DTO.ContactDto;
import com.reece.addressbookmanager.model.Contact;
import com.reece.addressbookmanager.service.IAddressBookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/addressbook/")
public class AddressBookController {
    private final IAddressBookService addressBookService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AddressBookController(IAddressBookService addressBookService){
        this.addressBookService = addressBookService;
    }

    /**
     * Returns all the contacts from the address book specified.
     *
     * @param addressBookID - the contact id to be looked up in the address book.
     * @return the contact that matches request's id, if such contact exists.
     * @throws ResponseStatusException Bad request status if no address is found with this id.
     */
    @ApiOperation("Get a contacts for address book.")
    @GetMapping(value = "{addressBookID}/contacts", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    @SuppressWarnings("unused")
    public Collection<ContactDto> getContactsFromAddressBook(
            @ApiParam(value="The id of the address book which holds all the contacts")
            @PathVariable("addressBookID")String addressBookID) throws IllegalArgumentException  {
        Collection<Contact> contactsFound;
        try{
            contactsFound = addressBookService.retrieveAllContactsFromAddressBook(Long.valueOf(addressBookID));
        }catch (IllegalArgumentException e) {
            log.error("Error when retrieving address book ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return contactsFound.stream().map(contact-> modelMapper.
                map(contact, ContactDto.class)).collect(Collectors.toList());
    }

    /**
     * Searches for all uniques contacts specified in the query. Query string
     * should contain all the ids of address books coma separated e.g. (1, 2)
     *
     * @param addressBookIDsString - String of address book ids (comma separated)
     * @return Unique list of contacts from the address books specified in the parameter passed in
     */
    @ApiOperation("Get all unique contacts from a list of address books.")
    @GetMapping(value = "contacts", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    @SuppressWarnings("unused")
    public Collection<ContactDto> getUniqueContactsFromAddressBooks(
            @ApiParam(value="A coma separated String which contains address book IDs. e.g '1,2'")
            @RequestParam("addressBookIDs") String addressBookIDsString) {

        String[] addressBookStrings = addressBookIDsString.split(" *, *");
        Collection<Long> addressBookIDs = Arrays.stream(addressBookStrings).map(Long::valueOf).collect(Collectors.toList());

        Collection<ContactDto> contactsFound = addressBookService.retrieveUniqieContactsFromAddressBooks(addressBookIDs).stream().
                map(contact-> modelMapper.map(contact, ContactDto.class)).collect(Collectors.toList());

        log.info("found contacts : {}", contactsFound);

        return contactsFound;
    }

    /**
     * Adds the contact into the address book.
     *
     * @param contactDto - the contact to be added in the address book.
     * @return a <code>Contact</code>, that has been added to the address book
     * @throws  ResponseStatusException Bad request status if no address is found with the specified ID
     * or contact does not have given, surname or phone number.
     *
     */
    @ApiOperation("Add a contact to an Address book.")
    @PostMapping(value = "{addressBookID}/addContact", consumes="application/json", produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @SuppressWarnings("unused")
    public ContactDto addContactToAddressBook (
            @PathVariable("addressBookID")String addressBookID,
            @ApiParam(value="The contact to be added in the address book. Id field Ignored by server") @RequestBody ContactDto contactDto)
            throws IllegalArgumentException{
        log.debug("this is the request body: " + contactDto);
        Contact contact =  modelMapper.map(contactDto, Contact.class);

        Contact savedContact;
        try{
            savedContact = addressBookService.addContactToAddressBook(Long.valueOf(addressBookID), contact);
        }catch (IllegalArgumentException | NullPointerException e){
            log.error("Error when adding contact to address book ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return modelMapper.map(savedContact, ContactDto.class);
    }

    /**
     * Deletes the contact
     *
     * @param addressBookID - the id of the address book where the contact to be deleted resides.
     * @param contactId - the id of the contact to be deleted.
     * @throws ResponseStatusException Bad request status if no address is found with the specified addressBookID
     * @throws ResponseStatusException Bad request  status if no contact is found with the specified contactID
     */
    @ApiOperation("Delete a contact from an address book.")
    @DeleteMapping(value="{addressBookID}/removeContact/{contactId}")
    @ResponseStatus(HttpStatus.OK)
    @SuppressWarnings("unused")
    public void deleteContact(@ApiParam(value="The id of the address book") @PathVariable String addressBookID,
                              @ApiParam(value="The id of the contact to be deleted") @PathVariable String contactId)
                                {
        try{
            addressBookService.deleteContactFromAddressBook(Long.valueOf(addressBookID), Long.valueOf(contactId));
        }catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }




}
