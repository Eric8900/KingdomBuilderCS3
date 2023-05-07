package Logic1;

import java.io.*;
import java.util.*;

public class PostGame {
    public GameHex[][] hexes = GameState.board.GameMatrix;
    public boolean[] vis;
    public int size;
    public int locationTiles;
    public boolean scoringMerchant = false;
    public int playerForMerchant = -1;
    public ArrayList<Sector> sectors;

    public ArrayList<Player> playerLeaders = new ArrayList<>();//orders to the player from greatest to least in terms of score
    ArrayList<Integer> locTilesIndicies = new ArrayList<>();

    public PostGame(boolean[] vis) {
        this.vis = vis;
        sectors = new ArrayList<>();
        for (int i = 0; i < GameState.players.size(); i++) {
            sectors.add(new Sector(new Pair[GameState.players.size()], i, new Pair[2]));
        }
        for (Sector s : sectors) {
            int startRow = s.sectorBounds[0].first;
            int startCol = s.sectorBounds[0].second;
            int endRow = s.sectorBounds[1].first;
            int endCol = s.sectorBounds[1].second;
            for (int i = startRow; i <= endRow; i++) {
                for (int j = startCol; j <= endCol; j++) {
                    if (hexes[i][j].player == -1) continue;
                    if(hexes[i][j].player == 0){
                        System.out.println("ANNOYING");
                    }
                    GameState.players.get(hexes[i][j].player).sectorCount[s.id]++;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sectors.get(i).updateSettleCount(GameState.players.get(j));
            }
        }
        scorePlayers();
        for(int i = 0; i<4; i++)
            System.out.println(Arrays.toString(GameState.players.get(i).sectorCount));
    }

    public void scorePlayers() {
        ArrayList<Integer> objCards = GameState.objCards;
        castleScoring();
        for (int i = 0; i < objCards.size(); i++) {
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
                if (hexes[i][j].terr != 5 && nextToType(hexes[i][j], 5)) {
                    GameState.players.get(player).score++;
                    GameState.players.get(player).getScore[3]++;
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
                    GameState.players.get(player).getScore[0]++;
                }
            }
        }
    }

    public void scoreDiscovers() {
        for (int i = 0; i < hexes.length; i++) {
            boolean[] seen = new boolean[GameState.players.size()];
            for (int j = 0; j < hexes[i].length; j++) {
                int player = hexes[i][j].player;
                if (player == -1) continue;
                if (!seen[player]) {
                    GameState.players.get(player).score++;
                    GameState.players.get(player).getScore[1]++;
                    seen[player] = true;
                }
            }
        }
    }

