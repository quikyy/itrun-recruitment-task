package person;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "person")
@XmlType(propOrder = { "id", "firstName", "lastName", "mobile", "email", "pesel" })
public class Person {

    protected int id;
    protected String firstName;
    protected String lastName;
    protected String mobile;
    protected String email;
    protected String pesel;

    public Person() {}

    public Person(String firstName, String lastName, String mobile, String email, String pesel) {
        this.validate(firstName, lastName, mobile, email, pesel);
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.pesel = pesel;
    }

    public Person(int id, String firstName, String lastName, String mobile, String email, String pesel) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.pesel = pesel;
    }

    private void validate(String firstName, String lastName, String mobile, String email, String pesel) {
        this.validateFirstName(firstName);
        this.validateLastName(lastName);
        this.validateLastName(mobile);
        this.validateEmail(email);
        this.validatePesel(pesel);
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getPesel() {
        return pesel;
    }

    @XmlElement(name = "id")
    protected void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "first_name")
    protected void setFirstName(String firstName) {
        this.validateFirstName(firstName);
        this.firstName = firstName;
    }

    @XmlElement(name = "last_name")
    protected void setLastName(String lastName) {
        this.validateLastName(lastName);
        this.lastName = lastName;
    }

    @XmlElement(name = "mobile")
    protected void setMobile(String mobile) {
        this.validateMobile(mobile);
        this.mobile = mobile;
    }

    @XmlElement(name = "email")
    protected void setEmail(String email) {
        this.validateEmail(email);
        this.email = email;
    }

    @XmlElement(name = "pesel")
    protected void setPesel(String pesel) {
        this.validatePesel(pesel);
        this.pesel = pesel;
    }

    private void validateFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("First Name cannot be null or empty");
        }
    }

    private void validateLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Last Name cannot be null or empty");
        }
    }

    private void validateMobile(String mobile) {
        if (!mobile.matches(PersonValidator.NUMBER_REGEX) || mobile.length() != PersonValidator.MOBILE_LENGTH) {
            throw new IllegalArgumentException("Mobile number has to contains only 9 number values");
        }
    }

    private void validateEmail(String email) {
        if (!email.matches(PersonValidator.EMAIL_REGEX)) {
            throw new IllegalArgumentException("Email address in invalid");
        }
    }

    private void validatePesel(String pesel) {
        if (!pesel.matches(PersonValidator.NUMBER_REGEX) || pesel.length() != PersonValidator.PESEL_LENGTH) {
            throw new IllegalArgumentException("PESEL number has to contains only 11 number values");
        }
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", pesel='" + pesel + '\'' +
                '}';
    }
}
