package main;

import database.*;
import disc.*;
import javafx.scene.image.*;
import java.sql.*;
import java.util.*;
import java.time.*;

/**
 * Models a movie
 */
import database.Retrievable;
public class Movie implements Retrievable, Persistable {

    private String id;
    private String title;
    private String actors;
    private String director;
    private double rating;
    private int lengthInMinutes;
    private List<Disc> discs;
    private MovieGenre genre;
    private boolean available;
    private LocalDate releaseDate;
    private ImageView image;
    private String pathToImageView;
    private String description;
    private double rentalPrice;

    /**
     * Constructs a movie
     */
    public Movie() {

    }

    /**
     * Constructs a movie with a given title, actors, director, rating, length, discs, genre, availability,
     * release date, image and path to image
     * @param title the title of the movie
     * @param actors the actors of the movie
     * @param director the director of the movie
     * @param rating the rating of the movie
     * @param lengthInMinutes the length of the movie in minutes
     * @param discs the discs associated with the movie
     * @param genre the genre of the movie
     * @param available the availability of the movie, true if available and false otherwise
     * @param releaseDate the release date of the movie
     * @param image the cover image associated with the movie
     * @param pathToImageView the path to the cover image of the movie
     * @param description the description of the movie
     */
    public Movie(String id, String title, String actors, String director, double rating,
                 int lengthInMinutes, List<Disc> discs, MovieGenre genre, boolean available,
                 LocalDate releaseDate, ImageView image, String pathToImageView, String description,
                 double rentalPrice) {
        this.id = id;
        this.title = title;
        this.actors = actors;
        this.director = director;
        this.rating = rating;
        this.lengthInMinutes = lengthInMinutes;
        this.discs = discs;
        this.genre = genre;
        this.available = available;
        this.releaseDate = releaseDate;
        this.image = image;
        this.pathToImageView = pathToImageView;
        this.description = description;
        this.rentalPrice = rentalPrice;
    }

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public List<Disc> getDiscs() {
        return discs;
    }

