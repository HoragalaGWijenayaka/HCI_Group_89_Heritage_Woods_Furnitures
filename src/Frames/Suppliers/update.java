package Frames.Suppliers;

import Components.Sidebar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Properties;

public class update extends JFrame {
    private JTextField codeField, nameField, contactField, catalogField, categoryField, termsField, listField;

    public update() {
        super("Edit Supplier");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        add(new Sidebar(), BorderLayout.WEST);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel, BorderLayout.CENTER);

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        header.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("src/Resources/images/newuserimg.png");
        Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        header.add(new JLabel(new ImageIcon(img)));
        JLabel title = new JLabel("Supplier Edit");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        header.add(title);
        mainPanel.add(header, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        // Build form fields
        codeField = addField(formPanel, gbc, 0, "Supply Code:");
        nameField = addField(formPanel, gbc, 1, "Supplier Name:");
        contactField = addField(formPanel, gbc, 2, "Contact Number:");
        catalogField = addField(formPanel, gbc, 3, "Product Catalog:");
        categoryField = addField(formPanel, gbc, 4, "Item Category:");
        termsField = addField(formPanel, gbc, 5, "Payment Terms:");
        listField = addField(formPanel, gbc, 6, "Item List:");

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        actionPanel.setBackground(Color.WHITE);
        JButton updateBtn = new JButton("Update");
        styleButton(updateBtn);
        actionPanel.add(updateBtn);

        JButton cancelBtn = new JButton("Cancel");
        styleButton(cancelBtn);
        actionPanel.add(cancelBtn);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        // Event handlers
        updateBtn.addActionListener(this::onUpdate);
        cancelBtn.addActionListener(e -> clearFields());

        // Load existing supplier data
        loadData();
    }

    private JTextField addField(JPanel panel, GridBagConstraints gbc, int row, String labelText) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        JTextField field = new JTextField(20);
        field.setPreferredSize(new Dimension(200, 30));
        panel.add(field, gbc);
        return field;
    }

    private void styleButton(JButton btn) {
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setBackground(btn.getText().equals("Cancel") ? Color.RED : Color.BLUE);
        btn.setForeground(Color.WHITE);
        btn.setFont(btn.getFont().deriveFont(18f));
    }

    private void clearFields() {
        codeField.setText("");
        nameField.setText("");
        contactField.setText("");
        catalogField.setText("");
        categoryField.setText("");
        termsField.setText("");
        listField.setText("");
    }

    private void loadData() {
        Properties props = new Properties();
        File file = new File("Saved_Items/suppliers.properties");
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                props.load(fis);
                codeField.setText(props.getProperty("Supply Code", ""));
                nameField.setText(props.getProperty("Supplier Name", ""));
                contactField.setText(props.getProperty("Contact Number", ""));
                catalogField.setText(props.getProperty("Product Catalog", ""));
                categoryField.setText(props.getProperty("Item Catalog", ""));
                termsField.setText(props.getProperty("Payment Terms", ""));
                listField.setText(props.getProperty("Item List", ""));
            } catch (IOException ignored) {}
        }
    }

    private void onUpdate(ActionEvent e) {
        Properties props = new Properties();
        props.setProperty("Supply Code", codeField.getText());
        props.setProperty("Supplier Name", nameField.getText());
        props.setProperty("Contact Number", contactField.getText());
        props.setProperty("Product Catalog", catalogField.getText());
        props.setProperty("Item Catalog", categoryField.getText());
        props.setProperty("Payment Terms", termsField.getText());
        props.setProperty("Item List", listField.getText());
        try (FileOutputStream fos = new FileOutputStream("Saved_Items/suppliers.properties")) {
            props.store(fos, "Supplier Details");
            JOptionPane.showMessageDialog(this, "Supplier updated successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error updating data: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new update().setVisible(true));
    }
}
