package org.dstadler.poiandroidtest.newpoi.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Constant;

import java.util.HashMap;
import java.util.Map;


public class ScanProfileFragment extends Fragment{
    private static final String TAG = "SCANPROFILEFRAGMENT";
    private View v;
    private EditText edit_name, edit_engName, edit_chName, edit_rrn, edit_phoneNum,
            edit_addr, edit_email, edit_age;
    private ImageButton imageButton_name, imageButton_engName, imageButton_chName, imageButton_rrn, imageButton_phoneNum,
            imageButton_addr, imageButton_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_scan_profile, container, false);

        //EditText
        edit_name = v.findViewById(R.id.edit_name);
        edit_engName = v.findViewById(R.id.edit_engName);
        edit_chName = v.findViewById(R.id.edit_chName);
        edit_rrn = v.findViewById(R.id.edit_rrn);
        edit_age = v.findViewById(R.id.edit_age);
        edit_phoneNum = v.findViewById(R.id.edit_phoneNum);
        edit_email = v.findViewById(R.id.edit_email);
        edit_addr = v.findViewById(R.id.edit_addr);

        //ImageButton
        imageButton_name = v.findViewById(R.id.imagebutton_name);
        imageButton_engName = v.findViewById(R.id.imagebutton_engName);
        imageButton_chName = v.findViewById(R.id.imagebutton_chName);
        imageButton_rrn = v.findViewById(R.id.imagebutton_rrn);
        imageButton_phoneNum = v.findViewById(R.id.imagebutton_phoneNum);
        imageButton_email = v.findViewById(R.id.imagebutton_email);
        imageButton_addr = v.findViewById(R.id.imagebutton_addr);

        return v;
    }

}