package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.customImageView;

public class ResignationCatActivity extends AppCompatActivity {
    public static String PACKAGE_NAME;
    private Context mContext;


    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter_inner recyclerViewAdapter_inner;
    String []arr = {"resignation0", "resignation1", "resignation2"};

    String clsN = "org.dstadler.poiandroidtest.newpoi.gnrtDoc.CareerDescriptionActivity";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resignation_category);
        //init
        //contents
        mContext = getApplicationContext();
        PACKAGE_NAME = getApplicationContext().getPackageName();
        //widgets
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter_inner = new RecyclerViewAdapter_inner(mContext, arr, clsN);
        recyclerView.setAdapter(recyclerViewAdapter_inner);

        //툴바 뒤로가기 버튼
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PACKAGE_NAME = getApplicationContext().getPackageName();
    }
}
