import java.util.*;
public class GameState {
	public static ArrayList<Player> players;
	public static GameHex [][] board; 
	public static int playerTurn;
	public static State currentState;
	public static ArrayList<Integer> deck;
	public static ArrayList<Integer> discard;
	public enum State {
		DRAWCARD,PLAYSETTLEMENTS, PLAYLOCATIONTILE,NEXTTURN
	}
	public GameState() {
		players = new ArrayList<Player>();
		
		playerTurn = 0;
		createBoard();
		
	}
	public static void update() {
		switch(currentState){
		case DRAWCARD:
			new DrawCard();
			break;
		case PLAYSETTLEMENTS:
			new PlaySettlements(factoryNumber, chosen, rowChosen);
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
	public static void createBoard() {
		
	}
	public static ArrayList<Pair> getValidSettlements(Player p){
		
	}
	public static ArrayList<Pair> getValidTavernSettlements(){
		
	}
	public static ArrayList<Pair> getValidHarborMoves(){
		
	}
	public static ArrayList<Pair> getValidBarnMoves(){
		
	}
	public static ArrayList<Pair> getValidPaddockMoves(){
	
	}
	
	
	
}
