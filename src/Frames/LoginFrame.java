package Frames;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;

    public LoginFrame() {
        setTitle("Heritage Wood Furniture");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 90)); // Margins between components

        // Left panel for the form
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));

        // Header at the top
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Adding the image icon
        // Create a panel with vertical layout
        JPanel imageTextPanel = new JPanel();
        imageTextPanel.setLayout(new BoxLayout(imageTextPanel, BoxLayout.Y_AXIS));
        imageTextPanel.setOpaque(false); // Optional: makes background transparent if needed

// Adding the image icon
        ImageIcon imageIcon = new ImageIcon("src/Resources/images/newLogo.png");
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally

// Adding the welcome text
        JLabel welcomeLabel = new JLabel("Heritage Woods Furniture ");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally

// Add image and text to the vertical panel
        imageTextPanel.add(imageLabel);
        imageTextPanel.add(Box.createVerticalStrut(10)); // Add spacing
        imageTextPanel.add(welcomeLabel);

// Add the vertical panel to your header panel
        headerPanel.add(imageTextPanel);


        leftPanel.add(headerPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));


        // Add some spacing
        leftPanel.add(Box.createRigidArea(new Dimension(0, 60)));

        usernameField = new JTextField(20); // Preferred size
        usernameField.setFont(new Font("Arial", Font.PLAIN,30));
        leftPanel.add(createLabeledField("Username", usernameField));

        // Add some spacing
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        passwordField = new JPasswordField(20); // Preferred size
        passwordField.setFont(new Font("Arial", Font.PLAIN,30));
        leftPanel.add(createLabeledField("Password", passwordField));

        // Add some spacing
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(51, 0, 255)); // Hex #3300FF
        loginButton.setForeground(Color.WHITE); // Optional: white text for contrast
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        Dimension buttonSize = new Dimension(600, 40);
        loginButton.setPreferredSize(buttonSize);
        leftPanel.add(loginButton);


        // Centering the form in the left half of the BorderLayout
        JPanel leftContainer = new JPanel(new GridBagLayout());
        leftContainer.add(leftPanel);
        add(leftContainer, BorderLayout.CENTER);

        // Image label on the right
        JLabel imageLabelRight = new JLabel();
        ImageIcon imageIconRight = new ImageIcon(new ImageIcon("src/Resources/images/newloginimage.jpg").getImage().getScaledInstance(600, 750, Image.SCALE_DEFAULT));
        imageLabelRight.setIcon(imageIconRight);
        imageLabelRight.setPreferredSize(new Dimension(650, 400));
        add(imageLabelRight, BorderLayout.EAST);

        loginButton.addActionListener(e -> onLogin());

        pack(); // Pack the frame to the preferred sizes of its components
        setLocationRelativeTo(null); // Center on screen
    }

    private JPanel createLabeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setFont(label.getFont().deriveFont(18.0f));
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    private void onLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Your authentication logic here
        if ("admin".equals(username) && "admin".equals(password)) {
            dispose();
            new HomeFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
