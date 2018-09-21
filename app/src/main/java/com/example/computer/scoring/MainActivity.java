package com.example.computer.scoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Checking"; //tag for logging.
    Spinner spinnerHours;
    Spinner spinnerMinutes;
    Spinner spinnerSeconds;
    Spinner spinnerScores;
    ArrayList<String> hours;
    ArrayList<String> minutes;
    ArrayList<String> seconds;
    ArrayList<String> scores;
    EditText scoreText;
    EditText teamA;
    EditText teamB;
    ArrayAdapter<String> adapter_scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        setTimeSpinners();
        scores = new ArrayList<String>();
        this.adapter_scores = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, this.scores);
        spinnerScores.setAdapter(adapter_scores);

    }

    /**
     * building the timer on the screen.
     */
    private void setTimeSpinners() {
        this.hours = new ArrayList<String>();
        this.minutes = new ArrayList<String>();
        this.seconds = new ArrayList<String>();
        for (int i = 0; i < 60; i++) {
            this.minutes.add(String.valueOf(i));
            this.seconds.add(String.valueOf(i));
        }
        for (int i = 0; i <= 100; i++)
            hours.add(String.valueOf(i));

        ArrayAdapter<String> adapter_hours = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, this.hours);
        ArrayAdapter<String> adapter_minutes = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, this.minutes);
        ArrayAdapter<String> adapter_seconds = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, this.seconds);
        this.spinnerSeconds.setAdapter(adapter_seconds);
        this.spinnerMinutes.setAdapter(adapter_minutes);
        this.spinnerHours.setAdapter(adapter_hours);
    }

    public void startGame(View view) {
        if (teamA.getText().toString().isEmpty() || teamB.getText().toString().isEmpty() || scores.isEmpty() ||
                (spinnerHours.getSelectedItem().toString().equals("0") && spinnerMinutes.getSelectedItem().toString().equals("0") && spinnerSeconds.getSelectedItem().toString().equals("0")))
            Toast.makeText(this, "please name both teams and enter scores and time period", Toast.LENGTH_SHORT).show();
        else {
            Intent setGame = new Intent(this, LiveScore.class);
            setGame.putExtra("hours", this.spinnerHours.getSelectedItem().toString());
            setGame.putExtra("minutes", this.spinnerMinutes.getSelectedItem().toString());
            setGame.putExtra("seconds", this.spinnerSeconds.getSelectedItem().toString());
            setGame.putExtra("teamA", this.teamA.getText().toString());
            setGame.putExtra("teamB", this.teamB.getText().toString());
            setGame.putStringArrayListExtra("scores", this.scores);
            startActivity(setGame);
        }
    }


    private void setUpViews() {
        this.spinnerHours = (Spinner) findViewById(R.id.spinner_hours);
        this.spinnerMinutes = (Spinner) findViewById(R.id.spinner_minitues);
        this.spinnerSeconds = (Spinner) findViewById(R.id.spinner_seconds);
        this.spinnerScores = (Spinner) findViewById(R.id.spinner_scores);
        this.scoreText = (EditText) findViewById(R.id.scores_edit_text);
        this.teamA = (EditText) findViewById(R.id.teamA_edit_text);
        this.teamB = (EditText) findViewById(R.id.teamB_edit_text);
    }

    public void addScoreToList(View view) {
        if (this.scoreText.getText().toString().isEmpty())
            Toast.makeText(this, "please enter a score", Toast.LENGTH_SHORT).show();
        else {
            String currentScore = scoreText.getText().toString();
            if (!this.scores.contains(currentScore)) {
                this.scores.add(currentScore);
                sortArrayList(scores);
                this.scoreText.setText(null);
                this.spinnerScores.setAdapter(this.adapter_scores);
            } else
                Toast.makeText(this, "score already exists", Toast.LENGTH_SHORT).show();
        }
    }

    private void sortArrayList(ArrayList<String> arrayList) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i < arrayList.size(); i++) {
            temp.add(Integer.parseInt(arrayList.get(i)));
        }
        arrayList.clear();
        Collections.sort(temp);
        for (int i = 0; i < temp.size(); i++) {
            arrayList.add(String.valueOf(temp.get(i)));
        }
    }

    public void deleteScoreFromList(View view) {
        String selected = this.spinnerScores.getSelectedItem().toString();
        int index = this.scores.indexOf(selected);
        this.scores.remove(index);
        this.spinnerScores.setAdapter(adapter_scores);
    }

    public void clearScoreList(View view) {
        this.adapter_scores.clear();
        this.scores.clear();
    }
}
