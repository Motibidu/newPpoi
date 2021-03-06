package org.dstadler.poiandroidtest.newpoi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import static org.dstadler.poiandroidtest.newpoi.customImageView.decodeSampledBitmapFromResource;

public class doc_careerDescription extends AppCompatActivity {
    public static String PACKAGE_NAME;
    private int reqWidth, reqHeight;
    private customImageView careerDescription0;
    private Intent intent;
    private Context mContext;
    private Uri imgPath1, imgPath2, imgPath3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_career_description);
        mContext = getApplicationContext();
        
        //툴바 뒤로가기 버튼
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PACKAGE_NAME = getApplicationContext().getPackageName();

        reqWidth = 368;
        reqHeight = 520;

        //경력기술서0
        careerDescription0 = findViewById(R.id.careerDescription0);
        //이미지 해상도 조정
        careerDescription0.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.career_description0_page1, reqWidth, reqHeight));
        //반투명 설정
        careerDescription0.setColorFilter(Color.parseColor("#08000000"));
        careerDescription0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //careerDescription_expanded_screen으로 이동
                intent = new Intent(mContext, doc_careerDescription_expandedScrn.class);
                //첫번째 페이지, 이미지 URI
                imgPath1 = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.career_description0_page1);
                //두번째 페이지, 이미지 URI
                imgPath2 = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.career_description0_page2);

                intent.putExtra("imgPath1", imgPath1.toString());
                intent.putExtra("imgPath2", imgPath2.toString());
                intent.putExtra("imgPath3", "");

                //Firebase Storage 내 문서 이름 : carrerDescription0
                intent.putExtra("docName","careerDescription0");
                startActivity(intent);
            }
        });




    }
}
