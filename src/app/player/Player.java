package app.player;

import app.audio.Collections.AudioCollection;
import app.audio.Files.AudioFile;
import app.audio.LibraryEntry;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public final class Player {
    private static final Integer SKIP_NUMBER_NEGATIVE = -90;
    private static final Integer SKIP_NUMBER = 90;
    private Enums.RepeatMode repeatMode;
    private boolean shuffle;
    private boolean paused;
    private PlayerSource source;
    @Getter
    private String type;

    private ArrayList<PodcastBookmark> bookmarks = new ArrayList<>();


    public Player() {
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.paused = true;
    }
    /**
     * Stops the player, bookmarks the current podcast if applicable, and resets player state.
     */
    public void stop() {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }

        repeatMode = Enums.RepeatMode.NO_REPEAT;
        paused = true;
        source = null;
        shuffle = false;
    }
    /**
     * Bookmarks the current podcast for later playback.
     */
    private void bookmarkPodcast() {
        if (source != null && source.getAudioFile() != null) {
            PodcastBookmark currentBookmark =
                    new PodcastBookmark(source.getAudioCollection().getName(),
                    source.getIndex(), source.getDuration());
            bookmarks.removeIf(bookmark -> bookmark.getName().equals(currentBookmark.getName()));
            bookmarks.add(currentBookmark);
        }
    }
    /**
     * Creates a player source based on the type of entry and initializes player state.
     *
     * @param type      The type of entry (e.g., "song", "playlist", "podcast").
     * @param entry     The library entry to be played.
     * @param bookmarks List of podcast bookmarks.
     * @return A PlayerSource representing the source of playback.
     */
    public static PlayerSource createSource(final String type,
                                            final LibraryEntry entry,
                                            final List<PodcastBookmark> bookmarks) {
        if ("song".equals(type)) {
            return new PlayerSource(Enums.PlayerSourceType.LIBRARY, (AudioFile) entry);
        } else if ("playlist".equals(type)) {
            return new PlayerSource(Enums.PlayerSourceType.PLAYLIST, (AudioCollection) entry);
        } else if ("podcast".equals(type)) {
            return createPodcastSource((AudioCollection) entry, bookmarks);
        }

        return null;
    }
    /**
     * Creates a podcast player source, considering existing bookmarks.
     *
     * @param collection The podcast collection to be played.
     * @param bookmarks  List of podcast bookmarks.
     * @return A PlayerSource for podcast playback.
     */
    private static PlayerSource createPodcastSource(final AudioCollection collection,
                                                    final List<PodcastBookmark> bookmarks) {
        for (PodcastBookmark bookmark : bookmarks) {
            if (bookmark.getName().equals(collection.getName())) {
                return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection, bookmark);
            }
        }
        return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection);
    }
    /**
     * Sets the current playback source and initializes player state.
     *
     * @param entry The library entry to be played.
     * @param typeSet  The type of entry (e.g., "song", "playlist", "podcast").
     */
    public void setSource(final LibraryEntry entry, final String typeSet) {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }

        this.type = typeSet;
        this.source = createSource(typeSet, entry, bookmarks);
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.shuffle = false;
        this.paused = true;
    }
    /**
     * Pauses or resumes playback.
     */
    public void pause() {
        paused = !paused;
    }
    /**
     * Activates or deactivates shuffle mode.
     *
     * @param seed Seed for shuffling (nullable).
     */
    public void shuffle(final Integer seed) {
        if (seed != null) {
            source.generateShuffleOrder(seed);
        }

        if (source.getType() == Enums.PlayerSourceType.PLAYLIST) {
            shuffle = !shuffle;
            if (shuffle) {
                source.updateShuffleIndex();
            }
        }
    }
    /**
     * Toggles between different repeat modes.
     *
     * @return The new repeat mode.
     */
    public Enums.RepeatMode repeat() {
        if (repeatMode == Enums.RepeatMode.NO_REPEAT) {
            if (source.getType() == Enums.PlayerSourceType.LIBRARY) {
                repeatMode = Enums.RepeatMode.REPEAT_ONCE;
            } else {
                repeatMode = Enums.RepeatMode.REPEAT_ALL;
            }
        } else {
            if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
                repeatMode = Enums.RepeatMode.REPEAT_INFINITE;
            } else {
                if (repeatMode == Enums.RepeatMode.REPEAT_ALL) {
                    repeatMode = Enums.RepeatMode.REPEAT_CURRENT_SONG;
                } else {
                    repeatMode = Enums.RepeatMode.NO_REPEAT;
                }
            }
        }

        return repeatMode;
    }
    /**
     * Simulates player playback for a specified time.
     *
     * @param time The simulated time in seconds.
     */
    public void simulatePlayer(int time) {
        if (!paused) {
            while (time >= source.getDuration()) {
                time -= source.getDuration();
                next();
                if (paused) {
                    break;
                }
            }
            if (!paused) {
                source.skip(-time);
            }
        }
    }
    /**
     * Skips to the next track based on repeat mode and shuffle status.
     */
    public void next() {
        paused = source.setNextAudioFile(repeatMode, shuffle);
        if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
            repeatMode = Enums.RepeatMode.NO_REPEAT;
        }

        if (source.getDuration() == 0 && paused) {
            stop();
        }
    }
    /**
     * Skips to the previous track.
     */
    public void prev() {
        source.setPrevAudioFile(shuffle);
        paused = false;
    }
    /**
     * Skips forward or backward in a podcast.
     *
     * @param duration The duration to skip (positive for skip forward, negative for skip backward).
     */
    private void skip(final int duration) {
        source.skip(duration);
        paused = false;
    }
    /**
     * Skips to the next podcast episode.
     */
    public void skipNext() {
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(SKIP_NUMBER_NEGATIVE);
        }
    }
    /**
     * Skips to the previous podcast episode.
     */
    public void skipPrev() {
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(SKIP_NUMBER);
        }
    }
    /**
     * Retrieves the current audio file being played.
     *
     * @return The current audio file.
     */
    public AudioFile getCurrentAudioFile() {
        if (source == null) {
            return null;
        }
        return source.getAudioFile();
    }
    /**
     * Gets the paused status of the player.
     *
     * @return True if paused, false if playing.
     */
    public boolean getPaused() {
        return paused;
    }
    /**
     * Gets the shuffle status of the player.
     *
     * @return True if shuffle is active, false otherwise.
     */
    public boolean getShuffle() {
        return shuffle;
    }
    /**
     * Retrieves player statistics, including filename, duration,
     * repeat mode, shuffle status, and paused status.
     *
     * @return PlayerStats object containing player statistics.
     */
    public PlayerStats getStats() {
        String filename = "";
        int duration = 0;
        if (source != null && source.getAudioFile() != null) {
            filename = source.getAudioFile().getName();
            duration = source.getDuration();
        } else {
            stop();
        }

        return new PlayerStats(filename, duration, repeatMode, shuffle, paused);
    }
}
