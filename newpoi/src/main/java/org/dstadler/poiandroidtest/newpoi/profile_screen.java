package org.dstadler.poiandroidtest.newpoi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private Button account_setting_button, profile_setting_button, profile_menu;
    private ImageButton profile_screen_back_button;
    private static int ACCOUNT_RQST_CODE = 0;
    private static int PROFILE_RQST_CODE = 1;
    private TextView profile_name_content, profile_e_name_content, profile_rrn_content,
    profile_age_content, profile_address_content, profile_email_content, profile_phoneNumber_content,
            profile_ch_name_content, profile_number_content, profile_SNS_content;
    private String userID, name, e_name, rrn, age, address, email, phoneNumber, ch_name, number, SNS;

    private Uri imageUri;
    private ImageView profile_screen_picture;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    private StorageReference storageReference;
    private Context context;
    private boolean simple_profile, detailed_profile, resume_profile;

    private FragmentManager fm;
    private FragmentTransaction ft;

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

        context = getApplicationContext();

//        profile_screen_back_button = (ImageButton)findViewById(R.id.profile_screen_back_button);
//        profile_screen_back_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        profile_menu = findViewById(R.id.profile_menu);
//        PopupMenu popupMenu = new PopupMenu(context, profile_menu);
//        popupMenu.getMenuInflater().inflate(R.menu.popup_profile, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                int id = menuItem.getItemId();
//                if(id == R.id.simple_profile){
//                    simple_profile  = true;
//                    detailed_profile = false;
//                    resume_profile = false;
//                    Toast.makeText(context,"simple profile", Toast.LENGTH_SHORT).show();
//                }
//                else if(id == R.id.detailed_profile){
//                    simple_profile  = false;
//                    detailed_profile = true;
//                    resume_profile = false;
//                    Toast.makeText(context,"detailed profile", Toast.LENGTH_SHORT).show();
//                }
//                else if(id == R.id.resume_profile){
//                    simple_profile  = false;
//                    detailed_profile = false;
//                    resume_profile = true;
//                    Toast.makeText(context,"resume profile", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//
//        });
        profile_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(profile_menu);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        storageReference = fStorage.getInstance().getReference();

        profile_name_content = findViewById(R.id.profile_name_content);
        profile_e_name_content = findViewById(R.id.profile_e_name_content);
        profile_rrn_content = findViewById(R.id.profile_rrn_content);
        profile_age_content = findViewById(R.id.profile_age_content);
        profile_address_content = findViewById(R.id.profile_address_content);
        profile_email_content = findViewById(R.id.profile_email_content);
        profile_phoneNumber_content = findViewById(R.id.profile_phoneNumber_content);
        profile_ch_name_content = findViewById(R.id.profile_ch_name_content);
        profile_number_content = findViewById(R.id.profile_number_content);
        profile_SNS_content = findViewById(R.id.profile_SNS_content);

        profile_screen_picture = findViewById(R.id.profile_screen_picture);



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
                    e_name = value.getString("e_name");
                    rrn = value.getString("rrn");
                    age = value.getString("age");
                    address = value.getString("address");
                    email = value.getString("email");
                    phoneNumber = value.getString("phoneNumber");

                    ch_name = value.getString("ch_name");
                    number = value.getString("number");
                    SNS = value.getString("SNS");

                    profile_ch_name_content.setText(ch_name);
                    profile_number_content.setText(number);
                    profile_SNS_content.setText(SNS);

                    profile_name_content.setText(name);
                    profile_e_name_content.setText(e_name);
                    profile_rrn_content.setText(rrn);
                    profile_age_content.setText(age);
                    profile_address_content.setText(address);
                    profile_email_content.setText(email);
                    profile_phoneNumber_content.setText(phoneNumber);

                }
            });
        }
        else if (account == null){
            profile_name_content.setText("");
            profile_rrn_content.setText("");
            profile_address_content.setText("");
            profile_phoneNumber_content.setText("");
//            Toast.makeText(profile_screen.this,"???",Toast.LENGTH_SHORT).show();
        }
    }
    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.popup_profile);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
//                int id = menuItem.getItemId();
//                if(id == R.id.simple_profile){
//                    simple_profile  = true;
//                    detailed_profile = false;
//                    resume_profile = false;
//                    Toast.makeText(context,"simple profile", Toast.LENGTH_SHORT).show();
//                }
//                else if(id == R.id.detailed_profile){
//                    simple_profile  = false;
//                    detailed_profile = true;
//                    resume_profile = false;
//                    Toast.makeText(context,"detailed profile", Toast.LENGTH_SHORT).show();
//                }
//                else if(id == R.id.resume_profile){
//                    simple_profile  = false;
//                    detailed_profile = false;
//                    resume_profile = true;
//                    Toast.makeText(context,"resume profile", Toast.LENGTH_SHORT).show();
//                }
//                return false;
                switch (menuItem.getItemId()){
                    case R.id.simple_profile:
                        if (menuItem.getTitle().toString().equals("똥똥똥")) {
                            addContact(0);
                        }
                        Toast.makeText(context,"simple profile", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.detailed_profile:
                        if (menuItem.getTitle().toString().equals("세부 프로필")) {
                            addContact(1);
                        }
                        Toast.makeText(context,"detailed profile", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.resume_profile:
                        if (menuItem.getTitle().toString().equals("이력서 프로필")) {
                            addContact(2);
                        }
                        Toast.makeText(context,"resume profile", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }

        });

        Menu menuOpts = popupMenu.getMenu();
        if (simple_profile) {
            menuOpts.getItem(0).setTitle("간편 프로필");
        }
        if (detailed_profile) {
            menuOpts.getItem(1).setTitle("세부 프로필");
        }
        if (resume_profile) {
            menuOpts.getItem(3).setTitle("이력서 프로필");
        }

        popupMenu.show();
    }
    private void addContact(final int circle) {
        switch (circle) {
            case 0:
                simple_profile = true;
                detailed_profile = false;
                resume_profile = false;
                break;
            case 1:
                simple_profile = false;
                detailed_profile = true;
                resume_profile = false;
                break;
            case 3:
                simple_profile = false;
                detailed_profile = false;
                resume_profile = true;
                break;

        }
    }
//    private void setFrag(int n){
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        switch(n) {
//            case 0:
//                ft.replace(R.id.content, recentitems);
//                ft.commit();
//                break;
//            case 1:
//                ft.replace(R.id.content, examples);
//                ft.commit();
//                break;
//            case 2:
//                ft.replace(R.id.content, open);
//                ft.commit();
//                break;
//            case 3:
//                ft.replace(R.id.content, bookmarked);
//                ft.commit();
//                break;
//        }
//    }
}
