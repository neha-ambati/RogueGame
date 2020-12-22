package rogue;


public class InvalidMoveException extends Exception {

    /**
     * Default constructor.
     */
    public InvalidMoveException() {
        super();
    }

    /**
     * Constructor including the exception/error message.
     * @param message
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
