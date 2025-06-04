RoadRegistry Assignment 4

Overview
--------

This repository contains the source code and tests for Assignment 4 of Software Engineering Fundamentals. It focuses on managing road registry data using Java, with GitHub Actions configured for CI/CD.

The project includes:
- Functions to add and update personal driver records
- Logic for handling demerit points and determining licence suspension
- Unit tests for core methods
- Automated testing via GitHub Actions

Technologies Used
-----------------
- Java 17
- Maven
- JUnit 5
- Git & GitHub
- GitHub Actions (CI workflow)

Project Structure
-----------------
.
├── pom.xml  
├── README.md  
├── persons.txt  
├── demerits.txt  
├── test-trigger.txt  
├── .github  
│   └── workflows  
│       └── java-ci.yml  
├── src  
│   ├── main  
│   │   └── java  
│   │       └── roadregistry  
│   │           └── Person.java  
│   └── test  
│       └── java  
│           └── roadregistry  
│               └── PersonTest.java  

Running Locally
---------------
1. Make sure you have Java 17 and Maven installed.
2. Open a terminal in the project directory.
3. To compile the project, run:
   mvn compile
4. To run the unit tests, run:
   mvn test

GitHub Actions CI
-----------------
- The CI pipeline is set up in `.github/workflows/java-ci.yml`.
- Tests automatically run whenever you push code.
- You can view test results in the "Actions" tab on GitHub.

Testing Details
---------------
Unit tests are written in `PersonTest.java` and cover:
- addPerson
- updatePersonalDetails
- addDemeritPoints

Each method is tested with valid and invalid input cases.

Team Contributions
------------------
- Each team member contributed to writing, testing, and pushing code.
- All commits are visible under the "Commits" tab on GitHub.

Important Notes
---------------
- Make sure `persons.txt` and `demerits.txt` exist in the root folder.
- Output is written to these files during testing and runtime.
- Use dummy commits to re-trigger CI if needed.

---

2025 RoadRegistry – Assignment 4 – Software Engineering Fundamentals
