package app.audio.Collections;

import app.audio.Files.AudioFile;
import lombok.Getter;

public class Event extends AudioCollection{
    private String name;
    @Getter
    private String description;
    @Getter
    private String date;

    public Event(String name, String owner, String name1, String description, String date) {
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
    public AudioFile getTrackByIndex(int index) {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
