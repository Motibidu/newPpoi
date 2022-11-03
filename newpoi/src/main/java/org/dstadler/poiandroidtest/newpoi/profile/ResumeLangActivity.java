package org.dstadler.poiandroidtest.newpoi.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import org.dstadler.poiandroidtest.newpoi.R;

public class ResumeLangActivity extends AppCompatActivity {
    private ImageButton imageBtn_back;
    private Button btn_complete;
    private TextInputEditText highschool_enterYM_EditText, highschool_graYM_EditText, highschool_name_EditText, highschool_graCls_EditText,
            university_enterYM_EditText, university_graYM_EditText, university_graCls_EditText, university_name_EditText, university_major_EditText,
            master_enterYM_EditText, master_graYM_EditText, master_graCls_EditText, master_name_EditText, master_major_EditText, master_graThe_EditText,master_LAB_EditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume_linguistics);

        imageBtn_back = findViewById(R.id.imageBtn_back);
        btn_complete = findViewById(R.id.btn_complete);

        imageBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
