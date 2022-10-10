package org.dstadler.poiandroidtest.newpoi.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;
import org.dstadler.poiandroidtest.newpoi.cls.RecyclerViewAdapter;

import java.io.File;

public class MainRecentItemsFragment extends Fragment implements RecyclerViewAdapter.clickListener{


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

    private BottomSheetDialog.bottomSheetListener listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main_recent_items, container, false);

        mContext = getContext();


        //recyclerView
        recyclerView = v.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerViewAdapter = new RecyclerViewAdapter(mContext, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        return v;
    }

    @Override
    public void onIconMoreClick(int position) {
        //Toast.makeText(mContext,"Open Bottom Sheat Dialog",Toast.LENGTH_SHORT).show();

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        PreferenceManager.setInt(getContext(),"filePosition", position);
        bottomSheetDialog.setFilePosition(position);
        bottomSheetDialog.show(getParentFragmentManager(), bottomSheetDialog.getTag());
    }
}
