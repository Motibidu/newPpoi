package org.dstadler.poiandroidtest.newpoi.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.dstadler.poiandroidtest.newpoi.R;

public class ProfileSetResumeActivity extends AppCompatActivity {

   private Context context;
   private ImageButton back_button;



    private TextView eduBack, formOfCareer, linguistics, selfIntroduction, licenses;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting_resume);

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
                Intent intent = new Intent(ProfileSetResumeActivity.this, ResumeEcubackActivity.class);
                startActivity(intent);
            }
        });
        formOfCareer = findViewById(R.id.formOfCareer);
        formOfCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileSetResumeActivity.this, ResumeCareerActivity.class);
                startActivity(intent);
            }
        });
        linguistics = findViewById(R.id.linguistics);
        linguistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileSetResumeActivity.this, ResumeLangActivity.class);
                startActivity(intent);
            }
        });
        selfIntroduction = findViewById(R.id.selfIntroduction);
        selfIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileSetResumeActivity.this, org.dstadler.poiandroidtest.newpoi.profile.SelfIntroductionActivity.class);
                startActivity(intent);
            }
        });
        licenses = findViewById(R.id.licenses);
        licenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileSetResumeActivity.this, ResumeLicActivity.class);
                startActivity(intent);
            }
        });
    }
}
