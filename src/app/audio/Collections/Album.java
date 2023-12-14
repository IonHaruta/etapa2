package app.audio.Collections;

import app.audio.Files.AudioFile;
import fileio.input.SongInput;

import java.util.ArrayList;

public final class Album extends AudioCollection {
    private ArrayList<SongInput> songs;

    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }


    public Album(final String name, final String owner, final ArrayList<SongInput> songs) {
        super(name, owner);
        this.songs = songs;
    }

    @Override
    public int getNumberOfTracks() {
        return 0;
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return null;
    }


}
