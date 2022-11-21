package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.GoogleManager;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;

import java.util.HashMap;

public class ContractFreelanceEmploymentActivity extends InnerClass{


    //0
    //사용자 상세정보
    //사용자 이름, 사용자 주소, 사용자 연락처, 사용자 사업체명
    //근무자 상세정보
    //근무자 이름, 근무자 주소, 근무자 연락처, 근무자 주민번호
    //계약 상세정보
    //계약건명, 업무이름, 계약기간 시작, 계약기간 종료, 게약일자, 납품 날짜, 납품 횟수
    //임금 상세정보
    //총 계약금액, 착수금액, 잔금금액
    //손해배상 상세정보
    //손해배상 방법, 분쟁관할법원

    //사용자 상세정보, 근무자 상세정보, 계약 상세정보, 임금 상세정보
    //['User details', 'worker details', 'Contract detail', 'pay detail']
    //['gapD', 'eulD', 'cD', 'pD']

//    사용자 이름, 사용자 주소, 사용자 연락처, 사용자 사업체명,
//    근무자 이름, 근무자 주소, 근무자 연락처, 근무자 주민번호,
//    계약건명, 업무이름, 계약기간 시작, 계약기간 종료, 계약일자, 납품 날짜, 납품 횟수,
//     총 계약금액, 착수금액, 잔금금액, 손해배상 방법, 분쟁관할법원

    //['userName', 'User address', 'User phoneNumber', 'User business name',
    // 'Worker name', 'Worker address', 'Worker phoneNum', 'Worker RRN',
    // 'Contract title', 'Business name', 'Start contract period', 'Termination contract Period', 'Contract date', 'Delivery day', 'Number delivery',
    // 'Total contract amount', 'Starting amount', 'Balance amount',
    // 'How to compensate', 'court to dispute']

//    ['gapN', 'gapL', 'gapPN', 'gapBN',
//     'eulRRN', 'eulA', 'eulPN', 'eulRRN',
//     'cT', 'bN', 'sCP', 'tCP', 'cD', 'dD', 'nD',
//     'tCA', 'sA', 'bA',
//     'hTC', 'cTD']

    private String
            gapN, gapL, gapPN, gapBN,
            eulRRN, eulA, eulPN,
            cT, bN, sCP, tCP, cD, dD, nD,
            tCA, sA, bA, hTC, cTD;

    private TextInputEditText
            TextInputEditText_gapN, TextInputEditText_gapL, TextInputEditText_gapPN, TextInputEditText_gapBN,
            TextInputEditText_eulN, TextInputEditText_eulA, TextInputEditText_eulPN, TextInputEditText_eulRRN,
            TextInputEditText_cT, TextInputEditText_bN, TextInputEditText_sCP, TextInputEditText_tCP, TextInputEditText_cD, TextInputEditText_dD, TextInputEditText_nD,
            TextInputEditText_tCA, TextInputEditText_sA, TextInputEditText_bA, TextInputEditText_hTC, TextInputEditText_cTD;
    private TextInputLayout
            TextInputLayout_gapN, TextInputLayout_gapL, TextInputLayout_gapPN, TextInputLayout_gapBN,
            TextInputLayout_eulRRN, TextInputLayout_eulA, TextInputLayout_eulPN,
            TextInputLayout_cT, TextInputLayout_bN, TextInputLayout_sCP, TextInputLayout_tCP, TextInputLayout_cD, TextInputLayout_dD, TextInputLayout_nD,
            TextInputLayout_tCA, TextInputLayout_sA, TextInputLayout_bA, TextInputLayout_hTC, TextInputLayout_cTD;

    private LinearLayout
            LinearLayout_gapD, LinearLayout_eulD, LinearLayout_contractD, LinearLayout_payD, LinearLayout_compensationDamagesD;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextInputEditText_gapN = findViewById(R.id.TextInputEditText_gapN);TextInputEditText_eulA = findViewById(R.id.TextInputEditText_eulA);
        TextInputEditText_gapL = findViewById(R.id.TextInputEditText_gapL);
        TextInputEditText_gapPN = findViewById(R.id.TextInputEditText_gapPN);
        TextInputEditText_gapBN = findViewById(R.id.TextInputEditText_gapBN);

