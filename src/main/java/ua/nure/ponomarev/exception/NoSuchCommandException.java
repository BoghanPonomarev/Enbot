package ua.nure.ponomarev.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
public class NoSuchCommandException extends Exception {
    private String massage;
    public NoSuchCommandException(String massage){
        this.massage= massage;
    }
}
