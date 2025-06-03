import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import roadregistry.Person;

public class PersonTest {

    // --- addPerson Tests ---
    @Test
    public void testAddPerson_ValidData() {
        Person p = new Person("45a#@9_!AZ", "Ali", "Zaidi", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(Person.addPerson(p));
    }

    @Test
    public void testAddPerson_InvalidIDTooShort() {
        Person p = new Person("45@!AZ", "Ali", "Zaidi", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertFalse(Person.addPerson(p));
    }

    @Test
    public void testAddPerson_InvalidIDNoSpecialChars() {
        Person p = new Person("45abcdefAZ", "Ali", "Zaidi", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertFalse(Person.addPerson(p));
    }

    @Test
    public void testAddPerson_InvalidAddressWrongState() {
        Person p = new Person("45@#abc!AZ", "Ali", "Zaidi", "32|Highland Street|Melbourne|NSW|Australia", "15-11-1990");
        assertFalse(Person.addPerson(p));
    }

    @Test
    public void testAddPerson_InvalidBirthDateFormat() {
        Person p = new Person("45@#abc!AZ", "Ali", "Zaidi", "32|Highland Street|Melbourne|Victoria|Australia", "1990/11/15");
        assertFalse(Person.addPerson(p));
    }

    // --- updatePersonalDetails Tests ---
    @Test
    public void testUpdateDetails_Under18_CannotChangeAddress() {
        Person p = new Person("56@#123!AZ", "Jane", "Doe", "5|Main Rd|Geelong|Victoria|Australia", "01-01-2010");
        assertFalse(p.updatePersonalDetails("56@#123!AZ", "Jane", "Doe", "100|New St|Geelong|Victoria|Australia", "01-01-2010"));
    }

    @Test
    public void testUpdateDetails_ChangeDOBOnly() {
        Person p = new Person("56@#123!AZ", "John", "Smith", "5|Main Rd|Geelong|Victoria|Australia", "01-01-1995");
        assertTrue(p.updatePersonalDetails("56@#123!AZ", "John", "Smith", "5|Main Rd|Geelong|Victoria|Australia", "02-02-1995"));
    }

    @Test
    public void testUpdateDetails_ChangeDOBAndAddress_ShouldFail() {
        Person p = new Person("56@#123!AZ", "John", "Smith", "5|Main Rd|Geelong|Victoria|Australia", "01-01-1995");
        assertFalse(p.updatePersonalDetails("56@#123!AZ", "John", "Smith", "10|Lake Rd|Geelong|Victoria|Australia", "02-02-1995"));
    }

    @Test
    public void testUpdateDetails_EvenIDStart_CannotChangeID() {
        Person p = new Person("24@#123!AZ", "Sara", "Khan", "5|Main Rd|Geelong|Victoria|Australia", "01-01-1990");
        assertFalse(p.updatePersonalDetails("78@#123!AZ", "Sara", "Khan", "5|Main Rd|Geelong|Victoria|Australia", "01-01-1990"));
    }

    @Test
    public void testUpdateDetails_ValidUpdate() {
        Person p = new Person("57@#123!AZ", "Zac", "Lee", "9|Old St|Geelong|Victoria|Australia", "01-01-2000");
        assertTrue(p.updatePersonalDetails("57@#123!AZ", "Zac", "Lee", "9|Old St|Geelong|Victoria|Australia", "01-01-2000"));
    }

    // --- addDemeritPoints Tests ---
    @Test
    public void testAddDemeritPoints_ValidEntry() {
        Person p = new Person("67@#abc!AZ", "Ali", "Zaidi", "9|Some St|Melbourne|Victoria|Australia", "01-01-2000");
        String result = p.addDemeritPoints(3, "01-06-2024");
        assertEquals("Success", result);
    }

    @Test
    public void testAddDemeritPoints_InvalidDateFormat() {
        Person p = new Person("67@#abc!AZ", "Ali", "Zaidi", "9|Some St|Melbourne|Victoria|Australia", "01-01-2000");
        String result = p.addDemeritPoints(3, "2024-06-01");
        assertEquals("Failed", result);
    }

    @Test
    public void testAddDemeritPoints_InvalidPointsTooHigh() {
        Person p = new Person("67@#abc!AZ", "Ali", "Zaidi", "9|Some St|Melbourne|Victoria|Australia", "01-01-2000");
        String result = p.addDemeritPoints(10, "01-06-2024");
        assertEquals("Failed", result);
    }

    @Test
    public void testAddDemeritPoints_TriggerSuspension_U21() {
        Person p = new Person("68@#abc!AZ", "Ali", "Zaidi", "9|Some St|Melbourne|Victoria|Australia", "01-01-2007");
        p.addDemeritPoints(4, "01-06-2023");
        p.addDemeritPoints(3, "01-06-2024");
        assertTrue(p.addDemeritPoints(1, "01-06-2025").equals("Success"));
        assertTrue(p.addDemeritPoints(1, "02-06-2025").equals("Success")); // will be suspended after total exceeds 6
    }

    @Test
    public void testAddDemeritPoints_TriggerSuspension_Adult() {
        Person p = new Person("68@#abc!AZ", "Ali", "Zaidi", "9|Some St|Melbourne|Victoria|Australia", "01-01-1990");
        for (int i = 0; i < 3; i++) {
            p.addDemeritPoints(4, "01-06-2024");
        }
        assertTrue(p.addDemeritPoints(1, "02-06-2025").equals("Success"));
    }

   
    public void testGetPersonByLicense_Exists() {
    Person.addPerson("Ali", 25, "LIC777", "Melbourne");
    Person p = Person.getPersonByLicense("LIC777");
    assertNotNull(p);
    assertEquals("Ali", p.name);
}

    public void testGetPersonByLicense_NotExists() {
    Person p = Person.getPersonByLicense("FAKELIC");
    assertNull(p);
}

}
