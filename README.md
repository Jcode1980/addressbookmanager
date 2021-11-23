Working with Address Book manager
====================================================
By default, the application's in memory database is set up with 3 address books with id 1 to 3.
(Refer to import.sql) Contacts must be added to those address books else an exception will occur.

prerequisites for running in Intellij
--------------
The following items should be installed in your system:
- Maven 4
- IntelliJ
- Java 11
- Lombok plugin

Running The Address Book Management REST Application
----------------------------------------------------

**Inside Intellij**
- Open project into Intellij

    Runnings Tests
    --------------
    - Right click on Address Book Manager project within Intellij
    Run -> "All Tests"

    Running REST API Server
    -----------------
    - Right click on src/main/java/com/reece/addressbookmanager/Application.java
    - Click on 'Run Application.main()'
    - Go to a web browser and access the rest api via the URL below:
    - http://localhost:8080/swagger-ui.html#/

Docker Image
-------------------
A docker image has also been supplid. Once imported into docker, the following line can be used to run a container:
docker run -p 8080:8080 -t addressmanager:0.0.1-SNAPSHOT
 
jar
--------------------
A jar has also been provided of there is an issue running the docker image. 
to run the jar, use the line. Please note, java most be installed on the machine running the jar:
java -jar addressbookmanager-0.0.1-SNAPSHOT.jar

Swaggerui
----------
Can be found at URL: http://localhost:8080/swagger-ui.html#/

API-Docs
---------
Can be found at URL: http://localhost:8080/v2/api-docs


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
    
    - No extra address books can be added other than the default 3 created on load
    as it was no within the acceptance criteria.
