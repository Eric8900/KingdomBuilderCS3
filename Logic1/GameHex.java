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
    public boolean isLocationTile;
    public int locationTileLeft;
    public GameHex[] neighbors = new GameHex[6];
    public int id;
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
        player = -1;
        neighbors[0] = l;
        neighbors[1] = r;
        neighbors[2] = tl;
        neighbors[3] = tr;
        neighbors[4] = bl;
        neighbors[5] = br;
        if (t >= 7 && t != 14) {
            isLocationTile = true;
            locationTileLeft = 2;
        }
        else {
            isLocationTile = false;
        }
    }
}
