package Modals; // Correct package name

import javax.swing.*;
import java.awt.*;

public class Furniture2DItem {
    private String type;
    private Point position;
    private JLabel label;

    // Constructor that takes type, position, and the associated JLabel as parameters
    // Existing constructor
    public Furniture2DItem(String type, Point position, JLabel label) {
        this.type = type;
        this.position = position;
        this.label = label;
    }

    // Overloaded constructor
    public Furniture2DItem(String type, Point position) {
        this.type = type;
        this.position = position;
        // Since no label is provided, set it to null or some default
        this.label = null;
    }



    // Method to update the position of a furniture item
    public void setPosition(Point position) {
        this.position = position;
        if (this.label != null) {
            this.label.setLocation(position);
        }
    }

    // Method to get the JLabel associated with the furniture item
    public JLabel getLabel() {
        return label;
    }

    // Getters and setters for type and position
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Point getPosition() {
        return position;
    }
}
