package com.or.appchiesa;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChildRecyclerAdapter
        extends RecyclerView.Adapter<ChildRecyclerAdapter.ViewHolder> {

    private ArrayList<String> captions; // Contains names of groups/lights
    private int[] imageIds; // Contains image's ids
    private Listener listener;
    private DBHelper dbHelper;

    public interface Listener {
        void onClickCard(int position, ImageView imageView);
        void onClickPopup(int position, View menuImage);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ChildRecyclerAdapter(Context context, ArrayList<String> captions, int[] imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
        this.dbHelper = new DBHelper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // visualizzazione di una nuova card
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_groups, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = (CardView) holder.cardView;
        ImageView popup = (ImageView) cardView.findViewById(R.id.popup_image);
        final ImageView imageViewCard = (ImageView) cardView.findViewById(R.id.group_image);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageViewCard.setImageDrawable(drawable);
        imageViewCard.setContentDescription(captions.get(position));
        TextView textView = (TextView) cardView.findViewById(R.id.group_text);
        textView.setText(captions.get(position));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onClickCard(position, imageViewCard);
                }
            }
        });
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onClickPopup(position, view);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return captions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public void updateRecycle(String type) {
        if(type == "group") {
            ArrayList<String> scenariosName = dbHelper.getAllScenariosName();
            this.captions = scenariosName;


            int[] groupImages = new int[scenariosName.size()];
            ArrayList<Boolean> scenariosState = dbHelper.getAllScenariosState();
            for(int i = 0; i < scenariosState.size(); i++){
                Boolean state = scenariosState.get(i);
                if(!state)
                    groupImages[i] = R.drawable.ic_bulb_group;
                else
                    groupImages[i] = R.drawable.ic_bulb_group_on;
            }
            this.imageIds = groupImages;

            notifyDataSetChanged();
        }
        else if(type == "light") {
            ArrayList<String> lightNames = dbHelper.getAllLightsName();
            this.captions = lightNames;
            ArrayList<Boolean> lightsState = dbHelper.getAllLightsState();
            int[] lightsImages = new int[lightsState.size()];
            for (int i = 0; i < lightsImages.length; i++) {
                Boolean state = lightsState.get(i);
                if(!state)
                    lightsImages[i] = R.drawable.ic_bulb;
                else
                    lightsImages[i] = R.drawable.ic_bulb_on;
            }
            this.imageIds = lightsImages;

            notifyDataSetChanged();
        }

    }
}
