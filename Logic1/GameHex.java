package Logic1;

public class GameHex {
	public int x;
    public int y;
    public int terr;
    public GameHex left;
    public GameHex right;
    public GameHex topLeft;
    public GameHex topRight;
    public GameHex botLeft;
    public GameHex botRight;
    public int player;
    public boolean highlighted;
    public GameHex(int x, int y, int t, GameHex l, GameHex r, GameHex tl, GameHex tr, GameHex bl, GameHex br, boolean highlighted) {
        this.x = x;
        this.y = y;
        terr = t;
        l = left;
        r = right;
        tl = topLeft;
        tr = topRight;
        bl = botLeft;
        br = botRight;
        highlighted = false;
    }
}
