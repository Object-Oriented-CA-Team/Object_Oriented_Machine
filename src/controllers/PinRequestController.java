
package controllers;

import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 *
 * Controller class for the PIN request view
 */
public class PinRequestController implements Initializable {
    
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnOK;

    @FXML
    private PasswordField pfPin;
    
    private static String pin;
    
    private static int pinAttemptsLeft = 3;
    
    private static final Alert ALERT = new Alert(Alert.AlertType.ERROR);

    public static void setPin(String pin) {
        PinRequestController.pin = pin;
    }
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        btnOK.setOnAction((event) -> {
            PaymentController.setPinCancelled(false);
            if(pin.equals(pfPin.getText())) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.close();
            }
            else {
                pinAttemptsLeft = pinAttemptsLeft - 1;
                ALERT.setAlertType(Alert.AlertType.ERROR);
                ALERT.setHeaderText("Incorrect PIN");
                ALERT.setContentText("You have entered an incorrect PIN. You have " + pinAttemptsLeft + " attempts left.");
                ALERT.show();
                
                if(pinAttemptsLeft == 0) {
                    ALERT.setAlertType(Alert.AlertType.WARNING);
                    ALERT.setHeaderText("PIN attempts exhausted.");
//                    ALERT.setContentText("You have entered an incorrect PIN three times. Your card has been blocked.");
                    ALERT.getDialogPane().setContent( new Label("You have entered an incorrect PIN three times. Your card has been blocked."));
                    ALERT.show();
                    btnOK.setDisable(true);
                    PaymentController.setPinAttemptsExhausted();
                }
            }
        });
        
         btnCancel.setOnAction((event) -> {
             PaymentController.setPinCancelled(true);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        });
    }
    
    
    
}
