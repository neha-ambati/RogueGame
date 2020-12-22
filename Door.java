package rogue;

import java.util.ArrayList;

public class Door {
    private ArrayList<Room> connectedRooms = new ArrayList<>();
    private String wallDir;
    private int wallPos;

    /**
     * Default constructor.
     */
    public Door() {
       wallPos = 0;
    }

    /**
     * Directions of door.
     * @param dir the directions
     * @param pos position of wall
     */
    public Door(String dir, int pos) {
        wallDir = dir;
        wallPos = pos;
    }

    /**
     * getter for position of wall.
     * @return wallPos
     */
    public int getWallPosition() {
        return wallPos;
    }

    /**
     * getter for direction.
     * @return wallDir
     */
    public String getDirection() {
        return wallDir;
    }

    /**
     * Room connected to other Room.
     * @param attachRoom room needed for attatchment
     */
    public void connectRoom(Room attachRoom) {
        connectedRooms.add(attachRoom);
    }

    /**
     * Arraylist which both rooms connected.
     * @return connectedRooms
     */
    public ArrayList<Room> getConnectedRooms() {
        return connectedRooms;
    }

    /**
     * get other room.
     * @param currentRoom
     * @return room position for other room
     */
    public Room getOtherRoom(Room currentRoom) {
        if (connectedRooms.get(0) == currentRoom) {
            return connectedRooms.get(1);
        } else {
            return connectedRooms.get(0);
        }
    }
}
