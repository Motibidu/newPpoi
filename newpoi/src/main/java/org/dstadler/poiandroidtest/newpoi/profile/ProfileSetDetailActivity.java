package org.dstadler.poiandroidtest.newpoi.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
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

import java.util.HashMap;
import java.util.Map;

public class ProfileSetDetailActivity extends AppCompatActivity{

    private ImageButton imageButton;
    private EditText profile_EditText_name, profile_EditText_rrn, profile_EditText_phoneNum,
            profile_EditText_addr, profile_EditText_email, profile_EditText_e_name, profile_EditText_age,
            profile_EditText_ch_name, profile_EditText_SNS, profile_EditText_number;

    private Button profile_picture_loadButton, complete_profile_setting_button, profile_menu;
    private String userID;
    private String name, e_name, ch_name, rrn, age, SNS, phoneNum, number, email, addr;

    public Uri imageUri;
    public ImageView profile_picture;

    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting_detail);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.themeColor));

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();
        storageReference = fStorage.getReference();

        profile_EditText_name = findViewById(R.id.profile_EditText_name);
        profile_EditText_e_name = findViewById(R.id.profile_EditText_e_name);
        profile_EditText_ch_name = findViewById(R.id.profile_EditText_ch_name);
        profile_EditText_rrn = findViewById(R.id.profile_EditText_rrn);
        profile_EditText_age = findViewById(R.id.profile_EditText_age);
        profile_EditText_SNS = findViewById(R.id.profile_EditText_SNS);
        profile_EditText_phoneNum = findViewById(R.id.profile_EditText_phoneNum);
        profile_EditText_number = findViewById(R.id.profile_EditText_number);
        profile_EditText_email = findViewById(R.id.profile_EditText_email);
        profile_EditText_addr = findViewById(R.id.profile_EditText_addr);


        profile_picture_loadButton = findViewById(R.id.profile_picture_loadButton);
        profile_picture = findViewById(R.id.profile_picture);



        imageButton =  findViewById(R.id.profile_setting_back_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        profile_picture_loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });



        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
        documentReference.addSnapshotListener(ProfileSetDetailActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name = value.getString("name");
                e_name = value.getString("e_name");
                ch_name = value.getString("ch_name");
                rrn = value.getString("rrn");
                age = value.getString("age");
                SNS = value.getString("SNS");
                addr = value.getString("addr");
                phoneNum = value.getString("phoneNum");
                number = value.getString("number");
                email = value.getString("email");


                profile_EditText_name.setText(name);
                profile_EditText_e_name.setText(e_name);
                profile_EditText_ch_name .setText(ch_name);
                profile_EditText_rrn.setText(rrn);
                profile_EditText_age.setText(age);
                profile_EditText_SNS.setText(SNS);
                profile_EditText_addr.setText(addr);
                profile_EditText_phoneNum.setText(phoneNum);
                profile_EditText_number.setText(number);
                profile_EditText_email.setText(email);



            }
        });
        StorageReference profileRef = storageReference.child("users/"+userID+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile_picture);
            }
        });

        complete_profile_setting_button = (Button)findViewById(R.id.complete_profile_setting_button);
        complete_profile_setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSetDetailActivity.this, ProfileScrnActivity.class);
//                Toast.makeText(profile_setting.this,imageUri.toString(),Toast.LENGTH_SHORT).show();

                name = profile_EditText_name.getText().toString().trim();
                e_name = profile_EditText_e_name.getText().toString().trim();
                ch_name=profile_EditText_ch_name.getText().toString().trim();
                rrn = profile_EditText_rrn.getText().toString().trim();
                age = profile_EditText_age.getText().toString().trim();
                SNS=profile_EditText_SNS.getText().toString().trim();
                phoneNum = profile_EditText_phoneNum.getText().toString().trim();
                number=profile_EditText_number.getText().toString().trim();
                email = profile_EditText_email.getText().toString().trim();
                addr = profile_EditText_addr.getText().toString().trim();

                if(mAuth.getCurrentUser() == null){

                }
                else{
                    userID = mAuth.getCurrentUser().getUid();
                    if(userID != null) {
                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("e_name", e_name);
                        user.put("ch_name",ch_name);
                        user.put("rrn", rrn);
                        user.put("age", age);
                        user.put("SNS",SNS);
                        user.put("phoneNum", phoneNum);
                        user.put("number",number);
                        user.put("email", email);
                        user.put("addr", addr);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProfileSetDetailActivity.this, "onSuccess : user Profile is created for " + name, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
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
//            profile_picture.setImageURI(imageUri);
            uploadPicture(imageUri);
        }
    }
    private void uploadPicture(Uri imageUri){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

//        imageKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide
                                    .with(ProfileSetDetailActivity.this)
                                    .load(uri)
                                    .into(profile_picture);
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
                        Toast.makeText(ProfileSetDetailActivity.this,"Failed Upload", Toast.LENGTH_SHORT).show();
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
}
