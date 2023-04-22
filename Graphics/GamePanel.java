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
    private BufferedImage[] locTileImages = new BufferedImage[4];
    public static int[] locTiles = new int[4]; 
    private BufferedImage[] settlements = new BufferedImage[4];
    private BufferedImage[] objectiveCards = new BufferedImage[10];
    private BufferedImage[] terrainCards = new BufferedImage[7];
    private BufferedImage cardBack;
    private BufferedImage[] backgrounds = new BufferedImage[2];
    public ArrayList<Player> players;
    private String cordXY = "YOOO";
    private static int curr_i = -1;
    private static int curr_j = -1;
    public static boolean[][] currentHighlights = new boolean[20][20];
    public boolean nextTurnPossible = false;
    private boolean objectiveCardDisplay = false;
    private BufferedImage drawACard;
    public GamePanel() {
        try {
            new GameState();
            players = GameState.players;
            BufferedImage[] BOARDS = new BufferedImage[16];
            boolean[] used = new boolean[8];
            drawACard = ImageIO.read(GamePanel.class.getResource("/Images/DrawACard.png"));
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
            terrainCards[1] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Card-Canyon.png"));
            terrainCards[0] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Card-Desert.png"));
            terrainCards[3] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Card-Flower.png"));
            terrainCards[4] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Card-Forest.png"));
            terrainCards[6] = ImageIO.read(GamePanel.class.getResource("/Images/KB-Card-Meadow.png"));
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
                int rand = (int) (Math.random() * 8);
                while (used[rand]) {
                    rand = (int) (Math.random() * 8);
                }
                used[rand] = true;
                int rev = (int) (Math.random() * 2);
                if (rev == 1) {
                    boards[i] = BOARDS[rand + 8];
                    rand++;
                }
                else {
                    boards[i] = BOARDS[rand];
                    rand++;
                }
                Scanner sc = new Scanner(new File("Boards/Board" + rand + ".txt"));
                int[][] tempBoard = new int[10][10];
                for (int j = 0; j < 10; j++) {
                    for (int r = 0; r < 10; r++) {
                        tempBoard[j][r] = sc.nextInt();
                    }
                    sc.nextLine();
                }
                if (rev == 0) boardConfig[i] = tempBoard;
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
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
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
                    if (initBoard[i][j] >= 7 && initBoard[i][j] != 14) LH.add(initBoard[i][j]);
                }
            }
            int bruh = 0;
            for (int i : LH) {
                locTiles[bruh] = i;
                locTileImages[bruh] = ImageIO.read(GamePanel.class.getResource("/Images/LH" + i + ".png"));
                bruh++;
            }
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
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
            e.printStackTrace();
        }
        addMouseListener(this);
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
        g.setFont(new Font("Times New Roman", 1, 50));
        g.setColor(Color.WHITE);
        g.drawString(cordXY, 50, 1000);
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
                if (currentHighlights[i][j]) {
                    Color shadeBackground = new Color(200, 200, 200, 100);
                    g.setColor(shadeBackground);
                    g.fillOval(x, y, (int) 48, 48); //47 and 48 is hardcoded...
                    g.setColor(getColor(GameBoard.GameMatrix[i][j]));
                    g.drawOval(x, y, (int) 48, 48);
                    g.setColor(Color.BLACK);
                }
                if (GameBoard.GameMatrix[i][j].isLocationTile) {
                    g.setFont(new Font("Times New Roman", 1, 30));
                    g.setColor(Color.MAGENTA);
                    g.drawString(" " + GameState.board.GameMatrix[i][j].locationTileLeft, x + 10, y + 20);
                    g.setColor(Color.WHITE);
                }
                if (GameBoard.GameMatrix[i][j].player >= 0) {
                    g.drawImage(settlements[GameBoard.GameMatrix[i][j].player], x + 8, y + 5, settlements[0].getWidth() / 2, settlements[0].getHeight() / 2, null);
                }
            }
        }
        if (GameState.currentState == GameState.State.PLAYLOCATIONTILE || GameState.currentState == GameState.State.PLAYSETTLEMENTS) {
            g.setColor(Color.WHITE);
            g.drawString("Current Action: ", KingdomFrame.WIDTH - 600, 950);
            if (players.get(GameState.currentPlayer).selectedAction == -1) {
                BufferedImage s = settlements[GameState.currentPlayer];
                g.drawImage(s, KingdomFrame.WIDTH - 400, 930, s.getWidth() / 2, s.getHeight() / 2, null);
            }
            else {
                BufferedImage s = locTileImages[players.get(GameState.currentPlayer).selectedAction];
                g.drawImage(s, KingdomFrame.WIDTH - 400, 890, s.getWidth() / 2, s.getHeight() / 2, null);
            }
            g.drawString("Actions Left: " + (players.get(GameState.currentPlayer).settleActionsLeft + players.get(GameState.currentPlayer).locActionsLeft) , KingdomFrame.WIDTH - 300, 950);
            if (nextTurnPossible) {
                g.fillRect(770, 880, 200, 50);
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
        if(GameState.deck.getDiscard().size()>0){
            g.drawImage(terrainCards[GameState.deck.getDiscard().get(GameState.deck.getDiscard().size()-1)],KingdomFrame.WIDTH*8/15,825, (int)(cardBack.getWidth()*scale), (int)(cardBack.getHeight()*scale),null);
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
        drawPlayerUI(g, players.get(0),0,KingdomFrame.WIDTH/3*2,0,KingdomFrame.WIDTH/3,KingdomFrame.HEIGHT/5);
        drawPlayerUI(g, players.get(1),1,KingdomFrame.WIDTH/3*2,KingdomFrame.HEIGHT/5,KingdomFrame.WIDTH/3,KingdomFrame.HEIGHT/5);
        drawPlayerUI(g, players.get(2),2,KingdomFrame.WIDTH/3*2,KingdomFrame.HEIGHT/5*2,KingdomFrame.WIDTH/3,KingdomFrame.HEIGHT/5);
        drawPlayerUI(g, players.get(3),3,KingdomFrame.WIDTH/3*2,KingdomFrame.HEIGHT/5*3,KingdomFrame.WIDTH/3,KingdomFrame.HEIGHT/5);
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
        //SETTLEMENTS
        g.drawImage(settlements[playerNum], x+width/3, y,(int)(settlements[playerNum].getWidth()*settlementScale),(int)(settlements[playerNum].getHeight()*settlementScale), null);
        drawPlayerLocationTiles(g,p,playerNum,x,y,width,height);
    }
    private void drawPlayerLocationTiles(Graphics g,Player p, int playerNum,int x, int y, int width, int height){
        g.drawImage(locTileImages[0],x+10,y+20,locTileImages[0].getWidth()/2,locTileImages[0].getWidth()/2, null);
        g.drawImage(locTileImages[1],x+width/2-10,y+20,locTileImages[0].getWidth()/2,locTileImages[0].getWidth()/2, null);
        g.drawImage(locTileImages[2],x+10,y+height/2,locTileImages[0].getWidth()/2,locTileImages[0].getWidth()/2, null);
        g.drawImage(locTileImages[3],x+width/2-10,y+height/2,locTileImages[0].getWidth()/2,locTileImages[0].getWidth()/2, null);
        g.drawString(p.roundLocTiles[0] + "",x+5+locTileImages[0].getWidth()/2/2,y+20+locTileImages[0].getWidth()/2);
        g.drawString(p.roundLocTiles[1] + "",x+locTileImages[0].getWidth()/2/2+width/2-15,y+20+locTileImages[0].getWidth()/2);
        g.drawString(p.roundLocTiles[2] + "",x+5+locTileImages[0].getWidth()/2/2,y+20+locTileImages[0].getWidth());
        g.drawString(p.roundLocTiles[3] + "",x+locTileImages[0].getWidth()/2/2+width/2-15,y+20+locTileImages[0].getWidth());
    }
    private void drawPlayerTerrainCards(Graphics g){
        for(int i = 0; i < players.size(); i++){
            if (GameState.currentPlayer != i) {
                g.drawImage(cardBack, 1150,KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80,(int)(cardBack.getWidth()*1.23),(int)(cardBack.getHeight()*1.23),null);
            }
            else if (players.get(i).chosenCard < 0) {
                g.drawImage(drawACard, 1150, KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80, (int)(terrainCards[0].getWidth()*1.23), (int)(terrainCards[0].getHeight()*1.23), null);
            }
            else {
                System.out.println(players.get(i).chosenCard);
                g.drawImage(terrainCards[players.get(i).chosenCard],1150,KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80,(int)(terrainCards[players.get(i).chosenCard].getWidth()*1.23),(int)(terrainCards[players.get(i).chosenCard].getHeight()*1.23),null);
            }
        }
    }

    private void drawObjectiveCards(Graphics g){
        g.setColor(new Color(0, 0, 0, 127));
        g.fillRoundRect(KingdomFrame.WIDTH*8/15-7, KingdomFrame.HEIGHT*1/32-7, (int)(objectiveCards[0].getWidth()*.4)+14, KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)-KingdomFrame.HEIGHT*1/32+14, 25, 50);
        g.setColor(Color.WHITE);
        g.drawRoundRect(KingdomFrame.WIDTH*8/15-7, KingdomFrame.HEIGHT*1/32-7, (int)(objectiveCards[0].getWidth()*.4)+14, KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)-KingdomFrame.HEIGHT*1/32+14, 25, 50);
        g.drawImage(objectiveCards[GameState.deck.getChosenObjectiveCards().get(0)], KingdomFrame.WIDTH*8/15, KingdomFrame.HEIGHT*1/32, (int)(objectiveCards[0].getWidth()*.4), (int)(objectiveCards[0].getHeight()*.4), null);
        g.drawImage(objectiveCards[GameState.deck.getChosenObjectiveCards().get(1)], KingdomFrame.WIDTH*8/15, KingdomFrame.HEIGHT*7/32, (int)(objectiveCards[0].getWidth()*.4), (int)(objectiveCards[0].getHeight()*.4), null);
        g.drawImage(objectiveCards[GameState.deck.getChosenObjectiveCards().get(2)], KingdomFrame.WIDTH*8/15, KingdomFrame.HEIGHT*13/32, (int)(objectiveCards[0].getWidth()*.4), (int)(objectiveCards[0].getHeight()*.4), null);
    }
    private void displayObjectiveCards(Graphics g){
        Color shadeBackground = new Color(0, 0, 0, 127);
        g.setColor(shadeBackground);
        g.fillRect(0, 0, KingdomFrame.WIDTH, KingdomFrame.HEIGHT);
        g.drawImage(objectiveCards[GameState.deck.getChosenObjectiveCards().get(0)], KingdomFrame.WIDTH*1/4-(int)(objectiveCards[0].getWidth()/2), KingdomFrame.HEIGHT/2-(int)(objectiveCards[0].getHeight()*1.5/2), (int)(objectiveCards[0].getWidth()*1.5), (int)(objectiveCards[0].getHeight()*1.5), null);
        g.drawImage(objectiveCards[GameState.deck.getChosenObjectiveCards().get(1)], KingdomFrame.WIDTH*2/4-(int)(objectiveCards[0].getWidth()/2), KingdomFrame.HEIGHT/2-(int)(objectiveCards[0].getHeight()*1.5/2), (int)(objectiveCards[0].getWidth()*1.5), (int)(objectiveCards[0].getHeight()*1.5), null);
        g.drawImage(objectiveCards[GameState.deck.getChosenObjectiveCards().get(2)], KingdomFrame.WIDTH*3/4-(int)(objectiveCards[0].getWidth()/2), KingdomFrame.HEIGHT/2-(int)(objectiveCards[0].getHeight()*1.5/2), (int)(objectiveCards[0].getWidth()*1.5), (int)(objectiveCards[0].getHeight()*1.5), null);
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
            //reference: X: KingdomFrame.WIDTH*8/15 Y: 650 WIDTH: (int)(cardBack.getWidth()*scale) HEIGHT: (int)(cardBack.getHeight()*scale)
            if ((x >= KingdomFrame.WIDTH*8/15) && (x <= KingdomFrame.WIDTH*8/15 + (int)(cardBack.getWidth())) && (y >= 650) && (y <= 650 + (int)(cardBack.getHeight()))) {
                System.out.println("YOU CLICKED THE DECK");
                GameState.update();
                GameState.setState(State.PLAYSETTLEMENTS);
                GameState.update();
            }
        }
        else if (GameState.currentState == State.PLAYSETTLEMENTS) {
            //START LOCATION TILE SELECTION
            int xRange = KingdomFrame.WIDTH/3*2;
            int yRange = KingdomFrame.HEIGHT/5 * (GameState.currentPlayer);
            int width = KingdomFrame.WIDTH/3; int height = KingdomFrame.HEIGHT/5;
            int w = locTileImages[0].getWidth()/2; int h = locTileImages[0].getWidth()/2;
            //top left 0
            if (x >= xRange + 10 && x <= xRange + 10 + w && y >= yRange + 20 && y <= yRange + 20 + h && !players.get(GameState.currentPlayer).settlementLock) {
                players.get(GameState.currentPlayer).selectedAction = 0;
                GameState.setState(GameState.State.PLAYLOCATIONTILE);
                GameState.update();
            }
            //top right 1
            if (x >= (xRange + width/2-10) && x <= (xRange + width/2-10) + w && y >= yRange + 20 && y <= yRange + 20 + h && !players.get(GameState.currentPlayer).settlementLock) {
                players.get(GameState.currentPlayer).selectedAction = 1;
                GameState.setState(GameState.State.PLAYLOCATIONTILE);
                GameState.update();
            }
            //bot left 2
            if (x >= xRange + 10 && x <= xRange + 10 + w && y >= (yRange+height/2) && y <= (yRange+height/2) + h && !players.get(GameState.currentPlayer).settlementLock) {
                players.get(GameState.currentPlayer).selectedAction = 2;
                GameState.setState(GameState.State.PLAYLOCATIONTILE);
                GameState.update();
            }
            //bot right 3
            if (x >= (xRange + width/2-10) && x <= (xRange + width/2-10) + w && y >= (yRange+height/2) && y <= (yRange+height/2) + h && !players.get(GameState.currentPlayer).settlementLock) {
                players.get(GameState.currentPlayer).selectedAction = 3;
                GameState.setState(GameState.State.PLAYLOCATIONTILE);
                GameState.update();
            }
            //END LOCATION TILE SELECTION
            //START PLACEMENT ON BOARD
            if (x >= 0 && x <= 1005 && y >= 0 && y <= 870 && players.get(GameState.currentPlayer).settleActionsLeft >= 1) {
                out: for(int i = 0; i<gm.length; i++){
                    for(int j = 0; j<gm[i].length; j++){
                        if((gm[i][j].x - x) * (gm[i][j].x - x) + (gm[i][j].y - y) *(gm[i][j].y - y) <= 24 * 24) {
                            if (currentHighlights[i][j]) {
                                players.get(GameState.currentPlayer).settlementLock = true;
                                players.get(GameState.currentPlayer).settleActionsLeft--;
                                gm[i][j].player = GameState.currentPlayer;

                                //check if it's adj to a loc tile
                                for (int a = 0; a < 6; a++) {
                                    if (gm[i][j].neighbors[a] != null && gm[i][j].neighbors[a].terr >= 7 && gm[i][j].neighbors[a].terr != 14) {
                                        for (int c = 0; c < locTiles.length; c++) {
                                            if (gm[i][j].neighbors[a].terr == locTiles[c] && !gm[i][j].neighbors[a].locationTilePlayers[GameState.currentPlayer] && gm[i][j].neighbors[a].locationTileLeft > 0) {
                                                gm[i][j].neighbors[a].updateLocTile();
                                                gm[i][j].neighbors[a].locationTileLeft--;
                                                players.get(GameState.currentPlayer).locationTiles[c]++;
                                            }
                                        }
                                    }
                                }
                                GameState.update();
                                break out;
                            }
                        }
                    }
                }
            }
            if (players.get(GameState.currentPlayer).settleActionsLeft < 1) {
                players.get(GameState.currentPlayer).settlementLock = false;
                nextTurnPossible = true;
            }
            //END PLACEMENT ON BOARD
        }
        else if (GameState.currentState == State.PLAYLOCATIONTILE) {
            int xRange = KingdomFrame.WIDTH/3*2;
            int yRange = KingdomFrame.HEIGHT/5 * (GameState.currentPlayer);
            int width = KingdomFrame.WIDTH/3; int height = KingdomFrame.HEIGHT/5;
            int w = locTileImages[0].getWidth()/2; int h = locTileImages[0].getWidth()/2;
            // START SETTLEMENT TILE SELECTION
            if (x >= (xRange + width / 3) && x <= (xRange + width / 3) + (int)(settlements[GameState.currentPlayer].getWidth()*0.5) &&
                y >= yRange && y <= yRange + (int)(settlements[GameState.currentPlayer].getHeight()*0.5)) {
                    players.get(GameState.currentPlayer).selectedAction = -1;
                    GameState.currentState = GameState.State.PLAYSETTLEMENTS;
                    GameState.update();
            }
            // END SETTLEMENT TILE SELECTION
            //START LOCATION TILE SELECTION
            if (x >= xRange + 10 && x <= xRange + 10 + w && y >= yRange + 20 && y <= yRange + 20 + h && !players.get(GameState.currentPlayer).settlementLock && players.get(GameState.currentPlayer).roundLocTiles[0] > 0) {
                players.get(GameState.currentPlayer).selectedAction = 0;
                GameState.setState(GameState.State.PLAYLOCATIONTILE);
                GameState.update();
            }
            //top right 1
            if (x >= (xRange + width/2-10) && x <= (xRange + width/2-10) + w && y >= yRange + 20 && y <= yRange + 20 + h && !players.get(GameState.currentPlayer).settlementLock && players.get(GameState.currentPlayer).roundLocTiles[1] > 0) {
                players.get(GameState.currentPlayer).selectedAction = 1;
                GameState.setState(GameState.State.PLAYLOCATIONTILE);
                GameState.update();
            }
            //bot left 2
            if (x >= xRange + 10 && x <= xRange + 10 + w && y >= (yRange+height/2) && y <= (yRange+height/2) + h && !players.get(GameState.currentPlayer).settlementLock && players.get(GameState.currentPlayer).roundLocTiles[2] > 0) {
                players.get(GameState.currentPlayer).selectedAction = 2;
                GameState.setState(GameState.State.PLAYLOCATIONTILE);
                GameState.update();
            }
            //bot right 3
            if (x >= (xRange + width/2-10) && x <= (xRange + width/2-10) + w && y >= (yRange+height/2) && y <= (yRange+height/2) + h && !players.get(GameState.currentPlayer).settlementLock && players.get(GameState.currentPlayer).roundLocTiles[3] > 0) {
                players.get(GameState.currentPlayer).selectedAction = 3;
                GameState.setState(GameState.State.PLAYLOCATIONTILE);
                GameState.update();
            }
            //END LOCATION TILE SELECTION
            //START PLAY LOCATION TILE
            if (x >= 0 && x <= 1005 && y >= 0 && y <= 870 && players.get(GameState.currentPlayer).locActionsLeft >= 1) {
                out: for(int i = 0; i<gm.length; i++){
                    for(int j = 0; j<gm[i].length; j++){
                        if((gm[i][j].x - x) * (gm[i][j].x - x) + (gm[i][j].y - y) *(gm[i][j].y - y) <= 24 * 24) {
                            if (currentHighlights[i][j]) {
                                players.get(GameState.currentPlayer).locActionsLeft--;
                                gm[i][j].player = GameState.currentPlayer;
                                players.get(GameState.currentPlayer).roundLocTiles[players.get(GameState.currentPlayer).selectedAction]--;
                                if (players.get(GameState.currentPlayer).roundLocTiles[players.get(GameState.currentPlayer).selectedAction] < 1) {
                                    boolean ok = false;
                                    for (int a = 0; a < 4; a++) {
                                        if (players.get(GameState.currentPlayer).roundLocTiles[i] > 0) {
                                            players.get(GameState.currentPlayer).selectedAction = i;
                                            ok = true;
                                        }
                                    }
                                    
                                    if (!ok) {
                                        players.get(GameState.currentPlayer).selectedAction = -1;
                                        GameState.setState(GameState.State.PLAYSETTLEMENTS);
                                    }
                                }

                                //check if it's adj to a loc tile
                                for (int a = 0; a < 6; a++) {
                                    if (gm[i][j].neighbors[a] != null && gm[i][j].neighbors[a].terr >= 7 && gm[i][j].neighbors[a].terr != 14) {
                                        for (int c = 0; c < locTiles.length; c++) {
                                            if (gm[i][j].neighbors[a].terr == locTiles[c] && !gm[i][j].neighbors[a].locationTilePlayers[GameState.currentPlayer] && gm[i][j].neighbors[a].locationTileLeft > 0) {
                                                gm[i][j].neighbors[a].updateLocTile();
                                                gm[i][j].neighbors[a].locationTileLeft--;
                                                players.get(GameState.currentPlayer).locationTiles[c]++;
                                            }
                                        }
                                    }
                                }
                                GameState.update();
                                break out;
                            }
                        }
                    }
                }
            }
            //END PLAY LOCATION TILE
        }
        else if (GameState.currentState == State.NEXTTURN) {

        }
        if (nextTurnPossible) {
            if (x >= 770 && x <= 770 + 200 && y >= 880 && y <= 930) {
                GameState.setState(GameState.State.NEXTTURN);
                GameState.update();
                nextTurnPossible = false;
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
    public Color getColor(GameHex a) {
        if (a.terr == 3) return Color.RED;
        else if (a.terr == 5) return Color.BLUE;
        else if (a.terr == 0) return new Color(145, 103, 0);
        else if (a.terr == 1) return new Color(237, 172, 14);
        else if (a.terr == 4) return new Color(7, 69, 0);
        else if (a.terr == 6) return new Color(88, 255, 78);
        return Color.BLACK;
    }
}

