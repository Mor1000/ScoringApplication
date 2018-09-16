package com.example.computer.scoring;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class LiveScore extends AppCompatActivity {
    TextView timerText;
    TextView teamA;
    TextView teamB;
    TextView teamAscore;
    TextView teamBscore;
    CountDownTimer countDownTimer;
    long timeLeftInMillSec = 0;
    boolean timeIsRunning = false;
    Button timerButton;
    Spinner liveScore;
    ArrayList<String> scores;
    ArrayAdapter<String> adapterScores;
    Bundle timeExtras;
    int pointsSumA = 0;
    int pointsSumB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_score);
        setUpViews();
        this.timeExtras = getIntent().getExtras();
        setScoresListAndTeams();
        setTimeLeftInMillSec();
        updateTimer();
    }

    private void setUpViews() {
        this.timerText = (TextView) findViewById(R.id.timer_text_view);
        this.timerButton = (Button) findViewById(R.id.timer_button);
        this.liveScore = (Spinner) findViewById(R.id.live_scores_spinner);
        this.teamA = (TextView) findViewById(R.id.teamA_text);
        this.teamB = (TextView) findViewById(R.id.teamB_text);
        this.teamAscore = (TextView) findViewById(R.id.teamA_score);
        this.teamBscore = (TextView) findViewById(R.id.teamB_score);
    }
//hh
    private void setScoresListAndTeams() {
        this.scores = timeExtras.getStringArrayList("scores");
        this.adapterScores = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, this.scores);
        this.liveScore.setAdapter(this.adapterScores);
        this.teamA.setText(timeExtras.getString("teamA"));
        this.teamB.setText(timeExtras.getString("teamB"));
    }

    private void setTimeLeftInMillSec() {
        if (timeExtras != null) {
            String hours = timeExtras.getString("hours");
            String minutes = timeExtras.getString("minutes");
            String seconds = timeExtras.getString("seconds");
            int secondTomMill = 1000 * Integer.parseInt(seconds);
            int minutesToMill = 60000 * Integer.parseInt(minutes);
            int hoursToMill = 3600000 * Integer.parseInt(hours);
            this.timeLeftInMillSec = hoursToMill + minutesToMill + secondTomMill;
        }
    }

    public void startOrStopTimer(View view) {
        if (this.timeIsRunning)
            stopTimer();
        else startTimer();
    }


    private void startTimer() {
        this.countDownTimer = new CountDownTimer(this.timeLeftInMillSec, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillSec = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerText.setText("0:00:00");
                String result = "";
                if (pointsSumA > pointsSumB)
                    result = "The winner is: " + teamA.getText().toString();
                else if (pointsSumA < pointsSumB)
                    result = "The winner is: " + teamB.getText().toString();
                else
                    result = "Draw";
                Intent finalResult = new Intent(getApplicationContext(), Results.class);
                finalResult.putExtra("resultOfGame", result);
                startActivity(finalResult);
            }
        }.start();
        this.timerButton.setText("pause");
        this.timeIsRunning = true;
    }

    private void stopTimer() {
        this.timeIsRunning = false;
        this.countDownTimer.cancel();
        this.timerButton.setText("continue");
    }

    private void updateTimer() {
        int hoursLeft = (int) timeLeftInMillSec / 3600000;
        int minutesLeft = (int) timeLeftInMillSec % 3600000 / 60000;
        int secondsLeft = (int) timeLeftInMillSec % 3600000 % 60000 / 1000;

        String currentTime;
        currentTime = "" + hoursLeft;
        currentTime += ":";
        if (minutesLeft < 10)
            currentTime += "0";
        currentTime += minutesLeft;
        currentTime += ":";
        if (secondsLeft < 10)
            currentTime += "0";
        currentTime += secondsLeft;
        timerText.setText(currentTime);

    }

    /**
     * event of adding or substracting a selected score.
     * @param view- the add or sub buttons
     */
    public void addOrSubTeams(View view) {
        int points = Integer.parseInt(liveScore.getSelectedItem().toString());
        switch (view.getId()) {
            case R.id.add_points_button_teamA:
                this.pointsSumA += points;
                break;
            case R.id.sub_points_button_teamA:
                this.pointsSumA -= points;
                break;
            case R.id.add_points_button_teamB:
                this.pointsSumB += points;
                break;
            case R.id.sub_points_button_teamB:
                this.pointsSumB -= points;
                break;
        }
        this.teamAscore.setText(String.valueOf(this.pointsSumA));
        this.teamBscore.setText(String.valueOf(this.pointsSumB));
    }
}
