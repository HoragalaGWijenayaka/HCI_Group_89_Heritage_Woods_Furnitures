package Frames;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

public class HistoryFrame extends JFrame {
    private JTable historyTable;
    private DefaultTableModel tableModel;

    public HistoryFrame() {
        super("Room History");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());



        // Toolbar with title and buttons
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Room History");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        toolBar.add(titleLabel);
        toolBar.addSeparator(new Dimension(20, 0));

        JButton viewBtn = new JButton("View Design");
        viewBtn.setBackground(Color.LIGHT_GRAY);
        viewBtn.setForeground(Color.WHITE);
        viewBtn.setFont(viewBtn.getFont().deriveFont(15f));
        viewBtn.addActionListener(this::onViewDesign);
        toolBar.add(viewBtn);

        toolBar.add(Box.createHorizontalGlue());

        JButton editBtn = makeIconButton("/Resources/images/Edit.png", "Edit");
        editBtn.addActionListener(e -> onEditSelected());
        toolBar.add(editBtn);

        JButton deleteBtn = makeIconButton("/Resources/images/Delete.png", "Delete");
        deleteBtn.addActionListener(e -> onDeleteSelected());
        toolBar.add(deleteBtn);

        add(toolBar, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Preview", "Room #", "Designer", "Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        historyTable = new JTable(tableModel);
        historyTable.setRowHeight(60);
        historyTable.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // Load data
        loadSavedRooms();

        // Double-click row to view design
        historyTable.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) onViewDesign(null);
            }
        });
    }

    private void loadSavedRooms() {
        // TODO: replace with real parsing logic
        for (int i = 1; i <= 5; i++) {
            ImageIcon icon = loadIcon("/Resources/images/room" + i + ".png", 50, 50);
            Vector<Object> row = new Vector<>();
            row.add(icon);
            row.add("Room " + i);
            row.add("Admin");
            row.add("01/01/2024");
            tableModel.addRow(row);
        }
    }

    private ImageIcon loadIcon(String resourcePath, int w, int h) {
        try {
            URL resUrl = getClass().getResource(resourcePath);
            if (resUrl != null) {
                Image img = ImageIO.read(resUrl).getScaledInstance(w, h, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (IOException ignored) {}
        // fallback to empty icon if resource missing
        return new ImageIcon(new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB));
    }

    private JButton makeIconButton(String resourcePath, String tooltip) {
        JButton btn;
        URL resUrl = getClass().getResource(resourcePath);
        if (resUrl != null) {
            ImageIcon icon = new ImageIcon(resUrl);
            Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            btn = new JButton(new ImageIcon(img));
        } else {
            btn = new JButton(tooltip);
        }
        btn.setToolTipText(tooltip);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(60, 60));
        return btn;
    }

    private void onViewDesign(ActionEvent e) {
        int row = historyTable.getSelectedRow();
        if (row >= 0) {
            new EditRoomDesigner(null).setVisible(true);
        }
    }

    private void onEditSelected() {
        int row = historyTable.getSelectedRow();
        if (row < 0) JOptionPane.showMessageDialog(this, "Select a room first.");
        else new EditRoomDesigner(null).setVisible(true);
    }

    private void onDeleteSelected() {
        int row = historyTable.getSelectedRow();
        if (row < 0) JOptionPane.showMessageDialog(this, "Select a room first.");
        else if (JOptionPane.showConfirmDialog(this,
                "Delete selected room?", "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
        }
    }

    // Renders the preview image
    private static class ImageRenderer extends JLabel implements TableCellRenderer {
        public ImageRenderer() { setOpaque(true); }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setIcon((ImageIcon) value);
            setHorizontalAlignment(CENTER);
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HistoryFrame().setVisible(true));
    }
}