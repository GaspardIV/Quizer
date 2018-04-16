package com.gaspard.quizer;


import android.content.Context;
import android.content.Intent;
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

public class QuizzesRecyclerViewAdapter extends RecyclerView.Adapter<QuizzesRecyclerViewAdapter.ViewHolder> {
    private ArrayList<QuizEntity> quizEntities;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cardView;

        ViewHolder(LinearLayout v) {
            super(v);
            cardView = v;
        }
    }

    public QuizzesRecyclerViewAdapter(ArrayList<QuizEntity> myDataset, Context context) {
        quizEntities = myDataset;
        this.context = context;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final QuizEntity actEntity = quizEntities.get(position);
        ((TextView) holder.cardView.findViewById(R.id.quiz_title)).setText(actEntity.getTitle());
        QuizCardLoadHelper.setQuizStatus(actEntity, (CardView) holder.cardView.findViewById(R.id.card_view));

        final ImageView imgView = (ImageView) holder.cardView.findViewById(R.id.quiz_img);
        if (UserPref.getLoadImagePref(context)) {
            imgView.setVisibility(View.VISIBLE);
            QuizCardLoadHelper.loadQuizImageIntoView(actEntity, imgView);
        } else {
            imgView.setVisibility(View.INVISIBLE);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToQuestion(actEntity.getId());
            }
        });

    }

    /*TODO PAMIETAC LASTQUESTION I updatowacc tylko ostatnie + double click disableowac*/
    // TODO   mAdapter.notifyItemChanged(position);
    private void goToQuestion(int id) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra(EXTRA_MESSAGE, id);
        context.startActivity(intent);
    }

    public static final String EXTRA_MESSAGE = "quizer.QUIZENTITY";

    @Override
    public int getItemCount() {
        return quizEntities.size();
    }
}

