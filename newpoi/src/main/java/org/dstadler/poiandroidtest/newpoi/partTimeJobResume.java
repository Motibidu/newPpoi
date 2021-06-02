package org.dstadler.poiandroidtest.newpoi;

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

public class partTimeJobResume extends AppCompatActivity {
    public static String PACKAGE_NAME;
    private customImageView partTimeJob0;
    private int reqWidth, reqHeight;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_parttimejob_resume);

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

        partTimeJob0 = findViewById(R.id.partTimeJob0);
        partTimeJob0.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.parttimejob0, reqWidth, reqHeight));
        partTimeJob0.setColorFilter(Color.parseColor("#08000000"));
        partTimeJob0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(partTimeJobResume.this, promissory_expanded_screen.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.parttimejob0);
                intent.putExtra("imgPath", imgPath.toString());
                intent.putExtra("imgName","parttimejob0");
                startActivity(intent);
            }
        });


    }
}
