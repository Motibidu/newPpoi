package org.dstadler.poiandroidtest.newpoi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;

import static org.dstadler.poiandroidtest.newpoi.customImageView.decodeSampledBitmapFromResource;

public class careerDescription extends AppCompatActivity {
    public static String PACKAGE_NAME;
    private int reqWidth, reqHeight;
    private customImageView careerDescription0;
    private Intent intent;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_career_description);

        context = getApplicationContext();

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.themeColor));

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        PACKAGE_NAME = getApplicationContext().getPackageName();

        reqWidth = 368;
        reqHeight = 520;
        careerDescription0 = findViewById(R.id.careerDescription0);
        careerDescription0.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.career_description0_page1, reqWidth, reqHeight));
        careerDescription0.setColorFilter(Color.parseColor("#08000000"));
        careerDescription0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(context, careerDescription_expanded_screen.class);

                Uri imgPath1 = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.career_description0_page1);
                Uri imgPath2 = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.career_description0_page2);
                intent.putExtra("imgPath1", imgPath1.toString());
                intent.putExtra("imgPath2", imgPath2.toString());
                intent.putExtra("imgName","careerDescription0");
                startActivity(intent);
            }
        });




    }
}
