package org.example;

import com.google.protobuf.InvalidProtocolBufferException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    // Simulating movie storage
    private static List<Movie> movieDatabase = new ArrayList<>();

    public static void main(String[] args) throws InvalidProtocolBufferException {
        // Simulate sending a new movie from the client (AddMovie RPC)
        byte[] addMovieRequest = createMovieRequest();
        addMovie(addMovieRequest);

        // Simulate retrieving a movie by title (GetMovie RPC)
        byte[] getMovieRequest = createMovieGetRequest("Inception");
        getMovie(getMovieRequest);
    }

    // Simulating AddMovie RPC (Server Side)
    private static void addMovie(byte[] requestData) throws InvalidProtocolBufferException {
        // Deserialize the request data to get the Movie object
        Movie movie = Movie.parseFrom(requestData);

        // Add the movie to the "database"
        movieDatabase.add(movie);  // Fixed: Adding movie to movieDatabase, not to Movie object itself
        System.out.println("Movie added: " + movie.getTitle());

        // Response
        Response response = Response.newBuilder()
                .setSuccess(true)
                .setMessage("Movie added successfully!")
                .build();
        System.out.println(response.getMessage());
    }

    // Simulating GetMovie RPC (Server Side)
    private static void getMovie(byte[] requestData) throws InvalidProtocolBufferException {
        // Deserialize the request data to get the MovieRequest
        MovieRequest movieRequest = MovieRequest.parseFrom(requestData);
        String requestedTitle = movieRequest.getTitle();

        // Search for the movie in the "database"
        for (Movie movie : movieDatabase) {  // Fixed: Iterating over movieDatabase
            if (movie.getTitle().equals(requestedTitle)) {
                System.out.println("Movie found:");
                printMovieDetails(movie);
                return;
            }
        }

        // Movie not found
        System.out.println("Movie not found: " + requestedTitle);
    }

    // Helper method to create a Movie request (Client Side)
    private static byte[] createMovieRequest() {
        // Create a cast list
        CastMember castMember1 = CastMember.newBuilder()
                .setActorName("Leonardo DiCaprio")
                .setRole("Cobb")
                .build();

        CastMember castMember2 = CastMember.newBuilder()
                .setActorName("Joseph Gordon-Levitt")
                .setRole("Arthur")
                .build();

        // Create a Rating
        Rating rating = Rating.newBuilder()
                .setScore(8.8f)
                .setSource("IMDb")
                .build();

        // Create a Movie
        Movie movie = Movie.newBuilder()
                .setTitle("Inception")
                .setDirector("Christopher Nolan")
                .setReleaseYear(2010)
                .setGenre(Genre.SCIFI)
                .addCast(castMember1)
                .addCast(castMember2)
                .setRating(rating)
                .build();

        // Serialize the movie to a byte array (this simulates sending the request)
        return movie.toByteArray();
    }

    // Helper method to create a MovieRequest to get a movie by title (Client Side)
    private static byte[] createMovieGetRequest(String title) {
        MovieRequest request = MovieRequest.newBuilder()
                .setTitle(title)
                .build();

        return request.toByteArray();
    }

    // Helper method to print movie details
    private static void printMovieDetails(Movie movie) {
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Director: " + movie.getDirector());
        System.out.println("Release Year: " + movie.getReleaseYear());
        System.out.println("Genre: " + movie.getGenre());
        System.out.println("Rating: " + movie.getRating().getScore() + " from " + movie.getRating().getSource());
        System.out.println("Cast:");
        movie.getCastList().forEach(cast -> System.out.println(" - " + cast.getActorName() + " as " + cast.getRole()));
    }
}
