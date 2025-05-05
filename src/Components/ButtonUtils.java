package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonUtils {
    public static JButton createImageButton(ImageIcon icon, String text, ActionListener listener) {
        JPanel panel = new JPanel(new BorderLayout());
        JButton button = new JButton(icon);
        button.addActionListener(listener);
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        panel.add(button, BorderLayout.CENTER);
        panel.add(label, BorderLayout.SOUTH);  // Displaying the image name below the image
        return button;
    }
}
