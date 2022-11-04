package org.dstadler.poiandroidtest.newpoi.profile;

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

import org.dstadler.poiandroidtest.newpoi.R;

import java.util.HashMap;
import java.util.Map;

public class ResumeEcubackActivity extends AppCompatActivity {
    private TextInputEditText highschool_enterYM_EditText, highschool_graYM_EditText, highschool_name_EditText, highschool_graCls_EditText,
            university_enterYM_EditText, university_graYM_EditText, university_graCls_EditText, university_name_EditText, university_major_EditText,
            master_enterYM_EditText, master_graYM_EditText, master_graCls_EditText, master_name_EditText, master_major_EditText, master_graThe_EditText,master_LAB_EditText;
    private String highschool_enterYM,  highschool_graYM, highschool_name, highschool_graCls, university_enterYM, university_graYM, university_graCls, university_name,
            university_major, master_enterYM, master_graYM, master_graCls, master_name, master_major, master_graThe, master_LAB;


    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    private StorageReference storageReference;
    private String userID;

    private Context mContext;
    private Button cmpltBtn;
    private ImageButton backBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_eduback);
        mContext = getApplicationContext();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


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

        highschool_enterYM_EditText = findViewById(R.id.highschool_enterYM_EditText);
        highschool_graYM_EditText = findViewById(R.id.highschool_graYM_EditText);
        highschool_name_EditText = findViewById(R.id.highschool_name_EditText);
        highschool_graCls_EditText = findViewById(R.id.highschool_graCls_EditText);

        university_enterYM_EditText = findViewById(R.id.university_enterYM_EditText);
        university_graYM_EditText = findViewById(R.id.university_graYM_EditText);
        university_graCls_EditText = findViewById(R.id.university_graCls_EditText);
        university_name_EditText = findViewById(R.id.university_name_EditText);
        university_major_EditText = findViewById(R.id.university_major_EditText);

        master_enterYM_EditText = findViewById(R.id.master_enterYM_EditText);
        master_graYM_EditText = findViewById(R.id.master_graYM_EditText);
        master_graCls_EditText = findViewById(R.id.master_graCls_EditText);
        master_name_EditText = findViewById(R.id.master_name_EditText);
        master_major_EditText = findViewById(R.id.master_major_EditText);
        master_graThe_EditText = findViewById(R.id.master_graThe_EditText);
        master_LAB_EditText = findViewById(R.id.master_LAB_EditText);

        //FirebaseFirestore의 collection("users").document(userID).collection("profiles").document("eduBack")에서 학력사항을 가져오고 각 항목에 맞게 EditText에 삽입한다.
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("eduBack");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                highschool_enterYM = value.getString("highschool_enterYM");
                highschool_graYM = value.getString("highschool_graYM");
                highschool_name=value.getString("highschool_name");
                highschool_graCls = value.getString("highschool_graCls");
                university_enterYM = value.getString("university_enterYM");
                university_graYM=value.getString("university_graYM");
                university_graCls = value.getString("university_graCls");
                university_name=value.getString("university_name");
                university_major = value.getString("university_major");
                master_enterYM = value.getString("master_enterYM");
                master_graYM = value.getString("master_graYM");
                master_graCls = value.getString("master_graCls");
                master_name = value.getString("master_name");
                master_major = value.getString("master_major");
                master_graThe = value.getString("master_graThe");
                master_LAB = value.getString("master_LAB");

                highschool_enterYM_EditText.setText(highschool_enterYM);
                highschool_graYM_EditText.setText(highschool_graYM);
                highschool_name_EditText.setText(highschool_name);
                highschool_graCls_EditText.setText(highschool_graCls);

                university_enterYM_EditText.setText(university_enterYM);
                university_graYM_EditText.setText(university_graYM);
                university_graCls_EditText.setText(university_graCls);
                university_name_EditText.setText(university_name);
                university_major_EditText.setText(university_major);

                master_enterYM_EditText.setText(master_enterYM);
                master_graYM_EditText.setText(master_graYM);
                master_graCls_EditText.setText(master_graCls);
                master_name_EditText.setText(master_name);
                master_major_EditText.setText(master_major);
                master_graThe_EditText.setText(master_graThe);
                master_LAB_EditText.setText(master_LAB);
            }
        });

        //완료 버튼
        cmpltBtn = findViewById(R.id.cmpltBtn);
        cmpltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResumeEcubackActivity.this, ProfileScrnActivity.class);

                highschool_enterYM = highschool_enterYM_EditText.getText().toString().trim();
                highschool_graYM = highschool_graYM_EditText.getText().toString().trim();
                highschool_name=highschool_name_EditText.getText().toString().trim();
                highschool_graCls = highschool_graCls_EditText.getText().toString().trim();

                university_enterYM = university_enterYM_EditText.getText().toString().trim();
                university_graYM=university_graYM_EditText.getText().toString().trim();
                university_graCls = university_graCls_EditText.getText().toString().trim();
                university_name=university_name_EditText.getText().toString().trim();
                university_major = university_major_EditText.getText().toString().trim();

                master_enterYM = master_enterYM_EditText.getText().toString().trim();
                master_graYM = master_graYM_EditText.getText().toString().trim();
                master_graCls = master_graCls_EditText.getText().toString().trim();
                master_name = master_name_EditText.getText().toString().trim();
                master_major = master_major_EditText.getText().toString().trim();
                master_graThe = master_graThe_EditText.getText().toString().trim();
                master_LAB = master_LAB_EditText.getText().toString().trim();

                //로그아웃 상태일 때 하는 수행은 없다.
                if(mAuth.getCurrentUser() == null){

                }
                //로그인 상태일 때 사용자가 기록을 마치고 완료버튼을 누르면 Firestore의 collection("users").document(userID).collection("profiles").document("eduBack")경로에 기록내용들을 저장한다.
                else{
                    userID = mAuth.getCurrentUser().getUid();
                    if(userID != null) {
                        DocumentReference documentReference = fStore.collection("users").document(userID).collection("profiles").document("eduBack");
                        Map<String, Object> user = new HashMap<>();
                        user.put("highschool_enterYM", highschool_enterYM);
                        user.put("highschool_graYM", highschool_graYM);
                        user.put("highschool_name",highschool_name);
                        user.put("highschool_graCls", highschool_graCls);
                        user.put("university_enterYM", university_enterYM);
                        user.put("university_graYM",university_graYM);
                        user.put("university_graCls", university_graCls);
                        user.put("university_name",university_name);
                        user.put("university_major", university_major);
                        user.put("master_enterYM", master_enterYM);
                        user.put("master_graYM", master_graYM);
                        user.put("master_graCls",master_graCls);
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
                    //profile_screen으로 화면을 전환한다.
                    onBackPressed();
                }
            }
        });
    }
}
