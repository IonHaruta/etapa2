package app.searchBar;

import app.audio.LibraryEntry;

import java.util.ArrayList;
import java.util.List;

public final class FilterUtils {

    private FilterUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }
    /**
     * Filters a list of {@link LibraryEntry} objects by name.
     *
     * @param entries The list of library entries to filter.
     * @param name    The name to match against.
     * @return A list of library entries matching the specified name.
     */
    public static List<LibraryEntry> filterByName(final List<LibraryEntry> entries,
                                                  final String name) {
        List<LibraryEntry> result = new ArrayList<>();
        for (LibraryEntry entry : entries) {
            if (entry.matchesName(name)) {
                result.add(entry);
            }
        }
        return result;
    }
    /**
     * Filters a list of {@link LibraryEntry} objects by album.
     *
     * @param entries The list of library entries to filter.
     * @param album   The album to match against.
     * @return A list of library entries matching the specified album.
     */
    public static List<LibraryEntry> filterByAlbum(final List<LibraryEntry> entries,
                                                   final String album) {
        return filter(entries, entry -> entry.matchesAlbum(album));
    }
    /**
     * Filters a list of {@link LibraryEntry} objects by tags.
     *
     * @param entries The list of library entries to filter.
     * @param tags    The list of tags to match against.
     * @return A list of library entries matching the specified tags.
     */
    public static List<LibraryEntry> filterByTags(final List<LibraryEntry> entries,
                                                  final ArrayList<String> tags) {
        return filter(entries, entry -> entry.matchesTags(tags));
    }
    /**
     * Filters a list of {@link LibraryEntry} objects by lyrics.
     *
     * @param entries The list of library entries to filter.
     * @param lyrics  The lyrics to match against.
     * @return A list of library entries matching the specified lyrics.
     */
    public static List<LibraryEntry> filterByLyrics(final List<LibraryEntry> entries,
                                                    final String lyrics) {
        return filter(entries, entry -> entry.matchesLyrics(lyrics));
    }
    /**
     * Filters a list of {@link LibraryEntry} objects by genre.
     *
     * @param entries The list of library entries to filter.
     * @param genre   The genre to match against.
     * @return A list of library entries matching the specified genre.
     */
    public static List<LibraryEntry> filterByGenre(final List<LibraryEntry> entries,
                                                   final String genre) {
        return filter(entries, entry -> entry.matchesGenre(genre));
    }
    /**
     * Filters a list of {@link LibraryEntry} objects by artist.
     *
     * @param entries The list of library entries to filter.
     * @param artist  The artist to match against.
     * @return A list of library entries matching the specified artist.
     */
    public static List<LibraryEntry> filterByArtist(final List<LibraryEntry> entries,
                                                    final String artist) {
        return filter(entries, entry -> entry.matchesArtist(artist));
    }
    /**
     * Filters a list of {@link LibraryEntry} objects by release year.
     *
     * @param entries     The list of library entries to filter.
     * @param releaseYear The release year to match against.
     * @return A list of library entries matching the specified release year.
     */
    public static List<LibraryEntry> filterByReleaseYear(final List<LibraryEntry> entries,
                                                         final String releaseYear) {
        return filter(entries, entry -> entry.matchesReleaseYear(releaseYear));
    }
    /**
     * Filters a list of {@link LibraryEntry} objects by owner.
     *
     * @param entries The list of library entries to filter.
     * @param user    The owner to match against.
     * @return A list of library entries matching the specified owner.
     */
    public static List<LibraryEntry> filterByOwner(final List<LibraryEntry> entries,
                                                   final String user) {
        return filter(entries, entry -> entry.matchesOwner(user));
    }
    /**
     * Filters a list of {@link LibraryEntry} objects by playlist visibility for a user.
     *
     * @param entries The list of library entries to filter.
     * @param user    The user to check visibility for.
     * @return A list of library entries visible to the specified user.
     */
    public static List<LibraryEntry> filterByPlaylistVisibility(final List<LibraryEntry> entries,
                                                                final String user) {
        return filter(entries, entry -> entry.isVisibleToUser(user));
    }
    /**
     * Filters a list of {@link LibraryEntry} objects by followers.
     *
     * @param entries   The list of library entries to filter.
     * @param followers The followers to match against.
     * @return A list of library entries matching the specified followers.
     */
    public static List<LibraryEntry> filterByFollowers(final List<LibraryEntry> entries,
                                                       final String followers) {
        return filter(entries, entry -> entry.matchesFollowers(followers));
    }
    /**
     * Generic method to filter a list of {@link LibraryEntry}
     * objects based on a custom filter criteria.
     *
     * @param entries  The list of library entries to filter.
     * @param criteria The custom filter criteria.
     * @return A list of library entries that match the specified filter criteria.
     */
    private static List<LibraryEntry> filter(final List<LibraryEntry> entries,
                                             final FilterCriteria criteria) {
        List<LibraryEntry> result = new ArrayList<>();
        for (LibraryEntry entry : entries) {
            if (criteria.matches(entry)) {
                result.add(entry);
            }
        }
        return result;
    }

    @FunctionalInterface
    private interface FilterCriteria {
        boolean matches(LibraryEntry entry);
    }
}
