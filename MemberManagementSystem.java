import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MemberManagementSystem extends JFrame {
    // Member class to store member data
    private static class Member {
        private int id;
        private String name;
        private int age;
        private double salary;

        public Member(int id, String name, int age, double salary) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.salary = salary;
        }

        // Getters and setters
        public int getId() { return id; }
        public String getName() { return name; }
        public int getAge() { return age; }
        public double getSalary() { return salary; }
        
        public void setName(String name) { this.name = name; }
        public void setAge(int age) { this.age = age; }
        public void setSalary(double salary) { this.salary = salary; }
    }

    private JTextArea displayArea;
    private List<Member> members = new ArrayList<>();
    private JButton addButton, showButton, searchButton;

    public MemberManagementSystem() {
        // Initialize pre-defined members
        members.add(new Member(1, "Alice Smith", 30, 4500.0));
        members.add(new Member(2, "Bob Johnson", 40, 5200.0));

        // Setup main frame
        setTitle("Member Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.LIGHT_GRAY);
        JLabel titleLabel = new JLabel("Welcome to the Member Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Create button panel (left side)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLUE);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setPreferredSize(new Dimension(80, getHeight()));

        // Create buttons
        addButton = new JButton("Add");
        showButton = new JButton("Show");
        searchButton = new JButton("Search");

        // Set button sizes and alignment
        Dimension buttonSize = new Dimension(60, 30);
        addButton.setMaximumSize(buttonSize);
        showButton.setMaximumSize(buttonSize);
        searchButton.setMaximumSize(buttonSize);
        
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        showButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add buttons to panel with spacing
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(showButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(searchButton);
        
        add(buttonPanel, BorderLayout.WEST);

        // Create display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Add button action listeners
        addButton.addActionListener(e -> showAddMemberDialog());
        showButton.addActionListener(e -> showAllMembers());
        searchButton.addActionListener(e -> showSearchDialog());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to display all members in the text area
    private void showAllMembers() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            sb.append("ID: ").append(member.getId()).append("\n");
            sb.append("Name: ").append(member.getName()).append("\n");
            sb.append("Age: ").append(member.getAge()).append("\n");
            sb.append("Salary: ").append(member.getSalary()).append("\n");
            sb.append("[index: ").append(i).append("]\n\n");
        }
        displayArea.setText(sb.toString());
    }

    // Method to show add member dialog
    private void showAddMemberDialog() {
        JDialog addDialog = new JDialog(this, "Add Member", true);
        addDialog.setSize(350, 250);
        addDialog.setLayout(new BorderLayout());

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add form fields
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        
        JLabel salaryLabel = new JLabel("Salary:");
        JTextField salaryField = new JTextField();

        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(ageLabel);
        formPanel.add(ageField);
        formPanel.add(salaryLabel);
        formPanel.add(salaryField);

        // Add button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add");
        buttonPanel.add(addButton);

        // Add action listener to add button
        addButton.addActionListener(e -> {
            try {
                // Validate fields are not empty
                if (idField.getText().isEmpty() || nameField.getText().isEmpty() || 
                    ageField.getText().isEmpty() || salaryField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(addDialog, "Invalid input format.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Validate and parse input
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                double salary = Double.parseDouble(salaryField.getText());

                // Check if ID already exists
                boolean idExists = false;
                for (Member member : members) {
                    if (member.getId() == id) {
                        idExists = true;
                        break;
                    }
                }

                if (idExists) {
                    JOptionPane.showMessageDialog(addDialog, "You entered existing id.", "Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Add new member
                    members.add(new Member(id, name, age, salary));
                    JOptionPane.showMessageDialog(addDialog, "Member added successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
                    addDialog.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addDialog, "Invalid input format.", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        addDialog.setLocationRelativeTo(this);
        addDialog.setVisible(true);
    }

    // Method to show search dialog
    private void showSearchDialog() {
        JDialog searchDialog = new JDialog(this, "Search Member", true);
        searchDialog.setSize(350, 150);
        searchDialog.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel searchLabel = new JLabel("Search by:");
        String[] searchOptions = {"ID", "Name", "Age", "Salary"};
        JComboBox<String> searchCriteria = new JComboBox<>(searchOptions);
        JTextField searchField = new JTextField(15);

        searchPanel.add(searchLabel);
        searchPanel.add(searchCriteria);
        searchPanel.add(searchField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton searchButton = new JButton("Search");
        buttonPanel.add(searchButton);

        searchButton.addActionListener(e -> {
            String criterion = (String) searchCriteria.getSelectedItem();
            String query = searchField.getText().trim();
            
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(searchDialog, "Please enter a search value", "Message", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            List<Integer> foundIndices = new ArrayList<>();
            StringBuilder resultText = new StringBuilder();
            
            for (int i = 0; i < members.size(); i++) {
                Member member = members.get(i);
                boolean match = false;
                
                switch (criterion) {
                    case "ID":
                        if (String.valueOf(member.getId()).contains(query)) {
                            match = true;
                        }
                        break;
                    case "Name":
                        if (member.getName().toLowerCase().contains(query.toLowerCase())) {
                            match = true;
                        }
                        break;
                    case "Age":
                        if (String.valueOf(member.getAge()).contains(query)) {
                            match = true;
                        }
                        break;
                    case "Salary":
                        if (String.valueOf(member.getSalary()).contains(query)) {
                            match = true;
                        }
                        break;
                }
                
                if (match) {
                    foundIndices.add(i);
                    resultText.append("ID: ").append(member.getId())
                             .append(" | Name: ").append(member.getName())
                             .append(" | Age: ").append(member.getAge())
                             .append(" | Salary: ").append(member.getSalary())
                             .append(" | (index: ").append(i).append(")\n");
                }
            }
            
            if (foundIndices.isEmpty()) {
                JOptionPane.showMessageDialog(searchDialog, "No members found", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                displayArea.setText(resultText.toString());
                searchDialog.dispose();
                showEditDeleteDialog(foundIndices);
            }
        });

        searchDialog.add(searchPanel, BorderLayout.CENTER);
        searchDialog.add(buttonPanel, BorderLayout.SOUTH);
        searchDialog.setLocationRelativeTo(this);
        searchDialog.setVisible(true);
    }

    // Method to show edit/delete dialog after search
    private void showEditDeleteDialog(List<Integer> indices) {
        JDialog editDeleteDialog = new JDialog(this, "Edit/Delete Member", true);
        editDeleteDialog.setSize(350, 100);
        editDeleteDialog.setLayout(new BorderLayout());

        JPanel indexPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel indexLabel = new JLabel("Enter index from search result:");
        JTextField indexField = new JTextField(5);
        indexPanel.add(indexLabel);
        indexPanel.add(indexField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Edit button action
        editButton.addActionListener(e -> {
            try {
                int index = Integer.parseInt(indexField.getText());
                if (indices.contains(index)) {
                    Member member = members.get(index);
                    editDeleteDialog.dispose();
                    showEditMemberDialog(member, index);
                } else {
                    JOptionPane.showMessageDialog(editDeleteDialog, "Invalid index", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDeleteDialog, "Invalid index format", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            try {
                int index = Integer.parseInt(indexField.getText());
                if (indices.contains(index)) {
                    members.remove(index);
                    JOptionPane.showMessageDialog(editDeleteDialog, "Member deleted.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    editDeleteDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(editDeleteDialog, "Invalid index", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDeleteDialog, "Invalid index format", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        editDeleteDialog.add(indexPanel, BorderLayout.CENTER);
        editDeleteDialog.add(buttonPanel, BorderLayout.SOUTH);
        editDeleteDialog.setLocationRelativeTo(this);
        editDeleteDialog.setVisible(true);
    }

    // Method to show edit member dialog
    private void showEditMemberDialog(Member member, int index) {
        JDialog editDialog = new JDialog(this, "Edit Member", true);
        editDialog.setSize(350, 250);
        editDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(String.valueOf(member.getId()));
        idField.setEditable(false); // ID cannot be changed

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(member.getName());

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(String.valueOf(member.getAge()));

        JLabel salaryLabel = new JLabel("Salary:");
        JTextField salaryField = new JTextField(String.valueOf(member.getSalary()));

        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(ageLabel);
        formPanel.add(ageField);
        formPanel.add(salaryLabel);
        formPanel.add(salaryField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Save");
        buttonPanel.add(saveButton);

        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                double salary = Double.parseDouble(salaryField.getText());

                // Update member
                member.setName(name);
                member.setAge(age);
                member.setSalary(salary);

                JOptionPane.showMessageDialog(editDialog, "Changes saved", "Message", JOptionPane.INFORMATION_MESSAGE);
                editDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDialog, "Invalid input format", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);
        editDialog.setLocationRelativeTo(this);
        editDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemberManagementSystem::new);
    }
} 