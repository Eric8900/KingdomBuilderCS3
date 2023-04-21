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
    private BufferedImage[] terrainCards = new BufferedImage[5];
    private BufferedImage cardBack;
    private BufferedImage[] backgrounds = new BufferedImage[2];
    private String cordXY = "YOOO";
    private static int curr_i = -1;
    private static int curr_j = -1;
    private Deck deckClass = new Deck();
    private boolean objectiveCardDisplay = false;
    public GamePanel() {
        beginGame();
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
            settlements[0] = ImageIO.read(GamePanel.class.getResource("/Images/settlement-blue.png"));
            settlements[1] = ImageIO.read(GamePanel.class.getResource("/Images/settlement-green.png"));
            settlements[2] = ImageIO.read(GamePanel.class.getResource("/Images/settlement-orange.png"));
            settlements[3] = ImageIO.read(GamePanel.class.getResource("/Images/settlement-yellow.png"));
            terrainCards[0] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Card-Canyon.png"));
            terrainCards[1] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Card-Desert.png"));
            terrainCards[2] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Card-Flower.png"));
            terrainCards[3] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Card-Forest.png"));
            terrainCards[4] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Card-Meadow.png"));
            backgrounds[0] = ImageIO.read(GamePanel.class.getResource("/Images/KB-MainMenuBackground.png"));
            backgrounds[1] = ImageIO.read(GamePanel.class.getResource("/Images/OregonTrail.jpg"));
            objectiveCards[0] = ImageIO.read(GamePanel.class.getResource("/Images/MinersObjective.png"));
            objectiveCards[1] = ImageIO.read(GamePanel.class.getResource("/Images/DiscoverersObjective.PNG"));
            objectiveCards[2] = ImageIO.read(GamePanel.class.getResource("/Images/LordsObjective.PNG"));
            objectiveCards[3] = ImageIO.read(GamePanel.class.getResource("/Images/FishermenObjective.PNG"));
            objectiveCards[4] = ImageIO.read(GamePanel.class.getResource("/Images/CitizensObjective.PNG"));
            objectiveCards[5] = ImageIO.read(GamePanel.class.getResource("/Images/WorkersObjective.png"));
            objectiveCards[6] = ImageIO.read(GamePanel.class.getResource("/Images/HermitsObjective.PNG"));
            objectiveCards[7] = ImageIO.read(GamePanel.class.getResource("/Images/FarmersObjective.PNG"));
            objectiveCards[8] = ImageIO.read(GamePanel.class.getResource("/Images/KnightsObjective.PNG"));
            objectiveCards[9] = ImageIO.read(GamePanel.class.getResource("/Images/MerchantsObjective.png"));
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
            TreeSet<Integer> LH = new TreeSet<>();
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
                    if (initBoard[i][j] >= 7) LH.add(initBoard[i][j]);
                }
            }
            int bruh = 0;
            for (int i : LH) {
                locationTiles[bruh] = ImageIO.read(GamePanel.class.getResource("/Images/LH" + i + ".png"));
                bruh++;
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

    private void beginGame(){
        //testing
        new GameState();
        GameState.players.add(new Player());
        GameState.players.add(new Player());
        GameState.players.add(new Player());
        GameState.players.add(new Player());
    }
    public void paint(Graphics g) {
        super.paintComponent(g);
        if(GameState.getState() == State.MAINMENU){
            paintMainMenu(g);
        }
        if(GameState.getState() == State.PLAYSETTLEMENTS || GameState.getState() == State.PLAYLOCATIONTILE || GameState.getState() == State.DRAWCARD || GameState.getState() == State.NEXTTURN){
            paintMainGameScene(g);
        }
    }
    private void paintMainMenu(Graphics g){
        g.drawImage(backgrounds[0], 0, 0,KingdomFrame.WIDTH,KingdomFrame.HEIGHT,null);
        g.setColor(new Color(211, 211, 211, 175));
        g.fillRoundRect(KingdomFrame.WIDTH/2-100, KingdomFrame.HEIGHT/3*2, 200, 100, 20,20);
        g.setColor(Color.BLACK);
        g.drawRoundRect(KingdomFrame.WIDTH/2-100, KingdomFrame.HEIGHT/3*2, 200, 100, 20,20);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString("PLAY", KingdomFrame.WIDTH/2-40, KingdomFrame.HEIGHT/3*2+60);
    }
    private void paintMainGameScene(Graphics g){
        g.drawImage(backgrounds[1], 0, 0,KingdomFrame.WIDTH,KingdomFrame.HEIGHT,null);
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
        drawPlayerTerrainCards(g);
        drawObjectiveCards(g);
        if(objectiveCardDisplay){
            displayObjectiveCards(g);
        }
    }
    private void drawDeckDiscard(Graphics g){
        g.setColor(Color.BLACK);
        double scale = 1.0f;
        g.drawImage(cardBack,KingdomFrame.WIDTH*8/15,650, (int)(cardBack.getWidth()*scale), (int)(cardBack.getHeight()*scale),null);
        if(deckClass.getDiscard().size()>0){
            g.drawImage(terrainCards[deckClass.getDiscard().get(deckClass.getDiscard().size()-1)],KingdomFrame.WIDTH*8/15,825, (int)(cardBack.getWidth()*scale), (int)(cardBack.getHeight()*scale),null);
        }else{
            g.setColor(new Color(255, 0, 0, 100));
            g.fillRoundRect(KingdomFrame.WIDTH*8/15,825, (int)(cardBack.getWidth()*scale), (int)(cardBack.getHeight()*scale), 25, 25);
            g.setColor(Color.WHITE);
            g.drawRoundRect(KingdomFrame.WIDTH*8/15,825, (int)(cardBack.getWidth()*scale), (int)(cardBack.getHeight()*scale), 25, 25);
            g.setColor(Color.BLACK);
        }
        g.drawString("Deck",  KingdomFrame.WIDTH*8/15, 812);
        g.drawString("Discard",  KingdomFrame.WIDTH*8/15, 987);
        g.setColor(Color.WHITE);
    }
    private void drawAllPlayerUI(Graphics g) {
        drawPlayerUI(g, GameState.players.get(0),0,KingdomFrame.WIDTH/3*2,0,KingdomFrame.WIDTH/3,KingdomFrame.HEIGHT/5);
        drawPlayerUI(g, GameState.players.get(1),1,KingdomFrame.WIDTH/3*2,KingdomFrame.HEIGHT/5,KingdomFrame.WIDTH/3,KingdomFrame.HEIGHT/5);
        drawPlayerUI(g, GameState.players.get(2),2,KingdomFrame.WIDTH/3*2,KingdomFrame.HEIGHT/5*2,KingdomFrame.WIDTH/3,KingdomFrame.HEIGHT/5);
        drawPlayerUI(g, GameState.players.get(3),3,KingdomFrame.WIDTH/3*2,KingdomFrame.HEIGHT/5*3,KingdomFrame.WIDTH/3,KingdomFrame.HEIGHT/5);
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
        g.drawImage(locationTiles[0],x+10,y+20,locationTiles[0].getWidth()/2,locationTiles[0].getWidth()/2, null);
        g.drawImage(locationTiles[1],x+width/2-10,y+20,locationTiles[0].getWidth()/2,locationTiles[0].getWidth()/2, null);
        g.drawImage(locationTiles[2],x+10,y+height/2,locationTiles[0].getWidth()/2,locationTiles[0].getWidth()/2, null);
        g.drawImage(locationTiles[3],x+width/2-10,y+height/2,locationTiles[0].getWidth()/2,locationTiles[0].getWidth()/2, null);
        g.drawString(p.locationTiles.get(0).toString(),x+5+locationTiles[0].getWidth()/2/2,y+20+locationTiles[0].getWidth()/2);
        g.drawString(p.locationTiles.get(1).toString(),x+locationTiles[0].getWidth()/2/2+width/2-15,y+20+locationTiles[0].getWidth()/2);
        g.drawString(p.locationTiles.get(2).toString(),x+5+locationTiles[0].getWidth()/2/2,y+20+locationTiles[0].getWidth());
        g.drawString(p.locationTiles.get(3).toString(),x+locationTiles[0].getWidth()/2/2+width/2-15,y+20+locationTiles[0].getWidth());
    }
    private void drawPlayerTerrainCards(Graphics g){
        for(int i = 0; i< GameState.players.size(); i++){
            g.drawImage(terrainCards[GameState.players.get(i).chosenCard],1150,KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80,(int)(terrainCards[GameState.players.get(i).chosenCard].getWidth()*1.23),(int)(terrainCards[GameState.players.get(i).chosenCard].getHeight()*1.23),null);
        }
    }

    private void drawObjectiveCards(Graphics g){
        g.setColor(new Color(0, 0, 0, 127));
        g.fillRoundRect(KingdomFrame.WIDTH*8/15-7, KingdomFrame.HEIGHT*1/32-7, (int)(objectiveCards[0].getWidth()*.4)+14, KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)-KingdomFrame.HEIGHT*1/32+14, 25, 50);
        g.setColor(Color.WHITE);
        g.drawRoundRect(KingdomFrame.WIDTH*8/15-7, KingdomFrame.HEIGHT*1/32-7, (int)(objectiveCards[0].getWidth()*.4)+14, KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)-KingdomFrame.HEIGHT*1/32+14, 25, 50);
        g.drawImage(objectiveCards[deckClass.getChosenObjectiveCards().get(0)], KingdomFrame.WIDTH*8/15, KingdomFrame.HEIGHT*1/32, (int)(objectiveCards[0].getWidth()*.4), (int)(objectiveCards[0].getHeight()*.4), null);
        g.drawImage(objectiveCards[deckClass.getChosenObjectiveCards().get(1)], KingdomFrame.WIDTH*8/15, KingdomFrame.HEIGHT*7/32, (int)(objectiveCards[0].getWidth()*.4), (int)(objectiveCards[0].getHeight()*.4), null);
        g.drawImage(objectiveCards[deckClass.getChosenObjectiveCards().get(2)], KingdomFrame.WIDTH*8/15, KingdomFrame.HEIGHT*13/32, (int)(objectiveCards[0].getWidth()*.4), (int)(objectiveCards[0].getHeight()*.4), null);
    }
    private void displayObjectiveCards(Graphics g){
        Color shadeBackground = new Color(0, 0, 0, 127);
        g.setColor(shadeBackground);
        g.fillRect(0, 0, KingdomFrame.WIDTH, KingdomFrame.HEIGHT);
        g.drawImage(objectiveCards[deckClass.getChosenObjectiveCards().get(0)], KingdomFrame.WIDTH*1/4-(int)(objectiveCards[0].getWidth()/2), KingdomFrame.HEIGHT/2-(int)(objectiveCards[0].getHeight()*1.5/2), (int)(objectiveCards[0].getWidth()*1.5), (int)(objectiveCards[0].getHeight()*1.5), null);
        g.drawImage(objectiveCards[deckClass.getChosenObjectiveCards().get(1)], KingdomFrame.WIDTH*2/4-(int)(objectiveCards[0].getWidth()/2), KingdomFrame.HEIGHT/2-(int)(objectiveCards[0].getHeight()*1.5/2), (int)(objectiveCards[0].getWidth()*1.5), (int)(objectiveCards[0].getHeight()*1.5), null);
        g.drawImage(objectiveCards[deckClass.getChosenObjectiveCards().get(2)], KingdomFrame.WIDTH*3/4-(int)(objectiveCards[0].getWidth()/2), KingdomFrame.HEIGHT/2-(int)(objectiveCards[0].getHeight()*1.5/2), (int)(objectiveCards[0].getWidth()*1.5), (int)(objectiveCards[0].getHeight()*1.5), null);
        g.setColor(Color.WHITE);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        int x = e.getX();
        int y = e.getY();
        cordXY = x + " " + y;
        GameHex[][] gm = GameState.board.GameMatrix;
        if(GameState.currentState == State.MAINMENU){
            if(x>KingdomFrame.WIDTH/2-100&&x<KingdomFrame.WIDTH/2+100&&y>KingdomFrame.HEIGHT/3*2&&y<KingdomFrame.HEIGHT/3*2+100){
                GameState.setState(State.DRAWCARD);
            }
        }
        else if (GameState.currentState == State.DRAWCARD) {
            
        }
        else if (GameState.currentState == State.PLAYSETTLEMENTS) {
            if(!objectiveCardDisplay){
                for(int i = 0; i<gm.length; i++){
                    for(int j = 0; j<gm[i].length; j++){
                        if((gm[i][j].x - x) * (gm[i][j].x - x) + (gm[i][j].y - y) *(gm[i][j].y - y) <= 24 * 24){

                            gm[i][j].highlighted = true;
                            curr_i = i;
                            curr_j = j;
                        }
                    }
                }
                if(x>KingdomFrame.WIDTH*8/15 && x<KingdomFrame.WIDTH*8/15+(int)(objectiveCards[0].getWidth()*.4) && y>KingdomFrame.HEIGHT*1/32 && y<KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)){
                    objectiveCardDisplay = true;
                }
            }else{
                objectiveCardDisplay = false;
            }
        }
        else if (GameState.currentState == State.PLAYLOCATIONTILE) {

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

