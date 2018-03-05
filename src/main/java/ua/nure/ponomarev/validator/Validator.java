package ua.nure.ponomarev.validator;
import ua.nure.ponomarev.exception.ValidationException;
import java.util.List;
/**
 * @author Bogdan_Ponamarev.
 */
public interface Validator <T> {
    void validate(T parameter) throws ValidationException;
}
