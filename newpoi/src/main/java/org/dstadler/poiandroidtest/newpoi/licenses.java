package org.dstadler.poiandroidtest.newpoi;

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

import java.util.HashMap;
import java.util.Map;

public class licenses extends AppCompatActivity {
    private TextInputEditText license1_date_EditText, license1_content_EditText, license1_grade_EditText, license1_publication_EditText,
            license2_date_EditText, license2_content_EditText, license2_grade_EditText, license2_publication_EditText,
            award1_date_EditText, award1_content_EditText, award1_publication_EditText,
            award2_date_EditText, award2_content_EditText, award2_publication_EditText;

    private String license1_date, license1_content, license1_grade, license1_publication,
            license2_date, license2_content, license2_grade, license2_publication,
            award1_date, award1_content, award1_publication,
            award2_date, award2_content, award2_publication;

    private ImageButton backButton;
    private Button completeButton;

    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private String userID;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume_licenses);
        license1_date_EditText = findViewById(R.id.license1_date_EditText);
        license1_content_EditText = findViewById(R.id.license1_content_Edittext);
        license1_grade_EditText = findViewById(R.id.license1_grade_EditText);
        license1_publication_EditText = findViewById(R.id.license1_publication_EditText);

        license2_date_EditText = findViewById(R.id.license2_date_EditText);
        license2_content_EditText = findViewById(R.id.license2_content_EditText);
        license2_grade_EditText = findViewById(R.id.license2_grade_EditText);
        license2_publication_EditText = findViewById(R.id.license2_publication_EditText);

        award1_date_EditText = findViewById(R.id.award1_date_EditText);
        award1_content_EditText = findViewById(R.id.award1_content_EditText);
        award1_publication_EditText = findViewById(R.id.award1_publication_EditText);

        award2_date_EditText = findViewById(R.id.award2_date_EditText);
        award2_content_EditText = findViewById(R.id.award2_content_EditText);
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
                license1_content = value.getString("license1_content");
                license1_grade = value.getString("license1_grade");
                license1_publication = value.getString("license1_publication");
                license2_date = value.getString("license2_date");
                license2_content = value.getString("license2_content");
                license2_grade = value.getString("license2_grade");
                license2_publication = value.getString("license2_publication");
                award1_date = value.getString("award1_date");
                award1_content = value.getString("award1_content");
                award1_publication = value.getString("award1_publication");
                award2_date = value.getString("award2_date");
                award2_content = value.getString("award2_content");
                award2_publication = value.getString("award2_publication");

                license1_date_EditText.setText(license1_date);
                license1_content_EditText.setText(license1_content);
                license1_grade_EditText.setText(license1_grade);
                license1_publication_EditText.setText(license1_publication);
                license2_date_EditText.setText(license2_date);
                license2_content_EditText.setText(license2_content);
                license2_grade_EditText.setText(license2_grade);
                license2_publication_EditText.setText(license2_publication);
                award1_date_EditText.setText(award1_date);
                award1_content_EditText.setText(award1_content);
                award1_publication_EditText.setText(award1_publication);
                award2_date_EditText.setText(award2_date);
                award2_content_EditText.setText(award2_content);
                award2_publication_EditText.setText(award2_publication);
            }
        });
        completeButton = findViewById(R.id.complete_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(licenses.this, profile_screen.class);
                license1_date = license1_date_EditText.getText().toString().trim();
                license1_content = license1_content_EditText.getText().toString().trim();
                license1_grade = license1_grade_EditText.getText().toString().trim();
                license1_publication = license1_publication_EditText.getText().toString().trim();

                license2_date = license2_date_EditText.getText().toString().trim();
                license2_content = license2_content_EditText.getText().toString().trim();
                license2_grade = license2_grade_EditText.getText().toString().trim();
                license2_publication = license2_publication_EditText.getText().toString().trim();

                award1_date = award1_date_EditText.getText().toString().trim();
                award1_content = award1_content_EditText.getText().toString().trim();
                award1_publication = award1_publication_EditText.getText().toString().trim();

                award2_date = award2_date_EditText.getText().toString().trim();
                award2_content = award2_content_EditText.getText().toString().trim();
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
                        user.put("license1_content", license1_content);
                        user.put("license1_grade",license1_grade);
                        user.put("license1_publication", license1_publication);

                        user.put("license2_date", license2_date);
                        user.put("license2_content",license2_content);
                        user.put("license2_grade", license2_grade);
                        user.put("license2_publication",license2_publication);

                        user.put("award1_date", award1_date);
                        user.put("award1_content", award1_content);
                        user.put("award1_publication", award1_publication);

                        user.put("award2_date",award2_date);
                        user.put("award2_content", award2_content);
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
