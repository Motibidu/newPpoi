package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.dstadler.poiandroidtest.newpoi.R;

import java.util.HashMap;

public class ResumeStandardActivity extends InnerClass{
    //String
    //프로필
    private String name, email, phoneNum, addr, engName, chName, rrn, age, num;

    //학력사항
    private String
            hN, hEnt, hGrad , hIfy,
            uN , uMaj, uEnt , uGrad, uLoc, uSco, uIfy,
            mN, mEnt, mLoc, mSco, mGrad, mIfy, mMaj;

    //경력사항
    private String
            corpN1, dep1, corpEnt1, corpRes1, work1
            ,corpN2, dep2, corpEnt2, corpRes2, work2
            ,corpN3, dep3, corpEnt3, corpRes3, work3;
    //TextinputEditText
    //프로필
    private TextInputEditText
            TextInputEditText_name, TextInputEditText_email, TextInputEditText_phoneNum,
            TextInputEditText_addr, TextInputEditText_engName, TextInputEditText_chName,
            TextInputEditText_rrn, TextInputEditText_age, TextInputEditText_num;

    //학력사항
    private TextInputEditText
            TextInputEditText_hN, TextInputEditText_hEnt, TextInputEditText_hGrad , TextInputEditText_hIfy,
            TextInputEditText_uN, TextInputEditText_uMaj, TextInputEditText_uEnt , TextInputEditText_uGrad, TextInputEditText_uLoc, TextInputEditText_uSco, TextInputEditText_uIfy,
            TextInputEditText_mN, TextInputEditText_mEnt, TextInputEditText_mLoc, TextInputEditText_mSco, TextInputEditText_mGrad, TextInputEditText_mIfy, TextInputEditText_mMaj;

    //경력사항
    private TextInputEditText
            TextInputEditText_corpN1, TextInputEditText_dep1, TextInputEditText_corpEnt1, TextInputEditText_corpRes1, TextInputEditText_work1
            ,TextInputEditText_corpN2, TextInputEditText_dep2, TextInputEditText_corpEnt2, TextInputEditText_corpRes2, TextInputEditText_work2
            ,TextInputEditText_corpN3, TextInputEditText_dep3, TextInputEditText_corpEnt3, TextInputEditText_corpRes3, TextInputEditText_work3;
    private LinearLayout
            LinearLayout_highschool, LinearLayout_university, LinearLayout_master,
            LinearLayout_corp1, LinearLayout_corp2, LinearLayout_corp3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout_highschool  = findViewById(R.id.LinearLayout_highschool);
        LinearLayout_university = findViewById(R.id.LinearLayout_university);
        LinearLayout_master = findViewById(R.id.LinearLayout_master);
        LinearLayout_corp1 = findViewById(R.id.LinearLayout_corp1);
        LinearLayout_corp2 = findViewById(R.id.LinearLayout_corp2);
        LinearLayout_corp3 = findViewById(R.id.LinearLayout_corp3);

        //프로필
        TextInputEditText_name = findViewById(R.id.TextInputEditText_name);                   //이름
        TextInputEditText_engName= findViewById(R.id.TextInputEditText_engName);              //영어이름
        TextInputEditText_chName= findViewById(R.id.TextInputEditText_chName);
        TextInputEditText_rrn= findViewById(R.id.TextInputEditText_rrn);
        TextInputEditText_age= findViewById(R.id.TextInputEditText_age);
        TextInputEditText_phoneNum = findViewById(R.id.TextInputEditText_phoneNum);     //휴대폰
        TextInputEditText_num= findViewById(R.id.TextInputEditText_num);
        TextInputEditText_email = findViewById(R.id.TextInputEditText_email);                 //이메일
        TextInputEditText_addr = findViewById(R.id.TextInputEditText_addr);             //주소

        //고등학교
        TextInputEditText_hN = findViewById(R.id.TextInputEditText_hN);
        TextInputEditText_hEnt = findViewById(R.id.TextInputEditText_hEnt);
        TextInputEditText_hGrad = findViewById(R.id.TextInputEditText_hGrad);
        TextInputEditText_hIfy = findViewById(R.id.TextInputEditText_hIfy);

        //대학교
        TextInputEditText_uN = findViewById(R.id.TextInputEditText_uN);
        TextInputEditText_uEnt = findViewById(R.id.TextInputEditText_uEnt);
        TextInputEditText_uGrad = findViewById(R.id.TextInputEditText_uGrad);
        TextInputEditText_uMaj = findViewById(R.id.TextInputEditText_uMaj);
        TextInputEditText_uLoc = findViewById(R.id.TextInputEditText_uLoc);
        TextInputEditText_uSco = findViewById(R.id.TextInputEditText_uSco);
        TextInputEditText_uIfy = findViewById(R.id.TextInputEditText_uIfy);

