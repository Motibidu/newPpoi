package org.dstadler.poiandroidtest.newpoi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class profile_setting_resume extends AppCompatActivity {

   private Context context;
   private ImageButton back_button;



    private TextView eduBack, formOfCareer, linguistics, selfIntroduction, licenses;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setting_resume);

        context = getApplicationContext();

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        eduBack = findViewById(R.id.eduBack);
        eduBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_setting_resume.this, eduBack.class);
                startActivity(intent);
            }
        });
        formOfCareer = findViewById(R.id.formOfCareer);
        formOfCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_setting_resume.this, formOfCareer.class);
                startActivity(intent);
            }
        });
        linguistics = findViewById(R.id.linguistics);
        linguistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_setting_resume.this, linguistics.class);
                startActivity(intent);
            }
        });
        selfIntroduction = findViewById(R.id.selfIntroduction);
        selfIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_setting_resume.this, selfIntroduction.class);
                startActivity(intent);
            }
        });
        licenses = findViewById(R.id.licenses);
        licenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_setting_resume.this, licenses.class);
                startActivity(intent);
            }
        });
    }
}
