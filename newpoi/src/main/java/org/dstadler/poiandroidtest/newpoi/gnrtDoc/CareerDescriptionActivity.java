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
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.Range;
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
    private static final String TAG = CareerDescriptionActivity.class.getName();

    //static vars
    private static final int MY_PERMISSION_STORAGE = 1111;
    //image attributes
    public static int sCorner = 80;
    public static int sMargin = 1;
    public static int sBorder = 0;

    private boolean bExpanded;
    private String docName;
    private String pagePath0, pagePath1, pagePath2;
    private Uri imgUri0, imgUri1, imgUri2;
    private String fileName, fileNameWithExt;
    private int docNum;
    private String userID;
    private File  docFile;
    private Map<String, Object> user;
    private Handler handler1, handler2;

    //widgets
    private ImageButton backBtn, imageBtn_more;
    private Button btn_download, btn_create;
    private ProgressBar progressBar;
    private EditText editText_name;
    private ImageView image_main0, image_main1, image_main2;

    //contents
    private Context mContext;
    private AppCompatActivity mActivity;


    //firebase
    private StorageReference storageReference;
    private FirebaseStorage fStorage;
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;

    private Intent intent, moveProfile;
    private DownloadEP downloadEP;


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

    //자격증 및 수상
    private String
            licYM1, licC1, licG1, licP1,
            licYM2, licC2, licG2, licP2,
            aYM1 ,aC1, aP1,
            aYM2 ,aC2, aP2;


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

    //자격증 및 수상
    private TextInputEditText
            TextInputEditText_licYM1, TextInputEditText_licC1, TextInputEditText_licG1, TextInputEditText_licP1,
            TextInputEditText_licYM2, TextInputEditText_licC2, TextInputEditText_licG2, TextInputEditText_licP2,
            TextInputEditText_aYM1 ,TextInputEditText_aC1, TextInputEditText_aP1,
            TextInputEditText_aYM2 ,TextInputEditText_aC2, TextInputEditText_aP2;

    private TextInputLayout
            TextInputLayout_name, TextInputLayout_email, TextInputLayout_phoneNum, TextInputLayout_addr, TextInputLayout_engName, TextInputLayout_chName, TextInputLayout_rrn, TextInputLayout_age, TextInputLayout_num;

    //학력사항
    private TextInputLayout
            TextInputLayout_hN, TextInputLayout_hEnt, TextInputLayout_hGrad , TextInputLayout_hIfy,
            TextInputLayout_uN, TextInputLayout_uMaj, TextInputLayout_uEnt , TextInputLayout_uGrad, TextInputLayout_uLoc, TextInputLayout_uSco, TextInputLayout_uIfy,
            TextInputLayout_mN, TextInputLayout_mEnt, TextInputLayout_mLoc, TextInputLayout_mSco, TextInputLayout_mGrad, TextInputLayout_mIfy, TextInputLayout_mMaj;

    //경력사항
    private TextInputLayout
            TextInputLayout_corpN1, TextInputLayout_dep1, TextInputLayout_corpEnt1, TextInputLayout_corpRes1, TextInputLayout_work1
            ,TextInputLayout_corpN2, TextInputLayout_dep2, TextInputLayout_corpEnt2, TextInputLayout_corpRes2, TextInputLayout_work2
            ,TextInputLayout_corpN3, TextInputLayout_dep3, TextInputLayout_corpEnt3, TextInputLayout_corpRes3, TextInputLayout_work3;

    //자격증 및 수상
    private TextInputLayout
            TextInputLayout_licYM1, TextInputLayout_licC1, TextInputLayout_licG1, TextInputLayout_licP1,
            TextInputLayout_licYM2, TextInputLayout_licC2, TextInputLayout_licG2, TextInputLayout_licP2,
            TextInputLayout_aYM1 ,TextInputLayout_aC1, TextInputLayout_aP1,
            TextInputLayout_aYM2 ,TextInputLayout_aC2, TextInputLayout_aP2;



    private LinearLayout
            LinearLayout_highschool, LinearLayout_university, LinearLayout_master,
            LinearLayout_corp1, LinearLayout_corp2, LinearLayout_corp3,
            LinearLayout_license1, LinearLayout_license2,
            LinearLayout_award1, LinearLayout_award2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_description); //
        //=================================common===================================//
        //Content
        mContext = getApplicationContext();
        mActivity = this;

        //handler1
        handler1 = new Handler();
        handler2 = new Handler();

        //firebase
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //뒤로가기 버튼
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //widgets
        progressBar = findViewById(R.id.progressBar);



        image_main0 = findViewById(R.id.image_main0);
        image_main1 = findViewById(R.id.image_main1);
        image_main2 = findViewById(R.id.image_main2);

        btn_download = findViewById(R.id.btn_download);
        btn_create = findViewById(R.id.btn_create);
        imageBtn_more = findViewById(R.id.imageBtn_more);


        editText_name = findViewById(R.id.editText_name);


        //var
        bExpanded = false;

        //첫번째, 두번째, 세번째 페이지를 설정한다.
        intent = getIntent();

        pagePath0 = intent.getStringExtra("pagePath0");
        pagePath1 = intent.getStringExtra("pagePath1");
        pagePath2 = intent.getStringExtra("pagePath2");

        docName = intent.getStringExtra("docName");

        //pagePath0에 값이 있을 때 첫번째 mainImageView에 rounded처리된 imgUri0의 이미지를 설정한다.
        if(!checkString(pagePath0)) {
            imgUri0 = Uri.parse(pagePath0);
            Glide.with(this).load(imgUri0)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(image_main0);
        }
        //pagePath1에 값이 있을 때 첫번째 mainImageView에 rounded처리된 imgUri1의 이미지를 설정한다.
        if(!checkString(pagePath1)) {
            imgUri1 = Uri.parse(pagePath1);
            Glide.with(this).load(imgUri1)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(image_main1);
        }
        //pagePath2에 값이 있을 때 첫번째 mainImageView에 rounded처리된 imgUri2의 이미지를 설정한다.
        if(!checkString(pagePath2)) {
            imgUri2 = Uri.parse(pagePath2);
            Glide.with(this).load(imgUri2)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(image_main2);
        }


        //양식만 저장, 생성 버튼, 더보기 버튼
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();

                //start downloading template, start progressbar
                downloadFileThread downloadFileThread = new downloadFileThread();
                downloadFileThread.start();

                //if downloading is finished,stop progressbar
                checkingDownloadFileThread checkingDownloadFileThread = new checkingDownloadFileThread();
                checkingDownloadFileThread.start();
            }
        });
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();

                //if user is logged off
                if (mAuth.getCurrentUser() == null) {
                    Toast.makeText(mContext, "로그인 해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    downloadTempFileThread downloadTempFileThread = new downloadTempFileThread();
                    downloadProfileImgThread downloadProfileImgThread = new downloadProfileImgThread();
                    createResultThread createResultThread = new createResultThread();
                    checkingResultThread checkingResultThread = new checkingResultThread();
                    downloadTempFileThread.start();
                    downloadProfileImgThread.start();
                    createResultThread.start();
                    checkingResultThread.start();
                }
            }
        });
        imageBtn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(imageBtn_more);
            }
        });




        //====================================================================================//
        //LinearLayout
        LinearLayout_highschool  = findViewById(R.id.LinearLayout_highschool);
        LinearLayout_university = findViewById(R.id.LinearLayout_university);
        LinearLayout_master = findViewById(R.id.LinearLayout_master);
        LinearLayout_corp1 = findViewById(R.id.LinearLayout_corp1);
        LinearLayout_corp2 = findViewById(R.id.LinearLayout_corp2);
        LinearLayout_corp3 = findViewById(R.id.LinearLayout_corp3);
        LinearLayout_license1 = findViewById(R.id.LinearLayout_license1);
        LinearLayout_license2 = findViewById(R.id.LinearLayout_license2);
        LinearLayout_award1 = findViewById(R.id.LinearLayout_award1);
        LinearLayout_award2 = findViewById(R.id.LinearLayout_award2);


        //TextInputLayout
        TextInputLayout_name = findViewById(R.id.TextInputLayout_name);
        TextInputLayout_engName= findViewById(R.id.TextInputLayout_engName);
        TextInputLayout_chName= findViewById(R.id.TextInputLayout_chName);
        TextInputLayout_rrn= findViewById(R.id.TextInputLayout_rrn);
        TextInputLayout_age= findViewById(R.id.TextInputLayout_age);
        TextInputLayout_phoneNum = findViewById(R.id.TextInputLayout_phoneNum);
        TextInputLayout_num= findViewById(R.id.TextInputLayout_num);
        TextInputLayout_email = findViewById(R.id.TextInputLayout_email);
        TextInputLayout_addr = findViewById(R.id.TextInputLayout_addr);
        //고등학교
        TextInputLayout_hN = findViewById(R.id.TextInputLayout_hN);
        TextInputLayout_hEnt = findViewById(R.id.TextInputLayout_hEnt);
        TextInputLayout_hGrad = findViewById(R.id.TextInputLayout_hGrad);
        TextInputLayout_hIfy = findViewById(R.id.TextInputLayout_hIfy);

        //대학교
        TextInputLayout_uN = findViewById(R.id.TextInputLayout_uN);
        TextInputLayout_uEnt = findViewById(R.id.TextInputLayout_uEnt);
        TextInputLayout_uGrad = findViewById(R.id.TextInputLayout_uGrad);
        TextInputLayout_uMaj = findViewById(R.id.TextInputLayout_uMaj);
        TextInputLayout_uLoc = findViewById(R.id.TextInputLayout_uLoc);
        TextInputLayout_uSco = findViewById(R.id.TextInputLayout_uSco);
        TextInputLayout_uIfy = findViewById(R.id.TextInputLayout_uIfy);

        //대학원

        TextInputLayout_mN = findViewById(R.id.TextInputLayout_mN);
        TextInputLayout_mEnt = findViewById(R.id.TextInputLayout_mEnt);
        TextInputLayout_mGrad = findViewById(R.id.TextInputLayout_mGrad);
        TextInputLayout_mMaj = findViewById(R.id.TextInputLayout_mMaj);
        TextInputLayout_mLoc = findViewById(R.id.TextInputLayout_mLoc);
        TextInputLayout_mSco = findViewById(R.id.TextInputLayout_mSco);
        TextInputLayout_mIfy = findViewById(R.id.TextInputLayout_mIfy);

        //경력사항 1
        TextInputLayout_corpN1 = findViewById(R.id.TextInputLayout_corpN1);
        TextInputLayout_dep1 = findViewById(R.id.TextInputLayout_dep1);
        TextInputLayout_corpEnt1 = findViewById(R.id.TextInputLayout_corpEnt1);
        TextInputLayout_corpRes1 = findViewById(R.id.TextInputLayout_corpRes1);
        TextInputLayout_work1 = findViewById(R.id.TextInputLayout_work1);

        //경력사항 2
        TextInputLayout_corpN2 = findViewById(R.id.TextInputLayout_corpN2);
        TextInputLayout_dep2 = findViewById(R.id.TextInputLayout_dep2);
        TextInputLayout_corpEnt2 = findViewById(R.id.TextInputLayout_corpEnt2);
        TextInputLayout_corpRes2 = findViewById(R.id.TextInputLayout_corpRes2);
        TextInputLayout_work2 = findViewById(R.id.TextInputLayout_work2);

        //경력사항 3
        TextInputLayout_corpN3 = findViewById(R.id.TextInputLayout_corpN3);
        TextInputLayout_dep3 = findViewById(R.id.TextInputLayout_dep3);
        TextInputLayout_corpEnt3 = findViewById(R.id.TextInputLayout_corpEnt3);
        TextInputLayout_corpRes3 = findViewById(R.id.TextInputLayout_corpRes3);
        TextInputLayout_work3 = findViewById(R.id.TextInputLayout_work3);

        
        //수상 및 자격증
        TextInputLayout_licYM1 = findViewById(R.id.TextInputLayout_licYM1);
        TextInputLayout_licC1 = findViewById(R.id.TextInputLayout_licC1);
        TextInputLayout_licG1 = findViewById(R.id.TextInputLayout_licG1);
        TextInputLayout_licP1 = findViewById(R.id.TextInputLayout_licP1);


        TextInputLayout_licYM2 = findViewById(R.id.TextInputLayout_licYM2);
        TextInputLayout_licC2 = findViewById(R.id.TextInputLayout_licC2);
        TextInputLayout_licG2 = findViewById(R.id.TextInputLayout_licG2);
        TextInputLayout_licP2 = findViewById(R.id.TextInputLayout_licP2);


        TextInputLayout_aYM1 = findViewById(R.id.TextInputLayout_aYM1);
        TextInputLayout_aC1 = findViewById(R.id.TextInputLayout_aC1);
        TextInputLayout_aP1 = findViewById(R.id.TextInputLayout_aP1);


        TextInputLayout_aYM2 = findViewById(R.id.TextInputLayout_aYM2);
        TextInputLayout_aC2 = findViewById(R.id.TextInputLayout_aC2);
        TextInputLayout_aP2 = findViewById(R.id.TextInputLayout_aP2);




        //TextInputEditText_name, TextInputEditText_email, TextInputEditText_phoneNum,
        //TextInputEditText_addr, TextInputEditText_engName, TextInputEditText_chName,
        //TextInputEditText_rrn, TextInputEditText_age, TextInputEditText_num;
        //TextInputEditText_hN, TextInputEditText_hEnt, TextInputEditText_hGrad , TextInputEditText_hIfy
        //TextInputEditText_uN, TextInputEditText_uMaj, TextInputEditText_uEnt , TextInputEditText_uGrad, TextInputEditText_uLoc, TextInputEditText_uSco, TextInputEditText_uIfy,
        //TextInputEditText_mN, TextInputEditText_mEnt, TextInputEditText_mLoc, TextInputEditText_mSco, TextInputEditText_mGrad, TextInputEditText_mIfy, TextInputEditText_mMaj;
        //TextInputEditText_corpN1, TextInputEditText_dep1, TextInputEditText_corpEnt1, TextInputEditText_corpRes1, TextInputEditText_work1
        //TextInputEditText_corpN2, TextInputEditText_dep2, TextInputEditText_corpEnt2, TextInputEditText_corpRes2, TextInputEditText_work2
        //TextInputEditText_corpN3, TextInputEditText_dep3, TextInputEditText_corpEnt3, TextInputEditText_corpRes3, TextInputEditText_work3;
        //TextInputEditText_licYM1, TextInputEditText_licC1, TextInputEditText_licG1, TextInputEditText_licP1,
        //TextInputEditText_licYM2, TextInputEditText_licC2, TextInputEditText_licG2, TextInputEditText_licP2,
        //TextInputEditText_aYM1 ,TextInputEditText_aC1, TextInputEditText_aP1,
        //TextInputEditText_aYM2 ,TextInputEditText_aC2, TextInputEditText_aP2,

        //TextInputEditText
        //프로필
        //TextInputEditText_name, TextInputEditText_email, TextInputEditText_phoneNum,
        //TextInputEditText_addr, TextInputEditText_engName, TextInputEditText_chName,
        //TextInputEditText_rrn, TextInputEditText_age, TextInputEditText_num;

        TextInputEditText_name = findViewById(R.id.TextInputEditText_name);                   //이름
        TextInputEditText_engName= findViewById(R.id.TextInputEditText_engName);              //영어이름
        TextInputEditText_chName= findViewById(R.id.TextInputEditText_chName);
        TextInputEditText_rrn= findViewById(R.id.TextInputEditText_rrn);
        TextInputEditText_age= findViewById(R.id.TextInputEditText_age);
        TextInputEditText_phoneNum = findViewById(R.id.TextInputEditText_phoneNum);     //휴대폰
        TextInputEditText_num= findViewById(R.id.TextInputEditText_num);
        TextInputEditText_email = findViewById(R.id.TextInputEditText_email);                 //이메일
        TextInputEditText_addr = findViewById(R.id.TextInputEditText_addr);             //주소

        //학력사항
        //TextInputEditText_hN, TextInputEditText_hEnt, TextInputEditText_hGrad , TextInputEditText_hIfy
        //TextInputEditText_uN, TextInputEditText_uMaj, TextInputEditText_uEnt , TextInputEditText_uGrad, TextInputEditText_uLoc, TextInputEditText_uSco, TextInputEditText_uIfy,
        //TextInputEditText_mN, TextInputEditText_mEnt, TextInputEditText_mLoc, TextInputEditText_mSco, TextInputEditText_mGrad, TextInputEditText_mIfy, TextInputEditText_mMaj;
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

        //경력사항//
        //TextInputEditText_corpN1, TextInputEditText_dep1, TextInputEditText_corpEnt1, TextInputEditText_corpRes1, TextInputEditText_work1
        //TextInputEditText_corpN2, TextInputEditText_dep2, TextInputEditText_corpEnt2, TextInputEditText_corpRes2, TextInputEditText_work2
        //TextInputEditText_corpN3, TextInputEditText_dep3, TextInputEditText_corpEnt3, TextInputEditText_corpRes3, TextInputEditText_work3;
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


        //자격증//
        //date : 취득일
        //cntnt => content / 특수자격 및 면허
        //grade : 등급
        //publication : 발행처


        //자격증 및 수상
        //TextInputEditText_licYM1, TextInputEditText_licC1, TextInputEditText_licG1, TextInputEditText_licP1,
        //TextInputEditText_licYM2, TextInputEditText_licC2, TextInputEditText_licG2, TextInputEditText_licP2,
        //TextInputEditText_aYM1 ,TextInputEditText_aC1, TextInputEditText_aP1,
        //TextInputEditText_aYM2 ,TextInputEditText_aC2, TextInputEditText_aP2,

        TextInputEditText_licYM1 = findViewById(R.id.TextInputEditText_licYM1);
        TextInputEditText_licC1 = findViewById(R.id.TextInputEditText_licC1);
        TextInputEditText_licG1 = findViewById(R.id.TextInputEditText_licG1);
        TextInputEditText_licP1 = findViewById(R.id.TextInputEditText_licP1);


        TextInputEditText_licYM2 = findViewById(R.id.TextInputEditText_licYM2);
        TextInputEditText_licC2 = findViewById(R.id.TextInputEditText_licC2);
        TextInputEditText_licG2 = findViewById(R.id.TextInputEditText_licG2);
        TextInputEditText_licP2 = findViewById(R.id.TextInputEditText_licP2);


        TextInputEditText_aYM1 = findViewById(R.id.TextInputEditText_aYM1);
        TextInputEditText_aC1 = findViewById(R.id.TextInputEditText_aC1);
        TextInputEditText_aP1 = findViewById(R.id.TextInputEditText_aP1);


        TextInputEditText_aYM2 = findViewById(R.id.TextInputEditText_aYM2);
        TextInputEditText_aC2 = findViewById(R.id.TextInputEditText_aC2);
        TextInputEditText_aP2 = findViewById(R.id.TextInputEditText_aP2);


        //각 양식마다 필요없는 사항은 보이지 않도록 한다.
        docNum = intent.getIntExtra("docNum", 0);
        Log.d(TAG, "onCreate/docNum : "+docNum);
        if(docNum == 0){
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
        else if(docNum == 1){
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
        else if(docNum == 2){
//            TextInputLayout_name.setVisibility(View.GONE);
//            TextInputLayout_engName.setVisibility(View.GONE);
//            TextInputLayout_chName.setVisibility(View.GONE);
//            TextInputLayout_rrn.setVisibility(View.GONE);
//            TextInputLayout_age.setVisibility(View.GONE);
//            TextInputLayout_phoneNum.setVisibility(View.GONE);
//            TextInputLayout_num.setVisibility(View.GONE);
//            TextInputLayout_email.setVisibility(View.GONE);
//            TextInputLayout_addr.setVisibility(View.GONE);
        }
        //====================================================================================//

    }

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
                        getAllTextFromTextInputEditText();
                        if(bExpanded) {
                            if (checkString(corpN2) && checkString(dep2) && checkString(corpEnt2) && checkString(corpRes2) &&
                                    checkString(work2)) {
                                LinearLayout_corp2.setVisibility(View.GONE);
                            }
                            if (checkString(corpN3) && checkString(dep3) && checkString(corpEnt3) && checkString(corpRes3) &&
                                    checkString(work3)) {
                                LinearLayout_corp3.setVisibility(View.GONE);
                            }
                            if (checkString(uEnt) && checkString(uGrad) && checkString(uIfy) && checkString(uN) &&
                                    checkString(uMaj)) {
                                LinearLayout_university.setVisibility(View.GONE);
                            }
                            if (checkString(mEnt) && checkString(mGrad) && checkString(mIfy) && checkString(mN) &&
                                    checkString(mMaj)) {
                                LinearLayout_master.setVisibility(View.GONE);

                            }if (checkString(licYM1) && checkString(licC1) && checkString(licG1) && checkString(licP1)) {
                                LinearLayout_license1.setVisibility(View.GONE);
                            }
                            if (checkString(licYM2) && checkString(licC2) && checkString(licG2) && checkString(licP2)) {
                                LinearLayout_license2.setVisibility(View.GONE);
                            }
                            if (checkString(aYM1) && checkString(aC1) && checkString(aP1)) {
                                LinearLayout_award1.setVisibility(View.GONE);
                            }
                            if (checkString(aYM2) && checkString(aC2) && checkString(aP2)) {
                                LinearLayout_award2.setVisibility(View.GONE);
                            }
                            bExpanded = false;
                            Toast.makeText(mContext, "기록되지 않은 항목이 숨겨 졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            PreferenceManager.setBoolean(mContext, "careerbExpanded", true);
                            LinearLayout_university.setVisibility(View.VISIBLE);
                            LinearLayout_master.setVisibility(View.VISIBLE);

                            LinearLayout_corp2.setVisibility(View.VISIBLE);
                            LinearLayout_corp3.setVisibility(View.VISIBLE);
                            LinearLayout_license1.setVisibility(View.VISIBLE);
                            LinearLayout_license2.setVisibility(View.VISIBLE);

                            LinearLayout_award1.setVisibility(View.VISIBLE);
                            LinearLayout_award2.setVisibility(View.VISIBLE);
                            bExpanded = true;
                            Toast.makeText(mContext, "모든 항목이 펼쳐 졌습니다.", Toast.LENGTH_SHORT).show();
                        }

                        return true;

                    //프로필 수정 스크린으로 이동하지 않고 사용자가 기록한 내용을 해당 스크린에서 프로필에 적용할 수 있도록 한다.
                    case R.id.applyToProfile:
                        if(mAuth.getCurrentUser() == null){
                            Toast.makeText(mContext,"로그인 해주세요!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //불러온 텍스트들을 모두 파이어베이스에 넣는다.
                            applyAll_Firestore();
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

    private void applyAll_Firestore(){

        //TextInputEditText로부터 텍스트를 불러오고, 클래스 변수에 넣는다.
        getAllTextFromTextInputEditText();

        userID = mAuth.getCurrentUser().getUid();

        if (!userID.isEmpty()) {
            //프로필사항
            //name, email, phoneNum, addr, engName, chName, rrn, age, num;
            Map<String, Object> users = new HashMap<>();
            users.put("name", name);
            users.put("email", email);
            users.put("phoneNum", phoneNum);
            users.put("addr", addr);
            users.put("engName", engName);
            users.put("chName", chName);
            users.put("rrn", rrn);
            users.put("age", age);
            users.put("num", num);
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
            documentReference.update(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    //Toast.makeText(mContext,"프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                }
            });

            //학력사항
            Map<String, Object> eduBack = new HashMap<>();
            eduBack.put("hN", hN);
            eduBack.put("hEnt", hEnt);
            eduBack.put("hGrad", hGrad);
            eduBack.put("hIfy", hIfy);


            eduBack.put("uN", uN);
            eduBack.put("uMaj", uMaj);
            eduBack.put("uEnt", uEnt);
            eduBack.put("uLoc", uLoc);
            eduBack.put("uSco",uSco);
            eduBack.put("uGrad", uGrad);
            eduBack.put("uIfy", uIfy);

            eduBack.put("mN", mN);
            eduBack.put("mEnt", mEnt);
            eduBack.put("mLoc", mLoc);
            eduBack.put("mSco", mSco);
            eduBack.put("mGrad",mGrad);
            eduBack.put("mIfy", mIfy);
            eduBack.put("mMaj", mMaj);

            documentReference = fStore.collection("users").document(userID).collection("profiles").document("eduBack");
            documentReference.update(eduBack).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
//                                        Toast.makeText(mContext,"학력 프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                }
            });

            //경력사항
            Map<String, Object> corp = new HashMap<>();
            corp.put("corpN1", corpN1);
            corp.put("dep1", dep1);
            corp.put("corpEnt1", corpEnt1);
            corp.put("corpRes1", corpRes1);
            corp.put("work1", work1);

            corp.put("corpN2", corpN2);
            corp.put("dep2", dep2);
            corp.put("corpEnt2", corpEnt2);
            corp.put("corpRes2", corpRes2);
            corp.put("work2", work2);

            corp.put("corpN3", corpN3);
            corp.put("dep3", dep3);
            corp.put("corpEnt3", corpEnt3);
            corp.put("corpRes3", corpRes3);
            corp.put("work3", work3);
            documentReference = fStore.collection("users").document(userID).collection("profiles").document("formOfCareer");
            documentReference.update(corp).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
//                                        Toast.makeText(mContext,"경력 프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                }
            });


            //자격증 및 수상
            Map<String, Object> licenses  = new HashMap<>();
            licenses.put("licYM1", licYM1);
            licenses.put("licC1", licC1);
            licenses.put("licG1", licG1);
            licenses.put("licP1", licP1);

            licenses.put("licYM2", licYM2);
            licenses.put("licC2", licC2);
            licenses.put("licG2", licG2);
            licenses.put("licP2", licP2);

            licenses.put("aYM1", aYM1);
            licenses.put("aC1", aC1);
            licenses.put("aP1", aP1);

            licenses.put("aYM2", aYM2);
            licenses.put("aC2", aC2);
            licenses.put("aP2", aP2);

            documentReference = fStore.collection("users").document(userID).collection("profiles").document("licenses");
            documentReference.update(licenses).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
//                                        Toast.makeText(mContext,"수상 프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                }
            });

            Toast.makeText(mContext,"프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        if (isSignedIn()) {
            mAuth = FirebaseAuth.getInstance();
            storageReference = fStorage.getInstance().getReference();
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
            //FirebaseFirestore의 collection("users").document(userID).collection("profiles").document("eduBack")에서 사용자가 프로필에 설정한 학력사항을 가져오고
            //EditText에 적용한다.
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
                        uSco= value.getString("uSco");
                        uMaj = value.getString("uMaj");
                        uIfy = value.getString("uIfy");
                        if (!bExpanded &&checkString(uN) && checkString(uEnt) && checkString(uGrad) && checkString(uLoc) &&checkString(uSco) && checkString(uMaj) && checkString(uIfy)) {
                            LinearLayout_university.setVisibility(View.GONE);
                        }

                        mN = value.getString("mN");
                        mEnt = value.getString("mEnt");
                        mGrad = value.getString("mGrad");
                        mLoc = value.getString("mLoc");
                        mSco= value.getString("mSco");
                        mMaj = value.getString("mMaj");
                        mIfy = value.getString("mIfy");
                        if (!bExpanded &&!bExpanded &&checkString(mN) && checkString(mEnt) && checkString(mGrad) && checkString(mLoc) &&checkString(mSco) && checkString(mMaj) && checkString(mIfy)) {
                            LinearLayout_master.setVisibility(View.GONE);
                        }
                        //불러온 모든 텍스트들을 TextInputEditText에 넣는다.
                        setAllTextInTextInputEditText();

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
                        licYM1 = value.getString("licYM1");
                        licC1 = value.getString("licC1");
                        licC1 = value.getString("licG1");
                        licP1 = value.getString("licP1");
                        if (!bExpanded && checkString(licYM1) && checkString(licC1) && checkString(licG1) && checkString(licP1)) {
                            LinearLayout_license1.setVisibility(View.GONE);
                        }
                        licYM2 = value.getString("licYM2");
                        licC2 = value.getString("licC2");
                        licG2 = value.getString("licG2");
                        licP2 = value.getString("licP2");
                        if (!bExpanded && checkString(licYM2) && checkString(licC2) && checkString(licG2) && checkString(licP2)) {
                            LinearLayout_license2.setVisibility(View.GONE);
                        }
                        aYM1 = value.getString("aYM1");
                        aC1 = value.getString("aC1");
                        aP1 = value.getString("aP1");
                        if (!bExpanded &&checkString(aYM1) && checkString(aC1) && checkString(aP1)) {
                            LinearLayout_award1.setVisibility(View.GONE);
                        }
                        aYM2 = value.getString("aYM2");
                        aC2 = value.getString("aC2");
                        aP2 = value.getString("aP2");
                        if (!bExpanded && checkString(aYM2) && checkString(aC2) && checkString(aP2)) {
                            LinearLayout_award2.setVisibility(View.GONE);
                        }
                        //불러온 모든 텍스트들을 TextInputEditText에 넣는다.
                        setAllTextInTextInputEditText();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"학력사항을 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //FirebaseFirestore의 collection("users").document(userID).collection("profiles").document("formOfCareer")에서 사용자가 프로필에 설정한 경력사항을 가져오고
            //EditText에 적용한다.
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
                        if (!bExpanded &&checkString(corpN2) && checkString(dep2) && checkString(corpEnt2) && checkString(corpRes2) &&
                                checkString(work2)) {
                            LinearLayout_corp2.setVisibility(View.GONE);
                        }

                        corpN3 = value.getString("corpN3");
                        corpEnt3 = value.getString("corpEnt3");
                        corpRes3 = value.getString("corpRes3");
                        dep3 = value.getString("dep3");
                        work3 = value.getString("work3");
                        if (!bExpanded &&checkString(corpN3) && checkString(dep3) && checkString(corpEnt3) && checkString(corpRes3) &&
                                checkString(work3)) {
                            LinearLayout_corp3.setVisibility(View.GONE);
                        }
                        //불러온 모든 텍스트들을 TextInputEditText에 넣는다.
                        setAllTextInTextInputEditText();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"학력사항을 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //불러온 모든 텍스트들을 TextInputEditText에 넣는다.
//            setAllTextInTextInputEditText();
        }
    }

    //DownloadManager.ACTION_DOWNLOAD_COMPLETE, RUNIMG_CMPLT, DOC_DWNL_CMPLT 이벤트를 받을 수 있도록 하는
    //broadcastReceiver를 등록한다.
    @Override
    protected void onStart() {
        super.onStart();

        updateUI();
    }
    //해당 액티비티가 포커스를 잃으면 broadcastReceiver등록을 해제한다.
    @Override
    protected void onStop() {
        super.onStop();

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
    class downloadFileThread extends Thread{
        public void run() {
            docName = intent.getStringExtra("docName");         //default이름
            fileName = editText_name.getText().toString().trim(); //사용자가 입력한 파일이름
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
    class checkingDownloadFileThread extends Thread{
        boolean downloadComplete = false;
        File f;

        public void run() {
            docName = intent.getStringExtra("docName");         //default이름
            fileName = editText_name.getText().toString().trim(); //사용자가 입력한 파일이름
            if(fileName.isEmpty()) {
                Log.d(TAG, "run/absolutePath : "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/"+docName+".docx");
            }
            else {
                Log.d(TAG, "run/absolutePath : " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName + ".docx");
            }
            //파일 인스턴스를 생성할 수 있으면 반복문을 빠져나온다.
            while(!downloadComplete) {
                try {
                    if(fileName.isEmpty()) {
                        f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/"+docName+".docx");
                    }
                    else{
                        f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName + ".docx");
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
    //임시파일을 다운한다.
    class downloadTempFileThread extends Thread{

        @Override
        public void run() {
            handler2.post(new Runnable() { //progressBar를 보인다.
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
            docName = intent.getStringExtra("docName");             //default이름
            fileName = editText_name.getText().toString().trim();     //사용자가 입력한 파일이름
            Log.d(TAG, "downloadTempFileThread: docName: "+docName+", fileName: "+fileName);

            if (checkString(fileName)) {
                downloadEP = new DownloadEP(getApplicationContext());
                downloadEP.download_with_modify(docName, docName);
            } else {
                downloadEP = new DownloadEP(getApplicationContext());
                downloadEP.download_with_modify(fileName, docName);
            }
        }
    }
    //임시파일 다운로드가 끝나면 텍스트와 프로필 이미지를 문서 내에 대체시킨다.
    class createResultThread extends Thread{
        boolean downloadComplete = false;
        File f;
        File profileImgFile;

        public void run() {
            docName = intent.getStringExtra("docName");         //default이름
            fileName = editText_name.getText().toString().trim(); //사용자가 입력한 파일이름

            if(fileName.isEmpty()) {
                Log.d(TAG, "run/absolutePath : "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/."+docName+".docx");
            }
            else {
                Log.d(TAG, "run/absolutePath : " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName + ".docx");
            }
            //파일 인스턴스를 생성할 수 있으면 반복문을 빠져나온다.
            while(!downloadComplete) {
                try {
                    if(fileName.isEmpty()) {
                        f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/."+docName+".docx");
                        profileImgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");
                    }
                    else{
                        f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName + ".docx");
                        profileImgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");
                    }
                    if(f.exists()&& !f.isDirectory() && profileImgFile.exists()&& !profileImgFile.isDirectory()){
                        downloadComplete = true;
                    }
                }catch (NullPointerException e){}
                try {
                    Log.d(TAG, "DDING DDONG");
                    Thread.sleep(500);
                } catch (Exception e) {}
            }

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
                    InputStream is = null;
                    Document document = null;
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

                    if(docNum == 0){
                        builder.insertImage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg",
                                RelativeHorizontalPosition.MARGIN,
                                360,
                                RelativeVerticalPosition.MARGIN,
                                20,
                                110,
                                150,
                                WrapType.SQUARE);
                    }
                    else if(docNum == 1){
                        builder.insertImage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg",
                                RelativeHorizontalPosition.MARGIN,
                                25,
                                RelativeVerticalPosition.MARGIN,
                                80,
                                110,
                                150,
                                WrapType.SQUARE);
                    }

                    //TextInputEditText로부터 텍스트를 불러오고, 클래스 변수에 넣는다.
                    getAllTextFromTextInputEditText();
                    Range range = document.getRange();

                    if(name.isEmpty()) {
                        range.replace("name","홍길동", new FindReplaceOptions());
                    } else{range.replace("name",name, new FindReplaceOptions());}
                    if(engName.isEmpty()) {
                        range.replace("engN","Hong Gil Dong", new FindReplaceOptions());
                    } else{range.replace("engN",engName, new FindReplaceOptions());}
                    if(chName.isEmpty()) {
                        range.replace("chN","", new FindReplaceOptions());}
                    else{range.replace("chN",chName, new FindReplaceOptions());}
                    if(rrn.isEmpty()) {
                        range.replace("rrn","", new FindReplaceOptions());}
                    else{range.replace("rrn",rrn, new FindReplaceOptions());}
                    if(age.isEmpty()) {
                        range.replace("age","", new FindReplaceOptions());
                    } else{range.replace("age",age, new FindReplaceOptions());}
                    if(phoneNum.isEmpty()) {
                        range.replace("phoneNum","", new FindReplaceOptions());
                    } else{range.replace("phoneNum",phoneNum, new FindReplaceOptions());}
                    if(email.isEmpty()) {
                        range.replace("name","", new FindReplaceOptions());
                    } else{range.replace("email",email, new FindReplaceOptions());}
                    if(addr.isEmpty()) {
                        range.replace("addr","", new FindReplaceOptions());
                    } else{range.replace("addr",addr, new FindReplaceOptions());}

                    if(hN.isEmpty()) {
                        range.replace("hN","", new FindReplaceOptions());
                    } else{range.replace("hN",hN, new FindReplaceOptions());}
                    if(hEnt.isEmpty()) {
                        range.replace("hEnt","", new FindReplaceOptions());
                    } else{range.replace("hEnt",hEnt, new FindReplaceOptions());}
                    if(hGrad.isEmpty()) {
                        range.replace("hGrad","", new FindReplaceOptions());
                    } else{range.replace("hGrad",hGrad, new FindReplaceOptions());}
                    if(hIfy.isEmpty()) {
                        range.replace("hIfy","", new FindReplaceOptions());
                    }else{range.replace("hIfy",hIfy, new FindReplaceOptions());}

                    if(uN.isEmpty()) {
                        range.replace("uN","", new FindReplaceOptions());
                    } else{range.replace("uN",uN, new FindReplaceOptions());}
                    if(uMaj.isEmpty()) {
                        range.replace("uMaj","", new FindReplaceOptions());
                    } else{range.replace("uMaj",uMaj, new FindReplaceOptions());}
                    if(uEnt.isEmpty()) {
                        range.replace("uEnt","", new FindReplaceOptions());
                    } else{range.replace("uEnt",uEnt, new FindReplaceOptions());}
                    if(uGrad.isEmpty()) {
                        range.replace("uGrad","", new FindReplaceOptions());
                    } else{range.replace("uGrad",uGrad, new FindReplaceOptions());}
                    if(uLoc.isEmpty()) {
                        range.replace("uLoc","", new FindReplaceOptions());
                    } else{range.replace("uLoc",uLoc, new FindReplaceOptions());}
                    if(uSco.isEmpty()) {
                        range.replace("uSco","", new FindReplaceOptions());
                    } else{range.replace("uSco",uSco, new FindReplaceOptions());}
                    if(uIfy.isEmpty()) {
                        range.replace("uIfy","", new FindReplaceOptions());
                    } else{range.replace("uIfy",uIfy, new FindReplaceOptions());}

                    if(mN.isEmpty()) {
                        range.replace("mN","", new FindReplaceOptions());
                    } else{range.replace("mN",mN+"석사", new FindReplaceOptions());}
                    if(mMaj.isEmpty()) {
                        range.replace("mMaj","", new FindReplaceOptions());
                    } else{range.replace("mMaj",mMaj, new FindReplaceOptions());}
                    if(mEnt.isEmpty()) {
                        range.replace("mEnt","", new FindReplaceOptions());
                    } else{range.replace("mEnt",mEnt, new FindReplaceOptions());}
                    if(mGrad.isEmpty()) {
                        range.replace("mGrad","", new FindReplaceOptions());
                    } else{range.replace("mGrad",mGrad, new FindReplaceOptions());}
                    if(mLoc.isEmpty()) {
                        range.replace("mLoc","", new FindReplaceOptions());
                    } else{range.replace("mLoc",mGrad, new FindReplaceOptions());}
                    if(uSco.isEmpty()) {
                        range.replace("mSco","", new FindReplaceOptions());
                    } else{range.replace("mSco",mSco, new FindReplaceOptions());}
                    if(mIfy.isEmpty()) {
                        range.replace("mIfy","", new FindReplaceOptions());
                    } else{range.replace("mIfy",mIfy, new FindReplaceOptions());}

                    if(corpN1.isEmpty()) {
                        range.replace("corpN1","", new FindReplaceOptions());
                    } else{range.replace("corpN1",corpN1, new FindReplaceOptions());}
                    if(dep1.isEmpty()) {
                        range.replace("dep1","", new FindReplaceOptions());
                    } else{range.replace("dep1",dep1, new FindReplaceOptions());}
                    if(corpEnt1.isEmpty()) {
                        range.replace("corpEnt1","", new FindReplaceOptions());
                    } else{range.replace("corpEnt1",corpEnt1, new FindReplaceOptions());}
                    if(corpRes1.isEmpty()) {
                        range.replace("corpRes1","", new FindReplaceOptions());
                    } else{range.replace("corpRes1",corpRes1, new FindReplaceOptions());}
                    if(work1.isEmpty()) {
                        range.replace("work1","", new FindReplaceOptions());
                    } else{range.replace("work1",work1, new FindReplaceOptions());}

                    if(corpN2.isEmpty()) {
                        range.replace("corpN2","", new FindReplaceOptions());
                    } else{range.replace("corpN2",corpN2, new FindReplaceOptions());}
                    if(dep2.isEmpty()) {
                        range.replace("dep2","", new FindReplaceOptions());
                    } else{range.replace("dep2",dep2, new FindReplaceOptions());}
                    if(corpEnt2.isEmpty()) {
                        range.replace("corpEnt2","", new FindReplaceOptions());
                    } else{range.replace("corpEnt2",corpEnt2, new FindReplaceOptions());}
                    if(corpRes2.isEmpty()) {
                        range.replace("corpRes2","", new FindReplaceOptions());
                    } else{range.replace("corpRes2",corpRes2, new FindReplaceOptions());}
                    if(work2.isEmpty()) {
                        range.replace("work2","", new FindReplaceOptions());
                    } else{range.replace("work2",work2, new FindReplaceOptions());}

                    if(corpN3.isEmpty()) {
                        range.replace("corpN3","", new FindReplaceOptions());
                    } else{range.replace("corpN3",corpN3, new FindReplaceOptions());}
                    if(dep3.isEmpty()) {
                        range.replace("dep3","", new FindReplaceOptions());
                    } else{range.replace("dep3",dep3, new FindReplaceOptions());}
                    if(corpEnt3.isEmpty()) {
                        range.replace("corpEnt3","", new FindReplaceOptions());
                    } else{range.replace("corpEnt3",corpEnt3, new FindReplaceOptions());}
                    if(corpRes3.isEmpty()) {
                        range.replace("corpRes3","", new FindReplaceOptions());
                    } else{range.replace("corpRes3",corpRes3, new FindReplaceOptions());}
                    if(work3.isEmpty()) {
                        range.replace("work3","", new FindReplaceOptions());
                    } else{range.replace("work3",work3, new FindReplaceOptions());}


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
            }
        }
    }


    //텍스트, 프로필 이미지 대체가 끝났는지 체크한다.
    class checkingResultThread extends Thread{
        boolean downloadComplete = false;

        File f;
        File imagePicture;

        public void run() {
            docName = intent.getStringExtra("docName");             //default이름
            fileName = editText_name.getText().toString().trim();     //사용자가 입력한 파일이름

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
    //프로필 이미지를 다운로드한다.
    class downloadProfileImgThread extends Thread{
        @Override
        public void run() {
                downloadEP = new DownloadEP(getApplicationContext());
                downloadEP.download_picture();
            }
    }
    //프로필이미지가 존재하는지 체크한다.
    class checkingProfileImgThread extends Thread{
        boolean ifProfileImgExists = false;
        File f;
        @Override
        public void run() {
            while(!ifProfileImgExists) {
                try {
                    f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");
                    if(f.exists()&& !f.isDirectory()){
                        ifProfileImgExists = true;
                    }
                    Log.d(TAG, "run/if_ProfileImgExists : "+f.exists());
                }catch (NullPointerException e){}
                try {
                    Log.d(TAG, "DDING DDONG_ProfileImgExists");
                    Thread.sleep(500);
                } catch (Exception e) {}
            }
        }
    }
    private void setAllTextInTextInputEditText(){
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

        TextInputEditText_licYM1.setText(licYM1);
        TextInputEditText_licC1.setText(licC1);
        TextInputEditText_licC1.setText(licC1);
        TextInputEditText_licP1.setText(licP1);

        TextInputEditText_licYM2.setText(licYM2);
        TextInputEditText_licC2.setText(licC2);
        TextInputEditText_licG2.setText(licG2);
        TextInputEditText_licP2.setText(licP2);

        TextInputEditText_aYM1.setText(aYM1);
        TextInputEditText_aC1.setText(aC1);
        TextInputEditText_aP1.setText(aP1);

        TextInputEditText_aYM2.setText(aYM2);
        TextInputEditText_aC2.setText(aC2);
        TextInputEditText_aP2.setText(aP2);

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

    private void getAllTextFromTextInputEditText(){
        //TextInputEditText_name, TextInputEditText_email, TextInputEditText_phoneNum,
        //TextInputEditText_addr, TextInputEditText_engName, TextInputEditText_chName,
        //TextInputEditText_rrn, TextInputEditText_age, TextInputEditText_num;

        //TextInputEditText_hN, TextInputEditText_hEnt, TextInputEditText_hGrad , TextInputEditText_hIfy
        //TextInputEditText_uN, TextInputEditText_uMaj, TextInputEditText_uEnt , TextInputEditText_uGrad, TextInputEditText_uLoc, TextInputEditText_uSco, TextInputEditText_uIfy,
        //TextInputEditText_mN, TextInputEditText_mEnt, TextInputEditText_mLoc, TextInputEditText_mSco, TextInputEditText_mGrad, TextInputEditText_mIfy, TextInputEditText_mMaj;

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

        //TextInputEditText_licYM1, TextInputEditText_licC1, TextInputEditText_licG1, TextInputEditText_licP1,
        //TextInputEditText_licYM2, TextInputEditText_licC2, TextInputEditText_licG2, TextInputEditText_licP2,
        //TextInputEditText_aYM1 ,TextInputEditText_aC1, TextInputEditText_aP1,
        //TextInputEditText_aYM2 ,TextInputEditText_aC2, TextInputEditText_aP2,

        licYM1 = TextInputEditText_licYM1.getText().toString().trim();
        licC1 = TextInputEditText_licC1.getText().toString().trim();
        licG1 = TextInputEditText_licG1.getText().toString().trim();
        licP1 = TextInputEditText_licP1.getText().toString().trim();

        licYM2 = TextInputEditText_licYM2.getText().toString().trim();
        licC2 = TextInputEditText_licC2.getText().toString().trim();
        licG2 = TextInputEditText_licG2.getText().toString().trim();
        licP2 = TextInputEditText_licP2.getText().toString().trim();

        aYM1 = TextInputEditText_aYM1.getText().toString().trim();
        aC1 = TextInputEditText_aC1.getText().toString().trim();
        aP1 = TextInputEditText_aP1.getText().toString().trim();

        aYM2 = TextInputEditText_aYM2.getText().toString().trim();
        aC2 = TextInputEditText_aC2.getText().toString().trim();
        aP2 = TextInputEditText_aP2.getText().toString().trim();
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
    boolean checkString(String str) {
        return str == null || str.length() == 0;
    }
}
