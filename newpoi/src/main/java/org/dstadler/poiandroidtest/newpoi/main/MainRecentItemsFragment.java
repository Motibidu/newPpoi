package org.dstadler.poiandroidtest.newpoi.main;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.fragment.app.Fragment;


import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Constant;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;

import java.io.File;
import java.util.ArrayList;

public class MainRecentItemsFragment extends Fragment implements RecyclerViewAdapter.clickListener {

    //static
    private static final String TAG = "MAINRECENTITEMSFRAGMENT";

    //views
    private View v;
    //widgets
    private SearchView searchView;
    //contents
    private Context mContext;
    private RecyclerViewAdapter recyclerViewAdapter;

    //newpoi classes
//    private RecyclerViewAdapter recyclerViewAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //initializations
        //views
        v = inflater.inflate(R.layout.fragment_main_recent_items, container, false);

        //widgets
        searchView = v.findViewById(R.id.SearchView_search);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);

        //contents
        mContext = getContext();


        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setNestedScrollingEnabled(false);

        Log.d(TAG, "onCreateView: Constant.allFileNameList.size(): "+Constant.allFileNameList.size());
        Log.d(TAG, "onCreateView: PreferenceManager.loadData(mContext, \"pref_allFileNameList\").size(): "+PreferenceManager.loadData(mContext, "pref_allFileNameList").size());

        //when there was no text change
        if(Constant.allFileNameList.size() == PreferenceManager.loadData(mContext,"pref_allFileNameList").size() ) {
            Log.d(TAG, "onCreateView/when there was no text change");
            recyclerViewAdapter = new RecyclerViewAdapter(mContext, this, PreferenceManager.loadData(mContext, "pref_allFileNameList"));
        //when there was text change
        }else{
            Log.d(TAG, "onCreateView/when there was text change");
            PreferenceManager.saveData(mContext,"pref_allFileNameList", Constant.allFileNameList);
            PreferenceManager.saveData(mContext,"pref_allParentPathList", Constant.allParentPathList);
            PreferenceManager.saveData(mContext,"pref_allAbsolutePathList", Constant.allAbsolutePathList);

            recyclerViewAdapter = new RecyclerViewAdapter(mContext, this, Constant.allFileNameList);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(recyclerViewAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onIconMoreClick(int position) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        PreferenceManager.setInt(getContext(),"filePosition", position);
        bottomSheetDialog.setFilePosition(position);
        bottomSheetDialog.show(getParentFragmentManager(), bottomSheetDialog.getTag());

//        logInfos(position);

    }

    public void logInfos(int position){
        String fileName;
        String fileNameWithoutExt;
        String absolutePath;
        String parentPath;

        if (PreferenceManager.loadData(mContext,"pref_allFileNameList").isEmpty()){
            fileName = Constant.allFileList.get(position).getName();
            absolutePath = Constant.allAbsolutePathList.get(position);
            parentPath = Constant.allParentPathList.get(position);

        }else{
            fileName = PreferenceManager.loadData(mContext,"pref_allFileNameList").get(position);
            absolutePath = PreferenceManager.loadData(mContext,"pref_allAbsolutePathList").get(position);
            parentPath = PreferenceManager.loadData(mContext,"pref_allParentPathList").get(position);
        }
        fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");


        Log.d(TAG, "^filePosition :"+position+", ^absolutePath: "+absolutePath+", ^parentPath: "+ parentPath + ", ^fileNameWithoutExt:" + fileNameWithoutExt);
        Log.d(TAG, "absolutePath that pdf File will be saved: "+parentPath+"/"+fileNameWithoutExt+".pdf");

    }
}
