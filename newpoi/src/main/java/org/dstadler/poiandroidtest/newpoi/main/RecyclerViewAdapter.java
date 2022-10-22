package org.dstadler.poiandroidtest.newpoi.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Constant;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context mContext;
    public clickListener clickListener;
    public ArrayList<CharSequence> list;
    private static final String TAG = "RECYCLERVIEWADAPTER";
    private ArrayList<String> a;



    public RecyclerViewAdapter(Context mContext, clickListener clickListener){
        this.mContext = mContext;
        this.clickListener = clickListener;
        this.a = PreferenceManager.loadData(mContext, "pref_allFileNameList");

        if (a.isEmpty() || a.size() == 0){
            Log.d(TAG, "empty/onBindViewHolder: Constant.size(): "+Constant.allFileList.size());
        }else{
            Log.d(TAG, "not empty/onBindViewHolder: a.size(): "+a.size());
        }
    }

    public interface clickListener{
        //position is the same position of file in arrayList
        void onIconMoreClick(int position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filelist, parent, false);

        return new FileLayoutHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (a.isEmpty() || a.size() == 0){
            ((FileLayoutHolder) holder).title.setText(Constant.allFileList.get(position).getName());
        }else{
            ((FileLayoutHolder) holder).title.setText(a.get(position));
        }
        ((FileLayoutHolder) holder).thumbnail.setImageResource(R.drawable.ic_icons8_microsoft_word_2019);
    }

    @Override
    public int getItemCount() {
        if (a.isEmpty() || a.size() == 0){
            return Constant.allFileList.size();

        }else{
            return a.size();
        }
    }

    class FileLayoutHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView title;
        ImageView ic_more_vert;

        public FileLayoutHolder(View itemView, clickListener clickListener){
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            ic_more_vert = itemView.findViewById(R.id.ic_more_vert);
            ic_more_vert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition() != RecyclerView.NO_POSITION) {
                        clickListener.onIconMoreClick(getAdapterPosition());
                    }
                }
            });

        }
    }
}