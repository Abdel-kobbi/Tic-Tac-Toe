package com.kobbi.tictactoe;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    protected TextView player;
    protected int playerTurn = 1;
    protected int[] buttonsStates;
    protected Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();

        buttonsStates = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};

        initButtons();

        addListenersToButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart:
                finish();
                startActivity(intent);
            case R.id.exit:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void initButtons() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        player = findViewById(R.id.player);
    }

    private void addListenersToButtons() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn1:
                checkWinner(0, view);
                break;
            case R.id.btn2:
                checkWinner(1, view);
                break;
            case R.id.btn3:
                checkWinner(2, view);
                break;
            case R.id.btn4:
                checkWinner(3, view);
                break;
            case R.id.btn5:
                checkWinner(4, view);
                break;
            case R.id.btn6:
                checkWinner(5, view);
                break;
            case R.id.btn7:
                checkWinner(6, view);
                break;
            case R.id.btn8:
                checkWinner(7, view);
                break;
            case R.id.btn9:
                checkWinner(8, view);
                break;
        }
    }

    private void checkWinner(int i, View view) {

        if (buttonsStates[i] == 0) {
            buttonsStates[i] = playerTurn;
        } else {
            return;
        }

        // check winner
        if ((buttonsStates[0] == playerTurn && buttonsStates[1] == playerTurn && buttonsStates[2] == playerTurn)
                || (buttonsStates[3] == playerTurn && buttonsStates[4] == playerTurn && buttonsStates[5] == playerTurn)
                || (buttonsStates[6] == playerTurn && buttonsStates[7] == playerTurn && buttonsStates[8] == playerTurn)
                || (buttonsStates[0] == playerTurn && buttonsStates[3] == playerTurn && buttonsStates[6] == playerTurn)
                || (buttonsStates[1] == playerTurn && buttonsStates[4] == playerTurn && buttonsStates[7] == playerTurn)
                || (buttonsStates[2] == playerTurn && buttonsStates[5] == playerTurn && buttonsStates[8] == playerTurn)
                || (buttonsStates[0] == playerTurn && buttonsStates[4] == playerTurn && buttonsStates[8] == playerTurn)
                || (buttonsStates[2] == playerTurn && buttonsStates[4] == playerTurn && buttonsStates[6] == playerTurn)
        ) {
            new AlertWinner(playerTurn == 1 ? "X" : "O", getIntent()).show(getSupportFragmentManager(), "");
            changeText(view);
            return;
        }

        changeText(view);

        boolean isDraw = true;
        for (int item : buttonsStates) {
            if (item == 0) {
                isDraw = false;
                break;
            }
        }

        if (isDraw) {
            new AlertDraw(getIntent()).show(getSupportFragmentManager(), "");
        }


    }

    private void changeText(View view) {
        Button btn = (Button) view;
        if (playerTurn == 1) {
            btn.setBackgroundResource(R.mipmap.x);
            playerTurn = 2;
            player.setText("Player: O");
        } else if (playerTurn == 2) {
            btn.setBackgroundResource(R.mipmap.o);
            playerTurn = 1;
            player.setText("Player: X");
        }

    }

    // Alert winner to display alert dialog

    public static class AlertWinner extends DialogFragment {

        private final String winner;
        private final Intent intent;

        public AlertWinner(String winner, Intent intent) {
            this.winner = winner;
            this.intent = intent;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle("You win!!!")
                    .setMessage("The winner is " + winner + "\nDo you want restart game")
                    .setPositiveButton("Yes", (dialog, id) -> {
                        requireActivity().finish();
                        requireActivity().startActivity(intent);
                    }).setNegativeButton("Exit", (dialog, id) -> requireActivity().finish());
            return builder.create();
        }
    }

    // Alert draw to display dialog if not have winner
    public static class AlertDraw extends DialogFragment {

        private final Intent intent;

        public AlertDraw(Intent intent) {
            this.intent = intent;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle("Draw!!!")
                    .setMessage("Do you want restart game")
                    .setPositiveButton("Yes", (dialog, id) -> {
                        requireActivity().finish();
                        requireActivity().startActivity(intent);
                    }).setNegativeButton("Exit", (dialog, id) -> requireActivity().finish());
            return builder.create();
        }
    }
}