package Frames.Staff;

import Components.Sidebar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class member extends JFrame {
    private Map<String, Staff> staffStorage = new HashMap<>();

    public member() {
        super("Staff Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.lightGray);

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
        JLabel titleLabel = new JLabel("Staff");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Profile and Details panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        contentPanel.setBackground(Color.WHITE);

        // Profile picture panel
        JPanel profilePanel = new JPanel();
        profilePanel.setPreferredSize(new Dimension(250, 300));
        profilePanel.setBackground(Color.WHITE);

        ImageIcon pp = new ImageIcon("src/Resources/images/Male.png");
        Image scaledImage = pp.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel profileLabel = new JLabel(new ImageIcon(scaledImage));
        profilePanel.add(profileLabel);

        // Details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Staff details fields
        String[][] fields = {
                {"Name:", "John"},
                {"Email Address:", "John@hello.com"},
                {"Contact Number:", ""},
                {"Position:", "Manager"},
                {"Date of Birth:", ""},
                {"Gender:", ""},
                {"Status:", ""},
                {"Additional information:", ""}
        };

        for(int i = 0; i < fields.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            detailsPanel.add(new JLabel(fields[i][0]), gbc);

            gbc.gridx = 1;
            JTextField tf = new JTextField(fields[i][1], 20);
            tf.setEditable(false);
            tf.setBorder(BorderFactory.createEmptyBorder());
            tf.setBackground(Color.WHITE);
            detailsPanel.add(tf, gbc);
        }

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

       ;

        contentPanel.add(profilePanel, BorderLayout.WEST);
        contentPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Sample data
        staffStorage.put("1", new Staff(
                "John", "john@hello.com", "0717542873", "Manager",
                "05-03-1999", "Male", "Active", "Level 2 designer"
        ));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 35));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            if(text.equals("Delete")) {
                int option = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Staff member deleted");
                }
            }
        });

        return button;
    }

    class Staff {
        private String name, email, contact, position, dob, gender, status, additionalInfo;

        public Staff(String name, String email, String contact, String position,
                     String dob, String gender, String status, String additionalInfo) {
            this.name = name;
            this.email = email;
            this.contact = contact;
            this.position = position;
            this.dob = dob;
            this.gender = gender;
            this.status = status;
            this.additionalInfo = additionalInfo;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new member());
    }
}