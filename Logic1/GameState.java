package Logic1;
import java.util.*;
public class GameState {
	public static ArrayList<Player> players;
	public static int playerTurn;
	public static GameBoard board;
	public static State currentState;
	public static ArrayList<Integer> deck;
	public static ArrayList<Integer> discard;
	public enum State {
		DRAWCARD,PLAYSETTLEMENTS, PLAYLOCATIONTILE,NEXTTURN
	}
	public GameState() {
		players = new ArrayList<Player>();
		playerTurn = 0;
		currentState = State.DRAWCARD;
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
