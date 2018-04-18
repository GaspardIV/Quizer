package com.gaspard.quizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE_SCORE = "quizer.SCORE_MESSAGE";
    public static final String EXTRA_MESSAGE_OUTOF = "quizer.OUTOF_MESSAGE";

    private Integer quizId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        quizId = getIntent().getIntExtra(QuestionActivity.EXTRA_MESSAGE_QID, 0);
        Integer score = getIntent().getIntExtra(EXTRA_MESSAGE_SCORE, 0);
        Integer outOf = getIntent().getIntExtra(EXTRA_MESSAGE_OUTOF, 0);
        ((TextView) findViewById(R.id.scoreTV)).setText(String.format(new Locale("pl"), "%d%%", score * 100 / outOf));
    }

    public void onRedoButton(View view) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(QuestionActivity.EXTRA_MESSAGE_QID, quizId);
        finish();
        startActivity(intent);
    }

    public void onGoToListingButton(View view) {
        finish();
    }
}
