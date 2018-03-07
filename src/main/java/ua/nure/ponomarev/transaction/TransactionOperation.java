package ua.nure.ponomarev.transaction;

import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.ValidationException;

/**
 * @author Bogdan_Ponamarev.
 * Function interface that that reprsents transaction operatin
 * Takes type that will be returned after operation
 */
public interface TransactionOperation<T> {
    T execute() throws DbException, ValidationException;
}
