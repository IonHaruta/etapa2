package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.user.Artist;
import fileio.input.SongInput;

import java.util.ArrayList;

public class Album extends AudioCollection{
    private ArrayList<SongInput> songs;

    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<SongInput> songs) {
        this.songs = songs;
    }


    public Album(String name, String owner, ArrayList<SongInput> songs) {
        super(name, owner);
        this.songs = songs;
    }

    @Override
    public int getNumberOfTracks() {
        return 0;
    }

    @Override
    public AudioFile getTrackByIndex(int index) {
        return null;
    }


}
