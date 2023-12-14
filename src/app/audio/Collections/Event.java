package app.audio.Collections;

import app.audio.Files.AudioFile;
import lombok.Getter;

public final class Event extends AudioCollection {
    private String name;
    @Getter
    private String description;
    @Getter
    private String date;

    public Event(final String name,
                 final String owner,
                 final String name1,
                 final String description, final String date) {
        super(name, owner);
        this.name = name1;
        this.description = description;
        this.date = date;
    }

    @Override
    public int getNumberOfTracks() {
        return 0;
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setDate(final String date) {
        this.date = date;
    }
}
