package org.dstadler.poiandroidtest.newpoi.profile;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import android.app.ProgressDialog;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;





import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Constant;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

public class ProfileSetSimpleActivity extends AppCompatActivity{
    private static final String TAG = "PROFILESETSIMPLEACTIVITY";

    //contents
    private Context mContext;

    //widgets
    private ImageButton imageButton_back;
    private EditText editText_name, editText_rrn, editText_age, editText_phoneNum, editText_email, editText_addr;
    private Button profile_picture_loadButton, complete_profile_setting_button, profile_menu;
    private Button button_complete, button_clear;
    private Button button_pictureLoad;
    private LinearLayout layout_clear_restore;
    private AlertDialog.Builder builder;
    public ImageView image_picture;


    //firebase
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private StorageReference storageRef, profileRef;
    private GoogleSignInAccount account;
    private DocumentReference documentRef;

    //String
    private String userID;
    private String name, rrn, age, phoneNum, email, addr;


    public Uri imageUri;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting_simple);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.themeColor));

        //initiate
        //contents
        mContext = getApplicationContext();

        //firebase instances
        account = GoogleSignIn.getLastSignedInAccount(mContext);
        mAuth = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();


        //widgets
        imageButton_back = findViewById(R.id.imageButton_back);
        layout_clear_restore = findViewById(R.id.layout_clear_restore);
        button_complete = findViewById(R.id.button_complete);

        image_picture = findViewById(R.id.image_picture);
        button_pictureLoad = findViewById(R.id.button_pictureLoad);

        editText_name = findViewById(R.id.editText_name);
        editText_rrn = findViewById(R.id.editText_rrn);
        editText_age = findViewById(R.id.editText_age);
        editText_phoneNum = findViewById(R.id.editText_phoneNum);
        editText_email = findViewById(R.id.editText_email);
        editText_addr = findViewById(R.id.editText_addr);



//        if(PreferenceManager.getString(mContext,"previous_name").isEmpty()){
//            PreferenceManager.setString(mContext, "previous_name", name);
//        }
//        if(PreferenceManager.getString(mContext,"previous_rrn").isEmpty()){
//            PreferenceManager.setString(mContext, "previous_rrn", rrn);
//        }
//        if(PreferenceManager.getString(mContext,"previous_age").isEmpty()){
//            PreferenceManager.setString(mContext, "previous_age", age);
//        }
//        if(PreferenceManager.getString(mContext,"previous_phoneNum").isEmpty()){
//            PreferenceManager.setString(mContext, "previous_phoneNum", phoneNum);
//        }
//        if(PreferenceManager.getString(mContext,"previous_email").isEmpty()){
//            PreferenceManager.setString(mContext, "previous_email", email);
//        }
//        if(PreferenceManager.getString(mContext,"previous_addr").isEmpty()){
//            PreferenceManager.setString(mContext, "previous_addr", addr);
//        }




        //functions
        //뒤로가기
        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layout_clear_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(layout_clear_restore);
            }
        });

        //update fireStore
        button_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ProfileSetSimpleActivity.this);
                builder.setTitle("알람")
                        .setMessage("완료하시겠습니까?")
                        .setCancelable(true)
                        .setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(mContext, ProfileScrnActivity.class);
