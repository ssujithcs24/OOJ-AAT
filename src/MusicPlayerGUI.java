import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MusicPlayerGUI extends JFrame
{
    private PlaylistManager manager;
    private Playlist currentPlaylist;
    private DefaultListModel<String> listModel;
    private JList<String> songList;
    private JTextArea nowPlayingArea;
    public MusicPlayerGUI() {

        // ---------- THEME ----------
        Color bgDark = new Color(18, 18, 18);
        Color panelDark = new Color(30, 30, 30);
        Color accentGreen = new Color(30, 215, 96);
        Color textWhite = Color.WHITE;

        Font uiFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 15);
        Font controlFont = new Font("Segoe UI Symbol", Font.BOLD, 18);

        UIManager.put("Button.background", panelDark);
        UIManager.put("Button.foreground", textWhite);
        UIManager.put("Panel.background", bgDark);
        UIManager.put("Label.foreground", textWhite);

        getContentPane().setBackground(bgDark);

        // ---------- LOGIC ----------
        manager = new PlaylistManager();
        manager.createPlaylist("My Playlist");
        currentPlaylist = manager.getPlaylist("My Playlist");

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

        // ---------- FRAME ----------
        setTitle("Music Playlist Manager");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));
        setLocationRelativeTo(null);

        // ---------- SONG LIST ----------
        listModel = new DefaultListModel<>();
        for (Song s : currentPlaylist.getSongs()) {
            listModel.addElement(s.toString());
        }

        songList = new JList<>(listModel);
        songList.setFont(uiFont);
        songList.setBackground(panelDark);
        songList.setForeground(textWhite);
        songList.setSelectionBackground(accentGreen);
        songList.setSelectionForeground(Color.BLACK);
        songList.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane songScroll = new JScrollPane(songList);
        songScroll.setBorder(null); // removes middle divider
        add(songScroll, BorderLayout.CENTER);

        // ---------- NOW PLAYING ----------
        nowPlayingArea = new JTextArea("Now Playing:\n");
        nowPlayingArea.setEditable(false);
        nowPlayingArea.setFont(titleFont);
        nowPlayingArea.setBackground(panelDark);
        nowPlayingArea.setForeground(textWhite);
        nowPlayingArea.setCaretColor(textWhite);
        nowPlayingArea.setBorder(new EmptyBorder(15, 15, 15, 15));

        JScrollPane nowPlayingScroll = new JScrollPane(nowPlayingArea);
        nowPlayingScroll.setPreferredSize(new Dimension(260, 0));
        nowPlayingScroll.setBorder(null); // removes divider
        add(nowPlayingScroll, BorderLayout.EAST);

        // ---------- CONTROLS ----------
        JButton prevBtn = new JButton("⏮");
        JButton playBtn = new JButton("▶");
        JButton nextBtn = new JButton("⏭");

        prevBtn.setFont(controlFont);
        playBtn.setFont(controlFont);
        nextBtn.setFont(controlFont);

        playBtn.setBackground(accentGreen);
        playBtn.setForeground(Color.BLACK);

        prevBtn.setFocusPainted(false);
        playBtn.setFocusPainted(false);
        nextBtn.setFocusPainted(false);

        prevBtn.addActionListener(e -> playPrevious());
        playBtn.addActionListener(e -> playNext());
        nextBtn.addActionListener(e -> playNext());

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(bgDark);
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        controlPanel.add(prevBtn);
        controlPanel.add(playBtn);
        controlPanel.add(nextBtn);

        add(controlPanel, BorderLayout.SOUTH);
    }

    // ---------- PLAYBACK ----------
    private void playNext() {
        Song song = currentPlaylist.nextSong();
        if (song != null) {
            nowPlayingArea.setText(
                "Now Playing:\n\n" +
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
                "Now Playing:\n\n" +
                song.getTitle() + "\n" +
                "Artist: " + song.getArtist() + "\n" +
                "Duration: " + song.getDuration() + " sec"
            );
        }
    }
}
