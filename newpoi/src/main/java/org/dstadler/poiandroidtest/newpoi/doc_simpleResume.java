package org.dstadler.poiandroidtest.newpoi;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;

import static org.dstadler.poiandroidtest.newpoi.customImageView.decodeSampledBitmapFromResource;

public class doc_simpleResume extends AppCompatActivity {
    public static String PACKAGE_NAME;

    private ImageButton simpleResume0;
    private Intent intent;
    private int reqWidth, reqHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_simple_resume);

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

        simpleResume0 = findViewById(R.id.simpleResume0);
        simpleResume0.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.simple_resume0, reqWidth, reqHeight));
        simpleResume0.setColorFilter(Color.parseColor("#08000000"));
        simpleResume0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(doc_simpleResume.this, doc_simpleResume_expandedScrn.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.simple_resume0);
                intent.putExtra("imgPath", imgPath.toString());
                intent.putExtra("imgName","simple_resume0");
                startActivity(intent);
            }
        });


    }
}
