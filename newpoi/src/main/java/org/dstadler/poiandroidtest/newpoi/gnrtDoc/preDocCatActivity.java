package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import static org.dstadler.poiandroidtest.newpoi.cls.customImageView.decodeSampledBitmapFromResource;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.customImageView;

public class preDocCatActivity extends AppCompatActivity {

    private customImageView promissory_customImgV, partTimeJobResume_customImgV, simpleResume_customImgV, memorandum_customImgV, careerDescription_customImgV;
    private Intent intent;
    public static String PACKAGE_NAME;
    private int reqWidth, reqHeight;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preactivity_category);

        //툴바 뒤로가기 버튼
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PACKAGE_NAME = getApplicationContext().getPackageName();


        //46, 65의 배수
        //imgWidth, 46의 배수
        reqWidth = 368;
        //imgHeight, 65의 배수
        reqHeight = 520;

        //차용증 ImageView
        promissory_customImgV = findViewById(R.id.promissory_customImgV);
        //이미지 해상도 조정
        promissory_customImgV.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.promissory0_page0, reqWidth, reqHeight));
        //반투명 설정
        promissory_customImgV.setColorFilter(Color.parseColor("#6F000000"));
        promissory_customImgV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(preDocCatActivity.this, PromissoryCatActivity.class);
                //어플리케이션 내 "R.drawble.promissory0" URI
                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.promissory0_page0);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });

        //경력 기술서 cumstomView
        careerDescription_customImgV = findViewById(R.id.careerDescription_customImgV);
        careerDescription_customImgV.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.career_description0_page0, reqWidth, reqHeight));
        careerDescription_customImgV.setColorFilter(Color.parseColor("#6F000000"));
        careerDescription_customImgV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(preDocCatActivity.this, CareerDescriptionCatActivity.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.career_description0_page0);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });

        // 아르바이트 이력서 cumstomView
        partTimeJobResume_customImgV = findViewById(R.id.partTimeJobResume_customImgV);
        partTimeJobResume_customImgV.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.parttimejob0, reqWidth, reqHeight));
        partTimeJobResume_customImgV.setColorFilter(Color.parseColor("#6F000000"));
        partTimeJobResume_customImgV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(preDocCatActivity.this, PtjResumeCatActivity.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.parttimejob0);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });

        // 간편 이력서 cumstomView
        simpleResume_customImgV = findViewById(R.id.simpleResume_customImgV);
        simpleResume_customImgV.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.simple_resume0, reqWidth, reqHeight));
        simpleResume_customImgV.setColorFilter(Color.parseColor("#6F000000"));
        simpleResume_customImgV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(preDocCatActivity.this, SimpleResumeCatActivity.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.simple_resume0);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });



    }




}
