package roadregistry;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.*;

public class Person {

    // Adds a person to persons.txt if all validations pass
    public boolean addPerson(String personID, String firstName, String lastName, String address, String birthDate) {
        // Validate ID, address, and birthdate format
        if (!isValidPersonID(personID) || !isValidAddress(address) || !isValidBirthDate(birthDate)) {
            return false;
        }

        try (FileWriter writer = new FileWriter("persons.txt", true)) {
            // Save data in pipe-separated format
            String line = personID + "|" + firstName + "|" + lastName + "|" + address + "|" + birthDate + "\n";
            writer.write(line);
            return true;
        } catch (IOException e) {
            // Handle write failure
            return false;
        }
    }

    // Validates the person ID format: 10 characters, specific rules
    private boolean isValidPersonID(String id) {
        if (id.length() != 10) return false;

        // First two chars must be digits between 2–9
        String firstTwo = id.substring(0, 2);
        if (!firstTwo.matches("[2-9]{2}")) return false;

        // Characters 3–8 must contain at least 2 special characters
        String middle = id.substring(2, 8);
        int specialCount = 0;
        for (char c : middle.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) specialCount++;
        }
        if (specialCount < 2) return false;

        // Last two characters must be uppercase letters
        String lastTwo = id.substring(8, 10);
        return lastTwo.matches("[A-Z]{2}"); 
    }

    // Validates address format and checks that state is Victoria
    private boolean isValidAddress(String address) {
        String[] parts = address.split("\\|");
        return parts.length == 5 && parts[3].trim().equalsIgnoreCase("Victoria");
    }

    // Checks birthdate is in DD-MM-YYYY format
    private boolean isValidBirthDate(String date) {
        return date.matches("^\\d{2}-\\d{2}-\\d{4}$");
    }
}
