package roadregistry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void testAddPerson_ValidData() {
        Person person = new Person();
        boolean result = person.addPerson("23@_#aB$CD", "John", "Doe", "12|Main St|Melbourne|Victoria|Australia", "15-08-1990");
        assertTrue(result);
    }

    @Test
    public void testAddPerson_InvalidPersonID_Length() {
        Person person = new Person();
        boolean result = person.addPerson("23@_aB$C", "Jane", "Smith", "10|King Rd|Melbourne|Victoria|Australia", "20-07-1995");
        assertFalse(result);
    }

    @Test
    public void testAddPerson_InvalidAddress_StateNotVictoria() {
        Person person = new Person();
        boolean result = person.addPerson("23@_#aB$CD", "Ali", "Reza", "45|Queen St|Melbourne|NSW|Australia", "01-01-1985");
        assertFalse(result);
    }

    @Test
    public void testAddPerson_InvalidBirthDateFormat() {
        Person person = new Person();
        boolean result = person.addPerson("23@_#aB$CD", "Mira", "Khan", "88|High St|Geelong|Victoria|Australia", "1990-08-15");
        assertFalse(result);
    }

    @Test
    public void testAddPerson_NotEnoughSpecialChars() {
        Person person = new Person();
        boolean result = person.addPerson("23abcdeFGH", "Sam", "Lee", "5|Hill Rd|Ballarat|Victoria|Australia", "10-12-1999");
        assertFalse(result);
    }
}
