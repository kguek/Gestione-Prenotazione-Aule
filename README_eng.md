# Classroom Booking Management (Java Swing)

A desktop application developed for a university exam in Java for the management and monitoring of university room bookings (Classrooms and Laboratories).
The project was built applying Object-Oriented Programming (OOP) principles and features a modern graphical user interface.

## Key Features
- **Room Management:** Support for different types of rooms (Classrooms, Laboratories) read dynamically from a file.
- **Smart Bookings:** Automatic check for schedule conflicts and compliance with specific constraints based on the room type.
- **Modern Graphical Interface:** Built with Java Swing and enhanced by the **FlatLaf** Look and Feel for a clean and professional design.
- **Data Persistence:** Automatic (background autosave) and manual saving to the file system.
- **Export:** Ability to print the occupancy table.

## Automated Tests (JUnit 5)
The project includes an automated unit test suite to ensure code reliability:
- **Logic Validation:** Preventive check of time overlaps and specific constraints for each room type.
- **CRUD Operations:** Verification of the correct functioning of adding, modifying, and deleting bookings.
- **Data Saving:** Testing of saving and loading on the file system using JUnit's isolated environment (`@TempDir`), to ensure that tests do not pollute the user's real files.

To run the entire test suite, use the command:
```bash
mvn test
```

To run a single test file, use the command:
```bash
mvn test -Dtest="TEST_FILENAME"
```

## How to run the project 

This project uses **Maven** for dependency management and compilation.

Make sure you have [Maven](https://maven.apache.org/) and JDK 17 (or higher) installed on your system.

1. **Compilation:**
   - **On Linux/MacOS/Windows:**
     ```bash
     mvn clean compile
     ```
    
2. **Execution:**
   ```bash
   mvn exec:java -Dexec.mainClass="App.Main"
   ```
