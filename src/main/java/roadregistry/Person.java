package roadregistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person {
    private String name;
    private int age;
    private String licenseNumber;
    private String address;

    private static List<Person> registry = new ArrayList<>();

    public Person(String name, int age, String licenseNumber, String address) {
        this.name = name;
        this.age = age;
        this.licenseNumber = licenseNumber;
        this.address = address;
    }

    public static String addPerson(String name, int age, String licenseNumber, String address) {
        if (age < 18) {
            return "Person must be at least 18 years old.";
        }

        for (Person p : registry) {
            if (Objects.equals(p.licenseNumber, licenseNumber)) {
                return "License number already exists.";
            }
        }

        Person newPerson = new Person(name, age, licenseNumber, address);
        registry.add(newPerson);
        return "Person added successfully.";
    }

    public static List<Person> getRegistry() {
        return registry;
    }

    public static Person getPersonByLicense(String licenseNumber) {
    for (Person p : personList) {
        if (p.licenseNumber.equals(licenseNumber)) {
            return p;
        }
    }
    return null;
}

}
