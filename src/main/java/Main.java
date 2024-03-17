
import org.apache.log4j.BasicConfigurator;
import person.Environment;
import person.Person;
import jakarta.xml.bind.JAXBException;
import person.Type;
import person.PersonRepositoryWrapper;
import random.RandomGenerator;

import javax.xml.stream.XMLStreamException;

public class Main {
    public static void main(String[] args) throws JAXBException, XMLStreamException {
        BasicConfigurator.configure();
        PersonRepositoryWrapper personRepository = new PersonRepositoryWrapper(Environment.PROD);

        boolean mockSomeData = true;
        if (mockSomeData) {
            personRepository.create(Type.EXTERNAL, new Person(RandomGenerator.getRandomFirstName(), RandomGenerator.getRandomLastName(), RandomGenerator.getRandomNumber(9), "paczuskiszymon16@gmail.com", RandomGenerator.getRandomNumber(11)));
            personRepository.create(Type.EXTERNAL, new Person(RandomGenerator.getRandomFirstName(), RandomGenerator.getRandomLastName(), RandomGenerator.getRandomNumber(9), "kowalski.jan@gmail.com", RandomGenerator.getRandomNumber(11)));
            personRepository.create(Type.EXTERNAL, new Person(RandomGenerator.getRandomFirstName(), RandomGenerator.getRandomLastName(), RandomGenerator.getRandomNumber(9), "mateusz_nowak5@gmail.com", RandomGenerator.getRandomNumber(11)));
            personRepository.create(Type.EXTERNAL, new Person(RandomGenerator.getRandomFirstName(), RandomGenerator.getRandomLastName(), RandomGenerator.getRandomNumber(9), "bylek@op.pl", RandomGenerator.getRandomNumber(11)));

            personRepository.create(Type.INTERNAL, new Person(RandomGenerator.getRandomFirstName(), RandomGenerator.getRandomLastName(), RandomGenerator.getRandomNumber(9), "loremipsum@gmail.com", RandomGenerator.getRandomNumber(11)));
            personRepository.create(Type.INTERNAL, new Person(RandomGenerator.getRandomFirstName(), RandomGenerator.getRandomLastName(), RandomGenerator.getRandomNumber(9), "develop@gmail.com", RandomGenerator.getRandomNumber(11)));
            personRepository.create(Type.INTERNAL, new Person(RandomGenerator.getRandomFirstName(), RandomGenerator.getRandomLastName(), RandomGenerator.getRandomNumber(9), "prod@gmail.com", RandomGenerator.getRandomNumber(11)));
        }

    }
}
