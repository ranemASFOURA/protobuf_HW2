package org.example;

import com.google.protobuf.InvalidProtocolBufferException;

public class Main {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        // Simulate sending a Movies object
        byte[] msg = sender();

        // Simulate receiving the Movies object
        receiver(msg);

        // Simulate adding a new movie
        Movie newMovie = Movie.newBuilder()
                .setTitle("Interstellar")
                .setDirector("Christopher Nolan")
                .setReleaseYear(2014)
                .setGenre(Genre.SCIFI)
                .addCast(CastMember.newBuilder()
                        .setActorName("Matthew McConaughey")
                        .setRole("Cooper")
                        .build())
                .setRating(Rating.newBuilder()
                        .setScore(8.6f)
                        .setSource("IMDb")
                        .build())
                .build();

        Response addResponse = addMovie(newMovie);
        System.out.println(addResponse.getMessage());

        // Simulate retrieving a movie by title
        MovieRequest request = MovieRequest.newBuilder()
                .setTitle("Inception")
                .build();

        Movie retrievedMovie = getMovie(request);
        printMovieDetails(retrievedMovie);

        // Simulate updating a movie
        Movie updatedMovie = Movie.newBuilder()
                .setTitle("Inception")
                .setDirector("Christopher Nolan")
                .setReleaseYear(2010)
                .setGenre(Genre.SCIFI)
                .addCast(CastMember.newBuilder()
                        .setActorName("Leonardo DiCaprio")
                        .setRole("Cobb")
                        .build())
                .setRating(Rating.newBuilder()
                        .setScore(9.0f)
                        .setSource("IMDb")
                        .build())
                .build();

        Response updateResponse = updateMovie(updatedMovie);
        System.out.println(updateResponse.getMessage());

        // Simulate deleting a movie
        Response deleteResponse = deleteMovie(request);
        System.out.println(deleteResponse.getMessage());
    }

    // Method to simulate sending a Movies object
    private static byte[] sender() {
        // Create cast members for Inception
        CastMember castMember1 = CastMember.newBuilder()
                .setActorName("Leonardo DiCaprio")
                .setRole("Cobb")
                .build();

        CastMember castMember2 = CastMember.newBuilder()
                .setActorName("Joseph Gordon-Levitt")
                .setRole("Arthur")
                .build();

        // Create a rating for Inception
        Rating rating1 = Rating.newBuilder()
                .setScore(8.8f)
                .setSource("IMDb")
                .build();

        // Create the Inception movie object
        Movie movie1 = Movie.newBuilder()
                .setTitle("Inception")
                .setDirector("Christopher Nolan")
                .setReleaseYear(2010)
                .setGenre(Genre.SCIFI)
                .addCast(castMember1)
                .addCast(castMember2)
                .setRating(rating1)
                .build();

        // Create cast members for The Dark Knight
        CastMember castMember3 = CastMember.newBuilder()
                .setActorName("Christian Bale")
                .setRole("Bruce Wayne")
                .build();

        CastMember castMember4 = CastMember.newBuilder()
                .setActorName("Heath Ledger")
                .setRole("Joker")
                .build();

        // Create a rating for The Dark Knight
        Rating rating2 = Rating.newBuilder()
                .setScore(9.0f)
                .setSource("IMDb")
                .build();

        // Create The Dark Knight movie object
        Movie movie2 = Movie.newBuilder()
                .setTitle("The Dark Knight")
                .setDirector("Christopher Nolan")
                .setReleaseYear(2008)
                .setGenre(Genre.ACTION)
                .addCast(castMember3)
                .addCast(castMember4)
                .setRating(rating2)
                .build();

        // Create a Movies object that holds multiple movies
        Movies movies = Movies.newBuilder()
                .addMovies(movie1)
                .addMovies(movie2)
                .build();

        // Serialize the movies to a byte array
        byte[] msg = movies.toByteArray();

        // Print serialized message as byte array
        System.out.println("Serialized Movies (Byte Array):");
        System.out.print("[ ");
        for (byte b : msg) {
            System.out.print(b + " ");
        }
        System.out.print("]\n");

        return msg;
    }

    // Receiver method to deserialize the byte array back to a Movies object
    private static void receiver(byte[] msg) throws InvalidProtocolBufferException {
        System.out.println("Received Movies Data (Byte Array):");
        System.out.print("[ ");
        for (byte b : msg) {
            System.out.print(b + " ");
        }
        System.out.print("]\n");

        Movies movies = Movies.parseFrom(msg);

        System.out.println("Decoded Movies Details:");
        System.out.println("--------------------------");
        for (Movie movie : movies.getMoviesList()) {
            printMovieDetails(movie);
        }
    }

    // Method to simulate adding a new movie
    private static Response addMovie(Movie movie) {
        // In a real application, you would add the movie to a database
        System.out.println("Adding movie: " + movie.getTitle());
        return Response.newBuilder()
                .setSuccess(true)
                .setMessage("Movie '" + movie.getTitle() + "' added successfully.")
                .build();
    }

    // Method to retrieve a movie by title
    private static Movie getMovie(MovieRequest request) {
        // In a real application, you would retrieve the movie from a database
        System.out.println("Retrieving movie: " + request.getTitle());
        // Simulating retrieval of a movie
        return Movie.newBuilder()
                .setTitle(request.getTitle())
                .setDirector("Christopher Nolan")
                .setReleaseYear(2010)
                .setGenre(Genre.SCIFI)
                .setRating(Rating.newBuilder().setScore(8.8f).setSource("IMDb").build())
                .addCast(CastMember.newBuilder().setActorName("Leonardo DiCaprio").setRole("Cobb").build())
                .addCast(CastMember.newBuilder().setActorName("Joseph Gordon-Levitt").setRole("Arthur").build())
                .build();
    }

    // Method to update an existing movie
    private static Response updateMovie(Movie movie) {
        // In a real application, you would update the movie in a database
        System.out.println("Updating movie: " + movie.getTitle());
        return Response.newBuilder()
                .setSuccess(true)
                .setMessage("Movie '" + movie.getTitle() + "' updated successfully.")
                .build();
    }

    // Method to delete a movie by title
    private static Response deleteMovie(MovieRequest request) {
        // In a real application, you would delete the movie from a database
        System.out.println("Deleting movie: " + request.getTitle());
        return Response.newBuilder()
                .setSuccess(true)
                .setMessage("Movie '" + request.getTitle() + "' deleted successfully.")
                .build();
    }

    // Helper method to print movie details
    private static void printMovieDetails(Movie movie) {
        System.out.println("=== Movie Info ===");
        System.out.printf("Title      : %s%n", movie.getTitle());
        System.out.printf("Director   : %s%n", movie.getDirector());
        System.out.printf("Year       : %d%n", movie.getReleaseYear());
        System.out.printf("Genre      : %s%n", movie.getGenre());
        System.out.printf("Rating     : %.1f from %s%n", movie.getRating().getScore(), movie.getRating().getSource());
        System.out.println("Cast       : ");
        movie.getCastList().forEach(cast ->
                System.out.printf("  - %s as %s%n", cast.getActorName(), cast.getRole())
        );
        System.out.println("==========================");
    }
}
