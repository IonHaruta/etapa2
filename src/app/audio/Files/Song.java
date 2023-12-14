package app.audio.Files;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Song extends AudioFile {
    private  String album;
    private  ArrayList<String> tags;
    private  String lyrics;
    private  String genre;
    private  Integer releaseYear;
    private  String artist;
    private Integer likes;



    public Song(final String name, final Integer duration) {
        super(name, duration);
    }

    public Song(final String name, final Integer duration,
                final String album, final ArrayList<String> tags,
                final String lyrics,
                final String genre, final Integer releaseYear,
                final String artist) {
        super(name, duration);
        this.album = album;
        this.tags = tags;
        this.lyrics = lyrics;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.artist = artist;
        this.likes = 0;
    }

    @Override
    public boolean matchesAlbum(final String albumMatched) {
        return this.getAlbum().equalsIgnoreCase(albumMatched);
    }

    @Override
    public boolean matchesTags(final ArrayList<String> tagsMatched) {
        List<String> songTags = new ArrayList<>();
        for (String tag : this.getTags()) {
            songTags.add(tag.toLowerCase());
        }

        for (String tag : tagsMatched) {
            if (!songTags.contains(tag.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean matchesLyrics(final String lyricsMatched) {
        return this.getLyrics().toLowerCase().contains(lyricsMatched.toLowerCase());
    }

    @Override
    public boolean matchesGenre(final String genreMatched) {
        return this.getGenre().equalsIgnoreCase(genreMatched);
    }

    @Override
    public boolean matchesArtist(final String artistMatched) {
        return this.getArtist().equalsIgnoreCase(artistMatched);
    }

    @Override
    public boolean matchesReleaseYear(final String releaseYearMatched) {
        return filterByYear(this.getReleaseYear(), releaseYearMatched);
    }

    private static boolean filterByYear(final int year, final String query) {
        if (query.startsWith("<")) {
            return year < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return year > Integer.parseInt(query.substring(1));
        } else {
            return year == Integer.parseInt(query);
        }
    }

    /**
     * Increments the like count for this entity.
     */
    public void like() {
        likes++;
    }
    /**
     * Decrements the like count for this entity.
     */
    public void dislike() {
        likes--;
    }
}
