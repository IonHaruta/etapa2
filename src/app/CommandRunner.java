package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Files.Song;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.Artist;
import app.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.List;



public final class CommandRunner {
    final static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * Performs a search based on the provided command input.
     *
     * @param commandInput The input containing the search parameters.
     * @return An object node containing the search results and message.
     */
    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();
        ArrayList<String> results;
        String message;
        if (user.isStatus()) {
            results = user.search(filters, type);
            message = "Search returned " + results.size() + " results";
        } else {
            results = new ArrayList<>();
            message = user.getUsername() + " is offline.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        objectNode.put("results", objectMapper.valueToTree(results));

        return objectNode;
    }
    /**
     * Selects an item based on the provided command input.
     *
     * @param commandInput The input containing the item selection parameters.
     * @return An object node containing the selection message.
     */
    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        String message = user.select(commandInput.getItemNumber());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Loads user data based on the provided command input.
     *
     * @param commandInput The input containing the load parameters.
     * @return An object node containing the load message.
     */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.load();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Toggles the play/pause state for the user's current playback.
     *
     * @param commandInput The input containing the command details.
     * @return An object node containing the command, user, timestamp, and a message
     *         indicating the success or failure of the play/pause operation.
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.playPause();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Toggles the repeat state for the user's current playback.
     *
     * @param commandInput The input containing the command details.
     * @return An object node containing the command, user, timestamp, and a message
     *         indicating the success or failure of the repeat operation.
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.repeat();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Shuffles the user's current playlist with the given seed for randomness.
     *
     * @param commandInput The input containing the command details, including the shuffle seed.
     * @return An object node containing the command, user, timestamp, and a message
     *         indicating the success or failure of the shuffle operation.
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();
        String message = user.shuffle(seed);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Skips to the next song in the user's current playlist.
     *
     * @param commandInput The input containing the command details.
     * @return An object node containing the command, user, timestamp, and a message
     *         indicating the success or failure of the forward operation.
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.forward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Skips to the previous song in the user's current playlist.
     *
     * @param commandInput The input containing the command details.
     * @return An object node containing the command, user, timestamp, and a message
     *         indicating the success or failure of the backward operation.
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.backward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Likes the currently playing song if the user is online.
     *
     * @param commandInput The input containing the command details.
     * @return An object node containing the command, user, timestamp, and a message
     *         indicating the success or failure of the like operation.
     */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user.isStatus()) {
            message = user.like();
        } else {
            message = user.getUsername() + " is offline.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Skips to the next song in the user's playlist if available.
     *
     * @param commandInput The input containing the command details.
     * @return An object node containing the command, user, timestamp, and a message
     *         indicating the success or failure of skipping to the next song.
     */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.next();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Skips to the previous song in the user's playlist if available.
     *
     * @param commandInput The input containing the command details.
     * @return An object node containing the command, user, timestamp, and a message
     *         indicating the success or failure of skipping to the previous song.
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.prev();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Creates a new playlist for the user based on the provided command input.
     *
     * @param commandInput The input containing the details for creating a playlist.
     * @return An object node containing the command, user, timestamp, and a message
     *         indicating the success or failure of the playlist creation.
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.createPlaylist(commandInput.getPlaylistName(),
                commandInput.getTimestamp());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Adds or removes items from a playlist based on the provided command input.
     *
     * @param commandInput The input containing the details
     *                     for adding or removing items
     *                     from a playlist, including the playlist ID.
     * @return An object node containing the command, user, timestamp, and a message
     *         indicating the success or failure of the operation.
     */

    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Switches the visibility of a playlist based on the provided command input.
     *
     * @param commandInput The input containing the details for switching the visibility
     *                     of a playlist, including the playlist ID.
     * @return An object node containing the command, user, timestamp, and a message
     *         indicating the success or failure of the operation.
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Retrieves and displays the playlists of a user based on the provided command input.
     *
     * @param commandInput The input containing the details
     *                    for retrieving and displaying playlists.
     * @return An object node containing the command, user,
     * timestamp, and a list of playlists
     *         belonging to the user.
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }
    /**
     * Initiates the process of following an entity (e.g., artist, user)
     * based on the provided command input.
     *
     * @param commandInput The input containing the details for
     *                     initiating the follow process.
     * @return An object node containing the command, user,
     * timestamp, and a message indicating
     *         the result of the follow operation.
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.follow();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Retrieves and provides the current status and statistics of
     * the user's player based on the
     * provided command input.
     *
     * @param commandInput The input containing the details for
     *                     retrieving player status and statistics.
     * @return An object node containing the command, user,
     * timestamp, and player statistics.
     */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }
    /**
     * Retrieves and provides a list of songs that the user
     * has marked as liked (preferred).
     *
     * @param commandInput The input containing the details
     *                     for retrieving the liked songs.
     * @return An object node containing the command, user,
     * timestamp, and a list of liked songs.
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }
    /**
     * Retrieves and provides the preferred genre of the specified user.
     *
     * @param commandInput The input containing the details
     *                     for retrieving the preferred genre.
     * @return An object node containing the command, user,
     * timestamp, and the preferred genre.
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    /**
     * Retrieves and provides a list of the top 5 songs based on some criteria.
     *
     * @param commandInput The input containing the details
     *                     for retrieving the top 5 songs.
     * @return An object node containing the command, timestamp,
     * and the result which is a list of top 5 songs.
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }
    /**
     * Retrieves and provides a list of the top 5 playlists based on some criteria.
     *
     * @param commandInput The input containing the details for
     *                     retrieving the top 5 playlists.
     * @return An object node containing the command, timestamp,
     * and the result which is a list of top 5 playlists.
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }
    /**
     * Switches the connection status of a user (online/offline) and
     * returns a message indicating the status change.
     *
     * @param commandInput The input containing the username and details
     *                    for the connection status switch.
     * @return An object node containing the command, timestamp, user,
     * and a message indicating the connection status change.
     */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user != null) {
            message = commandInput.getUsername() + user.switchConnectionStatus();
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Retrieves a list of online users and returns an object
     * node containing the command,
     * timestamp, and the list of online users.
     *
     * @param commandInput The input containing the command details.
     * @return An object node containing the command, timestamp,
     * and a list of online users.
     */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        List<String> onlineUsers = Admin.getOnlineUsers();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(onlineUsers));

        return objectNode;
    }
    /**
     * Adds a new user based on the provided command input, including username,
     * age, city, and type.
     * Returns an object node containing the command, timestamp, username,
     * and a message indicating the success or failure of the operation.
     *
     * @param commandInput The input containing the details for adding a new user.
     * @return An object node containing the command, timestamp, username,
     * and a message indicating the success or failure.
     */
    public static ObjectNode addUsers(final CommandInput commandInput) {
        String message = Admin.addUser(commandInput.getUsername(),
                commandInput.getAge(), commandInput.getCity(),
                commandInput.getType());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("message", message);

        return objectNode;
    }
    /**
     * Adds an album based on the provided command input.
     * The operation is performed by an artist user.
     * Returns an object node containing the command, timestamp, username,
     * and a message indicating the success or failure of the operation.
     *
     * @param commandInput The input containing details for adding an album,
     *                    including the username, album name, and songs.
     * @return An object node containing the command, timestamp, username,
     * and a message indicating the success or failure.
     */
    public static ObjectNode addAlbums(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user != null) {
            if (user.getType().equals("artist")) {
                Artist artist = (Artist) user;
                message = artist.addAlbum(commandInput);
            } else {
                message = commandInput.getUsername() + " is not an artist.";
            }
        } else {
            message = "The username " + commandInput.getName() + " doesn't exist.";
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }
    /**
     * Retrieves and displays the albums of an artist user based
     * on the provided command input.
     * Returns an object node containing the command, timestamp, username,
     * and an array of albums with their associated songs.
     *
     * @param commandInput The input containing details for showing the albums,
     *                    including the username.
     * @return An object node containing the command, timestamp, username,
     * and an array of albums with their associated songs.
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        Artist artist = (Artist) Admin.getUser(commandInput.getUsername());
        //List<String> showAlbums = Admin.showAlbums(commandInput);
        ArrayNode result = objectMapper.createArrayNode();
        for (Album album : artist.getAlbums()) {
            ObjectNode albums = objectMapper.createObjectNode();
            ArrayNode songs = objectMapper.createArrayNode();
            for (SongInput song : album.getSongs()) {
                songs.add(song.getName());
            }
            albums.put("name", album.getName());
            albums.put("songs", songs);
            result.add(albums);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("result", objectMapper.valueToTree(result));

        return objectNode;
    }
    /**
     * Retrieves and displays information about the current page for the specified
     * user based on the provided command input.
     * Returns an object node containing the command, timestamp, username,
     * and a message with details about the current page.
     *
     * @param commandInput The input containing details for printing the
     *                    current page, including the username.
     * @return An object node containing the command, timestamp, username,
     * and a message with details about the current page.
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = " ";
        switch (user.getPage()) {
            case "HomePage":
                int countS = 0;
                int countPL = 0;
                message = "Liked songs:\n\t[";
                for (Song song : user.getLikedSongs()) {
                    message += song.getName() + ", ";
                    countS++;
                }
                if (countS != 0) {
                    message = message.substring(0, message.length() - 2);
                }
                message += "]\n\nFollowed playlists:\n\t[";
                for (Playlist playlist : user.getPlaylists()) {
                    message += playlist.getName() + ", ";
                    countPL++;
                }
                if (countPL != 0) {
                    message = message.substring(0, message.length() - 2);
                }
                message += "]";
                break;
            case "LikedContentPage":
                break;
            case "ArtistPage":
                int countAlbum = 0;
                int countMerch = 0;
                message = "Albums:\n\t[";
                Artist artist = (Artist) Admin.getUser(user.getLastSearchedArtist());
                for (Album album : artist.getAlbums()) {
                    message += album.getName() + ", ";
                    countAlbum++;
                }
                if (countAlbum != 0) {
                    message = message.substring(0, message.length() - 2);
                }
                message += "]\n\nMerch:\n\t[";

                if (countMerch != 0) {
                    message = message.substring(0, message.length() - 2);
                }
                message += "]\n\nEvent:\n\t[";

                break;
            case "HostPage":
                break;
            default:
                break;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("message", objectMapper.valueToTree(message));
        return objectNode;
    }
    /**
     * Adds an event for the specified artist user based on the provided command input.
     * Returns an object node containing the command, timestamp, username,
     * and a message indicating the result of the operation.
     *
     * @param commandInput The input containing details for adding an event,
     *                    including the username.
     * @return An object node containing the command, timestamp, username,
     * and a message indicating the result of the operation.
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user != null) {
            if (user.getType() != null && user.getType().equals("artist")) {
                Artist artist = (Artist) user;
                message = artist.addEvent(commandInput);
            } else {
                message = commandInput.getUsername() + " is not an artist.";
            }
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }
}
