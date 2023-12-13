package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.Event;
import app.audio.Files.Song;
import fileio.input.CommandInput;
import fileio.input.SongInput;
import org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Artist extends User{
    private String name;
    private Integer releaseYear;
    private ArrayList<Album> albums;
    private ArrayList<Event> events;


    public Artist(String username, int age, String city, String type) {
        super(username, age, city, type);
        albums = new ArrayList<>();
        events = new ArrayList<>();
    }

    @Override
    public boolean isArtist(){
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }


    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> songs) {
        this.albums = songs;
    }

    public String addAlbum(CommandInput commandInput){
        Album album = new Album(commandInput.getName(), commandInput.getUsername(), commandInput.getSongs());
        for (Album albums : this.albums) {
            if (albums.getName().contains(commandInput.getName())){
                return commandInput.getUsername() + " has another album with the same name.";
            }
        }
        Set<String> uniqueSongNames = new HashSet<>();
        for (SongInput newSong : album.getSongs()) {
            String songName = newSong.getName();
            if (!uniqueSongNames.add(songName)) {
                return commandInput.getUsername() + " has the same song at least twice in this album.";
            }
        }

        albums.add(album);
        Admin.getAlbums().add(album);
        Admin.addSongs(album.getSongs());
        return commandInput.getUsername() + " has added new album successfully.";
    }
    public String addEvent(CommandInput commandInput){
        Event event = new Event(commandInput.getName(), commandInput.getUsername(), commandInput.getName(), commandInput.getDescription(), commandInput.getDate());
        for (Event event1 : this.events) {
            if (event1.getName().contains(commandInput.getName())){
                return commandInput.getUsername() + " has another event with the same name.";
            }
        }

        events.add(event);
        return commandInput.getUsername() + " has added new event successfully.";
    }


}
