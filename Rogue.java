package rogue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import java.awt.Point;


public class Rogue {
    // given static variables
    public static final char UP = 'h';
    public static final char DOWN = 'j';
    public static final char LEFT = 'k';
    public static final char RIGHT = 'l';

    private RogueParser parser;
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> curRoomItems = new ArrayList<>();
    private ArrayList<Item> roomItems = new ArrayList<>();
    private Player currentPlayer = new Player();
    private Map<String, Character> symbols;
    private Point location;
    private Point nextPlace;
    private int xLocation;
    private int yLocation;
    private int wall;
    private int id;
    private int xLoc;
    private int yLoc;
    private String roomDis = "";
    private String directionDoor = "";
    private String doorOneDirection;
    private String doorTwoDirection;
    private Room currentRoom;


    /**
     * Default constructor.
     */
    public Rogue() {

    }

    /**
     * Constructor calls the RogueParser to parse through the rooms and items of the game.
     * @param theDungeonInfo - the parser
     */
    public Rogue(RogueParser theDungeonInfo) {

        parser = theDungeonInfo;
        symbols = theDungeonInfo.getSymbols();

        Map roomInfo = parser.nextRoom();
        while (roomInfo != null) {
            addRoom(roomInfo);
            roomInfo = parser.nextRoom();
        }
        parser.resetRoomIterator();
        roomInfo = parser.nextRoom();
        while (roomInfo != null) {
            roomDoors(roomInfo);
            roomInfo = parser.nextRoom();
        }
        verifyRooms();

        Map itemInfo = parser.nextItem();
        while (itemInfo != null) {
            try {
                addItem(itemInfo);
            } catch (ImpossiblePositionException e) {
                try {
                    addItem(itemInfo, next(e.getPosition(), e.getRoom()));
                } catch (ImpossiblePositionException | NoSuchItemException impossiblePositionException) {
                }
            } catch (NoSuchItemException e) {
            }
            itemInfo = parser.nextItem();
        }

        for (Room a : rooms) {
            if (a.getBeginningRoom()) {
                currentRoom = a;
                currentPlayer.setCurrentRoom(currentRoom);
                break;
            }
        }
    }

    /**
     * Getter for Rooms.
     * @return rooms
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Getter for Items.
     * @return items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Getter for Player.
     * @return player
     */
    public Player getPlayer() {
        return currentPlayer;
    }

    /**
     * Setter for Player.
     * @param newPlayer
     */
    public void setPlayer(Player newPlayer) {
        currentPlayer.setXyLocation(newPlayer.getXyLocation());
        currentPlayer.setName(newPlayer.getName());
    }

    /**
     * make the player move.
     * @param input of the user
     * @return lil message above
     * @throws InvalidMoveException
     */
    public String makeMove(char input) throws InvalidMoveException {
        location = currentPlayer.getXyLocation();
        xLocation = (int) location.getX();
        yLocation = (int) location.getY();

        if ((input != RIGHT) && (input != LEFT) && (input != UP) && (input != DOWN)) {
            throw new InvalidMoveException();
        } else {

            if (input == DOWN) {
                yLocation = yLocation + 1;
            } else if (input == RIGHT) {
                xLocation = xLocation + 1;
            } else if (input == LEFT) {
                xLocation = xLocation - 1;
            } else if (input == UP) {
                yLocation = yLocation - 1;
            }

            if (notThere(xLocation, yLocation)) {
                Door b = findDoor(input, xLocation, yLocation);
                if (b != null) {
                    xLocation = 1;
                    yLocation = 1;
                    currentRoom = b.getOtherRoom(currentRoom);
                }
            }

            if (!notThere(xLocation, yLocation)) {
            Iterator<Item> c = curRoomItems.iterator();
            currentPlayer.setXyLocation(new Point(xLocation, yLocation));
            currentPlayer.setCurrentRoom(currentRoom);
            curRoomItems = currentRoom.getRoomItems();

            while (c.hasNext()) {
                Item item = c.next();
                Point itemLocation = item.getXyLocation();
                Point playerLocation = currentPlayer.getXyLocation();
                if (itemLocation.equals(playerLocation)) {
                    c.remove();
                    break;
                }
            }
            currentRoom.setRoomItems(curRoomItems);
        }
    }
        return "That's a lovely move: " + Character.toString(input);
    }

