package com.or.appchiesa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private ArrayList<Section> sectionList;

    public MainRecyclerAdapter(ArrayList<Section> sectionList) {
        this.sectionList = sectionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.section_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerAdapter.ViewHolder holder, int position) {
        Section section = sectionList.get(position);
        String sectionName = section.getName();
        ArrayList<String> items = section.getItems();

        holder.sectionNameTextView.setText(sectionName);

        RecyclerAdapter childRecyclerAdapter = new RecyclerAdapter(); //TODO
        holder.childRecyclerView.setAdapter(childRecyclerAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(holder.childRecyclerView.getContext(), 2);
        holder.childRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView sectionNameTextView;
        RecyclerView childRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sectionNameTextView = itemView.findViewById(R.id.section_name_tv);
            childRecyclerView = itemView.findViewById(R.id.child_recycler);
        }
    }
}