//                Toast.makeText(profile_setting.this,imageUri.toString(),Toast.LENGTH_SHORT).show();

                                name = editText_name.getText().toString().trim();
                                rrn = editText_rrn.getText().toString().trim();
                                age = editText_age.getText().toString().trim();
                                phoneNum = editText_phoneNum.getText().toString().trim();
                                email = editText_email.getText().toString().trim();
                                addr = editText_addr.getText().toString().trim();

                                if(mAuth.getCurrentUser() == null){

                                }
                                else {
                                    if (userID != null) {
                                        documentRef = fStore.collection("users").document(userID);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("name", name);
                                        user.put("rrn", rrn);
                                        user.put("age", age);
                                        user.put("phoneNum", phoneNum);
                                        user.put("email", email);
                                        user.put("addr", addr);
                                        documentRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(ProfileSetSimpleActivity.this, "onSuccess : user Profile is created for " + name, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        documentRef.set(user).addOnFailureListener(new OnFailureListener() {
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: " + e.toString());
                                            }
                                        });
                                    }
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            }
                        })
                        .show();
            }
        });

        //프로필 사진 선택하기
        button_pictureLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        //이미지 불러오기
        profileRef = storageRef.child("users/"+userID+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(image_picture);
            }
        });

        updateUI(account);


    }

    @Override
    protected void onStop() {
        super.onStop();
        name = editText_name.getText().toString().trim();
        rrn = editText_rrn.getText().toString().trim();
        age = editText_age.getText().toString().trim();
        phoneNum = editText_phoneNum.getText().toString().trim();
        email = editText_email.getText().toString().trim();
        addr = editText_addr.getText().toString().trim();

        PreferenceManager.setString(mContext, "previous_name", name);
        PreferenceManager.setString(mContext, "previous_rrn", rrn);
        PreferenceManager.setString(mContext, "previous_age", age);
        PreferenceManager.setString(mContext, "previous_phoneNum", phoneNum);
        PreferenceManager.setString(mContext, "previous_email", email);
        PreferenceManager.setString(mContext, "previous_addr", addr);
    }


    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        popupMenu.inflate(R.menu.clear_restore);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_clear:
                        editText_name.setText("");
                        editText_rrn.setText("");
                        editText_age.setText("");
                        editText_addr.setText("");
                        editText_phoneNum.setText("");
                        editText_email.setText("");
                        editText_addr.setText("");
                        image_picture.setImageResource(0);
                        return true;
                    case R.id.menu_restore:
                        editText_name.setText(PreferenceManager.getString(mContext,"previous_name"));
                        editText_rrn.setText(PreferenceManager.getString(mContext,"previous_rrn"));
                        editText_age.setText(PreferenceManager.getString(mContext,"previous_age"));
                        editText_addr.setText(PreferenceManager.getString(mContext,"previous_addr"));
                        editText_phoneNum.setText(PreferenceManager.getString(mContext,"previous_phoneNum"));
                        editText_email.setText(PreferenceManager.getString(mContext,"previous_email"));
                        editText_addr.setText(PreferenceManager.getString(mContext,"previous_addr"));
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }


    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null&&data.getData()!=null){
            imageUri = data.getData();
            uploadPicture(imageUri);
        }
    }
    private void uploadPicture(Uri imageUri){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        StorageReference riversRef = storageRef.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide
                                        .with(mContext)
                                        .load(uri)
                                        .into(image_picture);
                                pd.dismiss();
                                Snackbar.make(findViewById(R.id.profile_setting_entry),"Image uploaded", Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(mContext,"Failed Upload", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int)progressPercent+"%");
                    }
                });
    }
    private void updateUI(GoogleSignInAccount account){
        if(account != null) {
            if (mAuth.getCurrentUser() != null) {

                documentRef = FirebaseFirestore.getInstance().collection("users").document(userID);
                if(documentRef != null && mAuth.getCurrentUser() != null) {
                    documentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (value != null && value.exists()) {
                                name = value.getString("name");
                                rrn = value.getString("rrn");
                                age = value.getString("age");
                                phoneNum = value.getString("phoneNum");
                                email = value.getString("email");
                                addr = value.getString("addr");

                                editText_name.setText(name);
                                editText_rrn.setText(rrn);
                                editText_age.setText(age);
                                editText_phoneNum.setText(phoneNum);
                                editText_email.setText(email);
                                editText_addr.setText(addr);
                            }
                            else{
                                Log.d(TAG, "onEvent error: "+error);
                            }
                        }
                    });
                }
            }
        }
        else{
            editText_name.setText("");
            editText_rrn.setText("");
            editText_age.setText("");
            editText_addr.setText("");
            editText_phoneNum.setText("");
            editText_email.setText("");
            editText_addr.setText("");
//            Toast.makeText(profile_screen.this,"???",Toast.LENGTH_SHORT).show();
        }
    }
}
