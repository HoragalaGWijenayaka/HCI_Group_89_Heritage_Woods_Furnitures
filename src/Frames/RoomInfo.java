package Frames;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RoomInfo {
    private String roomNumber;
    private String designBy;
    private String date;
    private String imagePath;
    private int width;
    private int height;
    private List<Furniture2DItem> furnitureItems;

    // Constructor with width, height, and furniture items
    public RoomInfo(String roomNumber, String designBy, String date, String imagePath, int width, int height, List<Furniture2DItem> furnitureItems) {
        this.roomNumber = roomNumber;
        this.designBy = designBy;
        this.date = date;
        this.imagePath = imagePath;
        this.width = width;
        this.height = height;
        this.furnitureItems = furnitureItems != null ? furnitureItems : new ArrayList<>();
    }

    // Additional constructor without width, height, and furniture items
    public RoomInfo(String roomNumber, String designBy, String date, String imagePath) {
        this(roomNumber, designBy, date, imagePath, 0, 0, new ArrayList<>());
    }

    // Getters and Setters
    public String getRoomNumber() { return roomNumber; }
    public String getDesignBy() { return designBy; }
    public String getDate() { return date; }
    public String getImagePath() { return imagePath; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public List<Furniture2DItem> getFurnitureItems() { return furnitureItems; }

    // Method to add a furniture item
    public void addFurnitureItem(Furniture2DItem item) {
        if (item != null) {
            furnitureItems.add(item);
        }
    }
}

class Furniture2DItem {
    private String type;
    private Point position;

    public Furniture2DItem(String type, Point position) {
        this.type = type;
        this.position = position;
    }

    public String getType() { return type; }
    public Point getPosition() { return position; }
}
