package Logic1;

public class GameHex {
	public int x;
    public int y;
    public Pair pos;
    public int terr;
    public GameHex left;
    public GameHex right;
    public GameHex topLeft;
    public GameHex topRight;
    public GameHex botLeft;
    public GameHex botRight;
    public int player = -1;
    public boolean isLocationTile;
    public int locationTileLeft;
    public boolean[] locationTilePlayers = new boolean[4]; 
    public GameHex[] neighbors = new GameHex[6];
    public int id;
    public GameHex(int x, int y, int t, GameHex l, GameHex r, GameHex tl, GameHex tr, GameHex bl, GameHex br, int i, int j) {
        this.x = x;
        this.y = y;
        pos = new Pair(i, j);
        terr = t;
        l = left;
        r = right;
        tl = topLeft;
        tr = topRight;
        bl = botLeft;
        br = botRight;
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
    public void updateNeighbors() {
        neighbors[0] = left;
        neighbors[1] = right;
        neighbors[2] = topLeft;
        neighbors[3] = topRight;
        neighbors[4] = botLeft;
        neighbors[5] = botRight;
    }
    public void updateLocTile() {
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i].player > -1) {
                locationTilePlayers[neighbors[i].player] = true;
            }
        }
    }
}
