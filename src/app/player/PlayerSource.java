package app.player;

import app.audio.Collections.AudioCollection;
import app.audio.Files.AudioFile;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class PlayerSource {
    @Getter
    private Enums.PlayerSourceType type;
    @Getter
    private AudioCollection audioCollection;
    @Getter
    private AudioFile audioFile;
    @Getter
    private int index;
    private int indexShuffled;
    private int remainedDuration;
    private final List<Integer> indices = new ArrayList<>();

    public PlayerSource(final Enums.PlayerSourceType type,
                        final AudioFile audioFile) {
        this.type = type;
        this.audioFile = audioFile;
        this.remainedDuration = audioFile.getDuration();
    }

    public PlayerSource(final Enums.PlayerSourceType type,
                        final AudioCollection audioCollection) {
        this.type = type;
        this.audioCollection = audioCollection;
        this.audioFile = audioCollection.getTrackByIndex(0);
        this.index = 0;
        this.indexShuffled = 0;
        this.remainedDuration = audioFile.getDuration();
    }

    public PlayerSource(final Enums.PlayerSourceType type,
                        final AudioCollection audioCollection,
                        final PodcastBookmark bookmark) {
        this.type = type;
        this.audioCollection = audioCollection;
        this.index = bookmark.getId();
        this.remainedDuration = bookmark.getTimestamp();
        this.audioFile = audioCollection.getTrackByIndex(index);
    }

    public int getDuration() {
        return remainedDuration;
    }

    /**
     * Sets the next audio file based on the repeat mode and shuffle status.
     *
     * @param repeatMode The current repeat mode.
     * @param shuffle    The shuffle status.
     * @return {@code true} if the playback is paused, {@code false} otherwise.
     */
    public boolean setNextAudioFile(final Enums.RepeatMode repeatMode,
                                    final boolean shuffle) {
        boolean isPaused = false;

        if (type == Enums.PlayerSourceType.LIBRARY) {
            if (repeatMode != Enums.RepeatMode.NO_REPEAT) {
                remainedDuration = audioFile.getDuration();
            } else {
                remainedDuration = 0;
                isPaused = true;
            }
        } else {
            if (repeatMode == Enums.RepeatMode.REPEAT_ONCE
                    || repeatMode == Enums.RepeatMode.REPEAT_CURRENT_SONG
                    || repeatMode == Enums.RepeatMode.REPEAT_INFINITE) {
                remainedDuration = audioFile.getDuration();
            } else if (repeatMode == Enums.RepeatMode.NO_REPEAT) {
                if (shuffle) {
                    if (indexShuffled == indices.size() - 1) {
                        remainedDuration = 0;
                        isPaused = true;
                    } else {
                        indexShuffled++;

                        index = indices.get(indexShuffled);
                        updateAudioFile();
                        remainedDuration = audioFile.getDuration();
                    }
                } else {
                    if (index == audioCollection.getNumberOfTracks() - 1) {
                        remainedDuration = 0;
                        isPaused = true;
                    } else {
                        index++;
                        updateAudioFile();
                        remainedDuration = audioFile.getDuration();
                    }
                }
            } else if (repeatMode == Enums.RepeatMode.REPEAT_ALL) {
                if (shuffle) {
                    indexShuffled = (indexShuffled + 1) % indices.size();
                    index = indices.get(indexShuffled);
                } else {
                    index = (index + 1) % audioCollection.getNumberOfTracks();
                }
                updateAudioFile();
                remainedDuration = audioFile.getDuration();
            }
        }

        return isPaused;
    }
    /**
     * Sets the previous audio file based on the shuffle status.
     *
     * @param shuffle The shuffle status.
     */
    public void setPrevAudioFile(final boolean shuffle) {
        if (type == Enums.PlayerSourceType.LIBRARY) {
            remainedDuration = audioFile.getDuration();
        } else {
            if (remainedDuration != audioFile.getDuration()) {
                remainedDuration = audioFile.getDuration();
            } else {
                if (shuffle) {
                    if (indexShuffled > 0) {
                        indexShuffled--;
                    }
                    index = indices.get(indexShuffled);
                    updateAudioFile();
                    remainedDuration = audioFile.getDuration();
                } else {
                    if (index > 0) {
                        index--;
                    }
                    updateAudioFile();
                    remainedDuration = audioFile.getDuration();
                }
            }
        }
    }
    /**
     * Generates a shuffle order for the playlist based on the provided seed.
     *
     * @param seed The seed for randomness.
     */
    public void generateShuffleOrder(final Integer seed) {
        indices.clear();
        Random random = new Random(seed);
        for (int i = 0; i < audioCollection.getNumberOfTracks(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);
    }
    /**
     * Updates the shuffle index based on the current playback index.
     */
    public void updateShuffleIndex() {
        for (int i = 0; i < indices.size(); i++) {
            if (indices.get(i) == index) {
                indexShuffled = i;
                break;
            }
        }
    }
    /**
     * Skips the playback by the specified duration.
     *
     * @param duration The duration to skip.
     */
    public void skip(final int duration) {
        remainedDuration += duration;
        if (remainedDuration > audioFile.getDuration()) {
            remainedDuration = 0;
            index++;
            updateAudioFile();
        } else if (remainedDuration < 0) {
            remainedDuration = 0;
        }
    }
    /**
     * Updates the current audio file to the one at the current index.
     */
    private void updateAudioFile() {
        setAudioFile(audioCollection.getTrackByIndex(index));
    }
    /**
     * Sets the current audio file to the provided audio file.
     *
     * @param audioFile The audio file to set.
     */
    public void setAudioFile(final AudioFile audioFile) {
        this.audioFile = audioFile;
    }

}
