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
import androidx.fragment.app.Fragment;
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

public class ProfileScrnActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_profile_scrn);

        //content
        mContext = getApplicationContext();

        //widget
        backBtn = findViewById(R.id.backBtn);
        profileMenu_TextView = findViewById(R.id.profileMenu_TextView);
        profileEdit_TextView = findViewById(R.id.profileEdit_TextView);
        profileMenu = findViewById(R.id.profileMenu);
        profileEditMenu = findViewById(R.id.profile_edit_button);
        account_setting_button = (Button)findViewById(R.id.account_setting_button);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        storageReference = fStorage.getInstance().getReference();

        //back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //프로필 팝업메뉴
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(profileMenu);
            }
        });


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

        account_setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileScrnActivity.this, AcctSetAcitivity.class);
                startActivityForResult(intent, ACCOUNT_RQST_CODE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        updateUI(account);

//        Fragment frg = getSupportFragmentManager().findFragmentByTag(PreferenceManager.getString(mContext,"fragmentTag_ProfileScrnActivity"));
//        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.detach(frg);
//        ft.attach(frg);
//        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

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
    }

    @Override
    protected void onStop() {
        super.onStop();
//        PreferenceManager.setInt(mContext, "profile_screen_number", 0);
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
                            Toast.makeText(ProfileScrnActivity.this,"로그인 해주세요", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (b_simpleProfile){
                                Intent intent = new Intent(ProfileScrnActivity.this, ProfileSetSimpleActivity.class);
                                startActivity(intent);
                            }
                            else if (b_detailedProfile){
                                Intent intent = new Intent(ProfileScrnActivity.this, ProfileSetDetailActivity.class);
                                startActivity(intent);
                            }
                            else if (b_resumeProfile){
//                                Intent intent = new Intent(ProfileScrnActivity.this, ProfileSetResumeActivity.class);
//                                startActivity(intent);
                                Toast.makeText(mContext,"위 버튼을 이용해주시면 감사하겠습니다.",Toast.LENGTH_SHORT).show();
                            }

                        }
                        return true;
                    case R.id.menu_profileEdit_scan:
                        Intent intent = new Intent(ProfileScrnActivity.this, ScanActivity.class);
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

        ProfileScrnSimpleFragment ProfileScrnSimpleFragment = new ProfileScrnSimpleFragment();
        ProfileDetailFragment ProfileDetailFragment = new ProfileDetailFragment();
        ProfileScrnResumeFragment ProfileScrnResumeFragment = new ProfileScrnResumeFragment();

        switch(n) {
            case 0:
                ft.replace(R.id.content, ProfileScrnSimpleFragment, "0");
                PreferenceManager.setString(mContext, "fragmentTag_ProfileScrnActivity", "0");
                ft.commitNow();
                break;
            case 1:
                ft.replace(R.id.content, ProfileDetailFragment, "1");
                PreferenceManager.setString(mContext, "fragmentTag_ProfileScrnActivity", "1");
                ft.commitNow();
                break;
            case 2:
                ft.replace(R.id.content, ProfileScrnResumeFragment, "2");
                PreferenceManager.setString(mContext, "fragmentTag_ProfileScrnActivity", "2");
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
