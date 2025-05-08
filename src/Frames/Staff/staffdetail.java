package Frames.Staff;

import Components.Sidebar;
import Frames.User.addstaff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class staffdetail extends JFrame {

    // Simulating local storage with a Map
    private Map<String, String> localStorage = new HashMap<>();

    public staffdetail() {
        // Populating initial staff details
        populateInitialStaffDetails();

        // Your panel setup
        JPanel masterPanel = new JPanel();
        masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
        add(masterPanel, BorderLayout.NORTH);

        JLabel supplierLabel = new JLabel("Staff");
        supplierLabel.setHorizontalAlignment(SwingConstants.LEFT);
        supplierLabel.setFont(new Font("Arial", Font.BOLD, 20));
        supplierLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));
        masterPanel.add(supplierLabel);

        // Staff page details
        JPanel topPanel = new JPanel();

        // Creating buttons to add new staff
        JButton addButton = new JButton("+ Add New");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addstaff addstaff = new addstaff();
                addstaff.setVisible(true);
            }
        });

        addButton.setBackground(Color.BLUE);
        addButton.setForeground(Color.WHITE);
        addButton.setFont(addButton.getFont().deriveFont(15f));

        topPanel.add(addButton);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        add(topPanel, BorderLayout.EAST);

        JPanel scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new GridLayout(0, 1));

        // Simulating staff keys for dynamic staff addition
        String[] staffKeys = {"1744800232923", "1744800302055"};

        // Loop through staff keys and display details
        for (String staffKey : staffKeys) {
            // Fetch staff name dynamically using the staffKey
            String staffName = getStaffName(staffKey);

            // Create the item panel
            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));

            // Add profile picture
            ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/Resources/images/Male.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(imageIcon);
            itemPanel.add(imageLabel);

            // Create button with staff name
            JButton button = new JButton(staffName);
            button.setBackground(Color.white);
            button.setPreferredSize(new Dimension(600, 50));
            itemPanel.add(button);

            // Add delete button
            ImageIcon deleteIcon = new ImageIcon("src/Resources/images/Delete.png");
            Image deleteImg = deleteIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            JButton deleteButton = new JButton(new ImageIcon(deleteImg));
            deleteButton.setOpaque(false);
            deleteButton.setContentAreaFilled(false);
            deleteButton.setBorderPainted(false);
            deleteButton.setPreferredSize(new Dimension(100, 50));
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if(option == JOptionPane.YES_NO_OPTION) {
                        JOptionPane.showMessageDialog(null,"Item Deleted");
                    }
                }
            });
            itemPanel.add(deleteButton);

            // Add edit button
            ImageIcon editIcon = new ImageIcon("src/Resources/images/Edit.png");
            Image editImg = editIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            JButton editButton = new JButton(new ImageIcon(editImg));
            editButton.setPreferredSize(new Dimension(100, 50));
            editButton.setOpaque(false);
            editButton.setContentAreaFilled(false);
            editButton.setBorderPainted(false);
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure to Edit?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if(option == JOptionPane.YES_NO_OPTION) {
                        update updetails = new update();
                        updetails.setVisible(true);
                    }
                }
            });
            itemPanel.add(editButton);

            // Add action listener for button to navigate to the member page
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    member member = new member();
                    member.setVisible(true);
                }
            });

            // Add item panel to the scrollable panel
            scrollablePanel.add(itemPanel);
        }

        // To view details adding scroll panel
        JScrollPane scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setPreferredSize(new Dimension(900, 600));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(scrollPane, gbc);

        add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(Box.createRigidArea(new Dimension(20, 70)));
    }

    // Method to simulate fetching staff name using the staffKey from local storage (Map)
    private String getStaffName(String staffKey) {
        return localStorage.getOrDefault("Staff_" + staffKey + "_Name", "Unknown Staff");
    }

    // Method to populate initial staff details in the "localStorage"
    private void populateInitialStaffDetails() {
        // Simulating storing staff details in "localStorage"
        localStorage.put("Staff_1744800232923_Name", "Ben Rodric Fernando");
        localStorage.put("Staff_1744800302055_Name", "Alex Rodric Fernando");
        localStorage.put("Staff_1744800232923_Position", "IT");
        localStorage.put("Staff_1744800302055_Position", "Maths");
        localStorage.put("Staff_1744800232923_Address", "112/2, Gamunu street, Dehiwala");
        localStorage.put("Staff_1744800302055_Address", "112/2, flower street, Dehiwala");
        localStorage.put("Staff_1744800232923_Contact Number", "0715986366");
        localStorage.put("Staff_1744800302055_Contact Number", "0715986350");
        localStorage.put("Staff_1744800232923_Email", "benfernando@hotmail.com");
        localStorage.put("Staff_1744800302055_Email", "alexfernando@hotmail.com");
        localStorage.put("Staff_1744800232923_Date of Birth", "05/06/1998");
        localStorage.put("Staff_1744800302055_Date of Birth", "05/08/1998");
        localStorage.put("Staff_1744800232923_Gender", "Male");
        localStorage.put("Staff_1744800302055_Gender", "Male");
        localStorage.put("Staff_1744800232923_Description", "Test");
        localStorage.put("Staff_1744800302055_Description", "Test");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            staffdetail frame = new staffdetail();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Staff Details");

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            int screenWidth = toolkit.getScreenSize().width;
            int screenHeight = toolkit.getScreenSize().height;
            frame.setSize(screenWidth, screenHeight);
            frame.setVisible(true);
        });
    }
}
