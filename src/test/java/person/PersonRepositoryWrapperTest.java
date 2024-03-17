package person;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryWrapperTest {

    private PersonRepositoryWrapper repositoryWrapper;
    @BeforeEach
    void init() throws JAXBException {
        repositoryWrapper = new PersonRepositoryWrapper(Environment.TEST);
    }

    @Test
    void Create_Valid_External_Person() throws JAXBException {
        Person person = repositoryWrapper.create(Type.EXTERNAL, new Person("Szymon", "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        assertNotNull(person);
    }

    @Test
    void Create_Valid_Internal_Person() throws JAXBException {
        Person person = repositoryWrapper.create(Type.EXTERNAL, new Person("Szymon", "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        assertNotNull(person);
    }

    @Test
    void Create_Invalid_External_Person_Null_FirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            repositoryWrapper.create(Type.EXTERNAL, new Person(null , "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        });
    }

    @Test
    void Create_Invalid_External_Person_Invalid_Mobile() {
        assertThrows(IllegalArgumentException.class, () -> {
            repositoryWrapper.create(Type.EXTERNAL, new Person(null , "Paczuski", "51259636x", "paczuskiszymon16@gmail.com", "11111111111"));
        });
    }


    @Test
    void Create_Invalid_Internal_Person_Pesel_Too_Short() {
        assertThrows(IllegalArgumentException.class, () -> {
            repositoryWrapper.create(Type.EXTERNAL, new Person("Szymon" , "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "111"));
        });
    }

    @Test
    void Create_Invalid_Internal_Person_Invalid_Email_Format() {
        assertThrows(IllegalArgumentException.class, () -> {
            repositoryWrapper.create(Type.EXTERNAL, new Person("Szymon" , "Paczuski", "512596378", "paczuskiszymon16gmail.com", "111"));
        });
    }

    @Test
    void Delete_Valid_External_Person() throws JAXBException {
        Person person = repositoryWrapper.create(Type.EXTERNAL, new Person("Szymon", "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        boolean response = repositoryWrapper.delete(Type.EXTERNAL, person.getId());
        assertTrue(response);
    }

    @Test
    void Delete_Invalid_External_Person() throws JAXBException {
        boolean response = repositoryWrapper.delete(Type.EXTERNAL, 1);
        assertFalse(response);
    }

    @Test
    void Find_Person_By_Valid_Id() throws JAXBException {
        Person p1 = repositoryWrapper.create(Type.EXTERNAL, new Person("Szymon", "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        Person expectedPerson = repositoryWrapper.create(Type.EXTERNAL, new Person("Jan", "Kowalski", "501531649", "kowalski.jan@gmail.com", "11111111111"));
        Person p3 = repositoryWrapper.create(Type.EXTERNAL, new Person("Mateusz", "Nowak", "603993123", "mateusz_nowak5@gmail.com", "11111111111"));
        Person targetPerson = repositoryWrapper.findById(Type.EXTERNAL, 2);
        assertEquals(expectedPerson, targetPerson);
    }

    @Test
    void Find_Person_By_Invalid_Id() throws JAXBException {
        repositoryWrapper.create(Type.EXTERNAL, new Person("Szymon", "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        repositoryWrapper.create(Type.EXTERNAL, new Person("Jan", "Kowalski", "501531649", "kowalski.jan@gmail.com", "11111111111"));
        repositoryWrapper.create(Type.EXTERNAL, new Person("Mateusz", "Nowak", "603993123", "mateusz_nowak5@gmail.com", "11111111111"));
        Person targetPerson = repositoryWrapper.findById(Type.EXTERNAL, 10);
        assertNull(targetPerson);
    }

    @Test
    void Find_List_By_First_Name() throws JAXBException {
        repositoryWrapper.create(Type.EXTERNAL, new Person("Szymon", "Smuda", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        repositoryWrapper.create(Type.EXTERNAL, new Person("Jan", "Kowalski", "501531649", "kowalski.jan@gmail.com", "11111111111"));
        repositoryWrapper.create(Type.EXTERNAL, new Person("Mateusz", "Nowak", "603993123", "mateusz_nowak5@gmail.com", "11111111111"));
        repositoryWrapper.create(Type.INTERNAL, new Person("Szymon", "Paczuski", "512596378", "smudasmuda@gmail.com", "11111111111"));
        List<Person> personList = repositoryWrapper.findByFirstName("Szymon");
        assertEquals(2, personList.size());
    }

    @Test
    void Modify_Person_Valid() throws JAXBException {
        Person person = repositoryWrapper.create(Type.EXTERNAL, new Person("Szymon", "Smuda", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        person.setFirstName("New_First_Name");
        repositoryWrapper.modify(person);
        Person targetPerson = repositoryWrapper.findById(Type.EXTERNAL, 1);
        assertEquals(targetPerson.getFirstName(), "New_First_Name");
    }

    @Test
    void Modify_Person_Invalid() throws JAXBException {
        Person person = repositoryWrapper.create(Type.EXTERNAL, new Person("Szymon", "Smuda", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        assertThrows(IllegalArgumentException.class, () -> {
            person.setMobile("INVALID_MOBILE");
            repositoryWrapper.modify(person);
            Person targetPerson = repositoryWrapper.findById(Type.EXTERNAL, 1);
        });
    }


}