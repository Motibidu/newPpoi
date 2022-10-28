package org.dstadler.poiandroidtest.newpoi.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Constant;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    //static
    private static final String TAG = "RECYCLERVIEWADAPTER";

    //apps
    private AlertDialog.Builder builder;

    //views
    //widgets

    //contents
    public Context mContext;

    //utils
    private ArrayList<String> filteredList, unFilteredList;
    private ArrayList<String> pref_allFileNameList, pref_allAbsolutePathList, pref_allParentPathList;
    private ArrayList<String> allFileNameList, allAbsolutePathList, allParentPathList;

    //newpoi classes
    private BottomSheetDialog.bottomSheetListener listener;
    public clickListener clickListener;


    private int i;
    public RecyclerViewAdapter(Context mContext, clickListener clickListener, ArrayList<String> items){
        this.mContext = mContext;

        this.listener = (BottomSheetDialog.bottomSheetListener)mContext;
        this.clickListener = clickListener;

        //Array<String>
        this.pref_allFileNameList = PreferenceManager.loadData(mContext,"pref_allFileNameList");
        this.pref_allAbsolutePathList = PreferenceManager.loadData(mContext, "pref_allAbsolutePathList");
        this.pref_allParentPathList = PreferenceManager.loadData(mContext, "pref_allParentPathList");

        //for filtering
        this.filteredList = pref_allFileNameList;
        this.unFilteredList = pref_allFileNameList;
        i = items.size();

        if (pref_allFileNameList.isEmpty()){
            Log.d(TAG, "empty/onBindViewHolder: Constant.allFileList.size(): "+Constant.allFileList.size());
        }else{
            Log.d(TAG, "not empty/onBindViewHolder: pref_allFileNameList.size(): "+pref_allFileNameList.size());
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filelist, parent, false);

        return new FileLayoutHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

//        if (pref_allFileNameList.isEmpty() || pref_allFileNameList.size() == 0){
//            ((FileLayoutHolder) holder).title.setText(Constant.allFileList.get(position).getName());
////            ((FileLayoutHolder) holder).title.setText(Constant.allAbsolutePathList.get(position));
//        }else{
//            ((FileLayoutHolder) holder).title.setText(pref_allFileNameList.get(position));
////            ((FileLayoutHolder) holder).title.setText(pref_allAbsolutePathList.get(position));
//        }
//        Log.d(TAG, "onBindViewHolder: position : "+Integer.toString(position));
//        Log.d(TAG, "onBindViewHolder: size :"+filteredList.size());
//        for(String s : filteredList){
//            Log.d(TAG, "onBindViewHolder: string : "+s);
//        }
        ((FileLayoutHolder) holder).title.setText(filteredList.get(position));
        ((FileLayoutHolder) holder).thumbnail.setImageResource(R.drawable.ic_icons8_microsoft_word_2019);
        }

    @Override
    public int getItemCount() {
        if(getFilter() != null || !filteredList.isEmpty()){
//            Log.d(TAG, "getItemCount/filteredList.size() : "+Integer.toString(filteredList.size()));
            return filteredList.size();
        }
        else if (pref_allFileNameList.isEmpty()){
//            Log.d(TAG, "getItemCount/Constant.allFileList.size() : "+Integer.toString(Constant.allFileList.size()));
            return Constant.allFileList.size();
        }else if(!pref_allFileNameList.isEmpty()){
//            Log.d(TAG, "getItemCount/pref_allFileNameList.size() : "+Integer.toString(pref_allFileNameList.size()));
            return pref_allFileNameList.size();
        }else {
//            Log.d(TAG, "getItemCount/default : "+"0");
            return 0;
        }
    }


    public class FileLayoutHolder extends RecyclerView.ViewHolder{

        LinearLayout LinearLayout_fileList;
        ImageView thumbnail;
        TextView title;
        ImageView ic_more_vert;

        public String getTitle(){
            return (String) title.getText();
        }

        public FileLayoutHolder(View itemView, clickListener clickListener){
            super(itemView);

            LinearLayout_fileList = itemView.findViewById(R.id.LinearLayout_fileList);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            ic_more_vert = itemView.findViewById(R.id.ic_more_vert);

            LinearLayout_fileList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "getAdapterposition() : "+Integer.toString(getAdapterPosition()));
                    builder = new AlertDialog.Builder(mContext);
                    ArrayList<String> pref_allFileNameList = PreferenceManager.loadData(mContext, "pref_allFileNameList");
                    ArrayList<String> pref_allAbsolutePathList = PreferenceManager.loadData(mContext, "pref_allAbsolutePathList");
                    ArrayList<String> pref_allParentPathList = PreferenceManager.loadData(mContext, "pref_allParentPathList");
                    if (pref_allFileNameList.isEmpty()){
                        builder.setTitle(Constant.allAbsolutePathList.get(getAdapterPosition()))
                                .setMessage("파일을 여시겠습니까?")
                                .setCancelable(true)
                                .setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                })
                                .setNegativeButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        listener.open();
                                    }
                                })
                                .show();
                    }else{
                        builder.setTitle(pref_allAbsolutePathList.get(getAdapterPosition()))
                                .setMessage("파일을 여시겠습니까?")
                                .setCancelable(true)
                                .setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                })
                                .setNegativeButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        listener.open();
                                    }
                                })
                                .show();
                    }
                }
            });

            ic_more_vert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition() != RecyclerView.NO_POSITION) {
                        PreferenceManager.setInt(mContext,"filePosition",getAdapterPosition());
                        clickListener.onIconMoreClick(getAdapterPosition());
                    }
                }
            });
        }
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if(charString.isEmpty()){
                filteredList = unFilteredList;
            }
            else{
                ArrayList<String> filteringList = new ArrayList<>();
                allParentPathList = new ArrayList<>();
                allAbsolutePathList= new ArrayList<>();
//                for(String fileName : unFilteredList){
//                    if (fileName.toLowerCase().contains(charString.toLowerCase())){
//                        Log.d(TAG, "performFiltering: "+fileName);
//                        filteringList.add(fileName);
//                    }
//                }
                for(int j = 0; j< unFilteredList.size();j++){
                    if (unFilteredList.get(j).toLowerCase().contains(charString.toLowerCase())){
                        Log.d(TAG, "performFiltering: "+unFilteredList.get(j));
                        filteringList.add(unFilteredList.get(j));
                        allParentPathList.add(pref_allParentPathList.get(j));
                        allAbsolutePathList.add(pref_allAbsolutePathList.get(j));
                    }
                }
                filteredList = filteringList;

                PreferenceManager.saveData(mContext,"pref_allFileNameList", filteredList);
                PreferenceManager.saveData(mContext,"pref_allParentPathList", allParentPathList);
                PreferenceManager.saveData(mContext,"pref_allAbsolutePathList", allAbsolutePathList);
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredList = (ArrayList<String>)filterResults.values;

            notifyDataSetChanged();
        }
    };
    public interface clickListener{
        //position is the same position of file in arrayList
        void onIconMoreClick(int position);
    }
}
