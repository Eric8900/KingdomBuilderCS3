package Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.AttributeSet.ColorAttribute;
import Logic1.*;
public class GamePanel extends JPanel implements MouseListener {
    private static final String ArrayLis = null;
    private int[][] initBoard = new int[20][20];
    private BufferedImage[] boards = new BufferedImage[4];
    public static GameState gameState;
    private String cordXY = "YOOO";
    private static int curr_i = -1;
    private static int curr_j = -1;
    public GamePanel() {
        gameState = new GameState();
        try {
            BufferedImage[] BOARDS = new BufferedImage[16];
            boolean[] used = new boolean[16];
            BOARDS[0] = ImageIO.read(GamePanel.class.getResource("/Images/Board1.png"));
            BOARDS[1] = ImageIO.read(GamePanel.class.getResource("/Images/Board2.png"));
            BOARDS[2] = ImageIO.read(GamePanel.class.getResource("/Images/Board3.png"));
            BOARDS[3] = ImageIO.read(GamePanel.class.getResource("/Images/Board4.png"));
            BOARDS[4] = ImageIO.read(GamePanel.class.getResource("/Images/Board5.png"));
            BOARDS[5] = ImageIO.read(GamePanel.class.getResource("/Images/Board6.png"));
            BOARDS[6] = ImageIO.read(GamePanel.class.getResource("/Images/Board7.png"));
            BOARDS[7] = ImageIO.read(GamePanel.class.getResource("/Images/Board8.png"));
            BOARDS[8] = ImageIO.read(GamePanel.class.getResource("/Images/Board9.png"));
            BOARDS[9] = ImageIO.read(GamePanel.class.getResource("/Images/Board10.png"));
            BOARDS[10] = ImageIO.read(GamePanel.class.getResource("/Images/Board11.png"));
            BOARDS[11] = ImageIO.read(GamePanel.class.getResource("/Images/Board12.png"));
            BOARDS[12] = ImageIO.read(GamePanel.class.getResource("/Images/Board13.png"));
            BOARDS[13] = ImageIO.read(GamePanel.class.getResource("/Images/Board14.png"));
            BOARDS[14] = ImageIO.read(GamePanel.class.getResource("/Images/Board15.png"));
            BOARDS[15] = ImageIO.read(GamePanel.class.getResource("/Images/Board16.png"));
            //BOARD RANDOMIZATION INITIALIZATION
            int[][][] boardConfig = new int[4][10][10];
            for (int i = 0; i < 4; i++) {
                int rand = (int) (Math.random() * 16);
                while (used[rand]) {
                    rand = (int) (Math.random() * 16);
                }
                used[rand] = true;
                boards[i] = BOARDS[rand];
                boolean rev = false;
                if (rand > 7) {
                    rev = true;
                    rand -= 7;
                }
                else {
                    rand++;
                }
                Scanner sc = new Scanner(new File("Boards/Board" + rand + ".txt"));
                int[][] tempBoard = new int[10][10];
                for (int j = 0; j < 10; j++) {
                    for (int r = 0; r < 10; r++) {
                        tempBoard[j][r] = sc.nextInt();
                    }
                }
                if (!rev) boardConfig[i] = tempBoard;
                else {
                    //reversed one for the upside down cofig
                    for (int j = 9; j >= 0; j--) {
                        for (int r = 9; r >= 0; r--) {
                            boardConfig[i][9 - j][9 - r] = tempBoard[j][r];      
                        }
                    }
                }
            }
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j < 19; j++) {
                    int b = 0;
                    int I = i;
                    int J = j;
                    // hard coding goes crazy
                    if (j > 9) {
                        b++;
                        J -= 10;
                    }
                    if (i > 9) {
                        if (j < 10) b = 2;
                        if (j > 9) b = 3;
                        I -= 10;
                    }
                    initBoard[i][j] = boardConfig[b][I][J];
                }
            }
            for (int i = 0; i < 19; i++) {
                for (int j = 0; j < 19; j++) {
                    System.out.print(initBoard[i][j] + " ");
                }
                System.out.println();
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        addMouseListener(this);
    }
    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawString(cordXY, 1300, 50);
        double mult = 1.2;
        int startX = 0;
        int startY = 0;
        int width = (int)(620/mult);
        int height = (int)(528/mult);
        int hOffset = ((width - ((int)(32 / mult))));
        int vOffset = ((height - ((int)(18 / mult))));
        g.drawImage(boards[0], startX, startY, width, height, null);
        g.drawImage(boards[1], startX + hOffset, startY, width, height, null);
        g.drawImage(boards[2], startX, startY + vOffset, width, height, null);
        g.drawImage(boards[3], startX + hOffset, startY + vOffset, width, height, null);
        g.setColor(new Color(50, 50, 50));
        //58(x) 52.8(y)
        Pair[][] centers = new Pair[20][20];
        for (int i = 0; i < 20; i++) {
            int y = (startY + 4) + (int) (42.5 * i);
            for (int j = 0; j < 20; j++) {
                int x = i % 2 == 0 ? (startX + 1) + (49 * j) : (startX + 26) + (49 * j);
                g.drawOval(x, y, (int) 48, 48); //47 and 48 is hardcoded...
                //centers
                System.out.print((x + (48 / 2)) + " " + (y + (48 / 2)) + " / ");
                centers[i][j] = new Pair((x + (48 / 2)), (y + (48 / 2)));
            }
            System.out.println();
        }
        System.out.println("You clicked this hexagon: "+ curr_i +
                " " + curr_j);
        GameState.board = new GameBoard(initBoard, centers);
        drawAllPlayerUI(g);
    }
    private void drawAllPlayerUI(Graphics g) {
        //testing
        gameState.players.add(new Player());
        gameState.players.add(new Player());
        gameState.players.add(new Player());
        gameState.players.add(new Player());


        drawPlayerUI(g,gameState.players.get(0),0,KingdomFrame.WIDTH/5*3,0,KingdomFrame.WIDTH/5,KingdomFrame.HEIGHT/5);
        drawPlayerUI(g,gameState.players.get(1),1,KingdomFrame.WIDTH/5*4,0,KingdomFrame.WIDTH/5,KingdomFrame.HEIGHT/5);
        drawPlayerUI(g,gameState.players.get(2),2,KingdomFrame.WIDTH/5*3,KingdomFrame.HEIGHT/5,KingdomFrame.WIDTH/5,KingdomFrame.HEIGHT/5);
        drawPlayerUI(g,gameState.players.get(3),3,KingdomFrame.WIDTH/5*4,KingdomFrame.HEIGHT/5,KingdomFrame.WIDTH/5,KingdomFrame.HEIGHT/5);





    }
    private void drawPlayerUI(Graphics g , Player p, int playerNum,int x, int y, int width, int height){
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.drawRoundRect(x,y,width,height,50,50);
        g.drawString("Player " + (playerNum+1),x+10,y+25);
        g.drawString(""+p.tilesLeft,x+width/2,y+25);

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        int x = e.getX();
        int y = e.getY();
        cordXY = x + " " + y;
        GameHex[][] gm = GameState.board.GameMatrix;
        for(int i = 0; i<gm.length; i++){
            for(int j = 0; j<gm[i].length; j++){
                if((gm[i][j].x - x) * (gm[i][j].x - x) + (gm[i][j].y - y) *(gm[i][j].y - y) <= 24 * 24){
                    gm[i][j].highlighted = true;
                    curr_i = i;
                    curr_j = j;
                }
            }
        }
        repaint();
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}
