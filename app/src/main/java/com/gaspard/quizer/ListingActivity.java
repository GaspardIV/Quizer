package com.gaspard.quizer;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ListingActivity extends AppCompatActivity {

    private Cursor quizzesCursor;
    private QuizzesDatabase db;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
                    Toast.makeText(getApplication(), "Pobieranie okładek wł.", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    UserPref.setLoadImagePref(getApplication(), false);
                    Toast.makeText(getApplication(), "Pobieranie okładek wył.", Toast.LENGTH_SHORT)
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
        db = new QuizzesDatabase(this);
        quizzesCursor = db.getQuizzes(); // TODO IN ASYNC
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true); // improved performance
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<QuizEntity> quiezzes = new ArrayList<>();
        for (quizzesCursor.moveToFirst(); !quizzesCursor.isAfterLast(); quizzesCursor.moveToNext()) {
            quiezzes.add(new QuizEntity(quizzesCursor.getInt(0), quizzesCursor.getString(1), quizzesCursor.getString(2), quizzesCursor.getInt(3), quizzesCursor.getInt(4), quizzesCursor.getInt(5)));
        }
        mAdapter = new QuizzesRecyclerViewAdapter(quiezzes, this);
        mRecyclerView.setAdapter(mAdapter);


        quizzesCursor.close();      //!!!!!!!!!!!!!!!!!!!!!!!
        db.close();                 //!!!!!!!!!!!!!!!!!!!!!!!

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