    public void scoreHermits() {
        System.out.println("MAKE IY red");
        Arrays.fill(vis, false);
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                int player = hexes[i][j].player;
                if (player == -1) continue;
                if (!vis[hexes[i][j].id]) {
                    GameState.players.get(player).getScore[6]++;
                    GameState.players.get(player).score++;
                    dfs(hexes[i][j]);
                }
            }
        }
    }

    public void scoreCitizens() {
        Arrays.fill(vis, false);
        int[] playerMax = new int[GameState.players.size()];
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                int player = hexes[i][j].player;
                if (player == -1) continue;
                size = 0;
                if (!vis[hexes[i][j].id])
                    dfs(hexes[i][j]);
                playerMax[player] = Math.max(playerMax[player], size);
            }
        }
        for (int i = 0; i < GameState.players.size(); i++) {
            GameState.players.get(i).getScore[4] += playerMax[i] / 2;
            GameState.players.get(i).score += playerMax[i] / 2;
        }
    }

    public void scoreMerchants() {
        scoringMerchant = true;
        Arrays.fill(vis, false);
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                int player = hexes[i][j].player;
                if (player == -1) continue;
                if (!vis[hexes[i][j].id]) {
                    locationTiles = 0;
                    playerForMerchant = player;
                    if(playerForMerchant == 1) System.out.println(i + " " + j);
                    unvisitLocTiles();
                    dfs(hexes[i][j]);
                    if(playerForMerchant == 1) System.out.println("Num of loc tiles "+ locationTiles);
                    System.out.println("Bruh amount of loc tiles " + locationTiles);
                    if (locationTiles < 2) {
                        locTilesIndicies.clear();
                        continue;
                    }
                    ;//not sufficient
                    System.out.print("These are the location tile indicies that you visited in this component ");
                    for (int k : locTilesIndicies) System.out.print(k + " ");
                    System.out.println();
                    GameState.players.get(player).score += (4 * locationTiles);
                    GameState.players.get(player).getScore[9] += (4 * locationTiles);
                    locTilesIndicies.clear();
                }
            }
        }
        playerForMerchant = -1;

        scoringMerchant = false;
    }

    public void scoreWorkers() {
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                int player = hexes[i][j].player;
                if (player == -1) continue;
                //otherwise we know a player has settlement on this hex
                if (nextToLocation(hexes[i][j])) {
                    GameState.players.get(player).score++;
                    GameState.players.get(player).getScore[5]++;
                }
            }
        }
    }

    public void scoreKnights() {
        int[][] rowCount = new int[GameState.players.size()][hexes.length];
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                if (hexes[i][j].player == -1) continue;
                rowCount[hexes[i][j].player][i]++;
            }
        }
        for (int player = 0; player < GameState.players.size(); player++) {
            int[] rows = rowCount[player];
            int max = -1;
            for (int count : rows) {
                max = Math.max(max, count);
            }
            GameState.players.get(player).score += 2 * max;
            GameState.players.get(player).getScore[8] += 2 * max;
        }
    }

    public void scoreLords() {
        for(int i = 0; i<4; i++){
            TreeSet<Integer> set = new TreeSet<>();
            for(int j = 0; j<4; j++){
                set.add(-GameState.players.get(j).sectorCount[i]);
            }
            int highest = 0;
            if (set.size() > 0) {
                highest = -1 * set.first();
            }
            set.remove(set.first());
            int secondHighest = 0;
            if (set.size() > 0) {
                secondHighest = -1 * set.first();
            }
            for(int j = 0; j<4; j++){
                if(GameState.players.get(j).sectorCount[i] == highest && highest != 0){
                    GameState.players.get(j).getScore[2] += 12;
                    GameState.players.get(j).score += 12;
                }else if(GameState.players.get(j).sectorCount[i] == secondHighest && secondHighest != 0){
                    GameState.players.get(j).getScore[2] += 6;
                    GameState.players.get(j).score += 6;
                }
            }
        }
    }

    public void scoreFarmers() {
        for (int i = 0; i < GameState.players.size(); i++) {
            int min = Integer.MAX_VALUE;
            int[] sectorCount = GameState.players.get(i).sectorCount;
            for (int j = 0; j < sectorCount.length; j++) {
                min = Math.min(min, sectorCount[j]);
            }
            GameState.players.get(i).score += 3 * min;
            GameState.players.get(i).getScore[7] += 3 * min;
        }
    }

    public void castleScoring() {
        for (int i = 0; i < hexes.length; i++) {
            boolean[] seen = new boolean[GameState.players.size()];
            for (int j = 0; j < hexes[i].length; j++) {
                if (hexes[i][j].terr == 14) {
                    GameHex[] neigh = hexes[i][j].neighbors;
                    for (GameHex n : neigh) {
                        if (n.player == -1) continue;
                        if (!seen[n.player]) {
                            seen[n.player] = true;
                            GameState.players.get(n.player).score += 3;
                            GameState.players.get(n.player).getScore[10] += 3;
                        }
                    }
                }
            }
        }
    }

    public boolean nextToType(GameHex h, int t) {
        if (h == null) return false;
        GameHex[] neigh = h.neighbors;
        for (GameHex gameHex : neigh) {
            if (gameHex == null) continue;
            if (gameHex.terr == t) {
                return true;
            }
        }
        return false;
    }

    public boolean nextToLocation(GameHex h) {
        if (h == null) return false;
        GameHex[] neigh = h.neighbors;
        for (GameHex gameHex : neigh) {
            if (gameHex == null) continue;
            if (gameHex.terr > 6) {
                return true;
            }
        }
        return false;
    }

    public void dfs(GameHex c) {
        if (c.terr > 6) locationTiles++;
        vis[c.id] = true;
        size++;
        for (GameHex v : c.neighbors) {
            if (v == null) continue;
            if (c.player == -1 && scoringMerchant) {
                if (!vis[v.id] && v.player == playerForMerchant && v.player != -1) {
                    dfs(v);
                }
            } else if(scoringMerchant && !vis[v.id] && (v.terr > 6 || (v.player == playerForMerchant && v.player != -1))) {
                dfs(v);
            }else if(!vis[v.id] && c.player == v.player && c.player != -1){
                dfs(v);
            }
        }
    }

    public void unvisitLocTiles() {
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                if (hexes[i][j].terr > 6) {
                    vis[hexes[i][j].id] = false;
                }
            }
        }
    }
}
