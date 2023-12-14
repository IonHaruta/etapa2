package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.User;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import fileio.input.CommandInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



public final class Admin {
    private static final int TOP_SONGS_LIMIT = 5;
    private static Admin instance;
    @Getter
    @Setter
    private static List<Artist> artists = new ArrayList<>();;
    private static List<User> users;
    private static List<Song> songs;
    private static List<Podcast> podcasts;
    @Getter
    private static List<Album> albums = new ArrayList<>();
    private static int timestamp;

    private Admin() {
        // private constructor to prevent external instantiation
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
    }

    /**
     * Gets the singleton instance of the Admin class.
     *
     * @return The Admin instance.
     */
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    /**
     * Adds songs to the list of available songs.
     *
     * @param songsInput The list of song inputs to be added.
     */
    public static void addSongs(final ArrayList<SongInput> songsInput) {
        for (SongInput song : songsInput) {
            songs.add(new Song(song.getName(), song.getDuration(),
                    song.getAlbum(), song.getTags(),
                    song.getLyrics(), song.getGenre(),
                    song.getReleaseYear(), song.getArtist()));
        }
    }
    /**
     * Sets the list of albums.
     *
     * @param albums The list of albums to be set.
     */
    public static void setAlbums(final List<Album> albums) {
        Admin.albums = albums;
    }
    /**
     * Sets the list of users.
     *
     * @param userInputList The list of user inputs to be set.
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(),
                    userInput.getAge(),
                    userInput.getCity(),
                    userInput.getType()));
        }
    }
    /**
     * Sets the list of songs.
     *
     * @param songInputList The list of song inputs to be set.
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(),
                    songInput.getDuration(),
                    songInput.getAlbum(),
                    songInput.getTags(),
                    songInput.getLyrics(),
                    songInput.getGenre(),
                    songInput.getReleaseYear(),
                    songInput.getArtist()));
        }
    }
    /**
     * Sets the list of podcasts.
     *
     * @param podcastInputList The list of podcast inputs to be set.
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                        episodeInput.getDuration(),
                        episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(),
                    podcastInput.getOwner(), episodes));
        }
    }
    /**
     * Gets the list of available songs.
     *
     * @return The list of songs.
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }
    /**
     * Gets the list of available podcasts.
     *
     * @return The list of podcasts.
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }
    /**
     * Gets the list of available playlists.
     *
     * @return The list of playlists.
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }
    /**
     * Gets a user based on the provided username.
     *
     * @param username The username of the user to be retrieved.
     * @return The user with the specified username.
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Updates the timestamp and simulates time for all users.
     *
     * @param newTimestamp The new timestamp to be set.
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }
    /**
     * Gets the top 5 songs based on likes.
     *
     * @return The list of top 5 songs.
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= TOP_SONGS_LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }
    /**
     * Gets the top 5 playlists based on followers and timestamp.
     *
     * @return The list of top 5 playlists.
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp,
                        Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= TOP_SONGS_LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }
    /**
     * Resets all data.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
    }
    /**
     * Gets the list of online users.
     *
     * @return The list of online users.
     */
    public static ArrayList<String> getOnlineUsers() {
        ArrayList<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isStatus() && user.getType() == null) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }
    /**
     * Adds a new user to the system.
     *
     * @param username The username of the new user.
     * @param age      The age of the new user.
     * @param city     The city of the new user.
     * @param type     The type of the new user
     *                 ("artist" or null for regular users).
     * @return A message indicating the success or failure of the operation.
     */
    public static String addUser(final String username,
                                 final Integer age,
                                 final String city, final String type) {
        User user = getUser(username);
        if (user != null && users.contains(user)) {
            return "The username " + user.getUsername() + " is already taken.";
        } else {
            User newUser;
            if (type.equals("artist")) {
                newUser = new Artist(username, age, city, type);
                artists.add(new Artist(username, age, city, type));
            } else {
                newUser = new User(username, age, city, type);
            }

            users.add(newUser);
            return "The username "
                    + newUser.getUsername()
                    + " has been added successfully.";
        }
    }
    /**
     * Shows the albums associated with a user.
     *
     * @param commandInput The command input containing the username.
     * @return The list of album names associated with the user.
     */
    public static List<String> showAlbums(final CommandInput commandInput) {
        Artist artist = (Artist) getUser(commandInput.getUsername());
        List<String> allSongs = new ArrayList<>();
        for (Album album : artist.getAlbums()) {
            if (artist.getUsername().equals(album.getOwner())) {
                for (SongInput song : album.getSongs()) {
                    allSongs.add(song.getName());
                }
            }
        }
        return allSongs;
    }
}
