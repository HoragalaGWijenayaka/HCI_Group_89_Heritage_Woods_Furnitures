package Frames.FurniturePage;

import Components.Sidebar;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Furniture extends JFrame {
    private File selectedImageFile;
    private String enteredName;
    private JLabel imagePreviewLabel;
    private JComboBox<String> categoryField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField sizeField;
    private JTextField dateField;
    private JTextArea descField;

    public Furniture() {
        setTitle("Furniture");
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Add sidebar
        Sidebar sidebar = new Sidebar();
        add(sidebar, BorderLayout.WEST);

        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.lightGray);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(0xD9D9D9));
        JLabel titleLabel = new JLabel("Furniture");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0xD9D9D9));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Category
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Category"), gbc);

        gbc.gridx = 1;
        String[] categories = {"Chair", "Table", "Sofa", "Bed", "Desk"};
        categoryField = new JComboBox<>(categories);
        categoryField.setPreferredSize(new Dimension(300, 30));
        formPanel.add(categoryField, gbc);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Name"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(300, 30));
        formPanel.add(nameField, gbc);

        // Price
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Price"), gbc);

        gbc.gridx = 1;
        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(300, 30));
        formPanel.add(priceField, gbc);

        // Size
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Size"), gbc);

        gbc.gridx = 1;
        sizeField = new JTextField();
        sizeField.setPreferredSize(new Dimension(300, 30));
        formPanel.add(sizeField, gbc);

        // Date
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Date"), gbc);

        gbc.gridx = 1;
        dateField = new JTextField();
        dateField.setPreferredSize(new Dimension(300, 30));
        formPanel.add(dateField, gbc);

        // Description
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Description"), gbc);

        gbc.gridx = 1;
        descField = new JTextArea(3, 20);
        descField.setLineWrap(true);
        descField.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descField);
        scrollPane.setPreferredSize(new Dimension(300, 80));
        formPanel.add(scrollPane, gbc);

        // Image Upload
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Image"), gbc);

        gbc.gridx = 1;
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);

        JButton uploadBtn = new JButton("Upload Image");
        uploadBtn.setPreferredSize(new Dimension(150, 30));
        uploadBtn.setBackground(new Color(0, 120, 215));
        uploadBtn.setForeground(Color.WHITE);
        uploadBtn.setFocusPainted(false);

        imagePreviewLabel = new JLabel("No image selected", SwingConstants.CENTER);
        imagePreviewLabel.setPreferredSize(new Dimension(200, 200));
        imagePreviewLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        imagePanel.add(uploadBtn, BorderLayout.NORTH);
        imagePanel.add(imagePreviewLabel, BorderLayout.CENTER);
        formPanel.add(imagePanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(0xD9D9D9));

        JButton saveBtn = createActionButton("Save", new Color(40, 167, 69));
        JButton deleteBtn = createActionButton("Delete", new Color(220, 53, 69));

        buttonPanel.add(saveBtn);
        buttonPanel.add(deleteBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Upload event
        uploadBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = fileChooser.getSelectedFile();
                imagePreviewLabel.setText("");
                imagePreviewLabel.setIcon(new ImageIcon(new ImageIcon(selectedImageFile.getPath())
                        .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
            }
        });

        // Save event
        saveBtn.addActionListener(e -> {
            enteredName = nameField.getText();
            try {
                saveData();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        // Delete event
        deleteBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Delete functionality to be implemented");
        });

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void saveData() throws FileNotFoundException {
        Properties properties = new Properties();
        try {
            String filePath = "furniture.properties";
            File file = new File(filePath);

            if (file.exists()) {
                FileInputStream fileInput = new FileInputStream(file);
                properties.load(fileInput);
                fileInput.close();
            }

            properties.setProperty("Category", (String) categoryField.getSelectedItem());
            properties.setProperty("Name", nameField.getText());
            properties.setProperty("Price", priceField.getText());
            properties.setProperty("Size", sizeField.getText());
            properties.setProperty("Date", dateField.getText());
            properties.setProperty("Description", descField.getText());

            if (selectedImageFile != null) {
                String imagePath = saveImage(selectedImageFile);
                properties.setProperty("Image", imagePath);
            }

            FileOutputStream fileOut = new FileOutputStream(filePath);
            properties.store(fileOut, "Furniture Details");
            fileOut.close();

            JOptionPane.showMessageDialog(this, "Data saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }

    private String saveImage(File imageFile) throws IOException {
        String directoryPath = "Saved_Images";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = timeStamp + "_" + imageFile.getName();
        File destinationFile = new File(directoryPath + "/" + fileName);

        java.nio.file.Files.copy(imageFile.toPath(), destinationFile.toPath());

        return destinationFile.getAbsolutePath();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Furniture::new);
    }
}
