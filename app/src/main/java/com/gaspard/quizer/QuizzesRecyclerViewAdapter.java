package com.gaspard.quizer;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class QuizzesRecyclerViewAdapter extends RecyclerView.Adapter<QuizzesRecyclerViewAdapter.ViewHolder> {
    private ArrayList<QuizEntity> quizEntities;
    private Context context;
    private int lastQuizId = -1;
    private int lastPosition;
    private int[] someColors;

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cardView;

        ViewHolder(LinearLayout v) {
            super(v);
            cardView = v;
        }
    }

    QuizzesRecyclerViewAdapter(ArrayList<QuizEntity> myDataset, Context context) {
        quizEntities = myDataset;
        this.context = context;
        someColors = context.getResources().getIntArray(R.array.androidcolors);
    }

    @NonNull
    @Override
    public QuizzesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                    int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final QuizEntity actEntity = quizEntities.get(position);
        ((TextView) holder.cardView.findViewById(R.id.quiz_title)).setText(actEntity.getTitle());
        CardView card = holder.cardView.findViewById(R.id.card_view);
        QuizCardLoadHelper.setQuizStatus(actEntity, card);
        card.setBackgroundColor(someColors[new Random().nextInt(someColors.length)]);
        final ImageView imgView = holder.cardView.findViewById(R.id.quiz_img);
        if (UserPref.getLoadImagePref(context)) {
            imgView.setVisibility(View.VISIBLE);
            QuizCardLoadHelper.loadQuizImageIntoView(actEntity, imgView);
        } else {
            imgView.setVisibility(View.INVISIBLE);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToQuestions(actEntity.getId(), holder.getAdapterPosition());
            }
        });

    }

    private void goToQuestions(int quizId, int position) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra(QuestionActivity.EXTRA_MESSAGE_QID, quizId);
        lastQuizId = quizId;
        lastPosition = position;
        context.startActivity(intent);
    }

    public void reloadLastSelectedQuiz() {
        if (lastQuizId != -1) {
            QuizzesDatabase db = new QuizzesDatabase(context);
            new AsyncReloadLastAdapterPosition().execute(db);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class AsyncReloadLastAdapterPosition extends AsyncTask<QuizzesDatabase, Void, Cursor> {
        QuizzesDatabase db;

        @Override
        protected Cursor doInBackground(QuizzesDatabase... quizzesDatabases) {
            db = quizzesDatabases[0];
            return db.getQuiz(lastQuizId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            quizEntities.set(lastPosition, new QuizEntity(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
            cursor.close();
            db.close();
            notifyItemChanged(lastPosition);
        }
    }

    @Override
    public int getItemCount() {
        return quizEntities.size();
    }
}

