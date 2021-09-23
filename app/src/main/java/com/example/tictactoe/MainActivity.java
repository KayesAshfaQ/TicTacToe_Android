package com.example.tictactoe;

import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //0:O, 1:X, 2:null
    int activePlayer = 0;
    boolean gameActive = true;
    int[] gamePosition = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private final int[][] winningStates = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private TextView textView;
    private Button button;
    private GridLayout gridLayout;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        gridLayout = findViewById(R.id.gridLayout);
        player = MediaPlayer.create(this, R.raw.swiftly);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);

                for (int i = 0; i < gridLayout.getChildCount(); i++) {

                    ImageView iv = (ImageView) gridLayout.getChildAt(i);
                    iv.setImageDrawable(null);

                }

                gameActive = true;
                activePlayer = 0;
                Arrays.fill(gamePosition, 2);

            }
        });

    }

    public void userInteract(View view) {

        ImageView counterIv = (ImageView) view;

        int tag = Integer.parseInt(counterIv.getTag().toString());

        if (gamePosition[tag] == 2 && gameActive) {

            counterIv.setTranslationY(-1500);
            gamePosition[tag] = activePlayer;

            if (activePlayer == 0) {
                counterIv.setImageResource(R.drawable.o);
                activePlayer = 1;
            } else {
                counterIv.setImageResource(R.drawable.close);
                activePlayer = 0;
            }

            counterIv.animate().translationY(0).rotation(3600).setDuration(250);

            for (int[] winningState : winningStates) { //012

                if (gamePosition[winningState[0]] == gamePosition[winningState[1]]
                        && gamePosition[winningState[1]] == gamePosition[winningState[2]]
                        && gamePosition[winningState[0]] != 2
                ) {

                    gameActive = false;
                    player.start();
                    String winner = "";
                    if (activePlayer == 1) {
                        winner = "O";
                    } else {
                        winner = "X";
                    }
                    textView.setText(String.format("%s has won!", winner));
                    textView.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);

                }

            }
        }

    }
}