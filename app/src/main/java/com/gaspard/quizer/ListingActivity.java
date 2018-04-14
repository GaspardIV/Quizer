package com.gaspard.quizer;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

public class ListingActivity extends AppCompatActivity {


    private Cursor quizzesCursor;
    private QuizzesDatabase db;

    protected void updateQuestionsDB() {
        //shared preferences check for
//        final AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        new AsyncTask<Void, Void, Void>() {
            String ans;

            @Override
            protected Void doInBackground(Void... voids) {
//                db.dataDao().insertAll(new DataEntity("K0", "lka"));
//                Log.d("CHU", "doInBackground: " + db.dataDao().getAll().get(0).getText());
//                ans = db.dataDao().getAll().get(0).getText();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                t.setText(ans);
            }
        }.execute();
    }


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
//        final TextView t = findViewById(R.id.tekst);
        db = new QuizzesDatabase(this);
        quizzesCursor = db.getQuizzes();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        ArrayList<QuizzEntity> myDataset = new ArrayList<>();
        for (quizzesCursor.moveToFirst(); !quizzesCursor.isAfterLast(); quizzesCursor.moveToNext()) {
            // The Cursor is now set to the right position
            myDataset.add(new QuizzEntity(quizzesCursor.getInt(0), quizzesCursor.getString(1), quizzesCursor.getString(2), quizzesCursor.getInt(3), quizzesCursor.getInt(4), quizzesCursor.getInt(5)));
        }
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        quizzesCursor.close();
        db.close();
    }

}