        //대학원
        TextInputEditText_mN = findViewById(R.id.TextInputEditText_mN);
        TextInputEditText_mEnt = findViewById(R.id.TextInputEditText_mEnt);
        TextInputEditText_mGrad = findViewById(R.id.TextInputEditText_mGrad);
        TextInputEditText_mMaj = findViewById(R.id.TextInputEditText_mMaj);
        TextInputEditText_mLoc = findViewById(R.id.TextInputEditText_mLoc);
        TextInputEditText_mSco = findViewById(R.id.TextInputEditText_mSco);
        TextInputEditText_mIfy = findViewById(R.id.TextInputEditText_mIfy);

        //경력사항1
        TextInputEditText_corpN1 = findViewById(R.id.TextInputEditText_corpN1);
        TextInputEditText_dep1 = findViewById(R.id.TextInputEditText_dep1);
        TextInputEditText_corpEnt1 = findViewById(R.id.TextInputEditText_corpEnt1);
        TextInputEditText_corpRes1 = findViewById(R.id.TextInputEditText_corpRes1);
        TextInputEditText_work1 = findViewById(R.id.TextInputEditText_work1);

        //경력사항 2
        TextInputEditText_corpN2 = findViewById(R.id.TextInputEditText_corpN2);
        TextInputEditText_dep2 = findViewById(R.id.TextInputEditText_dep2);
        TextInputEditText_corpEnt2 = findViewById(R.id.TextInputEditText_corpEnt2);
        TextInputEditText_corpRes2 = findViewById(R.id.TextInputEditText_corpRes2);
        TextInputEditText_work2 = findViewById(R.id.TextInputEditText_work2);

        //경력사항 3
        TextInputEditText_corpN3 = findViewById(R.id.TextInputEditText_corpN3);
        TextInputEditText_dep3 = findViewById(R.id.TextInputEditText_dep3);
        TextInputEditText_corpEnt3 = findViewById(R.id.TextInputEditText_corpEnt3);
        TextInputEditText_corpRes3 = findViewById(R.id.TextInputEditText_corpRes3);
        TextInputEditText_work3 = findViewById(R.id.TextInputEditText_work3);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_resume_standard;
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

