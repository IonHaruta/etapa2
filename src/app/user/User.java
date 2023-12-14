package app.user;

import app.Admin;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.utils.Enums;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class User {
    @Getter
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    @Setter
    private String type;
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    private final Player player;
    private final SearchBar searchBar;
    private boolean lastSearched;
    @Setter
    @Getter
    private boolean status = true;
    @Getter
    @Setter
    private String page = "HomePage";
    @Getter
    @Setter
    private boolean searchArtist = false;
    @Getter
    @Setter
    private String lastSearchedArtist;

    /**
     * Constructs a new User with the given parameters.
     *
     * @param username The username of the user.
     * @param age The age of the user.
     * @param city The city where the user is located.
     * @param type The type of user (e.g., "artist" or "listener").
     */
    public User(final String username, final int age,
                final String city, final String type) {
        this.username = username;
        this.age = age;
        this.city = city;
        this.type = type;
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
    }
    /**
     * Checks if the user is an artist.
     *
     * @return True if the user is an artist, false otherwise.
     */
    public boolean isArtist() {
        return false;
    }
    /**
     * Performs a search based on the provided filters and type.
     *
     * @param filters The filters to apply to the search.
     * @param searchType The type of search (e.g., "song" or "artist").
     * @return A list of search results.
     */
    public ArrayList<String> search(final Filters filters, final String searchType) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        ArrayList<String> results = new ArrayList<>();
        if (searchType.equals("artist")) {
            for (Artist artist : Admin.getArtists()) {
                if (artist.getUsername().startsWith(filters.getName())) {
                    results.add(artist.getUsername());
                    searchArtist = true;
                    lastSearchedArtist = artist.getUsername();
                    page = "ArtistPage";
                }
            }
        } else {
            List<LibraryEntry> libraryEntries = searchBar.search(filters, searchType);
            for (LibraryEntry libraryEntry : libraryEntries) {
                results.add(libraryEntry.getName());
            }
        }

        return results;
    }

    /**
     * Selects an item from the search results.
     *
     * @param itemNumber The number of the item to select.
     * @return A message indicating the success or failure of the selection.
     */
    public String select(final int itemNumber) {
        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        LibraryEntry selected = searchBar.select(itemNumber);

        if (!searchArtist) {
            if (selected == null) {
                return "The selected ID is too high.";
            }
            return "Successfully selected %s.".formatted(selected.getName());
        }
        return "Successfully selected %s's page.".formatted(lastSearchedArtist);
    }
    /**
     * Loads the selected source for playback.
     *
     * @return A message indicating the success or failure of the load operation.
     */
    public String load() {
        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }
        if (!searchBar.getLastSearchType().equals("song")
                && ((AudioCollection) searchBar.getLastSelected()).
                        getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }
    /**
     * Pauses or resumes playback.
     *
     * @return A message indicating the success or failure of the operation.
     */
    public String playPause() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }
        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }
    /**
     * Sets the repeat mode for playback.
     *
     * @return A message indicating the new repeat mode.
     */
    public String repeat() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }
        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch (repeatMode) {
            case NO_REPEAT:
                repeatStatus = "no repeat";
                break;
            case REPEAT_ONCE:
                repeatStatus = "repeat once";
                break;
            case REPEAT_ALL:
                repeatStatus = "repeat all";
                break;
            case REPEAT_INFINITE:
                repeatStatus = "repeat infinite";
                break;
            case REPEAT_CURRENT_SONG:
                repeatStatus = "repeat current song";
                break;
            default:
                repeatStatus = "ERROR";
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }
    /**
     * Activates or deactivates the shuffle function.
     *
     * @param seed The seed for shuffle (if applicable).
     * @return A message indicating the success or failure of the shuffle operation.
     */
    public String shuffle(final Integer seed) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }
        if (!player.getType().equals("playlist")) {
            return "The loaded source is not a playlist.";
        }
        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }
    /**
     * Skips to the next track in a podcast.
     *
     * @return A message indicating the success or failure of the skip operation.
     */
    public String forward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }
    /**
     * Rewinds to the previous track in a podcast.
     *
     * @return A message indicating the success or failure of the rewind operation.
     */
    public String backward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }
    /**
     * Likes or unlikes the currently loaded song or playlist.
     *
     * @return A message indicating the success or failure of the like operation.
     */
    public String like() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType().equals("playlist")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }
    /**
     * Skips to the next track in the current source.
     *
     * @return A message indicating the success or failure of the skip operation.
     */
    public String next() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }
    /**
     * Returns to the previous track in the current source.
     *
     * @return A message indicating the success or failure of the return operation.
     */
    public String prev() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s.".
                formatted(player.getCurrentAudioFile().getName());
    }
    /**
     * Creates a new playlist with the given name.
     *
     * @param name The name of the new playlist.
     * @param timestamp The timestamp of playlist creation.
     * @return A message indicating the success or failure of the playlist creation.
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }
        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }
    /**
     * Adds or removes the currently loaded song from a playlist.
     *
     * @param Id The ID of the playlist.
     * @return A message indicating the success or failure of the add/remove operation.
     */
    public String addRemoveInPlaylist(final int Id) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }
        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }
        if (Id > playlists.size()) {
            return "The specified playlist does not exist.";
        }
        Playlist playlist = playlists.get(Id - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }
    /**
     * Switches the visibility status of a playlist.
     *
     * @param playlistId The ID of the playlist.
     * @return A message indicating the success or failure of the visibility switch.
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }
        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }
    /**
     * Shows a list of playlists associated with the user.
     *
     * @return A list of PlaylistOutput objects.
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }
    /**
     * Follows or unfollows a playlist.
     *
     * @return A message indicating the success or failure of the follow/unfollow operation.
     */
    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String searchType = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }
        if (!searchType.equals("playlist")) {
            return "The selected source is not a playlist.";
        }
        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(username)) {
            return "You cannot follow or unfollow your own playlist.";
        }
        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }
    /**
     * Retrieves the player statistics for the user.
     *
     * @return PlayerStats object containing player statistics.
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }
    /**
     * Shows a list of preferred songs liked by the user.
     *
     * @return A list of song names.
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }
    /**
     * Retrieves the preferred genre of the user based on liked songs.
     *
     * @return A message indicating the user's preferred genre.
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }
    /**
     * Simulates the passage of time for the user's playback status.
     *
     * @param time The amount of simulated time to advance.
     */
    public void simulateTime(final int time) {
        if (status) {
            player.simulatePlayer(time);
        }
    }
    /**
     * Switches the connection status of the user.
     *
     * @return A message indicating the success or failure of the status switch.
     */
    public String switchConnectionStatus() {
        if (status) {
            status = false;
        } else {
            status = true;
        }
        return " has changed status successfully.";
    }
}
