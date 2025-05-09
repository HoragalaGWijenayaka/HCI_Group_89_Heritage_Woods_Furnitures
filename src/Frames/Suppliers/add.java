package Frames.Suppliers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class add extends JFrame {

    public add() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.NORTH);

        JLabel supplierLabel = new JLabel("Supplier");
        supplierLabel.setHorizontalAlignment(SwingConstants.LEFT); // Center-align the text
        supplierLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Setting font and size
        supplierLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));
        mainPanel.add(supplierLabel);


        // Staff items
        JPanel scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new GridLayout(0, 1)); // Using GridLayout with one column

        JPanel topPanel = new JPanel();

        // Create the button and add action listener
        JButton addButton = new JButton("+ Add New");

       // Setting preferred size of Save button
        addButton.setBackground(Color.BLUE);
        addButton.setForeground(Color.WHITE);// Change the color to red
        addButton.setFont(addButton.getFont().deriveFont(15f));

        // Add the button to the top panel
        topPanel.add(addButton);

        // Add the top panel to the NORTH region of the frame
        add(topPanel, BorderLayout.EAST);




        // Staff items
        String[] staffItems = {"Samanala suppliers", "InflexTV suppliers", "Damaro chairs", "Sleep well beds"};
        for (String item : staffItems) {
            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));

            // Add icon next to the staff item
            ImageIcon imageicons = new ImageIcon(new ImageIcon("src/Resources/images/newuserimg.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(imageicons);
            itemPanel.add(imageLabel);

            JButton button = new JButton(item);
            button.setBackground(new Color(0xD9D9D9));
            button.setPreferredSize(new Dimension(650, 50));
            itemPanel.add(button);

            // Replace text buttons with icon buttons
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
                    if(option == JOptionPane.YES_NO_OPTION){
                        JOptionPane.showMessageDialog(null,"Item Deleted");
                    }
                }
            });
            itemPanel.add(deleteButton);

            ImageIcon editIcon = new ImageIcon("src/Resources/images/Edit.png");
//            ImageIcon editIcon = new ImageIcon(getClass().getClassLoader().getResource("/Resources/images/Edit.png"));
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
                    if(option == JOptionPane.YES_NO_OPTION){
                        update updetails = new update();
                        updetails.setVisible(true);
                    }

                }
            });
            itemPanel.add(editButton);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Navigate to detail page
                    detail detail = new detail();
                    detail.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    detail.setVisible(true);
                }
            });


            scrollablePanel.add(itemPanel);
        }

        // Add the scrollable panel to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setPreferredSize(new Dimension(900, 600)); // Set preferred size of scrollable panel
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Center the scrollable panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(scrollPane, gbc);



        add(centerPanel, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                furnitureinfo furnitureinfo = new furnitureinfo();
                furnitureinfo.setVisible(true);
            }
        });


        // Add the "+ Add New" button

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            add frame = new add();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Welcome");

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            int screenWidth = toolkit.getScreenSize().width;
            int screenHeight = toolkit.getScreenSize().height;
            frame.setSize(screenWidth, screenHeight);
            frame.setVisible(true);
        });
    }
}