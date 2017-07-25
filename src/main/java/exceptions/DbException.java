package exceptions;

import java.sql.SQLException;

public class DbException extends Exception {

    public DbException(String message) {
        super(message);
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }
}
