package org.dstadler.poiandroidtest.newpoi;

import android.content.Intent;
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

public class promissory extends AppCompatActivity {
    public static String PACKAGE_NAME;

    private ImageButton promissory0, promissory1, promissory2;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promissory);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.themeColor));

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        PACKAGE_NAME = getApplicationContext().getPackageName();

        promissory0 = findViewById(R.id.promissory0);
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
