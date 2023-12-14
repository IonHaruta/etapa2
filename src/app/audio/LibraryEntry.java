package app.audio;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class LibraryEntry {
    private final String name;

    /**
     * Constructor for LibraryEntry.
     *
     * @param name The name of the library entry.
     */
    public LibraryEntry(final String name) {
        this.name = name;
    }
    /**
     * Checks if the entry's name matches the specified name.
     *
     * @param queryName The name to check against.
     * @return True if there is a match, false otherwise.
     */
    public boolean matchesName(final String queryName) {
        return this.name.toLowerCase().startsWith(queryName.toLowerCase());
    }
    /**
     * Checks if the entry's album matches the specified album.
     *
     * @param album The album to check against.
     * @return True if there is a match, false otherwise.
     */
    public boolean matchesAlbum(final String album) {
        return false;
    }
    /**
     * Checks if the entry's tags match the specified tags.
     *
     * @param tags The tags to check against.
     * @return True if there is a match, false otherwise.
     */
    public boolean matchesTags(final ArrayList<String> tags) {
        return false;
    }
    /**
     * Checks if the entry's lyrics match the specified lyrics.
     *
     * @param lyrics The lyrics to check against.
     * @return True if there is a match, false otherwise.
     */
    public boolean matchesLyrics(final String lyrics) {
        return false;
    }
    /**
     * Checks if the entry's genre matches the specified genre.
     *
     * @param genre The genre to check against.
     * @return True if there is a match, false otherwise.
     */
    public boolean matchesGenre(final String genre) {
        return false;
    }
    /**
     * Checks if the entry's artist matches the specified artist.
     *
     * @param artist The artist to check against.
     * @return True if there is a match, false otherwise.
     */
    public boolean matchesArtist(final String artist) {
        return false;
    }
    /**
     * Checks if the entry's release year matches the specified release year.
     *
     * @param releaseYear The release year to check against.
     * @return True if there is a match, false otherwise.
     */
    public boolean matchesReleaseYear(final String releaseYear) {
        return false;
    }
    /**
     * Checks if the entry's owner matches the specified user.
     *
     * @param user The user to check against.
     * @return True if there is a match, false otherwise.
     */
    public boolean matchesOwner(final String user) {
        return false;
    }
    /**
     * Checks if the entry is visible to the specified user.
     *
     * @param user The user to check against.
     * @return True if visible to the user, false otherwise.
     */
    public boolean isVisibleToUser(final String user) {
        return false;
    }
    /**
     * Checks if the entry's followers match the specified followers.
     *
     * @param followers The followers to check against.
     * @return True if there is a match, false otherwise.
     */
    public boolean matchesFollowers(final String followers) {
        return false;
    }
}
