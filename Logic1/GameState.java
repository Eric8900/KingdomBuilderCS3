package Logic1;
import java.util.*;
public class GameState {
	public static ArrayList<Player> players;
	public static int playerTurn;
	public static GameBoard board;
	public static State currentState;
	public static Deck deck;

	public static ArrayList<Integer> objCards;
	public static Sector[] sectors = new Sector[4];
	public enum State {
		DRAWCARD,PLAYSETTLEMENTS, PLAYLOCATIONTILE,NEXTTURN, MAINMENU
	}
	public GameState() {
		players = new ArrayList<Player>();
		playerTurn = 0;
		deck = new Deck();
		currentState = State.MAINMENU;
		objCards = new ArrayList<>();
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
		case PLAYLOCATIONTILE:
			new PlayLocationTile();
			break;
		case NEXTTURN:
			new NextTurn();
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
