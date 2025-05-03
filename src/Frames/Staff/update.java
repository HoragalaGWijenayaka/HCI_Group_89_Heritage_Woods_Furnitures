package Frames.Staff;

import Components.Sidebar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class update extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField contactField;
    private JTextField positionField;
    private JTextField dobField;
    private JTextField genderField;
    private JTextField statusField;
    private JTextField infoField;

    public update() {
        super("Update Staff Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar (unchanged)
        Sidebar sidebar = new Sidebar();
        add(sidebar, BorderLayout.WEST);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel, BorderLayout.CENTER);

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        headerPanel.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("src/Resources/images/updateicon.png");
        Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        headerPanel.add(new JLabel(new ImageIcon(img)));
        JLabel title = new JLabel("Update Details");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        headerPanel.add(title);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0: Name
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        nameField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(nameField, gbc);

        // Row 1: Email
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(emailField, gbc);

        // Row 2: Contact
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1;
        contactField = new JTextField(20);
        contactField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(contactField, gbc);

        // Row 3: Position
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Position:"), gbc);
        gbc.gridx = 1;
        positionField = new JTextField(20);
        positionField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(positionField, gbc);

        // Row 4: DOB
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        dobField = new JTextField(20);
        dobField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(dobField, gbc);

        // Row 5: Gender
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        genderField = new JTextField(20);
        genderField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(genderField, gbc);

        // Row 6: Status
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        statusField = new JTextField(20);
        statusField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(statusField, gbc);

        // Row 7: Additional Info
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Additional Information:"), gbc);
        gbc.gridx = 1;
        infoField = new JTextField(20);
        infoField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(infoField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Action Buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        actionPanel.setBackground(Color.WHITE);
        JButton updateBtn = new JButton("Update");
        updateBtn.setPreferredSize(new Dimension(100, 30));
        updateBtn.setBackground(Color.BLUE);
        updateBtn.setForeground(Color.WHITE);
        actionPanel.add(updateBtn);
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setPreferredSize(new Dimension(100, 30));
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);
        actionPanel.add(cancelBtn);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        // Load existing data
        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Listeners
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateData();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        cancelBtn.addActionListener(e -> dispose());
    }

    private void readData() throws IOException {
        Properties props = new Properties();
        String path = "C:/Users/Manul Perera/IdeaProjects/Phoenix_Furnitures/Saved_Items/staff.properties";
        File file = new File(path);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                props.load(fis);
            }
        }
        nameField.setText(props.getProperty("Name", ""));
        emailField.setText(props.getProperty("Email", ""));
        contactField.setText(props.getProperty("Contact Number", ""));
        positionField.setText(props.getProperty("Position", ""));
        dobField.setText(props.getProperty("Date of Birth", ""));
        genderField.setText(props.getProperty("Gender", ""));
        statusField.setText(props.getProperty("Address", ""));
        infoField.setText(props.getProperty("Additional Information", ""));
    }

    private void updateData() throws IOException {
        Properties props = new Properties();
        String path = "C:/Users/Manul Perera/IdeaProjects/Phoenix_Furnitures/Saved_Items/staff.properties";
        File file = new File(path);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                props.load(fis);
            }
        }
        props.setProperty("Name", nameField.getText());
        props.setProperty("Email", emailField.getText());
        props.setProperty("Contact Number", contactField.getText());
        props.setProperty("Position", positionField.getText());
        props.setProperty("Date of Birth", dobField.getText());
        props.setProperty("Gender", genderField.getText());
        props.setProperty("Address", statusField.getText());
        props.setProperty("Additional Information", infoField.getText());
        props.store(new java.io.FileOutputStream(path), "Staff Details");
        JOptionPane.showMessageDialog(this, "Data updated successfully!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            update frame = new update();
            frame.setVisible(true);
        });
    }
}
