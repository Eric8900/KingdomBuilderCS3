package Logic1;
import java.util.*;
public class GameState {
	public static ArrayList<Player> players;
	public static GameBoard board;
	public static State currentState;
	public static Deck deck;
	public static int currentPlayer;
	public static GameHex tempChosenGameHex = null;
	public static ArrayList<Integer> objCards;
	public static Sector[] sectors = new Sector[4];
	public enum State {
		DRAWCARD, PLAYSETTLEMENTS, PLAYADDLOCATIONTILE, PLAYMOVELOCATIONTILE, MOVELOCATIONTILE, NEXTTURN, MAINMENU, ENDGAME
	}
	public GameState() {
		players = new ArrayList<Player>();
		deck = new Deck();
		currentState = State.MAINMENU;
		objCards = new ArrayList<>();
		currentPlayer = 0;
		for (int i = 0; i < 4; i++) {
			players.add(new Player(i));
		}
		for(int i = 0; i<4; i++){
			sectors[i] = new Sector(new Pair[4], i, new Pair[2]);
		}
	}
	public static void update() {
		switch(currentState){
		case DRAWCARD:
			new DrawCard();
			break;
		case PLAYSETTLEMENTS:
			new PlaySettlements();
			break;
		case PLAYADDLOCATIONTILE:
			new PlayAddLocationTile();
			break;
		case PLAYMOVELOCATIONTILE:
			new PlayMoveLocationTile();
			break;
		case MOVELOCATIONTILE:
			new MoveLocationTile(tempChosenGameHex);
			break;
		case NEXTTURN:
			new NextTurn();
			break;
		case ENDGAME:
			new PostGame(null, null);
			break;
		default:
		}
	}
	public static State getState() {
		return currentState;
	}

	public static void setState(State gs) {
		currentState = gs;
	}
	public static ArrayList<Pair> getValidSettlements(Player p){
		return null;
	}
	public static ArrayList<Pair> getValidTavernSettlements(){
		return null;
	}
	public static ArrayList<Pair> getValidHarborMoves(){
		return null;
	}
	public static ArrayList<Pair> getValidBarnMoves(){
		return null;
	}
	public static ArrayList<Pair> getValidPaddockMoves(){
		return null;
	}
	
	
	
}
