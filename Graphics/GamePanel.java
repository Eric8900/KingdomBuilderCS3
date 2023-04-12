package Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
import Logic1.GameState.State;
public class GamePanel extends JPanel implements MouseListener {
    private static final String ArrayLis = null;
    private int[][] initBoard = new int[20][20];
    private int startX = 0;
    private int startY = 0;
    private BufferedImage[] boards = new BufferedImage[4];
    private BufferedImage[] locationTiles = new BufferedImage[4];
    private BufferedImage[] settlements = new BufferedImage[4];
    private BufferedImage[] objectiveCards = new BufferedImage[10];
    private BufferedImage cardBack;
    private BufferedImage background;
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
            locationTiles[0] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Location-Oracle.png"));
            locationTiles[1] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Location-Oasis.png"));
            locationTiles[2] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Location-Tavern.png"));
            locationTiles[3] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Location-Tower.png"));
            settlements[0] = ImageIO.read(GamePanel.class.getResource("/Images/settlement-blue.png"));
            settlements[1] = ImageIO.read(GamePanel.class.getResource("/Images/settlement-green.png"));
            settlements[2] = ImageIO.read(GamePanel.class.getResource("/Images/settlement-orange.png"));
            settlements[3] = ImageIO.read(GamePanel.class.getResource("/Images/settlement-yellow.png"));
            background = ImageIO.read(GamePanel.class.getResource("/Images/OregonTrail.jpg"));
            objectiveCards[0] = ImageIO.read(GamePanel.class.getResource("/Images/WorkersObjective.png"));
            cardBack= ImageIO.read(GamePanel.class.getResource("/Images/KB-Card-Back.png"));
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
            //58(x) 52.8(y)
            Pair[][] centers = new Pair[20][20];
            for (int i = 0; i < 20; i++) {
                int y = (startY + 4) + (int) (42.5 * i);
                for (int j = 0; j < 20; j++) {
                    int x = i % 2 == 0 ? (startX + 1) + (49 * j) : (startX + 26) + (49 * j);
                    //g.drawOval(x, y, (int) 48, 48); //47 and 48 is hardcoded...
                    //centers
                    System.out.print((x + (48 / 2)) + " " + (y + (48 / 2)) + " / ");
                    centers[i][j] = new Pair((x + (48 / 2)), (y + (48 / 2)));
                }
                System.out.println();
            }
            GameState.board = new GameBoard(initBoard, centers);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        addMouseListener(this);
    }
    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0,KingdomFrame.WIDTH,KingdomFrame.HEIGHT,null);
        g.drawString(cordXY, 1300, 50);
        double mult = 1.2;
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
                if (GameState.board.GameMatrix[i][j].highlighted) {
                    g.setColor(Color.RED);
                    g.drawOval(x, y, (int) 48, 48); //47 and 48 is hardcoded...
                    g.setColor(Color.BLACK);
                }
            }
        }
        System.out.println("You clicked this hexagon: "+ curr_i +
                " " + curr_j);
        drawAllPlayerUI(g);
        drawDeckDiscard(g);
    }
    private void drawDeckDiscard(Graphics g){
        
        double rads = Math.toRadians(90);
        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));
        int w = (int) Math.floor(cardBack.getWidth() * cos + cardBack.getHeight() * sin);
        int h = (int) Math.floor(cardBack.getHeight() * cos + cardBack.getWidth() * sin);
        BufferedImage rotatedImage = new BufferedImage(w, h, cardBack.getType());
        AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads,0, 0);
        at.translate(-cardBack.getWidth() / 2, -cardBack.getHeight() / 2);
        AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(cardBack,rotatedImage);
        
        double scale = 1.2f;
        g.drawImage(rotatedImage,0,865, (int)(rotatedImage.getWidth()*scale), (int)(rotatedImage.getHeight()*scale),null);
        g.drawImage(rotatedImage,(int)(rotatedImage.getWidth()*scale),865, (int)(rotatedImage.getWidth()*scale), (int)(rotatedImage.getHeight()*scale),null);
        g.drawString("Deck",  0, (int)(rotatedImage.getHeight()*scale)+865);
        g.drawString("Discard",  (int)(rotatedImage.getWidth()*scale), (int)(rotatedImage.getHeight()*scale)+865);


    }
    private void drawAllPlayerUI(Graphics g) {
        //testing
        gameState.players.add(new Player());
        gameState.players.add(new Player());
        gameState.players.add(new Player());
        gameState.players.add(new Player());


        drawPlayerUI(g,gameState.players.get(0),0,KingdomFrame.WIDTH/3*2,0,KingdomFrame.WIDTH/3,KingdomFrame.HEIGHT/5);
        drawPlayerUI(g,gameState.players.get(1),1,KingdomFrame.WIDTH/3*2,KingdomFrame.HEIGHT/5,KingdomFrame.WIDTH/3,KingdomFrame.HEIGHT/5);
        drawPlayerUI(g,gameState.players.get(2),2,KingdomFrame.WIDTH/3*2,KingdomFrame.HEIGHT/5*2,KingdomFrame.WIDTH/3,KingdomFrame.HEIGHT/5);
        drawPlayerUI(g,gameState.players.get(3),3,KingdomFrame.WIDTH/3*2,KingdomFrame.HEIGHT/5*3,KingdomFrame.WIDTH/3,KingdomFrame.HEIGHT/5);





    }
    private void drawPlayerUI(Graphics g , Player p, int playerNum,int x, int y, int width, int height){

        double settlementScale = 0.5;

        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        Color playerRectColor = new Color(0, 0, 0, 127);
        g.setColor(playerRectColor);
        g.fillRoundRect(x,y,width,height,50,50);
        Color outLineColor = new Color(211, 211, 211);
        g.setColor(outLineColor);
        g.drawRoundRect(x,y,width,height,50,50);
        g.drawString("Player " + (playerNum+1),x+10,y+25);
        g.drawString(""+p.tilesLeft,x+width/3+(int)(settlements[playerNum].getWidth()*settlementScale),y+25);
        g.drawImage(settlements[playerNum], x+width/3, y,(int)(settlements[playerNum].getWidth()*settlementScale),(int)(settlements[playerNum].getHeight()*settlementScale), null);



        drawPlayerLocationTiles(g,p,playerNum,x,y,width,height);


    }
    private void drawPlayerLocationTiles(Graphics g,Player p, int playerNum,int x, int y, int width, int height){
        //g.fillOval();
        g.drawImage(locationTiles[0],x+10,y+20,locationTiles[0].getWidth()/2,locationTiles[0].getWidth()/2, null);
        g.drawImage(locationTiles[1],x+width/2-10,y+20,locationTiles[0].getWidth()/2,locationTiles[0].getWidth()/2, null);
        g.drawImage(locationTiles[2],x+10,y+height/2,locationTiles[0].getWidth()/2,locationTiles[0].getWidth()/2, null);
        g.drawImage(locationTiles[3],x+width/2-10,y+height/2,locationTiles[0].getWidth()/2,locationTiles[0].getWidth()/2, null);

        g.drawString(p.locationTiles.get(0).toString(),x+5+locationTiles[0].getWidth()/2/2,y+20+locationTiles[0].getWidth()/2);
        g.drawString(p.locationTiles.get(1).toString(),x+locationTiles[0].getWidth()/2/2+width/2-15,y+20+locationTiles[0].getWidth()/2);
        g.drawString(p.locationTiles.get(2).toString(),x+5+locationTiles[0].getWidth()/2/2,y+20+locationTiles[0].getWidth());
        g.drawString(p.locationTiles.get(3).toString(),x+locationTiles[0].getWidth()/2/2+width/2-15,y+20+locationTiles[0].getWidth());
        

        
    }

    private void drawObjectiveCards(Graphics g){

    }



    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        int x = e.getX();
        int y = e.getY();
        cordXY = x + " " + y;
        GameHex[][] gm = GameState.board.GameMatrix;
        if (GameState.currentState == State.PLAYSETTLEMENTS) {
            for(int i = 0; i<gm.length; i++){
                for(int j = 0; j<gm[i].length; j++){
                    if((gm[i][j].x - x) * (gm[i][j].x - x) + (gm[i][j].y - y) *(gm[i][j].y - y) <= 24 * 24){
                        
                        gm[i][j].highlighted = true;
                        curr_i = i;
                        curr_j = j;
                    }
                }
            }
        }
        if (GameState.currentState == State.DRAWCARD) {

        }
        if (GameState.currentState == State.PLAYLOCATIONTILE) {

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
