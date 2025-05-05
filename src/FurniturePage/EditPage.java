package Frames.FurniturePage;

import Components.Sidebar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class EditPage extends JFrame {

    private String enteredName;
    private JButton saveButton;  // Added saveButton declaration

    private static JLabel createIconLabel(ImageIcon icon) {
        JLabel label = new JLabel(icon);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add some padding
        return label;
    }

    private static JButton createButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(41, 41, 41));
        button.setPreferredSize(new Dimension(width, height));
        button.setBorderPainted(false);
        return button;
    }

    JPanel mainPanel, subPanel1, brandName, navPanel, bottomPanel, subPanel2;
    JLabel nameLabel;
    JLabel imageLabel1;
    JLabel nameLabel2, category, name, price, size, date, description, fileLabel;
    JComboBox<String> categoryField;
    JTextField nameField;
    JTextField sizeField;
    JTextField priceField;
    JTextField sizesField;
    JTextField dateField;
    JTextField descField;
    JButton home, interior, kitchen, sell, user, account, logout, save, uploadBtn;

    EditPage() {
        Sidebar sidebar = new Sidebar();

        JPanel buttonpanel = new JPanel(new BorderLayout());
        buttonpanel.setPreferredSize(new Dimension(9000, 1000));
        buttonpanel.setBackground(Color.WHITE);

        JPanel hiPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        hiPanel.setBackground(Color.WHITE);
        JLabel upLabel = new JLabel("Appliances");
        upLabel.setFont(upLabel.getFont().deriveFont(Font.BOLD, 24f));

        JPanel subheadPanel = new JPanel();
        JLabel subhead = new JLabel("Edit Furniture");
        subheadPanel.add(subhead);
        subhead.setForeground(new Color(255, 0, 0));
        hiPanel.add(upLabel);
        hiPanel.add(subheadPanel, BorderLayout.SOUTH);

        buttonpanel.add(hiPanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 70)));

        ImageIcon editIcon = new ImageIcon(getClass().getResource("/Resources/images/edit1.png"));
        Image editImg = editIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JButton addbtn = new JButton(new ImageIcon(editImg));
        addbtn.setPreferredSize(new Dimension(700, 200));
        addbtn.setOpaque(false);
        addbtn.setContentAreaFilled(false);
        addbtn.setBorderPainted(false);
        buttonsPanel.add(addbtn);

        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 90)));

        JButton savebtn = new JButton("Update");
        savebtn.setPreferredSize(new Dimension(250, 60));
        savebtn.setBackground(Color.BLUE);
        savebtn.setForeground(Color.WHITE);
        savebtn.setFont(savebtn.getFont().deriveFont(19f));
        buttonsPanel.add(savebtn);

        JButton cancelbtn = new JButton("Cancel");
        cancelbtn.setPreferredSize(new Dimension(250, 60));
        cancelbtn.setBackground(Color.RED);
        cancelbtn.setForeground(Color.WHITE);
        cancelbtn.setFont(savebtn.getFont().deriveFont(19f));
        buttonsPanel.add(cancelbtn);

        buttonpanel.add(buttonsPanel, BorderLayout.CENTER);

        getContentPane().add(buttonpanel, BorderLayout.CENTER);

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 50);
        detailsPanel.setBackground(Color.LIGHT_GRAY);

        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(50);
        nameField.setPreferredSize(new Dimension(100, 30));
        detailsPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        detailsPanel.add(new JLabel("Price"), gbc);

        gbc.gridx = 1;
        priceField = new JTextField(50);
        priceField.setPreferredSize(new Dimension(100, 30));
        detailsPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        detailsPanel.add(new JLabel("Size"), gbc);

        gbc.gridx = 1;
        sizeField = new JTextField(50);
        sizeField.setPreferredSize(new Dimension(100, 30));
        detailsPanel.add(sizeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        detailsPanel.add(new JLabel("Date"), gbc);

        gbc.gridx = 1;
        dateField = new JTextField(50);
        dateField.setPreferredSize(new Dimension(300, 30));
        detailsPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        detailsPanel.add(new JLabel("Description"), gbc);

        gbc.gridx = 1;
        descField = new JTextField(50);
        descField.setPreferredSize(new Dimension(300, 30));
        detailsPanel.add(descField, gbc);

        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        gbc.gridy++;
        detailsPanel.add(new JLabel(), gbc);

        detailsPanel.setPreferredSize(new Dimension(800, 100));

        getContentPane().add(detailsPanel, BorderLayout.EAST);
        this.add(sidebar, BorderLayout.WEST);

        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        savebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateData();
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
    }

    private void readData() throws IOException {
        Properties properties = new Properties();
        String filePath = "C:/Users/Manul Perera/IdeaProjects/Phoenix_Furnitures/Saved_Items/furniture.properties";
        File file = new File(filePath);

        if (file.exists()) {
            FileInputStream fileInput = new FileInputStream(file);
            try {
                properties.load(fileInput);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileInput.close();
        }

        nameField.setText(properties.getProperty("Name", ""));
        priceField.setText(properties.getProperty("Price", ""));
        sizeField.setText(properties.getProperty("Size", ""));
        dateField.setText(properties.getProperty("Date", ""));
        descField.setText(properties.getProperty("Description", ""));
    }

    private void updateData() throws IOException {
        Properties properties = new Properties();
        String filePath = "C:/Users/Manul Perera/IdeaProjects/Phoenix_Furnitures/Saved_Items/furniture.properties";
        File file = new File(filePath);

        if (file.exists()) {
            FileInputStream fileInput = new FileInputStream(file);
            try {
                properties.load(fileInput);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileInput.close();
        }

        properties.setProperty("Name", nameField.getText());
        properties.setProperty("Price", priceField.getText());
        properties.setProperty("Size", sizeField.getText());
        properties.setProperty("Date", dateField.getText());
        properties.setProperty("Description", descField.getText());

        try {
            properties.store(new FileOutputStream(filePath), "Furniture Details");
            JOptionPane.showMessageDialog(null, "Data updated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EditPage editPage = new EditPage();
        editPage.setTitle("Phoenix Furniture");
        editPage.setSize(990, 950);
        editPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editPage.setVisible(true);
    }
}
