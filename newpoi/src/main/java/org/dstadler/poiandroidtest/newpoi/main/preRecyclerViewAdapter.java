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

public class preRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
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
    public preRecyclerViewAdapter(Context mContext, clickListener clickListener, ArrayList<String> items){
        this.mContext = mContext;

        this.listener = (BottomSheetDialog.bottomSheetListener)mContext;
        this.clickListener = clickListener;

        //Array<String>
        this.pref_allFileNameList = PreferenceManager.loadData(mContext,"pref_allFileNameList");
        this.pref_allAbsolutePathList = PreferenceManager.loadData(mContext, "pref_allAbsolutePathList");
        this.pref_allParentPathList = PreferenceManager.loadData(mContext, "pref_allParentPathList");

        this.filteredList = pref_allFileNameList;
        this.unFilteredList = Constant.allFileNameList;
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
//        ((FileLayoutHolder) holder).title.setText("띵똥");
        ((FileLayoutHolder) holder).thumbnail.setImageResource(R.drawable.ic_icons8_microsoft_word_2019);
    }

    @Override
    public int getItemCount() {
        if(filteredList != null){
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
                    PreferenceManager.setInt(mContext,"filePosition",getAdapterPosition());
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

            ArrayList<String> pfilteredList = PreferenceManager.loadData(mContext,"pref_allFileNameList");
            ArrayList<String> punFilteredList = Constant.allFileNameList;

            ArrayList<String> ppref_allFileNameList = PreferenceManager.loadData(mContext,"pref_allFileNameList");
            ArrayList<String> ppref_allAbsolutePathList = PreferenceManager.loadData(mContext, "pref_allAbsolutePathList");
            ArrayList<String> ppref_allParentPathList = PreferenceManager.loadData(mContext, "pref_allParentPathList");

            if(charString.isEmpty()){
                pfilteredList =punFilteredList;
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
                filteringList.clear();
                for(int j = 0; j< punFilteredList.size();j++){
                    if (punFilteredList.get(j).toLowerCase().contains(charString.toLowerCase())){
                        Log.d(TAG, "performFiltering: "+punFilteredList.get(j));
                        filteringList.add(punFilteredList.get(j));
                        allParentPathList.add(ppref_allParentPathList.get(j));
                        allAbsolutePathList.add(ppref_allAbsolutePathList.get(j));
                    }
                }


                pfilteredList = filteringList;
                Log.d(TAG, "performFiltering/charSequecne.toString() : "+ charSequence.toString());
                for(String str : Constant.allFileNameList) {
                    Log.d(TAG, "performFiltering/Constant.allFilenameList: "+str);
                }
                for(String str : filteringList) {
                    Log.d(TAG, "performFiltering/filteringList: "+str);
                }
                for(String str : filteredList) {
                    Log.d(TAG, "performFiltering/filteredList: "+str);
                }
                for(String str : allParentPathList) {
                    Log.d(TAG, "performFiltering/allParentPathList: "+str);
                }
                for(String str : allAbsolutePathList) {
                    Log.d(TAG, "performFiltering/allAbsolutePathList: "+str);
                }
                for(String str : pref_allParentPathList) {
                    Log.d(TAG, "performFiltering/pref_allParentPathList: "+str);
                }
                for(String str : pref_allAbsolutePathList) {
                    Log.d(TAG, "performFiltering/pref_allAbsolutePathList: "+str);
                }

                PreferenceManager.saveData(mContext,"pref_allFileNameList", pfilteredList);
                PreferenceManager.saveData(mContext,"pref_allParentPathList", allParentPathList);
                PreferenceManager.saveData(mContext,"pref_allAbsolutePathList", allAbsolutePathList);

                filteredList = pfilteredList;
                unFilteredList = punFilteredList;

                pref_allFileNameList = ppref_allFileNameList;
                pref_allAbsolutePathList = ppref_allAbsolutePathList;
                pref_allParentPathList = ppref_allParentPathList;
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
