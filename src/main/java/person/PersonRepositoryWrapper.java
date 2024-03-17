package person;

import jakarta.xml.bind.JAXBException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonRepositoryWrapper {

    private final PersonRepository externalPersonRepository;
    private final PersonRepository internalPersonRepository;

    public PersonRepositoryWrapper(Environment environment) throws JAXBException {
        externalPersonRepository = new PersonRepository(Type.EXTERNAL, environment);
        internalPersonRepository = new PersonRepository(Type.INTERNAL, environment);
    }

    public Person create(Type type, Person person) throws JAXBException {
        if (type == Type.EXTERNAL) {
            return externalPersonRepository.create(person);
        } else {
            return internalPersonRepository.create(person);
        }
    }

    public boolean delete(Type type, int id) throws JAXBException {
        if (type == Type.EXTERNAL) {
            return externalPersonRepository.delete(id);
        } else {
            return internalPersonRepository.delete(id);
        }
    }

    public Person findById(Type type, int id) {
        if (type == Type.EXTERNAL) {
            return externalPersonRepository.findById(id);
        } else {
            return internalPersonRepository.findById(id);
        }
    }

    public List<Person> findByFirstName(String firstName) {
        List<Person> personList = new ArrayList<>();
        personList.addAll(externalPersonRepository.findByFirstName(firstName));
        personList.addAll(internalPersonRepository.findByFirstName(firstName));
        return personList;
    }

    public List<Person> findByFirstName(Type type, String firstName) {
        List<Person> personList = new ArrayList<>();
        switch (type) {
            case EXTERNAL -> personList = externalPersonRepository.findByFirstName(firstName);
            case INTERNAL -> personList = internalPersonRepository.findByFirstName(firstName);
        }
        return personList;
    }

    public void findByLastName(String lastName) {
        List<Person> personList = externalPersonRepository.findByLastName(lastName);
        personList.addAll(internalPersonRepository.findByLastName(lastName));
    }

    public List<Person> findByLastName(Type type, String lastName) {
        List<Person> personList = new ArrayList<>();
        switch (type) {
            case EXTERNAL -> personList = externalPersonRepository.findByFirstName(lastName);
            case INTERNAL -> personList = internalPersonRepository.findByLastName(lastName);
        }
        return personList;
    }

    public Person find(Type type, String firstName, String lastName, String mobile) {
        Person person = null;
        switch (type) {
            case EXTERNAL -> person = externalPersonRepository.find(firstName, lastName, mobile);
            case INTERNAL -> person = internalPersonRepository.find(firstName, lastName, mobile);
        }
        return person;
    }

    public void modify(Person person) throws JAXBException {
        if (person == null) {
            return;
        }
        Type type = this.findPersonInRepository(person);
        switch (Objects.requireNonNull(type)) {
            case EXTERNAL -> externalPersonRepository.modify(person);
            case INTERNAL -> internalPersonRepository.modify(person);
        }
    }

    public List<Person> selectAll() {
        List<Person> personList = externalPersonRepository.selectAll();
        personList.addAll(internalPersonRepository.selectAll());
        return personList;
    }

    public List<Person> selectAll(Type type) {
        List<Person> personList = new ArrayList<>();
        switch (type) {
            case EXTERNAL -> personList = externalPersonRepository.selectAll();
            case INTERNAL -> personList = internalPersonRepository.selectAll();
        }
        return personList;
    }

    private Type findPersonInRepository(Person person) {
        if (externalPersonRepository.getPersons().contains(person)) {
            return Type.EXTERNAL;
        } else if (internalPersonRepository.getPersons().contains(person)) {
            return Type.INTERNAL;
        } else {
            return null;
        }
    }

}