        TextInputEditText_eulN = findViewById(R.id.TextInputEditText_eulN);
        TextInputEditText_eulA = findViewById(R.id.TextInputEditText_eulA);
        TextInputEditText_eulPN = findViewById(R.id.TextInputEditText_eulPN);
        TextInputEditText_eulRRN = findViewById(R.id.TextInputEditText_eulRRN);
        TextInputEditText_cT = findViewById(R.id.TextInputEditText_cT);
        TextInputEditText_sCP = findViewById(R.id.TextInputEditText_sCP);
        TextInputEditText_tCP = findViewById(R.id.TextInputEditText_tCP);
        TextInputEditText_cD = findViewById(R.id.TextInputEditText_cD);
        TextInputEditText_dD = findViewById(R.id.TextInputEditText_dD);
        TextInputEditText_nD = findViewById(R.id.TextInputEditText_nD);
        TextInputEditText_tCA = findViewById(R.id.TextInputEditText_tCA);
        TextInputEditText_sA = findViewById(R.id.TextInputEditText_sA);
        TextInputEditText_bA = findViewById(R.id.TextInputEditText_bA);
        TextInputEditText_hTC = findViewById(R.id.TextInputEditText_hTC);
        TextInputEditText_cTD = findViewById(R.id.TextInputEditText_cTD);

        TextInputLayout_gapN = findViewById(R.id.TextInputLayout_gapN);
        TextInputLayout_gapL = findViewById(R.id.TextInputLayout_gapL);
        TextInputLayout_gapPN = findViewById(R.id.TextInputLayout_gapPN);
        TextInputLayout_gapBN = findViewById(R.id.TextInputLayout_gapBN);
        TextInputLayout_eulRRN = findViewById(R.id.TextInputLayout_eulRRN);
        TextInputLayout_eulA = findViewById(R.id.TextInputLayout_eulA);
        TextInputLayout_eulPN = findViewById(R.id.TextInputLayout_eulPN);
        TextInputLayout_eulRRN = findViewById(R.id.TextInputLayout_eulRRN);
        TextInputLayout_cT = findViewById(R.id.TextInputLayout_cT);
        TextInputLayout_sCP = findViewById(R.id.TextInputLayout_sCP);
        TextInputLayout_tCP = findViewById(R.id.TextInputLayout_tCP);
        TextInputLayout_cD = findViewById(R.id.TextInputLayout_cD);
        TextInputLayout_dD = findViewById(R.id.TextInputLayout_dD);
        TextInputLayout_nD = findViewById(R.id.TextInputLayout_nD);
        TextInputLayout_tCA = findViewById(R.id.TextInputLayout_tCA);
        TextInputLayout_sA = findViewById(R.id.TextInputLayout_sA);
        TextInputLayout_bA = findViewById(R.id.TextInputLayout_bA);
        TextInputLayout_hTC = findViewById(R.id.TextInputLayout_hTC);
        TextInputLayout_cTD = findViewById(R.id.TextInputLayout_cTD);

