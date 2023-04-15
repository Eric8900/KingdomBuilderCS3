package Logic1;

import java.io.*;
import java.util.*;

public class PostGame {
    public GameHex[][] hexes;
    public boolean[] vis;

    public PostGame(GameHex[][] hexes, boolean[] vis) {
        this.hexes = hexes;
        this.vis = vis;
    }

    public void scorePlayers() {

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

    }

    public void scoreLords() {

    }

    public void scoreFarmers() {

    }

    public void dfs() {

    }
    public static void increment(int x){
        ++x;
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
