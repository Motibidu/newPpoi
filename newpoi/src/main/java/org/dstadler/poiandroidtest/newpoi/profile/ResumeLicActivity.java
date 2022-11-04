package org.dstadler.poiandroidtest.newpoi.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.dstadler.poiandroidtest.newpoi.R;

import java.util.HashMap;
import java.util.Map;

public class ResumeLicActivity extends AppCompatActivity {
    private TextInputEditText license1_date_EditText, license1_cntnt_EditText, license1_grade_EditText, license1_publication_EditText,
            license2_date_EditText, license2_cntnt_EditText, license2_grade_EditText, license2_publication_EditText,
            award1_date_EditText, award1_cntnt_EditText, award1_publication_EditText,
            award2_date_EditText, award2_cntnt_EditText, award2_publication_EditText;

    private String license1_date, license1_cntnt, license1_grade, license1_publication,
            license2_date, license2_cntnt, license2_grade, license2_publication,
            award1_date, award1_cntnt, award1_publication,
            award2_date, award2_cntnt, award2_publication;

    private ImageButton backButton;
    private Button completeButton;

    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private String userID;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_licenses);
        license1_date_EditText = findViewById(R.id.license1_date_EditText);
        license1_cntnt_EditText = findViewById(R.id.license1_cntnt_Edittext);
        license1_grade_EditText = findViewById(R.id.license1_grade_EditText);
        license1_publication_EditText = findViewById(R.id.license1_publication_EditText);

        license2_date_EditText = findViewById(R.id.license2_date_EditText);
        license2_cntnt_EditText = findViewById(R.id.license2_cntnt_EditText);
        license2_grade_EditText = findViewById(R.id.license2_grade_EditText);
        license2_publication_EditText = findViewById(R.id.license2_publication_EditText);

        award1_date_EditText = findViewById(R.id.award1_date_EditText);
        award1_cntnt_EditText = findViewById(R.id.award1_cntnt_EditText);
        award1_publication_EditText = findViewById(R.id.award1_publication_EditText);

        award2_date_EditText = findViewById(R.id.award2_date_EditText);
        award2_cntnt_EditText = findViewById(R.id.award2_cntnt_EditText);
        award2_publication_EditText = findViewById(R.id.award2_publication_EditText);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("licenses");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                license1_date = value.getString("license1_date");
                license1_cntnt = value.getString("license1_cntnt");
                license1_grade = value.getString("license1_grade");
                license1_publication = value.getString("license1_publication");
                license2_date = value.getString("license2_date");
                license2_cntnt = value.getString("license2_cntnt");
                license2_grade = value.getString("license2_grade");
                license2_publication = value.getString("license2_publication");
                award1_date = value.getString("award1_date");
                award1_cntnt = value.getString("award1_cntnt");
                award1_publication = value.getString("award1_publication");
                award2_date = value.getString("award2_date");
                award2_cntnt = value.getString("award2_cntnt");
                award2_publication = value.getString("award2_publication");

                license1_date_EditText.setText(license1_date);
                license1_cntnt_EditText.setText(license1_cntnt);
                license1_grade_EditText.setText(license1_grade);
                license1_publication_EditText.setText(license1_publication);
                license2_date_EditText.setText(license2_date);
                license2_cntnt_EditText.setText(license2_cntnt);
                license2_grade_EditText.setText(license2_grade);
                license2_publication_EditText.setText(license2_publication);
                award1_date_EditText.setText(award1_date);
                award1_cntnt_EditText.setText(award1_cntnt);
                award1_publication_EditText.setText(award1_publication);
                award2_date_EditText.setText(award2_date);
                award2_cntnt_EditText.setText(award2_cntnt);
                award2_publication_EditText.setText(award2_publication);
            }
        });
        completeButton = findViewById(R.id.complete_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResumeLicActivity.this, ProfileScrnActivity.class);
                license1_date = license1_date_EditText.getText().toString().trim();
                license1_cntnt = license1_cntnt_EditText.getText().toString().trim();
                license1_grade = license1_grade_EditText.getText().toString().trim();
                license1_publication = license1_publication_EditText.getText().toString().trim();

                license2_date = license2_date_EditText.getText().toString().trim();
                license2_cntnt = license2_cntnt_EditText.getText().toString().trim();
                license2_grade = license2_grade_EditText.getText().toString().trim();
                license2_publication = license2_publication_EditText.getText().toString().trim();

                award1_date = award1_date_EditText.getText().toString().trim();
                award1_cntnt = award1_cntnt_EditText.getText().toString().trim();
                award1_publication = award1_publication_EditText.getText().toString().trim();

                award2_date = award2_date_EditText.getText().toString().trim();
                award2_cntnt = award2_cntnt_EditText.getText().toString().trim();
                award2_publication = award2_publication_EditText.getText().toString().trim();
                if(mAuth.getCurrentUser() == null){

                }
                else{
                    userID = mAuth.getCurrentUser().getUid();
                    fStore = FirebaseFirestore.getInstance();
                    if(userID != null) {
                        DocumentReference documentReference = fStore.collection("users").document(userID).collection("profiles").document("licenses");
                        Map<String, Object> user = new HashMap<>();
                        user.put("license1_date", license1_date);
                        user.put("license1_cntnt", license1_cntnt);
                        user.put("license1_grade",license1_grade);
                        user.put("license1_publication", license1_publication);

                        user.put("license2_date", license2_date);
                        user.put("license2_cntnt",license2_cntnt);
                        user.put("license2_grade", license2_grade);
                        user.put("license2_publication",license2_publication);

                        user.put("award1_date", award1_date);
                        user.put("award1_cntnt", award1_cntnt);
                        user.put("award1_publication", award1_publication);

                        user.put("award2_date",award2_date);
                        user.put("award2_cntnt", award2_cntnt);
                        user.put("award2_publication",award2_publication);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        });
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }
}