    public void setDiscs(List<Disc> discs) {
        this.discs = discs;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getPathToImage() {
        return pathToImageView;
    }

    public void setPathToImage(String pathToImageView) {
        this.pathToImageView = pathToImageView;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescrition(String description) {
        this.description = description;
    }
    
    public double getRentalPrice() {
        return this.rentalPrice;
    }
    
    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public static List<Movie> retrieveAll() {
        final String QUERY = "SELECT * FROM movie;";

        final List<Movie> retrievedMovies = new ArrayList<>();
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet retrievedMoviesRS = statement.executeQuery(QUERY);
            while(retrievedMoviesRS.next()) {
                Movie movie = new Movie();
                movie.setTitle(retrievedMoviesRS.getString("title"));
                movie.setDiscs(retrieveDiscsAssignedToMovie(retrievedMoviesRS.getString("id")));
                movie.setRating(retrievedMoviesRS.getDouble("rating"));
                movie.setActors(retrievedMoviesRS.getString("actors"));
                movie.setDirector(retrievedMoviesRS.getString("director"));
                movie.setLengthInMinutes(retrievedMoviesRS.getInt("length_in_minutes"));
                movie.setGenre(getGenre(retrievedMoviesRS.getString("genre")));
                movie.setReleaseDate(LocalDate.parse(retrievedMoviesRS.getString("release_date")));
                movie.setAvailable(Boolean.getBoolean(retrievedMoviesRS.getString("available")));
                movie.setPathToImage(retrievedMoviesRS.getString("path_to_image"));
                movie.setImage(new ImageView(retrievedMoviesRS.getString("path_to_image")));
                movie.setDescrition(retrievedMoviesRS.getString("description"));
                movie.setRentalPrice(Double.parseDouble(retrievedMoviesRS.getString("rental_price")));
                retrievedMovies.add(movie);
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return retrievedMovies;
    }


    /**
     * Retrieves the discs that have been assigned to a movie
     * @param title the title of movie whose discs to retrieve
     * @return a list of discs
     */
    private static List<Disc> retrieveDiscsAssignedToMovie(String movieId) {
        final String QUERY = "SELECT * FROM disc LEFT JOIN movie_discs"
                + " ON movie_discs.disc_id = disc.id WHERE movie_discs.movie_id = '" + movieId + "';";
        final List<Disc> discsAssignedToMovie = new ArrayList<>();
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet discsAssignedToMovieRS = statement.executeQuery(QUERY);
            while(discsAssignedToMovieRS.next()) {
                Disc disc = new Disc();
                disc.setId(discsAssignedToMovieRS.getString("id"));
                disc.setDiscTag(retrieveDiscTag(discsAssignedToMovieRS.getInt("disc_tag_id")));
                discsAssignedToMovie.add(disc);
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return discsAssignedToMovie;
    }

    /**
     * Retrieves the ID associated with a movie from the database
     * @param title the title of the movie whose ID to retrieve
     * @return the id associated with title
     */
    private int retrieveMovieID(String title) {
        final String QUERY = "SELECT id FROM movie WHERE title = '" + title + "';";
        int id;
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet retrievedMovieRS = statement.executeQuery(QUERY);
            while(retrievedMovieRS.next()) {
                return retrievedMovieRS.getInt("id");
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * Retrieve the disc tag associated with an id
     * @param id the id associated with the disc tag
     * @return
     */
    private static DiscTag retrieveDiscTag(int id) {
        final String QUERY = "SELECT * FROM disc_tag WHERE id = '" + id + "';";
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet discTagsRS = statement.executeQuery(QUERY);
            if(discTagsRS.next()) {
                DiscTag discTag = new DiscTag();
                discTag.setDateRented(LocalDate.parse(discTagsRS.getString("date_rented")));
                discTag.setTimeRented(LocalTime.parse(discTagsRS.getString("time_rented")));
                discTag.setDateReturned(LocalDate.parse(discTagsRS.getString("date_returned")));
                discTag.setTimeReturned(LocalTime.parse(discTagsRS.getString("time_returned")));
                return discTag;
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Maps a genre name in text form to the MovieGenre enum
     * @param genre the name of the genre to map
     * @return the movie genre
     */
    private static MovieGenre getGenre(String genre) {
        switch(genre) {
            case "Action": return MovieGenre.ACTION;
            case "Adventure": return MovieGenre.ADVENTURE;
            case "Comedy": return MovieGenre.COMEDY;
            case "Crime and Mystery": return MovieGenre.CRIME_AND_MYSTERY;
            case "Fantasy": return MovieGenre.FANTASY;
            case "Historical": return MovieGenre.HISTORICAL;
            case "Horror": return MovieGenre.HORROR;
            case "Romance": return MovieGenre.ROMANCE;
            case "Satire": return MovieGenre.SATIRE;
            case "Science Fiction": return MovieGenre.SCIENCE_FICTION;
            case "Speculative": return MovieGenre.SPECULATIVE;
            case "Thriller": return MovieGenre.THRILLER;
            case "Western": return MovieGenre.WESTERN;
            case "Other": return MovieGenre.OTHER;
            case "Drama": return MovieGenre.DRAMA;
            case "Mystery": return MovieGenre.MYSTERY;
        }
        return null;
    }


    @Override
    public Retrievable retrieveOne(String columnName, String columnValue) {
         final String QUERY = "SELECT * FROM movie WHERE '" + columnName + ""
                 + " = '" + columnValue + "';";
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet retrievedMoviesRS = statement.executeQuery(QUERY);
            while(retrievedMoviesRS.next()) {
                Movie movie = new Movie();
                movie.setTitle(retrievedMoviesRS.getString("title"));
                movie.setDiscs(retrieveDiscsAssignedToMovie(retrievedMoviesRS.getString("id")));
                movie.setRating(retrievedMoviesRS.getDouble("rating"));
                movie.setActors(retrievedMoviesRS.getString("actors"));
                movie.setDirector(retrievedMoviesRS.getString("director"));
                movie.setLengthInMinutes(retrievedMoviesRS.getInt("length_in_minutes"));
                movie.setGenre(getGenre(retrievedMoviesRS.getString("genre")));
                movie.setReleaseDate(LocalDate.parse(retrievedMoviesRS.getString("release_date")));
                movie.setAvailable(Boolean.getBoolean(retrievedMoviesRS.getString("available")));
                movie.setPathToImage(retrievedMoviesRS.getString("path_to_image"));
                movie.setImage(new ImageView(retrievedMoviesRS.getString("path_to_image")));
                movie.setDescrition(retrievedMoviesRS.getString("description"));
                movie.setRentalPrice(Double.parseDouble(retrievedMoviesRS.getString("rental_price")));
                return movie;
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean persist() {
        final String QUERY = "INSERT INTO MOVIE VALUES("
                + "'" + getId() + "',"
                + " '" + getTitle() + "',"
                + " '" + getActors() + "',"
                + " '" + getDirector()+ "',"
                + " '" + getRating() + "',"
                + " '" + getLengthInMinutes() + "',"
                + " '" + getGenre() + "',"
                + " '" + isAvailable() + "',"
                + " '" + getReleaseDate() +"',"
                + " '" + getPathToImage() + "',"
                + " '" + getDescription() + "',"
                + " '" + getRentalPrice() + "')"
                + " ON DUPLICATE KEY UPDATE"
                + " available = '" + isAvailable() + "';";
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(QUERY);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
