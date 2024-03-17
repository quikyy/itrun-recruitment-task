package person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@XmlRootElement(name = "persons")
public class PersonRepository {

    private List<Person> persons;
    private Type type;
    private Environment environment;
    private JAXBContext context;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private String repoPath;
    private final Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    public PersonRepository(Type type, Environment environment) throws JAXBException {
        this.type = type;
        this.persons = new ArrayList<>();
        this.context = JAXBContext.newInstance(PersonRepository.class);
        this.marshaller = context.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        this.unmarshaller = context.createUnmarshaller();
        this.environment = environment;

        if (environment.equals(Environment.PROD)) {
            repoPath = String.format("src/main/resources/%s/database.xml", type);
        } else if (environment.equals(Environment.TEST)) {
            repoPath = String.format("src/test/resources/%s/database.xml", type);
        }
        this.tryToLoadExistingDatabase();
    }

    public PersonRepository() {
    }

    private void tryToLoadExistingDatabase() {
        if (environment.equals(Environment.TEST)) {
            return;
        }
        try {
            XMLInputFactory xif = XMLInputFactory.newFactory();
            StreamSource xml = new StreamSource(repoPath);
            XMLStreamReader xsr = xif.createXMLStreamReader(xml);

            List<Person> existingPersons = new ArrayList<>();
            while (xsr.getEventType() != XMLStreamReader.END_DOCUMENT) {
                if (xsr.isStartElement() && "person".equals(xsr.getLocalName())) {
                    Person user = (Person) unmarshaller.unmarshal(xsr);
                    existingPersons.add(user);
                }
                xsr.next();
            }
            persons = existingPersons;
        } catch (XMLStreamException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    @XmlElementWrapper
    @XmlElement(name = "person")
    public List<Person> getPersons() {
        return persons;
    }

    public Person create(Person person) throws JAXBException {
        person.setId(this.assignIdToPerson());
        persons.add(person);
        marshaller.marshal(this, new File(repoPath));
        logger.info(String.format("[%s] Person created - %s", type.name(), person));
        return person;
    }


    public boolean delete(int id) throws JAXBException {
        Person person = this.findById(id);
        if (person == null) {
            logger.warn(String.format("[%s] Person with ID: %s not found and cannot be deleted", type, id));
            return false;
        }
        this.persons.remove(person);
        marshaller.marshal(this, new File(repoPath));
        logger.info(String.format("[%s] Person with ID: %s found and deleted", type, id));
        return true;
    }

    public Person findById(int id) {
        Optional<Person> optionalPerson = this.persons.stream()
                .filter(p -> p.getId() == id)
                .findAny();
        return optionalPerson.orElse(null);
    }

    public List<Person> findByFirstName(String firstName) {
        List<Person> personList =  persons.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName))
                .toList();
        logger.info(String.format("[%s] Persons found by first_name: %s", type.name(), personList.size()));
        if (!personList.isEmpty()) {
            personList.forEach(p -> logger.info(p.toString()));
        }
        return personList;
    }

    public List<Person> findByLastName(String lastName) {
        List<Person> personList = persons.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .toList();
        logger.info(String.format("[%s] Persons found by last_name: %s", type.name(), personList.size()));
        if (!personList.isEmpty()) {
            personList.forEach(p -> logger.info(p.toString()));
        }
        return personList;
    }

    public Person findByMobile(String mobile) {
        Optional<Person> optionalPerson = persons.stream()
                .filter(p -> p.getMobile().equals(mobile))
                .findFirst();
        if (optionalPerson.isEmpty()) {
            logger.warn(String.format("[%s] Person with provided details not found", type.name()));
            return null;
        }
        return optionalPerson.get();
    }

    public Person find(String firstName, String lastName, String mobile) {
        Optional<Person> optionalPerson = persons.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName) && p.getMobile().equals(mobile))
                .findAny();
        if (optionalPerson.isEmpty()) {
            logger.warn(String.format("[%s] Person with provided details not found", type.name()));
            return null;
        }
        logger.info(String.format("[%s] Person with provided details not found ", type.name()));
        return optionalPerson.get();
    }

    public void modify(Person person) throws JAXBException {
        logger.info(String.format("[%s] repository updated - %s", type.name(), person));
        marshaller.marshal(this, new File(repoPath));
    }

    public List<Person> selectAll() {
        logger.info(String.format("[%s] List[%s]: ", type.name(), persons.size()));
        persons.forEach(p -> logger.info(p.toString()));
        return persons;
    }

    private int assignIdToPerson() {
        return this.persons.size() + 1;
    }
}
