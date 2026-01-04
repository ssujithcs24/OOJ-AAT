import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MusicPlayerGUI extends JFrame {

    private PlaylistManager manager;
    private Playlist currentPlaylist;

    private DefaultListModel<Song> listModel;
    private JList<Song> songList;
    private JTextArea nowPlayingArea;

    // ---------- CIRCULAR BUTTON ----------
    static class CircleButton extends JButton {
        public CircleButton(String text, Color bg, Color fg, Font font) {
            super(text);
            setFont(font);
            setForeground(fg);
            setBackground(bg);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setPreferredSize(new Dimension(55, 55));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isArmed())
                g2.setColor(getBackground().darker());
            else
                g2.setColor(getBackground());

            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public boolean contains(int x, int y) {
            int r = getWidth() / 2;
            return Math.pow(x - r, 2) + Math.pow(y - r, 2) <= r * r;
        }
    }

    // ---------- SONG CELL RENDERER ----------
    static class SongRenderer extends JPanel implements ListCellRenderer<Song> {

        private JLabel titleLabel = new JLabel();
        private JLabel artistLabel = new JLabel();

        public SongRenderer() {
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(8, 12, 8, 12));

            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
            artistLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            artistLabel.setForeground(new Color(180, 180, 180));

            add(titleLabel, BorderLayout.NORTH);
            add(artistLabel, BorderLayout.SOUTH);
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends Song> list,
                Song song,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            titleLabel.setText(song.getTitle());
            artistLabel.setText(song.getArtist());

            if (isSelected) {
                setBackground(new Color(30, 215, 96));
                titleLabel.setForeground(Color.BLACK);
                artistLabel.setForeground(Color.BLACK);
            } else {
                setBackground(new Color(30, 30, 30));
                titleLabel.setForeground(Color.WHITE);
                artistLabel.setForeground(new Color(180, 180, 180));
            }
            return this;
        }
    }

    public MusicPlayerGUI() {

        // ---------- THEME ----------
        Color bgDark = new Color(18, 18, 18);
        Color panelDark = new Color(30, 30, 30);
        Color accentGreen = new Color(30, 215, 96);
        Color textWhite = Color.WHITE;

        Font controlFont = new Font("Segoe UI Symbol", Font.BOLD, 18);

        UIManager.put("Panel.background", bgDark);

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
        for (Song s : currentPlaylist.getSongs())
            listModel.addElement(s);

        songList = new JList<>(listModel);
        songList.setCellRenderer(new SongRenderer());
        songList.setBackground(panelDark);
        songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane songScroll = new JScrollPane(songList);
        songScroll.setBorder(null);
        add(songScroll, BorderLayout.CENTER);

        // ---------- NOW PLAYING ----------
        nowPlayingArea = new JTextArea("Now Playing:\n");
        nowPlayingArea.setEditable(false);
        nowPlayingArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nowPlayingArea.setBackground(panelDark);
        nowPlayingArea.setForeground(textWhite);
        nowPlayingArea.setBorder(new EmptyBorder(15, 15, 15, 15));

        JScrollPane nowPlayingScroll = new JScrollPane(nowPlayingArea);
        nowPlayingScroll.setPreferredSize(new Dimension(260, 0));
        nowPlayingScroll.setBorder(null);
        add(nowPlayingScroll, BorderLayout.EAST);

        // ---------- CONTROLS ----------
        CircleButton prevBtn = new CircleButton("⏮", panelDark, textWhite, controlFont);
        CircleButton playBtn = new CircleButton("▶", accentGreen, Color.BLACK, controlFont);
        CircleButton nextBtn = new CircleButton("⏭", panelDark, textWhite, controlFont);

        prevBtn.addActionListener(e -> playPrevious());
        playBtn.addActionListener(e -> playNext());
        nextBtn.addActionListener(e -> playNext());

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(bgDark);
        controlPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
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
