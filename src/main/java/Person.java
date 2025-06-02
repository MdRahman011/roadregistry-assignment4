import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthDate;
    private boolean isSuspended;
    private List<DemeritEntry> demeritHistory = new ArrayList<>();

    // --- Constructors and Getters/Setters ---
    public Person(String personID, String firstName, String lastName, String address, String birthDate) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthDate = birthDate;
        this.isSuspended = false;
    }

    public String getPersonID() { return personID; }
    public String getBirthDate() { return birthDate; }
    public String getAddress() { return address; }

    public void setPersonID(String personID) { this.personID = personID; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAddress(String address) { this.address = address; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    // --- Function 1: addPerson ---
    public static boolean addPerson(Person p) {
        if (!isValidPersonID(p.personID)) return false;
        if (!isValidAddress(p.address)) return false;
        if (!isValidDate(p.birthDate)) return false;

        try (FileWriter fw = new FileWriter("persons.txt", true)) {
            fw.write(p.personID + "|" + p.firstName + "|" + p.lastName + "|" + p.address + "|" + p.birthDate + "\n");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // --- Function 2: updatePersonalDetails ---
    public boolean updatePersonalDetails(String newID, String newFirstName, String newLastName, String newAddress, String newBirthDate) {
        LocalDate today = LocalDate.now();
        int age = getAge(this.birthDate);

        boolean changingDOB = !this.birthDate.equals(newBirthDate);
        boolean changingID = !this.personID.equals(newID);

        // Conditions
        if (age < 18 && !this.address.equals(newAddress)) return false;
        if (changingDOB && (!this.firstName.equals(newFirstName) || !this.lastName.equals(newLastName) || !this.address.equals(newAddress) || changingID)) return false;
        if (isEvenDigit(this.personID.charAt(0)) && changingID) return false;

        if (!isValidPersonID(newID)) return false;
        if (!isValidAddress(newAddress)) return false;
        if (!isValidDate(newBirthDate)) return false;

        // Passed all checks, update
        this.personID = newID;
        this.firstName = newFirstName;
        this.lastName = newLastName;
        this.address = newAddress;
        this.birthDate = newBirthDate;
        return true;
    }

    // --- Function 3: addDemeritPoints ---
    public String addDemeritPoints(int points, String date) {
        if (!isValidDate(date) || points < 1 || points > 6) return "Failed";

        this.demeritHistory.add(new DemeritEntry(points, date));
        int totalInTwoYears = getDemeritsInLastTwoYears();
        int age = getAge(this.birthDate);

        if ((age < 21 && totalInTwoYears > 6) || (age >= 21 && totalInTwoYears > 12)) {
            this.isSuspended = true;
        }

        try (FileWriter fw = new FileWriter("demerits.txt", true)) {
            fw.write(this.personID + "|" + points + "|" + date + "\n");
        } catch (IOException e) {
            return "Failed";
        }

        return "Success";
    }

    // --- Helper Classes and Functions ---
    private int getDemeritsInLastTwoYears() {
        int sum = 0;
        LocalDate now = LocalDate.now();
        for (DemeritEntry d : demeritHistory) {
            LocalDate offenseDate = LocalDate.parse(d.date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            if (offenseDate.isAfter(now.minusYears(2))) {
                sum += d.points;
            }
        }
        return sum;
    }

    private static boolean isValidPersonID(String id) {
        if (id.length() != 10) return false;
        if (!Character.isDigit(id.charAt(0)) || !Character.isDigit(id.charAt(1))) return false;
        int specialCount = 0;
        for (int i = 2; i <= 7; i++) {
            if (!Character.isLetterOrDigit(id.charAt(i))) specialCount++;
        }
        if (specialCount < 2) return false;
        return Character.isUpperCase(id.charAt(8)) && Character.isUpperCase(id.charAt(9));
    }

    private static boolean isValidAddress(String addr) {
        String[] parts = addr.split("\\|");
        return parts.length == 5 && parts[3].equals("Victoria");
    }

    private static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isEvenDigit(char c) {
        return Character.isDigit(c) && ((c - '0') % 2 == 0);
    }

    private static int getAge(String dob) {
        LocalDate birth = LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return LocalDate.now().getYear() - birth.getYear();
    }

    // --- Inner Class for Demerits ---
    private static class DemeritEntry {
        int points;
        String date;

        DemeritEntry(int points, String date) {
            this.points = points;
            this.date = date;
        }
    }
}
