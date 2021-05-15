
package controllers;

import disc.*;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;
import main.*;
import payment.*;
import utils.Mailer;

/**
 * 
 * Controller class for the card payment view
 */
public class PaymentController implements Initializable {
    
     @FXML
    private Button btnGoBack;

    @FXML
    private AnchorPane numberPadPane;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn6;

    @FXML
    private Button btn5;

    @FXML
    private Button btn7;

    @FXML
    private Button btn0;

    @FXML
    private Button btn9;

    @FXML
    private Button btn8;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnBackSpace;

    @FXML
    private Text txtCardNumber;

    @FXML
    private Button btnPay;
    
    @FXML
    private AnchorPane rootPane;

    private static final Alert ALERT = new Alert(Alert.AlertType.ERROR);
    
    private static String pin;
    
    private static boolean pinAttemptsExhausted;
    
    private static final List<String> blockedCardNumbers = new ArrayList<>();
    
    private static Receipt receipt;
    
    private static Rental rental;
    
    private static User user;
    
    private static boolean pinCancelled;
    
    private static List<Movie> moviesToRent = new ArrayList<>();
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpNumberPadEventHandlers();
    }
    
    public static void setPinAttemptsExhausted() {
        pinAttemptsExhausted = true;
    }
    
    public static void setPinCancelled(boolean pinCancelled) {
        PaymentController.pinCancelled = pinCancelled;
    }
    
    /**
     * Setups event handlers for number pad buttons
     */
    public void setUpNumberPadEventHandlers() {
        btn0.setOnAction((event) -> {
            registerKeyPress("0");
        });
        
        btn1.setOnAction((event) -> {
            registerKeyPress("1");
        });
        
        btn2.setOnAction((event) -> {
            registerKeyPress("2");
        });
        
        btn3.setOnAction((event) -> {
            registerKeyPress("3");
        });
        
        btn4.setOnAction((event) -> {
            registerKeyPress("4");
        });
        
        btn5.setOnAction((event) -> {
            registerKeyPress("5");
        });
        
        btn6.setOnAction((event) -> {
            registerKeyPress("6");
        });
        
        btn7.setOnAction((event) -> {
            registerKeyPress("7");
        });
        
        btn8.setOnAction((event) -> {
            registerKeyPress("8");
        });
        
        btn9.setOnAction((event) -> {
            registerKeyPress("9");
        });
        
        
        btnBackSpace.setOnAction((event) -> {
            registerKeyPress("DEL");
        });
        
        btnClear.setOnAction((event) -> {
            registerKeyPress("CLR");
        });
        
    }
    
    /**
     * Registers a key press on the number pad
     * @param key the key pressed
     */
    private void registerKeyPress(String key) {
        if(txtCardNumber.getText().length() == 19) {
            return;
        }
        
       switch(key) {
           case "0": txtCardNumber.setText(txtCardNumber.getText() + "0"); break;
           case "1": txtCardNumber.setText(txtCardNumber.getText() + "1"); break;
           case "2": txtCardNumber.setText(txtCardNumber.getText() + "2"); break;
           case "3": txtCardNumber.setText(txtCardNumber.getText() + "3"); break;
           case "4": txtCardNumber.setText(txtCardNumber.getText() + "4"); break;
           case "5": txtCardNumber.setText(txtCardNumber.getText() + "5"); break;
           case "6": txtCardNumber.setText(txtCardNumber.getText() + "6"); break;
           case "7": txtCardNumber.setText(txtCardNumber.getText() + "7"); break;
           case "8": txtCardNumber.setText(txtCardNumber.getText() + "8"); break;
           case "9": txtCardNumber.setText(txtCardNumber.getText() + "9"); break;
           case "DEL": {
            if(txtCardNumber.getText().length() == 0) {
                return;
            }
            String newText = txtCardNumber.getText().substring(0, txtCardNumber.getText().length() - 1);
            txtCardNumber.setText(newText);
           } break;
           case "CLR": txtCardNumber.setText(""); break;
                     
       }
    }
    
    @FXML
    /**
     *Processes a payment
     *@throws IOException passed on from the showPinRequestWindow() method
     */
    public void processPayment() throws IOException {
        String cardNumber = txtCardNumber.getText();
        
        PaymentCard paymentCard = new CreditCard();
        paymentCard = (CreditCard) paymentCard.retrieveOne("card_number", txtCardNumber.getText());
        
        PaymentCard retrievedCard;
        
        //if the card has been blocked because the user entered a wrong PIN three times,
        //block the card from making payments
        if(blockedCardNumbers.contains(cardNumber)) {
            ALERT.setAlertType(Alert.AlertType.ERROR);
            ALERT.setHeaderText("Card blocked.");
            ALERT.setContentText("Sorry. This card has been blocked because you entered a wrong PIN three times.");
            ALERT.show();
            return;
        }
        
        
        //if the card type is a credit card then instatiate a credit card
        if(!paymentCard.getCardNumber().equals("-1")) {
            paymentCard.setCardNumber(cardNumber);
            retrievedCard = (CreditCard) paymentCard;
            PinRequestController.setPin(retrievedCard.getPin());
        }
        
        //if its a debit card then instantiate a debit card
        else {
            paymentCard = new DebitCard();
            paymentCard.setCardNumber(cardNumber);
            retrievedCard = (DebitCard) paymentCard.retrieveOne("card_number", cardNumber);
            PinRequestController.setPin(retrievedCard.getPin());
        }
        
        if(!PaymentCard.isValidCard(paymentCard)) {
            ALERT.setAlertType(Alert.AlertType.ERROR);
            ALERT.setHeaderText("Invalid Payment Card.");
            ALERT.setContentText("Your payment card is either not valid or is not registered with Movie Rental"
                    + " . Ensure that your card number is correct and that it is registered with"
                    + "with Movie Rental");
            ALERT.show();
            return;
        } else {
        }
        
        System.out.println("Retrieved Card PIN" + retrievedCard.getPin());
        PinRequestController.setPin(retrievedCard.getPin());
        
        //request for PIN before making a payment
        showPinRequestWindow();
        
        //if a wrong PIN has been entered three times, add the card number to the list of blocked card numbers
        if(pinAttemptsExhausted) {
            blockedCardNumbers.add(cardNumber);
            return;
        }
        
        //if entry of PIN was cancelled don't do anything
        if(pinCancelled) {
            return;
        }
        
       
        paymentCard = retrievedCard;
        
        //set the retrieved card to null to avoid loiterring
        retrievedCard = null;
        
        
        //get the total amount to be paid from the receipt
        double[] amountToPay = new double[1];
        setUpPaymentInfo();
        
        receipt.getItems().forEach((item, rentalPrice) -> {
            amountToPay[0] += rentalPrice;
        });
        
        //if payment is successful, persist all information to the database,
        //display a success message and send a receipt to the email if given
        if(paymentCard.releaseFunds(amountToPay[0])) {
            
            rental.persist();
            receipt.persist();
            moviesToRent.forEach((movie) -> movie.persist());
            
            ALERT.setAlertType(Alert.AlertType.INFORMATION);
            ALERT.setHeaderText("Payment Successful");
            ALERT.setContentText("Your payment was successful. "
                    + "A receipt has been sent to your email (if you registered your email address)."
                    + " Please collect your disc(s).");
            ALERT.show();
            
            Runnable mailDeliverer = () -> {
                Mailer mailer = Mailer.getInstance();
                try {
                    
                    if(user.getEmailAddress().equals("")) return;
                    
                    mailer.sendReceiptEmail(user.getEmailAddress(), receipt);
                        
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            };
            
            Thread mailerDeliverThread = new Thread(mailDeliverer);
            mailerDeliverThread.start();
            
        }
            
    }
    
    /**
     * Sets up payment information
     */
    private void setUpPaymentInfo() {
        
        DiscTag discTag = new DiscTag();
        discTag.setDateRented(LocalDate.now());
        
        receipt = new Receipt();
        receipt.setIssueDate(LocalDate.now());
        receipt.setIssueTime(Instant.now());
        
        rental = new Rental();
        
        user = new User();
        user = (User) user.retrieveOne("payment_card_number", txtCardNumber.getText());
        
        Map<String, Double> cartItems = new HashMap<>();
        
        MovieCartController.getCart().forEach((movie) -> {
            
            cartItems.put(movie.getTitle(), movie.getRentalPrice());
            Disc disc = new Disc(movie.getDiscs().get(0).getId(), discTag);
            movie.getDiscs().remove(0);
            moviesToRent.add(movie);
            
            rental.setDateRented(LocalDate.now());
            rental.setDisc(disc);
            rental.setRentalFee(movie.getRentalPrice());
            rental.setUser(user);
            rental.setReceipt(receipt);
            
        });
        
        receipt.setItems(cartItems);
        
    }

    /**
     * Shows the PIN request pop up window
     * @throws IOException if the view file cannot be loaded
     */
    private void showPinRequestWindow() throws IOException {
        AnchorPane pinRequestRootPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/ui/PinRequest.fxml"));
        Scene scene = new Scene(pinRequestRootPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Enter PIN");
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
    }
    
    @FXML
    /**
     * Navigates to movie cart
     * @throws IOException if the view file cannot not be loaded
     */
    public void navigateToMovieCart() throws IOException {
        AnchorPane movieCartrootPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/ui/MovieCart.fxml"));
        Scene scene = new Scene(movieCartrootPane, 720, 600);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Movie Cart");
        stage.setResizable(false);
        stage.show();
    }
 
}
