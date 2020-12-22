package rogue;
import java.awt.Point;

/**
 * A basic Item class; basic functionality for both consumables and equipment.
 */
public class Item {
    private int itemId;
    private String itemName;
    private String itemType;
    private Point itemXYlocation;

    /**
     * Default item constructor.
     */
    public Item() {
        itemXYlocation = new Point();
        itemId = 0;
        itemName = "";
        itemType = "";
    }

    /**
     * sets parameters.
     * @param id of the item
     * @param name of the item
     * @param type of the item
     * @param xyLocation coordinates to represent item's location
     */
    public Item(int id, String name, String type, Point xyLocation) {
        itemId = id;
        itemName = name;
        itemType = type;
        itemXYlocation = xyLocation;
    }

    /**
     * Getter for id.
     * @return itemId
     */
    public int getId() {
        return itemId;
    }

    /**
     * Setter for id.
     * @param id of item
     */
    public void setId(int id) {
        itemId = id;
    }

    /**
     * Getter for name of item.
     * @return itemName
     */
    public String getName() {
        return itemName;
    }

    /**
     * Setter for name of item.
     * @param name of item
     */
    public void setName(String name) {
        itemName = name;
    }


    /**
     * Getter for item type.
     * @return itemType
     */
    public String getType() {
        return itemType;
    }

    /**
     * Setter for type of item.
     * @param type of item
     */
    public void setType(String type) {
        itemType = type;
    }

    /**
     * Getter for xyLocation.
     * @return itemXYlocation
     */
    public Point getXyLocation() {
        return itemXYlocation;
    }

    /**
     * Setter for xyLocation.
     * @param newXYlocation - represents the item's new xy coordinate location
     */
    public void setXyLocation(Point newXYlocation) {
        itemXYlocation = newXYlocation;
    }
}
