package roadregistry;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.*;

public class Person {

    // Adds a new person record to the file if validation passes
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

    // Updates address and birth date for a given personID if record exists and input is valid
    public boolean updatePersonalDetails(String personID, String newAddress, String newBirthDate) {
    if (!isValidPersonID(personID) || !isValidAddress(newAddress) || !isValidBirthDate(newBirthDate)) {
        return false;
    }

    try {
        java.io.File inputFile = new java.io.File("persons.txt");
        java.util.List<String> lines = java.nio.file.Files.readAllLines(inputFile.toPath());
        boolean found = false;

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split("\\|");
            if (parts.length >= 6 && parts[0].equals(personID)) {
                lines.set(i, personID + "|" + parts[1] + "|" + parts[2] + "|" + newAddress + "|" + newBirthDate);
                found = true;
                break;
            }
        }

        if (!found) return false;

        java.nio.file.Files.write(inputFile.toPath(), lines);
        return true;

    } catch (IOException e) {
        return false;
    }
}

     public String addDemeritPoints(String personID, int points, String offenseDate, String birthDate) {
    if (!isValidBirthDate(offenseDate) ||  points < 1 || points > 6) {
        return "Failed";
    }

    try {
        java.io.File file = new java.io.File("demerits.txt");
        java.util.List<String> lines = file.exists() ? java.nio.file.Files.readAllLines(file.toPath()) : new java.util.ArrayList<>();
        java.util.List<String> updatedLines = new java.util.ArrayList<>();
        int totalPointsLast2Years = 0;

        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
        java.time.LocalDate offense = java.time.LocalDate.parse(offenseDate, formatter);
        java.time.LocalDate dob = java.time.LocalDate.parse(birthDate, formatter);
        int age = java.time.Period.between(dob, today).getYears();

        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 4 && parts[0].equals(personID)) {
                java.time.LocalDate prevOffense = java.time.LocalDate.parse(parts[2], formatter);
                if (java.time.temporal.ChronoUnit.DAYS.between(prevOffense, today) <= 730) {
                    totalPointsLast2Years += Integer.parseInt(parts[1]);
                }
            }
            updatedLines.add(line);
        }

        totalPointsLast2Years += points;
        boolean suspended = (age < 21 && totalPointsLast2Years > 6) || (age >= 21 && totalPointsLast2Years > 12);

        updatedLines.add(personID + "|" + points + "|" + offenseDate + "|" + (suspended ? "true" : "false"));
        java.nio.file.Files.write(file.toPath(), updatedLines);
        return "Success";

    } catch (Exception e) {
        return "Failed";
    }
}






    // Checks if person ID has correct format: 10 characters, specific pattern
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
