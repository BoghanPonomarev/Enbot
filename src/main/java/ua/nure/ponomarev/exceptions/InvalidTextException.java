package ua.nure.ponomarev.exceptions;

/**
 * @author Bogdan_Ponamarev.
 */
public class InvalidTextException extends Exception{
    private String massage;
    public InvalidTextException(String massage) {
        this.massage = massage;
    }
}
