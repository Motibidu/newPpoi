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

public class InvoiceBalanceActivity extends InnerClass{

    ////상세내용////
    //수신자, 송금날짜, 잔액확인서날짜, 문서작성날짜

    ////발신자 상세내용////
    //주소, 사업체명, 이름

    //['수신자', ' 송금날짜', ' 잔액확인서 날짜', ' 문서작성 날짜', ' 주소', ' 사업체명', ' 이름']
    //['recipient', 'Remittance date', 'Balance Confirmation Date', 'Document date', 'address', 'Business name', 'name']
    //['rP', 'rD', 'bCD', 'docD', 'addr', 'bN', 'name']
    //(수신자 : rP,  송금날짜 : rD,  잔액확인서 날짜 : bCD,  문서작성 날짜 : docD,  주소 : addr,  사업체명 : bN,  이름 : name, )
    private String
            rP, rD, bCD, docD, addr, bN, name;

    private TextInputEditText
            TextInputEditText_rP, TextInputEditText_rD, TextInputEditText_bCD, TextInputEditText_docD, TextInputEditText_addr, TextInputEditText_bN, TextInputEditText_name;
    private TextInputLayout
            TextInputLayout_rP, TextInputLayout_rD, TextInputLayout_bCD, TextInputLayout_docD, TextInputLayout_addr, TextInputLayout_bN, TextInputLayout_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextInputEditText_rP = findViewById(R.id.TextInputEditText_rP);
        TextInputEditText_rD = findViewById(R.id.TextInputEditText_rD);
        TextInputEditText_bCD = findViewById(R.id.TextInputEditText_bCD);
        TextInputEditText_docD = findViewById(R.id.TextInputEditText_docD);
        TextInputEditText_addr = findViewById(R.id.TextInputEditText_addr);
        TextInputEditText_bN = findViewById(R.id.TextInputEditText_bN);
        TextInputEditText_name = findViewById(R.id.TextInputEditText_name);

        TextInputLayout_rP = findViewById(R.id.TextInputLayout_rP);
        TextInputLayout_rD = findViewById(R.id.TextInputLayout_rD);
        TextInputLayout_bCD = findViewById(R.id.TextInputLayout_bCD);
        TextInputLayout_docD = findViewById(R.id.TextInputLayout_docD);
        TextInputLayout_addr = findViewById(R.id.TextInputLayout_addr);
        TextInputLayout_bN = findViewById(R.id.TextInputLayout_bN);
        TextInputLayout_name = findViewById(R.id.TextInputLayout_name);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_invoice_balance;
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
            //FirebaseFirestore의 collection("users").document(userID)에서 이름, 이메일, 휴대전화, 주소를 가져오고
            //EditText에 적용한다.
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
            documentReference.addSnapshotListener(mActivity, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        name = value.getString("name");
                        addr = value.getString("addr");
                    }
                }
            });
        }
        rP = PreferenceManager.getString(mContext,"rP");
        rD = PreferenceManager.getString(mContext,"rD");
        bCD = PreferenceManager.getString(mContext,"bCD");
        docD = PreferenceManager.getString(mContext,"docD");
        addr = PreferenceManager.getString(mContext,"addr");
    }

    @Override
    protected void setAllTextInTextInputEditText() {
        TextInputEditText_rP.setText(rP);
        TextInputEditText_rD.setText(rD);
        TextInputEditText_bCD.setText(bCD);
        TextInputEditText_docD.setText(docD);
        TextInputEditText_addr.setText(addr);
        TextInputEditText_bN.setText(bN);
        TextInputEditText_name.setText(name);
    }

    @Override
    protected HashMap<String, String> getAllTextFromTextInputEditText() {
        rP = TextInputEditText_rP.getText().toString().trim();
        rD = TextInputEditText_rD.getText().toString().trim();
        bCD = TextInputEditText_bCD.getText().toString().trim();
        docD = TextInputEditText_docD.getText().toString().trim();
        addr = TextInputEditText_addr.getText().toString().trim();
        bN = TextInputEditText_bN.getText().toString().trim();
        name = TextInputEditText_name.getText().toString().trim();

        HashMap<String, String> attrs = new HashMap<>();
        attrs.put("rP", rP);
        attrs.put("rD", rD);
        attrs.put("bCD", bCD);
        attrs.put("docD", docD);
        attrs.put("addr", addr);
        attrs.put("bN", bN);
        attrs.put("name", name);
        return attrs;
    }

    @Override
    protected void setAllPreferences() {
        PreferenceManager.setString(mContext,"rP",TextInputEditText_rP.getText().toString().trim());
        PreferenceManager.setString(mContext,"rD",TextInputEditText_rD.getText().toString().trim());
        PreferenceManager.setString(mContext,"bCD",TextInputEditText_bCD.getText().toString().trim());
        PreferenceManager.setString(mContext,"docD",TextInputEditText_docD.getText().toString().trim());
    }
}
