package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import static org.dstadler.poiandroidtest.newpoi.cls.customImageView.decodeSampledBitmapFromResource;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.customImageView;

import java.util.HashMap;
import java.util.Map;

public class DocCatActivity extends AppCompatActivity {
    public static String PACKAGE_NAME;
    private Context mContext;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter_outter recyclerViewAdapter_outter;

    //1. 디자인 이력서
    //2. 차용증
    //3. 근로계약서
    //4. 사직서
    //5. 연봉계약서

    //6. 프리랜서 고용 계약서
    //7. 표준 이력서     //activity_standard_resume_category
    //8. 매출 잔액 청구서  //activity_invoice_balance_category
    //9. 근로계약 만료 통지문    //activity_expiration_labor_contract_category
    //10. 교회 이력서    //activity_church_resume_category
    //11. 부동산 상속계약서 //activity_property_inheritance_contract_category
    //12. 거래 신청서    //activity_transaction_application
    //13. 거래 약정서    //activity_transaction_agreement_category
    //14. 결근 사유서    //activity_reason_for_absence_category
    //15. 결제 계좌 신고서 //activity_payment_account_declaration_category
    //16. 경위서       //activity_report_category
    //17. 구매 요청서    //activity_purchase_request_category
    //18. 면접결과 통지서  //activity_notification_of_interview_category
    //19. 사원 외출서    //activity_letter_of_absence_category
    //20. 사실 확인서    //activity_fact_check_category
    //21. 교통사고 합의서  //activity_traffic_accident_agreement_category
    //22. 분실 사유서    //activity_reasons_for_loss_category
    //23. 권고 사직서    //activity_resignation_on_recommendation_category
    //24. 경매신청 취하서  //activity_withdrawal_of_auction_application_category
    //25. 독촉장       //activity_reminder_category
    //26. 매도 위임장    //activity_power_of_attorney_category



    String []title = {
            "디자인 이력서", "차용증", "근로계약서",
            "사직서", "연봉 계약서", "프리랜서 고용 계약서",
            "표준 이력서", "매출 잔액 청구서", "근로계약 만료 통지문",

            "교회 이력서", "구매 요청서", "면접결과 통지서",
            "부동산 상속계약서", "거래 신청서", "거래 약정서",
            "결근 사유서", "결제 계좌 신고서", "경위서",

            "구매 요청서", "면접결과 통지서", "사원 외출서",
            "사실 확인서", "교통사고 합의서", "분실 사유서",
            "권고 사직서", "경매신청 취하서", "독촉장",

            "매도 위임장"};

    String []res = {
            "career_description0", "promissory0", "employment_contract0",
            "resignation0","annual_salary_contract0", "freelance_employment_contract0",
            "standard_resume0", "invoice_balance0", "expiration_labor_contract0",

            "church_resume0", "property_inheritance_contract0", "transaction_application0",
            "transaction_agreement0", "reason_for_absence0", "payment_account_declaration0",
            "report0","purchase_request0","notification_of_interview0",

            "fact_check0","traffic_accident_agreement0","reasons_for_loss0",
            "resignation_on_recommendation0","withdrawal_of_auction_application0","reminder0",
            "power_of_attorney0"};



    String []clsN = {
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.CareerDescriptionCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.PromissoryCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.EmploymentContractCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ResignationCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ContractAnnualSalaryCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ContractFreelanceEmploymentCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.StandardResumeActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.InvoiceBalanceActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ExpirationLaborContractActivity",

            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ChurchResumeActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.PropertyInheritanceContractActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.TransactionApplicationActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.TransactionAgreementActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ReasonForAbsenceCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.PaymentAccountDeclarationCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ReportCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.PurchaseRequestCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.NotificationOfInterviewCatActivity",

            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.LetterOfAbsenceCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.FactCheckCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.TrafficAccidentAgreementCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ReasonsForLossCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ResignationOnRecommendationCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.WithdrawalOfAuctionApplicationCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ReminderCatActivity",
            "org.dstadler.poiandroidtest.newpoi.gnrtDoc.PowerOfAttorneyCatActivity"
    };


    private Map<String, String[]> arrMap = new HashMap<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        arrMap.put("res", res);
        arrMap.put("title", title);
        arrMap.put("clsN", clsN);

        //init
        //contents
        mContext = getApplicationContext();
        PACKAGE_NAME = getApplicationContext().getPackageName();
        //widgets
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter_outter = new RecyclerViewAdapter_outter(mContext, arrMap);
        recyclerView.setAdapter(recyclerViewAdapter_outter);

        //툴바 뒤로가기 버튼
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PACKAGE_NAME = getApplicationContext().getPackageName();
    }





}

