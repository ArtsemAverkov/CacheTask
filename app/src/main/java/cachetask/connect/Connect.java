package cachetask.connect;

import java.sql.Connection;
import java.sql.SQLException;

public interface Connect {
    Connection connect() throws SQLException, ClassNotFoundException;
    boolean close();
}
