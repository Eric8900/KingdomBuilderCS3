package Logic1;

import java.io.*;
import java.util.*;

public class PostGame {
    public GameHex[][] hexes;
    public boolean[] vis;

    public PostGame(GameHex[][] hexes, boolean[] vis) {
        this.hexes = hexes;
        this.vis = vis;
        scorePlayers();
    }

    public void scorePlayers() {
        ArrayList<Integer> objCards = GameState.objCards;
        for(int i = 0; i<objCards.size(); i++){
            int card = objCards.get(i);
            switch (card) {
                case 0 -> scoreMiners();
                case 1 -> scoreDiscovers();
                case 2 -> scoreLords();
                case 3 -> scoreFishermen();
                case 4 -> scoreCitizens();
                case 5 -> scoreWorkers();
                case 6 -> scoreHermits();
                case 7 -> scoreFarmers();
                case 8 -> scoreKnights();
                case 9 -> scoreMerchants();
            }
        }
    }

    public void scoreFishermen() {
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                int player = hexes[i][j].player;
                if (player == -1) continue;
                //otherwise we know a player has settlement on this hex
                if (nextToType(hexes[i][j], 5)) {
                    GameState.players.get(player).score++;
                }
            }
        }
    }

    public void scoreMiners() {
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                int player = hexes[i][j].player;
                if (player == -1) continue;
                //otherwise we know a player has settlement on this hex
                if (nextToType(hexes[i][j], 2)) {
                    GameState.players.get(player).score++;
                }
            }
        }
    }

    public void scoreDiscovers() {
        for (int i = 0; i < GameState.players.size(); i++) {
            GameState.players.get(i).score += GameState.players.get(i).uniqueRows;
        }
    }

    public void scoreHermits() {
        Arrays.fill(vis, false);
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                int player = hexes[i][j].player;
                if (player == -1) continue;
                int size = 0;
                if (!vis[hexes[i][j].id])
                    dfs(hexes[i][j], size, 0);
                GameState.players.get(player).score += size;
            }
        }
    }

    public void scoreCitizens() {
       for(int i = 0; i<GameState.players.size(); i++){
           GameState.players.get(i).score += GameState.players.get(i).sectorMax/2;
       }
    }

    public void scoreMerchants() {
        Arrays.fill(vis, false);
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                int player = hexes[i][j].player;
                if (player == -1) continue;
                if (hexes[i][j].terr == 14 && !vis[hexes[i][j].id]) {
                    int size = 0;
                    int castleHexes = 0;
                    dfs(hexes[i][j], size, castleHexes);
                    GameState.players.get(player).score += (size * castleHexes);

                }
            }
        }
    }

    public void scoreWorkers() {
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                int player = hexes[i][j].player;
                if (player == -1) continue;
                //otherwise we know a player has settlement on this hex
                if (nextToType(hexes[i][j], 2) || nextToLocation(hexes[i][j])) {
                    GameState.players.get(player).score++;
                }
            }
        }
    }

    public void scoreKnights() {
     //easier to maintain during the game
        for(int i = 0; i<GameState.players.size(); i++){
            GameState.players.get(i).score +=  2 * GameState.players.get(i).maxRowCount;
        }
    }

    public void scoreLords() {
        Sector[] sectors = GameState.sectors;
        for(int i = 0; i<sectors.length; i++){
            Pair[] sc = sectors[i].settleCounts;
            Arrays.sort(sc);
            int maxCount = sc[0].second;
            int uniqueValues = 1;
            for(int j = 0; j<sectors.length; j++){
                if(sc[i].second == maxCount){
                    GameState.players.get(sc[i].second).score += 12;
                }else{
                    uniqueValues++;
                    if(uniqueValues > 2) break;
                    GameState.players.get(sc[i].second).score += 6;
                }
            }
        }
    }

    public void scoreFarmers() {
       for(int i = 0; i<GameState.players.size(); i++){
           GameState.players.get(i).score += 3 * GameState.players.get(i).sectorMin;
       }
    }

    public boolean nextToType(GameHex h, int t) {
        GameHex[] neigh = h.neighbors;
        for (int i = 0; i < neigh.length; i++) {
            if (neigh[i].terr == t) {
                return true;
            }
        }
        return false;
    }

    public boolean nextToLocation(GameHex h) {
        GameHex[] neigh = h.neighbors;
        for (int i = 0; i < neigh.length; i++) {
            if (neigh[i].terr > 6) {
                return true;
            }
        }
        return false;
    }

    public void dfs(GameHex c, int size, int castleHexes) {
        if (c.terr == 14) castleHexes++;
        vis[c.id] = true;
        size++;
        for (GameHex v : c.neighbors) {
            if (!vis[v.id] && c.player == v.player) {
                dfs(v, size, castleHexes);
            }
        }
    }
}
