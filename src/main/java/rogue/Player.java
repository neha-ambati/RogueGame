package rogue;

import java.awt.Point;

/**
 * A basic function to represent the player character.
 */
public class Player {
    private Point xyLocation;
    private Room currentRoom;
    private String name;

    /**
     * Default constructor.
     */
    public Player() {
        name = "neha";
    }

    /**
     * Set parameters for player.
     * @param pName name of player
     */
    public Player(String pName) {
        name = pName;
        xyLocation = new Point(1, 1);
        currentRoom = null;
    }

    /**
     * getter name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name.
     * @param pName is Players
     **/
    public void setName(String pName) {
        name = pName;
    }

    /**
     * Getter for xyLocation.
     * @return xyLocation
     */
    public Point getXyLocation() {
        return xyLocation;
    }

    /**
     * Setter for xyLocation.
     * @param newXyLocation location
     */
    public void setXyLocation(Point newXyLocation) {
        xyLocation = newXyLocation;
    }

    /**
     * getter for current room.
     * @return currentRoom
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

     /**
     * setter for current room.
     * @param newRoom players
     */
    public void setCurrentRoom(Room newRoom) {
        currentRoom = newRoom;
    }
}
