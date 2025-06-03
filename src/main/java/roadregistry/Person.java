package roadregistry;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.*;

public class Person {

    public boolean addPerson(String personID, String firstName, String lastName, String address, String birthDate) {
        if (!isValidPersonID(personID) || !isValidAddress(address) || !isValidBirthDate(birthDate)) {
            return false;
        }

        try (FileWriter writer = new FileWriter("persons.txt", true)) {
            String line = personID + "|" + firstName + "|" + lastName + "|" + address + "|" + birthDate + "\n";
            writer.write(line);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean isValidPersonID(String id) {
        if (id.length() != 10) return false;

        String firstTwo = id.substring(0, 2);
        if (!firstTwo.matches("[2-9]{2}")) return false;

        String middle = id.substring(2, 8);
        int specialCount = 0;
        for (char c : middle.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) specialCount++;
        }
        if (specialCount < 2) return false;

        String lastTwo = id.substring(8, 10);
        return lastTwo.matches("[A-Z]{2}");
    }

    private boolean isValidAddress(String address) {
        String[] parts = address.split("\\|");
        return parts.length == 5 && parts[3].trim().equalsIgnoreCase("Victoria");
    }

    private boolean isValidBirthDate(String date) {
        return date.matches("^\\d{2}-\\d{2}-\\d{4}$");
    }
}
