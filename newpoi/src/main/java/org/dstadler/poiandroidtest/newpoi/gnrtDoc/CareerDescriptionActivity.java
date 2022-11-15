package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.RelativeHorizontalPosition;
import com.aspose.words.RelativeVerticalPosition;
import com.aspose.words.WrapType;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.CustomXWPFDocument;
import org.dstadler.poiandroidtest.newpoi.cls.DownloadEP;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;
import org.dstadler.poiandroidtest.newpoi.cls.RoundedCornersTransformation;
import org.dstadler.poiandroidtest.newpoi.profile.ProfileScrnActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CareerDescriptionActivity extends AppCompatActivity {


    //final vars
    private final String TAG = "CAREERDESCRIPTIONACTIVITY";
    private final String themeColor = "#34ace0";
    private final String RUNIMG_CMPLT = "org.dstadler.poiandroidtest.newpoi.RUNIMG_CMPLT";
    private final String DOC_DWNL_CMPLT = "org.dstadler.poiandroidtest.newpoi.DOC_DWNL_CMPLT";
    private static final int MY_PERMISSION_STORAGE = 1111;

    //image arguments
    public static int sCorner = 80;
    public static int sMargin = 1;
    public static int sBorder = 0;

    // vars
    private boolean bExpanded;
    private String docName;
    private String pagePath0, pagePath1, pagePath2;


    //widgets
    private ImageButton backBtn, expandedScrn_menu;
    private Button expandedScrn_download_without_modify, create, expand;


    //firebase
    private StorageReference storageReference;
    private FirebaseStorage fStorage;
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;





    private String name, email, phoneNum, addr, engName, chName, rrn, age, num;


    private TextInputEditText highschool_enterYM_EditText, highschool_graYM_EditText, highschool_name_EditText, highschool_graCls_EditText,
            university_enterYM_EditText, university_graYM_EditText, university_graCls_EditText, university_name_EditText, university_major_EditText,
            master_enterYM_EditText, master_graYM_EditText, master_graCls_EditText, master_name_EditText, master_major_EditText, master_graThe_EditText,master_LAB_EditText, TextInputEditText_uLoc, TextInputEditText_uSco, TextInputEditText_mLoc, TextInputEditText_mSco;

    private TextInputEditText name_EditText, email_EditText, phoneNum_EditText, addr_EditText, engName_EditText, chName_EditText, rrn_EditText, age_EditText, num_EditText;


    private String highschool_enterYM,  highschool_graYM, highschool_name, highschool_graCls,
            university_enterYM, university_graYM, university_graCls, university_name, university_major,
            master_enterYM, master_graYM, master_graCls, master_name, master_major, master_graThe, master_LAB,
            hN, hEnt, hGrad , hIfy, uN , uMaj, uEnt , uGrad, uLoc, uSco, uIfy , mN, mEnt, mLoc, mSco, mGrad, mIfy, mMaj;

    private TextInputEditText formOfCareer1_name_EditText, formOfCareer1_enterYM_EditText ,formOfCareer1_office_EditText  ,formOfCareer1_task_EditText ,formOfCareer1_resignYM_EditText
            ,formOfCareer2_name_EditText, formOfCareer2_enterYM_EditText , formOfCareer2_office_EditText, formOfCareer2_task_EditText, formOfCareer2_resignYM_EditText
            ,formOfCareer3_name_EditText, formOfCareer3_enterYM_EditText, formOfCareer3_office_EditText, formOfCareer3_task_EditText, formOfCareer3_resignYM_EditText;
    private String corpN1, dep1, corpEnt1, corpRes1, work1
            ,corpN2, dep2, corpEnt2, corpRes2, work2
            ,corpN3, dep3, corpEnt3, corpRes3, work3;
    private TextInputEditText license1_date_EditText, license1_cntnt_EditText, license1_grade_EditText, license1_publication_EditText,
            license2_date_EditText, license2_cntnt_EditText, license2_grade_EditText, license2_publication_EditText,
            award1_date_EditText, award1_cntnt_EditText, award1_publication_EditText,
            award2_date_EditText, award2_cntnt_EditText, award2_publication_EditText;
    private TextInputLayout TextInputLayout_name, TextInputLayout_engName, TextInputLayout_chName, TextInputLayout_rrn, TextInputLayout_age, TextInputLayout_phoneNum, TextInputLayout_num, TextInputLayout_email, TextInputLayout_addr, TextInputLayout_uLoc, TextInputLayout_uSco, TextInputLayout_mLoc, TextInputLayout_mSco;

    private String license1_date, license1_cntnt, license1_grade, license1_publication,
            license2_date, license2_cntnt, license2_grade, license2_publication,
            award1_date, award1_cntnt, award1_publication,
            award2_date, award2_cntnt, award2_publication;

    private RelativeLayout master, university, formOfCareer1, formOfCareer2, formOfCareer3, license2, award2,license1, award1;




    private Intent intent, moveProfile, documentProcess;
    private EditText expandedScrn_name;

    private DownloadEP downloadEP;
    private CustomXWPFDocument customXWPFDocument;


    private Uri imgUri1, imgUri2, imgUri3;


    private ImageView expandedScrn_mainImageView1, expandedScrn_mainImageView2, expandedScrn_mainImageView3;
    private String fileName, fileNameWithExt;




    private Map<String, String> data = new HashMap<String, String>();


    private Context mContext;


    private String userID;



    private File imgFile, docFile;
    private Map<String, Object> user;

    private Uri i;
    public static String PACKAGE_NAME;

    private Handler handler1, handler2;

    private ProgressBar progressBar;

    String filePath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_description);

        PACKAGE_NAME = getApplicationContext().getPackageName();
        filePath = "android.resource://"+PACKAGE_NAME+"/"+R.drawable.career_description0_page0;

        //handler1
        handler1 = new Handler();
        handler2 = new Handler();

        //뒤로가기 버튼
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //firebase
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Content
        mContext = getApplicationContext();
        progressBar = findViewById(R.id.progressBar);

        //var
        bExpanded = false;


        //widgets
        progressBar = findViewById(R.id.progressBar);
        expandedScrn_name = findViewById(R.id.expandedScrn_name);

        name_EditText = findViewById(R.id.name_EditText);                   //이름
        engName_EditText= findViewById(R.id.engName_EditText);              //영어이름
        chName_EditText= findViewById(R.id.chName_EditText);
        rrn_EditText= findViewById(R.id.rrn_EditText);
        age_EditText= findViewById(R.id.age_EditText);
        phoneNum_EditText = findViewById(R.id.phoneNum_EditText);     //휴대폰
        num_EditText= findViewById(R.id.num_EditText);
        email_EditText = findViewById(R.id.email_EditText);                 //이메일
        addr_EditText = findViewById(R.id.addr_EditText);             //주소

        TextInputLayout_name = findViewById(R.id.TextInputLayout_name);
        TextInputLayout_engName = findViewById(R.id.TextInputLayout_engName);
        TextInputLayout_chName = findViewById(R.id.TextInputLayout_chName);
        TextInputLayout_rrn = findViewById(R.id.TextInputLayout_rrn);
        TextInputLayout_age = findViewById(R.id.TextInputLayout_age);
        TextInputLayout_phoneNum = findViewById(R.id.TextInputLayout_phoneNum);
        TextInputLayout_num = findViewById(R.id.TextInputLayout_num);
        TextInputLayout_email = findViewById(R.id.TextInputLayout_email);
        TextInputLayout_addr = findViewById(R.id.TextInputLayout_addr);

        TextInputLayout_uLoc = findViewById(R.id.TextInputLayout_uLoc);
        TextInputLayout_uSco = findViewById(R.id.TextInputLayout_uSco);
        TextInputLayout_mLoc = findViewById(R.id.TextInputLayout_mLoc);
        TextInputLayout_mSco = findViewById(R.id.TextInputLayout_mSco);

        expandedScrn_download_without_modify = findViewById(R.id.expandedScrn_download_without_modify);

        //학력사항//
        //enterYM => enterYearMonth : 입학년월
        //graYM => graduationYearMonth : 졸업년월
        //name : 이름
        //graCls => graduationClassification : 졸업구분
        //고등학교
        highschool_enterYM_EditText = findViewById(R.id.highschool_enterYM_EditText);
        highschool_graYM_EditText = findViewById(R.id.highschool_graYM_EditText);
        highschool_name_EditText = findViewById(R.id.highschool_name_EditText);
        highschool_graCls_EditText = findViewById(R.id.highschool_graCls_EditText);
        //대학교
        university_enterYM_EditText = findViewById(R.id.university_enterYM_EditText);
        university_graYM_EditText = findViewById(R.id.university_graYM_EditText);
        university_graCls_EditText = findViewById(R.id.university_graCls_EditText);
        university_name_EditText = findViewById(R.id.university_name_EditText);
        university_major_EditText = findViewById(R.id.university_major_EditText);
        university = findViewById(R.id.university);
        TextInputEditText_uLoc = findViewById(R.id.TextInputEditText_uLoc);
        TextInputEditText_uSco = findViewById(R.id.TextInputEditText_uSco);

        //대학원
        master_enterYM_EditText = findViewById(R.id.master_enterYM_EditText);
        master_graYM_EditText = findViewById(R.id.master_graYM_EditText);
        master_graCls_EditText = findViewById(R.id.master_graCls_EditText);
        TextInputEditText_mLoc = findViewById(R.id.TextInputEditText_mLoc);
        TextInputEditText_mSco = findViewById(R.id.TextInputEditText_mSco);
        master_name_EditText = findViewById(R.id.master_name_EditText);
        master_major_EditText = findViewById(R.id.master_major_EditText);
        master_graThe_EditText = findViewById(R.id.master_graThe_EditText);
        master_LAB_EditText = findViewById(R.id.master_LAB_EditText);
        master = findViewById(R.id.master);


        //경력사항//
        //name : 이름
        //enterYM => enterYearMonth : 입사년월
        //resignYM => resignYearMonth : 퇴사년월
        //office : 담당부서
        //task : 담당업무
        //경력사항 1
        formOfCareer1_name_EditText = findViewById(R.id.formOfCareer1_name_EditText);
        formOfCareer1_enterYM_EditText = findViewById(R.id.formOfCareer1_enterYM_EditText);
        formOfCareer1_resignYM_EditText = findViewById(R.id.formOfCareer1_resignYM_EditText);
        formOfCareer1_office_EditText = findViewById(R.id.formOfCareer1_office_EditText);
        formOfCareer1_task_EditText = findViewById(R.id.formOfCareer1_task_EditText);
        formOfCareer1 = findViewById(R.id.formOfCareer1);
        //경력사항 2
        formOfCareer2_name_EditText = findViewById(R.id.formOfCareer2_name_EditText);
        formOfCareer2_enterYM_EditText = findViewById(R.id.formOfCareer2_enterYM_EditText);
        formOfCareer2_resignYM_EditText = findViewById(R.id.formOfCareer2_resignYM_EditText);
        formOfCareer2_office_EditText = findViewById(R.id.formOfCareer2_office_EditText);
        formOfCareer2_task_EditText = findViewById(R.id.formOfCareer2_task_EditText);
        formOfCareer2 = findViewById(R.id.formOfCareer2);
        //경력사항 3
        formOfCareer3_name_EditText = findViewById(R.id.formOfCareer3_name_EditText);
        formOfCareer3_enterYM_EditText = findViewById(R.id.formOfCareer3_enterYM_EditText);
        formOfCareer3_resignYM_EditText = findViewById(R.id.formOfCareer3_resignYM_EditText);
        formOfCareer3_office_EditText = findViewById(R.id.formOfCareer3_office_EditText);
        formOfCareer3_task_EditText = findViewById(R.id.formOfCareer3_task_EditText);
        formOfCareer3 = findViewById(R.id.formOfCareer3);

        //자격증//
        //date : 취득일
        //cntnt => content / 특수자격 및 면허
        //grade : 등급
        //publication : 발행처
        //자격증1
        license1_date_EditText = findViewById(R.id.license1_date_EditText);
        license1_cntnt_EditText = findViewById(R.id.license1_cntnt_Edittext);
        license1_grade_EditText = findViewById(R.id.license1_grade_EditText);
        license1_publication_EditText = findViewById(R.id.license1_publication_EditText);
        license1 = findViewById(R.id.license1);
        //자격증2
        license2_date_EditText = findViewById(R.id.license2_date_EditText);
        license2_cntnt_EditText = findViewById(R.id.license2_cntnt_EditText);
        license2_grade_EditText = findViewById(R.id.license2_grade_EditText);
        license2_publication_EditText = findViewById(R.id.license2_publication_EditText);
        license2 = findViewById(R.id.license2);

        //수상//
        //date : 수상일자
        //cntnt : 수상내역
        //publication : 발행처
        //수상1
        award1_date_EditText = findViewById(R.id.award1_date_EditText);
        award1_cntnt_EditText = findViewById(R.id.award1_cntnt_EditText);
        award1_publication_EditText = findViewById(R.id.award1_publication_EditText);
        award1 = findViewById(R.id.award1);
        //수상2
        award2_date_EditText = findViewById(R.id.award2_date_EditText);
        award2_cntnt_EditText = findViewById(R.id.award2_cntnt_EditText);
        award2_publication_EditText = findViewById(R.id.award2_publication_EditText);
        award2 = findViewById(R.id.award2);


        expandedScrn_mainImageView1 = findViewById(R.id.expandedScrn_mainImageView1);
        expandedScrn_mainImageView2 = findViewById(R.id.expandedScrn_mainImageView2);
        expandedScrn_mainImageView3 = findViewById(R.id.expandedScrn_mainImageView3);

        intent = getIntent();
        //pagePath0, pagePath1, pagePath2을 불러온다.
        pagePath0 = intent.getStringExtra("pagePath0");
        pagePath1 = intent.getStringExtra("pagePath1");
        pagePath2 = intent.getStringExtra("pagePath2");


        docName = intent.getStringExtra("docName");

        if(docName.equals("career_description0")){
            Log.d(TAG, "onCreate/docName : "+docName);
//            TextInputLayout_name.setVisibility(View.GONE);
            TextInputLayout_engName.setVisibility(View.GONE);
            TextInputLayout_chName.setVisibility(View.GONE);
            TextInputLayout_rrn.setVisibility(View.GONE);
            TextInputLayout_age.setVisibility(View.GONE);
//            TextInputLayout_phoneNum.setVisibility(View.GONE);
            TextInputLayout_num.setVisibility(View.GONE);
//            TextInputLayout_email.setVisibility(View.GONE);
//            TextInputLayout_addr.setVisibility(View.GONE);
        }
        else if(docName.equals("careerDescription1")){
            Log.d(TAG, "onCreate/docName : "+docName);
//            TextInputLayout_name.setVisibility(View.GONE);
//            TextInputLayout_engName.setVisibility(View.GONE);
            TextInputLayout_chName.setVisibility(View.GONE);
//            TextInputLayout_rrn.setVisibility(View.GONE);
//            TextInputLayout_age.setVisibility(View.GONE);
//            TextInputLayout_phoneNum.setVisibility(View.GONE);
            TextInputLayout_num.setVisibility(View.GONE);
//            TextInputLayout_email.setVisibility(View.GONE);
//            TextInputLayout_addr.setVisibility(View.GONE);
        }

        //find widgets;
        expandedScrn_name = findViewById(R.id.expandedScrn_name);

        //첫번째, 두번째, 세번째 페이지를 설정한다.
        if(!checkString(pagePath0)) {
            imgUri1 = Uri.parse(pagePath0);
            Glide.with(this).load(imgUri1)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(expandedScrn_mainImageView1);
        }
        //pagePath1에 값이 있을 때 첫번째 mainImageView에 rounded처리된 imgUri2의 이미지를 설정한다.
        if(!checkString(pagePath1)) {
            imgUri2 = Uri.parse(pagePath1);
            Glide.with(this).load(imgUri2)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(expandedScrn_mainImageView2);
        }
        //pagePath2에 값이 있을 때 첫번째 mainImageView에 rounded처리된 imgUri3의 이미지를 설정한다.
        if(!checkString(pagePath2)) {
            imgUri3 = Uri.parse(pagePath2);
            Glide.with(this).load(imgUri3)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(expandedScrn_mainImageView3);
        }


        //양식만 저장
        expandedScrn_download_without_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //권한을 요청 한다.
                checkPermission();

                //start downloading template, start progressbar
                downloadTmpltThread downloadTmpltThread = new downloadTmpltThread();
                downloadTmpltThread.start();

                //if downloading is finished,stop progressbar
                checkingDownloadThread checkingDownloadThread = new checkingDownloadThread();
                checkingDownloadThread.start();
            }
        });

        //생성 버튼
        create = findViewById(R.id.expandedScrn_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkPermission();
                checkPermission();

                //handler2
                //if user is logged off
                if (mAuth.getCurrentUser() == null) {
                    Toast.makeText(CareerDescriptionActivity.this, "로그인 해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    //start template processing, start progressbar
                    tmpltProcessThread tmpltProcessThread = new tmpltProcessThread();
                    tmpltProcessThread.start();

                    //if processing is finished,stop progressbar
                    checkingProcessThread checkingProcessThread = new checkingProcessThread();
                    checkingProcessThread.start();
                }
            }
        });

        expandedScrn_menu = findViewById(R.id.expandedScrn_menu);
        expandedScrn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(expandedScrn_menu);
            }
        });
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long cmpltDwnlID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            long doc_dwnlID = PreferenceManager.getLong(CareerDescriptionActivity.this, "doc_dwnlID");
            long img_dwnlID = PreferenceManager.getLong(CareerDescriptionActivity.this, "img_dwnlID");

            InputStream is = null;
            Document document = null;

            //다운로드 완료, 마지막 다운이 문서일때
            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (doc_dwnlID == cmpltDwnlID)) {
//                imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");
                downloadEP.download_picture();
            }


            //다운로드 완료, 마지막 다운이 프로필사진일 때
            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (img_dwnlID == cmpltDwnlID)) {
                fileNameWithExt = fileName+".docx";
                Log.d(TAG, "onReceive/fileNameWithExt : "+fileNameWithExt);
                docFile = new File(Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileNameWithExt);
                //임시파일이 존재할 때
                if (docFile.exists()) {
                    try {
                        //(name, engN, chN) = (이름, 영어이름, 한자이름)
                        //(rrn, age, phoneNum, email, addr) = (주민번호, 나이, 전화번호, 이메일, 주소)

                        //(hN, hEnt, hGrad, hIfy) = (고등학교 이름, 고등학교 입학년월, 고등학교 졸업년월, 졸업구분)
                        //(uN, uMaj, uEnt, uGrad, uLoc, uSco uIfy) = (대학교 이름, 대학교 전공, 대학교 입학년월, 대학교 졸업년월, 대학교 소재지, 대학교 학점, 대학교 졸업구분)
                        //(mN, mEnt, mGrad, mIfy) = (대학교 이름, 대학교 입학년월, 대학교 졸업년월, 졸업구분)

                        //(corpN, dep, corpEnt, corpRes, work) = (회사이름, 담당부서, 입사년월, 퇴사년월, 담당업무)

                        try {
                            is = new FileInputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileNameWithExt);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            document = new Document(is);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        DocumentBuilder builder = new DocumentBuilder(document);

                        if(docName.equals("career_description0")){
                            builder.insertImage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg",
                                    RelativeHorizontalPosition.MARGIN,
                                    360,
                                    RelativeVerticalPosition.MARGIN,
                                    20,
                                    110,
                                    150,
                                    WrapType.SQUARE);
                        }
                        else if(docName.equals("careerDescription1")){
                            builder.insertImage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg",
                                    RelativeHorizontalPosition.MARGIN,
                                    25,
                                    RelativeVerticalPosition.MARGIN,
                                    80,
                                    110,
                                    150,
                                    WrapType.SQUARE);
                        }
                        if(name.isEmpty()) {
                            document.getRange().replace("name","홍길동", new FindReplaceOptions());
                        } else{document.getRange().replace("name",name, new FindReplaceOptions());}
                        if(engName.isEmpty()) {
                            document.getRange().replace("engN","Hong Gil Dong", new FindReplaceOptions());
                        } else{document.getRange().replace("engN",engName, new FindReplaceOptions());}
                        if(chName.isEmpty()) {
                            document.getRange().replace("chN","", new FindReplaceOptions());}
                        else{document.getRange().replace("chN",chName, new FindReplaceOptions());}
                        if(rrn.isEmpty()) {
                            document.getRange().replace("rrn","", new FindReplaceOptions());}
                        else{document.getRange().replace("rrn",rrn, new FindReplaceOptions());}
                        if(age.isEmpty()) {
                            document.getRange().replace("age","", new FindReplaceOptions());
                        } else{document.getRange().replace("age",age, new FindReplaceOptions());}
                        if(phoneNum.isEmpty()) {
                            document.getRange().replace("phoneNum","", new FindReplaceOptions());
                        } else{document.getRange().replace("phoneNum",phoneNum, new FindReplaceOptions());}
                        if(email.isEmpty()) {
                            document.getRange().replace("name","", new FindReplaceOptions());
                        } else{document.getRange().replace("email",email, new FindReplaceOptions());}
                        if(addr.isEmpty()) {
                            document.getRange().replace("addr","", new FindReplaceOptions());
                        } else{document.getRange().replace("addr",addr, new FindReplaceOptions());}

                        if(hN.isEmpty()) {
                            document.getRange().replace("hN","", new FindReplaceOptions());
                        } else{document.getRange().replace("hN",hN, new FindReplaceOptions());}
                        if(hEnt.isEmpty()) {
                            document.getRange().replace("hEnt","", new FindReplaceOptions());
                        } else{document.getRange().replace("hEnt",hEnt, new FindReplaceOptions());}
                        if(hGrad.isEmpty()) {
                            document.getRange().replace("hGrad","", new FindReplaceOptions());
                        } else{document.getRange().replace("hGrad",hGrad, new FindReplaceOptions());}
                        if(hIfy.isEmpty()) {
                            document.getRange().replace("hIfy","", new FindReplaceOptions());
                        }else{document.getRange().replace("hIfy",hIfy, new FindReplaceOptions());}

                        if(uN.isEmpty()) {
                            document.getRange().replace("uN","", new FindReplaceOptions());
                        } else{document.getRange().replace("uN",uN, new FindReplaceOptions());}
                        if(uMaj.isEmpty()) {
                            document.getRange().replace("uMaj","", new FindReplaceOptions());
                        } else{document.getRange().replace("uMaj",uMaj, new FindReplaceOptions());}
                        if(uEnt.isEmpty()) {
                            document.getRange().replace("uEnt","", new FindReplaceOptions());
                        } else{document.getRange().replace("uEnt",uEnt, new FindReplaceOptions());}
                        if(uGrad.isEmpty()) {
                            document.getRange().replace("uGrad","", new FindReplaceOptions());
                        } else{document.getRange().replace("uGrad",uGrad, new FindReplaceOptions());}
                        if(uLoc.isEmpty()) {
                            document.getRange().replace("uLoc","", new FindReplaceOptions());
                        } else{document.getRange().replace("uLoc",uLoc, new FindReplaceOptions());}
                        if(uSco.isEmpty()) {
                            document.getRange().replace("uSco","", new FindReplaceOptions());
                        } else{document.getRange().replace("uSco",uSco, new FindReplaceOptions());}
                        if(uIfy.isEmpty()) {
                            document.getRange().replace("uIfy","", new FindReplaceOptions());
                        } else{document.getRange().replace("uIfy",uIfy, new FindReplaceOptions());}

                        if(mN.isEmpty()) {
                            document.getRange().replace("mN","", new FindReplaceOptions());
                        } else{document.getRange().replace("mN",mN+"석사", new FindReplaceOptions());}
                        if(mMaj.isEmpty()) {
                            document.getRange().replace("mMaj","", new FindReplaceOptions());
                        } else{document.getRange().replace("mMaj",mMaj, new FindReplaceOptions());}
                        if(mEnt.isEmpty()) {
                            document.getRange().replace("mEnt","", new FindReplaceOptions());
                        } else{document.getRange().replace("mEnt",mEnt, new FindReplaceOptions());}
                        if(mGrad.isEmpty()) {
                            document.getRange().replace("mGrad","", new FindReplaceOptions());
                        } else{document.getRange().replace("mGrad",mGrad, new FindReplaceOptions());}
                        if(mLoc.isEmpty()) {
                            document.getRange().replace("mLoc","", new FindReplaceOptions());
                        } else{document.getRange().replace("mLoc",mGrad, new FindReplaceOptions());}
                        if(uSco.isEmpty()) {
                            document.getRange().replace("mSco","", new FindReplaceOptions());
                        } else{document.getRange().replace("mSco",mSco, new FindReplaceOptions());}
                        if(mIfy.isEmpty()) {
                            document.getRange().replace("mIfy","", new FindReplaceOptions());
                        } else{document.getRange().replace("mIfy",mIfy, new FindReplaceOptions());}

                        if(corpN1.isEmpty()) {
                            document.getRange().replace("corpN1","", new FindReplaceOptions());
                        } else{document.getRange().replace("corpN1",corpN1, new FindReplaceOptions());}
                        if(dep1.isEmpty()) {
                            document.getRange().replace("dep1","", new FindReplaceOptions());
                        } else{document.getRange().replace("dep1",dep1, new FindReplaceOptions());}
                        if(corpEnt1.isEmpty()) {
                            document.getRange().replace("corpEnt1","", new FindReplaceOptions());
                        } else{document.getRange().replace("corpEnt1",corpEnt1, new FindReplaceOptions());}
                        if(corpRes1.isEmpty()) {
                            document.getRange().replace("corpRes1","", new FindReplaceOptions());
                        } else{document.getRange().replace("corpRes1",corpRes1, new FindReplaceOptions());}
                        if(work1.isEmpty()) {
                            document.getRange().replace("work1","", new FindReplaceOptions());
                        } else{document.getRange().replace("work1",work1, new FindReplaceOptions());}

                        if(corpN2.isEmpty()) {
                            document.getRange().replace("corpN2","", new FindReplaceOptions());
                        } else{document.getRange().replace("corpN2",corpN2, new FindReplaceOptions());}
                        if(dep2.isEmpty()) {
                            document.getRange().replace("dep2","", new FindReplaceOptions());
                        } else{document.getRange().replace("dep2",dep2, new FindReplaceOptions());}
                        if(corpEnt2.isEmpty()) {
                            document.getRange().replace("corpEnt2","", new FindReplaceOptions());
                        } else{document.getRange().replace("corpEnt2",corpEnt2, new FindReplaceOptions());}
                        if(corpRes2.isEmpty()) {
                            document.getRange().replace("corpRes2","", new FindReplaceOptions());
                        } else{document.getRange().replace("corpRes2",corpRes2, new FindReplaceOptions());}
                        if(work2.isEmpty()) {
                            document.getRange().replace("work2","", new FindReplaceOptions());
                        } else{document.getRange().replace("work2",work2, new FindReplaceOptions());}

                        if(corpN3.isEmpty()) {
                            document.getRange().replace("corpN3","", new FindReplaceOptions());
                        } else{document.getRange().replace("corpN3",corpN3, new FindReplaceOptions());}
                        if(dep3.isEmpty()) {
                            document.getRange().replace("dep3","", new FindReplaceOptions());
                        } else{document.getRange().replace("dep3",dep3, new FindReplaceOptions());}
                        if(corpEnt3.isEmpty()) {
                            document.getRange().replace("corpEnt3","", new FindReplaceOptions());
                        } else{document.getRange().replace("corpEnt3",corpEnt3, new FindReplaceOptions());}
                        if(corpRes3.isEmpty()) {
                            document.getRange().replace("corpRes3","", new FindReplaceOptions());
                        } else{document.getRange().replace("corpRes3",corpRes3, new FindReplaceOptions());}
                        if(work3.isEmpty()) {
                            document.getRange().replace("work3","", new FindReplaceOptions());
                        } else{document.getRange().replace("work3",work3, new FindReplaceOptions());}

                        document.save(Environment.getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileNameWithExt);

                        //임시파일을 삭제한다.
                        docFile.delete();


                        //CustomXWPFDocument클래스의 replace메소드는 워드 파일 내에 "${key}"를 value값으로 대체한다.
//                        CustomXWPFDocument c = new CustomXWPFDocument();
//                        c.replace(is,data,out);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(CareerDescriptionActivity.this, "No Document File!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        popupMenu.inflate(R.menu.expanded_menu);
        Menu menuOpts = popupMenu.getMenu();

        if(bExpanded){
            menuOpts.getItem(0).setTitle("숨기기");
        }
        else{
            menuOpts.getItem(0).setTitle("펼치기");
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.expand:
                        //bExpanded가 false일 때(숨겨져 있을 때) "펼치기"를 누르면
                        //대학교, 대학원, 경력사항2, 경력사항3, 자격증2, 수상2를 디스플레이 한다.
                         if(bExpanded) {
                             university_enterYM = university_enterYM_EditText.getText().toString().trim();
                             university_graYM = university_graYM_EditText.getText().toString().trim();
                             university_graCls = university_graCls_EditText.getText().toString().trim();
                             university_name = university_name_EditText.getText().toString().trim();
                             university_major = university_major_EditText.getText().toString().trim();

                             master_enterYM = master_enterYM_EditText.getText().toString().trim();
                             master_graYM = master_graYM_EditText.getText().toString().trim();
                             master_graCls = master_graCls_EditText.getText().toString().trim();
                             master_name = master_name_EditText.getText().toString().trim();
                             master_major = master_major_EditText.getText().toString().trim();
                             master_graThe = master_graThe_EditText.getText().toString().trim();
                             master_LAB = master_LAB_EditText.getText().toString().trim();

                             corpN2 = formOfCareer2_name_EditText.getText().toString().trim();
                             corpEnt2 = formOfCareer2_enterYM_EditText.getText().toString().trim();
                             corpRes2 = formOfCareer2_resignYM_EditText.getText().toString().trim();
                             dep2 = formOfCareer2_office_EditText.getText().toString().trim();
                             work2 = formOfCareer2_task_EditText.getText().toString().trim();

                             corpN3 = formOfCareer3_name_EditText.getText().toString().trim();
                             corpEnt3 = formOfCareer3_enterYM_EditText.getText().toString().trim();
                             corpRes3 = formOfCareer3_resignYM_EditText.getText().toString().trim();
                             dep3 = formOfCareer3_office_EditText.getText().toString().trim();
                             work3 = formOfCareer3_task_EditText.getText().toString().trim();

                             license1_date = license1_date_EditText.getText().toString().trim();
                             license1_cntnt = license1_cntnt_EditText.getText().toString().trim();
                             license1_grade = license1_grade_EditText.getText().toString().trim();
                             license1_publication = license1_publication_EditText.getText().toString().trim();

                             license2_date = license2_date_EditText.getText().toString().trim();
                             license2_cntnt = license2_cntnt_EditText.getText().toString().trim();
                             license2_grade = license2_grade_EditText.getText().toString().trim();
                             license2_publication = license2_publication_EditText.getText().toString().trim();

                             award1_date = award1_date_EditText.getText().toString().trim();
                             award1_cntnt = award1_cntnt_EditText.getText().toString().trim();
                             award1_publication = award1_publication_EditText.getText().toString().trim();

                             award2_date = award2_date_EditText.getText().toString().trim();
                             award2_cntnt = award2_cntnt_EditText.getText().toString().trim();
                             award2_publication = award2_publication_EditText.getText().toString().trim();

                             PreferenceManager.setBoolean(mContext, "careerbExpanded", false);

                             if (checkString(corpN2) && checkString(dep2) && checkString(corpEnt2) && checkString(corpRes2) &&
                                     checkString(work2)) {
                                 formOfCareer2.setVisibility(View.GONE);
                             }
                             if (checkString(corpN3) && checkString(dep3) && checkString(corpEnt3) && checkString(corpRes3) &&
                                     checkString(work3)) {
                                 formOfCareer3.setVisibility(View.GONE);
                             }
                             if (checkString(university_enterYM) && checkString(university_graYM) && checkString(university_graCls) && checkString(university_name) &&
                                     checkString(university_major)) {
                                 university.setVisibility(View.GONE);
                             }
                             if (checkString(master_enterYM) && checkString(master_graYM) && checkString(master_graCls) && checkString(master_name) &&
                                     checkString(master_major) && checkString(master_graThe) && checkString(master_LAB)) {
                                 master.setVisibility(View.GONE);

                             }if (checkString(license1_date) && checkString(license1_cntnt) && checkString(license1_grade) && checkString(license1_publication)) {
                                 license1.setVisibility(View.GONE);
                             }
                             if (checkString(license2_date) && checkString(license2_cntnt) && checkString(license2_grade) && checkString(license2_publication)) {
                                 license2.setVisibility(View.GONE);
                             }
                             if (checkString(award1_date) && checkString(award1_cntnt) && checkString(award1_publication)) {
                                 award1.setVisibility(View.GONE);
                             }
                             if (checkString(award2_date) && checkString(award2_cntnt) && checkString(award2_publication)) {
                                 award2.setVisibility(View.GONE);
                             }
                             bExpanded = false;
                             Toast.makeText(CareerDescriptionActivity.this, "기록되지 않은 항목이 숨겨 졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                         //bExpanded가 true일 때(펼쳐져 있을 때) "숨기기"를 누르면
                         //대학교, 대학원, 경력사항2, 경력사항3, 자격증2, 수상2의 모든 하위 항목을 검사하고
                         //모든 하위 항목이 비어있는 항목은 숨긴다.
                        else{
                             PreferenceManager.setBoolean(mContext, "careerbExpanded", true);
                             university.setVisibility(View.VISIBLE);
                             master.setVisibility(View.VISIBLE);
                             formOfCareer2.setVisibility(View.VISIBLE);
                             formOfCareer3.setVisibility(View.VISIBLE);
                             license1.setVisibility(View.VISIBLE);
                             license2.setVisibility(View.VISIBLE);
                             award1.setVisibility(View.VISIBLE);
                             award2.setVisibility(View.VISIBLE);
                             bExpanded = true;
                             Toast.makeText(CareerDescriptionActivity.this, "모든 항목이 펼쳐 졌습니다.", Toast.LENGTH_SHORT).show();

                        }
                        return true;
                        
                    //프로필 수정 스크린으로 이동하지 않고 사용자가 기록한 내용을 해당 스크린에서 프로필에 적용할 수 있도록 한다.
                    case R.id.applyToProfile:
                        name = name_EditText.getText().toString().trim();
                        email = email_EditText.getText().toString().trim();
                        phoneNum = phoneNum_EditText.getText().toString().trim();
                        addr = addr_EditText.getText().toString().trim();

                        license1_date = license1_date_EditText.getText().toString().trim();
                        license1_cntnt = license1_cntnt_EditText.getText().toString().trim();
                        license1_grade = license1_grade_EditText.getText().toString().trim();
                        license1_publication = license1_publication_EditText.getText().toString().trim();

                        license2_date = license2_date_EditText.getText().toString().trim();
                        license2_cntnt = license2_cntnt_EditText.getText().toString().trim();
                        license2_grade = license2_grade_EditText.getText().toString().trim();
                        license2_publication = license2_publication_EditText.getText().toString().trim();

                        award1_date = award1_date_EditText.getText().toString().trim();
                        award1_cntnt = award1_cntnt_EditText.getText().toString().trim();
                        award1_publication = award1_publication_EditText.getText().toString().trim();

                        award2_date = award2_date_EditText.getText().toString().trim();
                        award2_cntnt = award2_cntnt_EditText.getText().toString().trim();
                        award2_publication = award2_publication_EditText.getText().toString().trim();
                        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        highschool_enterYM = highschool_enterYM_EditText.getText().toString().trim();
                        highschool_graYM = highschool_graYM_EditText.getText().toString().trim();
                        highschool_name=highschool_name_EditText.getText().toString().trim();
                        highschool_graCls = highschool_graCls_EditText.getText().toString().trim();
                        
                        university_name=university_name_EditText.getText().toString().trim();
                        university_enterYM = university_enterYM_EditText.getText().toString().trim();
                        university_graYM=university_graYM_EditText.getText().toString().trim();
                        uLoc= TextInputEditText_uLoc.getText().toString().trim();
                        uSco= TextInputEditText_uSco.getText().toString().trim();
                        university_major = university_major_EditText.getText().toString().trim();
                        university_graCls = university_graCls_EditText.getText().toString().trim();

                        master_name = master_name_EditText.getText().toString().trim();
                        master_enterYM = master_enterYM_EditText.getText().toString().trim();
                        master_graYM = master_graYM_EditText.getText().toString().trim();
                        mLoc= TextInputEditText_mLoc.getText().toString().trim();
                        mSco= TextInputEditText_mSco.getText().toString().trim();
                        master_major = master_major_EditText.getText().toString().trim();
                        master_graCls = master_graCls_EditText.getText().toString().trim();
                        master_graThe = master_graThe_EditText.getText().toString().trim();
                        master_LAB = master_LAB_EditText.getText().toString().trim();
                        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                        corpN1 = formOfCareer1_name_EditText.getText().toString().trim();
                        corpEnt1 = formOfCareer1_enterYM_EditText.getText().toString().trim();
                        corpRes1 = formOfCareer1_resignYM_EditText.getText().toString().trim();
                        dep1 = formOfCareer1_office_EditText.getText().toString().trim();
                        work1 = formOfCareer1_task_EditText.getText().toString().trim();

                        corpN2 = formOfCareer2_name_EditText.getText().toString().trim();
                        corpEnt2 = formOfCareer2_enterYM_EditText.getText().toString().trim();
                        corpRes2 = formOfCareer2_resignYM_EditText.getText().toString().trim();
                        dep2 = formOfCareer2_office_EditText.getText().toString().trim();
                        work2 = formOfCareer2_task_EditText.getText().toString().trim();

                        corpN3 = formOfCareer3_name_EditText.getText().toString().trim();
                        corpEnt3 = formOfCareer3_enterYM_EditText.getText().toString().trim();
                        corpRes3 = formOfCareer3_resignYM_EditText.getText().toString().trim();
                        dep3 = formOfCareer3_office_EditText.getText().toString().trim();
                        work3 = formOfCareer3_task_EditText.getText().toString().trim();
                        
                        if(mAuth.getCurrentUser() == null){
                            Toast.makeText(mContext,"로그인 해주세요!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            userID = mAuth.getCurrentUser().getUid();
//                            fStore = FirebaseFirestore.getInstance();
                            if (userID != null) {
                                documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
                                user = new HashMap<>();
                                user.put("name", name);
                                user.put("email", email);
                                user.put("phoneNum", phoneNum);
                                user.put("addr", addr);
                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(mContext,"프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                documentReference = fStore.collection("users").document(userID).collection("profiles").document("licenses");
                                user = new HashMap<>();
                                user.put("license1_date", license1_date);
                                user.put("license1_cntnt", license1_cntnt);
                                user.put("license1_grade", license1_grade);
                                user.put("license1_publication", license1_publication);

                                user.put("license2_date", license2_date);
                                user.put("license2_cntnt", license2_cntnt);
                                user.put("license2_grade", license2_grade);
                                user.put("license2_publication", license2_publication);

                                user.put("award1_date", award1_date);
                                user.put("award1_cntnt", award1_cntnt);
                                user.put("award1_publication", award1_publication);

                                user.put("award2_date", award2_date);
                                user.put("award2_cntnt", award2_cntnt);
                                user.put("award2_publication", award2_publication);
                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(mContext,"수상 프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                documentReference = fStore.collection("users").document(userID).collection("profiles").document("eduBack");
                                user = new HashMap<>();
                                user.put("highschool_enterYM", highschool_enterYM);
                                user.put("highschool_graYM", highschool_graYM);
                                user.put("highschool_name", highschool_name);
                                user.put("highschool_graCls", highschool_graCls);


                                user.put("university_name", university_name);
                                user.put("university_enterYM", university_enterYM);
                                user.put("university_graYM", university_graYM);
                                user.put("uLoc", uLoc);
                                user.put("uSco",uSco);
                                user.put("university_major", university_major);
                                user.put("university_graCls", university_graCls);

                                user.put("master_name", master_name);
                                user.put("master_enterYM", master_enterYM);
                                user.put("master_graYM", master_graYM);
                                user.put("mLoc", mLoc);
                                user.put("mSco",mSco);
                                user.put("master_major", master_major);
                                user.put("master_graCls", master_graCls);
                                user.put("master_graThe", master_graThe);
                                user.put("master_LAB", master_LAB);
                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(mContext,"학력 프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                documentReference = fStore.collection("users").document(userID).collection("profiles").document("formOfCareer");
                                user = new HashMap<>();
                                user.put("formOfCareer1_name", corpN1);
                                user.put("formOfCareer1_enterYM", corpEnt1);
                                user.put("formOfCareer1_resignYM", corpRes1);
                                user.put("formOfCareer1_office", dep1);
                                user.put("formOfCareer1_task", work1);

                                user.put("formOfCareer2_name", corpN2);
                                user.put("formOfCareer2_enterYM", corpEnt2);
                                user.put("formOfCareer2_resignYM", corpRes2);
                                user.put("formOfCareer2_office", dep2);
                                user.put("formOfCareer2_task", work2);

                                user.put("formOfCareer3_name", corpN3);
                                user.put("formOfCareer3_enterYM", corpEnt3);
                                user.put("formOfCareer3_resignYM", corpRes3);
                                user.put("formOfCareer3_office", dep3);
                                user.put("formOfCareer3_task", work3);
                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(mContext,"경력 프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            Toast.makeText(mContext,"프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                        //프로필로 이동
                    case R.id.moveToProfile:
                        moveProfile = new Intent(mContext, ProfileScrnActivity.class);
                        mContext.startActivity(moveProfile);

                        return true;
                    default:
                        return false;
                }

            }
        });
        popupMenu.show();
    }
    private void updateUI() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (isSignedIn()) {
            mAuth = FirebaseAuth.getInstance();
            storageReference = fStorage.getInstance().getReference();
            userID = mAuth.getCurrentUser().getUid();
            bExpanded = false;

            //FirebaseFirestore의 collection("users").document(userID)에서 이름, 이메일, 휴대전화, 주소를 가져오고
            //EditText에 적용한다.
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
            documentReference.addSnapshotListener(CareerDescriptionActivity.this, new EventListener<DocumentSnapshot>() {
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

                        name_EditText.setText(name);
                        engName_EditText.setText(engName);
                        chName_EditText.setText(chName);
                        rrn_EditText.setText(rrn);
                        age_EditText.setText(age);
                        phoneNum_EditText.setText(phoneNum);
                        num_EditText.setText(num);
                        email_EditText.setText(email);
                        addr_EditText.setText(addr);
                    }

                }
            });
            //FirebaseFirestore의 collection("users").document(userID).collection("profiles").document("eduBack")에서 사용자가 프로필에 설정한 학력사항을 가져오고
            //EditText에 적용한다.
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("eduBack");
            documentReference.addSnapshotListener(CareerDescriptionActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {

                        hN = value.getString("highschool_name");
                        hEnt = value.getString("highschool_enterYM");
                        hGrad = value.getString("highschool_graYM");
                        hIfy = value.getString("highschool_graCls");

                        uN = value.getString("university_name");
                        uEnt = value.getString("university_enterYM");
                        uGrad = value.getString("university_graYM");
                        uLoc = value.getString("uLoc");
                        uSco= value.getString("uSco");
                        uMaj = value.getString("university_major");
                        uIfy = value.getString("university_graCls");
                        if (!bExpanded &&checkString(uN) && checkString(uEnt) && checkString(uGrad) && checkString(uLoc) &&checkString(uSco) && checkString(uMaj) && checkString(uIfy)) {
                            university.setVisibility(View.GONE);
                        }

                        mN = value.getString("master_name");
                        mEnt = value.getString("master_enterYM");
                        mGrad = value.getString("master_graYM");
                        mLoc = value.getString("mLoc");
                        mSco= value.getString("mSco");
                        mMaj = value.getString("master_major");
                        master_graThe = value.getString("master_graThe");
                        master_LAB = value.getString("master_LAB");
                        mIfy = value.getString("master_graCls");
                        if (!bExpanded &&!bExpanded &&checkString(mN) && checkString(mEnt) && checkString(mGrad) && checkString(mLoc) &&checkString(mSco) && checkString(mMaj) && checkString(mIfy)) {
                            master.setVisibility(View.GONE);
                        }
                        highschool_name_EditText.setText(hN);
                        highschool_enterYM_EditText.setText(hEnt);
                        highschool_graYM_EditText.setText(hGrad);
                        highschool_graCls_EditText.setText(hIfy);

                        university_name_EditText.setText(uN);
                        university_enterYM_EditText.setText(uEnt);
                        university_graYM_EditText.setText(uGrad);
                        TextInputEditText_uLoc.setText(uLoc);
                        TextInputEditText_uSco.setText(uSco);
                        university_major_EditText.setText(uMaj);
                        university_graCls_EditText.setText(uIfy);

                        master_name_EditText.setText(mN);
                        master_enterYM_EditText.setText(mEnt);
                        master_graYM_EditText.setText(mGrad);
                        TextInputEditText_mLoc.setText(mLoc);
                        TextInputEditText_mSco.setText(mSco);
                        master_major_EditText.setText(mMaj);
                        master_graThe_EditText.setText(master_graThe);
                        master_LAB_EditText.setText(master_LAB);
                        master_graCls_EditText.setText(mIfy);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"학력사항을 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //FirebaseFirestore의 collection("users").document(userID).collection("profiles").document("licenses")에서 사용자가 프로필에 설정한 자격증사항을
            //EditText에 적용한다.
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("licenses");
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        license1_date = value.getString("license1_date");
                        license1_cntnt = value.getString("license1_cntnt");
                        license1_grade = value.getString("license1_grade");
                        license1_publication = value.getString("license1_publication");
                        if (!bExpanded && checkString(license1_date) && checkString(license1_cntnt) && checkString(license1_grade) && checkString(license1_publication)) {
                            license1.setVisibility(View.GONE);
                        }
                        license2_date = value.getString("license2_date");
                        license2_cntnt = value.getString("license2_cntnt");
                        license2_grade = value.getString("license2_grade");
                        license2_publication = value.getString("license2_publication");
                        if (!bExpanded && checkString(license2_date) && checkString(license2_cntnt) && checkString(license2_grade) && checkString(license2_publication)) {
                            license2.setVisibility(View.GONE);
                        }
                        award1_date = value.getString("award1_date");
                        award1_cntnt = value.getString("award1_cntnt");
                        award1_publication = value.getString("award1_publication");
                        if (!bExpanded &&checkString(award1_date) && checkString(award1_cntnt) && checkString(award1_publication)) {
                            award1.setVisibility(View.GONE);
                        }
                        award2_date = value.getString("award2_date");
                        award2_cntnt = value.getString("award2_cntnt");
                        award2_publication = value.getString("award2_publication");
                        if (!bExpanded && checkString(award2_date) && checkString(award2_cntnt) && checkString(award2_publication)) {
                            award2.setVisibility(View.GONE);
                        }

                        license1_date_EditText.setText(license1_date);
                        license1_cntnt_EditText.setText(license1_cntnt);
                        license1_grade_EditText.setText(license1_grade);
                        license1_publication_EditText.setText(license1_publication);
                        license2_date_EditText.setText(license2_date);
                        license2_cntnt_EditText.setText(license2_cntnt);
                        license2_grade_EditText.setText(license2_grade);
                        license2_publication_EditText.setText(license2_publication);
                        award1_date_EditText.setText(award1_date);
                        award1_cntnt_EditText.setText(award1_cntnt);
                        award1_publication_EditText.setText(award1_publication);
                        award2_date_EditText.setText(award2_date);
                        award2_cntnt_EditText.setText(award2_cntnt);
                        award2_publication_EditText.setText(award2_publication);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"학력사항을 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //FirebaseFirestore의 collection("users").document(userID).collection("profiles").document("formOfCareer")에서 사용자가 프로필에 설정한 경력사항을 가져오고
            //EditText에 적용한다.
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("formOfCareer");
            documentReference.addSnapshotListener(CareerDescriptionActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        corpN1 = value.getString("formOfCareer1_name");
                        corpEnt1 = value.getString("formOfCareer1_enterYM");
                        corpRes1 = value.getString("formOfCareer1_resignYM");
                        dep1 = value.getString("formOfCareer1_office");
                        work1 = value.getString("formOfCareer1_task");
//                    if(checkString(formOfCareer1_name)&&checkString(formOfCareer1_enter)&&checkString(formOfCareer1_resign)&&checkString(formOfCareer1_office)&&
//                            checkString(formOfCareer1_task)){
//                        formOfCareer1.setVisibility(View.GONE);
//                    }

                        corpN2 = value.getString("formOfCareer2_name");
                        corpEnt2 = value.getString("formOfCareer2_enterYM");
                        corpRes2 = value.getString("formOfCareer2_resignYM");
                        dep2 = value.getString("formOfCareer2_office");
                        work2 = value.getString("formOfCareer2_task");
                        if (!bExpanded &&checkString(corpN2) && checkString(dep2) && checkString(corpEnt2) && checkString(corpRes2) &&
                                checkString(work2)) {
                            formOfCareer2.setVisibility(View.GONE);
                        }

                        corpN3 = value.getString("formOfCareer3_name");
                        corpEnt3 = value.getString("formOfCareer3_enterYM");
                        corpRes3 = value.getString("formOfCareer3_resignYM");
                        dep3 = value.getString("formOfCareer3_office");
                        work3 = value.getString("formOfCareer3_task");
                        if (!bExpanded &&checkString(corpN3) && checkString(dep3) && checkString(corpEnt3) && checkString(corpRes3) &&
                                checkString(work3)) {
                            formOfCareer3.setVisibility(View.GONE);
                        }

                        formOfCareer1_name_EditText.setText(corpN1);
                        formOfCareer1_enterYM_EditText.setText(corpEnt1);
                        formOfCareer1_resignYM_EditText.setText(corpRes1);
                        formOfCareer1_office_EditText.setText(dep1);
                        formOfCareer1_task_EditText.setText(work1);

                        formOfCareer2_name_EditText.setText(corpN2);
                        formOfCareer2_enterYM_EditText.setText(corpEnt2);
                        formOfCareer2_resignYM_EditText.setText(corpRes2);
                        formOfCareer2_office_EditText.setText(dep2);
                        formOfCareer2_task_EditText.setText(work2);

                        formOfCareer3_name_EditText.setText(corpN3);
                        formOfCareer3_enterYM_EditText.setText(corpEnt3);
                        formOfCareer3_resignYM_EditText.setText(corpRes3);
                        formOfCareer3_office_EditText.setText(dep3);
                        formOfCareer3_task_EditText.setText(work3);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"학력사항을 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //DownloadManager.ACTION_DOWNLOAD_COMPLETE, RUNIMG_CMPLT, DOC_DWNL_CMPLT 이벤트를 받을 수 있도록 하는
    //broadcastReceiver를 등록한다.
    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(RUNIMG_CMPLT);
        intentFilter.addAction(DOC_DWNL_CMPLT);

        registerReceiver(broadcastReceiver, intentFilter);
        updateUI();
    }
    //해당 액티비티가 포커스를 잃으면 broadcastReceiver등록을 해제한다.
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    
    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 다시 보지 않기 버튼을 만드려면 이 부분에 바로 요청을 하도록 하면 됨 (아래 else{..} 부분 제거)
            // ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_CAMERA);

            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_STORAGE:
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(mContext, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..
                break;
        }
    }

    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(getApplicationContext()) != null;
    }

    //문서를 다운하기만 한다.
    class downloadTmpltThread extends Thread{
        public void run() {
            handler1.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
            docName = intent.getStringExtra("docName");         //default이름
            fileName = expandedScrn_name.getText().toString().trim(); //사용자가 입력한 파일이름
            Log.d(TAG, "run: docName: "+docName+", fileName: "+fileName);

            //사용자가 파일 이름 입력을 하지 않았을 때, default이름으로 다운로드 한다.
            if(checkString(fileName)){
                downloadEP = new DownloadEP(getApplicationContext());
                downloadEP.download_without_modify(docName, docName);
            }
            //사용자가 파일이름을 입력했을 때
            else{
                downloadEP = new DownloadEP(getApplicationContext());
                downloadEP.download_without_modify(fileName, docName);
            }
        }
    }
    //문서 다운이 끝났는지 체크한다.
    class checkingDownloadThread extends Thread{
        boolean downloadComplete = false;
        File f;

        public void run() {
            docName = intent.getStringExtra("docName");         //default이름
            fileName = expandedScrn_name.getText().toString().trim(); //사용자가 입력한 파일이름
            if(fileName.isEmpty()) {
                Log.d(TAG, "run/absolutePath : "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/"+docName+".docx");
            }
            else {
                Log.d(TAG, "run/absolutePath : " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName + ".docx");
            }
            //파일 인스턴스를 생성할 수 있으면 반복문을 빠져나온다.
            while(!downloadComplete) {
                try {
                    if(checkString(fileName)) {
                        f = new File(Environment.DIRECTORY_DOWNLOADS + "/ZN/"+docName + ".docx");
                    }
                    else{
                        f = new File(Environment.DIRECTORY_DOWNLOADS + "/ZN/"+fileName + ".docx");
                    }
                    if(f.exists()&& !f.isDirectory()){
                        downloadComplete = true;
                    }
                }catch (NullPointerException e){}
                try {
                    Log.d(TAG, "DDING DDONG");
                    Thread.sleep(500);
                } catch (Exception e) {}
            }
            //progressBar를 숨긴다.
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext,"성공적으로 문서가 다운되었습니다.",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    //프로필이미지와 텍스트를 대체시킨다.
    class tmpltProcessThread extends Thread{
        @Override
        public void run() {
            handler2.post(new Runnable() { //progressBar를 보인다.
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

            docName = intent.getStringExtra("docName");             //default이름
            fileName = expandedScrn_name.getText().toString().trim();     //사용자가 입력한 파일이름
            Log.d(TAG, "run: docName: "+docName+", fileName: "+fileName);

            if (checkString(fileName)) {
                downloadEP = new DownloadEP(getApplicationContext());
                downloadEP.download_with_modify(docName, docName);
            } else {

                name = name_EditText.getText().toString().trim();
                email = email_EditText.getText().toString().trim();
                phoneNum = phoneNum_EditText.getText().toString().trim();
                addr = addr_EditText.getText().toString().trim();

                highschool_enterYM = highschool_enterYM_EditText.getText().toString().trim();
                highschool_graYM = highschool_graYM_EditText.getText().toString().trim();
                highschool_name = highschool_name_EditText.getText().toString().trim();
                highschool_graCls = highschool_graCls_EditText.getText().toString().trim();

                university_enterYM = university_enterYM_EditText.getText().toString().trim();
                university_graYM = university_graYM_EditText.getText().toString().trim();
                university_graCls = university_graCls_EditText.getText().toString().trim();
                university_name = university_name_EditText.getText().toString().trim();
                university_major = university_major_EditText.getText().toString().trim();

                master_enterYM = master_enterYM_EditText.getText().toString().trim();
                master_graYM = master_graYM_EditText.getText().toString().trim();
                master_graCls = master_graCls_EditText.getText().toString().trim();
                master_name = master_name_EditText.getText().toString().trim();
                master_major = master_major_EditText.getText().toString().trim();
                master_graThe = master_graThe_EditText.getText().toString().trim();
                master_LAB = master_LAB_EditText.getText().toString().trim();

                corpN1 = formOfCareer1_name_EditText.getText().toString().trim();
                corpEnt1 = formOfCareer1_enterYM_EditText.getText().toString().trim();
                corpRes1 = formOfCareer1_resignYM_EditText.getText().toString().trim();
                dep1 = formOfCareer1_office_EditText.getText().toString().trim();
                work1 = formOfCareer1_task_EditText.getText().toString().trim();

                corpN2 = formOfCareer2_name_EditText.getText().toString().trim();
                corpEnt2 = formOfCareer2_enterYM_EditText.getText().toString().trim();
                corpRes2 = formOfCareer2_resignYM_EditText.getText().toString().trim();
                dep2 = formOfCareer2_office_EditText.getText().toString().trim();
                work2 = formOfCareer2_task_EditText.getText().toString().trim();

                corpN3 = formOfCareer3_name_EditText.getText().toString().trim();
                corpEnt3 = formOfCareer3_enterYM_EditText.getText().toString().trim();
                corpRes3 = formOfCareer3_resignYM_EditText.getText().toString().trim();
                dep3 = formOfCareer3_office_EditText.getText().toString().trim();
                work3 = formOfCareer3_task_EditText.getText().toString().trim();

                license1_date = license1_date_EditText.getText().toString().trim();
                license1_cntnt = license1_cntnt_EditText.getText().toString().trim();
                license1_grade = license1_grade_EditText.getText().toString().trim();
                license1_publication = license1_publication_EditText.getText().toString().trim();

                license2_date = license2_date_EditText.getText().toString().trim();
                license2_cntnt = license2_cntnt_EditText.getText().toString().trim();
                license2_grade = license2_grade_EditText.getText().toString().trim();
                license2_publication = license2_publication_EditText.getText().toString().trim();

                award1_date = award1_date_EditText.getText().toString().trim();
                award1_cntnt = award1_cntnt_EditText.getText().toString().trim();
                award1_publication = award1_publication_EditText.getText().toString().trim();

                award2_date = award2_date_EditText.getText().toString().trim();
                award2_cntnt = award2_cntnt_EditText.getText().toString().trim();
                award2_publication = award2_publication_EditText.getText().toString().trim();

                downloadEP = new DownloadEP(getApplicationContext());
                downloadEP.download_with_modify(fileName, docName);
            }
        }
    }

    //텍스트, 프로필 이미지 대체가 끝났는지 체크한다.
    class checkingProcessThread extends Thread{
        boolean downloadComplete = false;

        File f;
        File imagePicture;

        public void run() {

            docName = intent.getStringExtra("docName");             //default이름
            fileName = expandedScrn_name.getText().toString().trim();     //사용자가 입력한 파일이름

            if(fileName.isEmpty()) {
                Log.d(TAG, "run/absolutePath : "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/"+docName+".docx");
            }
            else {
                Log.d(TAG, "run/absolutePath : " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName + ".docx");
            }
            //File 인스턴스를 만들 수 있으면 반복문을 빠져나온다.
            while(!downloadComplete) {
                try {
                    if(fileName.isEmpty()) {
                        f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/"+docName+".docx");
                    }
                    else{
                        f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/"+fileName+".docx");
                    }
                    if(f.exists()&& !f.isDirectory()){
                        downloadComplete = true;
                    }
                    Log.d(TAG, "run/f.exists() : "+f.exists());
                }catch (NullPointerException e){}
                try {
                    Log.d(TAG, "DDING DDONG");
                    Thread.sleep(500);
                } catch (Exception e) {}

            }
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext,"성공적으로 문서가 생성되었습니다.",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    boolean checkString(String str) {
        return str == null || str.length() == 0;
    }



}
