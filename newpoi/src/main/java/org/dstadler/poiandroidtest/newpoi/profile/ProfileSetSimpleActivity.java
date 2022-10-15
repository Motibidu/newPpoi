package org.dstadler.poiandroidtest.newpoi.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class ProfileSetSimpleActivity extends AppCompatActivity{
    private static final String TAG = "PROFILESETSIMPLEACTIVITY";


    private Context mContext;
    private AppCompatActivity activity;



    private ImageButton imageButton;
    private EditText profile_EditText_name, profile_EditText_rrn, profile_EditText_phoneNumber,
            profile_EditText_address, profile_EditText_email, profile_EditText_age;

    private Button profile_picture_loadButton, complete_profile_setting_button, profile_menu;
    private String userID;
    private String name, rrn, age, phoneNumber, email, address;

    public Uri imageUri;
    public ImageView profile_picture;

    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private DocumentReference documentReference;
    private GoogleSignInAccount account;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setting_simple);
        //firebase



        mContext = getApplicationContext();
        account = GoogleSignIn.getLastSignedInAccount(mContext);
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        fStore = FirebaseFirestore.getInstance();

//        updateUI(account);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.themeColor));






        profile_EditText_name = findViewById(R.id.profile_EditText_name);
        profile_EditText_rrn = findViewById(R.id.profile_EditText_rrn);
        profile_EditText_age = findViewById(R.id.profile_EditText_age);
        profile_EditText_phoneNumber = findViewById(R.id.profile_EditText_phoneNumber);
        profile_EditText_email = findViewById(R.id.profile_EditText_email);
        profile_EditText_address = findViewById(R.id.profile_EditText_address);

        profile_picture_loadButton = findViewById(R.id.profile_picture_loadButton);
        profile_picture = findViewById(R.id.profile_picture);


        imageButton = findViewById(R.id.profile_setting_back_button);
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

//        documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
//        if(documentReference != null && mAuth.getCurrentUser() != null) {
//            documentReference.addSnapshotListener(ProfileSetSimpleActivity.this, new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                    try {
//                        name = value.getString("name");
//                        rrn = value.getString("rrn");
//                        age = value.getString("age");
//                        address = value.getString("address");
//                        phoneNumber = value.getString("phoneNumber");
//                        email = value.getString("email");
//
//
//                        profile_EditText_name.setText(name);
//                        profile_EditText_rrn.setText(rrn);
//                        profile_EditText_age.setText(age);
//                        profile_EditText_address.setText(address);
//                        profile_EditText_phoneNumber.setText(phoneNumber);
//                        profile_EditText_email.setText(email);
//                    } catch (NullPointerException e) {
//                        Log.d(TAG, "onEvent: " + e.toString());
//                    }
//                }
//
//            });
//        }

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
                Intent intent = new Intent(mContext, ProfileScrnActivity.class);
//                Toast.makeText(profile_setting.this,imageUri.toString(),Toast.LENGTH_SHORT).show();

                name = profile_EditText_name.getText().toString().trim();
                rrn = profile_EditText_rrn.getText().toString().trim();
                age = profile_EditText_age.getText().toString().trim();
                phoneNumber = profile_EditText_phoneNumber.getText().toString().trim();
                email = profile_EditText_email.getText().toString().trim();
                address = profile_EditText_address.getText().toString().trim();

                if(mAuth.getCurrentUser() == null){

                }
                else{
                    userID = mAuth.getCurrentUser().getUid();
                    if(userID != null) {
                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("rrn", rrn);
                        user.put("age", age);
                        user.put("phoneNumber", phoneNumber);
                        user.put("email", email);
                        user.put("address", address);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProfileSetSimpleActivity.this, "onSuccess : user Profile is created for " + name, Toast.LENGTH_SHORT).show();
                            }
                        });
                        documentReference.set(user).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: "+e.toString());
                            }
                        });
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        updateUI(account);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        updateUI(account);
//    }
    @Override
    public void onResume() {
        super.onResume();
//        updateUI(account);
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
                                        .with(mContext)
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
                userID = mAuth.getCurrentUser().getUid();
                mAuth = FirebaseAuth.getInstance();
                storageReference = FirebaseStorage.getInstance().getReference();
                Log.d(TAG, "userID: "+userID);

                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
                if(documentReference != null && mAuth.getCurrentUser() != null) {
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (value != null && value.exists()) {

                                name = value.getString("name");
                                rrn = value.getString("rrn");
                                age = value.getString("age");
                                address = value.getString("address");
                                phoneNumber = value.getString("phoneNumber");
                                email = value.getString("email");


                                profile_EditText_name.setText(name);
                                profile_EditText_rrn.setText(rrn);
                                profile_EditText_age.setText(age);
                                profile_EditText_address.setText(address);
                                profile_EditText_phoneNumber.setText(phoneNumber);
                                profile_EditText_email.setText(email);
                            }
                            else{
                                Log.d(TAG, "onEvent error: "+error);
                            }
                        }

                    });
                }
            }
        }
        else if (account == null){
            profile_EditText_name.setText("");
            profile_EditText_rrn.setText("");
            profile_EditText_age.setText("");
            profile_EditText_address.setText("");
            profile_EditText_phoneNumber.setText("");
            profile_EditText_email.setText("");
//            Toast.makeText(profile_screen.this,"???",Toast.LENGTH_SHORT).show();
        }
    }
}
