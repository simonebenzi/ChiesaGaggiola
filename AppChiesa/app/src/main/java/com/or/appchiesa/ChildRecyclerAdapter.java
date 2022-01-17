package com.or.appchiesa;

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

    public interface Listener {
        void onClickCard(int position, ImageView imageView);
        void onClickPopup(int position, View menuImage);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ChildRecyclerAdapter(ArrayList<String> captions, int[] imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
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
            ArrayList<String> groupNames = new ArrayList<String>();
            for (int i = 0; i < groupNames.size(); i++) {
                groupNames.add(i, Group.groups.get(i).getName());
            }
            this.captions = groupNames;
            int[] groupImages = new int[Group.groups.size()];
            for (int i = 0; i < groupImages.length; i++) {
                groupImages[i] = Group.groups.get(i).getImageResourceId();
            }
            this.imageIds = groupImages;
            Log.e("NEW GROUP", groupNames.get(groupNames.size() - 1));
            notifyDataSetChanged();
        }
        else if(type == "light") {
            ArrayList<String> lightNames = new ArrayList<String>();
            for (int i = 0; i < lightNames.size(); i++) {
                lightNames.add(i, Light.lights.get(i).getName());
            }
            this.captions = lightNames;
            int[] lightsImages = new int[Light.lights.size()];
            for (int i = 0; i < lightsImages.length; i++) {
                lightsImages[i] = Light.lights.get(i).getImageResourceId();
            }
            this.imageIds = lightsImages;
            Log.e("NEW LIGHT", lightNames.get(lightNames.size() - 1));
            notifyDataSetChanged();
        }

    }
}
