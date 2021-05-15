package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import main.Movie;
import javafx.scene.text.*;
import java.util.*;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 *
 * Controller class for the movie list view
 */
public class MovieListController implements Initializable {
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private AnchorPane navigationButtonsPane;
    
    @FXML
    private VBox vBoxMovieImages;

    @FXML
    private HBox hBoxMovieRow1;

    @FXML
    private VBox vBoxCoverAndTitle;

    @FXML
    private ImageView imgViewMovieCover;

    @FXML
    private HBox hBoxMovieRow2;

    @FXML
    private VBox vBoxCoverAndTitle1;
    
    @FXML
    private Label lblPageNumber;
    
    @FXML
    private ImageView imgViewNextPage;

    @FXML
    private ImageView imgViewPreviousPage;
    
    @FXML
    private Pagination paginationMovieList;
    
    @FXML
    private ScrollPane movieListPane;
    
    private static List<Movie> movieList;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
          movieList = Movie.retrieveAll();
          Collections.shuffle(movieList);
          loadMovies();
    }
    
    /**
     * Loads the movies starting at a particular index
     * @param startIndex the index at which to start loading the movies
     * @param endIndex the index (exclusive) at which to end loading the movies
     */
    private void loadMovies() {
        movieListPane.setContent(createMovieCoverPane());
    }
   
    /**
     * Navigates to movie details UI
     * @param movie the movie whose details to display
     * @throws IOException 
     */
    private void navigateToMovieDetails(Movie movie) throws IOException{
        MovieDetailsController.setMovie(movie);
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
    
    private HBox createMovieCoverPane() {
        
           HBox movieCoversPane = new HBox(20);
           movieCoversPane.setPrefWidth(720);
           movieCoversPane.setPrefHeight(392);
           movieCoversPane.setStyle("-fx-background-color:  #1d2740;");
           
           for (int i = 0; i < movieList.size(); i++) {
               int[] index = new int[1];
               index[0] = i;
               
               ImageView imgViewMovieCover = movieList.get(i).getImage();
               imgViewMovieCover.setFitWidth(205);
               imgViewMovieCover.setFitHeight(300);
               imgViewMovieCover.setSmooth(true);
               imgViewMovieCover.setLayoutX(200);
               imgViewMovieCover.setOnMouseReleased((event) -> {
                   try {
                       navigateToMovieDetails(movieList.get(index[0]));
                   } catch (IOException ex) {
                       ex.printStackTrace();
                   }
               });
               
      
              Text txtMovieTitle = new Text(movieList.get(i).getTitle());
              txtMovieTitle.setFont(Font.font("Berlin Sans FB", 14));
              txtMovieTitle.setTextAlignment(TextAlignment.LEFT);
              txtMovieTitle.setFill(Paint.valueOf("WHITE"));
              txtMovieTitle.setWrappingWidth(205);

              VBox vBoxCoverAndTitle = new VBox();
              vBoxCoverAndTitle.setSpacing(5);
              vBoxCoverAndTitle.getChildren().addAll(imgViewMovieCover, txtMovieTitle);
              
              movieCoversPane.getChildren().add(vBoxCoverAndTitle);
           }
           
           return movieCoversPane;
    }
    
    
}
