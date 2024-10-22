package org.example;

import com.google.protobuf.InvalidProtocolBufferException;

public class Main {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        // Simulate sending a Movies object
        byte[] msg = sender();

        // Simulate receiving the Movies object
        receiver(msg);
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
//        for (int i = 0; i < msg.length; i++) {
//            System.out.print(msg[i] + " ");
//        }
//        System.out.println();
//        System.out.println("Byte array length: " + msg.length);
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
//        for (int i = 0; i < msg.length; i++) {
//            System.out.print(msg[i] + " ");
//        }
//        System.out.println();
//        System.out.println("Byte array length: " + msg.length);
//
//        // Deserialize the byte array back into a Movies object
//        Movies movies = Movies.parseFrom(msg);
//
//        // Print out the list of movies
//        System.out.println("Deserialized Movies List:");
//        System.out.println(movies.getMoviesList());

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
