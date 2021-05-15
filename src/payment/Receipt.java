package payment;

import static database.DatabaseAccessor.CONNECTION;
import database.Persistable;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.*;

/**
 * Models a receipt
 */
public class Receipt implements Persistable {
    private LocalDate issueDate;
    private Instant issueTime;
    private String receiptNumber;
    private Map<String, Double> items;

    public Receipt() {
        this.receiptNumber = generateReceiptNumber();
    }

    public Receipt(LocalDate issueDate, Instant issueTime, String details) {
        this.issueDate = issueDate;
        this.issueTime = issueTime;
        this.receiptNumber = generateReceiptNumber();
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public Instant getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Instant issueTime) {
        this.issueTime = issueTime;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public Map<String, Double> getItems() {
        return items;
    }
    
    public void setItems(Map<String, Double> items) {
        this.items = items;
    }
    
    @Override
    public boolean persist() {
       final String QUERY = "INSERT INTO receipt VALUES('" + getReceiptNumber() +
                 "','" + getIssueDate() + "', '" + getIssueTime() + "');";
        final String RECEIPT_NUMBER = getReceiptNumber();
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(QUERY);
            getItems().forEach((item, fee) -> {
                final String QUERY2 = "INSERT INTO receipt_items VALUES('" + RECEIPT_NUMBER
                        + "', 'Item: " + item + "');";
                try {
                    statement.executeUpdate(QUERY2);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Generates a random 10 digit receipt number
     * @return the receipt number
     */
    private String generateReceiptNumber() {
        final int RECEIPT_NUMBER_LENGTH = 10;
        Random rand = new Random();
        String receiptNumberString = "1234567890";
        StringBuilder receiptNumberSb = new StringBuilder();
        for(int i = 0; i < RECEIPT_NUMBER_LENGTH; i++) {
            receiptNumberSb.append(receiptNumberString.charAt(rand.nextInt(RECEIPT_NUMBER_LENGTH)));
        }
        return receiptNumberSb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }

        if(other == null) {
            return false;
        }

        if(!(other instanceof Receipt)) {
            return false;
        }

        if(!this.getReceiptNumber().equals(((Receipt) other).getReceiptNumber())) {
            return false;
        }

        return true;
    }
    
}
