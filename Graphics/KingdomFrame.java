package Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.*;
public class KingdomFrame extends JFrame{
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public KingdomFrame(String name) {
        super(name);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        add(new GamePanel());
        setVisible(true);
    }
}