    /**
     * locate room.
     * @param idA is for room
     * @return room or null
     */
    private Room locateRoom(int idA) {
        for (Room a : rooms) {
            int roomId = a.getId();
            if (roomId == idA) {
                return a;
            }
        }
        return null;
    }

    /**
     * returns next display.
     * @return current to display
     */
    public String getNextDisplay() {
        return currentRoom.displayRoom();
    }

    /**
     * stop points from reaching outside of room.
     * @param xPos x point
     * @param yPos y point
     * @param room
     * @return positions
     */
    private Boolean notThere(int xPos, int yPos, Room room) {
        int width = room.getWidth();
        int height = room.getHeight();

        return (xPos == 0 || xPos == width - 1 || yPos == 0 || yPos == height - 1);
    }

    /**
     * stop points from reaching outside of room.
     * @param xPos x point
     * @param yPos y point
     * @return notThere
     */
    private Boolean notThere(int xPos, int yPos) {
        return notThere(xPos, yPos, currentRoom);
    }

    /**
     * adding parameters of the rooms.
     * @param toAdd map from parser
     */
    public void addRoom(Map<String, String> toAdd) {
        Room a = new Room();
        a.setId(Integer.parseInt(toAdd.get("id")));
        a.setIsStartingRoom(Boolean.parseBoolean(toAdd.get("start")));
        a.setHeight(Integer.parseInt(toAdd.get("height")));
        a.setWidth(Integer.parseInt(toAdd.get("width")));
        a.setSymbols(symbols);
        a.setPlayer(currentPlayer);

        rooms.add(a);
    }

    /**
     * doors of the rooms from hashmap.
     * @param toAdd map
     */
    public void roomDoors(Map<String, String> toAdd) {
        String[] directions = {"N", "S", "W", "E"};
        Room a = locateRoom(Integer.parseInt(toAdd.get("id")));
        for (String direction : directions) {
            wall = Integer.parseInt(toAdd.get(direction + "_wall_pos"));
            if (wall != -1) {
                Door b = new Door(direction, wall);
                Room a2 = locateRoom(Integer.parseInt(toAdd.get(direction + "_con_room")));
                b.connectRoom(a2);
                b.connectRoom(a);
                a.setDoor(b);
            }
        }
    }

    /**
     * exceptions.
     * @param toAdd
     * @throws ImpossiblePositionException
     * @throws NoSuchItemException
     */
    public void addItem(Map<String, String> toAdd) throws
        ImpossiblePositionException,
        NoSuchItemException {
    addItem(toAdd, null);
    }

    /**
     * adding items to rooms.
     * @param toAdd map of items from parser
     * @param p points
     * @throws ImpossiblePositionException
     * @throws NoSuchItemException
     */
    private void addItem(Map<String, String> toAdd, Point p) throws ImpossiblePositionException, NoSuchItemException {
        String type = toAdd.get("type");
        id = Integer.parseInt(toAdd.get("room"));

        xLoc = p == null ? Integer.parseInt(toAdd.get("x")) : p.x;
        yLoc = p == null ? Integer.parseInt(toAdd.get("y")) : p.y;

        if (toAdd.get("room") == null) {
            throw new NoSuchItemException();
        }

        Room a = locateRoom(id);
        if (notThere(xLoc, yLoc, a)) {
            throw new ImpossiblePositionException(xLoc, yLoc, a);
        }

        Item item = new Item(Integer.parseInt(toAdd.get("id")), toAdd.get("name"), type, new Point(xLoc, yLoc));
        items.add(item);

        roomItems = a.getRoomItems();
        a.setRoomItems(roomItems);
        roomItems.add(item);
    }

