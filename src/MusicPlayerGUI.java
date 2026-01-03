import java.awt.*;
import javax.swing.*;

public class MusicPlayerGUI extends JFrame {

    private PlaylistManager manager;
    private Playlist currentPlaylist;

    private DefaultListModel<String> listModel;
    private JList<String> songList;
    private JTextArea nowPlayingArea;

    public MusicPlayerGUI() {

        manager = new PlaylistManager();
        manager.createPlaylist("My Playlist");
        currentPlaylist = manager.getPlaylist("My Playlist");

        // Sample songs
        currentPlaylist.addSong(new Song("Blinding Lights", "The Weeknd", 200));
        currentPlaylist.addSong(new Song("Levitating", "Dua Lipa", 203));
        currentPlaylist.addSong(new Song("Believer", "Imagine Dragons", 210));
        currentPlaylist.addSong(new Song("Watermelon Sugar", "Harry Styles", 174));
        currentPlaylist.addSong(new Song("Peaches", "Justin Bieber", 198));
        currentPlaylist.addSong(new Song("Save Your Tears", "The Weeknd", 215));
        currentPlaylist.addSong(new Song("Good 4 U", "Olivia Rodrigo", 178));
        currentPlaylist.addSong(new Song("Montero", "Lil Nas X", 137));
        currentPlaylist.addSong(new Song("Stay", "Justin Bieber", 141));
        currentPlaylist.addSong(new Song("Kiss Me More", "Doja Cat", 208));

        setTitle("Music Playlist Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // --- Song List ---
        listModel = new DefaultListModel<>();
        for (Song s : currentPlaylist.getSongs()) {
            listModel.addElement(s.toString());
        }

        songList = new JList<>(listModel);
        add(new JScrollPane(songList), BorderLayout.CENTER);

        // --- Now Playing Panel ---
        nowPlayingArea = new JTextArea();
        nowPlayingArea.setEditable(false);
        nowPlayingArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        nowPlayingArea.setText("Now Playing:\n");

        JScrollPane nowPlayingScroll = new JScrollPane(nowPlayingArea);
        nowPlayingScroll.setPreferredSize(new Dimension(250, 0));
        add(nowPlayingScroll, BorderLayout.EAST);

        // --- Control Buttons ---
        JButton prevBtn = new JButton("Previous");
        JButton playBtn = new JButton("Play");
        JButton nextBtn = new JButton("Next");

        prevBtn.addActionListener(e -> playPrevious());
        playBtn.addActionListener(e -> playNext());
        nextBtn.addActionListener(e -> playNext());

        JPanel controlPanel = new JPanel();
        controlPanel.add(prevBtn);
        controlPanel.add(playBtn);
        controlPanel.add(nextBtn);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void playNext() {
        Song song = currentPlaylist.nextSong();
        if (song != null) {
            nowPlayingArea.setText(
                "Now Playing:\n" +
                song.getTitle() + "\n" +
                "Artist: " + song.getArtist() + "\n" +
                "Duration: " + song.getDuration() + " sec"
            );
        }
    }

    private void playPrevious() {
        Song song = currentPlaylist.previousSong();
        if (song != null) {
            nowPlayingArea.setText(
                "Now Playing:\n" +
                song.getTitle() + "\n" +
                "Artist: " + song.getArtist() + "\n" +
                "Duration: " + song.getDuration() + " sec"
            );
        }
    }
}
