package akkastudy.typedactor.java;

/**
 * User: Daniel Hinojosa (dhinojosa@evolutionnext.com)
 * Date: 3/8/13
 * Time: 7:01 PM
 */
public class Person {
    private final String firstName;
    private final String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return !(firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) &&
                !(lastName != null ? !lastName.equals(person.lastName) : person.lastName != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 453 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}
