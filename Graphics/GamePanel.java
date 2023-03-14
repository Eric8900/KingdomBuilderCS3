package Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.AttributeSet.ColorAttribute;
public class GamePanel extends JPanel {
    private BufferedImage[] BOARDS = new BufferedImage[8];
    private HashMap<BufferedImage, Boolean> boards;
    public GamePanel() {
        try {
            BOARDS[0] = ImageIO.read(GamePanel.class.getResource("/Images/Board1.png"));
            BOARDS[1] = ImageIO.read(GamePanel.class.getResource("/Images/Board2.png"));
            BOARDS[2] = ImageIO.read(GamePanel.class.getResource("/Images/Board3.png"));
            BOARDS[3] = ImageIO.read(GamePanel.class.getResource("/Images/Board4.png"));
            BOARDS[4] = ImageIO.read(GamePanel.class.getResource("/Images/Board5.png"));
            BOARDS[5] = ImageIO.read(GamePanel.class.getResource("/Images/Board6.png"));
            BOARDS[6] = ImageIO.read(GamePanel.class.getResource("/Images/Board7.png"));
            BOARDS[7] = ImageIO.read(GamePanel.class.getResource("/Images/Board8.png"));
            
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    public void paint(Graphics g) {
        super.paintComponent(g);
        double mult = 1.2;
        int startX = 300;
        int startY = 75;
        int width = (int)(620/mult);
        int height = (int)(528/mult);
        //(startX + width) - (width + ((int)(20 / mult)))
        int hOffset = ((width - ((int)(30 / mult))));
        int vOffset = ((height - ((int)(17 / mult))));
        g.drawImage(BOARDS[0], startX, startY, width, height, null);
        g.drawImage(BOARDS[1], startX, startY + vOffset, width, height, null);
        g.drawImage(BOARDS[2], startX + hOffset, startY, width, height, null);
        g.drawImage(BOARDS[3], startX + hOffset, startY + vOffset, width, height, null);
    }
}
