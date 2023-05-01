package Logic1;

public class Sector {
    public Pair[] settleCounts;//settleCounts[i] gives the ID and Amount of settlements player ID as
    public int id;
    public Pair[] sectorBounds;//0 gives row bounds, 1 gives col bounds
    public Sector(Pair[] settleCounts, int id, Pair[] sectorBounds){
        this.settleCounts = settleCounts;
        this.id = id;
        this.sectorBounds = sectorBounds;
        constructBounds();
    }
    public void constructBounds(){
        int startRow = -1;
        int startCol = -1;
        int endRow = -1;
        int endCol = -1;
        if(id == 0 || id == 1){
            startRow = 0;//start at the top
            endRow = 9;//end at the bottom half
            startCol = id == 0 ? 0 : 10;
            endCol = id == 0 ? 9: 19;
        }else{
            startRow = 10;//start at the bottom half
            endRow = 19;
            startCol = id == 2 ? 0: 10;
            endCol = id == 2 ? 9: 19;
        }
        sectorBounds[0] = new Pair(startRow, startCol);
        sectorBounds[1] = new Pair(endRow, endCol);
    }
    public void updateSettleCount(Player p){
        settleCounts[p.num] = new Pair(p.num, p.sectorCount[id]);
    }
}
