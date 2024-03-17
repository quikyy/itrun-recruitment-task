# ItCountsServer
An application supports the management of the Person entity in a database stored in XML files.
In accordance with the content of the task, the application was divided into two subdirectories storing data about internal and external employees.

## Technologies Used
- Java 17
- Gradle

## Dependencies
- JAXB
- Jakarta XML Binding
- JUnit 5
- S4LJ Logger

## Features
- Load database from .XML file if present
- Create new .XML file if not present
- Create new Person
- Validate Person
- Delete Person
- Modify Person
- Find Persons by First Name and Type (External or Internal)
- Find Persons by First Name without Type
- Find Persons by Last Name and Type (External or Internal)
- Find Persons by Last Name without Type
- Find Person by Mobile
- Find Person by Type, First Name, Last Name and Mobile
- Two modes of enviroment (PROD, TEST) - created in order for TESTS to be performed on a non-existent 'production' database

