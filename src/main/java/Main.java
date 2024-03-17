
import org.apache.log4j.BasicConfigurator;
import person.Environment;
import person.Person;
import jakarta.xml.bind.JAXBException;
import person.Type;
import person.PersonRepositoryWrapper;
import javax.xml.stream.XMLStreamException;

public class Main {
    public static void main(String[] args) throws JAXBException, XMLStreamException {
        BasicConfigurator.configure();
        PersonRepositoryWrapper personRepository = new PersonRepositoryWrapper(Environment.PROD);

        boolean mockSomeData = true;
        if (mockSomeData) {
            personRepository.create(Type.EXTERNAL, new Person("Szymon", "Smuda", "512596378", "paczuskiszymon16@gmail.com", "11111111111"));
            personRepository.create(Type.EXTERNAL, new Person("Jan", "Kowalski", "501531649", "kowalski.jan@gmail.com", "11111111111"));
            personRepository.create(Type.EXTERNAL, new Person("Mateusz", "Nowak", "603993123", "mateusz_nowak5@gmail.com", "11111111111"));
            personRepository.create(Type.EXTERNAL, new Person("Janusz", "Bylak", "6559513123", "bylek@op.pl", "11111111111"));

            personRepository.create(Type.INTERNAL, new Person("Anna", "Przyblska", "571556512", "loremipsum@gmail.com", "11111111111"));
            personRepository.create(Type.INTERNAL, new Person("Grzegorz", "Testowy", "614167358", "develop@gmail.com", "11111111111"));
            personRepository.create(Type.INTERNAL, new Person("Jakub", "Produkcyjny", "590194371", "prod@gmail.com", "11111111111"));
        }

    }
}