            //FirebaseFirestore의 collection("users").document(userID)에서 이름, 이메일, 휴대전화, 주소를 가져오고
            //EditText에 적용한다.
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
            documentReference.addSnapshotListener(mActivity, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        name = value.getString("name");
                        engName = value.getString("engName");
                        chName = value.getString("chName");
                        rrn = value.getString("rrn");
                        age = value.getString("age");
                        phoneNum = value.getString("phoneNum");
                        num = value.getString("num");
                        email = value.getString("email");
                        addr = value.getString("addr");
                        //불러온 모든 텍스트들을 TextInputEditText에 넣는다.
                        setAllTextInTextInputEditText();

                    }
                }
            });

            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("eduBack");
            documentReference.addSnapshotListener(mActivity, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {

                        hN = value.getString("hN");
                        hEnt = value.getString("hEnt");
                        hGrad = value.getString("hGrad");
                        hIfy = value.getString("hIfy");

                        uN = value.getString("uN");
                        uEnt = value.getString("uEnt");
                        uGrad = value.getString("uGrad");
                        uLoc = value.getString("uLoc");
                        uSco = value.getString("uSco");
                        uMaj = value.getString("uMaj");
                        uIfy = value.getString("uIfy");
                        if (!bExpanded && checkString(uN) && checkString(uEnt) && checkString(uGrad) && checkString(uLoc) && checkString(uSco) && checkString(uMaj) && checkString(uIfy)) {
                            LinearLayout_university.setVisibility(View.GONE);
                        }

                        mN = value.getString("mN");
                        mEnt = value.getString("mEnt");
                        mGrad = value.getString("mGrad");
                        mLoc = value.getString("mLoc");
                        mSco = value.getString("mSco");
                        mMaj = value.getString("mMaj");
                        mIfy = value.getString("mIfy");
                        if (!bExpanded && !bExpanded && checkString(mN) && checkString(mEnt) && checkString(mGrad) && checkString(mLoc) && checkString(mSco) && checkString(mMaj) && checkString(mIfy)) {
                            LinearLayout_master.setVisibility(View.GONE);
                        }
                        //불러온 모든 텍스트들을 TextInputEditText에 넣는다.
                        setAllTextInTextInputEditText();

                    } else {
                        Toast.makeText(getApplicationContext(), "학력사항을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("formOfCareer");
            documentReference.addSnapshotListener(mActivity, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        corpN1 = value.getString("corpN1");
                        corpEnt1 = value.getString("corpEnt1");
                        corpRes1 = value.getString("corpRes1");
                        dep1 = value.getString("dep1");
                        work1 = value.getString("work1");

                        corpN2 = value.getString("corpN2");
                        corpEnt2 = value.getString("corpEnt2");
                        corpRes2 = value.getString("corpRes2");
                        dep2 = value.getString("dep2");
                        work2 = value.getString("work2");
                        if (!bExpanded && checkString(corpN2) && checkString(dep2) && checkString(corpEnt2) && checkString(corpRes2) &&
                                checkString(work2)) {
                            LinearLayout_corp2.setVisibility(View.GONE);
                        }

                        corpN3 = value.getString("corpN3");
                        corpEnt3 = value.getString("corpEnt3");
                        corpRes3 = value.getString("corpRes3");
                        dep3 = value.getString("dep3");
                        work3 = value.getString("work3");
                        if (!bExpanded && checkString(corpN3) && checkString(dep3) && checkString(corpEnt3) && checkString(corpRes3) &&
                                checkString(work3)) {
                            LinearLayout_corp3.setVisibility(View.GONE);
                        }
                        //불러온 모든 텍스트들을 TextInputEditText에 넣는다.
                        setAllTextInTextInputEditText();
                    } else {
                        Toast.makeText(getApplicationContext(), "학력사항을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void setAllTextInTextInputEditText() {
            TextInputEditText_name.setText(name);
            TextInputEditText_engName.setText(engName);
            TextInputEditText_chName.setText(chName);
            TextInputEditText_rrn.setText(rrn);
            TextInputEditText_age.setText(age);
            TextInputEditText_phoneNum.setText(phoneNum);
            TextInputEditText_num.setText(num);
            TextInputEditText_email.setText(email);
            TextInputEditText_addr.setText(addr);

            TextInputEditText_hN.setText(hN);
            TextInputEditText_hEnt.setText(hEnt);
            TextInputEditText_hGrad.setText(hGrad);
            TextInputEditText_hIfy.setText(hIfy);

            TextInputEditText_uN.setText(uN);
            TextInputEditText_uEnt.setText(uEnt);
            TextInputEditText_uGrad.setText(uGrad);
            TextInputEditText_uLoc.setText(uLoc);
            TextInputEditText_uSco.setText(uSco);
            TextInputEditText_uMaj.setText(uMaj);
            TextInputEditText_uIfy.setText(uIfy);

            TextInputEditText_mN.setText(mN);
            TextInputEditText_mEnt.setText(mEnt);
            TextInputEditText_mGrad.setText(mGrad);
            TextInputEditText_mLoc.setText(mLoc);
            TextInputEditText_mSco.setText(mSco);
            TextInputEditText_mMaj.setText(mMaj);
            TextInputEditText_mIfy.setText(mIfy);

            TextInputEditText_corpN1.setText(corpN1);
            TextInputEditText_corpEnt1.setText(corpEnt1);
            TextInputEditText_corpRes1.setText(corpRes1);
            TextInputEditText_dep1.setText(dep1);
            TextInputEditText_work1.setText(work1);

            TextInputEditText_corpN2.setText(corpN2);
            TextInputEditText_corpEnt2.setText(corpEnt2);
            TextInputEditText_corpRes2.setText(corpRes2);
            TextInputEditText_dep2.setText(dep2);
            TextInputEditText_work2.setText(work2);

            TextInputEditText_corpN3.setText(corpN3);
            TextInputEditText_corpEnt3.setText(corpEnt3);
            TextInputEditText_corpRes3.setText(corpRes3);
            TextInputEditText_dep3.setText(dep3);
            TextInputEditText_work3.setText(work3);
    }

    @Override
    protected HashMap<String, String> getAllTextFromTextInputEditText() {

        HashMap<String, String> attrs = new HashMap<>();

        name = TextInputEditText_name.getText().toString().trim();
        engName = TextInputEditText_engName.getText().toString().trim();
        chName = TextInputEditText_chName.getText().toString().trim();
        rrn = TextInputEditText_rrn.getText().toString().trim();
        age = TextInputEditText_age.getText().toString().trim();
        num = TextInputEditText_num.getText().toString().trim();
        email = TextInputEditText_email.getText().toString().trim();
        phoneNum = TextInputEditText_phoneNum.getText().toString().trim();
        addr = TextInputEditText_addr.getText().toString().trim();

        hN = TextInputEditText_hN.getText().toString().trim();
        hEnt = TextInputEditText_hEnt.getText().toString().trim();
        hGrad = TextInputEditText_hGrad.getText().toString().trim();
        hIfy = TextInputEditText_hIfy.getText().toString().trim();

        uN = TextInputEditText_uN.getText().toString().trim();
        uMaj = TextInputEditText_uMaj.getText().toString().trim();
        uEnt = TextInputEditText_uEnt.getText().toString().trim();
        uGrad = TextInputEditText_uGrad.getText().toString().trim();
        uLoc = TextInputEditText_uLoc.getText().toString().trim();
        uSco = TextInputEditText_uGrad.getText().toString().trim();
        uIfy = TextInputEditText_uLoc.getText().toString().trim();


        mN = TextInputEditText_mN.getText().toString().trim();
        mEnt = TextInputEditText_mEnt.getText().toString().trim();
        mLoc = TextInputEditText_mLoc.getText().toString().trim();
        mSco = TextInputEditText_mSco.getText().toString().trim();
        mGrad = TextInputEditText_mGrad.getText().toString().trim();
        mIfy = TextInputEditText_mIfy.getText().toString().trim();
        mMaj = TextInputEditText_mMaj.getText().toString().trim();

        corpN1 = TextInputEditText_corpN1.getText().toString().trim();
        dep1 = TextInputEditText_dep1.getText().toString().trim();
        corpEnt1 = TextInputEditText_corpEnt1.getText().toString().trim();
        corpRes1 = TextInputEditText_corpRes1.getText().toString().trim();
        work1 = TextInputEditText_work1.getText().toString().trim();

        corpN2 = TextInputEditText_corpN2.getText().toString().trim();
        dep2 = TextInputEditText_dep2.getText().toString().trim();
        corpEnt2 = TextInputEditText_corpEnt2.getText().toString().trim();
        corpRes2 = TextInputEditText_corpRes2.getText().toString().trim();
        work2 = TextInputEditText_work2.getText().toString().trim();

        corpN3 = TextInputEditText_corpN3.getText().toString().trim();
        dep3 = TextInputEditText_dep3.getText().toString().trim();
        corpEnt3 = TextInputEditText_corpEnt3.getText().toString().trim();
        corpRes3 = TextInputEditText_corpRes3.getText().toString().trim();
        work3 = TextInputEditText_work3.getText().toString().trim();

        attrs.put("name", name);
        attrs.put("engName", engName);
        attrs.put("chName", chName);
        attrs.put("rrn", rrn);
        attrs.put("age", age);
        attrs.put("num", num);
        attrs.put("email", email);
        attrs.put("phoneNum", phoneNum);
        attrs.put("addr", addr);
        attrs.put("hN", hN);
        attrs.put("hEnt", hEnt);
        attrs.put("hGrad", hGrad);
        attrs.put("hIfy", hIfy);
        attrs.put("uN", uN);
        attrs.put("uMaj", uMaj);
        attrs.put("uEnt", uEnt);
        attrs.put("uGrad", uGrad);
        attrs.put("uLoc", uLoc);
        attrs.put("uSco", uSco);
        attrs.put("uIfy", uIfy);
        attrs.put("mN", mN);
        attrs.put("mEnt", mEnt);
        attrs.put("mLoc", mLoc);
        attrs.put("mSco", mSco);
        attrs.put("mGrad", mGrad);
        attrs.put("mIfy", mIfy);
        attrs.put("mMaj", mMaj);
        attrs.put("corpN1", corpN1);
        attrs.put("dep1", dep1);
        attrs.put("corpEnt1", corpEnt1);
        attrs.put("corpRes1", corpRes1);
        attrs.put("work1", work1);
        attrs.put("corpN2", corpN2);
        attrs.put("dep2", dep2);
        attrs.put("corpEnt2", corpEnt2);
        attrs.put("corpRes2", corpRes2);
        attrs.put("work2", work2);
        attrs.put("corpN3", corpN3);
        attrs.put("dep3", dep3);
        attrs.put("corpEnt3", corpEnt3);
        attrs.put("corpRes3", corpRes3);
        attrs.put("work3", work3);


        return attrs;
    }

    @Override
    protected void setAllPreferences() {

    }
}
