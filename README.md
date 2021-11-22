
Working with Address Book manager in Intellij 2020.2
====================================================

prerequisites
--------------
The following items should be installed in your system:
- Maven 4
- IntelliJ
- Java 11

Running The Address Book Management REST Application
----------------------------------------------------

**Inside Intellij**
- Open project into Intellij

    Runnings Tests
    --------------
    - Right click on Address Book Management project within Intellij
    Run -> "All Tests"

    Running REST API Server
    -----------------
    - Right click on src/main/java/com/reece/addressbookmanagement/Application.java
    - Click on 'Run Application.main()'
    - Go to a web browser and access the rest api via the URL below:
    - http://localhost:8080/swagger-ui.html#/


Assumptions
===========
    - Uniqueness is based on a contacts given, surname and phone number.
    e.g. Two contacts will be deemed the equal if the 3 mentioned fields
    are the same.

    - I assumed that identical contact entry can be added to the address book.
    I was unsure if this was a correct assumption or not, but i just went with
    how Contacts app on mac handled identical entries and it seems like they
    allow them too.

    - Since it was not mentioned in the acceptance criteria, login security
    is not needed to access the REST endpoints.

    - Contact can only be related to one Address book and not by a two
    many relationship. If a contact is needed in two address books ,
    the contact just be copied across address books.