    /**
     * so Player finds the door.
     * @param input of the key
     * @param playerX location x of player
     * @param playerY location y of player
     * @return doors to connect
     */
    private Door findDoor(char input, int playerX, int playerY) {
        Door b = null;
        if (input == UP) {
            Door d = currentRoom.getDoor("N");
             int wallPos = d.getWallPosition();
            if (d != null && wallPos == playerX) {
                b = d;
            }
        } else if (input == DOWN) {
            Door d = currentRoom.getDoor("S");
            int wallPos = d.getWallPosition();
            if (d != null && wallPos == playerX) {
                b = d;
            }
        } else if (input == LEFT) {
            Door d = currentRoom.getDoor("W");
            if (d != null && d.getWallPosition() == playerY) {
                b = d;
            }
        } else if (input == RIGHT) {
            Door d = currentRoom.getDoor("E");
            if (d != null && d.getWallPosition() == playerY) {
                b = d;
            }
        }
        return b;
    }

    /**
     * locates next door.
     * @param a - room
     * @return direction of next door
     */
    private String anotherDoor(Room a) {
        String[] directions = {"N", "S", "W", "E"};
        for (String direction : directions) {
            boolean exist = false;
            for (Door d : a.getDoors()) {
                String getDirection = d.getDirection();
                if (direction.equals(getDirection)) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                directionDoor = direction;
                break;
            }
        }
        return directionDoor;
    }

    /**
     * finds point for next location.
     * @param place - the next point
     * @param a - room
     * @return nextPlace
     */
    private Point next(Point place, Room a) {
        nextPlace = new Point(place.x, place.y);

        if (place.y <= 0) {
            nextPlace.y = 1;
        } else if (place.y >= a.getHeight() - 1) {
            nextPlace.y = a.getHeight() - 2;
        }

        if (place.x <= 0) {
            nextPlace.x = 1;
        } else if (place.x >= a.getWidth() - 1) {
            nextPlace.x = a.getWidth() - 2;
        }
        return nextPlace;
    }

    /**
     * try and catches exceptions for not enough doors.
     */
    private void verifyRooms() {
        for (Room a : rooms) {
            try {
                a.verifyRoom();
            } catch (NotEnoughDoorsException e) {
                boolean corrected = false;

                for (Room a2 : rooms) {
                    if (createDoor(a, a2)) {
                        corrected = true;
                        break;
                    }
                }

                if (!corrected) {
                    System.out.println("The dungeon file cannot be used as it contains invalid room");
                    System.exit(0);
                }
            }
        }
    }

    /**
     * create the doors within the rooms.
     * @param roomOne room number 1
     * @param roomTwo room number 2
     * @return true/false
     */
    private boolean createDoor(Room roomOne, Room roomTwo) {
        if (roomOne == roomTwo) {
            return false;
        }

        String[] direction = {"N", "S", "W", "E"};
        int maxDoorsCount = direction.length;

        if (roomOne.getDoors().size() >= maxDoorsCount || roomTwo.getDoors().size() >= maxDoorsCount) {
            return false;
        }

        doorOneDirection = anotherDoor(roomOne);
        doorTwoDirection = anotherDoor(roomTwo);

        Door e = new Door(doorTwoDirection, 1);
        e.connectRoom(roomOne);
        e.connectRoom(roomTwo);
        roomTwo.setDoor(e);

        Door d = new Door(doorOneDirection, 1);
        d.connectRoom(roomOne);
        d.connectRoom(roomTwo);
        roomOne.setDoor(d);
        return true;
    }

    /**
     * displays full room.
     * @return roomDis - rooms
     */
    public String displayAll() {
        for (Room a : rooms) {
            String newLine = "\n\n";
            roomDis = roomDis.concat(a.displayRoom());
            roomDis = roomDis.concat(newLine);
        }
        return roomDis;
    }
}
