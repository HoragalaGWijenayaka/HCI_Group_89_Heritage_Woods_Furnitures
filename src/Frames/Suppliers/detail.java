package Frames.Suppliers;

import Components.Sidebar;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class detail extends JFrame {
    private JLabel codeValue, nameValue, contactValue, catalogValue, categoryValue, termsValue, listValue;

    public detail() {
        super("Supplier Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        add(new Sidebar(), BorderLayout.WEST);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel, BorderLayout.CENTER);

        // Header with title and profile image
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        JLabel title = new JLabel("Supplier Details");
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
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        codeValue = addDetail(detailsPanel, gbc, 0, "Supply Code:");
        nameValue = addDetail(detailsPanel, gbc, 1, "Supplier Name:");
        contactValue = addDetail(detailsPanel, gbc, 2, "Contact Number:");
        catalogValue = addDetail(detailsPanel, gbc, 3, "Product Catalog:");
        categoryValue = addDetail(detailsPanel, gbc, 4, "Item Category:");
        termsValue = addDetail(detailsPanel, gbc, 5, "Payment Terms:");
        listValue = addDetail(detailsPanel, gbc, 6, "Item List:");

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        actionPanel.setBackground(Color.WHITE);
        JButton editBtn = makeIconButton("src/Resources/images/Edit.png", "Edit");
        JButton deleteBtn = makeIconButton("src/Resources/images/Delete.png", "Delete");
        actionPanel.add(editBtn);
        actionPanel.add(deleteBtn);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        // Listeners
        editBtn.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to edit?", "Confirm",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                new detail().setVisible(true);
                dispose();
            }
        });
        deleteBtn.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete?", "Confirm",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Supplier record deleted.");
                dispose();
            }
        });

        // Load data from properties
        loadData();
    }

    private JLabel addDetail(JPanel panel, GridBagConstraints gbc, int row, String label) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        JLabel value = new JLabel();
        panel.add(value, gbc);
        return value;
    }

    private JButton makeIconButton(String path, String tooltip) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton btn = new JButton(new ImageIcon(img));
        btn.setToolTipText(tooltip);
        btn.setPreferredSize(new Dimension(60, 60));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        return btn;
    }

    private void loadData() {
        Properties props = new Properties();
        File file = new File("Saved_Items/suppliers.properties");
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                props.load(fis);
                codeValue.setText(props.getProperty("Supply Code", ""));
                nameValue.setText(props.getProperty("Supplier Name", ""));
                contactValue.setText(props.getProperty("Contact Number", ""));
                catalogValue.setText(props.getProperty("Product Catalog", ""));
                categoryValue.setText(props.getProperty("Item Catalog", ""));
                termsValue.setText(props.getProperty("Payment Terms", ""));
                listValue.setText(props.getProperty("Item List", ""));
            } catch (IOException ignored) {}
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new detail().setVisible(true));
    }
}
