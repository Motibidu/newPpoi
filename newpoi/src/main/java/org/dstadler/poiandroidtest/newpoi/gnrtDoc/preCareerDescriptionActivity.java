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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;

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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class preCareerDescriptionActivity extends AppCompatActivity {


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
    private String imgPath1, imgPath2, imgPath3;


    //widgets
    private ImageButton backBtn, expandedScrn_menu;
    private Button expandedScrn_download_without_modify, create, expand;


    //firebase
    private StorageReference storageReference;
    private FirebaseStorage fStorage;
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;





    private String name, email, phoneNum, addr;


    private TextInputEditText highschool_enterYM_EditText, highschool_graYM_EditText, highschool_name_EditText, highschool_graCls_EditText,
            university_enterYM_EditText, university_graYM_EditText, university_graCls_EditText, university_name_EditText, university_major_EditText,
            master_enterYM_EditText, master_graYM_EditText, master_graCls_EditText, master_name_EditText, master_major_EditText, master_graThe_EditText,master_LAB_EditText;
    private TextInputEditText name_EditText, email_EditText, phoneNum_EditText, addr_EditText;
    private String highschool_enterYM,  highschool_graYM, highschool_name, highschool_graCls,
            university_enterYM, university_graYM, university_graCls, university_name, university_major,
            master_enterYM, master_graYM, master_graCls, master_name, master_major, master_graThe, master_LAB;
    private TextInputEditText formOfCareer1_name_EditText, formOfCareer1_enterYM_EditText ,formOfCareer1_office_EditText  ,formOfCareer1_task_EditText ,formOfCareer1_resignYM_EditText
            ,formOfCareer2_name_EditText, formOfCareer2_enterYM_EditText , formOfCareer2_office_EditText, formOfCareer2_task_EditText, formOfCareer2_resignYM_EditText
            ,formOfCareer3_name_EditText, formOfCareer3_enterYM_EditText, formOfCareer3_office_EditText, formOfCareer3_task_EditText, formOfCareer3_resignYM_EditText;
    private String formOfCareer1_name, formOfCareer1_enterYM, formOfCareer1_office , formOfCareer1_task, formOfCareer1_resignYM
            ,formOfCareer2_name, formOfCareer2_enterYM, formOfCareer2_office, formOfCareer2_task, formOfCareer2_resignYM
            ,formOfCareer3_name, formOfCareer3_enterYM, formOfCareer3_office, formOfCareer3_task, formOfCareer3_resignYM;
    private TextInputEditText license1_date_EditText, license1_cntnt_EditText, license1_grade_EditText, license1_publication_EditText,
            license2_date_EditText, license2_cntnt_EditText, license2_grade_EditText, license2_publication_EditText,
            award1_date_EditText, award1_cntnt_EditText, award1_publication_EditText,
            award2_date_EditText, award2_cntnt_EditText, award2_publication_EditText;

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
    private String fileName, folder;




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
        filePath = "android.resource://"+PACKAGE_NAME+"/"+R.drawable.career_description0_page1;

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
        email_EditText = findViewById(R.id.email_EditText);                 //이메일
        phoneNum_EditText = findViewById(R.id.phoneNum_EditText);     //휴대폰
        addr_EditText = findViewById(R.id.addr_EditText);             //주소

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
        //대학원
        master_enterYM_EditText = findViewById(R.id.master_enterYM_EditText);
        master_graYM_EditText = findViewById(R.id.master_graYM_EditText);
        master_graCls_EditText = findViewById(R.id.master_graCls_EditText);
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

        //로그인 해있지 않은 경우 기본사항(고등학교, 경력사항1, 자격증1, 수상1)만 디스플레이하고 나머지 항목을 생략한다.
        //나머지 항목은 팝업 메뉴에 '펼치기'기능을 통해 디스플레이한다.
        //if(mAuth.getCurrentUser() == null){
        formOfCareer2.setVisibility(View.GONE);
        formOfCareer3.setVisibility(View.GONE);
        university.setVisibility(View.GONE);
        master.setVisibility(View.GONE);
        license2.setVisibility(View.GONE);
        award2.setVisibility(View.GONE);
        //}

        expandedScrn_mainImageView1 = findViewById(R.id.expandedScrn_mainImageView1);
        expandedScrn_mainImageView2 = findViewById(R.id.expandedScrn_mainImageView2);
        expandedScrn_mainImageView3 = findViewById(R.id.expandedScrn_mainImageView3);

        intent = getIntent();
        //imgPath1, imgPath2, imgPath3을 불러온다.
        imgPath1 = intent.getStringExtra("imgPath1");
        imgPath2 = intent.getStringExtra("imgPath2");
        imgPath3 = intent.getStringExtra("imgPath3");

        //find widgets;
        expandedScrn_name = findViewById(R.id.expandedScrn_name);

        //첫번째, 두번째, 세번째 페이지를 설정한다.
        if(!checkString(imgPath1)) {
            imgUri1 = Uri.parse(imgPath1);
            Glide.with(this).load(imgUri1)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(expandedScrn_mainImageView1);
        }
        //imgPath2에 값이 있을 때 첫번째 mainImageView에 rounded처리된 imgUri2의 이미지를 설정한다.
        if(!checkString(imgPath2)) {
            imgUri2 = Uri.parse(imgPath2);
            Glide.with(this).load(imgUri2)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(expandedScrn_mainImageView2);
        }
        //imgPath3에 값이 있을 때 첫번째 mainImageView에 rounded처리된 imgUri3의 이미지를 설정한다.
        if(!checkString(imgPath3)) {
            imgUri3 = Uri.parse(imgPath3);
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
                    Toast.makeText(mContext, "로그인 해주세요.", Toast.LENGTH_SHORT).show();
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
            long doc_dwnlID = PreferenceManager.getLong(mContext, "doc_dwnlID");
            long img_dwnlID = PreferenceManager.getLong(mContext, "img_dwnlID");
            //DownloadManager.ACTION_DOWNLOAD_COMPLETE 이벤트를 수신했으며,
            //DownloadManager의 최신 다운로드로부터 반환받은 long타입 값,
            // 그리고 downloadFile_with_modify()메소드를 수행하면서 PreferenceManager에 등록한
            // "doc_dwnlID"의 값이 같을 때 다음 if문을 수행한다.


            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (doc_dwnlID == cmpltDwnlID)) {
                imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");
                //download/ZN폴더에 profile.jpg라는 파일이 있다면 "Profile Image File Exists!"라는 팝업 문구를 띄운다.
                if (imgFile.exists()){
                    downloadEP.download_picture();
                    //프로필 이미지 파일이 존재할 때는 DOC_DWNL_CMPLT이벤트를 발생시킨다.
                    documentProcess = new Intent(DOC_DWNL_CMPLT);
                    sendBroadcast(documentProcess);
                }
                //파일이 없다면 downloadEP 클래스의 download_picture()메소드를
                // 출하고 "Downloading Profile Image"팝업 이미지를 띄운다.
                // (download_picture()메소드는 download/ZN 폴더에 "profile.jpg"라는 이름으로 프로필 이미지를 다운로드한다.)
                else{
                    downloadEP.download_picture();
                }
            }

            //DownloadManager.ACTION_DOWNLOAD_COMPLETE 이벤트를 수신했으며,
            //DownloadManager의 최신 다운로드로부터 반환받은 long타입 값, 그리고 download_picture()를 수행하면서
            // PreferenceManager에 등록한 "img_dwnlID"의 값이 같을 때 다음 if문을 수행한다.
            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (img_dwnlID == cmpltDwnlID)
                    || intent.getAction().equals(DOC_DWNL_CMPLT)){
                fileName = fileName+".docx";
                imgFile = new File(Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");


                //프로필 이미지가 존재할 때
                if (imgFile.exists()) {
                    //문서에 삽입할 프로필 이미지의 경로
                    String img_path = Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg";
                    //이미지를 삽입할 문서의 경로
                    String doc_path = Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DOWNLOADS) + "/ZN/."+fileName;
                    //CustomXWPFDocuemnt()의 runImg()메소드는 워드 문서에 "사진"으로 북마크를 등록해놓은 자리에 이미지 파일을 삽입한다.
                    //넓이와 높이는 워드파일의 확장명을 .zip으로 바꾸고 /word/document.xml에서 <wp:extent cx="?"(넓이) cy="?"(높이)/>태그에서 추출한다.
                    //behindDoc이 true일 때 글씨 "사진"은 사진의 뒤에 위치한다.
                    new CustomXWPFDocument().runImg("사진",doc_path, img_path, true,
                            1133475, 1510665, 0, 0);//Bookmark replacement picture
                }
                else {
                    Toast.makeText(mContext, "No Image File!", Toast.LENGTH_SHORT).show();
                }
                //RUNIMG_CMPLT 이벤트를 발생시킨다.
                documentProcess = new Intent(RUNIMG_CMPLT);
                sendBroadcast(documentProcess);
            }
            //수신한 action이 RUNIMG_CMPLT일 때
            if(intent.getAction().equals(RUNIMG_CMPLT)) {
                docFile = new File(Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);
                //임시파일이 존재할 때
                if (docFile.exists()) {
//                    Toast.makeText(mContext, "Document processing start!", Toast.LENGTH_SHORT).show();
                    try {
                        InputStream is = new FileInputStream(Environment.getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);
                        final FileOutputStream out = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName));

                        //문서 내에 key : value 데이터들을 Map<string, string> data 변수내에 삽입한다.
                        data.put("name", name);
                        data.put("email", email);
                        data.put("phoneNum", phoneNum);
                        data.put("addr", addr);

                        data.put("highschool_enterYM", highschool_enterYM);
                        data.put("highschool_graYM", highschool_graYM);
                        data.put("highschool_graCls", highschool_graCls);
                        data.put("highschool_name", highschool_name);

                        data.put("university_enterYM", university_enterYM);
                        data.put("university_graYM", university_graYM);
                        data.put("university_graCls", university_graCls);
                        data.put("university_name", university_name);
                        data.put("university_major", university_major);

                        data.put("master_enterYM", master_enterYM);
                        data.put("master_graYM", master_graYM);
                        data.put("master_graCls", master_graCls);
                        data.put("master_name", master_name);
                        data.put("master_major", master_major);
                        data.put("master_graThe", master_graThe);
                        data.put("master_LAB", master_LAB);

                        data.put("formOfCareer1_name", formOfCareer1_name);
                        data.put("formOfCareer1_enterYM", formOfCareer1_enterYM);
                        data.put("formOfCareer1_resignYM", formOfCareer1_resignYM);
                        data.put("formOfCareer1_office", formOfCareer1_office);
                        data.put("formOfCareer1_task", formOfCareer1_task);

                        data.put("formOfCareer2_name", formOfCareer2_name);
                        data.put("formOfCareer2_enterYM", formOfCareer2_enterYM);
                        data.put("formOfCareer2_resignYM", formOfCareer2_resignYM);
                        data.put("formOfCareer2_office", formOfCareer2_office);
                        data.put("formOfCareer2_task", formOfCareer2_task);

                        data.put("formOfCareer3_name", formOfCareer3_name);
                        data.put("formOfCareer3_enterYM", formOfCareer3_enterYM);
                        data.put("formOfCareer3_resignYM", formOfCareer3_resignYM);
                        data.put("formOfCareer3_office", formOfCareer3_office);
                        data.put("formOfCareer3_task", formOfCareer3_task);

                        data.put("license1_date", license1_date);
                        data.put("license1_cntnt", license1_cntnt);
                        data.put("license1_grade", license1_grade);
                        data.put("license1_publication", license1_publication);

                        data.put("license2_date", license2_date);
                        data.put("license2_cntnt", license2_cntnt);
                        data.put("license2_grade", license2_grade);
                        data.put("license2_publication", license2_publication);

                        data.put("award1_date", award1_date);
                        data.put("award1_cntnt", award1_cntnt);
                        data.put("award1_publication", award1_publication);

                        data.put("award2_date", award2_date);
                        data.put("award2_cntnt", award2_cntnt);
                        data.put("award2_publication", award2_publication);

                        //CustomXWPFDocument클래스의 replace메소드는 워드 파일 내에 "${key}"를 value값으로 대체한다.
                        CustomXWPFDocument c = new CustomXWPFDocument();
                        c.replace(is,data,out);

                        //임시파일을 삭제한다.
                        docFile.delete();

                        Toast.makeText(mContext, "Finished!", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "No Document File!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        popupMenu.inflate(R.menu.expanded_menu);
        Menu menuOpts = popupMenu.getMenu();

        //PreferenceManager에 등록된 bExpanded가 true일 때(펼쳐져 있을때) "숨기기"를 디스플레이 한다.
        //PreferenceManager에 등록된 bExpanded가 false일 때(숨겨져 있을 때) "펼치기"를 디스플레이한다.
        bExpanded = PreferenceManager.getBoolean(mContext, "careerbExpanded");
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
                        bExpanded = PreferenceManager.getBoolean(mContext, "careerbExpanded");
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

                            formOfCareer2_name = formOfCareer2_name_EditText.getText().toString().trim();
                            formOfCareer2_enterYM = formOfCareer2_enterYM_EditText.getText().toString().trim();
                            formOfCareer2_resignYM = formOfCareer2_resignYM_EditText.getText().toString().trim();
                            formOfCareer2_office = formOfCareer2_office_EditText.getText().toString().trim();
                            formOfCareer2_task = formOfCareer2_task_EditText.getText().toString().trim();

                            formOfCareer3_name = formOfCareer3_name_EditText.getText().toString().trim();
                            formOfCareer3_enterYM = formOfCareer3_enterYM_EditText.getText().toString().trim();
                            formOfCareer3_resignYM = formOfCareer3_resignYM_EditText.getText().toString().trim();
                            formOfCareer3_office = formOfCareer3_office_EditText.getText().toString().trim();
                            formOfCareer3_task = formOfCareer3_task_EditText.getText().toString().trim();

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

                            if (checkString(formOfCareer2_name) && checkString(formOfCareer2_enterYM) && checkString(formOfCareer2_resignYM) && checkString(formOfCareer2_office) &&
                                    checkString(formOfCareer2_task)) {
                                formOfCareer2.setVisibility(View.GONE);
                            }
                            if (checkString(formOfCareer3_name) && checkString(formOfCareer3_enterYM) && checkString(formOfCareer3_resignYM) && checkString(formOfCareer3_office) &&
                                    checkString(formOfCareer3_task)) {
                                formOfCareer3.setVisibility(View.GONE);
                            }
                            if (checkString(university_enterYM) && checkString(university_graYM) && checkString(university_graCls) && checkString(university_name) &&
                                    checkString(university_major)) {
                                university.setVisibility(View.GONE);
                            }
                            if (checkString(master_enterYM) && checkString(master_graYM) && checkString(master_graCls) && checkString(master_name) &&
                                    checkString(master_major) && checkString(master_graThe) && checkString(master_LAB)) {
                                master.setVisibility(View.GONE);
                            }
                            if (checkString(license2_date) && checkString(license2_cntnt) && checkString(license2_grade) && checkString(license2_publication)) {
                                license2.setVisibility(View.GONE);
                            }
                            if (checkString(award2_date) && checkString(award2_cntnt) && checkString(award2_publication)) {
                                award2.setVisibility(View.GONE);
                            }
                            Toast.makeText(mContext, "기록되지 않은 항목이 숨겨 졌습니다.", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(mContext, "모든 항목이 펼쳐 졌습니다.", Toast.LENGTH_SHORT).show();

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

                        university_enterYM = university_enterYM_EditText.getText().toString().trim();
                        university_graYM=university_graYM_EditText.getText().toString().trim();
                        university_graCls = university_graCls_EditText.getText().toString().trim();
                        university_name=university_name_EditText.getText().toString().trim();
                        university_major = university_major_EditText.getText().toString().trim();

                        master_enterYM = master_enterYM_EditText.getText().toString().trim();
                        master_graYM = master_graYM_EditText.getText().toString().trim();
                        master_graCls = master_graCls_EditText.getText().toString().trim();
                        master_name = master_name_EditText.getText().toString().trim();
                        master_major = master_major_EditText.getText().toString().trim();
                        master_graThe = master_graThe_EditText.getText().toString().trim();
                        master_LAB = master_LAB_EditText.getText().toString().trim();
                        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                        formOfCareer1_name = formOfCareer1_name_EditText.getText().toString().trim();
                        formOfCareer1_enterYM = formOfCareer1_enterYM_EditText.getText().toString().trim();
                        formOfCareer1_resignYM = formOfCareer1_resignYM_EditText.getText().toString().trim();
                        formOfCareer1_office = formOfCareer1_office_EditText.getText().toString().trim();
                        formOfCareer1_task = formOfCareer1_task_EditText.getText().toString().trim();

                        formOfCareer2_name = formOfCareer2_name_EditText.getText().toString().trim();
                        formOfCareer2_enterYM = formOfCareer2_enterYM_EditText.getText().toString().trim();
                        formOfCareer2_resignYM = formOfCareer2_resignYM_EditText.getText().toString().trim();
                        formOfCareer2_office = formOfCareer2_office_EditText.getText().toString().trim();
                        formOfCareer2_task = formOfCareer2_task_EditText.getText().toString().trim();

                        formOfCareer3_name = formOfCareer3_name_EditText.getText().toString().trim();
                        formOfCareer3_enterYM = formOfCareer3_enterYM_EditText.getText().toString().trim();
                        formOfCareer3_resignYM = formOfCareer3_resignYM_EditText.getText().toString().trim();
                        formOfCareer3_office = formOfCareer3_office_EditText.getText().toString().trim();
                        formOfCareer3_task = formOfCareer3_task_EditText.getText().toString().trim();

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
                                user.put("university_enterYM", university_enterYM);
                                user.put("university_graYM", university_graYM);
                                user.put("university_graCls", university_graCls);
                                user.put("university_name", university_name);
                                user.put("university_major", university_major);
                                user.put("master_enterYM", master_enterYM);
                                user.put("master_graYM", master_graYM);
                                user.put("master_graCls", master_graCls);
                                user.put("master_name", master_name);
                                user.put("master_major", master_major);
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
                                user.put("formOfCareer1_name", formOfCareer1_name);
                                user.put("formOfCareer1_enterYM", formOfCareer1_enterYM);
                                user.put("formOfCareer1_resignYM", formOfCareer1_resignYM);
                                user.put("formOfCareer1_office", formOfCareer1_office);
                                user.put("formOfCareer1_task", formOfCareer1_task);

                                user.put("formOfCareer2_name", formOfCareer2_name);
                                user.put("formOfCareer2_enterYM", formOfCareer2_enterYM);
                                user.put("formOfCareer2_resignYM", formOfCareer2_resignYM);
                                user.put("formOfCareer2_office", formOfCareer2_office);
                                user.put("formOfCareer2_task", formOfCareer2_task);

                                user.put("formOfCareer3_name", formOfCareer3_name);
                                user.put("formOfCareer3_enterYM", formOfCareer3_enterYM);
                                user.put("formOfCareer3_resignYM", formOfCareer3_resignYM);
                                user.put("formOfCareer3_office", formOfCareer3_office);
                                user.put("formOfCareer3_task", formOfCareer3_task);
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

            //FirebaseFirestore의 collection("users").document(userID)에서 이름, 이메일, 휴대전화, 주소를 가져오고
            //EditText에 적용한다.
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        name = value.getString("name");
                        email = value.getString("email");
                        phoneNum = value.getString("phoneNum");
                        addr = value.getString("addr");

                        name_EditText.setText(name);
                        email_EditText.setText(email);
                        phoneNum_EditText.setText(phoneNum);
                        addr_EditText.setText(addr);
                    }

                }
            });
            //FirebaseFirestore의 collection("users").document(userID).collection("profiles").document("eduBack")에서 사용자가 프로필에 설정한 학력사항을 가져오고
            //EditText에 적용한다.
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("eduBack");
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        highschool_enterYM = value.getString("highschool_enterYM");
                        highschool_graYM = value.getString("highschool_graYM");
                        highschool_name = value.getString("highschool_name");
                        highschool_graCls = value.getString("highschool_graCls");

                        university_enterYM = value.getString("university_enterYM");
                        university_graYM = value.getString("university_graYM");
                        university_graCls = value.getString("university_graCls");
                        university_name = value.getString("university_name");
                        university_major = value.getString("university_major");
                        if (!bExpanded &&checkString(university_enterYM) && checkString(university_graYM) && checkString(university_graCls) && checkString(university_name) &&
                                checkString(university_major)) {
                            university.setVisibility(View.GONE);
                        }

                        master_enterYM = value.getString("master_enterYM");
                        master_graYM = value.getString("master_graYM");
                        master_graCls = value.getString("master_graCls");
                        master_name = value.getString("master_name");
                        master_major = value.getString("master_major");
                        master_graThe = value.getString("master_graThe");
                        master_LAB = value.getString("master_LAB");
                        if (!bExpanded &&checkString(master_enterYM) && checkString(master_graYM) && checkString(master_graCls) && checkString(master_name) &&
                                checkString(master_major) && checkString(master_graThe) && checkString(master_LAB)) {
                            master.setVisibility(View.GONE);
                        }
                        highschool_enterYM_EditText.setText(highschool_enterYM);
                        highschool_graYM_EditText.setText(highschool_graYM);
                        highschool_name_EditText.setText(highschool_name);
                        highschool_graCls_EditText.setText(highschool_graCls);

                        university_enterYM_EditText.setText(university_enterYM);
                        university_graYM_EditText.setText(university_graYM);
                        university_graCls_EditText.setText(university_graCls);
                        university_name_EditText.setText(university_name);
                        university_major_EditText.setText(university_major);

                        master_enterYM_EditText.setText(master_enterYM);
                        master_graYM_EditText.setText(master_graYM);
                        master_graCls_EditText.setText(master_graCls);
                        master_name_EditText.setText(master_name);
                        master_major_EditText.setText(master_major);
                        master_graThe_EditText.setText(master_graThe);
                        master_LAB_EditText.setText(master_LAB);
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
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        formOfCareer1_name = value.getString("formOfCareer1_name");
                        formOfCareer1_enterYM = value.getString("formOfCareer1_enterYM");
                        formOfCareer1_resignYM = value.getString("formOfCareer1_resignYM");
                        formOfCareer1_office = value.getString("formOfCareer1_office");
                        formOfCareer1_task = value.getString("formOfCareer1_task");
