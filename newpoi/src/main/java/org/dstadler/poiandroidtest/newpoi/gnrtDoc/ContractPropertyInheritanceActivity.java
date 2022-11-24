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

public class ContractPropertyInheritanceActivity extends InnerClass{

    private String
            bLoc, bUAA, lLoc, lAA, prin, wON, sM1, sM2, sM3, gapN, gapAddr, gapRRN, gapPN, eulN, eulAddr, eulRRN, eulPN, bR1, bR2, docD;

    private TextInputEditText
            TextInputEditText_bLoc, TextInputEditText_bUAA, TextInputEditText_lLoc, TextInputEditText_lAA, TextInputEditText_prin, TextInputEditText_wON, TextInputEditText_sM1, TextInputEditText_sM2, TextInputEditText_sM3, TextInputEditText_gapN, TextInputEditText_gapAddr, TextInputEditText_gapRRN, TextInputEditText_gapPN, TextInputEditText_eulN, TextInputEditText_eulAddr, TextInputEditText_eulRRN, TextInputEditText_eulPN, TextInputEditText_bR1, TextInputEditText_bR2, TextInputEditText_docD;
    private TextInputLayout
            TextInputLayout_bLoc, TextInputLayout_bUAA, TextInputLayout_lLoc, TextInputLayout_lAA, TextInputLayout_prin, TextInputLayout_wON, TextInputLayout_sM1, TextInputLayout_sM2, TextInputLayout_sM3, TextInputLayout_gapN, TextInputLayout_gapAddr, TextInputLayout_gapRRN, TextInputLayout_gapPN, TextInputLayout_eulN, TextInputLayout_eulAddr, TextInputLayout_eulRRN, TextInputLayout_eulPN, TextInputLayout_bR1, TextInputLayout_bR2, TextInputLayout_docD;

    //상속부동산
    //건물소재지, 건물 용도 및 면적, 토지소재지, 토지 지목 및 면적

    //배신행위
    //월 원금(단위:원) 원정(단위:\)

    //특약사항 1, 2, 3
    //갑 상세정보
    //이름, 주소, 주민번호, 연락처
    //을 상세정보
    //이름, 주소, 주민번호, 연락처, 형제관계1, 형제관계2

    //['건물소재지', ' 건물 용도 및 면적', ' 토지소재지', ' 토지 지목 및 면적',
    // ' 원금', ' 원정', ' 특약사항1', ' 특약사항2', ' 특약사항3', ' 갑 이름', ' 갑 주소', ' 갑 주민번호', ' 갑 연락처', ' 을 이름', ' 을 주소', ' 을 주민번호', ' 을 연락처', '을 형제관계1', ' 을 형제관계2']

    //['Building Location', 'Building usage and area', 'Land Location', 'Landmark and area','principal', 'wON',
    // 'Special Matters 1', 'Special Matter 2', 'Special Matter 3',
    // 'Names', 'Gap address', 'Gap social security number', 'Contact information',
    // 'Name', 'The address', 'Eul social security number', 'Contact', 'Brother relationship 1', 'Brother relationship 2']

//    ['bLoc', 'bUAA', 'lLoc', 'lAA', 'prin', 'wON',
//     'sM1', 'sM2', 'sM3',
//     'gapN', 'gapAddr', 'gapRRN', 'gapPN',
//     'eulN', 'eulAddr', 'eulRRN', 'eulPN', 'bR1', 'bR2']
    //(건물소재지 : bLoc,  건물 용도 및 면적 : bUAA,  토지소재지 : lLoc,  토지 지목및 면적 : lAA,  원금 : prin,  원정 : wON,  특약사항1 : sM1,  특약사항2 : sM2,  특약사항3 : sM3,
    // 갑 이름 : gapN,  갑 주소 : gapAddr,  갑 주민번호 : gapRRN,  갑 연락처 : gapPN,  을 이름 : eulN,  을 주소 : eulAddr,  을 주민번호 : eulRRN,  을 연락처 : eulPN, 을 형제관계1 : bR1,  을 형제관계2 : bR2)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextInputEditText_bLoc = findViewById(R.id.TextInputEditText_bLoc);
        TextInputEditText_bUAA = findViewById(R.id.TextInputEditText_bUAA);
        TextInputEditText_lLoc = findViewById(R.id.TextInputEditText_lLoc);
        TextInputEditText_lAA = findViewById(R.id.TextInputEditText_lAA);
        TextInputEditText_prin = findViewById(R.id.TextInputEditText_prin);
        TextInputEditText_wON = findViewById(R.id.TextInputEditText_wON);
        TextInputEditText_sM1 = findViewById(R.id.TextInputEditText_sM1);
        TextInputEditText_sM2 = findViewById(R.id.TextInputEditText_sM2);
        TextInputEditText_sM3 = findViewById(R.id.TextInputEditText_sM3);
        TextInputEditText_gapN = findViewById(R.id.TextInputEditText_gapN);
        TextInputEditText_gapAddr = findViewById(R.id.TextInputEditText_gapAddr);
        TextInputEditText_gapRRN = findViewById(R.id.TextInputEditText_gapRRN);
        TextInputEditText_gapPN = findViewById(R.id.TextInputEditText_gapPN);
        TextInputEditText_eulN = findViewById(R.id.TextInputEditText_eulN);
        TextInputEditText_eulAddr = findViewById(R.id.TextInputEditText_eulAddr);
        TextInputEditText_eulRRN = findViewById(R.id.TextInputEditText_eulRRN);
        TextInputEditText_eulPN = findViewById(R.id.TextInputEditText_eulPN);
        TextInputEditText_bR1 = findViewById(R.id.TextInputEditText_bR1);
        TextInputEditText_bR2 = findViewById(R.id.TextInputEditText_bR2);
        TextInputEditText_docD = findViewById(R.id.TextInputEditText_docD);

