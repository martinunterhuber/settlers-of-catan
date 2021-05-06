package com.example.settlersofcatan.game;
import org.junit.Assert;
import org.junit.Test;

public class mytest {
    @Test
    public void my(){
        Tile[][] mytiles = new Tile[5][5];

        // init tiles
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i + j >= 2 && i + j <= 7){
                    mytiles[i][j] = new Tile(null, 0);
                    mytiles[i][j].u = i;
                    mytiles[i][j].v = j;
                }
            }
        }
        // TODO: init edges
        // (u,v) → (u,v,N) (u,v,E) (u+1,v-1,W) (u,v-1,N) (u-1,v,E) (u,v,W)

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 5; i++) {
                if (i + j >= 2 && i + j <= 6){
                    Edge n = new Edge();
                    Edge e = new Edge();
                    Edge w = new Edge();
                    n.adjacent1 = mytiles[i][j];
                    e.adjacent1 = mytiles[i][j];
                    w.adjacent1 = mytiles[i][j];
                    mytiles[i][j].northEdge = n;
                    mytiles[i][j].northeastEdge = e;
                    mytiles[i][j].northwestEdge = w;

                    if (inRange(i + 1, j)){
                        mytiles[i + 1][j - 1].northwestEdge.adjacent2 = mytiles[i][j];
                        mytiles[i][j].southeastEdge = mytiles[i + 1][j - 1].northwestEdge;
                    } else {
                        Edge se = new Edge();
                        se.adjacent1 = mytiles[i][j];
                        mytiles[i][j].southeastEdge = se;
                    }

                    if (inRange(i, j + 1)){
                        mytiles[i][j - 1].northEdge.adjacent2 = mytiles[i][j];
                        mytiles[i][j].southEdge = mytiles[i][j - 1].northEdge;
                    } else {
                        Edge s = new Edge();
                        s.adjacent1 = mytiles[i][j];
                        mytiles[i][j].southEdge = s;
                    }

                    if (inRange(i - 1, j + 1)){
                        mytiles[i - 1][j].northeastEdge.adjacent2 = mytiles[i][j];
                        mytiles[i][j].southwestEdge = mytiles[i - 1][j].northeastEdge;
                    } else {
                        Edge sw = new Edge();
                        sw.adjacent1 = mytiles[i][j];
                        mytiles[i][j].southwestEdge = sw;
                    }
                }
            }
        }

        System.out.println(mytiles[0][2].northEdge.adjacent2.u);
        System.out.println(mytiles[0][2].northEdge.adjacent2.v);

        // TODO: init nodes
        // (u,v) → (u+1,v,L) (u,v,R) (u+1,v-1,L) (u-1,v,R) (u,v,L) (u-1,v+1,R)
    }


    private static boolean inRange(int i, int j){
        return i < 5 && i >= 0 && j < 5 && j >= 0 && i + j >= 2 && i + j <= 6;
    }
}
