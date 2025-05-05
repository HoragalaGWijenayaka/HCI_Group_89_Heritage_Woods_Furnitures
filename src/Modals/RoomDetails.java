package Modals;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomDetails {
    public int roomNumber;
    public int width, height, depth;
    public String shape;
    public Color floorColor, leftWallColor, frontWallColor, rightWallColor;
    public List<Furniture2DItem> furnitureItems;

    public static RoomDetails readRoomDetails() {
        RoomDetails roomDetails = new RoomDetails();
        roomDetails.furnitureItems = new ArrayList<>();
        File file = new File("room_info.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Room Number:")) {
                    roomDetails.roomNumber = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Room Size (WxHxD):")) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String[] size = parts[1].trim().split("x");
                        if (size.length == 3) {
                            roomDetails.width = Integer.parseInt(size[0].trim());
                            roomDetails.height = Integer.parseInt(size[1].trim());
                            roomDetails.depth = Integer.parseInt(size[2].trim());
                        } else {
                            System.err.println("Invalid format for room size: " + line);
                        }
                    } else {
                        System.err.println("Invalid format for room size: " + line);
                    }
                } else if (line.startsWith("Room Shape:")) {
                    roomDetails.shape = line.split(":")[1].trim();
                } else if (line.startsWith("Floor Color:")) {
                    roomDetails.floorColor = parseColor(line.split(":")[1].trim());
                } else if (line.startsWith("Left Wall Color:")) {
                    roomDetails.leftWallColor = parseColor(line.split(":")[1].trim());
                } else if (line.startsWith("Front Wall Color:")) {
                    roomDetails.frontWallColor = parseColor(line.split(":")[1].trim());
                } else if (line.startsWith("Right Wall Color:")) {
                    roomDetails.rightWallColor = parseColor(line.split(":")[1].trim());
                } else if (line.startsWith("Furniture type:")) {
                    parseFurniture(line, roomDetails);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in room details.");
        }

        return roomDetails;
    }

    private static Color parseColor(String colorStr) {
        String[] colorValues = colorStr.split(",");
        if (colorValues.length != 3) {
            throw new IllegalArgumentException("Invalid color format: " + colorStr);
        }
        int r = Integer.parseInt(colorValues[0].trim());
        int g = Integer.parseInt(colorValues[1].trim());
        int b = Integer.parseInt(colorValues[2].trim());
        return new Color(r, g, b);
    }

    private static void parseFurniture(String line, RoomDetails roomDetails) {
        Pattern pattern = Pattern.compile("Furniture type: ([^,]+), Position: java.awt.Point\\[x=(\\d+),y=(\\d+)\\]");
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            String type = matcher.group(1).trim();
            int x = Integer.parseInt(matcher.group(2).trim());
            int y = Integer.parseInt(matcher.group(3).trim());
            roomDetails.furnitureItems.add(new Furniture2DItem(type, new Point(x, y)));
        } else {
            throw new IllegalArgumentException("Invalid format for furniture: " + line);
        }
    }

}
