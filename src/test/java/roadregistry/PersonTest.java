package roadregistry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class PersonTest {

    // --- Tests for addPerson() ---

    @Test
    public void testAddPerson_ValidData() {
        // All inputs valid – should return true
        Person person = new Person();
        boolean result = person.addPerson("23@_#aB$CD", "John", "Doe", "12|Main St|Melbourne|Victoria|Australia", "15-08-1990");
        assertTrue(result);
    }

    @Test
    public void testAddPerson_InvalidPersonID_Length() {
        // ID too short – should return false
        Person person = new Person();
        boolean result = person.addPerson("23@_aB$C", "Jane", "Smith", "10|King Rd|Melbourne|Victoria|Australia", "20-07-1995");
        assertFalse(result);
    }

    @Test
    public void testAddPerson_InvalidAddress_StateNotVictoria() {
        // Address has wrong state – should return false
        Person person = new Person();
        boolean result = person.addPerson("23@_#aB$CD", "Ali", "Reza", "45|Queen St|Melbourne|NSW|Australia", "01-01-1985");
        assertFalse(result);
    }

    @Test
    public void testAddPerson_InvalidBirthDateFormat() {
        // Date is in wrong format – should return false
        Person person = new Person();
        boolean result = person.addPerson("23@_#aB$CD", "Mira", "Khan", "88|High St|Geelong|Victoria|Australia", "1990-08-15");
        assertFalse(result);
    }

    @Test
    public void testAddPerson_NotEnoughSpecialChars() {
        // ID missing required special characters – should return false
        Person person = new Person();
        boolean result = person.addPerson("23abcdeFGH", "Sam", "Lee", "5|Hill Rd|Ballarat|Victoria|Australia", "10-12-1999");
        assertFalse(result);
    }

    // --- Tests for updatePersonalDetails() ---

    @Test
    public void testUpdatePersonalDetails_Success() {
        // Should update person details correctly if valid
        Person person = new Person();
        String personID = "23@#abCDXY";  
        String originalAddress = "12|Main St|Melbourne|Victoria|Australia";
        String originalBirthDate = "15-08-1990";
        String newAddress = "99|New St|Geelong|Victoria|Australia";
        String newBirthDate = "01-01-1995";

        try {
            // Clear persons.txt to start clean
            java.nio.file.Files.write(java.nio.file.Paths.get("persons.txt"), new byte[0]);

            // Add person and then update them
            boolean added = person.addPerson(personID, "John", "Doe", originalAddress, originalBirthDate);
            assertTrue(added);

            boolean updated = person.updatePersonalDetails(personID, newAddress, newBirthDate);
            assertTrue(updated);

        } catch (IOException e) {
            fail("IO Exception during test: " + e.getMessage());
        }
    }

    @Test
    public void testUpdatePersonalDetails_InvalidState() {
        // Invalid state in new address – should fail
        Person person = new Person();
        boolean result = person.updatePersonalDetails("23@#ab$CD", "22|Garden Ave|Geelong|NSW|Australia", "01-01-2000");
        assertFalse(result);
    }

    @Test
    public void testUpdatePersonalDetails_InvalidBirthDateFormat() {
        // Wrong birthdate format – should fail
        Person person = new Person();
        boolean result = person.updatePersonalDetails("23@#ab$CD", "22|Garden Ave|Geelong|Victoria|Australia", "2000/01/01");
        assertFalse(result);
    }

    @Test
    public void testUpdatePersonalDetails_PersonNotFound() {
        // Person ID not found in file – should fail
        Person person = new Person();
        boolean result = person.updatePersonalDetails("99!!0000ZZ", "33|Main St|Werribee|Victoria|Australia", "10-10-1990");
        assertFalse(result);
    }

    // --- Tests for addDemeritPoints() ---

    @Test
    public void testAddDemeritPoints_ValidUnder21NotSuspended() {
        // Valid underage case – should pass
        Person person = new Person();
        String result = person.addDemeritPoints("23@#CD!%AB", 3, "01-06-2024", "01-01-2006");
        assertEquals("Success", result);
    }

    @Test
    public void testAddDemeritPoints_InvalidDateFormat() {
        // Invalid offense date format – should fail
        Person person = new Person();
        String result = person.addDemeritPoints("23@#CD!%AB", 2, "2024-06-01", "01-01-2000");
        assertEquals("Failed", result);
    }

    @Test
    public void testAddDemeritPoints_InvalidPointRange() {
        // Too many points at once – should fail
        Person person = new Person();
        String result = person.addDemeritPoints("23@#CD!%AB", 10, "01-06-2024", "01-01-2000");
        assertEquals("Failed", result);
    }

    @Test
    public void testAddDemeritPoints_SuspensionOver21() {
        // Over 21 – build up to 12+ points
        Person person = new Person();
        for (int i = 0; i < 3; i++) {
            person.addDemeritPoints("23@#CD!%AB", 4, "01-06-2024", "01-01-1990");
        }
        String result = person.addDemeritPoints("23@#CD!%AB", 1, "01-06-2024", "01-01-1990");
        assertEquals("Success", result);
    }

    @Test
    public void testAddDemeritPoints_SuspensionUnder21() {
        // Under 21 – build up to >6 points to suspend
        Person person = new Person();
        for (int i = 0; i < 2; i++) {
            person.addDemeritPoints("23@#CD!%XY", 3, "01-06-2024", "01-01-2008");
        }
        String result = person.addDemeritPoints("23@#CD!%XY", 1, "01-06-2024", "01-01-2008");
        assertEquals("Success", result);
    }
}
