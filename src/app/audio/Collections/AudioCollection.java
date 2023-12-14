package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.LibraryEntry;
import lombok.Getter;

@Getter
public abstract class AudioCollection extends LibraryEntry {
    private final String owner;

    public AudioCollection(final String name, final String owner) {
        super(name);
        this.owner = owner;
    }

    /**
     * Returns the total number of tracks in the audio collection.
     *
     * @return The number of tracks in the audio collection.
     */
    public abstract int getNumberOfTracks();

    /**
     * Gets the audio file at the specified index in the collection.
     *
     * @param index The index of the track.
     * @return The audio file at the specified index.
     */
    public abstract AudioFile getTrackByIndex(int index);
    /**
     * Checks if the owner of the audio collection matches the given user.
     *
     * @param user The username to compare with the owner.
     * @return {@code true} if the owner matches the given user, {@code false} otherwise.
     */
    @Override
    public boolean matchesOwner(final String user) {
        return this.getOwner().equals(user);
    }
}
