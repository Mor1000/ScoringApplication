package com.example.computer.scoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/***
 * Summary: This activity shows the final results.
 */
public class Results extends AppCompatActivity {
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        resultText = (TextView) findViewById(R.id.winner_text);
        Bundle resultsOfGame = getIntent().getExtras();
        resultText.setText(resultsOfGame.getString("resultOfGame"));
    }
}
