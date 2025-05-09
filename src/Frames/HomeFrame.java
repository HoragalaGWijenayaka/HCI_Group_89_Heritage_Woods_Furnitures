package Frames;

import Frames.FurniturePage.FunitureDetails;
import Frames.Staff.staffdetail;
import Frames.Suppliers.add;
import Frames.User.userdetails;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class HomeFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JPanel sidebar;

    public HomeFrame() {
        setTitle("Heritage Woods Furniture - Home");
        setSize(1077, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        sidebar = createSidebar();
        initializeContentPanels();

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "Home");
    }

    private void initializeContentPanels() {
        AppliancesFrame appliancesFrame = new AppliancesFrame();
        contentPanel.add(appliancesFrame.getContentPane(), "Home");

        RoomSpecificationUI roomSpecificationUI = new RoomSpecificationUI();
        contentPanel.add(roomSpecificationUI.getContentPane(), "RoomDesigner");

        staffdetail staff = new staffdetail();
        contentPanel.add(staff.getContentPane(), "Staff");

        FunitureDetails funitureDetails = new FunitureDetails();
        contentPanel.add(funitureDetails.getContentPane(), "Furnitures");

        HistoryFrame historyFrame = new HistoryFrame();
        contentPanel.add(historyFrame.getContentPane(), "History");

        add add = new add();
        contentPanel.add(add.getContentPane(), "Suppliers");

        userdetails userDetails = new userdetails();
        contentPanel.add(userDetails.getContentPane(), "Account");

        LoginFrame loginFrame = new LoginFrame();
        contentPanel.add(loginFrame.getContentPane(), "Logout");
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(new Color(14, 0, 81));
        sidebar.setPreferredSize(new Dimension(200, 650));

        // Top logo section
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(new Color(14, 0, 81));

        ImageIcon logoIcon = scaleImageIcon(new ImageIcon(getClass().getResource("/Resources/images/newLogo.png")), 50, 50);
        JLabel logoLabel = new JLabel(logoIcon, SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 10));

        JLabel nameLabel = new JLabel("<html>Heritage Woods<br/>Furniture</html>", SwingConstants.CENTER);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel logoAndNamePanel = new JPanel(new BorderLayout());
        logoAndNamePanel.setBackground(new Color(14, 0, 81));
        logoAndNamePanel.add(logoLabel, BorderLayout.WEST);
        logoAndNamePanel.add(nameLabel, BorderLayout.CENTER);

        logoPanel.add(logoAndNamePanel, BorderLayout.NORTH);
        // Increased top and bottom padding for logo section
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 40, 0));
        sidebar.add(logoPanel, BorderLayout.NORTH);

        // All buttons (navigation + account/logout)
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(new Color(14, 0, 81));

        // Add margin around the buttons panel
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0)); 

        String[] labels = {
                "Home", "RoomDesigner", "Furnitures", "History", "Staff", "Suppliers", "Account", "Logout"
        };
        String[] icons = {
                "/Resources/images/icon-home.png",
                "/Resources/images/icon-roomdesigner.png",
                "/Resources/images/icon-furniture.png",
                "/Resources/images/history.png",
                "/Resources/images/icon-staff.png",
                "/Resources/images/supplierIcon.png",
                "/Resources/images/icon-account.png",
                "/Resources/images/icon-logout.png"
        };

        for (int i = 0; i < labels.length; i++) {
            JButton button = createSidebarButton(labels[i], icons[i]);
            final String label = labels[i];

            if (label.equals("Logout")) {
                button.addActionListener(e -> {
                    dispose();
                    LoginFrame loginFrame = new LoginFrame();
                    loginFrame.setVisible(true);
                });
            } else {
                button.addActionListener(e -> cardLayout.show(contentPanel, label));
            }

            // Increased vertical spacing between buttons
            buttonsPanel.add(Box.createVerticalStrut(20)); // Changed from 10 to 20
            buttonsPanel.add(button);
        }

        sidebar.add(buttonsPanel, BorderLayout.CENTER);
        return sidebar;
    }

    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    private JButton createSidebarButton(String text, String iconPath) {
        final int ICON_SIZE = 24;
        ImageIcon icon = new ImageIcon(new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB));

        URL iconURL = getClass().getResource(iconPath);
        if (iconURL != null) {
            icon = new ImageIcon(new ImageIcon(iconURL).getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        }

        JButton button = new JButton(text, icon);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 50));
        button.setPreferredSize(new Dimension(200, 50));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(14, 0, 81));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(15);
        button.setBorder(BorderFactory.createEmptyBorder(90, 20, 90, 0)); // Added more top and bottom padding

        return button;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeFrame homeFrame = new HomeFrame();
            homeFrame.setVisible(true);
        });
    }
}
