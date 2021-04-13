package org.dstadler.poiandroidtest.newpoi;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class add_screen extends AppCompatActivity {
    private expanded_screen expanded_screen;
    private ImageButton basic_self_introduction, self_introduction0, self_introduction1, plus;
    private Intent intent;
    public static String PACKAGE_NAME;
    private static int RQST_CODE = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_screen);

        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        PACKAGE_NAME = getApplicationContext().getPackageName();

//        expanded_screen = new expanded_screen();
        basic_self_introduction = (ImageButton)findViewById(R.id.basic_self_introduction);
        basic_self_introduction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(add_screen.this, expanded_screen.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.basic_self_introduction);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });
        self_introduction0 = (ImageButton)findViewById(R.id.self_introduction0);
        self_introduction0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(add_screen.this, expanded_screen.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.self_introduction0);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });
        self_introduction1 = (ImageButton)findViewById(R.id.self_introduction1);
        self_introduction1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intent = new Intent(add_screen.this, expanded_screen.class);

                Uri imgPath = Uri.parse("android.resource://"+PACKAGE_NAME+"/"+R.drawable.self_introduction1);
                intent.putExtra("imgPath", imgPath.toString());
                startActivity(intent);
            }
        });

    }

}
