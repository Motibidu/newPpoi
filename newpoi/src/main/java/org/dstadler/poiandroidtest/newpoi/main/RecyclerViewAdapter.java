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
    //static vars
    private static final String TAG = "RECYCLERVIEWADAPTER";

    //contents
    public Context mContext;

    public clickListener clickListener;
    public clickListener filterListener;
//    public ArrayList<CharSequence> list;

    private ArrayList<String> pref_allFileNameList, pref_allAbsolutePathList, pref_allParentPathList;
    private ArrayList<String> allParentPathList;
    private ArrayList<String> allAbsolutePathList;
    private BottomSheetDialog.bottomSheetListener listener;
    private AlertDialog.Builder builder;

    private List<FileLayoutHolder> exampleList;
    private List<FileLayoutHolder> exampleListFull;

    private ArrayList<String> r, filteredList, unFilteredList;

    private int i;


    public RecyclerViewAdapter(Context mContext, clickListener clickListener, ArrayList<String> items) {
        this.mContext = mContext;
        listener = (BottomSheetDialog.bottomSheetListener) mContext;
        this.clickListener = clickListener;

        i = items.size();
        this.filteredList = items;
        this.unFilteredList = items;

        this.pref_allFileNameList = PreferenceManager.loadData(mContext, "pref_allFileNameList");
        this.pref_allAbsolutePathList = PreferenceManager.loadData(mContext, "pref_allAbsolutePathList");
        this.pref_allParentPathList = PreferenceManager.loadData(mContext, "pref_allParentPathList");

        if (pref_allFileNameList.isEmpty()){
            Log.d(TAG, "empty/onBindViewHolder: Constant.size(): "+Constant.allFileList.size());
        }else{
            Log.d(TAG, "not empty/onBindViewHolder: pref_allFileNameList.size(): "+pref_allFileNameList.size());
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
                allParentPathList = pref_allParentPathList;
                allAbsolutePathList = pref_allAbsolutePathList;
            }
            else{
                ArrayList<String> filteringList = new ArrayList<>();

                allParentPathList = new ArrayList<>();
                allAbsolutePathList= new ArrayList<>();

                for(int j = 0; j< unFilteredList.size();j++){
                    if (unFilteredList.get(j).toLowerCase().contains(charString.toLowerCase())){
                        Log.d(TAG, "performFiltering: "+unFilteredList.get(j));
                        filteringList.add(unFilteredList.get(j));
                        allParentPathList.add(pref_allParentPathList.get(j));
                        allAbsolutePathList.add(pref_allAbsolutePathList.get(j));
                    }
                }
                filteredList = filteringList;

            }

            Log.d(TAG, "performFiltering/charSequecne.toString() : "+ charSequence.toString());
            for(String str : Constant.allFileNameList) {
                Log.d(TAG, "performFiltering/Constant.allFilenameList: "+str);
            }
            for(String str : unFilteredList) {
                Log.d(TAG, "performFiltering/unFilteredList: "+str);
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
            PreferenceManager.saveData(mContext,"pref_allFileNameList", filteredList);
            PreferenceManager.saveData(mContext,"pref_allParentPathList", allParentPathList);
            PreferenceManager.saveData(mContext,"pref_allAbsolutePathList", allAbsolutePathList);

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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filelist, parent, false);

        return new FileLayoutHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FileLayoutHolder) holder).title.setText(filteredList.get(position));
        ((FileLayoutHolder) holder).thumbnail.setImageResource(R.drawable.ic_icons8_microsoft_word_2019);
    }


    @Override
    public int getItemCount() {
        if(getFilter() != null || filteredList!=null){
            return filteredList.size();
        }
        else if (pref_allFileNameList.isEmpty()){
            return Constant.allFileList.size();
        }else if(!pref_allFileNameList.isEmpty()){
            return pref_allFileNameList.size();
        }else {
            return 0;
        }

    }
    public void removeAt(int position) {
        filteredList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, filteredList.size());
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
}
