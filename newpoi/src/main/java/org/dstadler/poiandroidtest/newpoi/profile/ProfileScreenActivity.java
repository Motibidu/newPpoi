package org.dstadler.poiandroidtest.newpoi.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;

public class ProfileScreenActivity extends AppCompatActivity {

    int profile_screen_number;
    private static int ACCOUNT_RQST_CODE = 0;
    private String userID;

    //for UI
    private Button account_setting_button, profile_setting_button;
    private TextView profileMenu_TextView, profileEdit_TextView;
    private ImageButton backBtn;
    private ImageView profile_screen_picture;
    private LinearLayout profileMenu, profileEditMenu;
    private Context mContext;

    //for firebase Google auth
    private FirebaseAuth mAuth;
    private GoogleSignInAccount account;
    private GoogleSignInClient mGoogleSignInClient;

    //for firebase storage
    private FirebaseStorage fStorage;
    private StorageReference storageReference;

    //for frag management
    private FragmentManager fm;
    private FragmentTransaction ft;
    private boolean b_simpleProfile, b_detailedProfile, b_resumeProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        mContext = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
        storageReference = fStorage.getInstance().getReference();
        
        profileMenu_TextView = findViewById(R.id.profileMenu_TextView);
        profileEdit_TextView = findViewById(R.id.profileEdit_TextView);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //프로필 팝업메뉴
        profileMenu = findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(profileMenu);
            }
        });

        profileEditMenu = findViewById(R.id.profile_edit_button);
        profileEditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup2(profileEditMenu);
            }
        });


        profile_screen_number = PreferenceManager.getInt(mContext,"profile_screen_number");
//        Toast.makeText(mContext,Integer.toString(profile_screen_number),Toast.LENGTH_SHORT).show();
        if(profile_screen_number == -1 || profile_screen_number == 0){
            b_simpleProfile= true; b_detailedProfile = false; b_resumeProfile = false;
            setFrag(0);
            profileMenu_TextView.setText("간편 프로필");
        }
        else if (profile_screen_number == 1){
            b_simpleProfile= false; b_detailedProfile = true; b_resumeProfile = false;
            setFrag(profile_screen_number);
            profileMenu_TextView.setText("세부 프로필");
        }
        else if (profile_screen_number == 2){
            b_simpleProfile= false; b_detailedProfile = false; b_resumeProfile = true;
            setFrag(profile_screen_number);
            profileMenu_TextView.setText("이력서 프로필");
        }
        //계정수정 화면으로 전환
        account_setting_button = (Button)findViewById(R.id.account_setting_button);
        account_setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileScreenActivity.this, AcctSetAcitivity.class);
                startActivityForResult(intent, ACCOUNT_RQST_CODE);
            }
        });

        //프로필수정 화면으로 전환
        /*profile_setting_button = (Button)findViewById(R.id.profile_setting_button);
        profile_setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃 상태일 때 "로그인 해주세요" 팝업문구를 띄운다.
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
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        updateUI(account);
    }


    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        popupMenu.inflate(R.menu.popup_profile);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_profile_simple:
                        profileMenu_TextView.setText("간편 프로필");
                        PreferenceManager.setInt(mContext, "profile_screen_number", 0);
                        setFrag(0);

                        b_simpleProfile = true;
                        b_detailedProfile = false;
                        b_resumeProfile = false;
//                        Toast.makeText(context,"simple profile", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_profile_detail:
                        profileMenu_TextView.setText("세부 프로필");
                        PreferenceManager.setInt(mContext, "profile_screen_number", 1);
                        setFrag(1);

                        b_simpleProfile = false;
                        b_detailedProfile = true;
                        b_resumeProfile = false;
//                        Toast.makeText(context,"detailed profile", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_profile_resume:
                        profileMenu_TextView.setText("이력서 프로필");
                        PreferenceManager.setInt(mContext, "profile_screen_number", 2);
                        setFrag(2);

                        b_simpleProfile = false;
                        b_detailedProfile = false;
                        b_resumeProfile = true;
//                        Toast.makeText(mContext,"resume profile", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }
    public void showPopup2(View v) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        popupMenu.inflate(R.menu.popup_profile_edit);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_profileEdit_personally:
                        if(mAuth.getCurrentUser() == null){
                            Toast.makeText(ProfileScreenActivity.this,"로그인 해주세요", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (b_simpleProfile){
                                Intent intent = new Intent(ProfileScreenActivity.this, profile_setting_simple.class);
                                startActivity(intent);
                            }
                            else if (b_detailedProfile){
                                Intent intent = new Intent(ProfileScreenActivity.this, profile_setting_detailed.class);
                                startActivity(intent);
                            }
                            else if (b_resumeProfile){
                                Intent intent = new Intent(ProfileScreenActivity.this, profile_setting_resume.class);
                                startActivity(intent);
                            }

                        }
                        return true;
                    case R.id.menu_profileEdit_scan:
                        Intent intent = new Intent(ProfileScreenActivity.this, ScanActivity.class);
                        startActivity(intent);
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
        DetailProfileFragment DetailProfileFragment = new DetailProfileFragment();
        profile_screen_resume profile_screen_resume = new profile_screen_resume();

        switch(n) {
            case 0:
                ft.replace(R.id.content, profile_screen_simple);
                ft.commitNow();
                break;
            case 1:
                ft.replace(R.id.content, DetailProfileFragment);
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
