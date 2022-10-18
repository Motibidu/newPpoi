package org.dstadler.poiandroidtest.newpoi.profile;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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

public class ProfileScrnSimpleFragment extends Fragment {
    private View view;
    private Activity activity;

    private TextView profile_name_content, profile_rrn_content, profile_age_content, profile_address_content, profile_email_content, profile_phoneNumber_content;
    private Context mContext;


    private String userID;
    String name, rrn, age, address, email, phoneNumber;

    private FirebaseStorage fStorage;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private GoogleSignInAccount account;
    private DocumentReference documentReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        account = GoogleSignIn.getLastSignedInAccount(getActivity());
        activity = getActivity();
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mContext);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_screen_simple, container, false);

        profile_name_content = view.findViewById(R.id.profile_name_content);
        profile_rrn_content = view.findViewById(R.id.profile_rrn_content);
        profile_age_content = view.findViewById(R.id.profile_age_content);
        profile_address_content = view.findViewById(R.id.profile_address_content);
        profile_email_content = view.findViewById(R.id.profile_email_content);
        profile_phoneNumber_content = view.findViewById(R.id.profile_phoneNumber_content);
        updateUI(account);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(account);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void updateUI(GoogleSignInAccount account){
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
//        Toast.makeText(activity,account.toString(),Toast.LENGTH_SHORT).show();

        if(account != null){
            if(mAuth.getCurrentUser() != null) {
                userID = mAuth.getCurrentUser().getUid();
                Toast.makeText(mContext,userID,Toast.LENGTH_SHORT).show();

                documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
                if(documentReference != null && mAuth.getCurrentUser() != null) {
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(value !=null && value.exists()) {
                                name = value.getString("name");
                                rrn = value.getString("rrn");
                                age = value.getString("age");
                                address = value.getString("address");
                                email = value.getString("email");
                                phoneNumber = value.getString("phoneNumber");

                                profile_name_content.setText(name);
                                profile_rrn_content.setText(rrn);
                                profile_age_content.setText(age);
                                profile_address_content.setText(address);
                                profile_email_content.setText(email);
                                profile_phoneNumber_content.setText(phoneNumber);
                            }
                        }
                    });
            }
//            Toast.makeText(profile_screen_simple.this, userID, Toast.LENGTH_SHORT).show();
            }
        }
        else if (account == null){
            profile_name_content.setText("");
            profile_rrn_content.setText("");
            profile_age_content.setText("");
            profile_address_content.setText("");
            profile_email_content.setText("");
            profile_phoneNumber_content.setText("");
//            Toast.makeText(profile_screen.this,"???",Toast.LENGTH_SHORT).show();
        }
    }
}
