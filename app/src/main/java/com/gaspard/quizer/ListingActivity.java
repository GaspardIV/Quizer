package com.gaspard.quizer;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ListingActivity extends AppCompatActivity {


    protected void updateQuestionsDB() {
        //shared preferences check for


        final AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        new AsyncTask<Void, Void, Void>() {
            String ans;

            @Override
            protected Void doInBackground(Void... voids) {
//                db.dataDao().insertAll(new DataEntity("K0", "lka"));
                Log.d("CHU", "doInBackground: " + db.dataDao().getAll().get(0).getText());
                ans = db.dataDao().getAll().get(0).getText();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                t.setText(ans);
            }
        }.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        final TextView t = findViewById(R.id.tekst);
    }
}
