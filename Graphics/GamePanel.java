package Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextField;
import java.awt.Window;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.AttributeSet.ColorAttribute;
import Logic1.*;
import Logic1.GameState.State;
public class GamePanel extends JPanel implements MouseListener {
    private int[][] initBoard = new int[20][20];
    private int startX = 0;
    private int startY = 0;
    private BufferedImage[] boards = new BufferedImage[4];
    private BufferedImage[] locTileImages = new BufferedImage[4];
    public static int[] locTiles = new int[4]; 
    private BufferedImage[] settlements = new BufferedImage[4];
    private BufferedImage[] objectiveCards = new BufferedImage[10];
    private BufferedImage[] terrainCards = new BufferedImage[7];
    private BufferedImage cardBack, startToken, infoScreen;
    private BufferedImage[] backgrounds = new BufferedImage[5];
    private BufferedImage[] themePreviews = new BufferedImage[4];
    public ArrayList<Player> players;
    public HashMap<Integer, String> getObjStr = new HashMap<>();
    public static boolean[][] currentHighlights = new boolean[20][20];
    public boolean  cancelMoveLocationTile = false, drawCardWarning = false, nextTurnPossible = false;
    private boolean objectiveCardDisplay = false, infoScreenDisplay = false, themeSelection = false;
    private BufferedImage drawACard;
    private BufferedImage[] BOARDS = new BufferedImage[16];
    public GamePanel() {
        try {
            new GameState();
            getObjStr.put(0,"Miners");
            getObjStr.put(1,"Discoverers");
            getObjStr.put(2,"Lords");
            getObjStr.put(3,"Fishermen");
            getObjStr.put(4,"Citizens");
            getObjStr.put(5,"Workers");
            getObjStr.put(6,"Hermits");
            getObjStr.put(7,"Farmers");
            getObjStr.put(8,"Knights");
            getObjStr.put(9, "Merchants");
            startToken = ImageIO.read(GamePanel.class.getResource("/Images/startToken.png"));
            drawACard = ImageIO.read(GamePanel.class.getResource("/Images/DrawACard.png"));
            infoScreen = ImageIO.read(GamePanel.class.getResource("/Images/infoScreen.png"));
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
            backgrounds[1] = ImageIO.read(GamePanel.class.getResource("/Images/KB-BG.jpg"));
            backgrounds[2] = ImageIO.read(GamePanel.class.getResource("/Images/medievalBG.jpeg"));
            backgrounds[3] = ImageIO.read(GamePanel.class.getResource("/Images/oregonTrailBG.jpeg"));
            backgrounds[4] = ImageIO.read(GamePanel.class.getResource("/Images/byzantiumPreview.jpeg"));
            themePreviews[0] = ImageIO.read(GamePanel.class.getResource("/Images/KB-BG.jpg"));
            themePreviews[1] = ImageIO.read(GamePanel.class.getResource("/Images/medievalPreview.jpg"));
            themePreviews[2] = ImageIO.read(GamePanel.class.getResource("/Images/OregonTrail.jpg"));
            themePreviews[3] = ImageIO.read(GamePanel.class.getResource("/Images/byzantiumBG.jpeg"));
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
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        addMouseListener(this);
    }
    public void reset(){
        players = GameState.players;
        try{
            nextTurnPossible = false;
            objectiveCardDisplay = false;
            drawCardWarning = false;
            cancelMoveLocationTile = false;
            //BOARD RANDOMIZATION INITIALIZATION
            boolean[] used = new boolean[8];
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
                //Scanner sc = new Scanner(new File("/Boards/Board" + rand + ".txt"));
                InputStream inputStream = getClass().getResourceAsStream("/Boards/Board" + rand + ".txt");
                BufferedReader sc = new BufferedReader(new InputStreamReader(inputStream));

                int[][] tempBoard = new int[10][10];
                for (int j = 0; j < 10; j++) {
                    String[] line = sc.readLine().split(" ");
                    for (int r = 0; r < 10; r++) {
                        tempBoard[j][r] = Integer.parseInt(line[r]);
                    }
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
            //58(x) 52.8(y)
            double mult = 1.2;
            int width = (int)(KingdomFrame.WIDTH/3.125/mult);
            int height = (int)(KingdomFrame.HEIGHT/2/mult);
            int circleHeight = (int) ((double) height * (48.0 / 440.0)); int circleWidth = (int) ((double)width * (48.0 / 516.666667));
            Pair[][] centers = new Pair[20][20];
            for (int i = 0; i < 20; i++) {
                int y = (int) (startY + ((double) height * 0.00909091)) + (int) (((double)height * (42.50559555 / 440.0)) * i);
                for (int j = 0; j < 20; j++) {
                    int x = i % 2 == 0 ? (int) (((double) width * (1.5 / 516.6666666667))) + (int) (((double) width * (49.055555 / 516.66666667)) * (double) j) : (int) ((width * 0.05032258)) + (int) (((double) width * (49.055555 / 516.66666667)) * (double) j);
                    //g.drawOval(x, y, (int) 48, 48); //47 and 48 is hardcoded...
                    centers[i][j] = new Pair((x + (circleWidth / 2)), (y + (circleHeight / 2)));
                }
            }
            GameState.board = new GameBoard(initBoard, centers);
            GameState.boardInstance.add(getNonStaticBoard());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void paint(Graphics g) {
        super.paintComponent(g);
        if(GameState.getState() == State.MAINMENU){
            paintMainMenu(g);


            // //FOR TESTING PURPOSES
            // g.setFont(new Font("Times New Roman", 1, 15));
            // //drawForceEndButton(g);
        }
        else if(GameState.getState() == State.OBJECTIVECARD){
            drawChooseObjCardScene(g);
        }
        else if(GameState.getState() == State.ENDGAME){
            paintEndGame(g);
        }
        else if(GameState.getState() == State.BOARD){
            paintShowBoard(g);
            drawAllPlayerUI(g, false);
            drawObjectiveCards(g);
            if(objectiveCardDisplay) {
                displayObjectiveCards(g);
            }
        }
        else {
            paintMainGameScene(g);
            //FOR TESTING PURPOSES
            //drawForceEndButton(g);
        }
    }

    private void drawThemeSelection(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, KingdomFrame.WIDTH, KingdomFrame.HEIGHT);
        g.setColor(Constants.Colors.whiteFade);
        g.setFont(new Font("Times New Roman", 1, 50));
        g.drawString("Select a Theme | Click Empty Space to Exit", KingdomFrame.WIDTH / 2 - 350, 75);
        int cnt = 0;
        for (int i = 0; i < 2; i++) {
            int SX = 75 + (i * KingdomFrame.WIDTH / 2);
            for (int j = 0; j < 2; j++) {
                int SY = (100) + (j * (KingdomFrame.HEIGHT / 2 - 40));
                g.fillRect(SX, SY, KingdomFrame.WIDTH / 2 - 125, KingdomFrame.HEIGHT / 2 - 125);
                if (cnt == 0) {
                    g.setColor(new Color(117, 217, 179, 255));
                    g.drawString("Default", SX + (int) ((KingdomFrame.WIDTH / 2 - 125) / 2.85), SY + (KingdomFrame.HEIGHT / 2 - 125) + 50);
                    g.drawImage(themePreviews[0], SX, SY,  KingdomFrame.WIDTH / 2 - 125, KingdomFrame.HEIGHT / 2 - 125, null);
                }
                if (cnt == 1) {
                    g.setColor(new Color(201, 123, 20, 255));
                    g.drawString("Medieval Expedition", SX + (int) ((KingdomFrame.WIDTH / 2 - 125) / 8), SY + (KingdomFrame.HEIGHT / 2 - 125) + 50);
                    g.drawImage(themePreviews[1], SX, SY,  KingdomFrame.WIDTH / 2 - 125, KingdomFrame.HEIGHT / 2 - 125, null);
                }
                if (cnt == 2) {
                    g.setColor(new Color(255, 232, 117, 255));
                    g.drawString("Oregon Trail", SX + (int) ((KingdomFrame.WIDTH / 2 - 125) / 3.8), SY + (KingdomFrame.HEIGHT / 2 - 125) + 50);
                    g.drawImage(themePreviews[2], SX, SY,  KingdomFrame.WIDTH / 2 - 125, KingdomFrame.HEIGHT / 2 - 125, null);
                }
                if (cnt == 3) {
                    g.setColor(new Color(7, 72, 125, 255));
                    g.drawString("Constantinople", SX + (int) ((KingdomFrame.WIDTH / 2 - 125) / 3.8), SY + (KingdomFrame.HEIGHT / 2 - 125) + 50);
                    g.drawImage(themePreviews[3], SX, SY,  KingdomFrame.WIDTH / 2 - 125, KingdomFrame.HEIGHT / 2 - 125, null);
                }
                cnt++;
            }
        }
    }

    private void drawChooseObjCardScene(Graphics g){
        g.drawImage(backgrounds[GameState.currentTheme + 1], 0, 0, KingdomFrame.WIDTH, KingdomFrame.HEIGHT, null);
        g.setFont(new Font("Times New Roman", 1, 50));
        g.setColor(Color.BLACK);
        g.drawString("CHOOSE 3 OBJECTIVE CARDS", KingdomFrame.WIDTH/2-350, KingdomFrame.HEIGHT/7);
        g.setColor(new Color(255, 255, 255, 200));
        //g.setColor(new Color(217, 41, 51));
        g.drawString("CHOOSE 3 OBJECTIVE CARDS", KingdomFrame.WIDTH/2-350-1, KingdomFrame.HEIGHT/7-1);
        g.setColor(Constants.Colors.whiteFade);
        g.fillRoundRect(KingdomFrame.WIDTH/2-150, KingdomFrame.HEIGHT*6/7-25, 300, 100, 20, 20);
        g.setColor(Constants.Colors.blue);
        g.drawRoundRect(KingdomFrame.WIDTH/2-150, KingdomFrame.HEIGHT*6/7-25, 300, 100, 20, 20);
        g.setColor(Constants.Colors.cyan);
        g.setFont(new Font("Times New Roman", 1, 25));
        g.drawString("Random", KingdomFrame.WIDTH/2-48, KingdomFrame.HEIGHT*6/7+30);
        for(int i = 0; i<10; i++){
            if(!GameState.objCards.contains(i)){
                g.drawImage(objectiveCards[i], KingdomFrame.WIDTH*(i%5+1)/6-100, KingdomFrame.HEIGHT*(i/5+1)/3-150, 200, 300, null);
            }
        }
    }

    private void drawForceEndButton(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(KingdomFrame.WIDTH*20/100, KingdomFrame.HEIGHT*90/100, KingdomFrame.WIDTH*5/100, KingdomFrame.HEIGHT*5/100);
        g.setColor(Color.BLUE);
        g.drawString("FORCEEND", KingdomFrame.WIDTH*20/100, KingdomFrame.HEIGHT*93/100);
    }

    public void paintEndGame(Graphics g){
        g.drawImage(backgrounds[GameState.currentTheme + 1], 0, 0,KingdomFrame.WIDTH,KingdomFrame.HEIGHT,null);
        g.setColor(Constants.Colors.green);
        g.fillRoundRect(KingdomFrame.WIDTH*1/14, KingdomFrame.HEIGHT*1/14, KingdomFrame.WIDTH*12/14, KingdomFrame.HEIGHT*10/14, 100, 100);
        g.setColor(Constants.Colors.blue);
        g.drawRoundRect(KingdomFrame.WIDTH*1/14, KingdomFrame.HEIGHT*1/14, KingdomFrame.WIDTH*12/14, KingdomFrame.HEIGHT*10/14, 100, 100);
        Collections.sort(players);
        for(int i = 0; i<5; i++) {
            if(i < 4){
                g.drawLine(KingdomFrame.WIDTH * 1 / 14, KingdomFrame.HEIGHT * (3 + 2 * i) / 14, KingdomFrame.WIDTH * 13 / 14, KingdomFrame.HEIGHT * (3 + 2 * i) / 14);
            }
            g.drawLine(KingdomFrame.WIDTH * (3+2*i) / 14, KingdomFrame.HEIGHT * 1 / 14, KingdomFrame.WIDTH * (3+2*i) / 14, KingdomFrame.HEIGHT * 11 / 14);
        }
        g.setColor(Constants.Colors.whiteFade);
        g.fillRoundRect(KingdomFrame.WIDTH*3/9, KingdomFrame.HEIGHT*6/7, KingdomFrame.WIDTH/9, 100, 20, 20);
        g.fillRoundRect(KingdomFrame.WIDTH*5/9, KingdomFrame.HEIGHT*6/7, KingdomFrame.WIDTH/9, 100, 20, 20);
        g.setColor(Constants.Colors.cyan);
        g.drawRoundRect(KingdomFrame.WIDTH*3/9, KingdomFrame.HEIGHT*6/7, KingdomFrame.WIDTH/9, 100, 20, 20);
        g.drawRoundRect(KingdomFrame.WIDTH*5/9, KingdomFrame.HEIGHT*6/7, KingdomFrame.WIDTH/9, 100, 20, 20);
        g.setFont(new Font("Times New Roman", 1, 25));
        g.drawString("Game Review", KingdomFrame.WIDTH/9*3+36, KingdomFrame.HEIGHT*13/14-15);
        g.drawString("Main Menu", KingdomFrame.WIDTH/9*5+45, KingdomFrame.HEIGHT*13/14-15);
        g.setFont(new Font("Times New Roman", 1, 50));
        g.setColor(Color.BLACK);
        for(int j = 0; j<2; j++){
            g.drawString("Players", KingdomFrame.WIDTH*3/28-j, KingdomFrame.HEIGHT*9/56-j);
            g.drawString("Castle", KingdomFrame.WIDTH*7/28-j, KingdomFrame.HEIGHT*9/56-j);
            g.drawString("Total", KingdomFrame.WIDTH*23/28-j, KingdomFrame.HEIGHT*9/56-j);
            for(int i = 0; i<3; i++){
                g.drawString(getObjStr.get(GameState.deck.getChosenObjectiveCards().get(i)), KingdomFrame.WIDTH*(5+2*i)/14-j, KingdomFrame.HEIGHT*9/56-j);
            }
            g.setColor(Constants.Colors.cyan);
        }
        for(int i = 0; i<4; i++){
            g.setColor(Color.BLACK);
            for(int j = 0; j<2;j++){
                g.drawString((i + 1) + ". Player " + (GameState.players.get(i).num + 1), KingdomFrame.WIDTH * 1 / 14-(j), KingdomFrame.HEIGHT * (33+16*i) / 112-(j));
                g.drawString("" + GameState.players.get(i).getScore[10], KingdomFrame.WIDTH * 3 / 14-(j), KingdomFrame.HEIGHT * (33+16*i) / 112-(j));
                g.drawString(""+ GameState.players.get(i).getScore[GameState.deck.getChosenObjectiveCards().get(0)], KingdomFrame.WIDTH * 5 / 14-(j), KingdomFrame.HEIGHT * (33+16*i) / 112-(j));
                g.drawString(""+ GameState.players.get(i).getScore[GameState.deck.getChosenObjectiveCards().get(1)], KingdomFrame.WIDTH * 7 / 14-(j), KingdomFrame.HEIGHT * (33+16*i) / 112-(j));
                g.drawString(""+ GameState.players.get(i).getScore[GameState.deck.getChosenObjectiveCards().get(2)], KingdomFrame.WIDTH * 9 / 14-(j), KingdomFrame.HEIGHT * (33+16*i) / 112-(j));
                g.drawString("" + GameState.players.get(i).score, KingdomFrame.WIDTH * 11 / 14-(j), KingdomFrame.HEIGHT * (33+16*i) / 112-(j));
                g.setColor(getColor(GameState.players.get(i).num));
            }
        }
    }
    private void paintMainMenu(Graphics g){
        KingdomFrame.textField1.setBounds((int) (KingdomFrame.WIDTH/2-380), (int) (KingdomFrame.HEIGHT/3*2+260), 750, 60);
        KingdomFrame.textField1.setHorizontalAlignment(JTextField.CENTER);
        g.drawImage(backgrounds[0], 0, 0,KingdomFrame.WIDTH,KingdomFrame.HEIGHT,null);
        g.setColor(new Color(207, 206, 242, 175));
        g.fillRoundRect((int) (KingdomFrame.WIDTH/2-100), (int) (KingdomFrame.HEIGHT/3*2), 200, 100, 20,20);
        g.fillRoundRect((int) (KingdomFrame.WIDTH/2-380), (int) (KingdomFrame.HEIGHT/3*2 + 140), 750, 100, 20,20);
        g.setColor(new Color(110, 108, 166));
        g.drawRoundRect((int)(KingdomFrame.WIDTH/2-100), (int)(KingdomFrame.HEIGHT/3*2), 200, 100, 20,20);
        g.drawRoundRect((int)(KingdomFrame.WIDTH/2-380), (int)(KingdomFrame.HEIGHT/3*2 + 140), 750, 100, 20,20);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("PLAY", (int)(KingdomFrame.WIDTH/2-40), (int)(KingdomFrame.HEIGHT/3*2+60));
        g.drawString("Number of Settlements | Max: 65 | Min: 1 | Default: 40", (int)(KingdomFrame.WIDTH/2-340), (int)(KingdomFrame.HEIGHT/3*2+200));
        paintComponents(g);
    }
    private void paintMainGameScene(Graphics g) {
        float borderWidth = 2.0f; // Set the desired border width
        ((Graphics2D) g).setStroke(new BasicStroke(borderWidth));
        g.drawImage(backgrounds[GameState.currentTheme + 1], 0, 0,KingdomFrame.WIDTH,KingdomFrame.HEIGHT,null);
        g.setFont(new Font("Times New Roman", 1, 50));
        g.setColor(Constants.Colors.green);
        int boardEndX = (int) ((double)KingdomFrame.WIDTH / 1.9238477); int boardEndY = (int) ((double) KingdomFrame.HEIGHT / 1.22033898);
        g.fillRoundRect(0, 0, boardEndX + 5, boardEndY + 5, 50, 50);
        g.setColor(Constants.Colors.blue);
        g.drawRoundRect(0, 0, boardEndX + 5, boardEndY + 5, 50, 50);
        double mult = 1.2;
        int width = (int)(KingdomFrame.WIDTH/3.125/mult);
        int height = (int)(KingdomFrame.HEIGHT/2/mult);
        int hOffset = ((width - ((int)(32 / mult))));
        int vOffset = ((height - ((int)(18 / mult))));
        g.drawImage(boards[0], startX, startY, width, height, null);
        g.drawImage(boards[1], startX + hOffset, startY, width, height, null);
        g.drawImage(boards[2], startX, startY + vOffset, width, height, null);
        g.drawImage(boards[3], startX + hOffset, startY + vOffset, width, height, null);
        g.setColor(new Color(50, 50, 50));
        //58(x) 52.8(y)
        Pair[][] centers = new Pair[20][20];
        int circleHeight = (int) ((double) height * (48.0 / 440.0)); int circleWidth = (int) ((double)width * (48.0 / 516.666667));
        for (int i = 0; i < 20; i++) {
            int y = (int) (startY + ((double) height * 0.00909091)) + (int) (((double)height * (42.50559555 / 440.0)) * i);
            for (int j = 0; j < 20; j++) {
                int x = i % 2 == 0 ? (int) (((double) width * (1.5 / 516.6666666667))) + (int) (((double) width * (49.055555 / 516.66666667)) * (double) j) : (int) ((width * 0.05032258)) + (int) (((double) width * (49.055555 / 516.66666667)) * (double) j);
                if (currentHighlights[i][j]) {
                    Color shadeBackground = new Color(255, 255, 255, 120);
                    g.setColor(shadeBackground);
                    g.fillOval(x, y, (int) circleWidth, circleHeight); //47 and 48 is hardcoded...
                    g.setColor(getColor(GameState.currentPlayer));
                    g.drawOval(x, y, (int) circleWidth, circleHeight);
                    g.setColor(Color.BLACK);
                }
                if (GameState.tempChosenGameHex != null && GameState.tempChosenGameHex.pos.first == i && GameState.tempChosenGameHex.pos.second == j) {
                    Color shadeBackground = new Color(200, 200, 200, 100);
                    g.setColor(shadeBackground);
                    g.fillOval(x, y, (int) circleWidth, circleHeight); //47 and 48 is hardcoded...
                    g.setColor(Color.RED);
                    g.drawOval(x, y, (int) circleWidth, circleHeight);
                    g.setColor(Color.BLACK);
                }
                if (GameBoard.GameMatrix[i][j].isLocationTile) {
                    g.setFont(new Font("Times New Roman", 1, 30));
                    g.setColor(Color.GREEN);
                    if (GameBoard.GameMatrix[i][j].locationTileLeft < 1) g.setColor(Color.RED);
                    g.drawString(" " + GameState.board.GameMatrix[i][j].locationTileLeft, x + 10, y + 20);
                    g.setColor(Color.WHITE);
                }
                if (GameBoard.GameMatrix[i][j].player >= 0) {
                    g.drawImage(settlements[GameBoard.GameMatrix[i][j].player], x + 8, y + 5, settlements[0].getWidth() / 2, settlements[0].getHeight() / 2, null);
                }
            }
        }
        g.setColor(Constants.Colors.green);
        g.fillRoundRect(KingdomFrame.WIDTH - (KingdomFrame.WIDTH / 6) - 40, KingdomFrame.HEIGHT - (KingdomFrame.HEIGHT / 8) - 50, (KingdomFrame.WIDTH / 6), (KingdomFrame.HEIGHT / 8), 50, 50);
        g.setColor(Constants.Colors.blue);
        g.drawRoundRect(KingdomFrame.WIDTH - (KingdomFrame.WIDTH / 6) - 40, KingdomFrame.HEIGHT - (KingdomFrame.HEIGHT / 8) - 50, (KingdomFrame.WIDTH / 6), (KingdomFrame.HEIGHT / 8), 50, 50);
        g.drawString("Current Action: ", KingdomFrame.WIDTH - (KingdomFrame.WIDTH / 6), KingdomFrame.HEIGHT - (KingdomFrame.HEIGHT / 8));
        if (players.get(GameState.currentPlayer).selectedAction == -1) {
            BufferedImage s = settlements[GameState.currentPlayer];
            g.drawImage(s, KingdomFrame.WIDTH - (KingdomFrame.WIDTH / 18), KingdomFrame.HEIGHT - (KingdomFrame.HEIGHT / 7) - 3, s.getWidth() / 2, s.getHeight() / 2, null);
        }
        else {
            BufferedImage s = locTileImages[players.get(GameState.currentPlayer).selectedAction];
            g.drawImage(s, KingdomFrame.WIDTH - (KingdomFrame.WIDTH / 18) - 10, KingdomFrame.HEIGHT - (int)(KingdomFrame.HEIGHT / 6.1), s.getWidth() / 3, s.getHeight() / 3, null);
        }
        g.drawString("Actions Left: " + (players.get(GameState.currentPlayer).settleActionsLeft + players.get(GameState.currentPlayer).locActionsLeft) , KingdomFrame.WIDTH - (KingdomFrame.WIDTH / 6), KingdomFrame.HEIGHT - (KingdomFrame.HEIGHT / 12));
        if (nextTurnPossible) {
            int SX = (int) (KingdomFrame.WIDTH / 38.4); int SY = KingdomFrame.HEIGHT - (int) (KingdomFrame.HEIGHT / 6.6842);
            g.setColor(Constants.Colors.blue);
            g.drawRoundRect(SX - 1, SY - 1, 201, 51, 50, 50);
            g.setColor(Constants.Colors.green);
            g.fillRoundRect(SX, SY, 200, 50, 50, 50);
            g.setColor(Constants.Colors.blue);
            g.drawString("Finish Turn", SX + 20, SY + 35);
        }
        if (cancelMoveLocationTile) {
            int SX = (int) (KingdomFrame.WIDTH / 6); int SY = KingdomFrame.HEIGHT - (int) (KingdomFrame.HEIGHT / 6.6842);
            g.setColor(Constants.Colors.blue);
            g.drawRoundRect(SX - 1, SY - 1, 201, 51, 50, 50);
            g.setColor(Constants.Colors.green);
            g.fillRoundRect(SX, SY, 200, 50, 50, 50);
            g.setColor(Constants.Colors.blue);
            g.drawString("Cancel Hex", SX + 20, SY + 35);
        }
        g.setColor(Constants.Colors.cautionRed);
        g.fillRoundRect(30, KingdomFrame.HEIGHT - 90, 60, 60, 50, 50);
        g.setColor(Constants.Colors.infoOrange);
        g.drawRoundRect(30, KingdomFrame.HEIGHT - 90, 60, 60, 50, 50);
        g.setFont(new Font("Times New Roman", 1, 25));
        g.drawString("Info", 37, KingdomFrame.HEIGHT - 52);

        g.setColor(Constants.Colors.cautionRed);
        g.fillRoundRect(120, KingdomFrame.HEIGHT - 90, 88, 60, 50, 50);
        g.setColor(Constants.Colors.infoOrange);
        g.drawRoundRect(120, KingdomFrame.HEIGHT - 90, 88, 60, 50, 50);
        g.setFont(new Font("Times New Roman", 1, 25));
        g.drawString("Theme", 127, KingdomFrame.HEIGHT - 52);

        drawAllPlayerUI(g, true);
        drawDeckDiscard(g);
        drawPlayerTerrainCards(g);
        drawObjectiveCards(g);
        if(objectiveCardDisplay) {
            displayObjectiveCards(g);
        }
        if (infoScreenDisplay) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, KingdomFrame.WIDTH, KingdomFrame.HEIGHT);
            g.drawImage(infoScreen, 100, 100, KingdomFrame.WIDTH - 200, KingdomFrame.HEIGHT - 200, null);
            g.setColor(Constants.Colors.whiteFade);
            g.setFont(new Font("Times New Roman", 1, 50));
            g.drawString("READ CAREFULLY! Click Anywhere to Exit! READ CAREFULLY!", 150, 75);
        }
        if (themeSelection) {
            drawThemeSelection(g);
        }
    }
    private void drawDeckDiscard(Graphics g){
        g.setColor(Color.BLACK);
        double scale = 1.0f;
        g.setColor(Constants.Colors.green);
        g.fillRoundRect(KingdomFrame.WIDTH*8/15 - 7, 643, (int)(cardBack.getWidth()*scale) + 14, (int)((cardBack.getHeight()*scale) * 2.1) + 14, 50, 50);
        g.setColor(Constants.Colors.blue);
        g.drawRoundRect(KingdomFrame.WIDTH*8/15 - 7, 643, (int)(cardBack.getWidth()*scale) + 14, (int)((cardBack.getHeight()*scale) * 2.1) + 14, 50, 50);
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
        g.setFont(new Font("Times New Roman", 1, 20));
        g.setColor(new Color(255, 255, 255));
        g.drawString("Deck: " + GameState.deck.getDeck().size(),  KingdomFrame.WIDTH*8/15 + 15, 805);
        g.drawString("Discard",  KingdomFrame.WIDTH*8/15 + 20, 980);
        g.setColor(Color.WHITE);
    }
    private void drawAllPlayerUI(Graphics g, boolean shadeOutPlayers) {
        int SX = (int) ((double) KingdomFrame.WIDTH/2.7*2); int SY = 3;
        for (int i = 0; i < 4; i++) {
            drawPlayerUI(g, players.get(i), i, SX,SY + KingdomFrame.HEIGHT/5*i,KingdomFrame.WIDTH - SX - 5, KingdomFrame.HEIGHT/5);
        }
        for (int i = 0; i < 4; i++) {
            if (GameState.currentPlayer != i && shadeOutPlayers) {
                g.setColor(new Color(40, 40, 40, 200));
                g.fillRoundRect(SX, SY + (KingdomFrame.HEIGHT / 5 * i),KingdomFrame.WIDTH - SX - 5, KingdomFrame.HEIGHT/5,50,50);
            }
        }
    }
    private void drawPlayerUI(Graphics g , Player p, int playerNum,int x, int y, int width, int height){
        double settlementScale = 0.5;
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        //g.fillRoundRect(x - 2,y - 2,width + 4,height + 4,50,50);
        g.setColor(getColor(playerNum));
        g.fillRoundRect(x,y,width,height,50,50);
        g.setColor(Constants.Colors.blue);
        g.drawRoundRect(x,y,width,height,50,50);
        g.drawString("Player " + (playerNum+1),x+15,y+30);
        
        //SETTLEMENTS
        int SX = x+width/3; int SY = y + 6; int w = (int)(settlements[playerNum].getWidth()*settlementScale); int l = (int)(settlements[playerNum].getHeight()*settlementScale);
        g.setColor(Constants.Colors.blue);
        g.fillRoundRect(SX - 7, SY - 2, w + 14, l + 9,25,25);
        g.setColor(Constants.Colors.green);
        g.fillRoundRect(SX - 5, SY, w + 10,l + 5,25,25);
        g.setColor(Constants.Colors.blue);
        g.drawString(""+p.tilesLeft,SX + w + 10,SY+25);
        g.drawImage(settlements[playerNum], SX, SY,w,l, null);
        drawPlayerLocationTiles(g,p,playerNum,x,y,width,height);
    }
    private void drawPlayerLocationTiles(Graphics g,Player p, int playerNum,int x, int y, int width, int height){
        int SX = x + 10; int SY = y + (int) (height / 5);
        int a = 0;
        for (int i = 0; i < 2; i++) {
            int Y = SY + (i * (height/2 - 20));
            for (int j = 0; j < 2; j++) {
                int X = SX + (j * (int) (width/2 - 20));
                g.setColor(Constants.Colors.blue);
                g.drawRoundRect(X - 2, Y - 2, 99, 76,50,50);
                g.setColor(Constants.Colors.green);
                g.fillRoundRect(X, Y, 95, 72,50,50);
                g.drawImage(locTileImages[a], X, Y, 70, 70, null);
                a++;
            }
        }
        a = 0;
        for (int i = 0; i < 2; i++) {
            int SY2 = y+15+locTileImages[0].getWidth()/2-20 + ((locTileImages[0].getWidth()/2 - 10) * i);
            for (int j = 0; j < 2; j++) {
                int SX2 = x+locTileImages[0].getWidth()/2/2+35 + ((width/2-20) * j);
                if (p.roundLocTiles[a] < 1) g.setColor(Color.RED);
                else g.setColor(Color.GREEN);
                g.drawString(p.roundLocTiles[a] + "", SX2, SY2);
                a++;
            }
        }
        if (playerNum == 0) {
            g.drawImage(startToken, SX + 120, SY + 38, 90, 105, null);
        }
    }
    private void drawPlayerTerrainCards(Graphics g){
        int obEndX = KingdomFrame.WIDTH*8/15-12 + (int)(objectiveCards[0].getWidth()*.4)+1; // (objective card endX)
        int SX = obEndX + ((int) ((double) KingdomFrame.WIDTH/2.7*2) - obEndX) / 2 - ((int) (cardBack.getWidth()*1.23) / 2);
        g.setColor(Constants.Colors.green);
        g.fillRoundRect(SX - 15, KingdomFrame.HEIGHT/80 - 10, (int)(cardBack.getWidth()*1.4) + 10, KingdomFrame.HEIGHT/5*3+KingdomFrame.HEIGHT/80 - 5 + (int)(terrainCards[0].getHeight()*1.4) + 5, 50, 50);
        g.setColor(Constants.Colors.blue);
        g.drawRoundRect(SX - 15, KingdomFrame.HEIGHT/80 - 10, (int)(cardBack.getWidth()*1.4) + 10, KingdomFrame.HEIGHT/5*3+KingdomFrame.HEIGHT/80 - 5 + (int)(terrainCards[0].getHeight()*1.4) + 5, 50, 50);
        for(int i = 0; i < players.size(); i++){
            if (GameState.currentPlayer != i) {
                g.drawImage(cardBack, SX,KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80,(int)(cardBack.getWidth()*1.25),(int)(cardBack.getHeight()*1.25),null);
            }
            else if (players.get(i).chosenCard < 0) {
                if (drawCardWarning) {
                    g.setColor(Color.RED);
                    g.fillRect(SX - 5, KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80 - 5, (int)(terrainCards[0].getWidth()*1.23) + 10, (int)(terrainCards[0].getHeight()*1.23) + 10);
                }
                g.drawImage(drawACard, SX, KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80, (int)(terrainCards[0].getWidth()*1.23), (int)(terrainCards[0].getHeight()*1.23), null);
            }
            else {
                g.drawImage(terrainCards[players.get(i).chosenCard],SX,KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80,(int)(terrainCards[players.get(i).chosenCard].getWidth()*1.23),(int)(terrainCards[players.get(i).chosenCard].getHeight()*1.23),null);
            }
        }
    }

    private void drawObjectiveCards(Graphics g){
        g.setColor(Constants.Colors.green);
        g.fillRoundRect(KingdomFrame.WIDTH*8/15-12, KingdomFrame.HEIGHT*1/32-7, (int)(objectiveCards[0].getWidth()*.4)+14, KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)-KingdomFrame.HEIGHT*1/32+14, 25, 50);
        g.setColor(Constants.Colors.blue);
        g.drawRoundRect(KingdomFrame.WIDTH*8/15-12, KingdomFrame.HEIGHT*1/32-7, (int)(objectiveCards[0].getWidth()*.4)+14, KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)-KingdomFrame.HEIGHT*1/32+14, 25, 50);
        g.setFont(new Font("Times New Roman", 1, 15));
        int SX = KingdomFrame.WIDTH*8/15 - 5; int w = (int)(objectiveCards[0].getWidth()*.4); int l = (int)(objectiveCards[0].getHeight()*.4);
        g.drawString("Click To Enlarge", SX + 4, KingdomFrame.HEIGHT*(1 + (1 * 6))/32 - 20);
        for (int i = 0; i < 3; i++) {
            int SY = KingdomFrame.HEIGHT*(1 + (i * 6))/32;
            g.drawImage(objectiveCards[GameState.deck.getChosenObjectiveCards().get(i)], SX, SY, w, l, null);
        }
    }
    private void displayObjectiveCards(Graphics g){
        Color shadeBackground = new Color(0, 0, 0, 180);
        g.setColor(shadeBackground);
        g.fillRect(0, 0, KingdomFrame.WIDTH, KingdomFrame.HEIGHT);
        g.drawImage(objectiveCards[GameState.deck.getChosenObjectiveCards().get(0)], (int) (KingdomFrame.WIDTH*1/4)-(int)(objectiveCards[0].getWidth()/1.5), KingdomFrame.HEIGHT/2-(int)(objectiveCards[0].getHeight()*1.5/2), (int)(objectiveCards[0].getWidth()*1.5), (int)(objectiveCards[0].getHeight()*1.5), null);
        g.drawImage(objectiveCards[GameState.deck.getChosenObjectiveCards().get(1)], (int) (KingdomFrame.WIDTH*2/4)-(int)(objectiveCards[0].getWidth()/1.5), KingdomFrame.HEIGHT/2-(int)(objectiveCards[0].getHeight()*1.5/2), (int)(objectiveCards[0].getWidth()*1.5), (int)(objectiveCards[0].getHeight()*1.5), null);
        g.drawImage(objectiveCards[GameState.deck.getChosenObjectiveCards().get(2)], (int) (KingdomFrame.WIDTH*3/4)-(int)(objectiveCards[0].getWidth()/1.5), KingdomFrame.HEIGHT/2-(int)(objectiveCards[0].getHeight()*1.5/2), (int)(objectiveCards[0].getWidth()*1.5), (int)(objectiveCards[0].getHeight()*1.5), null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", 1, 50));
        g.drawString("Click Anywhere to Exit", (int)(KingdomFrame.WIDTH / 2.75), 150);
    }
    private void paintShowBoard(Graphics g){
        float borderWidth = 2.0f; // Set the desired border width
        ((Graphics2D) g).setStroke(new BasicStroke(borderWidth));
        g.drawImage(backgrounds[GameState.currentTheme + 1], 0, 0,KingdomFrame.WIDTH,KingdomFrame.HEIGHT,null);
        g.setColor(Constants.Colors.whiteFade);
        g.fillRoundRect(KingdomFrame.WIDTH*5/11, KingdomFrame.HEIGHT*9/11+15, KingdomFrame.WIDTH/11, KingdomFrame.HEIGHT/11, 20, 20);
        g.setColor(Constants.Colors.blue);
        g.drawRoundRect(KingdomFrame.WIDTH*5/11, KingdomFrame.HEIGHT*9/11+15, KingdomFrame.WIDTH/11, KingdomFrame.HEIGHT/11, 20, 20);
        g.setColor(Constants.Colors.cyan);
        g.setFont(new Font("Times New Roman", 1, 25));
        g.drawString("Leaderboard", KingdomFrame.WIDTH*5/11+20, KingdomFrame.HEIGHT*9/11+70);
        double mult = 1.2;
        int width = (int)(KingdomFrame.WIDTH/3.125/mult);
        int height = (int)(KingdomFrame.HEIGHT/2/mult);
        int hOffset = ((width - ((int)(32 / mult))));
        int vOffset = ((height - ((int)(18 / mult))));
        g.drawImage(boards[0], startX, startY, width, height, null);
        g.drawImage(boards[1], startX + hOffset, startY, width, height, null);
        g.drawImage(boards[2], startX, startY + vOffset, width, height, null);
        g.drawImage(boards[3], startX + hOffset, startY + vOffset, width, height, null);
        g.setColor(new Color(50, 50, 50));
        //58(x) 52.8(y)
        for (int i = 0; i < 20; i++) {
            int y = (int) (startY + ((double) height * 0.00909091)) + (int) (((double)height * (42.50559555 / 440.0)) * i);
            for (int j = 0; j < 20; j++) {
                int x = i % 2 == 0 ? (int) (((double) width * (1.5 / 516.6666666667))) + (int) (((double) width * (49.055555 / 516.66666667)) * (double) j) : (int) ((width * 0.05032258)) + (int) (((double) width * (49.055555 / 516.66666667)) * (double) j);
//                if (currentHighlights[i][j]) {
//                    Color shadeBackground = new Color(255, 255, 255, 120);
//                    g.setColor(shadeBackground);
//                    g.fillOval(x, y, (int) circleWidth, circleHeight); //47 and 48 is hardcoded...
//                    g.setColor(getColor(GameState.currentPlayer));
//                    g.drawOval(x, y, (int) circleWidth, circleHeight);
//                    g.setColor(Color.BLACK);
//                }
//                if (GameState.tempChosenGameHex != null && GameState.tempChosenGameHex.pos.first == i && GameState.tempChosenGameHex.pos.second == j) {
//                    Color shadeBackground = new Color(200, 200, 200, 100);
//                    g.setColor(shadeBackground);
//                    g.fillOval(x, y, (int) circleWidth, circleHeight); //47 and 48 is hardcoded...
//                    g.setColor(Color.RED);
//                    g.drawOval(x, y, (int) circleWidth, circleHeight);
//                    g.setColor(Color.BLACK);
//                }
                if (GameState.boardInstance.get(GameState.boardInstanceIdx)[i][j].isLocationTile) {
                    g.setFont(new Font("Times New Roman", 1, 30));
                    g.setColor(Color.GREEN);
                    if (GameState.boardInstance.get(GameState.boardInstanceIdx)[i][j].locationTileLeft < 1) g.setColor(Color.RED);
                    g.drawString(" " + GameState.boardInstance.get(GameState.boardInstanceIdx)[i][j].locationTileLeft, x + 10, y + 20);
                    g.setColor(Color.WHITE);
                }
                if (GameState.boardInstance.get(GameState.boardInstanceIdx)[i][j].player >= 0) {
                    g.drawImage(settlements[GameState.boardInstance.get(GameState.boardInstanceIdx)[i][j].player], x + 8, y + 5, settlements[0].getWidth() / 2, settlements[0].getHeight() / 2, null);
                }
            }
        }
        g.setColor(Constants.Colors.green);
        g.fillRoundRect(200, KingdomFrame.HEIGHT - 140, 200, 50, 20, 20);
        g.fillRoundRect(450, KingdomFrame.HEIGHT - 140, 200, 50, 20, 20);
        g.setColor(Constants.Colors.blue);
        g.drawRoundRect(200, KingdomFrame.HEIGHT - 140, 200, 50, 20, 20);
        g.drawRoundRect(450, KingdomFrame.HEIGHT - 140, 200, 50, 20, 20);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Back", 265, KingdomFrame.HEIGHT - 105);
        g.drawString("Forward", 485, KingdomFrame.HEIGHT - 105);
        g.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g.drawString("Move: " + GameState.boardInstanceIdx, 350, KingdomFrame.HEIGHT - 158);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        int x = e.getX();
        int y = e.getY();
        GameHex[][] gm = GameState.board.GameMatrix;
        int width = (int)(KingdomFrame.WIDTH/3.125/1.2);
        int height = (int)(KingdomFrame.HEIGHT/2/1.2);
        int circleHeight = (int) ((double) height * (48.0 / 440.0)); int circleWidth = (int) ((double)width * (48.0 / 516.666667));
        int boardEndX = (int) ((double)KingdomFrame.WIDTH / 1.9238477); int boardEndY = (int) ((double) KingdomFrame.HEIGHT / 1.22033898);
        //FORCEEND
        // if(x >= KingdomFrame.WIDTH*20/100 && x <= KingdomFrame.WIDTH*25/100 && y >= KingdomFrame.HEIGHT*90/100 && y <= KingdomFrame.HEIGHT*95/100 && !(GameState.currentState == State.ENDGAME)){
        //     GameState.setState(State.ENDGAME);
        //     GameState.update();
        // }
        //FORCEEND
        if (infoScreenDisplay) {
            infoScreenDisplay = false;
            repaint();
            return;
        }
        else if (x >= 30 && x <= 80 && y >= KingdomFrame.HEIGHT - 90 && y <= KingdomFrame.HEIGHT - 40) {
            //g.fillRoundRect(30, KingdomFrame.HEIGHT - 60, 50, 50, 50, 50);
            infoScreenDisplay = true;
            repaint();
            return;
        }
        if (themeSelection) {
            int cnt = 0;
            boolean ok = false;
            for (int i = 0; i < 2; i++) {
                int SX = 75 + (i * KingdomFrame.WIDTH / 2);
                for (int j = 0; j < 2; j++) {
                    int SY = (100) + (j * (KingdomFrame.HEIGHT / 2 - 40));
                    //g.fillRect(SX, SY, KingdomFrame.WIDTH / 2 - 125, KingdomFrame.HEIGHT / 2 - 125);
                    if (x >= SX && x <= SX + KingdomFrame.WIDTH / 2 - 125 && y >= SY && y <= SY + KingdomFrame.WIDTH / 2 - 125) {
                        GameState.currentTheme = cnt;
                        Constants.Colors.updateColors();
                        ok = true;
                    }
                    cnt++;
                }
            }
            themeSelection = false;
            repaint();
            return;
        }
        //g.fillRoundRect(120, KingdomFrame.HEIGHT - 90, 88, 60, 50, 50);
        else if (x >= 120 && x <= 208 && y >= KingdomFrame.HEIGHT - 90 && y <= KingdomFrame.HEIGHT - 40) {
            themeSelection = true;
            repaint();
            return;
        }
        if(GameState.currentState == State.MAINMENU){
            if(x>KingdomFrame.WIDTH/2-100&&x<KingdomFrame.WIDTH/2+100&&y>KingdomFrame.HEIGHT/3*2&&y<KingdomFrame.HEIGHT/3*2+100){
                GameState.reset();
                reset();
                String a = KingdomFrame.textField1.getText();
                if (a.length() > 0 && a.length() < 3 && (a.charAt(0) >= 48 && a.charAt(0) <= 57) && (a.charAt(a.length() - 1) >= 48 && a.charAt(a.length() - 1) <= 57) && (Integer.parseInt(a) < 66 && Integer.parseInt(a) > 0)) {
                    for (int i = 0; i < 4; i++) {
                        players.get(i).tilesLeft = Integer.parseInt(a);
                    }
                }
                remove(KingdomFrame.textField1);
                GameState.setState(State.OBJECTIVECARD);
            }
        }
        else if(GameState.currentState == State.OBJECTIVECARD){
            //g.drawRoundRect(KingdomFrame.WIDTH/2-150, KingdomFrame.HEIGHT*6/7-25, 300, 100, 20, 20);
            if(x>KingdomFrame.WIDTH/2-150 && x<KingdomFrame.WIDTH/2+150 && y>KingdomFrame.HEIGHT*6/7-25 && y<KingdomFrame.HEIGHT*6/7+75){
                GameState.randomSetObjCards();
            }
            else{
                for(int i = 0; i<10; i++){
                    //g.drawImage(objectiveCards[i], KingdomFrame.WIDTH*(i%5+1)/6-100, KingdomFrame.HEIGHT*(i/5+1)/3-150, 200, 300, null);
                    if(x>KingdomFrame.WIDTH*(i%5+1)/6-100 && x<KingdomFrame.WIDTH*(i%5+1)/6+100 && y>KingdomFrame.HEIGHT*(i/5+1)/3-150 && y<KingdomFrame.HEIGHT*(i/5+1)/3+150){
                        GameState.addObjCard(i);
                    }
                }
            }
            if(GameState.objCards.size()>=3){
                GameState.setState(State.PLAYSETTLEMENTS);
                GameState.update();
                infoScreenDisplay = true;
            }
        }
        else if (GameState.currentState == State.DRAWCARD) {
            if (objectiveCardDisplay) {
                objectiveCardDisplay = false;
            }
            else if (x >= KingdomFrame.WIDTH*8/15-12 && x <= KingdomFrame.WIDTH*8/15-12 + (int)(objectiveCards[0].getWidth()*.4)+14 && y >= KingdomFrame.HEIGHT*1/32-7 && y <= KingdomFrame.HEIGHT*1/32-7 + KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)-KingdomFrame.HEIGHT*1/32+14) {
                objectiveCardDisplay = true;
            }
            else{
                boolean ok = false;
                int obEndX = KingdomFrame.WIDTH*8/15-12 + (int)(objectiveCards[0].getWidth()*.4)+1; // (objective card endX)
                int SXX = obEndX + ((int) ((double) KingdomFrame.WIDTH/2.7*2) - obEndX) / 2 - ((int) (cardBack.getWidth()*1.23) / 2);
                for (int i = 0; i < 4; i++) {
                    if (i == GameState.currentPlayer && x >= SXX && x <= SXX + (int)(cardBack.getWidth()*1.25) && y >= KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80 && y <= KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80 + (int)(cardBack.getHeight()*1.25)) {
                        GameState.update();
                        GameState.currentState = GameState.State.PLAYSETTLEMENTS;
                        GameState.update();
                        ok = true;
                    }
                }
                //reference: X: KingdomFrame.WIDTH*8/15 Y: 650 WIDTH: (int)(cardBack.getWidth()*scale) HEIGHT: (int)(cardBack.getHeight()*scale)
                if ((x >= KingdomFrame.WIDTH*8/15) && (x <= KingdomFrame.WIDTH*8/15 + (int)(cardBack.getWidth())) && (y >= 650) && (y <= 650 + (int)(cardBack.getHeight()))) {
                    GameState.update();
                    GameState.setState(State.PLAYSETTLEMENTS);
                    GameState.update();
                    drawCardWarning = false;
                    ok = true;
                }
                else if (!ok) {
                    drawCardWarning = true;
                }
            }
        }
        else if (GameState.currentState == State.PLAYSETTLEMENTS) {
            if (objectiveCardDisplay) {
                objectiveCardDisplay = false;
            }
            else if (x >= KingdomFrame.WIDTH*8/15-12 && x <= KingdomFrame.WIDTH*8/15-12 + (int)(objectiveCards[0].getWidth()*.4)+14 && y >= KingdomFrame.HEIGHT*1/32-7 && y <= KingdomFrame.HEIGHT*1/32-7 + KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)-KingdomFrame.HEIGHT*1/32+14) {
                objectiveCardDisplay = true;
            }
            else{
                checkIfClickedOnLocOrSettle(x, y);
                //START PLACEMENT ON BOARD
                if (x >= 0 && x <= boardEndX && y >= 0 && y <= boardEndY && players.get(GameState.currentPlayer).settleActionsLeft >= 1 && GameState.players.get(GameState.currentPlayer).tilesLeft > 0) {
                    out: for(int i = 0; i<gm.length; i++){
                        for(int j = 0; j<gm[i].length; j++){
                            if((gm[i][j].x - x) * (gm[i][j].x - x) + (gm[i][j].y - y) *(gm[i][j].y - y) <= (circleWidth / 2) * (circleHeight / 2)) {
                                if (currentHighlights[i][j]) {
                                    players.get(GameState.currentPlayer).settlementLock = true;
                                    players.get(GameState.currentPlayer).settleActionsLeft--;
                                    players.get(GameState.currentPlayer).tilesLeft--;
                                    if (players.get(GameState.currentPlayer).tilesLeft < 1) {
                                        players.get(GameState.currentPlayer).settleActionsLeft = 0;
                                    }
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
                                    GameState.boardInstance.add(getNonStaticBoard());
                                    if (players.get(GameState.currentPlayer).settleActionsLeft < 1) {
                                        currentHighlights = new boolean[20][20];
                                        break out;
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
        }
        else if (GameState.currentState == State.PLAYADDLOCATIONTILE) {
            if (objectiveCardDisplay) {
                objectiveCardDisplay = false;
            }
            else if (x >= KingdomFrame.WIDTH*8/15-12 && x <= KingdomFrame.WIDTH*8/15-12 + (int)(objectiveCards[0].getWidth()*.4)+14 && y >= KingdomFrame.HEIGHT*1/32-7 && y <= KingdomFrame.HEIGHT*1/32-7 + KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)-KingdomFrame.HEIGHT*1/32+14) {
                objectiveCardDisplay = true;
            }
            else{
                checkIfClickedOnLocOrSettle(x, y);
                //START PLAY LOCATION TILE
                if (x >= 0 && x <= boardEndX && y >= 0 && y <= boardEndY && players.get(GameState.currentPlayer).locActionsLeft >= 1 && players.get(GameState.currentPlayer).roundLocTiles[players.get(GameState.currentPlayer).selectedAction] > 0 && GameState.players.get(GameState.currentPlayer).tilesLeft > 0) {
                    out: for(int i = 0; i<gm.length; i++){
                        for(int j = 0; j<gm[i].length; j++){
                            if((gm[i][j].x - x) * (gm[i][j].x - x) + (gm[i][j].y - y) *(gm[i][j].y - y) <= (circleWidth / 2) * (circleHeight / 2)) {
                                if (currentHighlights[i][j]) {
                                    players.get(GameState.currentPlayer).locActionsLeft--;
                                    gm[i][j].player = GameState.currentPlayer;
                                    players.get(GameState.currentPlayer).roundLocTiles[players.get(GameState.currentPlayer).selectedAction]--;
                                    players.get(GameState.currentPlayer).tilesLeft--;
                                    if (players.get(GameState.currentPlayer).tilesLeft < 1) {
                                        players.get(GameState.currentPlayer).roundLocTiles[players.get(GameState.currentPlayer).selectedAction] = 0;
                                        nextTurnPossible = true;
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
                                    GameState.boardInstance.add(getNonStaticBoard());
                                    if (players.get(GameState.currentPlayer).roundLocTiles[players.get(GameState.currentPlayer).selectedAction] < 1) {
                                        currentHighlights = new boolean[20][20];
                                        break out;
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

        }
        else if (GameState.currentState == State.PLAYMOVELOCATIONTILE) {
            if (objectiveCardDisplay) {
                objectiveCardDisplay = false;
            }
            else if (x >= KingdomFrame.WIDTH*8/15-12 && x <= KingdomFrame.WIDTH*8/15-12 + (int)(objectiveCards[0].getWidth()*.4)+14 && y >= KingdomFrame.HEIGHT*1/32-7 && y <= KingdomFrame.HEIGHT*1/32-7 + KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)-KingdomFrame.HEIGHT*1/32+14) {
                objectiveCardDisplay = true;
            }
            else{
                checkIfClickedOnLocOrSettle(x, y);
                //START PLAY LOCATION TILE
                if (x >= 0 && x <= boardEndX && y >= 0 && y <= boardEndY && players.get(GameState.currentPlayer).locActionsLeft >= 1 && players.get(GameState.currentPlayer).roundLocTiles[players.get(GameState.currentPlayer).selectedAction] > 0) {
                    out: for(int i = 0; i<gm.length; i++){
                        for(int j = 0; j<gm[i].length; j++){
                            if((gm[i][j].x - x) * (gm[i][j].x - x) + (gm[i][j].y - y) *(gm[i][j].y - y) <= (circleWidth / 2) * (circleHeight / 2)) {
                                if (currentHighlights[i][j]) {
                                    cancelMoveLocationTile = true;
                                    GameState.setState(State.MOVELOCATIONTILE);
                                    GameState.tempChosenGameHex = gm[i][j];
                                    GameState.update();
                                }
                            }
                        }
                    }
                }
                //END PLAY LOCATION TILE
            }
        }
        else if (GameState.currentState == State.MOVELOCATIONTILE) {
            if (objectiveCardDisplay) {
                objectiveCardDisplay = false;
            }
            else if (x >= KingdomFrame.WIDTH*8/15-12 && x <= KingdomFrame.WIDTH*8/15-12 + (int)(objectiveCards[0].getWidth()*.4)+14 && y >= KingdomFrame.HEIGHT*1/32-7 && y <= KingdomFrame.HEIGHT*1/32-7 + KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)-KingdomFrame.HEIGHT*1/32+14) {
                objectiveCardDisplay = true;
            }
            else{
                //START PLAY LOCATION TILE
                if (x >= 0 && x <= boardEndX && y >= 0 && y <= boardEndY && players.get(GameState.currentPlayer).locActionsLeft >= 1 && players.get(GameState.currentPlayer).roundLocTiles[players.get(GameState.currentPlayer).selectedAction] > 0) {
                    out: for(int i = 0; i<gm.length; i++){
                        for(int j = 0; j<gm[i].length; j++){
                            if((gm[i][j].x - x) * (gm[i][j].x - x) + (gm[i][j].y - y) *(gm[i][j].y - y) <= (circleWidth / 2) * (circleHeight / 2)) {
                                if (currentHighlights[i][j]) {
                                    players.get(GameState.currentPlayer).locActionsLeft--;
                                    gm[i][j].player = GameState.currentPlayer;
                                    players.get(GameState.currentPlayer).roundLocTiles[players.get(GameState.currentPlayer).selectedAction]--;
                                    GameState.tempChosenGameHex.player = -1;
                                    currentHighlights[i][j] = false;
                                    cancelMoveLocationTile = false;
                                    //if moved away from a loc tile
                                    for (int a = 0; a < 6; a++) {
                                        if (GameState.tempChosenGameHex.neighbors[a] != null && GameState.tempChosenGameHex.neighbors[a].terr >= 7 &&  GameState.tempChosenGameHex.neighbors[a].terr != 14) {
                                            GameState.tempChosenGameHex.neighbors[a].playerMoveFromLocTile();
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
                                    GameState.boardInstance.add(getNonStaticBoard());
                                    if (players.get(GameState.currentPlayer).roundLocTiles[players.get(GameState.currentPlayer).selectedAction] < 1) {
                                        currentHighlights = new boolean[20][20];
                                        break out;
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
        }
        else if(GameState.getState() == State.ENDGAME){
            if(x>KingdomFrame.WIDTH*3/9 && x<KingdomFrame.WIDTH*4/9 && y>KingdomFrame.HEIGHT*6/7 && y<KingdomFrame.HEIGHT*6/7+100){
                GameState.setState(State.BOARD);
                GameState.boardInstanceIdx = GameState.boardInstance.size() - 1;
            }
            if(x>KingdomFrame.WIDTH*5/9 && x<KingdomFrame.WIDTH*6/9 && y>KingdomFrame.HEIGHT*6/7 && y<KingdomFrame.HEIGHT*6/7+100){
                GameState.setState(State.MAINMENU);
                KingdomFrame.textField1 = new JTextField("40");
                KingdomFrame.createTextField1();
                add(KingdomFrame.textField1);
            }
        }
        else if(GameState.getState() == State.BOARD){
            if (objectiveCardDisplay) {
                objectiveCardDisplay = false;
            }
            else if (x >= KingdomFrame.WIDTH*8/15-12 && x <= KingdomFrame.WIDTH*8/15-12 + (int)(objectiveCards[0].getWidth()*.4)+14 && y >= KingdomFrame.HEIGHT*1/32-7 && y <= KingdomFrame.HEIGHT*1/32-7 + KingdomFrame.HEIGHT*13/32+(int)(objectiveCards[0].getHeight()*.4)-KingdomFrame.HEIGHT*1/32+14) {
                objectiveCardDisplay = true;
            }
            else{
                if(x>KingdomFrame.WIDTH*5/11 && x<KingdomFrame.WIDTH*6/11 && y>KingdomFrame.HEIGHT*9/11+15 && y<KingdomFrame.HEIGHT*10/11+15){
                    GameState.setState(State.ENDGAME);
                }
                //g.drawRoundRect(200, KingdomFrame.HEIGHT - 140, 200, 50, 20, 20);
                //g.drawRoundRect(450, KingdomFrame.HEIGHT - 140, 200, 50, 20, 20);
                if (x >= 200 && x <= 400 && y >= KingdomFrame.HEIGHT - 140 && y <= KingdomFrame.HEIGHT - 90) {
                    //back
                    GameState.boardInstanceIdx--;
                    if (GameState.boardInstanceIdx < 0) {
                        GameState.boardInstanceIdx = GameState.boardInstance.size() - 1;
                    }
                }
                if (x >= 450 && x <= 650 && y >= KingdomFrame.HEIGHT - 140 && y <= KingdomFrame.HEIGHT - 90) {
                    //foreward
                    GameState.boardInstanceIdx++;
                    if (GameState.boardInstanceIdx == GameState.boardInstance.size()) {
                        GameState.boardInstanceIdx = 0;
                    }
                }
            }
        }
        if (nextTurnPossible) {
            int SX = (int) (KingdomFrame.WIDTH / 38.4); int SY = KingdomFrame.HEIGHT - (int) (KingdomFrame.HEIGHT / 6.6842);
            if (x >= SX && x <= SX + 200 && y >= SY && y <= SY + 50) {
                GameState.setState(GameState.State.NEXTTURN);
                GameState.update();
                nextTurnPossible = false;
            }
        }
        if (cancelMoveLocationTile) {
            int SX = (int) (KingdomFrame.WIDTH / 6); int SY = KingdomFrame.HEIGHT - (int) (KingdomFrame.HEIGHT / 6.6842);
            if (x >= SX && x <= SX + 200 && y >= SY && y <= SY + 50) {
                GameState.setState(State.PLAYMOVELOCATIONTILE);
                GameState.tempChosenGameHex = null;
                GameState.update();
                cancelMoveLocationTile = false;
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
    public void checkIfClickedOnLocOrSettle(int x, int y) {
        int xRange = (int) ((double) KingdomFrame.WIDTH/2.7*2);
        int yRange = 3 + KingdomFrame.HEIGHT/5*(GameState.currentPlayer);

        int width = KingdomFrame.WIDTH - xRange - 5; int height = KingdomFrame.HEIGHT/5;
        int SX = xRange + 10; int SY = yRange + (int) (height / 5);
        int a = 0;
        for (int i = 0; i < 2; i++) {
            int Y = SY + (i * (height/2 - 20));
            for (int j = 0; j < 2; j++) {
                int X = SX + (j * (int) (width/2 - 20));
                if (!players.get(GameState.currentPlayer).settlementLock && players.get(GameState.currentPlayer).roundLocTiles[a] > 0 && 
                        x >= X - 2 && x <= X - 2 + 99 && y >= Y - 2 && y <= Y - 2 + 76) {
                    players.get(GameState.currentPlayer).selectedAction = a;
                    if (locTiles[players.get(GameState.currentPlayer).selectedAction] >= 11 && locTiles[players.get(GameState.currentPlayer).selectedAction] <= 13) {
                        GameState.setState(State.PLAYMOVELOCATIONTILE);
                    }
                    else GameState.setState(GameState.State.PLAYADDLOCATIONTILE);
                    GameState.update();
                }
                a++;
            }
        }
        int w = KingdomFrame.WIDTH - SX - 5; int l = KingdomFrame.HEIGHT/5;
        // START SETTLEMENT TILE SELECTION
        if (x >= (xRange + w / 3 - 7) && x <= (xRange + w / 3 - 7) + (int)(settlements[GameState.currentPlayer].getWidth()*0.5) + 14 &&
                y >= yRange - 1 && y <= yRange - 1 + (int)(settlements[GameState.currentPlayer].getHeight()*0.5) + 9) {
            players.get(GameState.currentPlayer).selectedAction = -1;
            GameState.currentState = GameState.State.PLAYSETTLEMENTS;
            GameState.update();
        }

        /*for(int i = 0; i < players.size(); i++){
            if (GameState.currentPlayer != i) {
                g.drawImage(cardBack, SX,KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80,(int)(cardBack.getWidth()*1.25),(int)(cardBack.getHeight()*1.25),null);
            }
            else if (players.get(i).chosenCard < 0) {
                if (drawCardWarning) {
                    g.setColor(Color.RED);
                    g.fillRect(SX - 5, KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80 - 5, (int)(terrainCards[0].getWidth()*1.23) + 10, (int)(terrainCards[0].getHeight()*1.23) + 10);
                }
                g.drawImage(drawACard, SX, KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80, (int)(terrainCards[0].getWidth()*1.23), (int)(terrainCards[0].getHeight()*1.23), null);
            }
            else {
                g.drawImage(terrainCards[players.get(i).chosenCard],SX,KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80,(int)(terrainCards[players.get(i).chosenCard].getWidth()*1.23),(int)(terrainCards[players.get(i).chosenCard].getHeight()*1.23),null);
            }
        }*/
        int obEndX = KingdomFrame.WIDTH*8/15-12 + (int)(objectiveCards[0].getWidth()*.4)+1; // (objective card endX)
        int SXX = obEndX + ((int) ((double) KingdomFrame.WIDTH/2.7*2) - obEndX) / 2 - ((int) (cardBack.getWidth()*1.23) / 2);
        for (int i = 0; i < 4; i++) {
            if (i == GameState.currentPlayer && x >= SXX && x <= SXX + (int)(cardBack.getWidth()*1.25) && y >= KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80 && y <= KingdomFrame.HEIGHT/5*i+KingdomFrame.HEIGHT/80 + (int)(cardBack.getHeight()*1.25)) {
                players.get(GameState.currentPlayer).selectedAction = -1;
                GameState.currentState = GameState.State.PLAYSETTLEMENTS;
                GameState.update();
            }
        }
        // END SETTLEMENT TILE SELECTION
    }
    public Color getColor(int p) {
        int a = 255;
        if (p == 0) return new Color(29,159,191,a);
        else if (p == 1) return new Color(11,150,50,a);
        else if (p == 2) return new Color(252,171,20,a);
        else if (p == 3) return new Color(191,179,13,a);
        return Color.BLACK;
    }
    //LOL WHAT IS THIS
    public GameHex[][] getNonStaticBoard() {
        GameHex[][] nonStatic = new GameHex[20][20];
        GameHex[][] board = GameBoard.GameMatrix;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                GameHex a = board[i][j];
                nonStatic[i][j] = new GameHex(a);
            }
        }
        return nonStatic;
    }
}
