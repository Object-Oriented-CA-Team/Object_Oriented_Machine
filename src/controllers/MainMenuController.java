/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import javafx.stage.Stage;


/**
 *
 * Controller class for the main menu view
 */
public class MainMenuController implements Initializable {
    
    @FXML
    AnchorPane rootPane;
    
    @FXML
    private Button btnRent;
    
    @FXML
    private Button btnReturn;
    
    @FXML
    private Button btnSignUp;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
          
    }
    
    @FXML
    /**
     * Navigates to the movie list UI
     */
    public void navigateToMovieList() throws IOException {
        AnchorPane movieListRootPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/ui/MovieList.fxml"));
        Scene scene = new Scene(movieListRootPane, 720, 600);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Movie List");
        stage.setResizable(false);
        stage.show();
        
    }
    
    @FXML
    /**
     * Navigates to the user registration UI
     */
    public void navigateToRegistration() throws IOException {
        AnchorPane registrationRootPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/ui/UserRegistration.fxml"));
        Scene scene = new Scene(registrationRootPane, 720, 600);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("User Registration");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    /**
     * Navigates to disc return UI
     */
    public void navigateToDiscReturn() throws IOException {
        AnchorPane registrationRootPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/ui/ReturnDisc.fxml"));
        Scene scene = new Scene(registrationRootPane, 720, 600);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Return Disc");
        stage.setResizable(false);
        stage.show();
    }
}
