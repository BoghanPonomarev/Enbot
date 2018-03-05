package ua.nure.ponomarev.exception;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * @author Bogdan_Ponamarev.
 */
public class ValidationException extends Exception{
    private List<String> massages;

    public ValidationException(){
        massages = new ArrayList<String>();
    }

    public ValidationException(String massage){
        this();
        massages.add(massage);
    }

    public void addMassage(String massage){
        massages.add(massage);
    }
    public List<String> getMassages(){
        return new ArrayList<String>(massages);
    }

    public boolean isEmpty(){
        return massages.isEmpty();
    }
}
