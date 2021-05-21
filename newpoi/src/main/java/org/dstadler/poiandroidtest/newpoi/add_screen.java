package org.dstadler.poiandroidtest.newpoi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class add_screen extends AppCompatActivity {

    private customImageView promissory_ImageView, partTimeJobResume_ImageView, simpleResume_ImageView, memorandum_ImageView, careerDescription_ImageView;
    private Intent intent;
    public static String PACKAGE_NAME;
    private static int RQST_CODE = 0;
    private int reqWidth, reqHeight;

    @SuppressLint("WrongViewCast")
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


        //46, 65의 배수
        reqWidth = 368;
        reqHeight = 520;

        promissory_ImageView = findViewById(R.id.promissory_ImageView);
        promissory_ImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.promissory0, reqWidth, reqHeight));
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
        partTimeJobResume_ImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.parttimejob_resume0, reqWidth, reqHeight));

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
        simpleResume_ImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.simple_resume0, reqWidth, reqHeight));
        simpleResume_ImageView.setColorFilter(Color.parseColor("#6F000000"));
        simpleResume_ImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(add_screen.this, simpleResume.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.simple_resume0);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });
        careerDescription_ImageView = findViewById(R.id.careerDescription_ImageView);
        careerDescription_ImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.career_description0_page1, reqWidth, reqHeight));
        careerDescription_ImageView.setColorFilter(Color.parseColor("#6F000000"));
        careerDescription_ImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(add_screen.this, careerDescription.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.career_description0_page1);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });

//        memorandum_ImageView = findViewById(R.id.memorandum_ImageView);
//        memorandum_ImageView.setColorFilter(Color.parseColor("#6F000000"));
//        memorandum_ImageView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                intent = new Intent(add_screen.this, memorandum.class);
//
//                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.memorandum0);
//                intent.putExtra("imgPath", imgPath.toString());
//                startActivity(intent);
//            }
//        });

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


}

