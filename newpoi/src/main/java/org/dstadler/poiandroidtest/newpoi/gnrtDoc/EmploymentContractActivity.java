package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.RelativeLayout;
import android.widget.TimePicker;
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
import com.google.api.Distribution;
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
import org.dstadler.poiandroidtest.newpoi.cls.GoogleManager;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;
import org.dstadler.poiandroidtest.newpoi.cls.RoundedCornersTransformation;
import org.dstadler.poiandroidtest.newpoi.profile.ProfileScrnActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EmploymentContractActivity extends AppCompatActivity {
    private static final String TAG = EmploymentContractActivity.class.getName();

    //==================================================commons===============================================//
    //final vars
    private static final int MY_PERMISSION_STORAGE = 1111;
    //image attributes
    public static int sCorner = 80;
    public static int sMargin = 1;
    public static int sBorder = 0;

    // vars
    private boolean bExpanded;
    private String docName;
    private String pagePath0, pagePath1, pagePath2;
    private Uri imgUri0, imgUri1, imgUri2;
    private String fileName, fileNameWithExt;
    private int docNum;
    private String userID;
    private File  docFile;
    private Handler handler1, handler2;

    //widgets
    private ImageButton imageBtn_back, imageBtn_more;
    private Button btn_download, btn_create;
    private ProgressBar progressBar;
    private EditText editText_title;
    private ImageView image_main0, image_main1, image_main2;

    //contents
    private Context mContext;
    private AppCompatActivity mActivity;


    //firebase
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;

    private Intent intent, moveProfile;
    private DownloadEP downloadEP;

    /*(사업주, 근로자, 계약조건)
    =(bO, w, cC*/
    /*(사업주 이름, 사업주 주소, 사업주 연락처, 사업체명)
    =(business owner name, business owner address, business owner contact information, business name)
    =(bON, bOA, bOCI, bN)*/

    /*(근로자 이름, 근로자 주소, 근로자 연락처)
    =(worker name, worker address, worker contact information)
    =(wN, wA, wCI)*/

    /*(계약조건, 근로계약기간, 근무장소, 업무내용, 소정근로시간, 근무일,  임금, 사회보험적용여부)
     =(contract conditions, Labor contract period, place of work, Business information, fixed working hours, working day, wage, Social insurance coverage)
     =(cC, lCP, pOW, bI, fWH, wD, wD, wG, sIC)*/


    //String

    //사업주 정보
    private String bON, bOA, bOCI, bN;

    //근로자 정보
    private String
            wN, wA, wCI;

    //계약정보
    private String
            wSD, wED, wST, wET, lCP, pOW, bI, wD, sIC;


    //TextinputEditText
    //사업주 정보
    private TextInputEditText
            TextInputEditText_bON, TextInputEditText_bOA, TextInputEditText_bOCI, TextInputEditText_bN;
    //근로자 정보
    private TextInputEditText
            TextInputEditText_wN, TextInputEditText_wA, TextInputEditText_wCI, TextInputEditText_cC;
    //계약정보
    private TextInputEditText
            TextInputEditText_lCP, TextInputEditText_pOW, TextInputEditText_bI, TextInputEditText_wSD, TextInputEditText_wED, TextInputEditText_w, TextInputEditText_sIC,
            TextInputEditText_wST, TextInputEditText_wET;

    //TextinputLayout
    //사업주 정보
    private TextInputLayout
            TextInputLayout_bON, TextInputLayout_bOA, TextInputLayout_bOCI, TextInputLayout_bN;
    //근로자 정보
    private TextInputLayout
            TextInputLayout_wN, TextInputLayout_wA, TextInputLayout_wCI, TextInputLayout_cC;
    //계약정보
    private TextInputLayout
            TextInputLayout_lCP, TextInputLayout_pOW, TextInputLayout_bI, TextInputLayout_wD, TextInputLayout_w, TextInputLayout_sIC;


    //(wSD, wED, wST, wET) = (근로시작날, 근로종료날, 근로시작시간, 근로종료시간)
    private LinearLayout CalendarLayout_wSD, CalendarLayout_wED, TimeLayout_wST, TimeLayout_wET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment_contract); //
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
        imageBtn_back = findViewById(R.id.imageBtn_back);
        imageBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //widgets
        progressBar = findViewById(R.id.progressBar);

        Calendar calendar = Calendar.getInstance();

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
        CalendarLayout_wSD = findViewById(R.id.CalenderLayout_wSD);
        CalendarLayout_wED = findViewById(R.id.CalenderLayout_wED);
        TimeLayout_wST = findViewById(R.id.TimeLayout_wST);
        TimeLayout_wET = findViewById(R.id.TimeLayout_wET);

        //TextInputLayout
        TextInputLayout_bON = findViewById(R.id.TextInputLayout_bON);
        TextInputLayout_bOA = findViewById(R.id.TextInputLayout_bOA);
        TextInputLayout_bOCI = findViewById(R.id.TextInputLayout_bOCI);
        TextInputLayout_bN = findViewById(R.id.TextInputLayout_bN);
        TextInputLayout_wN = findViewById(R.id.TextInputLayout_wN);
        TextInputLayout_wA = findViewById(R.id.TextInputLayout_wA);
        TextInputLayout_wCI = findViewById(R.id.TextInputLayout_wCI);
        TextInputLayout_lCP = findViewById(R.id.TextInputLayout_wSD);
        TextInputLayout_pOW = findViewById(R.id.TextInputLayout_pOW);
        TextInputLayout_bI = findViewById(R.id.TextInputLayout_bI);
        TextInputLayout_wD = findViewById(R.id.TextInputLayout_wD);
        TextInputLayout_w = findViewById(R.id.TextInputLayout_w);
        TextInputLayout_sIC = findViewById(R.id.TextInputLayout_sIC);



        //TextInputEditText
        TextInputEditText_bON = findViewById(R.id.TextInputEditText_bON);
        TextInputEditText_bOA = findViewById(R.id.TextInputEditText_bOA);
        TextInputEditText_bOCI = findViewById(R.id.TextInputEditText_bOCI);
        TextInputEditText_bN = findViewById(R.id.TextInputEditText_bN);
        TextInputEditText_wN = findViewById(R.id.TextInputEditText_wN);
        TextInputEditText_wA = findViewById(R.id.TextInputEditText_wA);
        TextInputEditText_wCI = findViewById(R.id.TextInputEditText_wCI);
        TextInputEditText_lCP = findViewById(R.id.TextInputEditText_wSD);
        TextInputEditText_pOW = findViewById(R.id.TextInputEditText_pOW);
        TextInputEditText_bI = findViewById(R.id.TextInputEditText_bI);
        TextInputEditText_wSD = findViewById(R.id.TextInputEditText_wSD);
        TextInputEditText_wED = findViewById(R.id.TextInputEditText_wED);
        TextInputEditText_wST = findViewById(R.id.TextInputEditText_wST);
        TextInputEditText_wET = findViewById(R.id.TextInputEditText_wET);
        TextInputEditText_w = findViewById(R.id.TextInputEditText_w);
        TextInputEditText_sIC = findViewById(R.id.TextInputEditText_sIC);

        CalendarLayout_wSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1+1;
                        String date = i+"/"+i1+"/"+i2;
                        TextInputEditText_wSD.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        CalendarLayout_wED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1+1;
                        String date = i+"/"+i1+"/"+i2;
                        TextInputEditText_wED.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        TimeLayout_wST.setOnClickListener(new View.OnClickListener() {
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
        TimeLayout_wET.setOnClickListener(new View.OnClickListener() {
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
        PreferenceManager.setString(mContext,"wN",TextInputEditText_wN.getText().toString().trim());
        PreferenceManager.setString(mContext,"wA",TextInputEditText_wA.getText().toString().trim());
        PreferenceManager.setString(mContext,"wCI",TextInputEditText_wCI.getText().toString().trim());
        PreferenceManager.setString(mContext,"lCP",TextInputEditText_lCP.getText().toString().trim());
        PreferenceManager.setString(mContext,"pOW",TextInputEditText_pOW.getText().toString().trim());
        PreferenceManager.setString(mContext,"bI",TextInputEditText_bI.getText().toString().trim());
        PreferenceManager.setString(mContext,"wD",TextInputEditText_wSD.getText().toString().trim());
        PreferenceManager.setString(mContext,"sIC",TextInputEditText_sIC.getText().toString().trim());
        PreferenceManager.setString(mContext,"wSD",TextInputEditText_wSD.getText().toString().trim());
        PreferenceManager.setString(mContext,"wED",TextInputEditText_wED.getText().toString().trim());
        PreferenceManager.setString(mContext,"wST",TextInputEditText_wST.getText().toString().trim());
        PreferenceManager.setString(mContext,"wET",TextInputEditText_wET.getText().toString().trim());

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
                        TextInputEditText_bON.setText(value.getString("name"));
                        TextInputEditText_bOA.setText(value.getString("addr"));
                        TextInputEditText_bOCI.setText(value.getString("phoneNum"));
                    }
                }
            });
        }
        bON = PreferenceManager.getString(mContext,"bON");
        bOA = PreferenceManager.getString(mContext,"bOA");
        bOCI = PreferenceManager.getString(mContext,"bOCI");
        bN = PreferenceManager.getString(mContext,"bN");
        wN = PreferenceManager.getString(mContext,"wN");
        wA = PreferenceManager.getString(mContext,"wA");
        wCI = PreferenceManager.getString(mContext,"wCI");
        lCP = PreferenceManager.getString(mContext,"lCP");
        pOW = PreferenceManager.getString(mContext,"pOW");
        bI = PreferenceManager.getString(mContext,"bI");
        wD = PreferenceManager.getString(mContext,"wD");
        sIC = PreferenceManager.getString(mContext,"sIC");
        wSD = PreferenceManager.getString(mContext,"wSD");
        wED = PreferenceManager.getString(mContext,"wED");
        wST = PreferenceManager.getString(mContext,"wST");
        wET = PreferenceManager.getString(mContext,"wET");
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

                    //TextInputEditText로부터 텍스트를 불러오고, 클래스 변수에 넣는다.
                    getAllTextFromTextInputEditText();
                    Range range = document.getRange();

                    Log.d(TAG, "createResultThread: bON : " +bON);
                    Log.d(TAG, "createResultThread: bOA : " +bOA);
                    Log.d(TAG, "createResultThread: bOCI : " +bOCI);
                    Log.d(TAG, "createResultThread: bN : " +bN);
                    Log.d(TAG, "createResultThread: wN : " +wN);
                    Log.d(TAG, "createResultThread: wA : " +wA);
                    Log.d(TAG, "createResultThread: wCI : " +wCI);
                    Log.d(TAG, "createResultThread: wSD : " +wSD);
                    Log.d(TAG, "createResultThread: wED : " +wED);
                    Log.d(TAG, "createResultThread: wST : " +wST);
                    Log.d(TAG, "createResultThread: wET : " +wET);
                    Log.d(TAG, "createResultThread: lCP : " +lCP);
                    Log.d(TAG, "createResultThread: pOW : " +pOW);
                    Log.d(TAG, "createResultThread: bI : " +bI);
                    Log.d(TAG, "createResultThread: wD : " +wD);
                    Log.d(TAG, "createResultThread: wD : " +wD);
                    Log.d(TAG, "createResultThread: sIC : " +sIC);
                    if(bON.isEmpty()) {
                        range.replace("bON","", new FindReplaceOptions());
                    }else{range.replace("bON",bON, new FindReplaceOptions());}
                    if(bOA.isEmpty()) {
                        range.replace("bOA","", new FindReplaceOptions());
                    }else{range.replace("bOA",bOA, new FindReplaceOptions());}
                    if(bOCI.isEmpty()) {
                        range.replace("bOCI","", new FindReplaceOptions());
                    }else{range.replace("bOCI",bOCI, new FindReplaceOptions());}
                    if(bN.isEmpty()) {
                        range.replace("bN","", new FindReplaceOptions());
                    }else{range.replace("bN",bN, new FindReplaceOptions());}
                    if(wN.isEmpty()) {
                        range.replace("wN","", new FindReplaceOptions());
                    }else{range.replace("wN",wN, new FindReplaceOptions());}
                    if(wA.isEmpty()) {
                        range.replace("wA","", new FindReplaceOptions());
                    }else{range.replace("wA",wA, new FindReplaceOptions());}
                    if(wCI.isEmpty()) {
                        range.replace("wCI","", new FindReplaceOptions());
                    }else{range.replace("wCI",wCI, new FindReplaceOptions());}
                    if(lCP.isEmpty()) {
                        range.replace("lCP","", new FindReplaceOptions());
                    }else{range.replace("lCP",lCP, new FindReplaceOptions());}
                    if(pOW.isEmpty()) {
                        range.replace("pOW","", new FindReplaceOptions());
                    }else{range.replace("pOW",pOW, new FindReplaceOptions());}
                    if(bI.isEmpty()) {
                        range.replace("bI","", new FindReplaceOptions());
                    }else{range.replace("bI",bI, new FindReplaceOptions());}
                    if(wD.isEmpty()) {
                        range.replace("wD","", new FindReplaceOptions());
                    }else{range.replace("wD",wD, new FindReplaceOptions());}
                    if(wD.isEmpty()) {
                        range.replace("wD","", new FindReplaceOptions());
                    }else{range.replace("wD",wD, new FindReplaceOptions());}
                    if(sIC.isEmpty()) {
                        range.replace("sIC","", new FindReplaceOptions());
                    }else{range.replace("sIC",sIC, new FindReplaceOptions());}
                    if(wSD.isEmpty()) {
                        range.replace("wSD","", new FindReplaceOptions());
                    }else{range.replace("wSD부터",wSD, new FindReplaceOptions());}
                    if(wED.isEmpty()) {
                        range.replace("wED","", new FindReplaceOptions());
                    }else{range.replace("wED까지",wED, new FindReplaceOptions());}
                    if(wST.isEmpty()) {
                        range.replace("wST","", new FindReplaceOptions());
                    }else{range.replace("wST부터",wST, new FindReplaceOptions());}
                    if(wET.isEmpty()) {
                        range.replace("wET","", new FindReplaceOptions());
                    }else{range.replace("wET까지",wET, new FindReplaceOptions());}



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
    private void setAllTextInTextInputEditText(){
        TextInputEditText_bON.setText(bON);
        TextInputEditText_bOA.setText(bOA);
        TextInputEditText_bOCI.setText(bOCI);
        TextInputEditText_bN.setText(bN);
        TextInputEditText_wN.setText(wN);
        TextInputEditText_wA.setText(wA);
        TextInputEditText_wCI.setText(wCI);
        TextInputEditText_lCP.setText(lCP);
        TextInputEditText_pOW.setText(pOW);
        TextInputEditText_bI.setText(bI);
        TextInputEditText_wSD.setText(wD);
        TextInputEditText_wED.setText(wD);
        TextInputEditText_sIC.setText(sIC);
        TextInputEditText_wSD.setText(wSD);
        TextInputEditText_wED.setText(wED);
        TextInputEditText_wST.setText(wST);
        TextInputEditText_wET.setText(wET);
    }

    private void getAllTextFromTextInputEditText() {
        bON = TextInputEditText_bON.getText().toString().trim();
        bOA = TextInputEditText_bOA.getText().toString().trim();
        bOCI = TextInputEditText_bOCI.getText().toString().trim();
        bN = TextInputEditText_bN.getText().toString().trim();
        wN = TextInputEditText_wN.getText().toString().trim();
        wA = TextInputEditText_wA.getText().toString().trim();
        wCI = TextInputEditText_wCI.getText().toString().trim();
        lCP = TextInputEditText_lCP.getText().toString().trim();
        pOW = TextInputEditText_pOW.getText().toString().trim();
        bI = TextInputEditText_bI.getText().toString().trim();
        sIC = TextInputEditText_sIC.getText().toString().trim();
        wSD = TextInputEditText_wSD.getText().toString().trim();
        wED = TextInputEditText_wED.getText().toString().trim();
        wST = TextInputEditText_wST.getText().toString().trim();
        wET = TextInputEditText_wET.getText().toString().trim();
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
