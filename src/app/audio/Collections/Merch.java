package app.audio.Collections;

import app.audio.Files.AudioFile;
import lombok.Getter;
import lombok.Setter;

public final class Merch extends AudioCollection{
    @Getter
    @Setter
    private Integer price;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;

    public Merch(final String name,
                 final String owner,
                 final Integer price,
                 final String name1,
                 final String description) {
        super(name, owner);
        this.price = price;
        this.name = name1;
        this.description = description;
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return null;
    }

    @Override
    public int getNumberOfTracks() {
        return 0;
    }
}
