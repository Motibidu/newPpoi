package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import static org.dstadler.poiandroidtest.newpoi.cls.customImageView.decodeSampledBitmapFromResource;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.customImageView;

import java.util.HashMap;
import java.util.Map;

public class DocCatActivity extends AppCompatActivity {
    public static String PACKAGE_NAME;
    private Context mContext;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter_outter recyclerViewAdapter_outter;

    String []res = {"career_description0", "promissory0", "employment_contract0", "resignation0","annual_salary_contract0", "freelance_employment_contract0"};
    String []title = {"디자인 이력서", "차용증", "근로계약서", "사직서", "연봉 계약서", "프리랜서 고용 계약서"};
    String []clsN = {"org.dstadler.poiandroidtest.newpoi.gnrtDoc.CareerDescriptionCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.PromissoryCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.EmploymentContractCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ResignationCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.AnnualSalaryContractCatActivity",
    "org.dstadler.poiandroidtest.newpoi.gnrtDoc.FreelanceEmploymentContractCatActivity"};


    private Map<String, String[]> arrMap = new HashMap<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        arrMap.put("res", res);
        arrMap.put("title", title);
        arrMap.put("clsN", clsN);

        //init
        //contents
        mContext = getApplicationContext();
        PACKAGE_NAME = getApplicationContext().getPackageName();
        //widgets
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter_outter = new RecyclerViewAdapter_outter(mContext, arrMap);
        recyclerView.setAdapter(recyclerViewAdapter_outter);

        //툴바 뒤로가기 버튼
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PACKAGE_NAME = getApplicationContext().getPackageName();
    }





}

