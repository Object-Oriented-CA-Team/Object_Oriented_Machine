package disc;

import static database.DatabaseAccessor.CONNECTION;
import database.Persistable;
import database.Retrievable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;

/**
 * Models a disc tag
 * A disc tag is represented by the date, and the time the disc was rented and by the date and the time it was returned
 */
public class DiscTag implements Retrievable, Persistable{

    private LocalDate dateRented;
    private LocalTime timeRented;
    private LocalDate dateReturned;
    private LocalTime timeReturned;
    private String id;

    public DiscTag() {
    }

    
    public DiscTag(LocalDate dateRented, LocalTime timeRented, LocalDate dateReturned,
            LocalTime timeReturned, String id) {
        this.dateRented = dateRented;
        this.timeRented = timeRented;
        this.dateReturned = dateReturned;
        this.timeReturned = timeReturned;
        this.id = id;
    }

    public LocalDate getDateRented() {
        return dateRented;
    }

    public void setDateRented(LocalDate dateRented) {
        this.dateRented = dateRented;
    }

    public LocalTime getTimeRented() {
        return timeRented;
    }

    public void setTimeRented(LocalTime timeRented) {
        this.timeRented = timeRented;
    }

    public LocalDate getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(LocalDate dateReturned) {
        this.dateReturned = dateReturned;
    }

    public LocalTime getTimeReturned() {
        return timeReturned;
    }

    public void setTimeReturned(LocalTime timeReturned) {
        this.timeReturned = timeReturned;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    @Override
    public Retrievable retrieveOne(String columnName, String columnValue) {
       final String QUERY = "SELECT * FROM disc_tag WHERE " + columnName
               + " = '" + columnValue + "';";
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet discTagsRS = statement.executeQuery(QUERY);
            if(discTagsRS.next()) {
                DiscTag discTag = new DiscTag();
                discTag.setDateRented(LocalDate.parse(discTagsRS.getString("date_rented")));
                discTag.setTimeRented(LocalTime.parse(discTagsRS.getString("time_rented")));
                discTag.setDateReturned(LocalDate.parse(discTagsRS.getString("date_returned")));
                discTag.setTimeReturned(LocalTime.parse(discTagsRS.getString("time_returned")));
                discTag.setId(discTagsRS.getString("id"));
                return discTag;
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean persist() {
         final String QUERY = "INSERT INTO disc_tag VALUES('"
                + getId() + "',"
                + " '"  + getDateRented() + "',"
                + " '" + getTimeRented() + "',"
                + " '" + getDateReturned() + "',"
                + " '" + getTimeReturned() + "')"
                + " ON DUPLICATE KEY UPDATE "
                + "date_returned = '" + getDateReturned() + "',"
                + " time_returned = '" + getTimeReturned()+ "';";
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(QUERY);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

}
