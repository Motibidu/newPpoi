package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.dstadler.poiandroidtest.newpoi.R;

public class AnnualSalaryActivity extends AppCompatActivity {

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
    // 연봉총액 : tS,  기본급 금액 : bS,  제수당 : vA,  연차수당 : aA,  퇴직금중간 정산액 : sABSP,  지급 날짜 : pD,  당일날짜 : tD,  계약기간 시작 : jD,  계약기간 종료 : dD,  근무 시작 시간 : wS,  근무 종료 시간 : wE, )

    //list: ['uN', 'uL', 'bN', 'bT', 'wN', 'wRRN', 'wA', 'tS', 'bS', 'vA', 'aA', 'sABSP', 'pD', 'tD', 'jD', 'dD', 'wS', 'wE']
   private String[] attrs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annual_salary);

        AnnualSalaryActivity annualSalaryActivity = new AnnualSalaryActivity();
        Intent intent = getIntent();
        InnerClass innerClass = new InnerClass(getApplicationContext(), this, intent, attrs);

    }
}
