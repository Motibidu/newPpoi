package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.DatePicker;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

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
import org.dstadler.poiandroidtest.newpoi.cls.DownloadEP;
import org.dstadler.poiandroidtest.newpoi.cls.GoogleManager;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;
import org.dstadler.poiandroidtest.newpoi.cls.RoundedCornersTransformation;
import org.dstadler.poiandroidtest.newpoi.profile.ProfileScrnActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;

public class ResignationActivity extends AppCompatActivity {
    private static final String TAG = ResignationActivity.class.getName();

    //==================================================commons===============================================//
    //final vars
    private static final int MY_PERMISSION_STORAGE = 1111;
    //image attributes
    public static int sCorner = 80;
    public static int sMargin = 1;
    public static int sBorder = 0;

    // vars
    private boolean bExpanded= false;
    private String docName;
    private String pagePath0, pagePath1, pagePath2;
    private Uri imgUri0, imgUri1, imgUri2;
    private String fileName, fileNameWithExt;
    private int docNum;
    private String userID;
    private File  docFile;
    private Handler handler1, handler2;

    //widgets
    private ImageButton backBtn, imageBtn_more;
    private Button btn_download, btn_create;
    private ProgressBar progressBar;
    private EditText editText_title;
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
    

    //resignation0
    //공통 : 이름, 생년월일, 주소, 직급, 근무부서, 입사날짜, 퇴사날짜, 사직사유, 당일날짜
    //예외 :

    //resignation1
    //공통 : 이름, 생년월일, 주소, 직급, 근무부서, 입사날짜, 퇴사날짜, 사직사유, 당일날짜
    //예외 : 사번, 전화번호

    //<1> 총 추출 :


    //<2> 카테고리 네이밍 및 분류
    //신청인 정보
    //이름, 생년월일, 주소, 전화번호
    //'name', 'dB', 'addr', 'pN'

    //신청인 사내 상세정보
    //사번, 직급, 근무부서, 사직사유
    //'rK', 'dT', 'eN','rFR'

    //날짜
    //입사날짜, 퇴사날짜, 당일날짜
    // 'jD', 'dD', 'tD'

    //<3> 총 추출, 카테고리에 맞게 정렬 : 이름, 생년월일, 주소, 전화번호, 사번, 직급, 근무부서, 사직사유, 입사날짜, 퇴사날짜, 당일날짜

    //<4> pair, list
    //(이름 : name,  생년월일 : dB,  주소 : addr,  전화번호 사번 : pN,  직급 : rK,  근무부서 : dT,  사직사유 : rFR, 입사날짜 : jD,  퇴사날짜 : dD,  당일날짜 : tD, )
    // ['name', 'dB', 'addr', 'pN'', 'rK', 'dT', 'rFR', 'jD', 'dD', 'tD']


    //<5> String 선언
    //String
    //신청인 정보
    private String name, dB, addr, pN;
    //신청인 사내 상세정보
    private String rK, dT, rFR, eN;
    //날짜
    private String jD, dD, tD;

    //<5> TextInputEditText 선언

    //TextInputEditText
    private TextInputEditText
        TextInputEditText_name, TextInputEditText_dB, TextInputEditText_addr,TextInputEditText_pN;
    private TextInputEditText
        TextInputEditText_rK, TextInputEditText_dT,   TextInputEditText_rFR,  TextInputEditText_eN;
    private TextInputEditText
            TextInputEditText_jD, TextInputEditText_dD, TextInputEditText_tD;

    //<6> TextInputLayout 선언
    //TextInputLayout
    private TextInputLayout
        TextInputLayout_name, TextInputLayout_dB, TextInputLayout_addr, TextInputLayout_pN, TextInputLayout_rK, TextInputLayout_dT, TextInputLayout_rFR, TextInputLayout_jD, TextInputLayout_dD, TextInputLayout_tD;


