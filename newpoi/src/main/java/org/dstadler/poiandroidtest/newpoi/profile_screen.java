package org.dstadler.poiandroidtest.newpoi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class profile_screen extends AppCompatActivity {
    private Button account_setting_button, profile_setting_button;
    private ImageButton profile_screen_back_button;
    private static int ACCOUNT_RQST_CODE = 0;
    private static int PROFILE_RQST_CODE = 1;
    private TextView profile_name_content, profile_eame_content, profile_rnn_content,
    profile_age_content, profile_address_content, profile_email_content, profile_phoneNumber_content;
    private String userID, name, eame, rnn, age, address, email, phoneNumber;

    private Uri imageUri;
    private ImageView profile_screen_picture;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    private StorageReference storageReference;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == ACCOUNT_RQST_CODE){
//            if(resultCode==RESULT_OK){
////                Toast.makeText(profile_screen.this,data.getStringExtra("email"), Toast.LENGTH_SHORT).show();
////                profile_TextView_name_content = findViewById(R.id.profile_TextView_name_content);
////                String email = data.getStringExtra("email");
////                profile_TextView_name_content.setText(email);
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if(user == null){
//                    Toast.makeText(profile_screen.this,"null",Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(profile_screen.this,"not null",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }
//        else if(requestCode == PROFILE_RQST_CODE){
//            if(resultCode == RESULT_OK){
//                Toast.makeText(profile_screen.this,"HI", Toast.LENGTH_SHORT).show();
//            }
//        }
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.themeColor));

        mAuth = FirebaseAuth.getInstance();
        storageReference = fStorage.getInstance().getReference();

        profile_name_content = findViewById(R.id.profile_name_content);
        profile_eame_content = findViewById(R.id.profile_eame_content);
        profile_rnn_content = findViewById(R.id.profile_rnn_content);
        profile_age_content = findViewById(R.id.profile_age_content);
        profile_address_content = findViewById(R.id.profile_address_content);
        profile_email_content = findViewById(R.id.profile_email_content);
        profile_phoneNumber_content = findViewById(R.id.profile_phoneNumber_content);


        profile_screen_picture = findViewById(R.id.profile_screen_picture);

        profile_screen_back_button = (ImageButton)findViewById(R.id.profile_screen_back_button);
        profile_screen_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        account_setting_button = (Button)findViewById(R.id.account_setting_button);
        account_setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_screen.this, account_setting.class);
                startActivityForResult(intent, ACCOUNT_RQST_CODE);
            }
        });

        profile_setting_button = (Button)findViewById(R.id.profile_setting_button);
        profile_setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser() == null){
                    Toast.makeText(profile_screen.this,"로그인 해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(profile_screen.this, profile_setting.class);
                    startActivityForResult(intent, PROFILE_RQST_CODE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            Toast.makeText(profile_screen.this,"mAuth.get CurrentUser() = null", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(profile_screen.this,"mAuth.get CurrentUser() = not null", Toast.LENGTH_SHORT).show();
        }
        updateUI(user);
    }

    private void updateUI(FirebaseUser fUser){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
//        Toast.makeText(profile_screen.this,account.toString(),Toast.LENGTH_SHORT).show();

        if(account != null){
//            Toast.makeText(profile_screen.this,account.toString(),Toast.LENGTH_SHORT).show();
            userID = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
            documentReference.addSnapshotListener(profile_screen.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    StorageReference profileRef = storageReference.child("users/"+userID+"/profile.jpg");
                    profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(profile_screen_picture);
                        }
                    });

                    name = value.getString("name");
                    eame = value.getString("eame");
                    rnn = value.getString("rnn");
                    age = value.getString("age");
                    address = value.getString("address");
                    email = value.getString("email");
                    phoneNumber = value.getString("phoneNumber");

                    profile_name_content.setText(name);
                    profile_eame_content.setText(eame);
                    profile_rnn_content.setText(rnn);
                    profile_age_content.setText(age);
                    profile_address_content.setText(address);
                    profile_email_content.setText(email);
                    profile_phoneNumber_content.setText(phoneNumber);

                }
            });
        }
        else if (account == null){
            profile_name_content.setText("");
            profile_rnn_content.setText("");
            profile_address_content.setText("");
            profile_phoneNumber_content.setText("");
//            Toast.makeText(profile_screen.this,"???",Toast.LENGTH_SHORT).show();
        }
    }
}