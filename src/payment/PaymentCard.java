package payment;

import database.*;
import static database.DatabaseAccessor.CONNECTION;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Models a payment card
 * A payment card has a card number, an expiry date, a pin and a balance
 */
public abstract class PaymentCard implements Persistable, Retrievable {
    private String cardNumber;
    private String cardName;
    private String expiryDate;
    private String pin;
    private double balance;

    public PaymentCard() {

    }

    public PaymentCard(String cardNumber, String cardName, String expiryDate, String pin, double balance) {
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.expiryDate = expiryDate;
        this.pin = pin;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getCardName() {
        return cardName;
    }
    
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    

    /**
     * Releases funds from the payment card
     * @param amount the amount to release
     * @return true if fund release was successful or false if fund release was denied for some reason
     */
    public abstract boolean releaseFunds(double amount);

    /**
     * Validates a payment card using the Luhn algorithm
     * @param paymentCard the payment card whose number is to be verified
     * @return true if the payment card number is valid or false if otherwise
     */
    public static boolean isValidCard(PaymentCard paymentCard) {
        int[] ints = new int[paymentCard.getCardNumber().length()];
        for (int i = 0; i < paymentCard.getCardNumber().length(); i++) {
            ints[i] = Integer.parseInt(paymentCard.getCardNumber().substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        if (sum % 10 == 0) {
            return true;
        }
        return false;
    }


    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }

        if(other == null) {
            return false;
        }

        if(!(other instanceof PaymentCard)) {
            return false;
        }

        return this.getCardNumber().equals(((PaymentCard) other).getCardNumber());
    }
    
}
