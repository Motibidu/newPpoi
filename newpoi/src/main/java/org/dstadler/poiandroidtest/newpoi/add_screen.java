package org.dstadler.poiandroidtest.newpoi;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class add_screen extends AppCompatActivity {
    private promissory_expanded_screen promissory_expanded_screen;
    private ImageView promissory_ImageView, partTimeJobResume_ImageView, simpleResume_ImageView, memorandum_ImageView;
    private ImageView self_introduction1;
    private Intent intent;
    public static String PACKAGE_NAME;
    private static int RQST_CODE = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_screen);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.themeColor));


        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        PACKAGE_NAME = getApplicationContext().getPackageName();

//        expanded_screen = new expanded_screen();
        promissory_ImageView = findViewById(R.id.promissory_ImageView);
        promissory_ImageView.setColorFilter(Color.parseColor("#6F000000"));
        promissory_ImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(add_screen.this, promissory.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.promissory0);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });
        partTimeJobResume_ImageView = findViewById(R.id.partTimeJobResume_ImageView);
        partTimeJobResume_ImageView.setColorFilter(Color.parseColor("#6F000000"));
        partTimeJobResume_ImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(add_screen.this, partTimeJobResume.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.parttimejob_resume0);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });
        simpleResume_ImageView = findViewById(R.id.simpleResume_ImageView);
        simpleResume_ImageView.setColorFilter(Color.parseColor("#6F000000"));
        simpleResume_ImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(add_screen.this, simple_resume.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.simple_resume0);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });

        memorandum_ImageView = findViewById(R.id.memorandum_ImageView);
        memorandum_ImageView.setColorFilter(Color.parseColor("#6F000000"));
        memorandum_ImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(add_screen.this, memorandum.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.memorandum0);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });

    }

}
