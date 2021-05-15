package payment;

import static database.DatabaseAccessor.CONNECTION;
import database.*;
import java.sql.*;
import java.util.*;
import javafx.scene.control.Alert;
/**
 * Models a debit card
 */
public class DebitCard extends PaymentCard {

    public DebitCard() {
        super();
    }

    public DebitCard(String cardNumber, String cardName, String expiryDate, String pin, double balance) {
        super(cardNumber, cardName, expiryDate, pin, balance);
    }

    @Override
    public boolean persist() {
        final String QUERY = "INSERT INTO debit_card VALUES('" + getCardNumber() + "',"
                + " '" + getCardName() + "',"
                + " '" + getBalance() + "',"
                + " '" + getExpiryDate()+ "',"
                + " '" + getPin() + "')"
                + " ON DUPLICATE KEY UPDATE"
                + " balance = '" + getBalance() + "';";
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(QUERY);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public static List<DebitCard> retrieveAll() {
        final String QUERY = "SELECT * FROM debit_card;";

        final List<DebitCard> retrievedDebitCards = new ArrayList<>();
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet retrievedDCardRS = statement.executeQuery(QUERY);
            while(retrievedDCardRS.next()) {
                DebitCard debitCard = new DebitCard();
                debitCard.setCardNumber(retrievedDCardRS.getString("card_number"));
                debitCard.setCardName(retrievedDCardRS.getString("card_name"));
                debitCard.setBalance(retrievedDCardRS.getDouble("balance"));
                debitCard.setExpiryDate(retrievedDCardRS.getString("expiry_date"));
                debitCard.setPin(retrievedDCardRS.getString("pin"));
                retrievedDebitCards.add(debitCard);
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return retrievedDebitCards;
    }
    
    @Override
    public Retrievable retrieveOne(String columnName, String columnValue) {
        final String QUERY = "SELECT * FROM debit_card WHERE " + columnName + 
                " = '" + columnValue + "';";
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet retrievedDCardRS = statement.executeQuery(QUERY);
            if(retrievedDCardRS.next()) {
                DebitCard debitCard = new DebitCard();
                debitCard.setCardNumber(retrievedDCardRS.getString("card_number"));
                debitCard.setCardName(retrievedDCardRS.getString("card_name"));
                debitCard.setBalance(retrievedDCardRS.getDouble("balance"));
                debitCard.setExpiryDate(retrievedDCardRS.getString("expiry_date"));
                debitCard.setPin(retrievedDCardRS.getString("pin"));
                return debitCard;
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        DebitCard debitCard = new DebitCard();
        debitCard.setCardNumber("-1");
        return debitCard;
    }
    
    @Override
    public boolean releaseFunds(double amount) {
        if(amount <= getBalance()) {
            setBalance(getBalance() - amount);
            if(persist()) {
                return true;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Insufficient Funds.");
        alert.setContentText("Looks like you do not have sufficient funds. "
                + "Please ensure that your payment card has enough funds before"
                + " attempting a rental. ");
        alert.show();
        return false;
    }

}
