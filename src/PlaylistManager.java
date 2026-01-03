import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PlaylistManager {
    private Map<String, Playlist> playlists;

    public PlaylistManager() {
        playlists = new LinkedHashMap<>();
    }

    public void createPlaylist(String name) {
        playlists.put(name, new Playlist(name));
    }

    public Playlist getPlaylist(String name) {
        return playlists.get(name);
    }

    public Set<String> getPlaylistNames() {
        return playlists.keySet();
    }
}
