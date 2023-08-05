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

        definePositionsMatrix();

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
        Log.i("turn", "Turn of " + turns + " - " + positionSelect + " - " + id );

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

    private void definePositionsMatrix() {
        // horizontal
        winnerPositions[0][0] = "107";
        winnerPositions[0][1] = "108";
        winnerPositions[0][2] = "109";

        winnerPositions[1][0] = "110";
        winnerPositions[1][1] = "111";
        winnerPositions[1][2] = "112";

        winnerPositions[2][0] = "113";
        winnerPositions[2][1] = "114";
        winnerPositions[2][2] = "115";

        // vertical
        winnerPositions[5][0] = "109";
        winnerPositions[5][1] = "112";
        winnerPositions[5][2] = "115";

        winnerPositions[3][0] = "107";
        winnerPositions[3][1] = "110";
        winnerPositions[3][2] = "113";

        winnerPositions[4][0] = "108";
        winnerPositions[4][1] = "111";
        winnerPositions[4][2] = "114";

        // diagonal
        winnerPositions[6][0] = "107";
        winnerPositions[6][1] = "111";
        winnerPositions[6][2] = "115";

        winnerPositions[7][0] = "109";
        winnerPositions[7][1] = "111";
        winnerPositions[7][2] = "113";
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

        for (int i = 195; i <= 203; i++) {
            int id = Integer.parseInt(defaultId + i);
            ImageView position = findViewById(id);
            position.setEnabled(false);
        }
    }
}