        LinearLayout_gapD = findViewById(R.id.LinearLayout_gapD);
        LinearLayout_eulD = findViewById(R.id.LinearLayout_eulD);
        LinearLayout_contractD = findViewById(R.id.LinearLayout_contractD);
        LinearLayout_payD= findViewById(R.id.LinearLayout_payD);
        LinearLayout_compensationDamagesD = findViewById(R.id.LinearLayout_compensationDamagesD);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_contract_freelance_employment;
    }

    @Override
    protected Intent getIntentFromEachActivity() {
        return getIntent();
    }

    @Override
    protected void updateUI() {
        GoogleManager googleManager = new GoogleManager(mContext);

        //<10> firebase setText, PreferenceManager.getString
        if (googleManager.isSignedIn()) {
            mAuth = FirebaseAuth.getInstance();
            userID = mAuth.getCurrentUser().getUid();
            //FirebaseFirestore의 collection("users").document(userID)에서 이름, 이메일, 휴대전화, 주소를 가져오고
            //EditText에 적용한다.
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
            documentReference.addSnapshotListener(mActivity, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        TextInputEditText_gapN.setText(value.getString("name"));
                        TextInputEditText_gapL.setText(value.getString("addr"));
                        TextInputEditText_gapPN.setText(value.getString("phoneNum"));
                    }
                }
            });

        }

        gapBN = PreferenceManager.getString(mContext,"bN");
        eulRRN = PreferenceManager.getString(mContext,"eulRRN");
        eulA = PreferenceManager.getString(mContext,"eulA");
        eulPN = PreferenceManager.getString(mContext,"eulPN");
        eulRRN = PreferenceManager.getString(mContext,"eulRRN");
        cT = PreferenceManager.getString(mContext,"cT");
        sCP = PreferenceManager.getString(mContext,"sCP");
        tCP = PreferenceManager.getString(mContext,"tCP");
        cD = PreferenceManager.getString(mContext,"cD");
        dD = PreferenceManager.getString(mContext,"dD");
        nD = PreferenceManager.getString(mContext,"nD");
        tCA = PreferenceManager.getString(mContext,"tCA");
        sA = PreferenceManager.getString(mContext,"sA");
        bA = PreferenceManager.getString(mContext,"bA");
        hTC = PreferenceManager.getString(mContext,"hTC");
        cTD = PreferenceManager.getString(mContext,"cTD");
    }

    @Override
    protected void setAllTextInTextInputEditText() {
        TextInputEditText_gapN.setText(gapN);
        TextInputEditText_gapL.setText(gapL);
        TextInputEditText_gapPN.setText(gapPN);
        TextInputEditText_gapBN.setText(gapBN);
        TextInputEditText_eulRRN.setText(eulRRN);
        TextInputEditText_eulA.setText(eulA);
        TextInputEditText_eulPN.setText(eulPN);
        TextInputEditText_eulRRN.setText(eulRRN);
        TextInputEditText_cT.setText(cT);
        TextInputEditText_sCP.setText(sCP);
        TextInputEditText_tCP.setText(tCP);
        TextInputEditText_cD.setText(cD);
        TextInputEditText_dD.setText(dD);
        TextInputEditText_nD.setText(nD);
        TextInputEditText_tCA.setText(tCA);
        TextInputEditText_sA.setText(sA);
        TextInputEditText_bA.setText(bA);
        TextInputEditText_hTC.setText(hTC);
        TextInputEditText_cTD.setText(cTD);
    }

    @Override
    protected HashMap<String, String> getAllTextFromTextInputEditText() {

        gapN = TextInputEditText_gapN.getText().toString().trim();
        gapL = TextInputEditText_gapL.getText().toString().trim();
        gapPN = TextInputEditText_gapPN.getText().toString().trim();
        gapBN = TextInputEditText_gapBN.getText().toString().trim();
        eulRRN = TextInputEditText_eulRRN.getText().toString().trim();
        eulA = TextInputEditText_eulA.getText().toString().trim();
        eulPN = TextInputEditText_eulPN.getText().toString().trim();
        eulRRN = TextInputEditText_eulRRN.getText().toString().trim();
        cT = TextInputEditText_cT.getText().toString().trim();
        sCP = TextInputEditText_sCP.getText().toString().trim();
        tCP = TextInputEditText_tCP.getText().toString().trim();
        cD = TextInputEditText_cD.getText().toString().trim();
        dD = TextInputEditText_dD.getText().toString().trim();
        nD = TextInputEditText_nD.getText().toString().trim();
        tCA = TextInputEditText_tCA.getText().toString().trim();
        sA = TextInputEditText_sA.getText().toString().trim();
        bA = TextInputEditText_bA.getText().toString().trim();
        hTC = TextInputEditText_hTC.getText().toString().trim();
        cTD = TextInputEditText_cTD.getText().toString().trim();

        HashMap<String, String> attrs = new HashMap<>();
        attrs.put("gapN", gapN);
        attrs.put("gapL", gapL);
        attrs.put("gapPN", gapPN);
        attrs.put("gapBN", gapBN);
        attrs.put("eulRRN", eulRRN);
        attrs.put("eulA", eulA);
        attrs.put("eulPN", eulPN);
        attrs.put("eulRRN", eulRRN);
        attrs.put("cT", cT);
        attrs.put("sCP", sCP);
        attrs.put("tCP", tCP);
        attrs.put("cD", cD);
        attrs.put("dD", dD);
        attrs.put("nD", nD);
        attrs.put("tCA", tCA);
        attrs.put("sA", sA);
        attrs.put("bA", bA);
        attrs.put("hTC", hTC);
        attrs.put("cTD", cTD);
        return attrs;
    }

    @Override
    protected void setAllPreferences() {
        PreferenceManager.setString(mContext,"eulRRN",TextInputEditText_eulRRN.getText().toString().trim());
        PreferenceManager.setString(mContext,"eulA",TextInputEditText_eulA.getText().toString().trim());
        PreferenceManager.setString(mContext,"eulPN",TextInputEditText_eulPN.getText().toString().trim());
        PreferenceManager.setString(mContext,"cT",TextInputEditText_cT.getText().toString().trim());
        PreferenceManager.setString(mContext,"bN",TextInputEditText_bN.getText().toString().trim());
        PreferenceManager.setString(mContext,"sCP",TextInputEditText_sCP.getText().toString().trim());
        PreferenceManager.setString(mContext,"tCP",TextInputEditText_tCP.getText().toString().trim());
        PreferenceManager.setString(mContext,"cD",TextInputEditText_cD.getText().toString().trim());
        PreferenceManager.setString(mContext,"dD",TextInputEditText_dD.getText().toString().trim());
        PreferenceManager.setString(mContext,"nD",TextInputEditText_nD.getText().toString().trim());
        PreferenceManager.setString(mContext,"tCA",TextInputEditText_tCA.getText().toString().trim());
        PreferenceManager.setString(mContext,"sA",TextInputEditText_sA.getText().toString().trim());
        PreferenceManager.setString(mContext,"bA",TextInputEditText_bA.getText().toString().trim());
        PreferenceManager.setString(mContext,"hTC",TextInputEditText_hTC.getText().toString().trim());
        PreferenceManager.setString(mContext,"cTD",TextInputEditText_cTD.getText().toString().trim());
    }
}
