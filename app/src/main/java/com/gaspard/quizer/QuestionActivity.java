package com.gaspard.quizer;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    private QuizEntity quizzEntity;
    List<QuestionEntity> questionEntitiesl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        final Integer quizId = getIntent().getIntExtra(QuizzesRecyclerViewAdapter.EXTRA_MESSAGE, 0);

        quizzEntity = new QuizEntity(-1);
        questionEntitiesl = new ArrayList<>();
        new GetterQuestionsFromDbTask(this, quizId, quizzEntity, questionEntitiesl).execute();
    }

    public void onRadioButtonClicked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            RadioGroup rg = findViewById(R.id.radioG);
            rg.setEnabled(false);

            RadioButton r1 = findViewById(R.id.ans1);
            RadioButton r2 = findViewById(R.id.ans2);
            RadioButton r3 = findViewById(R.id.ans3);
            RadioButton r4 = findViewById(R.id.ans4);
            r1.setVisibility(View.INVISIBLE);
            r2.setVisibility(View.INVISIBLE);
            r3.setVisibility(View.INVISIBLE);
            r4.setVisibility(View.INVISIBLE);

            TextView question = findViewById(R.id.question);
            ImageView questionImage = findViewById(R.id.question_img);
            question.setVisibility(View.INVISIBLE);
            questionImage.setVisibility(View.GONE);

            int actualQuestionIndex = quizzEntity.getLastQuestion() + 1;

            // if it was first question, we reset the last result
            if (actualQuestionIndex == 0) {
                quizzEntity.setLastScore(0);
            }

            switch (view.getId()) {
                case R.id.ans1:
                    if (r1.getText() == questionEntitiesl.get(actualQuestionIndex).getRightAnswer())
                        quizzEntity.setLastScore(quizzEntity.getLastScore() + 1);
                    break;
                case R.id.ans2:
                    if (r2.getText() == questionEntitiesl.get(actualQuestionIndex).getRightAnswer())
                        quizzEntity.setLastScore(quizzEntity.getLastScore() + 1);
                    break;
                case R.id.ans3:
                    if (r3.getText() == questionEntitiesl.get(actualQuestionIndex).getRightAnswer())
                        quizzEntity.setLastScore(quizzEntity.getLastScore() + 1);
                    break;
                case R.id.ans4:
                    if (r4.getText() == questionEntitiesl.get(actualQuestionIndex).getRightAnswer())
                        quizzEntity.setLastScore(quizzEntity.getLastScore() + 1);
                    break;
            }
            rg.clearCheck();
            if (actualQuestionIndex == quizzEntity.getQstCnt() - 1) {
                //TODO WRRZUC DDO BAZY DANYCH
                quizzEntity.setLastQuestion(-1);
                // go to result
            } else {
                quizzEntity.setLastQuestion(actualQuestionIndex);
                // tODO WRRZUC DDO BAZY DANYCH
                loadQuestion();
            }
        }
    }

    public void loadQuestion() {
        RadioButton r1 = findViewById(R.id.ans1);
        RadioButton r2 = findViewById(R.id.ans2);
        RadioButton r3 = findViewById(R.id.ans3);
        RadioButton r4 = findViewById(R.id.ans4);
        RadioGroup rg = findViewById(R.id.radioG);
        TextView question = findViewById(R.id.question);
        ImageView questionImage = findViewById(R.id.question_img);
        CardView cardView = findViewById(R.id.card_view);
        QuizCardLoadHelper.setQuizStatus(quizzEntity, cardView);

        int actualQuestionNumber = quizzEntity.getLastQuestion() + 1;
        // question
        if (isNotEmptyField(questionEntitiesl.get(actualQuestionNumber).getQuestion())) {
            question.setVisibility(View.VISIBLE);
            question.setText(questionEntitiesl.get(actualQuestionNumber).getQuestion());
        }

        // image
        if (isNotEmptyField(questionEntitiesl.get(actualQuestionNumber).getImage())) {
            questionImage.setVisibility(View.VISIBLE);
            Picasso.get().load(questionEntitiesl.get(actualQuestionNumber).getImage())
                    .placeholder(R.drawable.progress_animation).error(R.drawable.ic_broken_image_black)
                    .into(questionImage);
// TODO ONCLICK questionImage.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Picasso.get().load(questionEntitiesl.get(actualQuestionNumber).getImage())
//                                .placeholder(R.drawable.progress_animation).error(R.drawable.ic_broken_image_black)
//                                .into(questionImage);
//                    }
//                });
        }

        //answers
        if (isNotEmptyField(questionEntitiesl.get(actualQuestionNumber).getAnswer1())) {
            r1.setVisibility(View.VISIBLE);
            r1.setText(questionEntitiesl.get(actualQuestionNumber).getAnswer1());
        }
        if (isNotEmptyField(questionEntitiesl.get(actualQuestionNumber).getAnswer2())) {
            r2.setVisibility(View.VISIBLE);
            r2.setText(questionEntitiesl.get(actualQuestionNumber).getAnswer2());
        }
        if (isNotEmptyField(questionEntitiesl.get(actualQuestionNumber).getAnswer3())) {
            r3.setVisibility(View.VISIBLE);
            r3.setText(questionEntitiesl.get(actualQuestionNumber).getAnswer3());
        }
        if (isNotEmptyField(questionEntitiesl.get(actualQuestionNumber).getAnswer4())) {
            r4.setVisibility(View.VISIBLE);
            r4.setText(questionEntitiesl.get(actualQuestionNumber).getAnswer4());
        }
        rg.setEnabled(true);
    }


    public static boolean isNotEmptyField(String str) {
        return str != null && !str.equals("");
    }

    private static class GetterQuestionsFromDbTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<QuestionActivity> activityReference;

        private Cursor quizCursor;
        private QuizzesDatabase db;
        private Cursor questionsCursor;
        private int quizId;
        private QuizEntity quizEntityRef;
        private List<QuestionEntity> questionEntitiesRef;

        GetterQuestionsFromDbTask(QuestionActivity context, int quizId, QuizEntity quizEntityRef, List<QuestionEntity> questionEntitiesRef) {
            activityReference = new WeakReference<>(context);
            this.quizEntityRef = quizEntityRef;
            this.questionEntitiesRef = questionEntitiesRef;
            this.quizId = quizId;
        }

        @Override
        protected Void doInBackground(Void... params) {
            QuestionActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return null;
            db = new QuizzesDatabase(activity);
            quizCursor = db.getQuiz(quizId);
            questionsCursor = db.getQuestions(quizId);
            quizEntityRef.setValues(quizCursor.getInt(0), quizCursor.getString(1), quizCursor.getString(2), quizCursor.getInt(3), quizCursor.getInt(4), quizCursor.getInt(5));
            quizCursor.close();
            for (questionsCursor.moveToFirst(); !questionsCursor.isAfterLast(); questionsCursor.moveToNext()) {
                questionEntitiesRef.add(new QuestionEntity(questionsCursor.getInt(0), questionsCursor.getInt(1), questionsCursor.getInt(2), questionsCursor.getString(3), questionsCursor.getString(4), questionsCursor.getString(5), questionsCursor.getString(6), questionsCursor.getString(7), questionsCursor.getString(8), questionsCursor.getString(9)));
            }
            quizEntityRef.setQstCnt(questionEntitiesRef.size());
            questionsCursor.close();
            db.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            QuestionActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            CardView cardView = activity.findViewById(R.id.card_view);
            TextView quizTitle = cardView.findViewById(R.id.quiz_title);

            quizTitle.setText(quizEntityRef.getTitle());

            // quiz image {

            final ImageView imgView = (ImageView) cardView.findViewById(R.id.quiz_img);
            if (UserPref.getLoadImagePref(activity)) {
                QuizCardLoadHelper.loadQuizImageIntoView(quizEntityRef, imgView);
            } else {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        QuizCardLoadHelper.loadQuizImageIntoView(quizEntityRef, imgView);
                    }
                });
            }
            // }
            activity.loadQuestion();
        }

    }
}
