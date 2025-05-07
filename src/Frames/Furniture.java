package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Furniture extends JFrame {

    private File selectedImageFile;
    private String enteredName;

    private static JLabel createIconLabel(ImageIcon icon) {
        JLabel label = new JLabel(icon);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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

    public Furniture() {

        JPanel buttonpanel = new JPanel(new BorderLayout());
        buttonpanel.setPreferredSize(new Dimension(9000, 1000));
        buttonpanel.setBackground(Color.WHITE);

        JPanel hiPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        hiPanel.setBackground(Color.WHITE);
        JLabel upLabel = new JLabel("Appliances");
        upLabel.setFont(upLabel.getFont().deriveFont(Font.BOLD, 24f));
        hiPanel.add(upLabel);

        buttonpanel.add(hiPanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 70)));

        ImageIcon editIcon = new ImageIcon("src/Resources/images/adding1.png");
        Image editImg = editIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JButton addbtn = new JButton(new ImageIcon(editImg));
        addbtn.setPreferredSize(new Dimension(700, 200));
        addbtn.setOpaque(false);
        addbtn.setContentAreaFilled(false);
        addbtn.setBorderPainted(false);
        buttonsPanel.add(addbtn);

        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 90)));

        JButton savebtn = new JButton("Save");
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
        detailsPanel.add(new JLabel("Category"), gbc);

        gbc.gridx = 1;
        String[] categories = {"Chair", "Table", "Sofa", "Bed", "Desk"};
        categoryField = new JComboBox<>(categories);
        categoryField.setPreferredSize(new Dimension(100, 30));
        categoryField.setBackground(Color.WHITE);
        detailsPanel.add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        detailsPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(50);
        nameField.setPreferredSize(new Dimension(100, 30));
        detailsPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        detailsPanel.add(new JLabel("Price"), gbc);

        gbc.gridx = 1;
        priceField = new JTextField(50);
        priceField.setPreferredSize(new Dimension(100, 30));
        detailsPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        detailsPanel.add(new JLabel("Size"), gbc);

        gbc.gridx = 1;
        sizeField = new JTextField(50);
        sizeField.setPreferredSize(new Dimension(100, 30));
        detailsPanel.add(sizeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        detailsPanel.add(new JLabel("Date"), gbc);

        gbc.gridx = 1;
        dateField = new JTextField(50);
        dateField.setPreferredSize(new Dimension(300, 30));
        detailsPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        detailsPanel.add(new JLabel("Description"), gbc);

        gbc.gridx = 1;
        descField = new JTextField(50);
        descField.setPreferredSize(new Dimension(300, 30));
        detailsPanel.add(descField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        detailsPanel.add(new JLabel("Upload Image"), gbc);

        gbc.gridx = 1;
        uploadBtn = createButton("Upload Image", 300, 30);
        uploadBtn.setBackground(new Color(4, 29, 255));
        uploadBtn.setForeground(Color.WHITE);
        detailsPanel.add(uploadBtn, gbc);

        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        gbc.gridy++;
        detailsPanel.add(new JLabel(), gbc);

        detailsPanel.setPreferredSize(new Dimension(800, 100));


        uploadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedImageFile = fileChooser.getSelectedFile();
                    fileLabel.setText("Uploaded Image: " + selectedImageFile.getName());
                }
            }
        });

        savebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        enteredName = nameField.getText();
                        saveData();
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                });
            }
        });
    }

    private void saveData() throws FileNotFoundException {
        Properties properties = new Properties();
        try {
            String filePath = "C:/Users/Manul Perera/IdeaProjects/Phoenix_Furnitures/Saved_Items/furniture.properties";
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

            String imagePath = saveImage(selectedImageFile);
            properties.setProperty("Image", imagePath);

            FileOutputStream fileOut = new FileOutputStream(filePath);
            properties.store(fileOut, "Furniture Details");
            fileOut.close();

            JOptionPane.showMessageDialog(null, "Data saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
        }
    }

    private String saveImage(File imageFile) throws IOException {
        String directoryPath = "C:/Users/Manul Perera/IdeaProjects/Phoenix_Furnitures/Saved_Items/furniture.properties";
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

    public File getSelectedImageFile() {
        return selectedImageFile;
    }

    public String getEnteredName() {
        return enteredName;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Furniture furniture = new Furniture();
            furniture.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            furniture.setTitle("Update");

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            int screenWidth = toolkit.getScreenSize().width;
            int screenHeight = toolkit.getScreenSize().height;
            furniture.setSize(screenWidth, screenHeight);
            furniture.setVisible(true);
        });
    }
}
