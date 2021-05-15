package database;

import java.util.List;

/**
 * Models an entity that can be retrieved from a database
 */
public interface Retrievable extends DatabaseAccessor {

    /**
     * Retrieves one object from the database
     * @param columnName the database column name
     * @param columnValue the database column value
     * @return the object retrieved
     */
    public abstract Retrievable retrieveOne(String columnName, String columnValue);
    
    /**
     * Retrieves all objects from the database
     * @return the objects retrieved
     */
//    public abstract List<Retrievable> retrieveAll();
}
