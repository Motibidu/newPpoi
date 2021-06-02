package org.dstadler.poiandroidtest.newpoi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

    int profile_screen_number;

    private Button account_setting_button, profile_setting_button;//, profile_menu;
    TextView profile_menu;
    private ImageButton profile_screen_back_button;
    private static int ACCOUNT_RQST_CODE = 0;
    private static int PROFILE_RQST_CODE = 1;

    private FirebaseStorage fStorage;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;

    private Uri imageUri;
    private ImageView profile_screen_picture;

    private GoogleSignInClient mGoogleSignInClient;
    private String userID;



    private Context context;
    private boolean b_simpleProfile, b_detailedProfile, b_resumeProfile;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private LinearLayout l;
    private GoogleSignInAccount account;


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
//        updateUI(user);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.themeColor));

        context = getApplicationContext();



        profile_screen_back_button = findViewById(R.id.profile_screen_back_button);
        profile_screen_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        l = findViewById(R.id.profile_menu);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(profile_menu);
            }
        });

        profile_menu = findViewById(R.id.profile_menu1);
        profile_screen_number = PreferenceManager.getInt(context,"profile_screen_number");
        Toast.makeText(context,Integer.toString(profile_screen_number),Toast.LENGTH_SHORT).show();
        if(profile_screen_number == -1 || profile_screen_number == 0){
            b_simpleProfile= true; b_detailedProfile = false; b_resumeProfile = false;
            setFrag(0);
            profile_menu.setText("간편 프로필");
        }
        else if (profile_screen_number == 1){
            b_simpleProfile= false; b_detailedProfile = true; b_resumeProfile = false;
            setFrag(profile_screen_number);
            profile_menu.setText("세부 프로필");
        }
        else if (profile_screen_number == 2){
            b_simpleProfile= false; b_detailedProfile = false; b_resumeProfile = true;
            setFrag(profile_screen_number);
            profile_menu.setText("이력서 프로필");
        }

        mAuth = FirebaseAuth.getInstance();
        storageReference = fStorage.getInstance().getReference();


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
                    if (b_simpleProfile){
                        Intent intent = new Intent(profile_screen.this, profile_setting_simple.class);
                        startActivity(intent);
                    }
                    else if (b_detailedProfile){
                        Intent intent = new Intent(profile_screen.this, profile_setting_detailed.class);
                        startActivity(intent);
                    }
                    else if (b_resumeProfile){
                        Intent intent = new Intent(profile_screen.this, profile_setting_resume.class);
                        startActivity(intent);
                    }

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        updateUI(account);
    }


    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.popup_profile);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.simple_profile:
                        profile_menu.setText("간편 프로필");
                        PreferenceManager.setInt(context, "profile_screen_number", 0);
                        setFrag(0);

                        b_simpleProfile = true;
                        b_detailedProfile = false;
                        b_resumeProfile = false;
//                        Toast.makeText(context,"simple profile", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.detailed_profile:
                        profile_menu.setText("세부 프로필");
                        PreferenceManager.setInt(context, "profile_screen_number", 1);
                        setFrag(1);

                        b_simpleProfile = false;
                        b_detailedProfile = true;
                        b_resumeProfile = false;
//                        Toast.makeText(context,"detailed profile", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.resume_profile:
                        profile_menu.setText("이력서 프로필");
                        PreferenceManager.setInt(context, "profile_screen_number", 2);
                        setFrag(2);

                        b_simpleProfile = false;
                        b_detailedProfile = false;
                        b_resumeProfile = true;
//                        Toast.makeText(context,"resume profile", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }

        });
        popupMenu.show();
    }
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        profile_screen_simple profile_screen_simple = new profile_screen_simple();
        profile_screen_detailed profile_screen_detailed = new profile_screen_detailed();
        profile_screen_resume profile_screen_resume = new profile_screen_resume();

        switch(n) {
            case 0:
                ft.replace(R.id.content, profile_screen_simple);
                ft.commitNow();
                break;
            case 1:
                ft.replace(R.id.content, profile_screen_detailed);
                ft.commitNow();
                break;
            case 2:
                ft.replace(R.id.content, profile_screen_resume);
                ft.commitNow();
                break;
            default:
                break;
        }
    }
    private void updateUI(GoogleSignInAccount account){

//        Toast.makeText(profile_screen.this,account.toString(),Toast.LENGTH_SHORT).show();

        if(isSignedIn()){
//            Toast.makeText(profile_screen.this,account.toString(),Toast.LENGTH_SHORT).show();
            if(mAuth.getCurrentUser() != null) {
                userID = mAuth.getCurrentUser().getUid();
                mAuth = FirebaseAuth.getInstance();
                storageReference = FirebaseStorage.getInstance().getReference();
                userID = mAuth.getCurrentUser().getUid();

                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && value.exists()) {
                            StorageReference profileRef = storageReference.child("users/" + userID + "/profile.jpg");
                            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    profile_screen_picture = findViewById(R.id.profile_screen_picture);
                                    Picasso.get().load(uri).into(profile_screen_picture);
                                }
                            });
                        }
                    }
                });
            }
        }
        else{
            profile_screen_picture = findViewById(R.id.profile_screen_picture);
            profile_screen_picture.setImageResource(0);
        }
    }
    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(getApplicationContext()) != null;
    }
}
