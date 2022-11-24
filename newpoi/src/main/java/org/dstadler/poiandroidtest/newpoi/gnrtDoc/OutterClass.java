package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import org.dstadler.poiandroidtest.newpoi.R;

public abstract class OutterClass extends AppCompatActivity {

    private static final String TAG = "OUTTERCLASS";
    private Context mContext;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter_inner recyclerViewAdapter_inner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        mContext = getApplicationContext();

        //widgets
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(layoutManager);
        Log.d(TAG, "onCreate/getLocalClassName : " + getLocalClassName());
        recyclerViewAdapter_inner = new RecyclerViewAdapter_inner(mContext, getImagesTitle(), getClassPath());
        recyclerView.setAdapter(recyclerViewAdapter_inner);

        //툴바 뒤로가기 버튼
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    protected abstract int getLayoutResourceId();
    protected abstract String[] getImagesTitle();
    protected abstract String getClassPath();

}

