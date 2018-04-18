package com.gaspard.quizer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainListingActivity extends AppCompatActivity {

    private QuizzesRecyclerViewAdapter mAdapter;
    private static final String IMG_UP = "Pobieranie okładek wł.";
    private static final String IMG_DOWN = "Pobieranie okładek wył.";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.switch_pane);
        item.setActionView(R.layout.switch_layout);
        SwitchCompat switchIMG = item.getActionView().findViewById(R.id.switchImage);
        switchIMG.setChecked(UserPref.getLoadImagePref(getApplication()));
        switchIMG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    UserPref.setLoadImagePref(getApplication(), true);
                    Toast.makeText(getApplication(), IMG_UP, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    UserPref.setLoadImagePref(getApplication(), false);
                    Toast.makeText(getApplication(), IMG_DOWN, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        new QuizzesRecyclerViewFromDBAsyncLoader(this).execute();
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @SuppressLint("StaticFieldLeak")
    private class QuizzesRecyclerViewFromDBAsyncLoader extends AsyncTask<Void, Void, ArrayList<QuizEntity>> {
        private Activity context;

        QuizzesRecyclerViewFromDBAsyncLoader(Activity context) {
            this.context = context;
        }

        @Override
        protected ArrayList<QuizEntity> doInBackground(Void... voids) {
            QuizzesDatabase db = new QuizzesDatabase(context);
            Cursor quizzesCursor = db.getQuizzes();
            ArrayList<QuizEntity> quiezzes = new ArrayList<>();
            for (quizzesCursor.moveToFirst(); !quizzesCursor.isAfterLast(); quizzesCursor.moveToNext()) {
                quiezzes.add(new QuizEntity(quizzesCursor.getInt(0), quizzesCursor.getString(1), quizzesCursor.getString(2), quizzesCursor.getInt(3), quizzesCursor.getInt(4), quizzesCursor.getInt(5)));
            }
            quizzesCursor.close();
            db.close();
            return quiezzes;
        }

        @Override
        protected void onPostExecute(ArrayList<QuizEntity> quizzes) {
            super.onPostExecute(quizzes);
            mAdapter = new QuizzesRecyclerViewAdapter(quizzes, context);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            RecyclerView mRecyclerView = context.findViewById(R.id.recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) mAdapter.reloadLastSelectedQuiz();
    }
}
