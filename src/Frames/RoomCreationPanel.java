package Frames;//package Frames;
//
//
//import Modals.ApplianceSelectionDialog;
//import Objects.Chair3D;
//import Objects.Rooms.Room3D;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//
//public class RoomCreationPanel extends JPanel {
//    private JButton createButton, resetButton;
//    private JPanel headerPanel, contentPanel;
//    private Room3D room3D;
//    private JTextField widthField, heightField, depthField;
//    private boolean[] selectedAppliances;
//
//    public RoomCreationPanel() {
//        room3D = new Room3D();
//        headerPanel = createHeaderPanel();
//        contentPanel = createContentPanel();
//
//        setLayout(new BorderLayout());
//        add(headerPanel, BorderLayout.NORTH);
//        add(contentPanel, BorderLayout.CENTER);
//
//    }
//
//    private void updateRoom() {
//        try {
//            float width = Float.parseFloat(widthField.getText());
//            float height = Float.parseFloat(heightField.getText());
//            float depth = Float.parseFloat(depthField.getText());
//            room3D.updateRoom(width, height, depth);
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Please enter valid numbers for width, height, and depth.",
//                    "Input Error", JOptionPane.ERROR_MESSAGE);
//        }
//        if (selectedAppliances != null) {
//            // Check if a chair was selected
//            if (selectedAppliances[0]) { // Assuming the chair is the first item
//                Chair3D chair = new Chair3D();
//                room3D.addFurniture(chair.getChairGroup());
//            }
//            // Add other appliances similarly if selected
//        }
//    }
//
//    private JPanel createHeaderPanel() {
//        // ... existing headerPanel setup
//
//        createButton = new JButton("Create");
//        createButton.setBackground(Color.ORANGE);
//        createButton.addActionListener(e -> updateRoom());
//
//        resetButton = new JButton("Reset");
//        resetButton.setBackground(Color.GREEN);
//        resetButton.addActionListener(e -> resetFields());
//
//        // Add buttons to buttonPanel
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        buttonPanel.add(createButton);
//        buttonPanel.add(resetButton);
//
//        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        inputPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
//        // Create fields
//        widthField = new JTextField(5);
//        heightField = new JTextField(5);
//        depthField = new JTextField(5);
//        // Add components to inputPanel
//        inputPanel.add(new JLabel("Room Number:"));
//        inputPanel.add(new JTextField(10)); // Just as a placeholder, no variable attached
//        inputPanel.add(new JLabel("Width:"));
//        inputPanel.add(widthField);
//        inputPanel.add(new JLabel("Height:"));
//        inputPanel.add(heightField);
//        inputPanel.add(new JLabel("Depth:"));
//        inputPanel.add(depthField);
//
//        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
//        headerPanel.add(inputPanel);
//        headerPanel.add(buttonPanel);
//
//        return headerPanel;
//    }
//
//    private void resetFields() {
//        widthField.setText("");
//        heightField.setText("");
//        depthField.setText("");
//        room3D.resetScene();
//    }
//    private JPanel createContentPanel() {
//        JPanel contentPanel = new JPanel(new BorderLayout());
//
//        // Panel to hold appliance selection and actions
//        JPanel applianceSelectionPanel = new JPanel(new GridLayout(3, 1)); // Three rows for selection, save, and delete
//
//        applianceSelectionPanel.add(createApplianceSelectionArea());
//        applianceSelectionPanel.add(createActionButton("Save", Color.BLUE));
//        applianceSelectionPanel.add(createActionButton("Delete", Color.RED));
//
//        // Placeholder for the 3D view area
//        JPanel view3DPanel = new JPanel(new BorderLayout());
//        view3DPanel.add(room3D.getCanvas(), BorderLayout.CENTER); // Add the Room3D canvas to the panel
//        view3DPanel.setBackground(Color.BLACK);
//        view3DPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Margin for the 3D canvas
//
//        int margin = 50; // Adjust the margin size as needed
//        view3DPanel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
//
//        contentPanel.add(applianceSelectionPanel, BorderLayout.WEST);
//        contentPanel.add(view3DPanel, BorderLayout.CENTER); // This will take up the rest of the space
//
//        return contentPanel;
//    }
//
//    // Helper method to create the appliance selection area
//    // Helper method to create the appliance selection area
//    private JPanel createApplianceSelectionArea() {
//        JPanel applianceSelectionArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
//
//        // Load and scale the image
//        ImageIcon icon = new ImageIcon("src/main/resources/images/applicances.png");
//        Image image = icon.getImage();
//        int iconWidth = 250; // Adjust the width as needed
//        int iconHeight = 200; // Adjust the height as needed
//        Image scaledImage = image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
//        ImageIcon scaledIcon = new ImageIcon(scaledImage);
//
//        // Create the button and set properties
//        JButton imageButton = new JButton(scaledIcon);
//        imageButton.setBorderPainted(false);
//        imageButton.setContentAreaFilled(false);
//        imageButton.setFocusPainted(false);
//        imageButton.setOpaque(false);
//
//        // Add the action listener to the button
//        imageButton.addActionListener(e -> {
//            // Assuming this is the listener for opening the appliance dialog
//            ApplianceSelectionDialog dialog = new ApplianceSelectionDialog((JFrame) SwingUtilities.getWindowAncestor(this));
//            selectedAppliances = dialog.showDialog(); // Save the selected items to be used later
//        });
//
//        // Add the button to the panel
//        applianceSelectionArea.add(imageButton);
//
//        return applianceSelectionArea;
//    }
//
//    // Helper method to create action buttons like 'Save' and 'Delete'
//    private JButton createActionButton(String text, Color color) {
//        JButton button = new JButton(text);
//        button.setBackground(color);
//
//        // Set a fixed size for the buttons
//        button.setPreferredSize(new Dimension(100, 20)); // Adjust width and height as needed
//
//        // Center align the text within the button
//        button.setHorizontalAlignment(SwingConstants.CENTER);
//        button.setVerticalAlignment(SwingConstants.CENTER);
//
//        return button;
//    }
//
//    // Main method to run a simple test
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Room Creation");
//        frame.setSize(1077, 768);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new RoomCreationPanel());
//        frame.pack(); // Fit the frame to the content
//        frame.setLocationRelativeTo(null); // Center on screen
//        frame.setVisible(true);
//    }
//}