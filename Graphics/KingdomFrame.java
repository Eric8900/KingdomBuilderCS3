package Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.*;
import Logic1.*;

public class KingdomFrame extends JFrame {
    public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private boolean isFullScreen = true;
    public static JTextField textField1 = new JTextField("40");
    GamePanel panel = new GamePanel();

    public KingdomFrame(String name) {
        super(name);

        // Get the screen size
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        createTextField1();
        panel.add(textField1);
        add(panel);
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
                    isFullScreen = !isFullScreen;
                    if (!isFullScreen) {
                        device.setFullScreenWindow(null);
                        setExtendedState(JFrame.NORMAL);
                    } else {
                        setExtendedState(JFrame.MAXIMIZED_BOTH);
                        toggleFullScreen(device);
                    }
                }
            }
        });
    }

    public static void createTextField1() {
        textField1.setFont(new Font("TimesRoman", Font.BOLD, 30));
        textField1.setForeground(new Color(110, 108, 166));
        textField1.setBackground(new Color(207, 206, 242));
        textField1.setSelectionColor(Constants.Colors.whiteFade);
        textField1.setVisible(true);
    }

    private void toggleFullScreen(GraphicsDevice device) {
        device.setFullScreenWindow(this);
    }
}
