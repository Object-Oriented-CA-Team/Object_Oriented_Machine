/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import disc.Disc;
import disc.DiscHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.Movie;

/**
 *
 * @author MTH
 */
public class DiscReturnController implements Initializable {
    
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnGoBack;

    @FXML
    private TextField tfDiscId;

    @FXML
    private Button btnReturnDisc;
    
    private static final Alert ALERT = new Alert(AlertType.INFORMATION);
    
//    private DiscHandler discHandler = DiscHandler.se
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    /**
     * Accepts a returned disc
     */
    public void acceptReturnedDisc() throws IOException {
        Disc disc = new Disc();
        disc.setId(tfDiscId.getText());
        disc = (Disc) disc.retrieveOne("id", disc.getId());
        
        if(disc.getId().equals("-1")) {
            ALERT.setAlertType(AlertType.ERROR);
            ALERT.setHeaderText("Unrecognized disc.");
            ALERT.setContentText("This disc does not appear to belong to us."
                    + " Ensure that you have entered the correct disc ID and that the"
                    + " disc is correctly inserted.");
            ALERT.show();
            return;
        }
        
        double additionalCost = DiscHandler.acceptReturnedDisc(disc);
        
        if(additionalCost > 0) {
           ALERT.setAlertType(AlertType.INFORMATION);
           ALERT.setHeaderText("Pay additional Costs");
           ALERT.setContentText("Looks like you kept the disc for more than a"
                   + "You will be required to pay additional costs. You will now be "
                   + "redirected to the payment section.");
           ALERT.showAndWait();
           Movie movie = new Movie();
           movie.setRentalPrice(additionalCost);
           movie.setTitle("Extra Costs on borrowed movie");
           MovieCartController.addToCart(movie);
           
           navigateToPayment();
           
           
        }
        
        else {
            ALERT.setAlertType(AlertType.INFORMATION);
            ALERT.setHeaderText("Disc return successful");
            ALERT.setContentText("You have successfully the disc. Thank you");
            ALERT.show();
        }
        
        
        
        
    }
    
    /**
     * Navigates to the payment UI
     * throws IOException if the view file cannot be loaded
     */
    public void navigateToPayment() throws IOException {
        AnchorPane mainMenuRootPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/ui/CardPayment.fxml"));
        Scene scene = new Scene(mainMenuRootPane, 720, 600);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Card Payment");
        stage.setResizable(false);
        stage.show();
    }
    
     @FXML
    /**
     * Navigates to the main menu
     * @throws IOException if the view file cannot be loaded
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
