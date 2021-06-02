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

public class promissory extends AppCompatActivity {
    public static String PACKAGE_NAME;

    private ImageButton promissory0, promissory1, promissory2;
    private Intent intent;

    private int reqWidth, reqHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_promissory);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.themeColor));

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
        promissory0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(promissory.this, promissory_expanded_screen.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.promissory0);
                intent.putExtra("imgPath", imgPath.toString());
                intent.putExtra("imgName","promissory0");
                startActivity(intent);
            }
        });

        promissory1 = findViewById(R.id.promissory1);
        promissory1.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.promissory1_page1, reqWidth, reqHeight));
        promissory1.setColorFilter(Color.parseColor("#08000000"));
        promissory1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(promissory.this, promissory_expanded_screen.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.promissory1_page1);
                intent.putExtra("imgPath", imgPath.toString());
                intent.putExtra("imgName","promissory1");
                startActivity(intent);
            }
        });

        promissory2 = findViewById(R.id.promissory2);
        promissory2.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.promissory2, reqWidth, reqHeight));
        promissory2.setColorFilter(Color.parseColor("#08000000"));
        promissory2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(promissory.this, promissory_expanded_screen.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.promissory2);
                intent.putExtra("imgPath", imgPath.toString());
                intent.putExtra("imgName","promissory2");
                startActivity(intent);
            }
        });
    }
}
