import java.util.LinkedList;
import java.util.ListIterator;

public class Playlist {
    private String name;
    private LinkedList<Song> songs;
    private ListIterator<Song> iterator;

    public Playlist(String name) {
        this.name = name;
        songs = new LinkedList<>();
        iterator = songs.listIterator();
    }

    public void addSong(Song song) {
        songs.add(song);
        iterator = songs.listIterator();
    }

    public Song nextSong() {
        if (iterator.hasNext())
            return iterator.next();
        return null;
    }

    public Song previousSong() {
        if (iterator.hasPrevious())
            return iterator.previous();
        return null;
    }

    public LinkedList<Song> getSongs() {
        return songs;
    }

    public String getName() {
        return name;
    }
}
