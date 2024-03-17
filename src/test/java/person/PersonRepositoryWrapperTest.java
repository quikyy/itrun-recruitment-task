package person;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryWrapperTest {

    private PersonRepositoryWrapper personRepository;
    @BeforeEach
    void init() throws JAXBException {
        personRepository = new PersonRepositoryWrapper(Environment.TEST);
    }

    @Test
    void Create_Valid_External_Person() throws JAXBException {
        Person person = personRepository.create(Type.EXTERNAL, new Person("Szymon", "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        assertNotNull(person);
    }

    @Test
    void Create_Valid_Internal_Person() throws JAXBException {
        Person person = personRepository.create(Type.EXTERNAL, new Person("Szymon", "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        assertNotNull(person);
    }

    @Test
    void Create_Invalid_External_Person_Null_FirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            personRepository.create(Type.EXTERNAL, new Person(null , "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        });
    }

    @Test
    void Create_Invalid_External_Person_Invalid_Mobile() {
        assertThrows(IllegalArgumentException.class, () -> {
            personRepository.create(Type.EXTERNAL, new Person(null , "Paczuski", "51259636x", "paczuskiszymon16@gmail.com", "11111111111"));
        });
    }


    @Test
    void Create_Invalid_Internal_Person_Pesel_Too_Short() {
        assertThrows(IllegalArgumentException.class, () -> {
            personRepository.create(Type.EXTERNAL, new Person("Szymon" , "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "111"));
        });
    }

    @Test
    void Create_Invalid_Internal_Person_Invalid_Email_Format() {
        assertThrows(IllegalArgumentException.class, () -> {
            personRepository.create(Type.EXTERNAL, new Person("Szymon" , "Paczuski", "512596378", "paczuskiszymon16gmail.com", "111"));
        });
    }

    @Test
    void Delete_Valid_External_Person() throws JAXBException {
        Person person = personRepository.create(Type.EXTERNAL, new Person("Szymon", "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        boolean response = personRepository.delete(Type.EXTERNAL, person.getId());
        assertTrue(response);
    }

    @Test
    void Delete_Invalid_External_Person() throws JAXBException {
        boolean response = personRepository.delete(Type.EXTERNAL, 1);
        assertFalse(response);
    }

    @Test
    void Find_Person_By_Valid_Id() throws JAXBException {
        Person p1 = personRepository.create(Type.EXTERNAL, new Person("Szymon", "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        Person expectedPerson = personRepository.create(Type.EXTERNAL, new Person("Jan", "Kowalski", "501531649", "kowalski.jan@gmail.com", "11111111111"));
        Person p3 = personRepository.create(Type.EXTERNAL, new Person("Mateusz", "Nowak", "603993123", "mateusz_nowak5@gmail.com", "11111111111"));
        Person targetPerson = personRepository.findById(Type.EXTERNAL, 2);
        assertEquals(expectedPerson, targetPerson);
    }

    @Test
    void Find_Person_By_Invalid_Id() throws JAXBException {
        personRepository.create(Type.EXTERNAL, new Person("Szymon", "Paczuski", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Jan", "Kowalski", "501531649", "kowalski.jan@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Mateusz", "Nowak", "603993123", "mateusz_nowak5@gmail.com", "11111111111"));
        Person targetPerson = personRepository.findById(Type.EXTERNAL, 10);
        assertNull(targetPerson);
    }

    @Test
    void Find_List_By_First_Name() throws JAXBException {
        personRepository.create(Type.EXTERNAL, new Person("Szymon", "Smuda", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Jan", "Kowalski", "501531649", "kowalski.jan@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Mateusz", "Nowak", "603993123", "mateusz_nowak5@gmail.com", "11111111111"));
        personRepository.create(Type.INTERNAL, new Person("Szymon", "Paczuski", "512596378", "smudasmuda@gmail.com", "11111111111"));
        List<Person> personList = personRepository.findByFirstName("Szymon");
        assertEquals(2, personList.size());
    }

    @Test
    void Find_Person_By_Mobile() throws JAXBException {
        Person targetPerson = personRepository.create(Type.EXTERNAL, new Person("Szymon", "Smuda", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        Person p2 = personRepository.create(Type.EXTERNAL, new Person("Jan", "Kowalski", "501531649", "kowalski.jan@gmail.com", "11111111111"));
        Person person = personRepository.findByMobile(Type.EXTERNAL, "512596378");
        assertEquals(targetPerson, person);
    }

    @Test
    void Find_Person_By_Type_First_Name_Last_Name_Mobile() throws JAXBException {
        Person targetPerson = personRepository.create(Type.EXTERNAL, new Person("Szymon", "Smuda", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        Person person = personRepository.find(Type.EXTERNAL, "Szymon", "Smuda", "512596378");
        assertEquals(targetPerson, person);
    }

    @Test
    void Modify_Person_Valid() throws JAXBException {
        Person person = personRepository.create(Type.EXTERNAL, new Person("Szymon", "Smuda", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        person.setFirstName("New_First_Name");
        personRepository.modify(person);
        Person targetPerson = personRepository.findById(Type.EXTERNAL, 1);
        assertEquals(targetPerson.getFirstName(), "New_First_Name");
    }

    @Test
    void Modify_Person_Invalid() throws JAXBException {
        Person person = personRepository.create(Type.EXTERNAL, new Person("Szymon", "Smuda", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        assertThrows(IllegalArgumentException.class, () -> {
            person.setMobile("INVALID_MOBILE");
            personRepository.modify(person);
            Person targetPerson = personRepository.findById(Type.EXTERNAL, 1);
        });
    }

    @Test
    void Select_External_And_Internal() throws JAXBException {
        personRepository.create(Type.EXTERNAL, new Person("Szymon", "Smuda", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Jan", "Kowalski", "501531649", "kowalski.jan@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Mateusz", "Nowak", "603993123", "mateusz_nowak5@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Janusz", "Bylak", "6559513123", "bylek@op.pl", "11111111111"));

        personRepository.create(Type.INTERNAL, new Person("Anna", "Przyblska", "571556512", "loremipsum@gmail.com", "11111111111"));
        personRepository.create(Type.INTERNAL, new Person("Grzegorz", "Testowy", "614167358", "develop@gmail.com", "11111111111"));
        personRepository.create(Type.INTERNAL, new Person("Jakub", "Produkcyjny", "590194371", "prod@gmail.com", "11111111111"));

        List<Person> allPersons = personRepository.selectAll();
        assertEquals(7, allPersons.size());
    }

    @Test
    void Select_External() throws JAXBException {
        personRepository.create(Type.EXTERNAL, new Person("Szymon", "Smuda", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Jan", "Kowalski", "501531649", "kowalski.jan@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Mateusz", "Nowak", "603993123", "mateusz_nowak5@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Janusz", "Bylak", "6559513123", "bylek@op.pl", "11111111111"));

        personRepository.create(Type.INTERNAL, new Person("Anna", "Przyblska", "571556512", "loremipsum@gmail.com", "11111111111"));
        personRepository.create(Type.INTERNAL, new Person("Grzegorz", "Testowy", "614167358", "develop@gmail.com", "11111111111"));
        personRepository.create(Type.INTERNAL, new Person("Jakub", "Produkcyjny", "590194371", "prod@gmail.com", "11111111111"));

        List<Person> allPersons = personRepository.selectAll(Type.EXTERNAL);
        assertEquals(4, allPersons.size());
    }

    @Test
    void Select_Internal() throws JAXBException {
        personRepository.create(Type.EXTERNAL, new Person("Szymon", "Smuda", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Jan", "Kowalski", "501531649", "kowalski.jan@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Mateusz", "Nowak", "603993123", "mateusz_nowak5@gmail.com", "11111111111"));
        personRepository.create(Type.EXTERNAL, new Person("Janusz", "Bylak", "6559513123", "bylek@op.pl", "11111111111"));

        personRepository.create(Type.INTERNAL, new Person("Anna", "Przyblska", "571556512", "loremipsum@gmail.com", "11111111111"));
        personRepository.create(Type.INTERNAL, new Person("Grzegorz", "Testowy", "614167358", "develop@gmail.com", "11111111111"));
        personRepository.create(Type.INTERNAL, new Person("Jakub", "Produkcyjny", "590194371", "prod@gmail.com", "11111111111"));

        List<Person> allPersons = personRepository.selectAll(Type.INTERNAL);
        assertEquals(3, allPersons.size());
    }
}