package org.dstadler.poiandroidtest.newpoi.profile;

import android.content.Context;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.dstadler.poiandroidtest.newpoi.R;

import java.util.HashMap;
import java.util.Map;

public class resume_formOfCareer extends AppCompatActivity {
    private TextInputEditText formOfCareer1_name_EditText, formOfCareer1_enterYM_EditText ,formOfCareer1_office_EditText  ,formOfCareer1_task_EditText ,formOfCareer1_resignYM_EditText
            ,formOfCareer2_name_EditText, formOfCareer2_enterYM_EditText , formOfCareer2_office_EditText, formOfCareer2_task_EditText, formOfCareer2_resignYM_EditText
            ,formOfCareer3_name_EditText, formOfCareer3_enterYM_EditText, formOfCareer3_office_EditText, formOfCareer3_task_EditText, formOfCareer3_resignYM_EditText;
    private String formOfCareer1_name, formOfCareer1_enterYM, formOfCareer1_office , formOfCareer1_task, formOfCareer1_resignYM
            ,formOfCareer2_name, formOfCareer2_enterYM, formOfCareer2_office, formOfCareer2_task, formOfCareer2_resignYM
            ,formOfCareer3_name, formOfCareer3_enterYM, formOfCareer3_office, formOfCareer3_task, formOfCareer3_resignYM;


    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    private StorageReference storageReference;
    private String userID;

