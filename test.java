import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ali extends JFrame {

    private JTextArea displayArea;
    private Map<Integer, String> memberMap;  // Üye bilgilerini saklamak için

    public ali() {
        // Üye haritasını başlatma
        memberMap = new HashMap<>();
        // Varsayılan üyeleri ekleme
        memberMap.put(1, " Alice Smith, Age: 30, Salary: 4500.0");
        memberMap.put(2, " Bob Johnson, Age: 40, Salary: 5200.0");

        // Ana pencere ayarları
        setTitle("Member Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500, 400);

        // Başlık paneli
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Welcome to the Member Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Yan panel (Butonlar)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.BLUE);

        // Butonlar
        JButton addButton = new JButton("Add");
        addButton.setMaximumSize(new Dimension(60, 25));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton showButton = new JButton("Show");
        showButton.setMaximumSize(new Dimension(70, 25));
        showButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton searchButton = new JButton("Search");
        searchButton.setMaximumSize(new Dimension(80, 25));
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Butonları ekleme
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        buttonPanel.add(showButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        buttonPanel.add(searchButton);

        buttonPanel.setPreferredSize(new Dimension(90, 0));
        add(buttonPanel, BorderLayout.WEST);

        // Bilgi gösterim alanı (JTextArea)
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Show butonuna tıklama olayı
        showButton.addActionListener(e -> showMembers());

        // Add butonuna tıklama olayı
        addButton.addActionListener(e -> showAddMemberDialog());

        // Search butonuna tıklama olayı
        searchButton.addActionListener(e -> showSearchMemberDialog());

        // Pencereyi görünür yapma
        setVisible(true);
    }

    // Üyeleri gösterme
    private void showMembers() {
        StringBuilder members = new StringBuilder();
        int index = 0;
        for (Map.Entry<Integer, String> entry : memberMap.entrySet()) {
            members.append("ID: ").append(entry.getKey()).append("\n")
                    .append("Name: ").append(entry.getValue()).append("\n")
                    .append("[index: ").append(index).append("]\n\n");
            index++;
        }
        displayArea.setText(members.toString());
    }

    // Üye ekleme penceresi
    private void showAddMemberDialog() {
        JFrame addFrame = new JFrame("Add Member");
        addFrame.setSize(400, 350);
        addFrame.setLocationRelativeTo(null);
        addFrame.setLayout(new BorderLayout(5, 5));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();

        JLabel salaryLabel = new JLabel("Salary:");
        JTextField salaryField = new JTextField();

        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton addMemberButton = new JButton("Add");
        addMemberButton.setPreferredSize(new Dimension(0, 30));
        buttonPanel.add(addMemberButton, BorderLayout.CENTER);

        addMemberButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                double salary = Double.parseDouble(salaryField.getText());

                // ID'nin zaten mevcut olup olmadığını kontrol et
                if (memberMap.containsKey(id)) {
                    JOptionPane.showMessageDialog(addFrame, "You entered existing id.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    return; // ID zaten var, eklemeyi durdur
                }

                // İsim alanının boş olup olmadığını kontrol et
                if (name.isEmpty()) throw new Exception("Name cannot be empty");

                // Başarılı ekleme işlemi
                memberMap.put(id, name + ", Age: " + age + ", Salary: " + salary);
                JOptionPane.showMessageDialog(addFrame, "Member added successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
                addFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addFrame, "Invalid input format.", "Message", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addFrame, ex.getMessage(), "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(ageLabel);
        formPanel.add(ageField);
        formPanel.add(salaryLabel);
        formPanel.add(salaryField);

        addFrame.add(formPanel, BorderLayout.CENTER);
        addFrame.add(buttonPanel, BorderLayout.SOUTH);
        addFrame.setVisible(true);
    }

    // Üye arama penceresi
    private void showSearchMemberDialog() {
        JFrame searchFrame = new JFrame("Search Member");
        searchFrame.setSize(400, 200);
        searchFrame.setLocationRelativeTo(null);
        searchFrame.setLayout(new BorderLayout(5, 5));

        // Üst panel: ID ve TextField yan yana
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel searchLabel = new JLabel("Search by:");
        String[] searchOptions = {"ID", "Name", "Age", "Salary"};
        JComboBox<String> searchCriteria = new JComboBox<>(searchOptions);
        JTextField searchField = new JTextField(23);

        // Arama butonu paneli (alt kısım)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton searchMemberButton = new JButton("Search");
        searchMemberButton.setPreferredSize(new Dimension(100, 25));

        // Arama işlemi
        searchMemberButton.addActionListener(e -> {
            String criteria = (String) searchCriteria.getSelectedItem();
            String query = searchField.getText().toLowerCase();
            StringBuilder result = new StringBuilder();

            if (query.isEmpty()) {
                displayArea.setText("Please enter a value to search.");
                return;
            }

            boolean found = false;
            int index = 1;
            for (Map.Entry<Integer, String> entry : memberMap.entrySet()) {
                String[] memberDetails = entry.getValue().split(", ");
                switch (criteria) {
                    case "ID":
                        if (String.valueOf(entry.getKey()).contains(query)) found = true;
                        break;
                    case "Name":
                        if (memberDetails[0].toLowerCase().contains(query)) found = true;
                        break;
                    case "Age":
                        if (memberDetails[1].toLowerCase().contains("age: " + query)) found = true;
                        break;
                    case "Salary":
                        if (memberDetails[2].toLowerCase().contains("salary: " + query)) found = true;
                        break;
                }

                if (found) {
                    result.append("Index: ").append(index).append(" | ID: ").append(entry.getKey()).append("\n")
                            .append("Details: ").append(entry.getValue()).append("\n\n");
                    index++;
                    found = false;
                }
            }

            if (result.length() == 0) {
                displayArea.setText("No member found.");
            } else {
                displayArea.setText(result.toString());
            }

            // MouseListener ekleme (Çift tıklama ile düzenleme/silme)
            displayArea.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Çift tıklama
                        try {
                            int caretPosition = displayArea.getCaretPosition();
                            int line = displayArea.getLineOfOffset(caretPosition);
                            String[] lines = displayArea.getText().split("\n");
                            String clickedLine = lines[line].trim();

                            // ID bilgisini doğru şekilde çekme
                            if (clickedLine.startsWith("Index: ")) {
                                // Satırın doğru formatta olup olmadığını kontrol et
                                String[] parts = clickedLine.split("\\|");
                                if (parts.length > 1 && parts[1].trim().startsWith("ID:")) {
                                    String idPart = parts[1].trim(); // "ID: X" şeklinde olan kısım
                                    int memberId = Integer.parseInt(idPart.split(": ")[1].trim());
                                    showEditDeleteDialog(memberId);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Error: Invalid ID format in the selected line.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Please click on a valid result line.");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error identifying the member. Try again.");
                        }
                    }
                }
            });


        });

        // Bileşenleri üst panele ekleme
        topPanel.add(searchLabel);
        topPanel.add(searchCriteria);
        topPanel.add(searchField);

        // Buton paneline arama butonunu ekleme
        buttonPanel.add(searchMemberButton);

        // Ana pencereye ekleme
        searchFrame.add(topPanel, BorderLayout.NORTH);
        searchFrame.add(buttonPanel, BorderLayout.CENTER);

        searchFrame.setVisible(true);
    }



    private void showEditDeleteDialog(int memberId) {
        JFrame editFrame = new JFrame("Edit/Delete Member");
        editFrame.setSize(400, 250);
        editFrame.setLocationRelativeTo(null);

        String memberInfo = memberMap.get(memberId);
        JTextField editField = new JTextField(memberInfo, 30);

        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");

        saveButton.addActionListener(e -> {
            memberMap.put(memberId, editField.getText());
            JOptionPane.showMessageDialog(editFrame, "Change saved!");
            editFrame.dispose();
        });

        deleteButton.addActionListener(e -> {
            memberMap.remove(memberId);
            JOptionPane.showMessageDialog(editFrame, "Member deleted!");
            editFrame.dispose();
        });

        editFrame.setLayout(new FlowLayout());
        editFrame.add(editField);
        editFrame.add(saveButton);
        editFrame.add(deleteButton);
        editFrame.setVisible(true);
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(ali::new);
    }
}