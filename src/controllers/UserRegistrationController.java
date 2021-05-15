/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.User;
import payment.*;


/**
 * Controller class for the user registration view
 */
public class UserRegistrationController implements Initializable {
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfCardNumber;

    @FXML
    private TextField tfCardName;

    @FXML
    private TextField tfExpiryDate;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnGoback;

    @FXML
    private ChoiceBox<String> choiceBoxCardType;
    
    private final Alert ALERT = new Alert(AlertType.ERROR);
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBoxCardType.getItems().addAll("Credit Card", "Debit Card");
        choiceBoxCardType.setValue("Credit Card");
    }
    
    @FXML
    /**
     * Attempts to register a user
     */
    public void registerUser() {
        PaymentCard card;
        
        if(!isFilledRegistrationForm()) {
            ALERT.setAlertType(AlertType.ERROR);
            ALERT.setHeaderText("Incomplete information provided");
            ALERT.setContentText("You have entered incomplete registration information."
                    + " Please ensure that all information is complete. "
                    + "The email is optional");
            ALERT.show();
            return;
        }
        if(choiceBoxCardType.getValue().equals("Debit Card")) {
            card = new DebitCard();
            card.setPin("0000");
            card.setBalance(5000);
            card.setCardName(tfCardName.getText());
            card.setCardNumber(tfCardNumber.getText());
            card.setExpiryDate(tfExpiryDate.getText());
        }
        
        else {
            card = new CreditCard();
            card.setPin("0000");
            card.setBalance(5000);
            card.setCardName(tfCardName.getText());
            card.setCardNumber(tfCardNumber.getText());
            card.setExpiryDate(tfExpiryDate.getText());
            ((CreditCard)card).setCreditLimit(5000);
            ((CreditCard)card).setAmountCredited(0);
        }
        
        if(PaymentCard.isValidCard(card)) {
            User user = new User();
            if(tfEmail.getText() != null) {
                user.setEmailAddress(tfEmail.getText());
                user.setPaymentCard(card);
                if(card.persist() && user.persist()) {
                    ALERT.setAlertType(AlertType.INFORMATION);
                    ALERT.setHeaderText("Registration Successful");
                    ALERT.setContentText("Congratulations. Your registration was sucessful.");
                    ALERT.show();
                }
                else {
                    ALERT.setAlertType(AlertType.ERROR);
                    ALERT.setHeaderText("Registration Unsuccessful.");
                    ALERT.setContentText("There was an error during the registration process. Please try again later.");
                    ALERT.show();
                    clearTextFields();
                }
            }
            else {
                user.setEmailAddress("");
                user.setPaymentCard(card);
                if(card.persist() && user.persist()) {
                    ALERT.setAlertType(AlertType.INFORMATION);
                    ALERT.setHeaderText("Registration Successful");
                    ALERT.setContentText("Congratulations. Your registration was sucessful");
                    ALERT.show();
                    clearTextFields();
                }
                else {
                    ALERT.setAlertType(AlertType.ERROR);
                    ALERT.setHeaderText("Registration Unsuccessful.");
                    ALERT.setContentText("There was an error during the registration process. Please try again later.");
                    ALERT.show();
                }
                }
            } 
            else {
                ALERT.setAlertType(AlertType.ERROR);
                ALERT.setHeaderText("Invalid Card Number");
                ALERT.setContentText("The card number you have entered is not valid. Please enter a valid card number.");
                ALERT.show();
            }
    }
    
    /**
     * Clears the text fields
     */    
    private void clearTextFields() {
        tfCardName.clear();
        tfCardNumber.clear();
        tfEmail.clear();
        tfExpiryDate.clear();
    }
    
    /**
     * Checks if all necessary registration information is entered
     * @return true if all necessary information is entered, otherwise false
     */
    private boolean isFilledRegistrationForm() {
        return !tfCardName.getText().equals("") || !tfCardNumber.getText().equals("")
                     || !tfExpiryDate.getText().equals("");
    }
    
    @FXML
    /**
     * Navigates to the main menu
     */
    public void navigateToMainMenu() throws IOException {
        AnchorPane mainMenuRootPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/ui/MainMenu.fxml"));
        Scene scene = new Scene(mainMenuRootPane, 720, 600);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.setResizable(false);
        stage.show();
    }
}
    
    
    