    private Context context;
    private Button cmpltBtn;
    private ImageButton backBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume_form_of_career);

        context = getApplicationContext();
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();
        storageReference = fStorage.getReference();

        formOfCareer1_name_EditText = findViewById(R.id.formOfCareer1_name_EditText);
        formOfCareer1_enterYM_EditText = findViewById(R.id.formOfCareer1_enterYM_EditText);
        formOfCareer1_resignYM_EditText = findViewById(R.id.formOfCareer1_resignYM_EditText);
        formOfCareer1_office_EditText = findViewById(R.id.formOfCareer1_office_EditText);
        formOfCareer1_task_EditText = findViewById(R.id.formOfCareer1_task_EditText);

        formOfCareer2_name_EditText = findViewById(R.id.formOfCareer2_name_EditText);
        formOfCareer2_enterYM_EditText = findViewById(R.id.formOfCareer2_enterYM_EditText);
        formOfCareer2_resignYM_EditText = findViewById(R.id.formOfCareer2_resignYM_EditText);
        formOfCareer2_office_EditText = findViewById(R.id.formOfCareer2_office_EditText);
        formOfCareer2_task_EditText = findViewById(R.id.formOfCareer2_task_EditText);

        formOfCareer3_name_EditText = findViewById(R.id.formOfCareer3_name_EditText);
        formOfCareer3_enterYM_EditText = findViewById(R.id.formOfCareer3_enterYM_EditText);
        formOfCareer3_resignYM_EditText = findViewById(R.id.formOfCareer3_resignYM_EditText);
        formOfCareer3_office_EditText = findViewById(R.id.formOfCareer3_office_EditText);
        formOfCareer3_task_EditText = findViewById(R.id.formOfCareer3_task_EditText);

        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("formOfCareer");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                formOfCareer1_name= value.getString("formOfCareer1_name");
                formOfCareer1_enterYM= value.getString("formOfCareer1_enterYM");
                formOfCareer1_resignYM= value.getString("formOfCareer1_resignYM");
                formOfCareer1_office = value.getString("formOfCareer1_office");
                formOfCareer1_task= value.getString("formOfCareer1_task");

                formOfCareer2_name= value.getString("formOfCareer2_name");
                formOfCareer2_enterYM= value.getString("formOfCareer2_enterYM");
                formOfCareer2_resignYM= value.getString("formOfCareer2_resignYM");
                formOfCareer2_office= value.getString("formOfCareer2_office");
                formOfCareer2_task= value.getString("formOfCareer2_task");

                formOfCareer3_name= value.getString("formOfCareer3_name");
                formOfCareer3_enterYM= value.getString("formOfCareer3_enterYM");
                formOfCareer3_resignYM= value.getString("formOfCareer3_resignYM");
                formOfCareer3_office= value.getString("formOfCareer3_office");
                formOfCareer3_task= value.getString("formOfCareer3_task");

                formOfCareer1_name_EditText.setText(formOfCareer1_name);
                formOfCareer1_enterYM_EditText.setText(formOfCareer1_enterYM);
                formOfCareer1_resignYM_EditText.setText(formOfCareer1_resignYM);
                formOfCareer1_office_EditText.setText(formOfCareer1_office);
                formOfCareer1_task_EditText.setText(formOfCareer1_task);

                formOfCareer2_name_EditText.setText(formOfCareer2_name);
                formOfCareer2_enterYM_EditText.setText(formOfCareer2_enterYM);
                formOfCareer2_resignYM_EditText.setText(formOfCareer2_resignYM);
                formOfCareer2_office_EditText.setText(formOfCareer2_office);
                formOfCareer2_task_EditText.setText(formOfCareer2_task);

                formOfCareer3_name_EditText.setText(formOfCareer3_name);
                formOfCareer3_enterYM_EditText.setText(formOfCareer3_enterYM);
                formOfCareer3_resignYM_EditText.setText(formOfCareer3_resignYM);
                formOfCareer3_office_EditText.setText(formOfCareer3_office);
                formOfCareer3_task_EditText.setText(formOfCareer3_task);
            }
        });
        cmpltBtn = findViewById(R.id.cmpltBtn);
        cmpltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resume_formOfCareer.this, profile_screen.class);

                formOfCareer1_name = formOfCareer1_name_EditText.getText().toString().trim();
                formOfCareer1_enterYM = formOfCareer1_enterYM_EditText.getText().toString().trim();
                formOfCareer1_resignYM = formOfCareer1_resignYM_EditText.getText().toString().trim();
                formOfCareer1_office = formOfCareer1_office_EditText.getText().toString().trim();
                formOfCareer1_task = formOfCareer1_task_EditText.getText().toString().trim();

                formOfCareer2_name = formOfCareer2_name_EditText.getText().toString().trim();
                formOfCareer2_enterYM = formOfCareer2_enterYM_EditText.getText().toString().trim();
                formOfCareer2_resignYM = formOfCareer2_resignYM_EditText.getText().toString().trim();
                formOfCareer2_office = formOfCareer2_office_EditText.getText().toString().trim();
                formOfCareer2_task = formOfCareer2_task_EditText.getText().toString().trim();

                formOfCareer3_name = formOfCareer3_name_EditText.getText().toString().trim();
                formOfCareer3_enterYM = formOfCareer3_enterYM_EditText.getText().toString().trim();
                formOfCareer3_resignYM = formOfCareer3_resignYM_EditText.getText().toString().trim();
                formOfCareer3_office = formOfCareer3_office_EditText.getText().toString().trim();
                formOfCareer3_task = formOfCareer3_task_EditText.getText().toString().trim();
                if(mAuth.getCurrentUser() == null){

                }
                else{
                    userID = mAuth.getCurrentUser().getUid();
                    if(userID != null) {
                        DocumentReference documentReference = fStore.collection("users").document(userID).collection("profiles").document("formOfCareer");
                        Map<String, Object> user = new HashMap<>();
                        user.put("formOfCareer1_name", formOfCareer1_name);
                        user.put("formOfCareer1_enterYM", formOfCareer1_enterYM);
                        user.put("formOfCareer1_resignYM", formOfCareer1_resignYM);
                        user.put("formOfCareer1_office",formOfCareer1_office);
                        user.put("formOfCareer1_task", formOfCareer1_task);

                        user.put("formOfCareer2_name",formOfCareer2_name);
                        user.put("formOfCareer2_enterYM", formOfCareer2_enterYM);
                        user.put("formOfCareer2_resignYM", formOfCareer2_resignYM);
                        user.put("formOfCareer2_office",formOfCareer2_office);
                        user.put("formOfCareer2_task", formOfCareer2_task);

                        user.put("formOfCareer3_name", formOfCareer3_name);
                        user.put("formOfCareer3_enterYM",formOfCareer3_enterYM);
                        user.put("formOfCareer3_resignYM", formOfCareer3_resignYM);
                        user.put("formOfCareer3_office", formOfCareer3_office);
                        user.put("formOfCareer3_task",formOfCareer3_task);
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
