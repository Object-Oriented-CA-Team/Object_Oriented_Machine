/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import main.Movie;

/**
 *
 * Controller class for the movie cart view
 */
public class MovieCartController implements Initializable {
    @FXML
    AnchorPane middlePane;
    
    @FXML
    private Button btnRent;
    
    @FXML
    private Button btnRedeem;
    
    @FXML
    private Button btnGoBack;
    
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnCheckout;

    @FXML
    private Button btnAddMovie;

    @FXML
    private Label anchorPaneCartDetails;

    @FXML
    private Text txtCartItemsNumber;
    
    @FXML
    private Button btnRemoveFromCart;
    
    @FXML
    private TableView<Movie> tvMovieCart;

    @FXML
    private TableColumn<Movie, String> tcMovieCover;

    @FXML
    private TableColumn<Movie, String> tcMovieTitle;

    @FXML
    private TableColumn<Movie, String> tcTotal;
    
    private static final ObservableList<Movie> MOVIE_CART = FXCollections.observableArrayList();
    
    public static void addToCart(Movie movie) {
        MOVIE_CART.add(movie);
    }
    
    public static ObservableList<Movie> getCart() {
        return MOVIE_CART;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        btnRemoveFromCart.setDisable(true);
        showMoviesInCart();
          
          txtCartItemsNumber.setText(MOVIE_CART.size() + " Item(s) in cart.");
          
           MOVIE_CART.addListener(((ListChangeListener) c-> {
              if(!MOVIE_CART.isEmpty())
                txtCartItemsNumber.setText(MOVIE_CART.size() + " Item(s) in cart.");
              else
                  txtCartItemsNumber.setText("0 Item(s) in cart.");
          }));
           
          tvMovieCart.getSelectionModel().selectedItemProperty().addListener((observable) -> {
              if(tvMovieCart.getSelectionModel().getSelectedItems().isEmpty()) {
                  
                  btnRemoveFromCart.setDisable(true);
              }
              else btnRemoveFromCart.setDisable(false);
          });
          
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
     * Navigates to movie details UI
     * @param movie the movie whose details to display
     * @throws IOException 
     */
    private void navigateToMovieDetails() throws IOException{
        AnchorPane movieDetailsRootPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/ui/MovieDetails.fxml"));
        Scene scene = new Scene(movieDetailsRootPane, 720, 600);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Movie Details");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    /**
     * Removes a movie from the cart
     */
    public void removeFromCart() {
        List<Movie> selecetedMovies = tvMovieCart.getSelectionModel().getSelectedItems();
        tvMovieCart.getItems().removeAll(selecetedMovies);
    }
    
    /**
     * Loads the movies in the cart
     */
    private void showMoviesInCart() {
        
        tvMovieCart.setItems(MOVIE_CART);
        
        
        tcMovieCover.setPrefWidth(220);
        tcMovieCover.setCellValueFactory(new PropertyValueFactory<>("image"));
        
        tcMovieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        tcTotal.setCellValueFactory(new PropertyValueFactory<>("rentalPrice"));
     
    }
    
    @FXML
    /**
     * Navigates to the payment UI
     * 
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
    
}
