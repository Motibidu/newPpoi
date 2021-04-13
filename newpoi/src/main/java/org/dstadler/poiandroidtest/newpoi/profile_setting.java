package org.dstadler.poiandroidtest.newpoi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class profile_setting extends AppCompatActivity {

    private ImageButton imageButton;
    private EditText profile_EditText_name, profile_EditText_birth, profile_EditText_phoneNumber, profile_EditText_address;
    private Button profile_picture_loadButton, complete_profile_setting_button;
    private String userID, fullName, birth, phoneNumber, address, imageKey;
    public Uri imageUri;
    public ImageView profile_picture;

    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setting);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();
        storageReference = fStorage.getReference();

        profile_EditText_name = (EditText)findViewById(R.id.profile_EditText_name);
        profile_EditText_birth = (EditText)findViewById(R.id.profile_EditText_birth);
        profile_EditText_phoneNumber = (EditText)findViewById(R.id.profile_EditText_phoneNumber);
        profile_EditText_address = (EditText)findViewById(R.id.profile_EditText_address);
        profile_picture_loadButton = (Button)findViewById(R.id.profile_picture_loadButton);
        profile_picture = (ImageView)findViewById(R.id.profile_picture);


        imageButton = (ImageButton) findViewById(R.id.profile_setting_back_button);
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
        documentReference.addSnapshotListener(profile_setting.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fullName = value.getString("fullName");
                birth = value.getString("birth");
                address = value.getString("address");
                phoneNumber = value.getString("phoneNumber");
                profile_EditText_name.setText(fullName);
                profile_EditText_birth.setText(birth);
                profile_EditText_address.setText(phoneNumber);
                profile_EditText_phoneNumber.setText(address);
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
                Intent intent = new Intent(profile_setting.this, profile_screen.class);
//                Toast.makeText(profile_setting.this,imageUri.toString(),Toast.LENGTH_SHORT).show();
                fullName = profile_EditText_name.getText().toString().trim();
                birth = profile_EditText_birth.getText().toString().trim();
                phoneNumber = profile_EditText_phoneNumber.getText().toString().trim();
                address = profile_EditText_address.getText().toString().trim();

                if(mAuth.getCurrentUser() == null){

                }
                else{
                    userID = mAuth.getCurrentUser().getUid();
                    if(userID != null) {
                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("fullName", fullName);
                        user.put("birth", birth);
                        user.put("phoneNumber", phoneNumber);
                        user.put("address", address);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(profile_setting.this, "onSuccess : user Profile is created for " + fullName, Toast.LENGTH_SHORT).show();
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
                                Picasso.get().load(uri).into(profile_picture);
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
                        Toast.makeText(profile_setting.this,"Failed Upload", Toast.LENGTH_SHORT).show();
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
