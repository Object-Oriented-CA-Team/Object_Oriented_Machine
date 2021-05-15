package payment;

import static database.DatabaseAccessor.CONNECTION;
import database.*;
import java.sql.*;
import java.util.*;

public class CreditCard extends PaymentCard {
    private double creditLimit;
    private double amountCredited;

    public CreditCard() {
        super();
    }

    public CreditCard(String cardNumber, String cardName, String expiryDate, 
            String pin, double balance, double creditLimit, double amountCredited) {
        super(cardNumber, cardName, expiryDate, pin, balance);
        this.creditLimit = creditLimit;
        this.amountCredited = amountCredited;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }
    
    public double getAmountCredited() {
        return amountCredited;
    }
    
    public void setAmountCredited(double amountCredited) {
        this.amountCredited = amountCredited;
    }

    @Override
    public boolean persist() {
        final String QUERY = "INSERT INTO credit_card VALUES('" + getCardNumber() + "',"
                + " '" + getCardName() + "',"
                + " '" + getBalance() + "',"
                + " '" + getExpiryDate()+ "',"
                + " '" + getPin() + "',"
                + " '" + getCreditLimit() + "', "
                + " '" + getAmountCredited() + "')"
                + " ON DUPLICATE KEY UPDATE "
                + "amount_credited = '" + getAmountCredited() + "',"
                + " balance = '" + getBalance() + "';";
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(QUERY);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }
    
    /**
     * Retrieves all the credit cards from the database
     * @return the list of credit cards retrieved 
     */
    public static List<CreditCard> retrieveAll() {
        final String QUERY = "SELECT * FROM credit_card;";

        final List<CreditCard> retrievedCreditCards = new ArrayList<>();
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet retrievedCCardRS = statement.executeQuery(QUERY);
            while(retrievedCCardRS.next()) {
                CreditCard creditCard = new CreditCard();
                creditCard.setCardNumber(retrievedCCardRS.getString("card_number"));
                creditCard.setCardName(retrievedCCardRS.getString("card_name"));
                creditCard.setBalance(retrievedCCardRS.getDouble("balance"));
                creditCard.setCreditLimit(retrievedCCardRS.getDouble("credit_limit"));
                creditCard.setExpiryDate(retrievedCCardRS.getString("expiry_date"));
                creditCard.setPin(retrievedCCardRS.getString("pin"));
                creditCard.setAmountCredited(retrievedCCardRS.getDouble("amount_credited"));
                retrievedCreditCards.add(creditCard);
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return retrievedCreditCards;
    }
    
    @Override
    public Retrievable retrieveOne(String columnName, String columnValue) {
        final String QUERY = "SELECT * FROM credit_card WHERE " + columnName + 
                " = '" + columnValue + "';";
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet retrievedCCardRS = statement.executeQuery(QUERY);
            if(retrievedCCardRS.next()) {
                CreditCard creditCard = new CreditCard();
                creditCard.setCardNumber(retrievedCCardRS.getString("card_number"));
                creditCard.setCardName(retrievedCCardRS.getString("card_name"));
                creditCard.setBalance(retrievedCCardRS.getDouble("balance"));
                creditCard.setCreditLimit(retrievedCCardRS.getDouble("credit_limit"));
                creditCard.setExpiryDate(retrievedCCardRS.getString("expiry_date"));
                creditCard.setPin(retrievedCCardRS.getString("pin"));
                creditCard.setAmountCredited(retrievedCCardRS.getDouble("amount_credited"));
                return creditCard;
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber("-1");
        return creditCard;
    }
    
    
    @Override
    public boolean releaseFunds(double amount) {
        if(amount <= getBalance()) {
            setBalance(getBalance() - amount);
            if(persist()) {
                return true;
            }
        }
        else if ((getCreditLimit() - getAmountCredited()) >= amount) {
            setAmountCredited(amount);
            if(persist()) {
                return true;
            }
        }
        return false;
    }
}
