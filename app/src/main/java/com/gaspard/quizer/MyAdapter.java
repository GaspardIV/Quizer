package com.gaspard.quizer;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static final int IMAGE_CACHING_HEIGHT = 200;
    private ArrayList<QuizzEntity> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout cardView;

        public ViewHolder(LinearLayout v) {
            super(v);
            cardView = v;
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_card_view, parent, false);
//        ...
//        ...
//        ...
//        ...
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    private static final String SOLVING_STATUS = "Quiz rozwiazany w %d %%";
    private static final String SOLVED_STATUS = "Ostatni wynik : %d/%d (%d%%)";
    // Replace the contents of a view (invoked by the layout manager)

//    private

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final QuizzEntity actEntity = mDataset.get(position);
        ((TextView) holder.cardView.findViewById(R.id.quiz_title)).setText(actEntity.getTitle());
        String desc = "";
        if (actEntity.getLastScore() >= 0 && actEntity.getLastQuestion() < 0) {
            desc = String.format(new Locale("pl"), SOLVED_STATUS,
                    actEntity.getLastScore(), actEntity.getQstCnt(),
                    actEntity.getLastScore() / actEntity.getQstCnt() * 100);
        } else {
            desc = String.format(new Locale("pl"), SOLVING_STATUS,
                    (actEntity.getLastQuestion() + 1) / actEntity.getQstCnt() * 100);
        }
        ((TextView) holder.cardView.findViewById(R.id.quiz_score_progress_info)).setText(desc);

        final ImageView imgView = (ImageView) holder.cardView.findViewById(R.id.quiz_img);
        Picasso.get()
                .load(actEntity.getUrl())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .resize(0, IMAGE_CACHING_HEIGHT)
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
                                .load(actEntity.getUrl())
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .resize(0, IMAGE_CACHING_HEIGHT)
                                .centerCrop()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.ic_broken_image_black)
                                .into(imgView);
                    }
                });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

