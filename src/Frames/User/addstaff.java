package Frames.User;

import Components.Sidebar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;

public class addstaff extends JFrame {
    private JTextField namefield, emailfild, cntctnumfield, posfield,
            dobfield, genfield, statusfield, additionalInfoField;
    private String enteredName;

    public addstaff() {
        setTitle("Add Staff");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Add sidebar
        Sidebar sidebar = new Sidebar();
        add(sidebar, BorderLayout.WEST);

        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(0xD9D9D9));

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(0xD9D9D9));
        JLabel titleLabel = new JLabel("Add New Staff Member");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0xD9D9D9));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Form fields
        String[][] fields = {
                {"Name:", "namefield"},
                {"Email address:", "emailfild"},
                {"Contact Number:", "cntctnumfield"},
                {"Position:", "posfield"},
                {"Date of Birth:", "dobfield"},
                {"Gender:", "genfield"},
                {"Status:", "statusfield"},
                {"Additional Information:", "additionalInfoField"}
        };

        for(int i = 0; i < fields.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            JLabel label = new JLabel(fields[i][0]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            formPanel.add(label, gbc);

            gbc.gridx = 1;
            JTextField tf = new JTextField(25);
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            // Special handling for additional information field
            if(i == fields.length - 1) {
                tf.setPreferredSize(new Dimension(250, 80));
            } else {
                tf.setPreferredSize(new Dimension(250, 30));
            }

            switch(fields[i][1]) {
                case "namefield": namefield = tf; break;
                case "emailfild": emailfild = tf; break;
                case "cntctnumfield": cntctnumfield = tf; break;
                case "posfield": posfield = tf; break;
                case "dobfield": dobfield = tf; break;
                case "genfield": genfield = tf; break;
                case "statusfield": statusfield = tf; break;
                case "additionalInfoField": additionalInfoField = tf; break;
            }
            formPanel.add(tf, gbc);
        }

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        buttonPanel.setBackground(new Color(0xD9D9D9));

        JButton saveButton = createActionButton("Save", new Color(0, 120, 215));
        JButton cancelButton = createActionButton("Cancel", new Color(220, 53, 69));

        saveButton.addActionListener(e -> saveData());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 35));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private void saveData() {
        Properties properties = new Properties();
        try {
            String filePath = "staff.properties";
            File file = new File(filePath);

            if(file.exists()) {
                try (FileInputStream fileInput = new FileInputStream(file)) {
                    properties.load(fileInput);
                }
            }

            String staffKey = "Staff_" + System.currentTimeMillis();
            properties.setProperty(staffKey + ".name", namefield.getText());
            properties.setProperty(staffKey + ".email", emailfild.getText());
            properties.setProperty(staffKey + ".contact", cntctnumfield.getText());
            properties.setProperty(staffKey + ".position", posfield.getText());
            properties.setProperty(staffKey + ".dob", dobfield.getText());
            properties.setProperty(staffKey + ".gender", genfield.getText());
            properties.setProperty(staffKey + ".status", statusfield.getText());
            properties.setProperty(staffKey + ".additional", additionalInfoField.getText());

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                properties.store(fileOut, "Staff Details");
            }

            JOptionPane.showMessageDialog(this, "Staff member saved successfully!");
            clearForm();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }

    private void clearForm() {
        namefield.setText("");
        emailfild.setText("");
        cntctnumfield.setText("");
        posfield.setText("");
        dobfield.setText("");
        genfield.setText("");
        statusfield.setText("");
        additionalInfoField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new addstaff());
    }
}