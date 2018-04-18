package com.gaspard.quizer;

import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Locale;


public class QuizCardLoadHelper {
    private static final String SOLVING_STATUS = "Quiz rozwiazany w %d %%";
    private static final String SOLVED_STATUS = "Ostatni wynik : %d/%d (%d%%)";

    private static int width = -1;

    private static int getScreenWidth() {
        if (width < 0) width = Resources.getSystem().getDisplayMetrics().widthPixels;
        return width;
    }


    public static void setQuizStatus(QuizEntity actEntity, CardView cardView) {
        String desc = "";
        ProgressBar progressBar = (ProgressBar) cardView.findViewById(R.id.progressBar);
        progressBar.setMax(actEntity.getQstCnt());
        progressBar.setProgress(actEntity.getLastQuestion() + 1);
        if (actEntity.getLastScore() >= 0 && actEntity.getLastQuestion() + 1 == actEntity.getQstCnt()) {
            desc = String.format(new Locale("pl"), SOLVED_STATUS,
                    actEntity.getLastScore(), actEntity.getQstCnt(),
                    actEntity.getLastScore() * 100 / actEntity.getQstCnt());
        } else {
            desc = String.format(new Locale("pl"), SOLVING_STATUS,
                    (actEntity.getLastQuestion() + 1) * 100 / actEntity.getQstCnt());
        }
        ((TextView) cardView.findViewById(R.id.quiz_score_progress_info)).setText(desc);
    }

    public static void loadQuizImageIntoView(final QuizEntity quizEntity, final ImageView imgView) {
        Picasso.get()
                .load(quizEntity.getUrl())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .resize(getScreenWidth(), 0)
                .onlyScaleDown()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_broken_image_black)
                .into(imgView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(quizEntity.getUrl())
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .resize(getScreenWidth(), 0)
                                .onlyScaleDown()
                                .centerCrop()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.ic_broken_image_black)
                                .into(imgView);
                    }
                });
    }
}
