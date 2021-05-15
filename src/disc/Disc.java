package disc;

import database.*;
import static database.DatabaseAccessor.CONNECTION;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Models a disc
 * A disc has an ID by which it is recognized and a disc tag by which it can be traced
 */
public class Disc implements Retrievable {

    private String id;
    private DiscTag discTag;

    public  Disc() {

    }

    public Disc(String id, DiscTag discTag) {
        this.id = id;
        this.discTag = discTag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DiscTag getDiscTag() {
        return discTag;
    }

    public void setDiscTag(DiscTag discTag) {
        this.discTag = discTag;
    }
    
    @Override
    public Retrievable retrieveOne(String columnName, String columnValue) {
        final String QUERY = "SELECT * FROM disc WHERE " + columnName + 
                " = '" + columnValue + "';";
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet retrievedDiscRS = statement.executeQuery(QUERY);
            if(retrievedDiscRS.next()) {
               DiscTag discTag = new DiscTag();
               discTag.setId(retrievedDiscRS.getString("disc_tag_id"));
               discTag = (DiscTag) discTag.retrieveOne("id", retrievedDiscRS.getString("disc_tag_id"));
               return new Disc(retrievedDiscRS.getString("id"), discTag);
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        Disc disc = new Disc();
        disc.setId("-1");
        return disc;
    }

}
