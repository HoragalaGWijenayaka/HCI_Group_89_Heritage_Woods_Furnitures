package Frames;

import Components.ButtonUtils;
import Frames.FurniturePage.FunitureDetails;
import Frames.FurniturePage.Furniture;
import Frames.Staff.staffdetail;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppliancesFrame extends JFrame {

    private Furniture furnitureFrame = null;
    public AppliancesFrame() {
        setTitle("Heritage Woods Furnitures - Appliances");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ✅ Fix: Declare and initialize headerPanel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Appliances");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));
        titleLabel.setBackground(new Color(233, 229, 229));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        // Button panel and addFurnitureButton
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addFurnitureButton = createButton("+Add Furniture", Color.BLUE, Color.WHITE);
        buttonPanel.add(addFurnitureButton);
        headerPanel.add(buttonPanel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);


        // Add listener ONCE outside the loop
        addFurnitureButton.addActionListener(e -> {
            if (furnitureFrame == null || !furnitureFrame.isDisplayable()) {
                furnitureFrame = new Furniture();
                furnitureFrame.setVisible(true);
            } else {
                furnitureFrame.toFront();
                furnitureFrame.requestFocus();
            }
        });

        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        gridPanel.setBackground(new Color(233, 229, 229));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] imagePaths = {
                "/Resources/images/chair2.png",
                "/Resources/images/redchair.png",
                "/Resources/images/chair.png",
                "/Resources/images/bed1.png",
                "/Resources/images/table.png",
                "/Resources/images/tv.png"
        };

        String[] labels = {"Chair 1", "Chair 2", "Chair 3", "Bed", "Table", "TV"};

        for (int i = 0; i < imagePaths.length; i++) {
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePaths[i]));
            JPanel panel = new JPanel(new BorderLayout());

            JButton imageButton = ButtonUtils.createImageButton(icon, null, e -> {
                FunitureDetails funitureDetails = new FunitureDetails();
                funitureDetails.setVisible(true);
            });

            JLabel textLabel = new JLabel(labels[i], SwingConstants.CENTER);
            panel.add(imageButton, BorderLayout.CENTER);
            panel.add(textLabel, BorderLayout.SOUTH);

            gridPanel.add(panel);
        }

        add(gridPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }


    private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppliancesFrame());
    }
}

class CustomBorderFactory {

    public static Border createRoundedBorder(int radius) {
        return new RoundedBorder(radius);
    }

    private static class RoundedBorder implements Border {

        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
}

