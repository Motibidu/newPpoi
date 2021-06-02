package org.dstadler.poiandroidtest.newpoi;

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

import java.util.HashMap;
import java.util.Map;

public class formOfCareer extends AppCompatActivity {
    private TextInputEditText formOfCareer0_name_EditText, formOfCareer0_enter_EditText ,formOfCareer0_office_EditText  ,formOfCareer0_task_EditText ,formOfCareer0_resign_EditText
            ,formOfCareer1_name_EditText, formOfCareer1_enter_EditText , formOfCareer1_office_EditText, formOfCareer1_task_EditText, formOfCareer1_resign_EditText
            ,formOfCareer2_name_EditText, formOfCareer2_enter_EditText, formOfCareer2_office_EditText, formOfCareer2_task_EditText, formOfCareer2_resign_EditText;
    private String formOfCareer0_name, formOfCareer0_enter, formOfCareer0_office , formOfCareer0_task, formOfCareer0_resign
            ,formOfCareer1_name, formOfCareer1_enter, formOfCareer1_office, formOfCareer1_task, formOfCareer1_resign
            ,formOfCareer2_name, formOfCareer2_enter, formOfCareer2_office, formOfCareer2_task, formOfCareer2_resign;


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
        setContentView(R.layout.resume_form_of_career);

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

        formOfCareer0_name_EditText = findViewById(R.id.formOfCareer0_name_EditText);
        formOfCareer0_enter_EditText = findViewById(R.id.formOfCareer0_enter_EditText);
        formOfCareer0_resign_EditText = findViewById(R.id.formOfCareer0_resign_EditText);
        formOfCareer0_office_EditText = findViewById(R.id.formOfCareer0_office_EditText);
        formOfCareer0_task_EditText = findViewById(R.id.formOfCareer0_task_EditText);

        formOfCareer1_name_EditText = findViewById(R.id.formOfCareer1_name_EditText);
        formOfCareer1_enter_EditText = findViewById(R.id.formOfCareer1_enter_EditText);
        formOfCareer1_resign_EditText = findViewById(R.id.formOfCareer1_resign_EditText);
        formOfCareer1_office_EditText = findViewById(R.id.formOfCareer1_office_EditText);
        formOfCareer1_task_EditText = findViewById(R.id.formOfCareer1_task_EditText);

        formOfCareer2_name_EditText = findViewById(R.id.formOfCareer2_name_EditText);
        formOfCareer2_enter_EditText = findViewById(R.id.formOfCareer2_enter_EditText);
        formOfCareer2_resign_EditText = findViewById(R.id.formOfCareer2_resign_EditText);
        formOfCareer2_office_EditText = findViewById(R.id.formOfCareer2_office_EditText);
        formOfCareer2_task_EditText = findViewById(R.id.formOfCareer2_task_EditText);

        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("formOfCareer");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                formOfCareer0_name= value.getString("formOfCareer0_name");
                formOfCareer0_enter= value.getString("formOfCareer0_enter");
                formOfCareer0_resign= value.getString("formOfCareer0_resign");
                formOfCareer0_office = value.getString("formOfCareer0_office");
                formOfCareer0_task= value.getString("formOfCareer0_task");

                formOfCareer1_name= value.getString("formOfCareer1_name");
                formOfCareer1_enter= value.getString("formOfCareer1_enter");
                formOfCareer1_resign= value.getString("formOfCareer1_resign");
                formOfCareer1_office= value.getString("formOfCareer1_office");
                formOfCareer1_task= value.getString("formOfCareer1_task");

                formOfCareer2_name= value.getString("formOfCareer2_name");
                formOfCareer2_enter= value.getString("formOfCareer2_enter");
                formOfCareer2_resign= value.getString("formOfCareer2_resign");
                formOfCareer2_office= value.getString("formOfCareer2_office");
                formOfCareer2_task= value.getString("formOfCareer2_task");

