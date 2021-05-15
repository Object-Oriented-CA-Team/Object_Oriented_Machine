package main;

import database.*;
import static database.DatabaseAccessor.CONNECTION;
import disc.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import payment.DebitCard;
import payment.Receipt;

public class Rental implements Persistable {
    private double rentalFee;
    private User user;
    private Disc disc;
    private DiscHandler discHandler;
    private LocalDate dateRented;
    private String id;
    private Receipt receipt;
    
    public Rental() {
        this.id = generateRentalId();
    }

    public Rental(double rentalFee, User user, Disc disc,
            DiscHandler discHandler, LocalDate dateRented,
            Receipt receipt) {
        this.rentalFee = rentalFee;
        this.user = user;
        this.disc = disc;
        this.discHandler = discHandler;
        this.dateRented = dateRented;
        this.id = generateRentalId();
        this.receipt = receipt;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Disc getDisc() {
        return disc;
    }

    public void setDisc(Disc disc) {
        this.disc = disc;
    }

    public DiscHandler getDiscHandler() {
        return discHandler;
    }

    public void setDiscHandler(DiscHandler discHandler) {
        this.discHandler = discHandler;
    }

    public LocalDate getDateRented() {
        return dateRented;
    }

    public void setDateRented(LocalDate dateRented) {
        this.dateRented = dateRented;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public Receipt getReceipt() {
        return this.receipt;
    }
    
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

   
    @Override
    public boolean persist() {
        final String QUERY = "INSERT INTO rental VALUES('"  + getId() + "'," +
                getRentalFee() + ", '" + getDateRented() + "', 1" //+ getDisc().getDiscId()
                + ", '" + getReceipt().getReceiptNumber() + "');";
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(QUERY);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
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

        if(!(other instanceof Rental)) {
            return false;
        }

        if(!this.getId().equals(((Rental) other).getId())) {
            return false;
        }

        return true;
    }
    
    /**
     * Generates a random 10 digit rental ID
     * @return the receipt number
     */
    private String generateRentalId() {
        final int RECEIPT_NUMBER_LENGTH = 10;
        Random rand = new Random();
        String rentalIdString = "1234567890";
        StringBuilder rentalIdSb = new StringBuilder();
        for(int i = 0; i < RECEIPT_NUMBER_LENGTH; i++) {
            rentalIdSb.append(rentalIdString.charAt(rand.nextInt(RECEIPT_NUMBER_LENGTH)));
        }
        return rentalIdSb.toString();
    }
    
    public static void main(String[] args) {
//        DiscTag discTag = new DiscTag();
//        discTag.setDateRented(LocalDate.now());
//        discTag.setDateReturned(LocalDate.now());
//        discTag.setTimeRented(Instant.now());
//        discTag.setTimeReturned(Instant.now());
//        
//        Disc disc = new Disc(0, discTag);
//        
//        Map<Movie, Double> items = new HashMap<>();
//        items.put(Movie.retrieveAll().get(0), 22.0);
//        
//        Receipt receipt = new Receipt();
//        receipt.setIssueDate(LocalDate.now());
//        receipt.setIssueTime(Instant.now());
//        receipt.setItems(items);
//        
//        Rental rental = new Rental();
//        rental.setDateRented(LocalDate.now());
//        rental.setDisc(disc);
//        rental.setReceipt(receipt);
//        rental.setRentalFee(22.0);
//        rental.setUser(new User("thomasmunguya@gmail.com", new DebitCard()));
//        
//        System.out.println("Persisted: " + rental.persist());
    }
}
