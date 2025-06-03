package roadregistry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class PersonTest {

    //addperson function tests

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

    //tests for update personal details functions

    @Test
    public void testUpdatePersonalDetails_Success() {
    Person person = new Person();
    String personID = "23@#abCDXY";  
    String originalAddress = "12|Main St|Melbourne|Victoria|Australia";
    String originalBirthDate = "15-08-1990";
    String newAddress = "99|New St|Geelong|Victoria|Australia";
    String newBirthDate = "01-01-1995";

    try {
        // Clear the file
        java.nio.file.Files.write(java.nio.file.Paths.get("persons.txt"), new byte[0]);

        // Add person
        boolean added = person.addPerson(personID, "John", "Doe", originalAddress, originalBirthDate);
        assertTrue(added, "Person should be added successfully");

        // Try update
        boolean updated = person.updatePersonalDetails(personID, newAddress, newBirthDate);
        assertTrue(updated, "Update should return true for valid existing record");

    } catch (IOException e) {
        fail("IO Exception during test: " + e.getMessage());
    }
}


    @Test
    public void testUpdatePersonalDetails_InvalidState() {
        Person person = new Person();
        boolean result = person.updatePersonalDetails("23@#ab$CD", "22|Garden Ave|Geelong|NSW|Australia", "01-01-2000");
        assertFalse(result);
    }

    @Test
    public void testUpdatePersonalDetails_InvalidBirthDateFormat() {
        Person person = new Person();
        boolean result = person.updatePersonalDetails("23@#ab$CD", "22|Garden Ave|Geelong|Victoria|Australia", "2000/01/01");
        assertFalse(result);
    }

    @Test
    public void testUpdatePersonalDetails_PersonNotFound() {
        Person person = new Person();
        boolean result = person.updatePersonalDetails("99!!0000ZZ", "33|Main St|Werribee|Victoria|Australia", "10-10-1990");
        assertFalse(result);
    }
}
