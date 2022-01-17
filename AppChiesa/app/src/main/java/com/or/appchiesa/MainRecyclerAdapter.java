package com.or.appchiesa;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private ArrayList<Section> sectionList;
    private ChildRecyclerAdapter childRecyclerAdapter;
    private ClickListener clickListener;
    private Context context;

    public ChildRecyclerAdapter getAdapter() {
        return this.childRecyclerAdapter;
    }

    public MainRecyclerAdapter(ArrayList<Section> sectionList, Context context) {
        this.sectionList = sectionList;
        this.context = context;
    }

    public interface ClickListener {
        void onClickLight(int position, ImageView imageView, String sectionName);
        void onItemClick(int position, View menuImage);
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
        Section section = sectionList.get(position);
        String sectionName = section.getName();

        SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);

        int itemsSize = section.getItems().size();
        ArrayList<Light> lights = section.getItems();
        String lightOpName;
        String lightName;
        int[] lightResIds = new int[itemsSize];
        ArrayList<String> lightNames = new ArrayList<>();
        for(int i = 0; i < itemsSize; i++) {
            try {
                SQLiteDatabase db = databaseHelper.getReadableDatabase();
                lightOpName = lights.get(i).getOpName();
                lightName = lights.get(i).getName();
                Cursor cursor = db.query("LIGHTS", new String[]{"NAME", "OP_NAME", "IP_ADDRESS",
                                "SECTION", "STATE", "IMAGE_RESOURCE_ID"},
                        "OP_NAME = ? AND NAME = ?", new String[]{lightOpName, lightName},
                        null, null, null);

                if(cursor.moveToFirst()){
                    lightResIds[i] = cursor.getInt(5);
                    lightNames.add(cursor.getString(0));
                }
                cursor.close();
                db.close();
            }
            catch(SQLException e) {
                Toast toast = Toast.makeText(context,
                        "Database unavailable",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        holder.sectionNameTextView.setText(sectionName);
        childRecyclerAdapter = new ChildRecyclerAdapter(lightNames, lightResIds);
        holder.childRecyclerView.setAdapter(childRecyclerAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(holder.childRecyclerView.getContext(), 2);
        holder.childRecyclerView.setLayoutManager(layoutManager);
        this.childRecyclerAdapter.setListener(new ChildRecyclerAdapter.Listener() {
            @Override
            public void onClickCard(int position, ImageView imageView) {
                clickListener.onClickLight(position, imageView, sectionName);
            }

            @Override
            public void onClickPopup(final int position, View menuImage) {
                clickListener.onItemClick(position, menuImage);
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
