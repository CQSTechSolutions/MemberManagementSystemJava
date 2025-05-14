# Member Management System

A Java Swing application for managing member information with basic CRUD (Create, Read, Update, Delete) functionality.

## Features

- View all members
- Add new members with validation
- Search members by ID, Name, Age, or Salary
- Edit member information
- Delete members
- Input validation for each field

## Requirements

- Java Development Kit (JDK) 8 or higher
- Java Swing (included in the JDK)

## How to Run

1. **Compile the Java file:**
   ```
   javac MemberManagementSystem.java
   ```

2. **Run the application:**
   ```
   java MemberManagementSystem
   ```

## Usage Guide

### Main Window
The main window displays three buttons on the left side:
- **Add**: Opens a dialog to add a new member
- **Show**: Displays all members in the system
- **Search**: Opens a dialog to search for members

### Adding a Member
1. Click the "Add" button
2. Enter the member details (ID, Name, Age, Salary)
3. Click "Add" to save the new member
   - The system validates the input formats and checks for duplicate IDs
   - A confirmation message appears when successful

### Viewing Members
Click the "Show" button to display all members in the system.

### Searching for Members
1. Click the "Search" button
2. Select a search criterion (ID, Name, Age, Salary)
3. Enter the search value
4. Click "Search"
   - Matching members will be displayed in the main window
   - An Edit/Delete dialog will appear for further actions

### Editing a Member
1. After searching, enter the index of the member you want to edit
2. Click "Edit"
3. Update the member information (ID cannot be changed)
4. Click "Save" to confirm changes

### Deleting a Member
1. After searching, enter the index of the member you want to delete
2. Click "Delete"
3. A confirmation message will appear when the member is deleted 