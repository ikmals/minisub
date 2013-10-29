package com;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class MiniSub {

    private static final String MEDIA_FILE_PATH = "C:\\Users\\Ikmal\\Videos\\ovj.mp4";
    private static final String VLC_INSTALL_PATH = "C:\\Program Files\\VideoLAN\\VLC";
    private static EmbeddedMediaPlayer player;

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            private JButton bPlay;
//            private JButton bPause;

            @Override
            public void run() {
                JFrame frame = new JFrame("MiniSub");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 500);

                JPanel panel = new JPanel();
                bPlay = new JButton("Play/Pause");
                panel.add(bPlay);

                frame.setContentPane(panel);

                Canvas canvas = new Canvas();
                canvas.setSize(400, 400);
                panel.add(canvas);

                bPlay.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me) {
                        if (player.isPlayable()) {
                            if(player.isPlaying()){
                                player.pause();
                                System.out.println(player.getTime());
                                
                            }
                            else{
                                player.play();
                            }
                        }
                        else{
                            player.playMedia(MEDIA_FILE_PATH);
                            System.out.println(player.getLength());
                        }
                    }
                });

                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                // Load library
                NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), VLC_INSTALL_PATH);
                Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

                // Initialize the media player
                MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
                DefaultFullScreenStrategy fsStrat = new DefaultFullScreenStrategy(frame);

                CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
                player = mediaPlayerFactory.newEmbeddedMediaPlayer(fsStrat);
                player.setVideoSurface(videoSurface);
            }
        });
    }
}