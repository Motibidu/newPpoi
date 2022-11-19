//package org.dstadler.poiandroidtest.newpoi.gnrtDoc;
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.app.DownloadManager;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.provider.Settings;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.PopupMenu;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.aspose.words.Document;
//import com.aspose.words.DocumentBuilder;
//import com.aspose.words.FindReplaceOptions;
//import com.aspose.words.IReplacingCallback;
//import com.aspose.words.RelativeHorizontalPosition;
//import com.aspose.words.RelativeVerticalPosition;
//import com.aspose.words.WrapType;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.RequestOptions;
//
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.material.textfield.TextInputEditText;
//
//import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//
//import org.dstadler.poiandroidtest.newpoi.R;
//import org.dstadler.poiandroidtest.newpoi.cls.CustomXWPFDocument;
//import org.dstadler.poiandroidtest.newpoi.cls.DownloadEP;
//import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;
//import org.dstadler.poiandroidtest.newpoi.cls.RoundedCornersTransformation;
//import org.dstadler.poiandroidtest.newpoi.profile.ProfileScrnActivity;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//public class preEmploymentContractActivity extends AppCompatActivity {
//    private final String TAG = EmploymentContractActivity.class.getName();//
//    //==================================================commons===============================================//
//    //final vars
//    private final String RUNIMG_CMPLT = "org.dstadler.poiandroidtest.newpoi.RUNIMG_CMPLT";
//    private final String DOC_DWNL_CMPLT = "org.dstadler.poiandroidtest.newpoi.DOC_DWNL_CMPLT";
//    private static final int MY_PERMISSION_STORAGE = 1111;
//
//    //image arguments
//    public static int sCorner = 80;
//    public static int sMargin = 1;
//    public static int sBorder = 0;
//
//    // vars
//    private boolean bExpanded;
//    private String docName;
//    private String pagePath0, pagePath1, pagePath2;
//    private Uri imgUri0, imgUri1, imgUri2;
//    private String fileName, fileNameWithExt;
//    private int docNum;
//    private String userID;
//    private File  docFile;
//    private Map<String, Object> user;
//    private Handler handler1, handler2;
//
//
//    //widgets
//    private ImageButton backBtn, imageBtn_more;
//    private Button btn_download, btn_create;
//    private ProgressBar progressBar;
//    private EditText editText_name;
//    private ImageView image_main0, image_main1, image_main2;
//
//    //contents
//    private Context mContext;
//    private AppCompatActivity mActivity;
//
//
//    //firebase
//    private StorageReference storageReference;
//    private FirebaseStorage fStorage;
//    private FirebaseFirestore fStore;
//    private FirebaseAuth mAuth;
//    private DocumentReference documentReference;
//
//    private Intent intent, moveProfile;
//    private DownloadEP downloadEP;
//    //==================================================commons===============================================//
//
//    /*(사업주 이름, 사업주 주소, 사업주 연락처, 사업체명)
//    =(business owner name, business owner address, business owner contact information, business name)
//    =(bON, bOA, bOCI, bN)*/
//
//    /*(근로자 이름, 근로자 주소, 근로자 연락처)
//    =(worker name, worker address, worker contact information)
//    =(wN, wA, wCI)*/
//
//    /*(계약조건, 근로계약기간, 근무장소, 업무내용, 소정근로시간, 근무일,  임금, 사회보험적용여부)
//     =(contract conditions, Labor contract period, place of work, Business information, fixed working hours, working day, wage, Social insurance coverage)
//     =(cC, lCP, pOW, bI, fWH, wD, wD, w, sIC)*/
//
//
//    //String
//
//    //사업주 정보
//    private String bON, bOA, bOCI, bN;
//
//    //근로자 정보
//    private String
//            wN, wA, wCI;
//
//    //계약정보
//    private String
//            cC, lCP, pOW, bI, fWH, wD, w, sIC;
//
//
//    //TextinputEditText
//    //사업주 정보
//    private TextInputEditText
//            TextInputEditText_bON, TextInputEditText_bOA, TextInputEditText_bOCI, TextInputEditText_bN;
//    //근로자 정보
//    private TextInputEditText
//            TextInputEditText_wN, TextInputEditText_wA, TextInputEditText_wCI, TextInputEditText_cC;
//    //계약정보
//    private TextInputEditText
//            TextInputEditText_lCP, TextInputEditText_pOW, TextInputEditText_bI, TextInputEditText_fWH,  TextInputEditText_wD, TextInputEditText_w, TextInputEditText_sIC;
//
//    //TextinputLayout
//    //사업주 정보
//    private TextInputLayout
//            TextInputLayout_bON, TextInputLayout_bOA, TextInputLayout_bOCI, TextInputLayout_bN;
//    //근로자 정보
//    private TextInputLayout
//            TextInputLayout_wN, TextInputLayout_wA, TextInputLayout_wCI, TextInputLayout_cC;
//    //계약정보
//    private TextInputLayout
//            TextInputLayout_lCP, TextInputLayout_pOW, TextInputLayout_bI, TextInputLayout_fWH,  TextInputLayout_wD, TextInputLayout_w, TextInputLayout_sIC;
//
//
//    private RelativeLayout RelativeLayout_bO, RelativeLayout_w, RelativeLayout_cI;
//
//
//
//
//
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_career_description); //
//        //=================================common===================================//
//        //Content
//        mContext = getApplicationContext();
//        mActivity = this;
//
//        //handler1
//        handler1 = new Handler();
//        handler2 = new Handler();
//
//        //firebase
//        mAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
//
//        //뒤로가기 버튼
//        backBtn = findViewById(R.id.backBtn);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        //widgets
//        progressBar = findViewById(R.id.progressBar);
//
//
//
//        image_main0 = findViewById(R.id.image_main0);
//        image_main1 = findViewById(R.id.image_main1);
//        image_main2 = findViewById(R.id.image_main2);
//
//        btn_download = findViewById(R.id.btn_download);
//        btn_create = findViewById(R.id.btn_create);
//        imageBtn_more = findViewById(R.id.imageBtn_more);
//
//
//        editText_name = findViewById(R.id.editText_name);
//
//
//        //var
//        bExpanded = false;
//
//        //첫번째, 두번째, 세번째 페이지를 설정한다.
//        intent = getIntent();
//
//        pagePath0 = intent.getStringExtra("pagePath0");
//        pagePath1 = intent.getStringExtra("pagePath1");
//        pagePath2 = intent.getStringExtra("pagePath2");
//
//        docName = intent.getStringExtra("docName");
//
//        //pagePath0에 값이 있을 때 첫번째 mainImageView에 rounded처리된 imgUri0의 이미지를 설정한다.
//        if(!checkString(pagePath0)) {
//            imgUri0 = Uri.parse(pagePath0);
//            Glide.with(this).load(imgUri0)
//                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
//                    .apply(RequestOptions.skipMemoryCacheOf(true))
//                    .apply(RequestOptions.bitmapTransform(
//                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(image_main0);
//        }
//        //pagePath1에 값이 있을 때 첫번째 mainImageView에 rounded처리된 imgUri1의 이미지를 설정한다.
//        if(!checkString(pagePath1)) {
//            imgUri1 = Uri.parse(pagePath1);
//            Glide.with(this).load(imgUri1)
//                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
//                    .apply(RequestOptions.skipMemoryCacheOf(true))
//                    .apply(RequestOptions.bitmapTransform(
//                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(image_main1);
//        }
//        //pagePath2에 값이 있을 때 첫번째 mainImageView에 rounded처리된 imgUri2의 이미지를 설정한다.
//        if(!checkString(pagePath2)) {
//            imgUri2 = Uri.parse(pagePath2);
//            Glide.with(this).load(imgUri2)
//                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
//                    .apply(RequestOptions.skipMemoryCacheOf(true))
//                    .apply(RequestOptions.bitmapTransform(
//                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(image_main2);
//        }
//
//
//        //양식만 저장, 생성 버튼, 더보기 버튼
//        btn_download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkPermission();
//
//                //start downloading template, start progressbar
//                downloadTmpltThread downloadTmpltThread = new downloadTmpltThread();
//                downloadTmpltThread.start();
//
//                //if downloading is finished,stop progressbar
//                checkingDownloadThread checkingDownloadThread = new checkingDownloadThread();
//                checkingDownloadThread.start();
//            }
//        });
//        btn_create.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkPermission();
//
//                //if user is logged off
//                if (mAuth.getCurrentUser() == null) {
//                    Toast.makeText(mContext, "로그인 해주세요.", Toast.LENGTH_SHORT).show();
//                }else{
//                    //start template processing, start progressbar
//                    tmpltProcessThread tmpltProcessThread = new tmpltProcessThread();
//                    tmpltProcessThread.start();
//
//                    //if processing is finished,stop progressbar
//                    checkingProcessThread checkingProcessThread = new checkingProcessThread();
//                    checkingProcessThread.start();
//                }
//            }
//        });
//        imageBtn_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopup(imageBtn_more);
//            }
//        });
//
//
//
//        //====================================================================================//
//
//        //각 양식마다 필요없는 사항은 보이지 않도록 한다.
//        docNum = intent.getIntExtra("docNum", 0);
//        if(docNum == 0){
//            Log.d(TAG, "onCreate/docName : "+docName);
////            TextInputLayout_name.setVisibility(View.GONE);
//            TextInputLayout_engName.setVisibility(View.GONE);
//            TextInputLayout_chName.setVisibility(View.GONE);
//            TextInputLayout_rrn.setVisibility(View.GONE);
//            TextInputLayout_age.setVisibility(View.GONE);
////            TextInputLayout_phoneNum.setVisibility(View.GONE);
//            TextInputLayout_num.setVisibility(View.GONE);
////            TextInputLayout_email.setVisibility(View.GONE);
////            TextInputLayout_addr.setVisibility(View.GONE);
//        }
//        else if(docNum == 1){
//            Log.d(TAG, "onCreate/docName : "+docName);
////            TextInputLayout_name.setVisibility(View.GONE);
////            TextInputLayout_engName.setVisibility(View.GONE);
//            TextInputLayout_chName.setVisibility(View.GONE);
////            TextInputLayout_rrn.setVisibility(View.GONE);
////            TextInputLayout_age.setVisibility(View.GONE);
////            TextInputLayout_phoneNum.setVisibility(View.GONE);
//            TextInputLayout_num.setVisibility(View.GONE);
////            TextInputLayout_email.setVisibility(View.GONE);
////            TextInputLayout_addr.setVisibility(View.GONE);
//        }
//        else if(docNum == 2){
//            Log.d(TAG, "onCreate/docName : "+docName);
////            TextInputLayout_name.setVisibility(View.GONE);
////            TextInputLayout_engName.setVisibility(View.GONE);
////            TextInputLayout_chName.setVisibility(View.GONE);
////            TextInputLayout_rrn.setVisibility(View.GONE);
////            TextInputLayout_age.setVisibility(View.GONE);
////            TextInputLayout_phoneNum.setVisibility(View.GONE);
////            TextInputLayout_num.setVisibility(View.GONE);
////            TextInputLayout_email.setVisibility(View.GONE);
////            TextInputLayout_addr.setVisibility(View.GONE);
//        }
//        //====================================================================================//
//
//        //TextInputLayout
//        TextInputLayout_bON = findViewById(R.id.TextInputLayout_bON);
//        TextInputLayout_bOA = findViewById(R.id.TextInputLayout_bOA);
//        TextInputLayout_bOCI = findViewById(R.id.TextInputLayout_bOCI);
//        TextInputLayout_bN = findViewById(R.id.TextInputLayout_bN);
//        TextInputLayout_wN = findViewById(R.id.TextInputLayout_wN);
//        TextInputLayout_wA = findViewById(R.id.TextInputLayout_wA);
//        TextInputLayout_wCI = findViewById(R.id.TextInputLayout_wCI);
//        TextInputLayout_cC = findViewById(R.id.TextInputLayout_cC);
//        TextInputLayout_lCP = findViewById(R.id.TextInputLayout_lCP);
//        TextInputLayout_pOW = findViewById(R.id.TextInputLayout_pOW);
//        TextInputLayout_bI = findViewById(R.id.TextInputLayout_bI);
//        TextInputLayout_fWH = findViewById(R.id.TextInputLayout_fWH);
//        TextInputLayout_wD = findViewById(R.id.TextInputLayout_wD);
//        TextInputLayout_wD = findViewById(R.id.TextInputLayout_wD);
//        TextInputLayout_w = findViewById(R.id.TextInputLayout_w);
//        TextInputLayout_sIC = findViewById(R.id.TextInputLayout_sIC);
//
//        //TextInputEditText
//        TextInputEditText_bON = findViewById(R.id.TextInputEditText_bON);
//        TextInputEditText_bOA = findViewById(R.id.TextInputEditText_bOA);
//        TextInputEditText_bOCI = findViewById(R.id.TextInputEditText_bOCI);
//        TextInputEditText_bN = findViewById(R.id.TextInputEditText_bN);
//        TextInputEditText_wN = findViewById(R.id.TextInputEditText_wN);
//        TextInputEditText_wA = findViewById(R.id.TextInputEditText_wA);
//        TextInputEditText_wCI = findViewById(R.id.TextInputEditText_wCI);
//        TextInputEditText_cC = findViewById(R.id.TextInputEditText_cC);
//        TextInputEditText_lCP = findViewById(R.id.TextInputEditText_lCP);
//        TextInputEditText_pOW = findViewById(R.id.TextInputEditText_pOW);
//        TextInputEditText_bI = findViewById(R.id.TextInputEditText_bI);
//        TextInputEditText_fWH = findViewById(R.id.TextInputEditText_fWH);
//        TextInputEditText_wD = findViewById(R.id.TextInputEditText_wD);
//        TextInputEditText_wD = findViewById(R.id.TextInputEditText_wD);
//        TextInputEditText_w = findViewById(R.id.TextInputEditText_w);
//        TextInputEditText_sIC = findViewById(R.id.TextInputEditText_sIC);
//
//
//    }
//
//
//    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            long cmpltDwnlID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//            long doc_dwnlID = PreferenceManager.getLong(mContext, "doc_dwnlID");
//            long img_dwnlID = PreferenceManager.getLong(mContext, "img_dwnlID");
//
//            InputStream is = null;
//            Document document = null;
//
//            //다운로드 완료, 마지막 다운이 문서일때
//            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (doc_dwnlID == cmpltDwnlID)) {
////                imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");
//                downloadEP.download_picture();
//            }
//
//            //다운로드 완료, 마지막 다운이 프로필사진일 때
//            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (img_dwnlID == cmpltDwnlID)) {
//                fileNameWithExt = fileName+".docx";
//                Log.d(TAG, "onReceive/fileNameWithExt : "+fileNameWithExt);
//                docFile = new File(Environment.getExternalStoragePublicDirectory
//                        (Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileNameWithExt);
//                //임시파일이 존재할 때
//                if (docFile.exists()) {
//                    try {
//                        //(name, engN, chN) = (이름, 영어이름, 한자이름)
//                        //(rrn, age, phoneNum, email, addr) = (주민번호, 나이, 전화번호, 이메일, 주소)
//
//                        //(hN, hEnt, hGrad, hIfy) = (고등학교 이름, 고등학교 입학년월, 고등학교 졸업년월, 졸업구분)
//                        //(uN, uMaj, uEnt, uGrad, uLoc, uSco uIfy) = (대학교 이름, 대학교 전공, 대학교 입학년월, 대학교 졸업년월, 대학교 소재지, 대학교 학점, 대학교 졸업구분)
//                        //(mN, mEnt, mGrad, mIfy) = (대학교 이름, 대학교 입학년월, 대학교 졸업년월, 졸업구분)
//
//                        //(corpN, dep, corpEnt, corpRes, work) = (회사이름, 담당부서, 입사년월, 퇴사년월, 담당업무)
//
//                        try {
//                            is = new FileInputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileNameWithExt);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            document = new Document(is);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        DocumentBuilder builder = new DocumentBuilder(document);
//
//                        if(docNum == 0){
//                            builder.insertImage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg",
//                                    RelativeHorizontalPosition.MARGIN,
//                                    360,
//                                    RelativeVerticalPosition.MARGIN,
//                                    20,
//                                    110,
//                                    150,
//                                    WrapType.SQUARE);
//                        }
//                        else if(docNum == 1){
//                            builder.insertImage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg",
//                                    RelativeHorizontalPosition.MARGIN,
//                                    25,
//                                    RelativeVerticalPosition.MARGIN,
//                                    80,
//                                    110,
//                                    150,
//                                    WrapType.SQUARE);
//                        }
//
//                        //TextInputEditText로부터 텍스트를 불러오고, 클래스 변수에 넣는다.
//                        getAllTextFromTextInputEditText();
//
//                        //워드 파일 내 텍스트를 대체한다.
//                        if(name.isEmpty()) {
//                            document.getRange().replace("name","홍길동", new FindReplaceOptions());
//                        } else{document.getRange().replace("name",name, new FindReplaceOptions());}
//                        if(engName.isEmpty()) {
//                            document.getRange().replace("engN","Hong Gil Dong", new FindReplaceOptions());
//                        } else{document.getRange().replace("engN",engName, new FindReplaceOptions());}
//                        if(chName.isEmpty()) {
//                            document.getRange().replace("chN","", new FindReplaceOptions());}
//                        else{document.getRange().replace("chN",chName, new FindReplaceOptions());}
//                        if(rrn.isEmpty()) {
//                            document.getRange().replace("rrn","", new FindReplaceOptions());}
//                        else{document.getRange().replace("rrn",rrn, new FindReplaceOptions());}
//                        if(age.isEmpty()) {
//                            document.getRange().replace("age","", new FindReplaceOptions());
//                        } else{document.getRange().replace("age",age, new FindReplaceOptions());}
//                        if(phoneNum.isEmpty()) {
//                            document.getRange().replace("phoneNum","", new FindReplaceOptions());
//                        } else{document.getRange().replace("phoneNum",phoneNum, new FindReplaceOptions());}
//                        if(email.isEmpty()) {
//                            document.getRange().replace("name","", new FindReplaceOptions());
//                        } else{document.getRange().replace("email",email, new FindReplaceOptions());}
//                        if(addr.isEmpty()) {
//                            document.getRange().replace("addr","", new FindReplaceOptions());
//                        } else{document.getRange().replace("addr",addr, new FindReplaceOptions());}
//
//                        if(hN.isEmpty()) {
//                            document.getRange().replace("hN","", new FindReplaceOptions());
//                        } else{document.getRange().replace("hN",hN, new FindReplaceOptions());}
//                        if(hEnt.isEmpty()) {
//                            document.getRange().replace("hEnt","", new FindReplaceOptions());
//                        } else{document.getRange().replace("hEnt",hEnt, new FindReplaceOptions());}
//                        if(hGrad.isEmpty()) {
//                            document.getRange().replace("hGrad","", new FindReplaceOptions());
//                        } else{document.getRange().replace("hGrad",hGrad, new FindReplaceOptions());}
//                        if(hIfy.isEmpty()) {
//                            document.getRange().replace("hIfy","", new FindReplaceOptions());
//                        }else{document.getRange().replace("hIfy",hIfy, new FindReplaceOptions());}
//
//                        if(uN.isEmpty()) {
//                            document.getRange().replace("uN","", new FindReplaceOptions());
//                        } else{document.getRange().replace("uN",uN, new FindReplaceOptions());}
//                        if(uMaj.isEmpty()) {
//                            document.getRange().replace("uMaj","", new FindReplaceOptions());
//                        } else{document.getRange().replace("uMaj",uMaj, new FindReplaceOptions());}
//                        if(uEnt.isEmpty()) {
//                            document.getRange().replace("uEnt","", new FindReplaceOptions());
//                        } else{document.getRange().replace("uEnt",uEnt, new FindReplaceOptions());}
//                        if(uGrad.isEmpty()) {
//                            document.getRange().replace("uGrad","", new FindReplaceOptions());
//                        } else{document.getRange().replace("uGrad",uGrad, new FindReplaceOptions());}
//                        if(uLoc.isEmpty()) {
//                            document.getRange().replace("uLoc","", new FindReplaceOptions());
//                        } else{document.getRange().replace("uLoc",uLoc, new FindReplaceOptions());}
//                        if(uSco.isEmpty()) {
//                            document.getRange().replace("uSco","", new FindReplaceOptions());
//                        } else{document.getRange().replace("uSco",uSco, new FindReplaceOptions());}
//                        if(uIfy.isEmpty()) {
//                            document.getRange().replace("uIfy","", new FindReplaceOptions());
//                        } else{document.getRange().replace("uIfy",uIfy, new FindReplaceOptions());}
//
//                        if(mN.isEmpty()) {
//                            document.getRange().replace("mN","", new FindReplaceOptions());
//                        } else{document.getRange().replace("mN",mN+"석사", new FindReplaceOptions());}
//                        if(mMaj.isEmpty()) {
//                            document.getRange().replace("mMaj","", new FindReplaceOptions());
//                        } else{document.getRange().replace("mMaj",mMaj, new FindReplaceOptions());}
//                        if(mEnt.isEmpty()) {
//                            document.getRange().replace("mEnt","", new FindReplaceOptions());
//                        } else{document.getRange().replace("mEnt",mEnt, new FindReplaceOptions());}
//                        if(mGrad.isEmpty()) {
//                            document.getRange().replace("mGrad","", new FindReplaceOptions());
//                        } else{document.getRange().replace("mGrad",mGrad, new FindReplaceOptions());}
//                        if(mLoc.isEmpty()) {
//                            document.getRange().replace("mLoc","", new FindReplaceOptions());
//                        } else{document.getRange().replace("mLoc",mGrad, new FindReplaceOptions());}
//                        if(uSco.isEmpty()) {
//                            document.getRange().replace("mSco","", new FindReplaceOptions());
//                        } else{document.getRange().replace("mSco",mSco, new FindReplaceOptions());}
//                        if(mIfy.isEmpty()) {
//                            document.getRange().replace("mIfy","", new FindReplaceOptions());
//                        } else{document.getRange().replace("mIfy",mIfy, new FindReplaceOptions());}
//
//                        if(corpN1.isEmpty()) {
//                            document.getRange().replace("corpN1","", new FindReplaceOptions());
//                        } else{document.getRange().replace("corpN1",corpN1, new FindReplaceOptions());}
//                        if(dep1.isEmpty()) {
//                            document.getRange().replace("dep1","", new FindReplaceOptions());
//                        } else{document.getRange().replace("dep1",dep1, new FindReplaceOptions());}
//                        if(corpEnt1.isEmpty()) {
//                            document.getRange().replace("corpEnt1","", new FindReplaceOptions());
//                        } else{document.getRange().replace("corpEnt1",corpEnt1, new FindReplaceOptions());}
//                        if(corpRes1.isEmpty()) {
//                            document.getRange().replace("corpRes1","", new FindReplaceOptions());
//                        } else{document.getRange().replace("corpRes1",corpRes1, new FindReplaceOptions());}
//                        if(work1.isEmpty()) {
//                            document.getRange().replace("work1","", new FindReplaceOptions());
//                        } else{document.getRange().replace("work1",work1, new FindReplaceOptions());}
//
//                        if(corpN2.isEmpty()) {
//                            document.getRange().replace("corpN2","", new FindReplaceOptions());
//                        } else{document.getRange().replace("corpN2",corpN2, new FindReplaceOptions());}
//                        if(dep2.isEmpty()) {
//                            document.getRange().replace("dep2","", new FindReplaceOptions());
//                        } else{document.getRange().replace("dep2",dep2, new FindReplaceOptions());}
//                        if(corpEnt2.isEmpty()) {
//                            document.getRange().replace("corpEnt2","", new FindReplaceOptions());
//                        } else{document.getRange().replace("corpEnt2",corpEnt2, new FindReplaceOptions());}
//                        if(corpRes2.isEmpty()) {
//                            document.getRange().replace("corpRes2","", new FindReplaceOptions());
//                        } else{document.getRange().replace("corpRes2",corpRes2, new FindReplaceOptions());}
//                        if(work2.isEmpty()) {
//                            document.getRange().replace("work2","", new FindReplaceOptions());
//                        } else{document.getRange().replace("work2",work2, new FindReplaceOptions());}
//
//                        if(corpN3.isEmpty()) {
//                            document.getRange().replace("corpN3","", new FindReplaceOptions());
//                        } else{document.getRange().replace("corpN3",corpN3, new FindReplaceOptions());}
//                        if(dep3.isEmpty()) {
//                            document.getRange().replace("dep3","", new FindReplaceOptions());
//                        } else{document.getRange().replace("dep3",dep3, new FindReplaceOptions());}
//                        if(corpEnt3.isEmpty()) {
//                            document.getRange().replace("corpEnt3","", new FindReplaceOptions());
//                        } else{document.getRange().replace("corpEnt3",corpEnt3, new FindReplaceOptions());}
//                        if(corpRes3.isEmpty()) {
//                            document.getRange().replace("corpRes3","", new FindReplaceOptions());
//                        } else{document.getRange().replace("corpRes3",corpRes3, new FindReplaceOptions());}
//                        if(work3.isEmpty()) {
//                            document.getRange().replace("work3","", new FindReplaceOptions());
//                        } else{document.getRange().replace("work3",work3, new FindReplaceOptions());}
//
//                        document.save(Environment.getExternalStoragePublicDirectory
//                                (Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileNameWithExt);
//
//                        //임시파일을 삭제한다.
//                        docFile.delete();
//
//
//                        //CustomXWPFDocument클래스의 replace메소드는 워드 파일 내에 "${key}"를 value값으로 대체한다.
////                        CustomXWPFDocument c = new CustomXWPFDocument();
////                        c.replace(is,data,out);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(mContext, "No Document File!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    };
//
//    public void showPopup(View v) {
//        PopupMenu popupMenu = new PopupMenu(mContext, v);
//        popupMenu.inflate(R.menu.expanded_menu);
//        Menu menuOpts = popupMenu.getMenu();
//
//        if(bExpanded){
//            menuOpts.getItem(0).setTitle("숨기기");
//        }
//        else{
//            menuOpts.getItem(0).setTitle("펼치기");
//        }
//
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.expand:
//                        //bExpanded가 false일 때(숨겨져 있을 때) "펼치기"를 누르면
//                        //대학교, 대학원, 경력사항2, 경력사항3, 자격증2, 수상2를 디스플레이 한다.
////                        if(bExpanded) {
////                            university_enterYM = university_enterYM.getText().toString().trim();
////                            university_graYM = university_graYM.getText().toString().trim();
////                            university_graCls = university_graCls.getText().toString().trim();
////                            university_name = university_name.getText().toString().trim();
////                            university_major = university_major.getText().toString().trim();
////
////                            master_enterYM = master_enterYM.getText().toString().trim();
////                            master_graYM = master_graYM.getText().toString().trim();
////                            master_graCls = master_graCls.getText().toString().trim();
////                            master_name = master_name.getText().toString().trim();
////                            master_major = master_major.getText().toString().trim();
////                            master_graThe = master_graThe.getText().toString().trim();
////                            master_LAB = master_LAB.getText().toString().trim();
////
////                            corpN2 = formOfCareer2_name.getText().toString().trim();
////                            corpEnt2 = formOfCareer2_enterYM.getText().toString().trim();
////                            corpRes2 = formOfCareer2_resignYM.getText().toString().trim();
////                            dep2 = formOfCareer2_office.getText().toString().trim();
////                            work2 = formOfCareer2_task.getText().toString().trim();
////
////                            corpN3 = formOfCareer3_name.getText().toString().trim();
////                            corpEnt3 = formOfCareer3_enterYM.getText().toString().trim();
////                            corpRes3 = formOfCareer3_resignYM.getText().toString().trim();
////                            dep3 = formOfCareer3_office.getText().toString().trim();
////                            work3 = formOfCareer3_task.getText().toString().trim();
////
////                            license1_date = license1_date.getText().toString().trim();
////                            license1_cntnt = license1_cntnt.getText().toString().trim();
////                            license1_grade = license1_grade.getText().toString().trim();
////                            license1_publication = license1_publication.getText().toString().trim();
////
////                            license2_date = license2_date.getText().toString().trim();
////                            license2_cntnt = license2_cntnt.getText().toString().trim();
////                            license2_grade = license2_grade.getText().toString().trim();
////                            license2_publication = license2_publication.getText().toString().trim();
////
////                            award1_date = award1_date.getText().toString().trim();
////                            award1_cntnt = award1_cntnt.getText().toString().trim();
////                            award1_publication = award1_publication.getText().toString().trim();
////
////                            award2_date = award2_date.getText().toString().trim();
////                            award2_cntnt = award2_cntnt.getText().toString().trim();
////                            award2_publication = award2_publication.getText().toString().trim();
////
////                            PreferenceManager.setBoolean(mContext, "careerbExpanded", false);
////
////                            if (checkString(corpN2) && checkString(dep2) && checkString(corpEnt2) && checkString(corpRes2) &&
////                                    checkString(work2)) {
////                                formOfCareer2.setVisibility(View.GONE);
////                            }
////                            if (checkString(corpN3) && checkString(dep3) && checkString(corpEnt3) && checkString(corpRes3) &&
////                                    checkString(work3)) {
////                                formOfCareer3.setVisibility(View.GONE);
////                            }
////                            if (checkString(university_enterYM) && checkString(university_graYM) && checkString(university_graCls) && checkString(university_name) &&
////                                    checkString(university_major)) {
////                                university.setVisibility(View.GONE);
////                            }
////                            if (checkString(master_enterYM) && checkString(master_graYM) && checkString(master_graCls) && checkString(master_name) &&
////                                    checkString(master_major) && checkString(master_graThe) && checkString(master_LAB)) {
////                                master.setVisibility(View.GONE);
////
////                            }if (checkString(license1_date) && checkString(license1_cntnt) && checkString(license1_grade) && checkString(license1_publication)) {
////                                license1.setVisibility(View.GONE);
////                            }
////                            if (checkString(license2_date) && checkString(license2_cntnt) && checkString(license2_grade) && checkString(license2_publication)) {
////                                license2.setVisibility(View.GONE);
////                            }
////                            if (checkString(award1_date) && checkString(award1_cntnt) && checkString(award1_publication)) {
////                                award1.setVisibility(View.GONE);
////                            }
////                            if (checkString(award2_date) && checkString(award2_cntnt) && checkString(award2_publication)) {
////                                award2.setVisibility(View.GONE);
////                            }
////                            bExpanded = false;
////                            Toast.makeText(mContext, "기록되지 않은 항목이 숨겨 졌습니다.", Toast.LENGTH_SHORT).show();
////                        }
////                        //bExpanded가 true일 때(펼쳐져 있을 때) "숨기기"를 누르면
////                        //대학교, 대학원, 경력사항2, 경력사항3, 자격증2, 수상2의 모든 하위 항목을 검사하고
////                        //모든 하위 항목이 비어있는 항목은 숨긴다.
////                        else{
////                            PreferenceManager.setBoolean(mContext, "careerbExpanded", true);
////                            university.setVisibility(View.VISIBLE);
////                            master.setVisibility(View.VISIBLE);
////                            formOfCareer2.setVisibility(View.VISIBLE);
////                            formOfCareer3.setVisibility(View.VISIBLE);
////
////                            license1.setVisibility(View.VISIBLE);
////                            license2.setVisibility(View.VISIBLE);
////                            award1.setVisibility(View.VISIBLE);
////                            award2.setVisibility(View.VISIBLE);
////                            bExpanded = true;
////                            Toast.makeText(mContext, "모든 항목이 펼쳐 졌습니다.", Toast.LENGTH_SHORT).show();
////                        }
//                        return true;
//
//                    //프로필 수정 스크린으로 이동하지 않고 사용자가 기록한 내용을 해당 스크린에서 프로필에 적용할 수 있도록 한다.
//                    case R.id.applyToProfile:
//                        if(mAuth.getCurrentUser() == null){
//                            Toast.makeText(mContext,"로그인 해주세요!",Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            //불러온 텍스트들을 모두 파이어베이스에 넣는다.
//                            applyAll_Firestore();
//                        }
//                        return true;
//                    //프로필로 이동
//                    case R.id.moveToProfile:
//                        moveProfile = new Intent(mContext, ProfileScrnActivity.class);
//                        mContext.startActivity(moveProfile);
//
//                        return true;
//                    default:
//                        return false;
//                }
//
//            }
//        });
//        popupMenu.show();
//    }
//
//    private void applyAll_Firestore(){
//
//        //TextInputEditText로부터 텍스트를 불러오고, 클래스 변수에 넣는다.
//        getAllTextFromTextInputEditText();
//
//        userID = mAuth.getCurrentUser().getUid();
//
//        if (!userID.isEmpty()) {
//            //프로필사항
//            //name, email, phoneNum, addr, engName, chName, rrn, age, num;
//            Map<String, Object> users = new HashMap<>();
//            users.put("name", name);
//            users.put("email", email);
//            users.put("phoneNum", phoneNum);
//            users.put("addr", addr);
//            users.put("engName", engName);
//            users.put("chName", chName);
//            users.put("rrn", rrn);
//            users.put("age", age);
//            users.put("num", num);
//            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
//            documentReference.update(users).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    //Toast.makeText(mContext,"프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            //학력사항
//            user = new HashMap<>();
//            user.put("hN", hN);
//            user.put("hEnt", hEnt);
//            user.put("hGrad", hGrad);
//            user.put("hIfy", hIfy);
//
//
//            user.put("uN", uN);
//            user.put("uMaj", uMaj);
//            user.put("uEnt", uEnt);
//            user.put("uLoc", uLoc);
//            user.put("uSco",uSco);
//            user.put("uGrad", uGrad);
//            user.put("uIfy", uIfy);
//
//            user.put("mN", mN);
//            user.put("mEnt", mEnt);
//            user.put("mLoc", mLoc);
//            user.put("mSco", mSco);
//            user.put("mGrad",mGrad);
//            user.put("mIfy", mIfy);
//            user.put("mMaj", mMaj);
//
//            documentReference = fStore.collection("users").document(userID).collection("profiles").document("eduBack");
//            documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
////                                        Toast.makeText(mContext,"학력 프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            //경력사항
//            user = new HashMap<>();
//            user.put("corpN1", corpN1);
//            user.put("dep1", dep1);
//            user.put("corpEnt1", corpEnt1);
//            user.put("corpRes1", corpRes1);
//            user.put("work1", work1);
//
//            user.put("corpN2", corpN2);
//            user.put("dep2", dep2);
//            user.put("corpEnt2", corpEnt2);
//            user.put("corpRes2", corpRes2);
//            user.put("work2", work2);
//
//            user.put("corpN3", corpN3);
//            user.put("dep3", dep3);
//            user.put("corpEnt3", corpEnt3);
//            user.put("corpRes3", corpRes3);
//            user.put("work3", work3);
//            documentReference = fStore.collection("users").document(userID).collection("profiles").document("formOfCareer");
//            documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
////                                        Toast.makeText(mContext,"경력 프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
//                }
//            });
//
//
//            //자격증 및 수상
//            Map<String, Object> licenses  = new HashMap<>();
//            licenses.put("licYM1", licYM1);
//            licenses.put("licC1", licC1);
//            licenses.put("licG1", licG1);
//            licenses.put("licP1", licP1);
//
//            licenses.put("licYM2", licYM2);
//            licenses.put("licC2", licC2);
//            licenses.put("licG2", licG2);
//            licenses.put("licP2", licP2);
//
//            licenses.put("aYM1", aYM1);
//            licenses.put("aC1", aC1);
//            licenses.put("aP1", aP1);
//
//            licenses.put("aYM2", aYM2);
//            licenses.put("aC2", aC2);
//            licenses.put("aP2", aP2);
//
//            documentReference = fStore.collection("users").document(userID).collection("profiles").document("licenses");
//            documentReference.update(licenses).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
////                                        Toast.makeText(mContext,"수상 프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            Toast.makeText(mContext,"프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void updateUI() {
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
//
//        if (isSignedIn()) {
//            mAuth = FirebaseAuth.getInstance();
//            storageReference = fStorage.getInstance().getReference();
//            userID = mAuth.getCurrentUser().getUid();
//            bExpanded = false;
//
//            //FirebaseFirestore의 collection("users").document(userID)에서 이름, 이메일, 휴대전화, 주소를 가져오고
//            //EditText에 적용한다.
//            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
//            documentReference.addSnapshotListener(mActivity, new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                    if (value != null && value.exists()) {
//                        name = value.getString("name");
//                        engName = value.getString("engName");
//                        chName = value.getString("chName");
//                        rrn = value.getString("rrn");
//                        age = value.getString("age");
//                        phoneNum = value.getString("phoneNum");
//                        num = value.getString("num");
//                        email = value.getString("email");
//                        addr = value.getString("addr");
//
//
//                    }
//                }
//            });
//            //FirebaseFirestore의 collection("users").document(userID).collection("profiles").document("eduBack")에서 사용자가 프로필에 설정한 학력사항을 가져오고
//            //EditText에 적용한다.
//            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("eduBack");
//            documentReference.addSnapshotListener(mActivity, new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                    if (value != null && value.exists()) {
//
//                        hN = value.getString("hN");
//                        hEnt = value.getString("hEnt");
//                        hGrad = value.getString("hGrad");
//                        hIfy = value.getString("hIfy");
//
//                        uN = value.getString("uN");
//                        uEnt = value.getString("uEnt");
//                        uGrad = value.getString("uGrad");
//                        uLoc = value.getString("uLoc");
//                        uSco= value.getString("uSco");
//                        uMaj = value.getString("uMaj");
//                        uIfy = value.getString("uIfy");
//                        if (!bExpanded &&checkString(uN) && checkString(uEnt) && checkString(uGrad) && checkString(uLoc) &&checkString(uSco) && checkString(uMaj) && checkString(uIfy)) {
//                            university.setVisibility(View.GONE);
//                        }
//
//                        mN = value.getString("mN");
//                        mEnt = value.getString("mEnt");
//                        mGrad = value.getString("mGrad");
//                        mLoc = value.getString("mLoc");
//                        mSco= value.getString("mSco");
//                        mMaj = value.getString("mMaj");
//                        mIfy = value.getString("mIfy");
//                        if (!bExpanded &&!bExpanded &&checkString(mN) && checkString(mEnt) && checkString(mGrad) && checkString(mLoc) &&checkString(mSco) && checkString(mMaj) && checkString(mIfy)) {
//                            master.setVisibility(View.GONE);
//                        }
//
//                    }
//                    else{
//                        Toast.makeText(getApplicationContext(),"학력사항을 입력해주세요",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            //FirebaseFirestore의 collection("users").document(userID).collection("profiles").document("licenses")에서 사용자가 프로필에 설정한 자격증사항을
//            //EditText에 적용한다.
//            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("licenses");
//            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                    if (value != null && value.exists()) {
//                        licYM1 = value.getString("licYM1");
//                        licC1 = value.getString("licC1");
//                        licC1 = value.getString("licG1");
//                        licP1 = value.getString("licP1");
//                        if (!bExpanded && checkString(licYM1) && checkString(licC1) && checkString(licG1) && checkString(licP1)) {
//                            license1.setVisibility(View.GONE);
//                        }
//                        licYM2 = value.getString("licYM2");
//                        licC2 = value.getString("licC2");
//                        licG2 = value.getString("licG2");
//                        licP2 = value.getString("licP2");
//                        if (!bExpanded && checkString(licYM2) && checkString(licC2) && checkString(licG2) && checkString(licP2)) {
//                            license2.setVisibility(View.GONE);
//                        }
//                        aYM1 = value.getString("aYM1");
//                        aC1 = value.getString("aC1");
//                        aP1 = value.getString("aP1");
//                        if (!bExpanded &&checkString(aYM1) && checkString(aC1) && checkString(aP1)) {
//                            award1.setVisibility(View.GONE);
//                        }
//                        aYM2 = value.getString("aYM2");
//                        aC2 = value.getString("aC2");
//                        aP2 = value.getString("aP2");
//                        if (!bExpanded && checkString(aYM2) && checkString(aC2) && checkString(aP2)) {
//                            award2.setVisibility(View.GONE);
//                        }
//
//
//                    }
//                    else{
//                        Toast.makeText(getApplicationContext(),"학력사항을 입력해주세요",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            //FirebaseFirestore의 collection("users").document(userID).collection("profiles").document("formOfCareer")에서 사용자가 프로필에 설정한 경력사항을 가져오고
//            //EditText에 적용한다.
//            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("formOfCareer");
//            documentReference.addSnapshotListener(mActivity, new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                    if (value != null && value.exists()) {
//                        corpN1 = value.getString("corpN1");
//                        corpEnt1 = value.getString("corpEnt1");
//                        corpRes1 = value.getString("corpRes1");
//                        dep1 = value.getString("dep1");
//                        work1 = value.getString("work1");
//
//                        corpN2 = value.getString("corpN2");
//                        corpEnt2 = value.getString("corpEnt2");
//                        corpRes2 = value.getString("corpRes2");
//                        dep2 = value.getString("dep2");
//                        work2 = value.getString("work2");
//                        if (!bExpanded &&checkString(corpN2) && checkString(dep2) && checkString(corpEnt2) && checkString(corpRes2) &&
//                                checkString(work2)) {
//                            formOfCareer2.setVisibility(View.GONE);
//                        }
//
//                        corpN3 = value.getString("corpN3");
//                        corpEnt3 = value.getString("corpEnt3");
//                        corpRes3 = value.getString("corpRes3");
//                        dep3 = value.getString("dep3");
//                        work3 = value.getString("work3");
//                        if (!bExpanded &&checkString(corpN3) && checkString(dep3) && checkString(corpEnt3) && checkString(corpRes3) &&
//                                checkString(work3)) {
//                            formOfCareer3.setVisibility(View.GONE);
//                        }
//                    }
//                    else{
//                        Toast.makeText(getApplicationContext(),"학력사항을 입력해주세요",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            //불러온 모든 텍스트들을 TextInputEditText에 넣는다.
//            setAllTextInTextInputEditText();
//        }
//    }
//
//    //DownloadManager.ACTION_DOWNLOAD_COMPLETE, RUNIMG_CMPLT, DOC_DWNL_CMPLT 이벤트를 받을 수 있도록 하는
//    //broadcastReceiver를 등록한다.
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//        intentFilter.addAction(RUNIMG_CMPLT);
//        intentFilter.addAction(DOC_DWNL_CMPLT);
//
//        registerReceiver(broadcastReceiver, intentFilter);
//        updateUI();
//    }
//    //해당 액티비티가 포커스를 잃으면 broadcastReceiver등록을 해제한다.
//    @Override
//    protected void onStop() {
//        super.onStop();
//        unregisterReceiver(broadcastReceiver);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case MY_PERMISSION_STORAGE:
//                for (int i = 0; i < grantResults.length; i++) {
//                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
//                    if (grantResults[i] < 0) {
//                        Toast.makeText(mContext, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
//                // 허용했다면 이 부분에서..
//                break;
//        }
//    }
//
//    private boolean isSignedIn() {
//        return GoogleSignIn.getLastSignedInAccount(getApplicationContext()) != null;
//    }
//
//    //문서를 다운하기만 한다.
//    class downloadTmpltThread extends Thread{
//        public void run() {
//            handler1.post(new Runnable() {
//                @Override
//                public void run() {
//                    progressBar.setVisibility(View.VISIBLE);
//                }
//            });
//            docName = intent.getStringExtra("docName");         //default이름
//            fileName = editText_name.getText().toString().trim(); //사용자가 입력한 파일이름
//            Log.d(TAG, "run: docName: "+docName+", fileName: "+fileName);
//
//            //사용자가 파일 이름 입력을 하지 않았을 때, default이름으로 다운로드 한다.
//            if(checkString(fileName)){
//                downloadEP = new DownloadEP(getApplicationContext());
//                downloadEP.download_without_modify(docName, docName);
//            }
//            //사용자가 파일이름을 입력했을 때
//            else{
//                downloadEP = new DownloadEP(getApplicationContext());
//                downloadEP.download_without_modify(fileName, docName);
//            }
//        }
//    }
//    //문서 다운이 끝났는지 체크한다.
//    class checkingDownloadThread extends Thread{
//        boolean downloadComplete = false;
//        File f;
//
//        public void run() {
//            docName = intent.getStringExtra("docName");         //default이름
//            fileName = editText_name.getText().toString().trim(); //사용자가 입력한 파일이름
//            if(fileName.isEmpty()) {
//                Log.d(TAG, "run/absolutePath : "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/"+docName+".docx");
//            }
//            else {
//                Log.d(TAG, "run/absolutePath : " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName + ".docx");
//            }
//            //파일 인스턴스를 생성할 수 있으면 반복문을 빠져나온다.
//            while(!downloadComplete) {
//                try {
//                    if(checkString(fileName)) {
//                        f = new File(Environment.DIRECTORY_DOWNLOADS + "/ZN/"+docName + ".docx");
//                    }
//                    else{
//                        f = new File(Environment.DIRECTORY_DOWNLOADS + "/ZN/"+fileName + ".docx");
//                    }
//                    if(f.exists()&& !f.isDirectory()){
//                        downloadComplete = true;
//                    }
//                }catch (NullPointerException e){}
//                try {
//                    Log.d(TAG, "DDING DDONG");
//                    Thread.sleep(500);
//                } catch (Exception e) {}
//            }
//            //progressBar를 숨긴다.
//            handler2.post(new Runnable() {
//                @Override
//                public void run() {
//                    progressBar.setVisibility(View.INVISIBLE);
//                    Toast.makeText(mContext,"성공적으로 문서가 다운되었습니다.",Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
//    }
//
//    //프로필이미지와 텍스트를 대체시킨다.
//    class tmpltProcessThread extends Thread{
//        @Override
//        public void run() {
//            handler2.post(new Runnable() { //progressBar를 보인다.
//                @Override
//                public void run() {
//                    progressBar.setVisibility(View.VISIBLE);
//                }
//            });
//
//            docName = intent.getStringExtra("docName");             //default이름
//            fileName = editText_name.getText().toString().trim();     //사용자가 입력한 파일이름
//            Log.d(TAG, "run: docName: "+docName+", fileName: "+fileName);
//
//            if (checkString(fileName)) {
//                downloadEP = new DownloadEP(getApplicationContext());
//                downloadEP.download_with_modify(docName, docName);
//            } else {
//                downloadEP = new DownloadEP(getApplicationContext());
//                downloadEP.download_with_modify(fileName, docName);
//            }
//        }
//    }
//
//    //텍스트, 프로필 이미지 대체가 끝났는지 체크한다.
//    class checkingProcessThread extends Thread{
//        boolean downloadComplete = false;
//
//        File f;
//        File imagePicture;
//
//        public void run() {
//
//            docName = intent.getStringExtra("docName");             //default이름
//            fileName = editText_name.getText().toString().trim();     //사용자가 입력한 파일이름
//
//            if(fileName.isEmpty()) {
//                Log.d(TAG, "run/absolutePath : "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/"+docName+".docx");
//            }
//            else {
//                Log.d(TAG, "run/absolutePath : " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName + ".docx");
//            }
//            //File 인스턴스를 만들 수 있으면 반복문을 빠져나온다.
//            while(!downloadComplete) {
//                try {
//                    if(fileName.isEmpty()) {
//                        f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/"+docName+".docx");
//                    }
//                    else{
//                        f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/"+fileName+".docx");
//                    }
//                    if(f.exists()&& !f.isDirectory()){
//                        downloadComplete = true;
//                    }
//                    Log.d(TAG, "run/f.exists() : "+f.exists());
//                }catch (NullPointerException e){}
//                try {
//                    Log.d(TAG, "DDING DDONG");
//                    Thread.sleep(500);
//                } catch (Exception e) {}
//
//            }
//            handler2.post(new Runnable() {
//                @Override
//                public void run() {
//                    progressBar.setVisibility(View.INVISIBLE);
//                    Toast.makeText(mContext,"성공적으로 문서가 생성되었습니다.",Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
//    private void setAllTextInTextInputEditText(){
//        TextInputEditText_bON.setText(bON);
//        TextInputEditText_bOA.setText(bOA);
//        TextInputEditText_bOCI.setText(bOCI);
//        TextInputEditText_bN.setText(bN);
//        TextInputEditText_wN.setText(wN);
//        TextInputEditText_wA.setText(wA);
//        TextInputEditText_wCI.setText(wCI);
//        TextInputEditText_cC.setText(cC);
//        TextInputEditText_lCP.setText(lCP);
//        TextInputEditText_pOW.setText(pOW);
//        TextInputEditText_bI.setText(bI);
//        TextInputEditText_fWH.setText(fWH);
//        TextInputEditText_wD.setText(wD);
//        TextInputEditText_wD.setText(wD);
//        TextInputEditText_w.setText(w);
//        TextInputEditText_sIC.setText(sIC);
//    }
//
//    private void getAllTextFromTextInputEditText(){
//        bON = TextInputEditText_bON.getText().toString().trim();
//        bOA = TextInputEditText_bOA.getText().toString().trim();
//        bOCI = TextInputEditText_bOCI.getText().toString().trim();
//        bN = TextInputEditText_bN.getText().toString().trim();
//        wN = TextInputEditText_wN.getText().toString().trim();
//        wA = TextInputEditText_wA.getText().toString().trim();
//        wCI = TextInputEditText_wCI.getText().toString().trim();
//        cC = TextInputEditText_cC.getText().toString().trim();
//        lCP = TextInputEditText_lCP.getText().toString().trim();
//        pOW = TextInputEditText_pOW.getText().toString().trim();
//        bI = TextInputEditText_bI.getText().toString().trim();
//        fWH = TextInputEditText_fWH.getText().toString().trim();
//        wD = TextInputEditText_wD.getText().toString().trim();
//        wD = TextInputEditText_wD.getText().toString().trim();
//        w = TextInputEditText_w.getText().toString().trim();
//        sIC = TextInputEditText_sIC.getText().toString().trim();
//    }
//
//    private void checkPermission(){
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            // 다시 보지 않기 버튼을 만드려면 이 부분에 바로 요청을 하도록 하면 됨 (아래 else{..} 부분 제거)
//            // ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_CAMERA);
//
//            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                new AlertDialog.Builder(this)
//                        .setTitle("알림")
//                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
//                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                intent.setData(Uri.parse("package:" + getPackageName()));
//                                startActivity(intent);
//                            }
//                        })
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                finish();
//                            }
//                        })
//                        .setCancelable(false)
//                        .create()
//                        .show();
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
//            }
//        }
//    }
//    boolean checkString(String str) {
//        return str == null || str.length() == 0;
//    }
//}