        TextInputLayout_bLoc = findViewById(R.id.TextInputLayout_bLoc);
        TextInputLayout_bUAA = findViewById(R.id.TextInputLayout_bUAA);
        TextInputLayout_lLoc = findViewById(R.id.TextInputLayout_lLoc);
        TextInputLayout_lAA = findViewById(R.id.TextInputLayout_lAA);
        TextInputLayout_prin = findViewById(R.id.TextInputLayout_prin);
        TextInputLayout_wON = findViewById(R.id.TextInputLayout_wON);
        TextInputLayout_sM1 = findViewById(R.id.TextInputLayout_sM1);
        TextInputLayout_sM2 = findViewById(R.id.TextInputLayout_sM2);
        TextInputLayout_sM3 = findViewById(R.id.TextInputLayout_sM3);
        TextInputLayout_gapN = findViewById(R.id.TextInputLayout_gapN);
        TextInputLayout_gapAddr = findViewById(R.id.TextInputLayout_gapAddr);
        TextInputLayout_gapRRN = findViewById(R.id.TextInputLayout_gapRRN);
        TextInputLayout_gapPN = findViewById(R.id.TextInputLayout_gapPN);
        TextInputLayout_eulN = findViewById(R.id.TextInputLayout_eulN);
        TextInputLayout_eulAddr = findViewById(R.id.TextInputLayout_eulAddr);
        TextInputLayout_eulRRN = findViewById(R.id.TextInputLayout_eulRRN);
        TextInputLayout_eulPN = findViewById(R.id.TextInputLayout_eulPN);
        TextInputLayout_bR1 = findViewById(R.id.TextInputLayout_bR1);
        TextInputLayout_bR2 = findViewById(R.id.TextInputLayout_bR2);
        TextInputLayout_docD = findViewById(R.id.TextInputLayout_docD);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_contract_property_inheritance;
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
                        gapN = value.getString("name");
                        gapAddr = value.getString("addr");
                        gapRRN = value.getString("rrn");
                        gapPN = value.getString("phoneNum");
                        //불러온 모든 텍스트들을 TextInputEditText에 넣는다.
                        setAllTextInTextInputEditText();

                    }
                }
            });
        }

        bLoc = PreferenceManager.getString(mContext,"bLoc");
        bUAA = PreferenceManager.getString(mContext,"bUAA");
        lLoc = PreferenceManager.getString(mContext,"lLoc");
        lAA = PreferenceManager.getString(mContext,"lAA");
        prin = PreferenceManager.getString(mContext,"prin");
        wON = PreferenceManager.getString(mContext,"wON");
        sM1 = PreferenceManager.getString(mContext,"sM1");
        sM2 = PreferenceManager.getString(mContext,"sM2");
        sM3 = PreferenceManager.getString(mContext,"sM3");

        eulN = PreferenceManager.getString(mContext,"eulN");
        eulAddr = PreferenceManager.getString(mContext,"eulAddr");
        eulRRN = PreferenceManager.getString(mContext,"eulRRN");
        eulPN = PreferenceManager.getString(mContext,"eulPN");
        bR1 = PreferenceManager.getString(mContext,"bR1");
        bR2 = PreferenceManager.getString(mContext,"bR2");
        docD = PreferenceManager.getString(mContext,"docD");
    }

    @Override
    protected void setAllTextInTextInputEditText() {
        TextInputEditText_bLoc.setText(bLoc);
        TextInputEditText_bUAA.setText(bUAA);
        TextInputEditText_lLoc.setText(lLoc);
        TextInputEditText_lAA.setText(lAA);
        TextInputEditText_prin.setText(prin);
        TextInputEditText_wON.setText(wON);
        TextInputEditText_sM1.setText(sM1);
        TextInputEditText_sM2.setText(sM2);
        TextInputEditText_sM3.setText(sM3);
        TextInputEditText_gapN.setText(gapN);
        TextInputEditText_gapAddr.setText(gapAddr);
        TextInputEditText_gapRRN.setText(gapRRN);
        TextInputEditText_gapPN.setText(gapPN);
        TextInputEditText_eulN.setText(eulN);
        TextInputEditText_eulAddr.setText(eulAddr);
        TextInputEditText_eulRRN.setText(eulRRN);
        TextInputEditText_eulPN.setText(eulPN);
        TextInputEditText_bR1.setText(bR1);
        TextInputEditText_bR2.setText(bR2);
        TextInputEditText_docD.setText(docD);
    }

    @Override
    protected HashMap<String, String> getAllTextFromTextInputEditText() {
        bLoc = TextInputEditText_bLoc.getText().toString().trim();
        bUAA = TextInputEditText_bUAA.getText().toString().trim();
        lLoc = TextInputEditText_lLoc.getText().toString().trim();
        lAA = TextInputEditText_lAA.getText().toString().trim();
        prin = TextInputEditText_prin.getText().toString().trim();
        wON = TextInputEditText_wON.getText().toString().trim();
        sM1 = TextInputEditText_sM1.getText().toString().trim();
        sM2 = TextInputEditText_sM2.getText().toString().trim();
        sM3 = TextInputEditText_sM3.getText().toString().trim();
        gapN = TextInputEditText_gapN.getText().toString().trim();
        gapAddr = TextInputEditText_gapAddr.getText().toString().trim();
        gapRRN = TextInputEditText_gapRRN.getText().toString().trim();
        gapPN = TextInputEditText_gapPN.getText().toString().trim();
        eulN = TextInputEditText_eulN.getText().toString().trim();
        eulAddr = TextInputEditText_eulAddr.getText().toString().trim();
        eulRRN = TextInputEditText_eulRRN.getText().toString().trim();
        eulPN = TextInputEditText_eulPN.getText().toString().trim();
        bR1 = TextInputEditText_bR1.getText().toString().trim();
        bR2 = TextInputEditText_bR2.getText().toString().trim();
        docD = TextInputEditText_docD.getText().toString().trim();

        HashMap<String, String> attrs = new HashMap<>();
        attrs.put("bLoc", bLoc);
        attrs.put("bUAA", bUAA);
        attrs.put("lLoc", lLoc);
        attrs.put("lAA", lAA);
        attrs.put("prin", prin);
        attrs.put("wON", wON);
        attrs.put("sM1", sM1);
        attrs.put("sM2", sM2);
        attrs.put("sM3", sM3);
        attrs.put("gapN", gapN);
        attrs.put("gapAddr", gapAddr);
        attrs.put("gapRRN", gapRRN);
        attrs.put("gapPN", gapPN);
        attrs.put("eulN", eulN);
        attrs.put("eulAddr", eulAddr);
        attrs.put("eulRRN", eulRRN);
        attrs.put("eulPN", eulPN);
        attrs.put("bR1", bR1);
        attrs.put("bR2", bR2);
        attrs.put("docD", docD);
        return attrs;
    }

    @Override
    protected void setAllPreferences() {
        PreferenceManager.setString(mContext,"bLoc",TextInputEditText_bLoc.getText().toString().trim());
        PreferenceManager.setString(mContext,"bUAA",TextInputEditText_bUAA.getText().toString().trim());
        PreferenceManager.setString(mContext,"lLoc",TextInputEditText_lLoc.getText().toString().trim());
        PreferenceManager.setString(mContext,"lAA",TextInputEditText_lAA.getText().toString().trim());
        PreferenceManager.setString(mContext,"prin",TextInputEditText_prin.getText().toString().trim());
        PreferenceManager.setString(mContext,"wON",TextInputEditText_wON.getText().toString().trim());
        PreferenceManager.setString(mContext,"sM1",TextInputEditText_sM1.getText().toString().trim());
        PreferenceManager.setString(mContext,"sM2",TextInputEditText_sM2.getText().toString().trim());
        PreferenceManager.setString(mContext,"sM3",TextInputEditText_sM3.getText().toString().trim());
        PreferenceManager.setString(mContext,"eulN",TextInputEditText_eulN.getText().toString().trim());
        PreferenceManager.setString(mContext,"eulAddr",TextInputEditText_eulAddr.getText().toString().trim());
        PreferenceManager.setString(mContext,"eulRRN",TextInputEditText_eulRRN.getText().toString().trim());
        PreferenceManager.setString(mContext,"eulPN",TextInputEditText_eulPN.getText().toString().trim());
        PreferenceManager.setString(mContext,"bR1",TextInputEditText_bR1.getText().toString().trim());
        PreferenceManager.setString(mContext,"bR2",TextInputEditText_bR2.getText().toString().trim());
        PreferenceManager.setString(mContext,"docD",TextInputEditText_bR2.getText().toString().trim());
    }
}
