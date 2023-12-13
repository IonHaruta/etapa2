package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.User;
import fileio.input.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Admin {

    private static Admin instance;
    @Getter
    @Setter
    private static List<Artist> artists = new ArrayList<>();;
    private static List<User> users;
    private static List<Song> songs;
    private static List<Podcast> podcasts;
    @Getter
    private static List<Album> albums = new ArrayList<>();
    private static int timestamp;

    private Admin() {
        // private constructor to prevent external instantiation
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
    }


    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public static void addSongs(ArrayList<SongInput> songsInput){
        for (SongInput song : songsInput) {
            songs.add(new Song(song.getName(), song.getDuration(),
                    song.getAlbum(), song.getTags(),
                    song.getLyrics(), song.getGenre(),
                    song.getReleaseYear(), song.getArtist()));
        }
    }
    public static void setAlbums(List<Album> albums) {
        Admin.albums = albums;
    }

    public static void setUsers(List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity(), userInput.getType()));
        }
    }

    public static void setSongs(List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    public static void setPodcasts(List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(), episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }


    public static void updateTimestamp(int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= 5) break;
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= 5) break;
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
    }
    public static ArrayList<String> getOnlineUsers(){
        ArrayList<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isStatus() && user.getType() == null){
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    public static String addUser(String username, Integer age, String city, String type) {
        User user = getUser(username);
        if (user != null && users.contains(user)) {
            return "The username " + user.getUsername() + " is already taken.";
        } else {
            User newUser;
            if (type.equals("artist")) {
                newUser = new Artist(username, age, city, type);
                artists.add(new Artist(username, age, city, type));
            } else {
                newUser = new User(username, age, city, type);
            }

            users.add(newUser);
            return "The username " + newUser.getUsername() + " has been added successfully.";
        }
    }
    public static List<String> showAlbums(CommandInput commandInput) {
        Artist artist = (Artist) getUser(commandInput.getUsername());
        List<String> allSongs = new ArrayList<>();
        for (Album album : artist.getAlbums()) {
            if ( artist.getUsername().equals(album.getOwner())){
                for (SongInput song : album.getSongs() ) {
                    allSongs.add(song.getName());
                }
            }
        }
        return allSongs;
    }
}