//                    if(checkString(formOfCareer1_name)&&checkString(formOfCareer1_enter)&&checkString(formOfCareer1_resign)&&checkString(formOfCareer1_office)&&
//                            checkString(formOfCareer1_task)){
//                        formOfCareer1.setVisibility(View.GONE);
//                    }

                        formOfCareer2_name = value.getString("formOfCareer2_name");
                        formOfCareer2_enterYM = value.getString("formOfCareer2_enterYM");
                        formOfCareer2_resignYM = value.getString("formOfCareer2_resignYM");
                        formOfCareer2_office = value.getString("formOfCareer2_office");
                        formOfCareer2_task = value.getString("formOfCareer2_task");
                        if (!bExpanded &&checkString(formOfCareer2_name) && checkString(formOfCareer2_enterYM) && checkString(formOfCareer2_resignYM) && checkString(formOfCareer2_office) &&
                                checkString(formOfCareer2_task)) {
                            formOfCareer2.setVisibility(View.GONE);
                        }

                        formOfCareer3_name = value.getString("formOfCareer3_name");
                        formOfCareer3_enterYM = value.getString("formOfCareer3_enterYM");
                        formOfCareer3_resignYM = value.getString("formOfCareer3_resignYM");
                        formOfCareer3_office = value.getString("formOfCareer3_office");
                        formOfCareer3_task = value.getString("formOfCareer3_task");
                        if (!bExpanded &&checkString(formOfCareer3_name) && checkString(formOfCareer3_enterYM) && checkString(formOfCareer3_resignYM) && checkString(formOfCareer3_office) &&
                                checkString(formOfCareer3_task)) {
                            formOfCareer3.setVisibility(View.GONE);
                        }

                        formOfCareer1_name_EditText.setText(formOfCareer1_name);
                        formOfCareer1_enterYM_EditText.setText(formOfCareer1_enterYM);
                        formOfCareer1_resignYM_EditText.setText(formOfCareer1_resignYM);
                        formOfCareer1_office_EditText.setText(formOfCareer1_office);
                        formOfCareer1_task_EditText.setText(formOfCareer1_task);

                        formOfCareer2_name_EditText.setText(formOfCareer2_name);
                        formOfCareer2_enterYM_EditText.setText(formOfCareer2_enterYM);
                        formOfCareer2_resignYM_EditText.setText(formOfCareer2_resignYM);
                        formOfCareer2_office_EditText.setText(formOfCareer2_office);
                        formOfCareer2_task_EditText.setText(formOfCareer2_task);

                        formOfCareer3_name_EditText.setText(formOfCareer3_name);
                        formOfCareer3_enterYM_EditText.setText(formOfCareer3_enterYM);
                        formOfCareer3_resignYM_EditText.setText(formOfCareer3_resignYM);
                        formOfCareer3_office_EditText.setText(formOfCareer3_office);
                        formOfCareer3_task_EditText.setText(formOfCareer3_task);
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


    class downloadTmpltThread extends Thread{
        public void run() {
            handler1.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

            //사용자가 파일이름 EditText에 기록한 내용을 불러온다.

            //파일이름이 비어있을 경우 "제목을 입력해주세요!"팝업 문구를 띄우고,
            //파일이름이 존재할 경우 파일 제목을 fileName으로 하는 docName+".docx"의 문서를 다운로드한다.
            //get titles
            docName = intent.getStringExtra("docName");
            fileName = expandedScrn_name.getText().toString().trim();

            if(checkString(fileName)){

                //다운로드진입점 클래스(downloadEP)를 생성하고 download_without_modfiy메소드를 호출한다.
                //download_without_modify메소드는 파이어베이스에서 uri를 성공적으로 불러오면 해당 uri를 downloadFile_without_modify메소드에 전달한다.
                //downloadFile_without_modify메소드는 download/ZN 폴더에 ".docx" 확장자를 붙여 문서를 다운로드한다.
                downloadEP = new DownloadEP(getApplicationContext());
                downloadEP.download_without_modify(docName, docName);
            }
            else{
                //careerDescription.class에서 보내온 docName을 불러온다.
                docName = intent.getStringExtra("docName");
                //다운로드진입점 클래스(downloadEP)를 생성하고 download_without_modfiy메소드를 호출한다.
                //download_without_modify메소드는 파이어베이스에서 uri를 성공적으로 불러오면 해당 uri를 downloadFile_without_modify메소드에 전달한다.
                //downloadFile_without_modify메소드는 download/ZN 폴더에 ".docx" 확장자를 붙여 문서를 다운로드한다.
                downloadEP = new DownloadEP(getApplicationContext());
                downloadEP.download_without_modify(fileName, docName);
            }
        }
    }
    //check if download is complete or not
    class checkingDownloadThread extends Thread{
        boolean downloadComplete = false;
        File f;


        public void run() {
            while(!downloadComplete) {
                //if can make File object, jump out of a loop
                try {
                    if(docName == docName) {
                        f = new File(Environment.DIRECTORY_DOWNLOADS + "/ZN/"+docName + ".docx");

                    }
                    else{
                        f = new File(Environment.DIRECTORY_DOWNLOADS + "/ZN/"+fileName + ".docx");

                    }

                    downloadComplete = true;

                }catch (NullPointerException e){
                    Log.d(TAG, "Download is not completed yet");
                }
                try {
                    Log.d(TAG, "DDING DDONG");
                    Thread.sleep(500);
                } catch (Exception e) {}
            }
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext,"성공적으로 문서가 다운되었습니다.",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    class tmpltProcessThread extends Thread{
        @Override
        public void run() {
            handler2.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

            docName = intent.getStringExtra("docName");
            fileName = expandedScrn_name.getText().toString().trim();
            if (checkString(fileName)) {
//                Toast.makeText(mContext, "파일 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "run: docName: "+docName+", fileName: "+fileName);

                downloadEP = new DownloadEP(getApplicationContext());
                downloadEP.download_with_modify(docName, docName);
            } else {
//                Toast.makeText(mContext, "Document processing Start!", Toast.LENGTH_SHORT).show();

                //get titles;
                docName = intent.getStringExtra("docName");
                fileName = expandedScrn_name.getText().toString().trim();

                //get texts;
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

                formOfCareer1_name = formOfCareer1_name_EditText.getText().toString().trim();
                formOfCareer1_enterYM = formOfCareer1_enterYM_EditText.getText().toString().trim();
                formOfCareer1_resignYM = formOfCareer1_resignYM_EditText.getText().toString().trim();
                formOfCareer1_office = formOfCareer1_office_EditText.getText().toString().trim();
                formOfCareer1_task = formOfCareer1_task_EditText.getText().toString().trim();

                formOfCareer2_name = formOfCareer2_name_EditText.getText().toString().trim();
                formOfCareer2_enterYM = formOfCareer2_enterYM_EditText.getText().toString().trim();
                formOfCareer2_resignYM = formOfCareer2_resignYM_EditText.getText().toString().trim();
                formOfCareer2_office = formOfCareer2_office_EditText.getText().toString().trim();
                formOfCareer2_task = formOfCareer2_task_EditText.getText().toString().trim();

                formOfCareer3_name = formOfCareer3_name_EditText.getText().toString().trim();
                formOfCareer3_enterYM = formOfCareer3_enterYM_EditText.getText().toString().trim();
                formOfCareer3_resignYM = formOfCareer3_resignYM_EditText.getText().toString().trim();
                formOfCareer3_office = formOfCareer3_office_EditText.getText().toString().trim();
                formOfCareer3_task = formOfCareer3_task_EditText.getText().toString().trim();

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

                //사용자가 인터페이스에 기록한 모든 내용을 불러오고 downloadEP클래스의 download_with_modify메소드를 호출한다.
                //download_with_modify메소드는 Firebase Storage로부터 uri를 성공적으로 불러오면 해당 uri를 downloadFile_with_modify메소드에게 전달한다.
                //downloadFile_with_modify메소드는 전달받은 uri를 통해 DownloadManager클래스 long enqueue(Uri uri)메소드를 호출한다.
                //enqueue메소드로부터 반환받은 long타입의 값을 PreferenceManager의 키 "doc_dwnlID"의 대응값으로 저장한다.

                //fileName : 사용자 입력사항
                //docName : 파이어베이스 문서 이름

                Log.d(TAG, "run: docName: "+docName+", fileName: "+fileName);
                downloadEP = new DownloadEP(getApplicationContext());
                downloadEP.download_with_modify(fileName, docName);
            }
        }
    }
    class checkingProcessThread extends Thread{

        boolean downloadComplete = false;
        File f;
        File imagePicture;

        public void run() {
            while(!downloadComplete) {
                //if can make File object, jump out of a loop
                try {
                    if(docName == docName) {
                        f = new File(Environment.DIRECTORY_DOWNLOADS + "/ZN/"+docName + ".docx");
                        imagePicture = new File(Environment.DIRECTORY_DOWNLOADS + "/ZN/"+"profile.jpg");
                    }
                    else{
                        f = new File(Environment.DIRECTORY_DOWNLOADS + "/ZN/"+fileName + ".docx");
                        imagePicture = new File(Environment.DIRECTORY_DOWNLOADS + "/ZN/"+"profile.jpg");
                    }

                    downloadComplete = true;

                }catch (NullPointerException e){
                    Log.d(TAG, "Download is not completed yet");
                }
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
