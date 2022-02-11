package com.or.appchiesa;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private ArrayList<String> sectionList;
    private ChildRecyclerAdapter childRecyclerAdapter;
    private ClickListener clickListener;
    private Context context;
    private DBHelper dbHelper;

    public ChildRecyclerAdapter getAdapter() {
        return this.childRecyclerAdapter;
    }

    public MainRecyclerAdapter(ArrayList<String> sectionList, Context context) {
        this.sectionList = sectionList;
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }

    public interface ClickListener {
        void onClickLight(int position, ImageView imageView, String sectionName);
        void onItemClick(int position, View menuImage, String section);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
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
        String sectionName = sectionList.get(position);

        ArrayList<String> lightsName;
        ArrayList<Boolean> lightsState;

        lightsName = dbHelper.getAllLightsNameFromSection(sectionName);
        lightsState = dbHelper.getAllLightsState(sectionName);
        Log.e("LIGHTS SECTION: ", lightsName.toString());

        int[] lightResIds = new int[lightsState.size()];

        for(int i = 0; i < lightsState.size(); i++){
            if(!(lightsState.get(i)))
                lightResIds[i] = R.drawable.ic_bulb;
            else
                lightResIds[i] = R.drawable.ic_bulb_on;
        }

        holder.sectionNameTextView.setText(sectionName);
        childRecyclerAdapter = new ChildRecyclerAdapter(context, lightsName, lightResIds, "light");
        holder.childRecyclerView.setAdapter(childRecyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.childRecyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        holder.childRecyclerView.setLayoutManager(layoutManager);
        this.childRecyclerAdapter.setListener(new ChildRecyclerAdapter.Listener() {
            @Override
            public void onClickCard(int position, ImageView imageView) {
                clickListener.onClickLight(position, imageView, sectionName);
            }

            @Override
            public void onClickPopup(final int position, View menuImage) {
                clickListener.onItemClick(position, menuImage, sectionName);
            }
        });

        RecyclerView.OnItemTouchListener scrollTouchListener = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                int action = e.getAction();
                switch (action){
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };

        holder.childRecyclerView.addOnItemTouchListener(scrollTouchListener);
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
