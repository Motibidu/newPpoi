package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AnnualSalaryActivity extends InnerClass {

    //resignation0
    //사용자 상세정보
    //사용자 이름, 사업체명

    //근로자 상세정보
    //근로자 이름

    //임금 상세조건
    //연봉총액, 기본급 금액, 월 근로시간

    //날짜 및 시작,종료시간
    //당일날짜, 계약기간 시작, 계약기간 종료
    
    //파생
    //임금 상세조건 : 합계
    
    //resignation1
    //사용자 상세정보
    //사용자 이름, 사용자 소재지, 사업체명, 사업의 종류
    
    //근로자 상세정보
    //근로자 이름, 근로자 주민등록번호, 근로자 주소

    //임금 상세조건
    //연봉총액, 기본급 금액, 제수당, 연차수당, 퇴직금중간 정산액, 지급 날짜

    //날짜 및 시작, 종료시간
    //당일날짜, 계약기간 시작, 계약기간 종료, 근무 시작 시간, 근무 종료 시간

    //파생
    //익월 ?일 마감, 익월 ?일 지급

    //<3> 총 추출, 카테고리에 맞게 정렬 : 사용자 이름, 사용자 소재지, 사업체명, 사업의 종류, 근로자 이름, 근로자 주민등록번호, 근로자 주소, 연봉총액, 기본급 금액, 제수당, 연차수당, 퇴직금중간 정산액, 지급 날짜, 당일날짜, 계약기간 시작, 계약기간 종료, 근무 시작 시간, 근무 종료 시간
    //    //en_list : ['user name', 'User location', 'Business name', 'Business type', 'Worker name', "Workers' resident registration number", 'Worker address',
    //    'Total salary', 'Basic salary', 'vairous allowance', 'Annual allowance', 'Settlement amount between severance pay', 'Payment date', 'today date', 'Start contract period',
    //    'End contract period', 'work start ', 'work end']

    //<4>
    //pair:(사용자 이름 : uN,  사용자 소재지 : uL,  사업체명 : bN,  사업의 종류 : bT,  근로자 이름 : wN,  근로자 주민등록번호 : wRRN,  근로자 주소 : wA,
    // 연봉총액 : tS,  기본급 금액 : bS,  제수당 : vA,  연차수당 : aA,  퇴직금중간 정산액 : sABSP,  지급 날짜 : pD,  당일날짜 : tD,  계약기간 시작 : jD,  계약기간 종료 : dD,  근무 시작 시간 : wST,  근무 종료 시간 : wET, )

    //list: ['uN', 'uL', 'bN', 'bT', 'wN', 'wRRN', 'wA', 'tS', 'bS', 'vA', 'aA', 'sABSP', 'pD', 'tD', 'jD', 'dD', 'wST', 'wET']
    private String
            uN, uL, bN, bT, wN, wRRN, wA, tS, bS, vA, aA, sABSP, pD, tD, jD, dD, wST, wET;

    private TextInputEditText
            TextInputEditText_uN, TextInputEditText_uL, TextInputEditText_bN, TextInputEditText_bT,
            TextInputEditText_wN, TextInputEditText_wRRN, TextInputEditText_wA,
            TextInputEditText_tS, TextInputEditText_bS, TextInputEditText_vA, TextInputEditText_aA, TextInputEditText_sABSP, TextInputEditText_pD,
            TextInputEditText_tD, TextInputEditText_jD, TextInputEditText_dD, TextInputEditText_wST, TextInputEditText_wET;

    private TextInputLayout
            TextInputLayout_uN, TextInputLayout_uL, TextInputLayout_bN, TextInputLayout_bT,
            TextInputLayout_wN, TextInputLayout_wRRN, TextInputLayout_wA,
            TextInputLayout_tS, TextInputLayout_bS, TextInputLayout_vA, TextInputLayout_aA, TextInputLayout_sABSP, TextInputLayout_pD,
            TextInputLayout_tD, TextInputLayout_jD, TextInputLayout_dD, TextInputLayout_wST, TextInputLayout_wET;

    private LinearLayout
         LinearLayout_pD, LinearLayout_tD, LinearLayout_jD , LinearLayout_dD, LinearLayout_wST , LinearLayout_wET;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextInputEditText_uN = findViewById(R.id.TextInputEditText_uN);
        TextInputEditText_uL = findViewById(R.id.TextInputEditText_uL);
        TextInputEditText_bN = findViewById(R.id.TextInputEditText_bN);
        TextInputEditText_bT = findViewById(R.id.TextInputEditText_bT);
        TextInputEditText_wN = findViewById(R.id.TextInputEditText_wN);
        TextInputEditText_wRRN = findViewById(R.id.TextInputEditText_wRRN);
        TextInputEditText_wA = findViewById(R.id.TextInputEditText_wA);
        TextInputEditText_tS = findViewById(R.id.TextInputEditText_tS);
        TextInputEditText_bS = findViewById(R.id.TextInputEditText_bS);
        TextInputEditText_vA = findViewById(R.id.TextInputEditText_vA);
        TextInputEditText_aA = findViewById(R.id.TextInputEditText_aA);
        TextInputEditText_sABSP = findViewById(R.id.TextInputEditText_sABSP);
        TextInputEditText_pD = findViewById(R.id.TextInputEditText_pD);
        TextInputEditText_tD = findViewById(R.id.TextInputEditText_tD);
        TextInputEditText_jD = findViewById(R.id.TextInputEditText_jD);
        TextInputEditText_dD = findViewById(R.id.TextInputEditText_dD);
        TextInputEditText_wST = findViewById(R.id.TextInputEditText_wST);
        TextInputEditText_wET = findViewById(R.id.TextInputEditText_wET);

        TextInputLayout_uN = findViewById(R.id.TextInputLayout_uN);
        TextInputLayout_uL = findViewById(R.id.TextInputLayout_uL);
        TextInputLayout_bN = findViewById(R.id.TextInputLayout_bN);
        TextInputLayout_bT = findViewById(R.id.TextInputLayout_bT);
        TextInputLayout_wN = findViewById(R.id.TextInputLayout_wN);
        TextInputLayout_wRRN = findViewById(R.id.TextInputLayout_wRRN);
        TextInputLayout_wA = findViewById(R.id.TextInputLayout_wA);
        TextInputLayout_tS = findViewById(R.id.TextInputLayout_tS);
        TextInputLayout_bS = findViewById(R.id.TextInputLayout_bS);
        TextInputLayout_vA = findViewById(R.id.TextInputLayout_vA);
        TextInputLayout_aA = findViewById(R.id.TextInputLayout_aA);
        TextInputLayout_sABSP = findViewById(R.id.TextInputLayout_sABSP);
        TextInputLayout_pD = findViewById(R.id.TextInputLayout_pD);
        TextInputLayout_tD = findViewById(R.id.TextInputLayout_tD);
        TextInputLayout_jD = findViewById(R.id.TextInputLayout_jD);
        TextInputLayout_dD = findViewById(R.id.TextInputLayout_dD);
        TextInputLayout_wST = findViewById(R.id.TextInputLayout_wST);
        TextInputLayout_wET = findViewById(R.id.TextInputLayout_wET);

        LinearLayout_pD = findViewById(R.id.LinearLayout_pD);
        LinearLayout_tD = findViewById(R.id.LinearLayout_tD);
        LinearLayout_jD = findViewById(R.id.LinearLayout_jD);
        LinearLayout_dD = findViewById(R.id.LinearLayout_dD);
        LinearLayout_wST = findViewById(R.id.LinearLayout_wST);
        LinearLayout_wET = findViewById(R.id.LinearLayout_wET);

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        final int[] hour = new int[1];
        final int[] minute = new int[1];

        LinearLayout_pD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1+1;
                        String date = i+"년 "+i1+"월 "+i2+"일";
                        TextInputEditText_pD.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        LinearLayout_tD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1+1;
                        String date = i+"년 "+i1+"월 "+i2+"일";
                        TextInputEditText_tD.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        LinearLayout_jD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1+1;
                        String date = i+"년 "+i1+"월 "+i2+"일";
                        TextInputEditText_jD.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        LinearLayout_dD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1+1;
                        String date = i+"년 "+i1+"월 "+i2+"일";
                        TextInputEditText_dD.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        LinearLayout_wST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        mActivity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                hour[0] = i;
                                minute[0] = i1;

                                String time = i + ":" + i1;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("aa hh:mm");
                                    TextInputEditText_wST.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour[0], minute[0]);
                timePickerDialog.show();
            }
        });
        LinearLayout_wET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        mActivity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                hour[0] = i;
                                minute[0] = i1;

                                String time = i + ":" + i1;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("aa hh:mm");
                                    TextInputEditText_wET.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour[0], minute[0]);
                timePickerDialog.show();
            }
        });
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
                        TextInputEditText_uN.setText(value.getString("name"));
                        TextInputEditText_uL.setText(value.getString("addr"));
                    }
                }
            });

        }
        bN = PreferenceManager.getString(mContext,"bN");
        bT = PreferenceManager.getString(mContext,"bT");
        wN = PreferenceManager.getString(mContext,"wN");
        wRRN = PreferenceManager.getString(mContext,"wRRN");
        wA = PreferenceManager.getString(mContext,"wA");
        tS = PreferenceManager.getString(mContext,"tS");
        bS = PreferenceManager.getString(mContext,"bS");
        vA = PreferenceManager.getString(mContext,"vA");
        aA = PreferenceManager.getString(mContext,"aA");
        sABSP = PreferenceManager.getString(mContext,"sABSP");
        pD = PreferenceManager.getString(mContext,"pD");
        tD = PreferenceManager.getString(mContext,"tD");
        jD = PreferenceManager.getString(mContext,"jD");
        dD = PreferenceManager.getString(mContext,"dD");
        wST = PreferenceManager.getString(mContext,"wST");
        wET= PreferenceManager.getString(mContext,"wET");
    }

    @Override
    protected String[] getAllTextFromTextInputEditText() {

        uN = TextInputEditText_uN.getText().toString().trim();
        uL = TextInputEditText_uL.getText().toString().trim();
        bN = TextInputEditText_bN.getText().toString().trim();
        bT = TextInputEditText_bT.getText().toString().trim();
        wN = TextInputEditText_wN.getText().toString().trim();
        wRRN = TextInputEditText_wRRN.getText().toString().trim();
        wA = TextInputEditText_wA.getText().toString().trim();
        tS = TextInputEditText_tS.getText().toString().trim();
        bS = TextInputEditText_bS.getText().toString().trim();
        vA = TextInputEditText_vA.getText().toString().trim();
        aA = TextInputEditText_aA.getText().toString().trim();
        sABSP = TextInputEditText_sABSP.getText().toString().trim();
        pD = TextInputEditText_pD.getText().toString().trim();
        tD = TextInputEditText_tD.getText().toString().trim();
        jD = TextInputEditText_jD.getText().toString().trim();
        dD = TextInputEditText_dD.getText().toString().trim();
        wST = TextInputEditText_wST.getText().toString().trim();
        wET = TextInputEditText_wET.getText().toString().trim();

        String[] attrs = new String[]{"uN", "uL", "bN", "bT", "wN", "wRRN", "wA", "tS", "bS", "vA", "aA", "sABSP", "pD", "tD", "jD", "dD", "wST", "wET"};

        return attrs;
    }

    @Override
    protected void setAllTextInTextInputEditText() {
        TextInputEditText_uN.setText(uN);
        TextInputEditText_uL.setText(uL);
        TextInputEditText_bN.setText(bN);
        TextInputEditText_bT.setText(bT);
        TextInputEditText_wN.setText(wN);
        TextInputEditText_wRRN.setText(wRRN);
        TextInputEditText_wA.setText(wA);
        TextInputEditText_tS.setText(tS);
        TextInputEditText_bS.setText(bS);
        TextInputEditText_vA.setText(vA);
        TextInputEditText_aA.setText(aA);
        TextInputEditText_sABSP.setText(sABSP);
        TextInputEditText_pD.setText(pD);
        TextInputEditText_tD.setText(tD);
        TextInputEditText_jD.setText(jD);
        TextInputEditText_dD.setText(dD);
        TextInputEditText_wST.setText(wST);
        TextInputEditText_wET.setText(wET);
    }

    @Override
    protected void setAllPreferences() {
        PreferenceManager.setString(mContext,"bN",TextInputEditText_bN.getText().toString().trim());
        PreferenceManager.setString(mContext,"bT",TextInputEditText_bT.getText().toString().trim());
        PreferenceManager.setString(mContext,"wN",TextInputEditText_wN.getText().toString().trim());
        PreferenceManager.setString(mContext,"wRRN",TextInputEditText_wRRN.getText().toString().trim());
        PreferenceManager.setString(mContext,"wA",TextInputEditText_wA.getText().toString().trim());
        PreferenceManager.setString(mContext,"tS",TextInputEditText_tS.getText().toString().trim());
        PreferenceManager.setString(mContext,"bS",TextInputEditText_bS.getText().toString().trim());
        PreferenceManager.setString(mContext,"vA",TextInputEditText_vA.getText().toString().trim());
        PreferenceManager.setString(mContext,"aA",TextInputEditText_aA.getText().toString().trim());
        PreferenceManager.setString(mContext,"sABSP",TextInputEditText_sABSP.getText().toString().trim());
        PreferenceManager.setString(mContext,"pD",TextInputEditText_pD.getText().toString().trim());
        PreferenceManager.setString(mContext,"tD",TextInputEditText_tD.getText().toString().trim());
        PreferenceManager.setString(mContext,"jD",TextInputEditText_jD.getText().toString().trim());
        PreferenceManager.setString(mContext,"dD",TextInputEditText_dD.getText().toString().trim());
        PreferenceManager.setString(mContext,"wST",TextInputEditText_wST.getText().toString().trim());
        PreferenceManager.setString(mContext,"wET",TextInputEditText_wET.getText().toString().trim());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_annual_salary;
    }

    @Override
    protected Intent getIntentFromEachActivity() {
        return getIntent();
    }

}
