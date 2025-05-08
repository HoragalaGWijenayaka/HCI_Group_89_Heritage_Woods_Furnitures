package Frames.User;

import Components.Sidebar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Properties;

public class updatedetails extends JFrame {

    private JTextField namefield;
    private JTextField possitionfild;
    private JTextField Addressfield;
    private JTextField phonenumber;
    private JTextField dobfield;
    private JTextField descfeild;

    public updatedetails() {
        setLayout(new BorderLayout());

        // Sidebar
        Sidebar sidebar = new Sidebar();
        add(sidebar, BorderLayout.WEST);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel, BorderLayout.CENTER);

        // Title
        JLabel title = new JLabel("Update User", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mainPanel.add(title, BorderLayout.NORTH);

        // Left Image Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0xD9D9D9));
        leftPanel.setPreferredSize(new Dimension(300, 500));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        ImageIcon userIcon = new ImageIcon("src/Resources/images/newuserimg.png");
        Image scaledImage = userIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);



        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(imageLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        leftPanel.add(Box.createVerticalGlue());

        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Right Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(0xD9D9D9));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Arial", Font.BOLD, 16);

        String[] labels = {"Name:", "Position:", "Address:", "Phone Number:", "Date of Birth:", "Description:"};
        JTextField[] fields = new JTextField[]{
                namefield = new JTextField(20),
                possitionfild = new JTextField(20),
                Addressfield = new JTextField(20),
                phonenumber = new JTextField(20),
                dobfield = new JTextField(20),
                descfeild = new JTextField(20)
        };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            JLabel label = new JLabel(labels[i]);
            label.setFont(labelFont);
            formPanel.add(label, gbc);

            gbc.gridx = 1;
            fields[i].setPreferredSize(new Dimension(250, i == 5 ? 70 : 30));
            formPanel.add(fields[i], gbc);
        }

        // Save Button
        gbc.gridx = 0;
        gbc.gridy = labels.length + 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(200, 40));
        saveButton.setBackground(Color.BLUE);
        saveButton.setForeground(Color.WHITE);
        formPanel.add(saveButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        saveButton.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    saveData();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            });
        });
    }

    private void saveData() throws FileNotFoundException {
        Properties properties = new Properties();
        try {
            String filePath = "C:/Users/Manul Perera/IdeaProjects/Phoenix_Furnitures/Saved_Items/user.properties";
            File file = new File(filePath);

            if (file.exists()) {
                FileInputStream fileInput = new FileInputStream(file);
                properties.load(fileInput);
                fileInput.close();
            }

            properties.setProperty("Name", namefield.getText());
            properties.setProperty("Position", possitionfild.getText());
            properties.setProperty("Address", Addressfield.getText());
            properties.setProperty("Phone Number", phonenumber.getText());
            properties.setProperty("Date of Birth", dobfield.getText());
            properties.setProperty("Description", descfeild.getText());

            FileOutputStream fileOut = new FileOutputStream(filePath);
            properties.store(fileOut, "User Details");
            fileOut.close();

            JOptionPane.showMessageDialog(null, "Data updated successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            updatedetails frame = new updatedetails();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("User Update");
            frame.setSize(1200, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
