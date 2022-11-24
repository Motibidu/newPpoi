package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;

import java.util.HashMap;

public class ContractExpirationLaborActivity extends InnerClass{
    //수신자, 계약날짜, 계약만료날짜, 문서작성날짜
    //주소, 사업체명, 이름
    //['수신자', ' 계약날짜', ' 계약만료날짜', ' 문서작성날짜', ' 주소', ' 사업체명', ' 이름']
    //['recepient', 'Contract date', 'Contract expiration date', 'Document date', 'address', 'Business name', 'name']
    //['rP', 'cD', 'cED', 'docD', 'addr', 'bN', 'name']

    private String
            rP, cD, cED, docD, addr, bN, name;

    private TextInputEditText
            TextInputEditText_rP, TextInputEditText_cD, TextInputEditText_cED, TextInputEditText_docD, TextInputEditText_addr, TextInputEditText_bN, TextInputEditText_name;

    private TextInputLayout
            TextInputLayout_rP, TextInputLayout_cD, TextInputLayout_cED, TextInputLayout_docD, TextInputLayout_addr, TextInputLayout_bN, TextInputLayout_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextInputEditText_rP = findViewById(R.id.TextInputEditText_rP);
        TextInputEditText_cD = findViewById(R.id.TextInputEditText_cD);
        TextInputEditText_cED = findViewById(R.id.TextInputEditText_cED);
        TextInputEditText_docD = findViewById(R.id.TextInputEditText_docD);
        TextInputEditText_addr = findViewById(R.id.TextInputEditText_addr);
        TextInputEditText_bN = findViewById(R.id.TextInputEditText_bN);
        TextInputEditText_name = findViewById(R.id.TextInputEditText_name);


        TextInputLayout_rP = findViewById(R.id.TextInputLayout_rP);
        TextInputLayout_cD = findViewById(R.id.TextInputLayout_cD);
        TextInputLayout_cED = findViewById(R.id.TextInputLayout_cED);
        TextInputLayout_docD = findViewById(R.id.TextInputLayout_docD);
        TextInputLayout_addr = findViewById(R.id.TextInputLayout_addr);
        TextInputLayout_bN = findViewById(R.id.TextInputLayout_bN);
        TextInputLayout_name = findViewById(R.id.TextInputLayout_name);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_contract_expiration_labor;
    }

    @Override
    protected Intent getIntentFromEachActivity() {
        return getIntent();
    }

    @Override
    protected void updateUI() {
        if (isSignedIn()) {
            mAuth = FirebaseAuth.getInstance();
            userID = mAuth.getCurrentUser().getUid();
            bExpanded = false;

            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
            documentReference.addSnapshotListener(mActivity, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        name = value.getString("name");
                        addr = value.getString("addr");
                        //불러온 모든 텍스트들을 TextInputEditText에 넣는다.
                        setAllTextInTextInputEditText();

                    }
                }
            });
        }
        rP = PreferenceManager.getString(mContext,"rP");
        cD = PreferenceManager.getString(mContext,"cD");
        cED = PreferenceManager.getString(mContext,"cED");
        docD = PreferenceManager.getString(mContext,"docD");
        bN = PreferenceManager.getString(mContext,"bN");
    }

    @Override
    protected void setAllTextInTextInputEditText() {
        TextInputEditText_rP.setText(rP);
        TextInputEditText_cD.setText(cD);
        TextInputEditText_cED.setText(cED);
        TextInputEditText_docD.setText(docD);
        TextInputEditText_addr.setText(addr);
        TextInputEditText_bN.setText(bN);
        TextInputEditText_name.setText(name);
    }

    @Override
    protected HashMap<String, String> getAllTextFromTextInputEditText() {
        rP = TextInputEditText_rP.getText().toString().trim();
        cD = TextInputEditText_cD.getText().toString().trim();
        cED = TextInputEditText_cED.getText().toString().trim();
        docD = TextInputEditText_docD.getText().toString().trim();
        addr = TextInputEditText_addr.getText().toString().trim();
        bN = TextInputEditText_bN.getText().toString().trim();
        name = TextInputEditText_name.getText().toString().trim();

        HashMap<String, String> attrs = new HashMap<>();
        attrs.put("rP", rP);
        attrs.put("cD", cD);
        attrs.put("cED", cED);
        attrs.put("docD", docD);
        attrs.put("addr", addr);
        attrs.put("bN", bN);
        attrs.put("name", name);
        return attrs;
    }

    @Override
    protected void setAllPreferences() {
        PreferenceManager.setString(mContext,"rP",TextInputEditText_rP.getText().toString().trim());
        PreferenceManager.setString(mContext,"cD",TextInputEditText_cD.getText().toString().trim());
        PreferenceManager.setString(mContext,"cED",TextInputEditText_cED.getText().toString().trim());
        PreferenceManager.setString(mContext,"docD",TextInputEditText_docD.getText().toString().trim());
        PreferenceManager.setString(mContext,"bN",TextInputEditText_bN.getText().toString().trim());
    }
}
