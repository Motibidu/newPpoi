package org.dstadler.poiandroidtest.newpoi.cls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.dstadler.poiandroidtest.newpoi.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context mContext;
    public clickListener listener;


    public RecyclerViewAdapter(Context mContext, clickListener listener){
        this.mContext = mContext;
        this.listener = listener;
    }

    public interface clickListener{
        //position is the same position of file in arrayList
        void onIconMoreClick(int position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filelist, parent, false);

        return new FileLayoutHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FileLayoutHolder)holder).title.setText(Constant.allFileList.get(position).getName());
        ((FileLayoutHolder)holder).thumbnail.setImageResource(R.drawable.ic_icons8_microsoft_word_2019);
    }

    @Override
    public int getItemCount() {
        return Constant.allFileList.size();
    }

    class FileLayoutHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView title;
        ImageView ic_more_vert;

        public FileLayoutHolder(View itemView, clickListener listener){
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            ic_more_vert = itemView.findViewById(R.id.ic_more_vert);

            ic_more_vert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onIconMoreClick(getAdapterPosition());
                    }
                }
            });
        }
    }


}
