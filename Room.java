package rogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.Point;

public class Room {
    private ArrayList<Item> roomItems = new ArrayList<>();;
    private Map<String, Character> symbolMap = new HashMap<>();
    private ArrayList<Door> list = new ArrayList<>();

    private int id;
    private boolean beginningRoom = false;
    private int height;
    private int width;
    private Map<String, Door> doors = new HashMap<>();
    private Player player = null;
    private String add;

    /**
     * Default constructor.
     */
    public Room() {
        id = 0;
    }

    /**
     * getter for beginning room.
     * @return beginning room
     */
    public boolean getBeginningRoom() {
        return beginningRoom;
    }

    /**
     * getter for width.
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * setter for width.
     * @param newWidth
     */
    public void setWidth(int newWidth) {
        width = newWidth;
    }

    /**
     * setter for starting in room.
     * @param startingValue
     */
    public void setIsStartingRoom(boolean startingValue) {
        beginningRoom = startingValue;
    }

    /**
     * getter for height.
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * setter for height.
     * @param newHeight
     */
    public void setHeight(int newHeight) {
        height = newHeight;
    }

    /**
     * getter for id.
     * @return is
     */
    public int getId() {
        return id;
    }

    /**
     * setter for id.
     * @param newId
     */
    public void setId(int newId) {
        id = newId;
    }

    /**
     * getter for room items.
     * @return room items.
     */
    public ArrayList<Item> getRoomItems() {
        return roomItems;
    }

    /**
     * setter room items.
     * @param newRoomItems
     */
    public void setRoomItems(ArrayList<Item> newRoomItems) {
        roomItems = newRoomItems;
    }

    /**
     * setter for player.
     * @param newSymbols
     */
    public void setSymbols(Map<String, Character> newSymbols) {
        symbolMap = newSymbols;
    }

    /**
     * getter for player.
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * setter for player.
     * @param newPlayer
     */
    public void setPlayer(Player newPlayer) {
        player = newPlayer;
    }

    /**
     * getter for door.
     * @param direction of door
     * @return doors
     */
    public Door getDoor(String direction) {
        return doors.get(direction);
    }

    /**
     * getter for door array.
     * @return list
     */
    public ArrayList<Door> getDoors() {
        for (Map.Entry<String, Door> door : doors.entrySet()) {
            list.add(door.getValue());
        }
        return list;
    }

    /**
     * settings for door.
     * @param door
     */
    public void setDoor(Door door) {
        doors.put(door.getDirection(), door);
    }
    /**
     * checks if player is in room.
     * @return player
     */
    public boolean isPlayerInRoom() {
        return player.getCurrentRoom() == this;
    }

    /**
     * make sure enough doors.
     * @return true
     * @throws NotEnoughDoorsException
     */
    public boolean verifyRoom() throws NotEnoughDoorsException {
        if (doors.isEmpty()) {
            throw new NotEnoughDoorsException();
        }
        return true;
    }

    /**
     * displayy different componentss.
     * @param enviroment
     * @return add
     */
    private String display(Character[][] enviroment) {
        add = "";

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                add = add.concat(String.valueOf(enviroment[y][x]));
            }
            add = add.concat("\n");
        }
        return add;
    }

    /**
     * sends to enviroments in.
     * @return enviroment
     */
    private Character[][] assembleTiles() {
        Character[][] enviroment = new Character[width][height];
        setting(enviroment);
    return enviroment;
    }

    /**
     * adds all the compnents to the room.
     * @param enviroment
     */
    private void setting(Character[][] enviroment) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                enviroment[x][y] = symbolMap.get("FLOOR");
            }
        }
        if (player.getCurrentRoom() == this) {
            Point playerPos = player.getXyLocation();
            enviroment[playerPos.x][playerPos.y] = symbolMap.get("PLAYER");
        }
        for (Item obj : roomItems) {
            Point point = obj.getXyLocation();
            enviroment[point.x][point.y] = symbolMap.get(obj.getType().toUpperCase());
        }
        for (int x = 0; x < height; x++) {
            enviroment[0][x] = symbolMap.get("EW_WALL");
            enviroment[width - 1][x] = symbolMap.get("EW_WALL");
        }
        for (int x = 0; x < width; x++) {
            enviroment[x][0] = symbolMap.get("NS_WALL");
            enviroment[x][height - 1] = symbolMap.get("NS_WALL");
        }
        for (Map.Entry<String, Door> d : doors.entrySet()) {
            if (d.getKey() == "N") {
                enviroment[d.getValue().getWallPosition()][0] = symbolMap.get("DOOR");
            } else if (d.getKey() == "W") {
                enviroment[0][d.getValue().getWallPosition()] = symbolMap.get("DOOR");
            } else if (d.getKey() == "S") {
                enviroment[d.getValue().getWallPosition()][height - 1] = symbolMap.get("DOOR");
            } else if (d.getKey() == "E") {
                enviroment[width - 1][d.getValue().getWallPosition()] = symbolMap.get("DOOR");
            }
        }
    }

    /**
    * Produces a string that can be printed to produce an ascii rendering of the room and all of its contents.
    * @return add
    */
    public String displayRoom() {
        add = "";

        Character[][] enviroment = assembleTiles();
        add = add.concat(display(enviroment));

        if (isPlayerInRoom()) {
            Point point = player.getXyLocation();
            enviroment[(int) point.getX()][(int) point.getY()] = symbolMap.get("PLAYER");
        }
        return add;
    }

}
