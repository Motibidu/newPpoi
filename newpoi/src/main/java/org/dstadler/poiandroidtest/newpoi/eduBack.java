package org.dstadler.poiandroidtest.newpoi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class eduBack extends AppCompatActivity {
    private TextInputEditText highschool_enterYM_EditText, highschool_graYM_EditText, highschool_name_EditText, highschool_graIfy_EditText,
            university_enterYM_EditText, university_graYM_EditText, university_graIfy_EditText, university_name_EditText, university_major_EditText,
            master_enterYM_EditText, master_graYM_EditText, master_graIfy_EditText, master_name_EditText, master_major_EditText, master_graThe_EditText,master_LAB_EditText;
    private String highschool_enterYM,  highschool_graYM, highschool_name, highschool_graIfy, university_enterYM, university_graYM, university_graIfy, university_name,
            university_major, master_enterYM, master_graYM, master_graIfy, master_name, master_major, master_graThe, master_LAB;


    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    private StorageReference storageReference;
    private String userID;

    private Context context;
    private Button completeButton;
    private ImageButton backButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume_edu_back);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        context = getApplicationContext();
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();
        storageReference = fStorage.getReference();

        highschool_enterYM_EditText = findViewById(R.id.highschool_enterYM_EditText);
        highschool_graYM_EditText = findViewById(R.id.highschool_graYM_EditText);
        highschool_name_EditText = findViewById(R.id.highschool_name_EditText);
        highschool_graIfy_EditText = findViewById(R.id.highschool_graIfy_EditText);

        university_enterYM_EditText = findViewById(R.id.university_enterYM_EditText);
        university_graYM_EditText = findViewById(R.id.university_graYM_EditText);
        university_graIfy_EditText = findViewById(R.id.university_graIfy_EditText);
        university_name_EditText = findViewById(R.id.university_name_EditText);
        university_major_EditText = findViewById(R.id.university_major_EditText);

        master_enterYM_EditText = findViewById(R.id.master_enterYM_EditText);
        master_graYM_EditText = findViewById(R.id.master_graYM_EditText);
        master_graIfy_EditText = findViewById(R.id.master_graIfy_EditText);
        master_name_EditText = findViewById(R.id.master_name_EditText);
        master_major_EditText = findViewById(R.id.master_major_EditText);
        master_graThe_EditText = findViewById(R.id.master_graThe_EditText);
        master_LAB_EditText = findViewById(R.id.master_LAB_EditText);

        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("eduBack");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                highschool_enterYM = value.getString("highschool_enterYM");
                highschool_graYM = value.getString("highschool_graYM");
                highschool_name=value.getString("highschool_name");
                highschool_graIfy = value.getString("highschool_graIfy");
                university_enterYM = value.getString("university_enterYM");
                university_graYM=value.getString("university_graYM");
                university_graIfy = value.getString("university_graIfy");
                university_name=value.getString("university_name");
                university_major = value.getString("university_major");
                master_enterYM = value.getString("master_enterYM");
                master_graYM = value.getString("master_graYM");
                master_graIfy = value.getString("master_graIfy");
                master_name = value.getString("master_name");
                master_major = value.getString("master_major");
                master_graThe = value.getString("master_graThe");
                master_LAB = value.getString("master_LAB");

                highschool_enterYM_EditText.setText(highschool_enterYM);
                highschool_graYM_EditText.setText(highschool_graYM);
                highschool_name_EditText.setText(highschool_name);
                highschool_graIfy_EditText.setText(highschool_graIfy);

                university_enterYM_EditText.setText(university_enterYM);
                university_graYM_EditText.setText(university_graYM);
                university_graIfy_EditText.setText(university_graIfy);
                university_name_EditText.setText(university_name);
                university_major_EditText.setText(university_major);

                master_enterYM_EditText.setText(master_enterYM);
                master_graYM_EditText.setText(master_graYM);
                master_graIfy_EditText.setText(master_graIfy);
                master_name_EditText.setText(master_name);
                master_major_EditText.setText(master_major);
                master_graThe_EditText.setText(master_graThe);
                master_LAB_EditText.setText(master_LAB);
            }
        });

        completeButton = findViewById(R.id.complete_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(eduBack.this, profile_screen.class);

                highschool_enterYM = highschool_enterYM_EditText.getText().toString().trim();
                highschool_graYM = highschool_graYM_EditText.getText().toString().trim();
                highschool_name=highschool_name_EditText.getText().toString().trim();
                highschool_graIfy = highschool_graIfy_EditText.getText().toString().trim();
                university_enterYM = university_enterYM_EditText.getText().toString().trim();
                university_graYM=university_graYM_EditText.getText().toString().trim();
                university_graIfy = university_graIfy_EditText.getText().toString().trim();
                university_name=university_name_EditText.getText().toString().trim();
                university_major = university_major_EditText.getText().toString().trim();
                master_enterYM = master_enterYM_EditText.getText().toString().trim();
                master_graYM = master_graYM_EditText.getText().toString().trim();
                master_graIfy = master_graIfy_EditText.getText().toString().trim();
                master_name = master_name_EditText.getText().toString().trim();
                master_major = master_major_EditText.getText().toString().trim();
                master_graThe = master_graThe_EditText.getText().toString().trim();
                master_LAB = master_LAB_EditText.getText().toString().trim();

                if(mAuth.getCurrentUser() == null){

                }
                else{
                    userID = mAuth.getCurrentUser().getUid();
                    if(userID != null) {
                        DocumentReference documentReference = fStore.collection("users").document(userID).collection("profiles").document("eduBack");
                        Map<String, Object> user = new HashMap<>();
                        user.put("highschool_enterYM", highschool_enterYM);
                        user.put("highschool_graYM", highschool_graYM);
                        user.put("highschool_name",highschool_name);
                        user.put("highschool_graIfy", highschool_graIfy);
                        user.put("university_enterYM", university_enterYM);
                        user.put("university_graYM",university_graYM);
                        user.put("university_graIfy", university_graIfy);
                        user.put("university_name",university_name);
                        user.put("university_major", university_major);
                        user.put("master_enterYM", master_enterYM);
                        user.put("master_graYM", master_graYM);
                        user.put("master_graIfy",master_graIfy);
                        user.put("master_name", master_name);
                        user.put("master_major",master_major);
                        user.put("master_graThe", master_graThe);
                        user.put("master_LAB", master_LAB);
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
