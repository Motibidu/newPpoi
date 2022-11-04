package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import static org.dstadler.poiandroidtest.newpoi.cls.customImageView.decodeSampledBitmapFromResource;

import org.dstadler.poiandroidtest.newpoi.R;

public class PromissoryCatActivity extends AppCompatActivity {
    public static String PACKAGE_NAME;

    private ImageButton promissory0, promissory1, promissory2;
    private Intent intent;

    private int reqWidth, reqHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promissory_category);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        PACKAGE_NAME = getApplicationContext().getPackageName();

        //46, 65의 배수
        reqWidth = 368;
        reqHeight = 520;

        promissory0 = findViewById(R.id.promissory0);
        promissory0.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.promissory0, reqWidth, reqHeight));
        promissory0.setColorFilter(Color.parseColor("#08000000"));
        promissory1 = findViewById(R.id.promissory1);
        promissory1.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.promissory1_page1, reqWidth, reqHeight));
        promissory1.setColorFilter(Color.parseColor("#08000000"));
        promissory2 = findViewById(R.id.promissory2);
        promissory2.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.promissory2, reqWidth, reqHeight));
        promissory2.setColorFilter(Color.parseColor("#08000000"));

        promissory0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(PromissoryCatActivity.this, PromissoryActivity.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.promissory0);
                intent.putExtra("imgPath", imgPath.toString());
                intent.putExtra("imgName","promissory0");
                startActivity(intent);
            }
        });


        promissory1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(PromissoryCatActivity.this, PromissoryActivity.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.promissory1_page1);
                intent.putExtra("imgPath", imgPath.toString());
                intent.putExtra("imgName","promissory1");
                startActivity(intent);
            }
        });


        promissory2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(PromissoryCatActivity.this, PromissoryActivity.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.promissory2);
                intent.putExtra("imgPath", imgPath.toString());
                intent.putExtra("imgName","promissory2");
                startActivity(intent);
            }
        });
    }
}
