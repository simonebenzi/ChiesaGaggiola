package com.or.appchiesa;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String[] captions; // Contains names of groups/lights
    private int[] imageIds; // Contains image's ids
    private Listener listener;

    interface Listener {
        void onClickCard(int position, ImageView imageView);
        void onClickPopup(int position, View menuImage);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public RecyclerAdapter(String[] captions, int[] imageIds) {
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
        imageViewCard.setContentDescription(captions[position]);
        TextView textView = (TextView) cardView.findViewById(R.id.group_text);
        textView.setText(captions[position]);
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
        return captions.length;
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
            String[] groupNames = new String[Group.groups.size()];
            for (int i = 0; i < groupNames.length; i++) {
                groupNames[i] = Group.groups.get(i).getName();
            }
            this.captions = groupNames;
            int[] groupImages = new int[Group.groups.size()];
            for (int i = 0; i < groupImages.length; i++) {
                groupImages[i] = Group.groups.get(i).getImageResourceId();
            }
            this.imageIds = groupImages;
            Log.e("NEW GROUP", groupNames[groupNames.length - 1]);
            notifyDataSetChanged();
        }
        else if(type == "light") {
            String[] lightNames = new String[Light.lights.size()];
            for (int i = 0; i < lightNames.length; i++) {
                lightNames[i] = Light.lights.get(i).getName();
            }
            this.captions = lightNames;
            int[] lightsImages = new int[Light.lights.size()];
            for (int i = 0; i < lightsImages.length; i++) {
                lightsImages[i] = Light.lights.get(i).getImageResourceId();
            }
            this.imageIds = lightsImages;
            Log.e("NEW LIGHT", lightNames[lightNames.length - 1]);
            notifyDataSetChanged();
        }

    }
}
