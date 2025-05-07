package Frames.User;

import Components.Sidebar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class userdetails extends JFrame {
    private JLabel nameValue, phoneValue, positionValue, addressValue, dobValue, descValue;

    public userdetails() {
        super("User Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());



        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel, BorderLayout.CENTER);

        // Header: title and profile picture
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        JLabel title = new JLabel("User Details");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 20));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(title);
        header.add(titlePanel, BorderLayout.WEST);

        ImageIcon picIcon = new ImageIcon("src/Resources/images/newuserimg.png");
        Image picImg = picIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel picLabel = new JLabel(new ImageIcon(picImg));
        JPanel picPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 20, 20));
        picPanel.setBackground(Color.WHITE);
        picPanel.add(picLabel);
        header.add(picPanel, BorderLayout.EAST);

        mainPanel.add(header, BorderLayout.NORTH);

        // Details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;

        nameValue = addDetail(detailsPanel, gbc, 0, "Name:", "Gambheera Lakmewan");
        phoneValue = addDetail(detailsPanel, gbc, 1, "Phone Number:", "0711920304");
        positionValue = addDetail(detailsPanel, gbc, 2, "Position:", "Designer Level 1");
        addressValue = addDetail(detailsPanel, gbc, 3, "Address:", "Dhabulla road, Kurunegala");
        dobValue = addDetail(detailsPanel, gbc, 4, "Date of Birth:", "05-03-1999");
        descValue = addDetail(detailsPanel, gbc, 5, "Description:", "Level 1 designer and admin of this system");

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Action panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        actionPanel.setBackground(Color.WHITE);
        JButton editBtn = makeIconButton("src/Resources/images/Edit.png", 40, 40);
        editBtn.setToolTipText("Edit");
        editBtn.addActionListener((ActionEvent e) -> {
            int option = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to edit?", "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                new updatedetails().setVisible(true);
                dispose();
            }
        });
        actionPanel.add(editBtn);

        mainPanel.add(actionPanel, BorderLayout.SOUTH);
    }

    private JLabel addDetail(JPanel panel, GridBagConstraints gbc, int row, String labelText, String valueText) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        JLabel value = new JLabel(valueText);
        panel.add(value, gbc);
        return value;
    }

    private JButton makeIconButton(String path, int w, int h) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        JButton btn = new JButton(new ImageIcon(img));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(w + 20, h + 20));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new userdetails().setVisible(true));
    }
}
