package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomSpecificationUI extends JFrame {
    private JTextField roomNumberField;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField depthField;
    private JComboBox<String> shapeComboBox;
    private JButton floorColorButton;
    private JButton leftWallColorButton;
    private JButton frontWallColorButton;
    private JButton rightWallColorButton;
    private Color floorColor;
    private Color leftWallColor;
    private Color frontWallColor;
    private Color rightWallColor;

    public RoomSpecificationUI() {
        setTitle("Room Specification");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addRoomSpecificationPanel(mainPanel);
        addShapeSpecificationPanel(mainPanel);
        addColorSchemePanel(mainPanel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Next button with green color
        JButton nextButton = createStyledButton("Next", new Color(0, 153, 0), 60);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int width = Integer.parseInt(widthField.getText());
                    int height = Integer.parseInt(heightField.getText());
                    int depth = Integer.parseInt(depthField.getText());
                    int roomNumber = Integer.parseInt(roomNumberField.getText());
                    String shape = (String) shapeComboBox.getSelectedItem();

                    DesignRoom roomDesigner = new DesignRoom(width, height, depth, roomNumber, shape,
                            floorColor, leftWallColor, frontWallColor, rightWallColor);
                    roomDesigner.setVisible(true);
                    RoomSpecificationUI.this.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(RoomSpecificationUI.this,
                            "Please enter valid numeric dimensions.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Cancel button with red color
        JButton cancelButton = createStyledButton("Cancel", new Color(204, 0, 0), 60);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(nextButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel);
        add(mainPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private JButton createStyledButton(String text, Color bgColor, int height) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, height));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        return button;
    }

    private void addRoomSpecificationPanel(JPanel parent) {
        JPanel roomPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        roomPanel.setBorder(BorderFactory.createTitledBorder("Room Specifications"));

        roomPanel.add(new JLabel("Room Number:"));
        roomNumberField = new JTextField();
        roomPanel.add(roomNumberField);

        roomPanel.add(new JLabel("Width:"));
        widthField = new JTextField();
        roomPanel.add(widthField);

        roomPanel.add(new JLabel("Height:"));
        heightField = new JTextField();
        roomPanel.add(heightField);

        roomPanel.add(new JLabel("Depth:"));
        depthField = new JTextField();
        roomPanel.add(depthField);

        parent.add(roomPanel);
        parent.add(Box.createVerticalStrut(15));
    }

    private void addShapeSpecificationPanel(JPanel parent) {
        JPanel shapePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        shapePanel.setBorder(BorderFactory.createTitledBorder("Shape Specification"));

        shapePanel.add(new JLabel("Shape:"));
        String[] shapes = {"Square", "Rectangle"};
        shapeComboBox = new JComboBox<>(shapes);
        shapePanel.add(shapeComboBox);

        parent.add(shapePanel);
        parent.add(Box.createVerticalStrut(15));
    }

    private void addColorSchemePanel(JPanel parent) {
        JPanel colorPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        colorPanel.setBorder(BorderFactory.createTitledBorder("Color Scheme"));

        floorColorButton = createColorButton("Choose Floor Color");
        leftWallColorButton = createColorButton("Choose Left Wall Color");
        frontWallColorButton = createColorButton("Choose Front Wall Color");
        rightWallColorButton = createColorButton("Choose Right Wall Color");

        colorPanel.add(floorColorButton);
        colorPanel.add(leftWallColorButton);
        colorPanel.add(frontWallColorButton);
        colorPanel.add(rightWallColorButton);

        parent.add(colorPanel);
        parent.add(Box.createVerticalStrut(15));
    }

    private JButton createColorButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(new ColorActionListener());
        return button;
    }

    private class ColorActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color chosenColor = JColorChooser.showDialog(RoomSpecificationUI.this, "Choose a color", Color.WHITE);
            if (chosenColor != null) {
                if (e.getSource() == floorColorButton) {
                    floorColor = chosenColor;
                } else if (e.getSource() == leftWallColorButton) {
                    leftWallColor = chosenColor;
                } else if (e.getSource() == frontWallColorButton) {
                    frontWallColor = chosenColor;
                } else if (e.getSource() == rightWallColorButton) {
                    rightWallColor = chosenColor;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RoomSpecificationUI frame = new RoomSpecificationUI();
            frame.setVisible(true);
        });
    }
}