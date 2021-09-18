package org.dstadler.poiandroidtest.newpoi;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class profile_screen_detailed extends Fragment {
    private View view;
    private Activity activity;

    private TextView profile_name_content, profile_e_name_content, profile_rrn_content,
            profile_age_content, profile_address_content, profile_email_content, profile_phoneNumber_content,
            profile_ch_name_content, profile_number_content, profile_SNS_content, profile_screen_picture;
    private String userID, name, e_name, rrn, age, address, email, phoneNumber, ch_name, number, SNS;

    private FirebaseStorage fStorage;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_screen_detailed, container, false);

        activity = getActivity();

        profile_name_content = view.findViewById(R.id.profile_name_content);
        profile_e_name_content = view.findViewById(R.id.profile_e_name_content);
        profile_rrn_content = view.findViewById(R.id.profile_rrn_content);
        profile_age_content = view.findViewById(R.id.profile_age_content);
        profile_address_content = view.findViewById(R.id.profile_address_content);
        profile_email_content = view.findViewById(R.id.profile_email_content);
        profile_phoneNumber_content = view.findViewById(R.id.profile_phoneNumber_content);
        profile_ch_name_content = view.findViewById(R.id.profile_ch_name_content);
        profile_number_content = view.findViewById(R.id.profile_number_content);
        profile_SNS_content = view.findViewById(R.id.profile_SNS_content);
        profile_screen_picture = view.findViewById(R.id.profile_screen_picture);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    private void updateUI(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
//        Toast.makeText(profile_screen.this,account.toString(),Toast.LENGTH_SHORT).show();

        if(account != null) {
//            Toast.makeText(profile_screen.this,account.toString(),Toast.LENGTH_SHORT).show();
            mAuth = FirebaseAuth.getInstance();
            storageReference = FirebaseStorage.getInstance().getReference();
            if (mAuth.getCurrentUser() != null) {
                userID = mAuth.getCurrentUser().getUid();

                DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                    StorageReference profileRef = storageReference.child("users/"+userID+"/profile.jpg");
//                    profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            Picasso.get().load(uri).into(profile_screen_picture);
//                        }
//                    });
                        if (value != null && value.exists()) {
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
                    }
                });
            } else if (account == null) {
                profile_ch_name_content.setText("");
                profile_number_content.setText("");
                profile_SNS_content.setText("");
                profile_name_content.setText("");
                profile_e_name_content.setText("");
                profile_rrn_content.setText("");
                profile_age_content.setText("");
                profile_address_content.setText("");
                profile_email_content.setText("");
                profile_phoneNumber_content.setText("");
//            Toast.makeText(profile_screen.this,"???",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
