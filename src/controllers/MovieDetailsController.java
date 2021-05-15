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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.*;
import main.Movie;

/**
 *
 * Controller class for the movie details view
 */
public class MovieDetailsController implements Initializable {
  
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnRent;

    @FXML
    private Text txtRating;

    @FXML
    private Text txtRentalFee;

    @FXML
    private Text txtReleaseDate;

    @FXML
    private Text txtLength;

    @FXML
    private Text txtDirector;

    @FXML
    private Text txtActors;

    @FXML
    private Text txtDescription;
    
    @FXML 
    private Text txtTitle;
    
    @FXML
    private ImageView imgViewCover;

    private static Movie movie;

    public static void setMovie(Movie movieToDisplay) {
        movie = movieToDisplay;
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        displayMovieDetails();
    }
    
    @FXML
    /**
     * Navigates to the movie cart UI
     */
    public void navigateToMovieCart() throws IOException {
        MovieCartController.addToCart(movie);
        AnchorPane movieCartrootPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/ui/MovieCart.fxml"));
        Scene scene = new Scene(movieCartrootPane, 720, 600);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Movie Cart");
        stage.setResizable(false);
        stage.show();
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
    
    /**
     *Displays movie details
     */
    private void displayMovieDetails() {
        txtRating.setText(movie.getRating() + "");
        txtActors.setText(movie.getActors());
        txtReleaseDate.setText(movie.getReleaseDate().toString());
        txtTitle.setText(movie.getTitle());
        txtDirector.setText(movie.getDirector());
        imgViewCover.setImage(movie.getImage().getImage());
        txtDescription.setText(movie.getDescription());
        txtLength.setText(movie.getLengthInMinutes() + " min");
        txtRentalFee.setText("€" + movie.getRentalPrice() + "");
    }
    
}
