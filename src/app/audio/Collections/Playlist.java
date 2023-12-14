package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public final class Playlist extends AudioCollection {
    private final ArrayList<Song> songs;
    private Enums.Visibility visibility;
    private Integer followers;
    private int timestamp;

    public Playlist(final String name, final String owner) {
        this(name, owner, 0);
    }

    public Playlist(final String name, final String owner, final int timestamp) {
        super(name, owner);
        this.songs = new ArrayList<>();
        this.visibility = Enums.Visibility.PUBLIC;
        this.followers = 0;
        this.timestamp = timestamp;
    }

    /**
     * Checks if the playlist contains the specified song.
     *
     * @param song The song to check for.
     * @return {@code true} if the playlist contains the song, {@code false} otherwise.
     */
    public boolean containsSong(final Song song) {
        return songs.contains(song);
    }
    /**
     * Adds the given song to the playlist.
     *
     * @param song The song to add.
     */
    public void addSong(final Song song) {
        songs.add(song);
    }
    /**
     * Removes the specified song from the playlist.
     *
     * @param song The song to remove.
     */
    public void removeSong(final Song song) {
        songs.remove(song);
    }
    /**
     * Removes the song at the specified index from the playlist.
     *
     * @param index The index of the song to remove.
     */
    public void removeSong(final int index) {
        songs.remove(index);
    }

    /**
     * Switches the visibility status of the playlist between public and private.
     * If the playlist is currently public, it becomes private, and vice versa.
     */
    public void switchVisibility() {
        if (visibility == Enums.Visibility.PUBLIC) {
            visibility = Enums.Visibility.PRIVATE;
        } else {
            visibility = Enums.Visibility.PUBLIC;
        }
    }
    /**
     * Increases the number of followers for the playlist by one.
     */
    public void increaseFollowers() {
        followers++;
    }
    /**
     * Decreases the number of followers for the playlist by one.
     */
    public void decreaseFollowers() {
        followers--;
    }
    /**
     * Returns the total number of tracks in the playlist.
     *
     * @return The number of tracks in the playlist.
     */
    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }
    /**
     * Gets the audio file at the specified index in the playlist.
     *
     * @param index The index of the track.
     * @return The audio file at the specified index.
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }
    /**
     * Checks if the playlist is visible to the given user based on its visibility status.
     * If the playlist is public or if the user is the owner, it is considered visible.
     *
     * @param user The username to check visibility for.
     * @return {@code true} if the playlist is visible to the user, {@code false} otherwise.
     */
    @Override
    public boolean isVisibleToUser(final String user) {
        return this.getVisibility() == Enums.Visibility.PUBLIC
                || (this.getVisibility() == Enums.Visibility.PRIVATE
                && this.getOwner().equals(user));
    }
    /**
     * Checks if the playlist's follower count matches the specified query.
     *
     * @param followersMatched The query to filter followers count
     *                         ("<", ">", or "=" followed by a number).
     * @return {@code true} if the follower count matches the query,
     * {@code false} otherwise.
     */
    @Override
    public boolean matchesFollowers(final String followersMatched) {
        return filterByFollowersCount(this.getFollowers(), followersMatched);
    }
    /**
     * Helper method to filter a count based on a query ("<", ">", or "=" followed by a number).
     *
     * @param count The count to filter.
     * @param query The query for filtering ("<", ">", or "=" followed by a number).
     * @return {@code true} if the count matches the query, {@code false} otherwise.
     */
    private static boolean filterByFollowersCount(final int count, final String query) {
        if (query.startsWith("<")) {
            return count < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return count > Integer.parseInt(query.substring(1));
        } else {
            return count == Integer.parseInt(query);
        }
    }
}
