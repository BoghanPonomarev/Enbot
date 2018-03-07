package ua.nure.ponomarev.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
public class DbException extends Exception {
    private String massage;
    private Exception e;
    public DbException(String massage,Exception e){
        this.massage= massage;
        this.e = e;
    }
}
