package database;

import java.sql.*;

/**
 * Models an entity that is able to access the database to retrieve or persist data
 */
public interface DatabaseAccessor {
    /**
     * The database instance needed to handle database business
     */
    public final Database DATABASE = Database.getInstance();
    /**
     * The connection to the database
     */
    public final Connection CONNECTION = DATABASE.connectToDB();
}
