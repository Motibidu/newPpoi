package org.dstadler.poiandroidtest.newpoi.profile;

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

public class ResumeLangActivity extends AppCompatActivity {
    private ImageButton imageBtn_back;
    private Button btn_complete;
    private TextInputEditText TextInputEditText_lang1, TextInputEditText_test1, TextInputEditText_score1, TextInputEditText_institution1,
            TextInputEditText_lang2, TextInputEditText_test2, TextInputEditText_score2, TextInputEditText_institution2,
            TextInputEditText_lang3, TextInputEditText_test3, TextInputEditText_score3, TextInputEditText_institution3;
    private String lang1, test1, score1, institution1,
            lang2, test2, score2, institution2,
            lang3, test3, score3, institution3;


    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    private StorageReference storageReference;
    private String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_lang);

        imageBtn_back = findViewById(R.id.imageBtn_back);
        btn_complete = findViewById(R.id.btn_complete);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();
        storageReference = fStorage.getReference();

        TextInputEditText_lang1= findViewById(R.id.TextInputEditText_lang1);
        TextInputEditText_test1= findViewById(R.id.TextInputEditText_test1);
        TextInputEditText_score1 = findViewById(R.id.TextInputEditText_score1);
        TextInputEditText_institution1= findViewById(R.id.TextInputEditText_institution1);
        TextInputEditText_lang2= findViewById(R.id.TextInputEditText_lang2);
        TextInputEditText_test2= findViewById(R.id.TextInputEditText_test2);
        TextInputEditText_score2= findViewById(R.id.TextInputEditText_score2);
        TextInputEditText_institution2= findViewById(R.id.TextInputEditText_institution2);
        TextInputEditText_lang3= findViewById(R.id.TextInputEditText_lang3);
        TextInputEditText_test3= findViewById(R.id.TextInputEditText_test3);
        TextInputEditText_score3= findViewById(R.id.TextInputEditText_score3);
        TextInputEditText_institution3= findViewById(R.id.TextInputEditText_institution3);

        imageBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("lang");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                lang1 = value.getString("lang1");
                test1 = value.getString("test1");
                score1 = value.getString("score1");
                institution1 = value.getString("institution1");
                lang2 = value.getString("lang2");
                test2 = value.getString("test2");
                score2 = value.getString("score2");
                institution2 = value.getString("institution2");
                lang3 = value.getString("lang3");
                test3 = value.getString("test3");
                score3 = value.getString("score3");
                institution3 = value.getString("institution3");

                TextInputEditText_lang1.setText(lang1);
                TextInputEditText_test1.setText(test1);
                TextInputEditText_score1.setText(score1);
                TextInputEditText_institution1.setText(institution1);
                TextInputEditText_lang2.setText(lang2);
                TextInputEditText_test2.setText(test2);
                TextInputEditText_score2.setText(score2);
                TextInputEditText_institution2.setText(institution2);
                TextInputEditText_lang3.setText(lang3);
                TextInputEditText_test3.setText(test3);
                TextInputEditText_score3.setText(score3);
                TextInputEditText_institution3.setText(institution3);
            }
        });

        //완료 버튼
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang1 = TextInputEditText_lang1.getText().toString().trim();
                test1 = TextInputEditText_test1.getText().toString().trim();
                score1 = TextInputEditText_score1.getText().toString().trim();
                institution1 = TextInputEditText_institution1.getText().toString().trim();
                lang2 = TextInputEditText_lang2.getText().toString().trim();
                test2 = TextInputEditText_test2.getText().toString().trim();
                score2 = TextInputEditText_score2.getText().toString().trim();
                institution2 = TextInputEditText_institution2.getText().toString().trim();
                lang3 = TextInputEditText_lang3.getText().toString().trim();
                test3 = TextInputEditText_test3.getText().toString().trim();
                score3 = TextInputEditText_score3.getText().toString().trim();
                institution3 = TextInputEditText_institution3.getText().toString().trim();

                //로그아웃 상태일 때 하는 수행은 없다.
                if(mAuth.getCurrentUser() == null){

                }
                //로그인 상태일 때 사용자가 기록을 마치고 완료버튼을 누르면 Firestore의 collection("users").document(userID).collection("profiles").document("eduBack")경로에 기록내용들을 저장한다.
                else{
                    userID = mAuth.getCurrentUser().getUid();
                    if(userID != null) {
                        DocumentReference documentReference = fStore.collection("users").document(userID).collection("profiles").document("lang");
                        Map<String, Object> user = new HashMap<>();
                        user.put("lang1", lang1);
                        user.put("test1", test1);
                        user.put("score1", score1);
                        user.put("institution1", institution1);
                        user.put("lang2", lang2);
                        user.put("test2", test2);
                        user.put("score2", score2);
                        user.put("institution2", institution2);
                        user.put("lang3", lang3);
                        user.put("test3", test3);
                        user.put("score3", score3);
                        user.put("institution3", institution3);
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