    //<7> LinearLayout, TimeLayout 선언
    private LinearLayout LinearLayout_jD, LinearLayout_dD, LinearLayout_tD;
    private LinearLayout TimeLayout_pp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resignation); //
        //=================================common===================================//
        //Content
        mContext = getApplicationContext();

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
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener setListener;

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        final int[] hour = new int[1];
        final int[] minute = new int[1];


        image_main0 = findViewById(R.id.image_main0);
        image_main1 = findViewById(R.id.image_main1);
        image_main2 = findViewById(R.id.image_main2);

        editText_title = findViewById(R.id.editText_title);
        btn_download = findViewById(R.id.btn_download);
        btn_create = findViewById(R.id.btn_create);
        imageBtn_more = findViewById(R.id.imageBtn_more);


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
        //<8> findViewById LinearLayout, TimeLayout
        //LinearLayout, TimeLayout
        LinearLayout_dD = findViewById(R.id.LinearLayout_dD);
        LinearLayout_tD = findViewById(R.id.LinearLayout_tD);
        LinearLayout_jD = findViewById(R.id.LinearLayout_jD);

        //TextInputLayout
        TextInputLayout_name = findViewById(R.id.TextInputLayout_name);
        TextInputLayout_dB = findViewById(R.id.TextInputLayout_dB);
        TextInputLayout_addr = findViewById(R.id.TextInputLayout_addr);
        TextInputLayout_pN = findViewById(R.id.TextInputLayout_pN);
        TextInputLayout_rK = findViewById(R.id.TextInputLayout_rK);
        TextInputLayout_dT = findViewById(R.id.TextInputLayout_dT);
        TextInputLayout_rFR = findViewById(R.id.TextInputLayout_rFR);
        TextInputLayout_jD = findViewById(R.id.TextInputLayout_jD);
        TextInputLayout_dD = findViewById(R.id.TextInputLayout_dD);
        TextInputLayout_tD = findViewById(R.id.TextInputLayout_tD);



        //TextInputEditText
        TextInputEditText_name = findViewById(R.id.TextInputEditText_name);
        TextInputEditText_dB = findViewById(R.id.TextInputEditText_dB);
        TextInputEditText_addr = findViewById(R.id.TextInputEditText_addr);
        TextInputEditText_pN = findViewById(R.id.TextInputEditText_pN);
        TextInputEditText_rK = findViewById(R.id.TextInputEditText_rK);
        TextInputEditText_dT = findViewById(R.id.TextInputEditText_dT);
        TextInputEditText_rFR = findViewById(R.id.TextInputEditText_rFR);
        TextInputEditText_jD = findViewById(R.id.TextInputEditText_jD);
        TextInputEditText_dD = findViewById(R.id.TextInputEditText_dD);
        TextInputEditText_tD = findViewById(R.id.TextInputEditText_tD);

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



        //각 양식마다 필요없는 사항은 보이지 않도록 한다.
        docNum = intent.getIntExtra("docNum", 0);
        Log.d(TAG, "onCreate/docNum : "+docNum);
        if(docNum == 0){
//            TextInputLayout_bON.setVisibility(View.GONE);
//            TextInputLayout_bOA.setVisibility(View.GONE);
//            TextInputLayout_bOCI.setVisibility(View.GONE);
//            TextInputLayout_bN.setVisibility(View.GONE);
//            TextInputLayout_wN.setVisibility(View.GONE);
//            TextInputLayout_wA.setVisibility(View.GONE);
//            TextInputLayout_wCI.setVisibility(View.GONE);
//            TextInputLayout_cC.setVisibility(View.GONE);
//            TextInputLayout_lCP.setVisibility(View.GONE);
//            TextInputLayout_pOW.setVisibility(View.GONE);
//            TextInputLayout_bI.setVisibility(View.GONE);
//            TextInputLayout_wD.setVisibility(View.GONE);
//            TextInputLayout_wD.setVisibility(View.GONE);
//            TextInputLayout_w.setVisibility(View.GONE);
//            TextInputLayout_sIC.setVisibility(View.GONE);
        }
        //====================================================================================//
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.setString(mContext,"rK",TextInputEditText_rK.getText().toString().trim());
        PreferenceManager.setString(mContext,"dT",TextInputEditText_dT.getText().toString().trim());
        PreferenceManager.setString(mContext,"rFR",TextInputEditText_rFR.getText().toString().trim());
        PreferenceManager.setString(mContext,"jD",TextInputEditText_jD.getText().toString().trim());
        PreferenceManager.setString(mContext,"dD",TextInputEditText_dD.getText().toString().trim());
        PreferenceManager.setString(mContext,"tD",TextInputEditText_tD.getText().toString().trim());

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
                        Toast.makeText(mContext,"펼칠 항목이 없습니다!",Toast.LENGTH_SHORT).show();
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

            Toast.makeText(mContext,"프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(){
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
                        TextInputEditText_name.setText(value.getString("name"));
                        TextInputEditText_dB.setText(value.getString("rrn"));
                        TextInputEditText_addr.setText(value.getString("addr"));
                        TextInputEditText_pN.setText(value.getString("phoneNum"));
                    }
                }
            });
        }
        rK = PreferenceManager.getString(mContext,"rK");
        dT = PreferenceManager.getString(mContext,"dT");
        rFR = PreferenceManager.getString(mContext,"rFR");
        jD = PreferenceManager.getString(mContext,"jD");
        dD = PreferenceManager.getString(mContext,"dD");
        tD = PreferenceManager.getString(mContext,"tD");
        setAllTextInTextInputEditText();
    }

    //DownloadManager.ACTION_DOWNLOAD_COMPLETE, RUNIMG_CMPLT, DOC_DWNL_CMPLT 이벤트를 받을 수 있도록 하는
    //broadcastReceiver를 등록한다.
    @Override
    protected void onStart() {
        super.onStart();

        updateUI();
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

    //문서를 다운하기만 한다.
    class downloadFileThread extends Thread{
        public void run() {
            docName = intent.getStringExtra("docName");         //default이름
            fileName = editText_title.getText().toString().trim(); //사용자가 입력한 파일이름
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
            fileName = editText_title.getText().toString().trim(); //사용자가 입력한 파일이름
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
            fileName = editText_title.getText().toString().trim();     //사용자가 입력한 파일이름
            Log.d(TAG, "run: docName: "+docName+", fileName: "+fileName);

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
            fileName = editText_title.getText().toString().trim(); //사용자가 입력한 파일이름

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

            if(fileName.isEmpty()) {
                docFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/."+docName+".docx");
            }
            else{
                docFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName + ".docx");
            }
            fileNameWithExt = fileName+".docx";
            Log.d(TAG, "onReceive/fileNameWithExt : "+fileNameWithExt);
            //임시파일이 존재할 때
            if (docFile.exists()) {
                try {
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

                    //TextInputEditText로부터 텍스트를 불러오고, 클래스 변수에 넣는다.
                    getAllTextFromTextInputEditText();
                    Range range = document.getRange();

                    //<11> Logd
                    Log.d(TAG, "createResultThread: name : " +name);
                    Log.d(TAG, "createResultThread: dB : " +dB);
                    Log.d(TAG, "createResultThread: addr : " +addr);
                    Log.d(TAG, "createResultThread: pN : " +pN);
                    Log.d(TAG, "createResultThread: rK : " +rK);
                    Log.d(TAG, "createResultThread: dT : " +dT);
                    Log.d(TAG, "createResultThread: rFR : " +rFR);
                    Log.d(TAG, "createResultThread: jD : " +jD);
                    Log.d(TAG, "createResultThread: dD : " +dD);
                    Log.d(TAG, "createResultThread: tD : " +tD);

                    //<12> replace
                    if(name.isEmpty()) {
                        range.replace("name","", new FindReplaceOptions());
                    }else{range.replace("name",name, new FindReplaceOptions());}
                    if(dB.isEmpty()) {
                        range.replace("dB","", new FindReplaceOptions());
                    }else{range.replace("dB",dB, new FindReplaceOptions());}
                    if(addr.isEmpty()) {
                        range.replace("addr","", new FindReplaceOptions());
                    }else{range.replace("addr",addr, new FindReplaceOptions());}
                    if(pN.isEmpty()) {
                        range.replace("pN","", new FindReplaceOptions());
                    }else{range.replace("pN",pN, new FindReplaceOptions());}
                    if(rK.isEmpty()) {
                        range.replace("rK","", new FindReplaceOptions());
                    }else{range.replace("rK",rK, new FindReplaceOptions());}
                    if(dT.isEmpty()) {
                        range.replace("dT","", new FindReplaceOptions());
                    }else{range.replace("dT",dT, new FindReplaceOptions());}
                    if(rFR.isEmpty()) {
                        range.replace("rFR","", new FindReplaceOptions());
                    }else{range.replace("rFR",rFR, new FindReplaceOptions());}
                    if(jD.isEmpty()) {
                        range.replace("jD","", new FindReplaceOptions());
                    }else{range.replace("jD",jD, new FindReplaceOptions());}
                    if(dD.isEmpty()) {
                        range.replace("dD","", new FindReplaceOptions());
                    }else{range.replace("dD",dD, new FindReplaceOptions());}
                    if(tD.isEmpty()) {
                        range.replace("tD","", new FindReplaceOptions());
                    }else{range.replace("tD",tD, new FindReplaceOptions());}



                    document.save(Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileNameWithExt);

                    //임시파일을 삭제한다.
                    docFile.delete();

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
        public void run() {
            docName = intent.getStringExtra("docName");             //default이름
            fileName = editText_title.getText().toString().trim();     //사용자가 입력한 파일이름

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
    // <12> setAllTextInTextInputEditText
    private void setAllTextInTextInputEditText(){
        TextInputEditText_name.setText(name);
        TextInputEditText_dB.setText(dB);
        TextInputEditText_addr.setText(addr);
        TextInputEditText_pN.setText(pN);
        TextInputEditText_rK.setText(rK);
        TextInputEditText_dT.setText(dT);
        TextInputEditText_rFR.setText(rFR);
        TextInputEditText_jD.setText(jD);
        TextInputEditText_dD.setText(dD);
        TextInputEditText_tD.setText(tD);
    }
    // <13> getAllTextFromTextInputEditText
    private void getAllTextFromTextInputEditText() {
        name = TextInputEditText_name.getText().toString().trim();
        dB = TextInputEditText_dB.getText().toString().trim();
        addr = TextInputEditText_addr.getText().toString().trim();
        pN = TextInputEditText_pN.getText().toString().trim();
        rK = TextInputEditText_rK.getText().toString().trim();
        dT = TextInputEditText_dT.getText().toString().trim();
        rFR = TextInputEditText_rFR.getText().toString().trim();
        jD = TextInputEditText_jD.getText().toString().trim();
        dD = TextInputEditText_dD.getText().toString().trim();
        tD = TextInputEditText_tD.getText().toString().trim();
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
