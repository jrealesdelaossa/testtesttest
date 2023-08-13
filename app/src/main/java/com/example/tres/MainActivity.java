package com.example.tres;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView square1;
    ImageView square2;
    ImageView square3;
    ImageView square4;
    ImageView square5;
    ImageView square6;
    ImageView square7;
    ImageView square8;
    ImageView square9;

    // players
    ImageView playerX;
    ImageView playerO;

    // game variables
    String turns;

    // matrix of positions
    String[][] winnerPositions = new String[8][3];
    ArrayList<String> positionsX = new ArrayList<>();
    ArrayList<String> positionsO = new ArrayList<>();

    String idString;
    String firstPosition;
    String lastPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        square1 = findViewById(R.id.square1);
        square2 = findViewById(R.id.square2);
        square3 = findViewById(R.id.square3);
        square4 = findViewById(R.id.square4);
        square5 = findViewById(R.id.square5);
        square6 = findViewById(R.id.square6);
        square7 = findViewById(R.id.square7);
        square8 = findViewById(R.id.square8);
        square9 = findViewById(R.id.square9);

        String idFirstPosition = square1.getId() + "";

        definePositionsMatrix(idFirstPosition);

        playerX = findViewById(R.id.playerx);
        playerO = findViewById(R.id.playero);

        // selected random player
        int numRandom = new Random().nextInt(4);
        if (numRandom == 0) {
            turns = "X";
            playerO.setBackgroundColor(Color.WHITE);
            playerX.setBackgroundColor(Color.CYAN);
        } else {
            turns = "O";
            playerX.setBackgroundColor(Color.WHITE);
            playerO.setBackgroundColor(Color.CYAN);
        }
    }

    public void clickImageTable(View view) {

        // get id of image
        String id = view.getId() + "";
        String positionSelect = id.substring(id.length() - 3);

        boolean winnerX;
        boolean winnerY;

        // change turn
        if (turns.equals("X")) {
            view.setBackgroundResource(R.drawable.x);

            playerX.setBackgroundColor(Color.WHITE);
            playerO.setBackgroundColor(Color.CYAN);

            positionsX.add(positionSelect);
            winnerX = validateWinner(turns, positionsX, winnerPositions);

            if (!winnerX) {
                turns = "O";
            } else {
                disableTableOfGame();
                return;
            }
        } else {
            view.setBackgroundResource(R.drawable.o);

            playerO.setBackgroundColor(Color.WHITE);
            playerX.setBackgroundColor(Color.CYAN);

            positionsO.add(positionSelect);
            winnerY = validateWinner(turns, positionsO, winnerPositions);

            if (!winnerY) {
                turns = "X";
            } else {
                disableTableOfGame();
                return;
            }
        }

        // disable image
        view.setEnabled(false);
    }

    private void definePositionsMatrix(String idFirstPosition) {
        String id1 = idFirstPosition.substring(idFirstPosition.length() - 3);
        String id2 = Integer.parseInt(id1) + 1 + "";
        String id3 = Integer.parseInt(id2) + 1 + "";
        String id4 = Integer.parseInt(id3) + 1 + "";
        String id5 = Integer.parseInt(id4) + 1 + "";
        String id6 = Integer.parseInt(id5) + 1 + "";
        String id7 = Integer.parseInt(id6) + 1 + "";
        String id8 = Integer.parseInt(id7) + 1 + "";
        String id9 = Integer.parseInt(id8) + 1 + "";

        firstPosition = id1;
        lastPosition = id9;

        // horizontal
        winnerPositions[0][0] = id1;
        winnerPositions[0][1] = id2;
        winnerPositions[0][2] = id3;

        winnerPositions[1][0] = id4;
        winnerPositions[1][1] = id5;
        winnerPositions[1][2] = id6;

        winnerPositions[2][0] = id7;
        winnerPositions[2][1] = id8;
        winnerPositions[2][2] = id9;

        // vertical
        winnerPositions[5][0] = id3;
        winnerPositions[5][1] = id6;
        winnerPositions[5][2] = id9;

        winnerPositions[3][0] = id2;
        winnerPositions[3][1] = id5;
        winnerPositions[3][2] = id8;

        winnerPositions[4][0] = id1;
        winnerPositions[4][1] = id4;
        winnerPositions[4][2] = id7;

        // diagonal
        winnerPositions[6][0] = id1;
        winnerPositions[6][1] = id5;
        winnerPositions[6][2] = id9;

        winnerPositions[7][0] = id3;
        winnerPositions[7][1] = id5;
        winnerPositions[7][2] = id7;
    }

    private boolean validateWinner(String player, ArrayList<String> posPlayer, String[][] posWinner) {
        Log.i("arrayPositions: ", player + " >> " + Arrays.toString(posPlayer.toArray()));

        String[] posWinSetBg = new String[3];

        boolean winner = false;
        for (String[] winComb : posWinner) {
            posWinSetBg = winComb;
            int count = 0;
            for (String posWin : winComb) {
                for (String pos : posPlayer) {
                    if (pos.equals(posWin)) {
                        count++;
                        Log.i("error", "Error");
                    }
                }
            }

            if (count == 3) {
                winner = true;
                break;
            }
        }

        if (winner) {
            Log.i("winnerPositions", "winnerPositions : " + Arrays.toString(posWinSetBg));
            setBackgroundWinnerPositions(posWinSetBg, player);
        }

        return winner;
    }

    public void setBackgroundWinnerPositions(String[] posWinSetBg, String player) {
        String defaultId = "2131231";

        for (String pos : posWinSetBg) {
            int id = Integer.parseInt(defaultId + pos);
            ImageView position = findViewById(id);
            position.setBackgroundColor(Color.GREEN);
        }

        if (player.equals("X")) {
            playerX.setBackgroundColor(Color.GREEN);
            playerO.setBackgroundColor(Color.RED);
        } else {
            playerO.setBackgroundColor(Color.GREEN);
            playerX.setBackgroundColor(Color.RED);
        }
    }

    private void disableTableOfGame() {
        String defaultId = "2131231";
        int start = Integer.parseInt(firstPosition);
        int end = Integer.parseInt(lastPosition);

        for (int i = start; i <= end; i++) {
            int id = Integer.parseInt(defaultId + i);
            ImageView position = findViewById(id);
            position.setEnabled(false);
        }
    }
}