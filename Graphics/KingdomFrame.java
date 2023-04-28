package Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.*;

public class KingdomFrame extends JFrame {
    public static final int WIDTH =  Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int HEIGHT =  Toolkit.getDefaultToolkit().getScreenSize().height;
    private boolean isFullScreen = false;

    public KingdomFrame(String name) {
        super(name);

        // Get the screen size
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        add(new GamePanel());
        setVisible(true);

        // Set the frame to fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        device.setFullScreenWindow(this);

        // Add a key listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F11 || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    toggleFullScreen(device);
                }
            }
        });
    }

    private void toggleFullScreen(GraphicsDevice device) {
        if (isFullScreen) {
            device.setFullScreenWindow(null);
            //setExtendedState(JFrame.NORMAL);
        } else {
            //setExtendedState(JFrame.MAXIMIZED_BOTH);
            device.setFullScreenWindow(this);
        }
        isFullScreen = !isFullScreen;
    }
}
