package org.dstadler.poiandroidtest.newpoi.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Method;
import org.dstadler.poiandroidtest.newpoi.cls.RecyclerViewAdapter;
import org.dstadler.poiandroidtest.newpoi.cls.StorageUtil;

import java.io.File;

public class MainRecentItemsFragment extends Fragment {

    //View
    private View v;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    //contents
    private Context mContext;


    //Document Items
    private String[] allPath;
    private File storage;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main_recent_items, container, false);
        mContext = getActivity();

//        allPath = StorageUtil.getStorageDirectories(mContext);
//
//        for (String path: allPath){
//            storage = new File(path);
//            Method.load_Directory_Files(storage);
//        }
        //recyclerView
        recyclerView = v.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerViewAdapter = new RecyclerViewAdapter(mContext);
        recyclerView.setAdapter(recyclerViewAdapter);

        return v;
    }

}