                formOfCareer0_name_EditText.setText(formOfCareer0_name);
                formOfCareer0_enter_EditText.setText(formOfCareer0_enter);
                formOfCareer0_resign_EditText.setText(formOfCareer0_resign);
                formOfCareer0_office_EditText.setText(formOfCareer0_office);
                formOfCareer0_task_EditText.setText(formOfCareer0_task);

                formOfCareer1_name_EditText.setText(formOfCareer1_name);
                formOfCareer1_enter_EditText.setText(formOfCareer1_enter);
                formOfCareer1_resign_EditText.setText(formOfCareer1_resign);
                formOfCareer1_office_EditText.setText(formOfCareer1_office);
                formOfCareer1_task_EditText.setText(formOfCareer1_task);

                formOfCareer2_name_EditText.setText(formOfCareer2_name);
                formOfCareer2_enter_EditText.setText(formOfCareer2_enter);
                formOfCareer2_resign_EditText.setText(formOfCareer2_resign);
                formOfCareer2_office_EditText.setText(formOfCareer2_office);
                formOfCareer2_task_EditText.setText(formOfCareer2_task);
            }
        });
        completeButton = findViewById(R.id.complete_button);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(formOfCareer.this, profile_screen.class);

                formOfCareer0_name = formOfCareer0_name_EditText.getText().toString().trim();
                formOfCareer0_enter = formOfCareer0_enter_EditText.getText().toString().trim();
                formOfCareer0_resign = formOfCareer0_resign_EditText.getText().toString().trim();
                formOfCareer0_office = formOfCareer0_office_EditText.getText().toString().trim();
                formOfCareer0_task = formOfCareer0_task_EditText.getText().toString().trim();

                formOfCareer1_name = formOfCareer1_name_EditText.getText().toString().trim();
                formOfCareer1_enter = formOfCareer1_enter_EditText.getText().toString().trim();
                formOfCareer1_resign = formOfCareer1_resign_EditText.getText().toString().trim();
                formOfCareer1_office = formOfCareer1_office_EditText.getText().toString().trim();
                formOfCareer1_task = formOfCareer1_task_EditText.getText().toString().trim();

                formOfCareer2_name = formOfCareer2_name_EditText.getText().toString().trim();
                formOfCareer2_enter = formOfCareer2_enter_EditText.getText().toString().trim();
                formOfCareer2_resign = formOfCareer2_resign_EditText.getText().toString().trim();
                formOfCareer2_office = formOfCareer2_office_EditText.getText().toString().trim();
                formOfCareer2_task = formOfCareer2_task_EditText.getText().toString().trim();
                if(mAuth.getCurrentUser() == null){

                }
                else{
                    userID = mAuth.getCurrentUser().getUid();
                    if(userID != null) {
                        DocumentReference documentReference = fStore.collection("users").document(userID).collection("profiles").document("formOfCareer");
                        Map<String, Object> user = new HashMap<>();
                        user.put("formOfCareer0_name", formOfCareer0_name);
                        user.put("formOfCareer0_enter", formOfCareer0_enter);
                        user.put("formOfCareer0_resign", formOfCareer0_resign);
                        user.put("formOfCareer0_office",formOfCareer0_office);
                        user.put("formOfCareer0_task", formOfCareer0_task);

                        user.put("formOfCareer1_name",formOfCareer1_name);
                        user.put("formOfCareer1_enter", formOfCareer1_enter);
                        user.put("formOfCareer1_resign", formOfCareer1_resign);
                        user.put("formOfCareer1_office",formOfCareer1_office);
                        user.put("formOfCareer1_task", formOfCareer1_task);

                        user.put("formOfCareer2_name", formOfCareer2_name);
                        user.put("formOfCareer2_enter",formOfCareer2_enter);
                        user.put("formOfCareer2_resign", formOfCareer2_resign);
                        user.put("formOfCareer2_office", formOfCareer2_office);
                        user.put("formOfCareer2_task",formOfCareer2_task);
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
