import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.regex.Pattern;

public class AddressBook {
    // Email and phone validation patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@gmail\\.com$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");

    public static void main(String[] args) {
        // Create the main window
        JFrame frame = new JFrame("Address Book");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Set up the table
        String[] columns = {"Name", "Phone", "Email"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Create input panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input fields and labels
        JTextField nameField = new JTextField(10);
        JTextField phoneField = new JTextField(10);
        JTextField emailField = new JTextField(10);

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // Buttons
        JButton addButton = new JButton("Add Contact");
        JButton editButton = new JButton("Edit Contact");
        JButton deleteButton = new JButton("Delete Contact");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(addButton, gbc);
        gbc.gridx = 1;
        panel.add(editButton, gbc);
        gbc.gridx = 2;
        panel.add(deleteButton, gbc);

        // Add button action with validation
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            // Validate input
            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                System.out.println("Error: All fields must be filled to add a contact.");
                return;
            }
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                System.out.println("Error: Email must be a valid Gmail address (e.g., user@gmail.com).");
                return;
            }
            if (!PHONE_PATTERN.matcher(phone).matches()) {
                System.out.println("Error: Phone number must be exactly 10 digits (e.g., 1234567890).");
                return;
            }

            // Add contact if validation passes
            model.addRow(new Object[]{name, phone, email});
            System.out.println("Added Contact: Name=" + name + ", Phone=" + phone + ", Email=" + email);
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
        });

        // Edit button action with validation
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();

                // Validate input
                if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    System.out.println("Error: All fields must be filled to edit a contact.");
                    return;
                }
                if (!EMAIL_PATTERN.matcher(email).matches()) {
                    System.out.println("Error: Email must be a valid Gmail address (e.g., user@gmail.com).");
                    return;
                }
                if (!PHONE_PATTERN.matcher(phone).matches()) {
                    System.out.println("Error: Phone number must be exactly 10 digits (e.g., 1234567890).");
                    return;
                }

                // Update contact if validation passes
                model.setValueAt(name, selectedRow, 0);
                model.setValueAt(phone, selectedRow, 1);
                model.setValueAt(email, selectedRow, 2);
                System.out.println("Edited Contact at row " + selectedRow + ": Name=" + name + ", Phone=" + phone + ", Email=" + email);
                nameField.setText("");
                phoneField.setText("");
                emailField.setText("");
                table.clearSelection();
            } else {
                System.out.println("Error: No contact selected for editing.");
            }
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String name = (String) model.getValueAt(selectedRow, 0);
                model.removeRow(selectedRow);
                System.out.println("Deleted Contact: Name=" + name);
                nameField.setText("");
                phoneField.setText("");
                emailField.setText("");
            } else {
                System.out.println("Error: No contact selected for deletion.");
            }
        });

        // Table selection listener to populate fields
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    nameField.setText((String) model.getValueAt(selectedRow, 0));
                    phoneField.setText((String) model.getValueAt(selectedRow, 1));
                    emailField.setText((String) model.getValueAt(selectedRow, 2));
                }
            }
        });

        // Add panel to frame
        frame.add(panel, BorderLayout.SOUTH);

        // Display the window
        frame.setVisible(true);
    }
}