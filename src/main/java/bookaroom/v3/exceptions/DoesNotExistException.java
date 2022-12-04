package bookaroom.v3.exceptions;

/*
 * @author Team BookARoom
 */

public class DoesNotExistException extends Exception {

    private String message;

    public DoesNotExistException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
