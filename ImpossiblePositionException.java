package rogue;
import java.awt.Point;

public class ImpossiblePositionException extends Exception {
    private Point loc;
    private Room room;

    /**
     * Default constructor.
     */
    public ImpossiblePositionException() {
       // loc(0,0);
    }

    /**
     * setting values.
     * @param x point
     * @param y point
     * @param roomLoc - room associated with position
     */
    public ImpossiblePositionException(int x, int y, Room roomLoc) {
        loc = new Point(x, y);
        room = roomLoc;
    }

    /**
     * Getter for position.
     * @return loc
     */
    public Point getPosition() {
        return loc;
    }

    /**
     * Getter for Rooms.
     * @return room
     */
    public Room getRoom() {
        return room;
    }
}
