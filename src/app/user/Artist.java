package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.Event;
import fileio.input.CommandInput;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class Artist extends User {
    private String name;
    private Integer releaseYear;
    private ArrayList<Album> albums;
    private ArrayList<Event> events;


    public Artist(final String username, final int age,
                  final String city, final String type) {
        super(username, age, city, type);
        albums = new ArrayList<>();
        events = new ArrayList<>();
    }

    @Override
    public boolean isArtist() {
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(final Integer releaseYear) {
        this.releaseYear = releaseYear;
    }


    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(final ArrayList<Album> songs) {
        this.albums = songs;
    }
    /**
     * Adds a new album to the artist's collection.
     *
     * @param commandInput The input containing the album details.
     * @return A message indicating the success or failure of the operation.
     */
    public String addAlbum(final CommandInput commandInput) {
        Album album = new Album(commandInput.getName(),
                commandInput.getUsername(), commandInput.getSongs());
        for (Album artistAlbum : this.albums) {
            if (artistAlbum.getName().contains(commandInput.getName())) {
                return commandInput.getUsername() + " has another album with the same name.";
            }
        }

        Set<String> uniqueSongNames = new HashSet<>();
        for (SongInput newSong : album.getSongs()) {
            String songName = newSong.getName();
            if (!uniqueSongNames.add(songName)) {
                return commandInput.getUsername()
                        + " has the same song at least twice in this album.";
            }
        }

        this.albums.add(album);
        Admin.getAlbums().add(album);
        Admin.addSongs(album.getSongs());
        return commandInput.getUsername() + " has added a new album successfully.";
    }
    /**
     * Adds a new event to the artist's collection.
     *
     * @param commandInput The input containing the event details.
     * @return A message indicating the success or failure of the operation.
     */
    public String addEvent(final CommandInput commandInput) {
        Event event = new Event(commandInput.getName(),
                commandInput.getUsername(),
                commandInput.getName(),
                commandInput.getDescription(), commandInput.getDate());
        for (Event event1 : this.events) {
            if (event1.getName().contains(commandInput.getName())) {
                return commandInput.getUsername() + " has another event with the same name.";
            }
        }

        events.add(event);
        return commandInput.getUsername() + " has added new event successfully.";
    }


}
