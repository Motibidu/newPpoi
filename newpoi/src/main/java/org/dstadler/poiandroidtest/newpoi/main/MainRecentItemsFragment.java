package org.dstadler.poiandroidtest.newpoi.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Constant;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;
import org.dstadler.poiandroidtest.newpoi.gnrtDoc.DocCatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainRecentItemsFragment extends Fragment implements RecyclerViewAdapter.clickListener, TextWatcher {


    private static final String TAG = "MAINRECENTITEM_TAG";

    private View v;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    //contents
    private Context mContext;


    //Document Items
    private String[] allPath;
    private File storage;
    private int filePosition;


    private MenuItem search;
    private SearchView searchView;

    private BottomSheetDialog.bottomSheetListener listener;

    private ArrayList<String> pref_allFileNameList, pref_allAbsolutePathList, pref_allParentPathList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main_recent_items, container, false);
        mContext = getContext();


        pref_allFileNameList = PreferenceManager.loadData(mContext,"pref_allFileNameList");
        pref_allAbsolutePathList = PreferenceManager.loadData(mContext,"pref_allAbsolutePathList");
        pref_allParentPathList = PreferenceManager.loadData(mContext,"pref_allParentPathList");

        searchView = v.findViewById(R.id.SearchView_search);
        recyclerView = v.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setNestedScrollingEnabled(false);


//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                recyclerViewAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
        recyclerViewAdapter = new RecyclerViewAdapter(mContext, this, pref_allFileNameList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(recyclerViewAdapter);



        return v;
    }



//    }

    @Override
    public void onIconMoreClick(int position) {
        String fileName;
        String fileNameWithoutExt;
        String absolutePath;
        String parentPath;
        //Toast.makeText(mContext,"Open Bottom Sheat Dialog",Toast.LENGTH_SHORT).show();

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        PreferenceManager.setInt(getContext(),"filePosition", position);
        bottomSheetDialog.setFilePosition(position);
        bottomSheetDialog.show(getParentFragmentManager(), bottomSheetDialog.getTag());

        if (pref_allFileNameList.isEmpty() || pref_allFileNameList.size() == 0){
            fileName = Constant.allFileList.get(position).getName();
            absolutePath = Constant.allAbsolutePathList.get(position);
            parentPath = Constant.allParentPathList.get(position);

        }else{
            fileName = pref_allFileNameList.get(position);
            absolutePath = pref_allAbsolutePathList.get(position);
            parentPath = pref_allParentPathList.get(position);
        }

//        fileName = Constant.allFileList.get(position).getName();
//        absolutePath = Constant.allAbsolutePathList.get(position);
//        parentPath = Constant.allParentPathList.get(position);
        fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");

        Log.d(TAG, "^filePosition :"+position+", ^absolutePath: "+absolutePath+", ^parentPath: "+ parentPath + ", ^fileNameWithoutExt:" + fileNameWithoutExt);
        Log.d(TAG, "absolutePath that pdf File will be saved: "+parentPath+"/"+fileNameWithoutExt+".pdf");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                recyclerViewAdapter.getFilter().filter(charSequence);

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
