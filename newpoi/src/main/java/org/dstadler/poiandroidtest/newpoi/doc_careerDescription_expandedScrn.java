package org.dstadler.poiandroidtest.newpoi;

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
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class doc_careerDescription_expandedScrn extends AppCompatActivity {

    public static int sCorner = 80;
    public static int sMargin = 1;
    public static int sBorder = 0;

    private boolean bExpanded;
    private ImageButton backBtn, expandedScrn_menu;
    private Button expandedScrn_download_without_modify, create, expand;
    private String themeColor = "#34ace0";

    private TextInputEditText highschool_enterYM_EditText, highschool_graYM_EditText, highschool_name_EditText, highschool_graCls_EditText,
            university_enterYM_EditText, university_graYM_EditText, university_graCls_EditText, university_name_EditText, university_major_EditText,
            master_enterYM_EditText, master_graYM_EditText, master_graCls_EditText, master_name_EditText, master_major_EditText, master_graThe_EditText,master_LAB_EditText;
    private TextInputEditText name_EditText, email_EditText, phoneNumber_EditText, address_EditText;
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

    private String name, email, phoneNumber, address;

    private String docName;
    private Intent intent, moveProfile, documentProcess;
    private EditText expandedScrn_name;

    private DownloadEP downloadEP;
    private CustomXWPFDocument customXWPFDocument;

    private String imgPath1, imgPath2, imgPath3;
    private Uri imgUri1, imgUri2, imgUri3;


    private ImageView expandedScrn_mainImageView1, expandedScrn_mainImageView2, expandedScrn_mainImageView3;
    private String fileName, folder;
    private StorageReference storageReference;

    private static final int MY_PERMISSION_STORAGE = 1111;

    private Map<String, String> data = new HashMap<String, String>();

    private FirebaseStorage fStorage;
    private Context mContext;
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private String userID;
    private DocumentReference documentReference;

    final String RUNIMG_CMPLT = "org.dstadler.poiandroidtest.newpoi.RUNIMG_CMPLT";
    final String DOC_DWNL_CMPLT = "org.dstadler.poiandroidtest.newpoi.DOC_DWNL_CMPLT";
    private File imgFile, docFile;
    private Map<String, Object> user;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_career_description_expanded_scrn);
        mContext = getApplicationContext();
        bExpanded = false;

        //???????????? ??????
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        expandedScrn_name = findViewById(R.id.expandedScrn_name);

        name_EditText = findViewById(R.id.name_EditText);                   //??????
        email_EditText = findViewById(R.id.email_EditText);                 //?????????
        phoneNumber_EditText = findViewById(R.id.phoneNumber_EditText);     //?????????
        address_EditText = findViewById(R.id.address_EditText);             //??????

        //????????????//
        //enterYM => enterYearMonth : ????????????
        //graYM => graduationYearMonth : ????????????
        //name : ??????
        //graCls => graduationClassification : ????????????
        //????????????
        highschool_enterYM_EditText = findViewById(R.id.highschool_enterYM_EditText);
        highschool_graYM_EditText = findViewById(R.id.highschool_graYM_EditText);
        highschool_name_EditText = findViewById(R.id.highschool_name_EditText);
        highschool_graCls_EditText = findViewById(R.id.highschool_graCls_EditText);
        //?????????
        university_enterYM_EditText = findViewById(R.id.university_enterYM_EditText);
        university_graYM_EditText = findViewById(R.id.university_graYM_EditText);
        university_graCls_EditText = findViewById(R.id.university_graCls_EditText);
        university_name_EditText = findViewById(R.id.university_name_EditText);
        university_major_EditText = findViewById(R.id.university_major_EditText);
        university = findViewById(R.id.university);
        //?????????
        master_enterYM_EditText = findViewById(R.id.master_enterYM_EditText);
        master_graYM_EditText = findViewById(R.id.master_graYM_EditText);
        master_graCls_EditText = findViewById(R.id.master_graCls_EditText);
        master_name_EditText = findViewById(R.id.master_name_EditText);
        master_major_EditText = findViewById(R.id.master_major_EditText);
        master_graThe_EditText = findViewById(R.id.master_graThe_EditText);
        master_LAB_EditText = findViewById(R.id.master_LAB_EditText);
        master = findViewById(R.id.master);

        //????????????//
        //name : ??????
        //enterYM => enterYearMonth : ????????????
        //resignYM => resignYearMonth : ????????????
        //office : ????????????
        //task : ????????????
        //???????????? 1
        formOfCareer1_name_EditText = findViewById(R.id.formOfCareer1_name_EditText);
        formOfCareer1_enterYM_EditText = findViewById(R.id.formOfCareer1_enterYM_EditText);
        formOfCareer1_resignYM_EditText = findViewById(R.id.formOfCareer1_resignYM_EditText);
        formOfCareer1_office_EditText = findViewById(R.id.formOfCareer1_office_EditText);
        formOfCareer1_task_EditText = findViewById(R.id.formOfCareer1_task_EditText);
        formOfCareer1 = findViewById(R.id.formOfCareer1);
        //???????????? 2
        formOfCareer2_name_EditText = findViewById(R.id.formOfCareer2_name_EditText);
        formOfCareer2_enterYM_EditText = findViewById(R.id.formOfCareer2_enterYM_EditText);
        formOfCareer2_resignYM_EditText = findViewById(R.id.formOfCareer2_resignYM_EditText);
        formOfCareer2_office_EditText = findViewById(R.id.formOfCareer2_office_EditText);
        formOfCareer2_task_EditText = findViewById(R.id.formOfCareer2_task_EditText);
        formOfCareer2 = findViewById(R.id.formOfCareer2);
        //???????????? 3
        formOfCareer3_name_EditText = findViewById(R.id.formOfCareer3_name_EditText);
        formOfCareer3_enterYM_EditText = findViewById(R.id.formOfCareer3_enterYM_EditText);
        formOfCareer3_resignYM_EditText = findViewById(R.id.formOfCareer3_resignYM_EditText);
        formOfCareer3_office_EditText = findViewById(R.id.formOfCareer3_office_EditText);
        formOfCareer3_task_EditText = findViewById(R.id.formOfCareer3_task_EditText);
        formOfCareer3 = findViewById(R.id.formOfCareer3);

        //?????????//
        //date : ?????????
        //cntnt => content / ???????????? ??? ??????
        //grade : ??????
        //publication : ?????????
        //?????????1
        license1_date_EditText = findViewById(R.id.license1_date_EditText);
        license1_cntnt_EditText = findViewById(R.id.license1_cntnt_Edittext);
        license1_grade_EditText = findViewById(R.id.license1_grade_EditText);
        license1_publication_EditText = findViewById(R.id.license1_publication_EditText);
        license1 = findViewById(R.id.license1);
        //?????????2
        license2_date_EditText = findViewById(R.id.license2_date_EditText);
        license2_cntnt_EditText = findViewById(R.id.license2_cntnt_EditText);
        license2_grade_EditText = findViewById(R.id.license2_grade_EditText);
        license2_publication_EditText = findViewById(R.id.license2_publication_EditText);
        license2 = findViewById(R.id.license2);

        //??????//
        //date : ????????????
        //cntnt : ????????????
        //publication : ?????????
        //??????1
        award1_date_EditText = findViewById(R.id.award1_date_EditText);
        award1_cntnt_EditText = findViewById(R.id.award1_cntnt_EditText);
        award1_publication_EditText = findViewById(R.id.award1_publication_EditText);
        award1 = findViewById(R.id.award1);
        //??????2
        award2_date_EditText = findViewById(R.id.award2_date_EditText);
        award2_cntnt_EditText = findViewById(R.id.award2_cntnt_EditText);
        award2_publication_EditText = findViewById(R.id.award2_publication_EditText);
        award2 = findViewById(R.id.award2);

        //????????? ????????? ?????? ?????? ????????????(????????????, ????????????1, ?????????1, ??????1)??? ????????????????????? ????????? ????????? ????????????.
        //????????? ????????? ?????? ????????? '?????????'????????? ?????? ?????????????????????.
        if(mAuth.getCurrentUser() == null){
            formOfCareer2.setVisibility(View.GONE);
            formOfCareer3.setVisibility(View.GONE);
            university.setVisibility(View.GONE);
            master.setVisibility(View.GONE);
            license2.setVisibility(View.GONE);
            award2.setVisibility(View.GONE);
        }
        
        expandedScrn_mainImageView1 = findViewById(R.id.expandedScrn_mainImageView1);
        expandedScrn_mainImageView2 = findViewById(R.id.expandedScrn_mainImageView2);
        expandedScrn_mainImageView3 = findViewById(R.id.expandedScrn_mainImageView3);

        intent = getIntent();
        //imgPath1, imgPath2, imgPath3??? ????????????.
        imgPath1 = intent.getStringExtra("imgPath1");
        imgPath2 = intent.getStringExtra("imgPath2");
        imgPath3 = intent.getStringExtra("imgPath3");

        //imgPath1??? ?????? ?????? ??? ????????? mainImageView??? rounded????????? imgUri1??? ???????????? ????????????.
        if(!checkString(imgPath1)) {
            imgUri1 = Uri.parse(imgPath1);
            Glide.with(this).load(imgUri1)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(expandedScrn_mainImageView1);
        }
        //imgPath2??? ?????? ?????? ??? ????????? mainImageView??? rounded????????? imgUri2??? ???????????? ????????????.
        if(!checkString(imgPath2)) {
            imgUri2 = Uri.parse(imgPath2);
            Glide.with(this).load(imgUri2)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(expandedScrn_mainImageView2);
        }
        //imgPath3??? ?????? ?????? ??? ????????? mainImageView??? rounded????????? imgUri3??? ???????????? ????????????.
        if(!checkString(imgPath3)) {
            imgUri3 = Uri.parse(imgPath3);
            Glide.with(this).load(imgUri3)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(expandedScrn_mainImageView3);
        }


        //???????????? ???????????? ??????
        expandedScrn_download_without_modify = findViewById(R.id.expandedScrn_download_without_modify);
        //????????? ?????? ?????? ????????? ?????? ????????? ?????? ???????????????????????? docName??? ????????? ???????????? ??????????????????.
        expandedScrn_download_without_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //????????? ?????? ??????.
                checkPermission();
                //???????????? ???????????? EditText??? ????????? ????????? ????????????.
                fileName = expandedScrn_name.getText().toString().trim();
                //??????????????? ???????????? ?????? "????????? ??????????????????!"?????? ????????? ?????????,
                //??????????????? ????????? ?????? ?????? ????????? fileName?????? ?????? docName+".docx"??? ????????? ??????????????????.
                if(checkString(fileName)){
                    Toast.makeText(mContext,"????????? ??????????????????!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //careerDescription.class?????? ????????? docName??? ????????????.
                    docName = intent.getStringExtra("docName");
                    //????????????????????? ?????????(downloadEP)??? ???????????? download_without_modfiy???????????? ????????????.
                    //download_without_modify???????????? ???????????????????????? uri??? ??????????????? ???????????? ?????? uri??? downloadFile_without_modify???????????? ????????????.
                    //downloadFile_without_modify???????????? download/ZN ????????? ".docx" ???????????? ?????? ????????? ??????????????????.
                    downloadEP = new DownloadEP(getApplicationContext());
                    downloadEP.download_without_modify(fileName, docName);
                }
            }
        });

        //?????? ??????
        create = findViewById(R.id.expandedScrn_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //????????? ?????? ??????.
                checkPermission();
                //???????????? ???????????? EditText??? ????????? ????????? ????????????.
                fileName = expandedScrn_name.getText().toString().trim();
                //????????? ????????? ?????? ?????? ?????? ????????? ????????????,
                //???????????? ????????? ?????? "????????? ????????????!" ?????? ????????? ?????????.
                if (mAuth.getCurrentUser() == null) {
                    Toast.makeText(doc_careerDescription_expandedScrn.this, "????????? ????????????!", Toast.LENGTH_SHORT).show();
                } else {
                    //??????????????? ???????????? ?????? "????????? ??????????????????!"?????? ????????? ?????????,
                    //??????????????? ????????? ?????? ?????? ????????? fileName?????? ?????? docName+".docx"??? ????????? ???????????? ??? ??? ??????????????????.
                    if (checkString(fileName)) {
                        Toast.makeText(mContext, "????????? ??????????????????!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "Document processing Start!", Toast.LENGTH_SHORT).show();

                        expandedScrn_name = findViewById(R.id.expandedScrn_name);
                        fileName = expandedScrn_name.getText().toString().trim();
                        docName = intent.getStringExtra("docName");

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


                        name = name_EditText.getText().toString().trim();
                        email = email_EditText.getText().toString().trim();
                        phoneNumber = phoneNumber_EditText.getText().toString().trim();
                        address = address_EditText.getText().toString().trim();

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

                        //???????????? ?????????????????? ????????? ?????? ????????? ???????????? downloadEP???????????? download_with_modify???????????? ????????????.
                        //download_with_modify???????????? Firebase Storage????????? uri??? ??????????????? ???????????? ?????? uri??? downloadFile_with_modify??????????????? ????????????.
                        //downloadFile_with_modify???????????? ???????????? uri??? ?????? DownloadManager????????? long enqueue(Uri uri)???????????? ????????????.
                        //enqueue?????????????????? ???????????? long????????? ?????? PreferenceManager??? ??? "doc_dwnlID"??? ??????????????? ????????????.
                        downloadEP = new DownloadEP(getApplicationContext());
                        downloadEP.download_with_modify(fileName, docName);
                    }
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
            long doc_dwnlID = PreferenceManager.getLong(doc_careerDescription_expandedScrn.this, "doc_dwnlID");
            long img_dwnlID = PreferenceManager.getLong(doc_careerDescription_expandedScrn.this, "img_dwnlID");
            //DownloadManager.ACTION_DOWNLOAD_COMPLETE ???????????? ???????????????,
            //DownloadManager??? ?????? ????????????????????? ???????????? long?????? ???,
            // ????????? downloadFile_with_modify()???????????? ??????????????? PreferenceManager??? ?????????
            // "doc_dwnlID"??? ?????? ?????? ??? ?????? if?????? ????????????.


            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (doc_dwnlID == cmpltDwnlID)) {
                imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");
                //download/ZN????????? profile.jpg?????? ????????? ????????? "Profile Image File Exists!"?????? ?????? ????????? ?????????.
                if (imgFile.exists()){
                    Toast.makeText(mContext, "Profile Image File Exists!", Toast.LENGTH_SHORT).show();
                    //????????? ????????? ????????? ????????? ?????? DOC_DWNL_CMPLT???????????? ???????????????.
                    documentProcess = new Intent(DOC_DWNL_CMPLT);
                    sendBroadcast(documentProcess);
                }
                //????????? ????????? downloadEP ???????????? download_picture()????????????
                // ????????? "Downloading Profile Image"?????? ???????????? ?????????.
                // (download_picture()???????????? download/ZN ????????? "profile.jpg"?????? ???????????? ????????? ???????????? ??????????????????.)
                else{
                    downloadEP.download_picture();
                    Toast.makeText(mContext, "Downloading Profile Image", Toast.LENGTH_SHORT).show();
                }
            }

            //DownloadManager.ACTION_DOWNLOAD_COMPLETE ???????????? ???????????????,
            //DownloadManager??? ?????? ????????????????????? ???????????? long?????? ???, ????????? download_picture()??? ???????????????
            // PreferenceManager??? ????????? "img_dwnlID"??? ?????? ?????? ??? ?????? if?????? ????????????.
            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (img_dwnlID == cmpltDwnlID)
                    || intent.getAction().equals(DOC_DWNL_CMPLT)){
                fileName = fileName+".docx";
                imgFile = new File(Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");

                //????????? ???????????? ????????? ???
                if (imgFile.exists()) {
                    //????????? ????????? ????????? ???????????? ??????
                    String img_path = Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg";
                    //???????????? ????????? ????????? ??????
                    String doc_path = Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DOWNLOADS) + "/ZN/."+fileName;
                    //CustomXWPFDocuemnt()??? runImg()???????????? ?????? ????????? "??????"?????? ???????????? ??????????????? ????????? ????????? ????????? ????????????.
                    //????????? ????????? ??????????????? ???????????? .zip?????? ????????? /word/document.xml?????? <wp:extent cx="?"(??????) cy="?"(??????)/>???????????? ????????????.
                    //behindDoc??? true??? ??? ?????? "??????"??? ????????? ?????? ????????????.
                    new CustomXWPFDocument().runImg("??????",doc_path, img_path, true,
                            1133475, 1510665, 0, 0);//Bookmark replacement picture
                }
                else {
                    Toast.makeText(doc_careerDescription_expandedScrn.this, "No Image File!", Toast.LENGTH_SHORT).show();
                }
                //RUNIMG_CMPLT ???????????? ???????????????.
                documentProcess = new Intent(RUNIMG_CMPLT);
                sendBroadcast(documentProcess);
            }
            //????????? action??? RUNIMG_CMPLT??? ???
            if(intent.getAction().equals(RUNIMG_CMPLT)) {
                docFile = new File(Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);
                //??????????????? ????????? ???
                if (docFile.exists()) {
//                    Toast.makeText(mContext, "Document processing start!", Toast.LENGTH_SHORT).show();
                    try {
                        InputStream is = new FileInputStream(Environment.getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);
                        final FileOutputStream out = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName));

                        //?????? ?????? key : value ??????????????? Map<string, string> data ???????????? ????????????.
                        data.put("name", name);
                        data.put("email", email);
                        data.put("phoneNumber", phoneNumber);
                        data.put("address", address);

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

                        //CustomXWPFDocument???????????? replace???????????? ?????? ?????? ?????? "${key}"??? value????????? ????????????.
                        CustomXWPFDocument c = new CustomXWPFDocument();
                        c.replace(is,data,out);

                        //??????????????? ????????????.
                        docFile.delete();

                        Toast.makeText(doc_careerDescription_expandedScrn.this, "Finished!", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(doc_careerDescription_expandedScrn.this, "No Document File!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        popupMenu.inflate(R.menu.expanded_menu);
        Menu menuOpts = popupMenu.getMenu();

        //PreferenceManager??? ????????? bExpanded??? true??? ???(????????? ?????????) "?????????"??? ??????????????? ??????.
        //false??? ???(????????? ?????? ???) "?????????"??? ?????????????????????.
        bExpanded = PreferenceManager.getBoolean(mContext, "careerbExpanded");
        if(bExpanded){
            menuOpts.getItem(0).setTitle("?????????");
        }
        else{
            menuOpts.getItem(0).setTitle("?????????");

        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.expand:
                        bExpanded = PreferenceManager.getBoolean(mContext, "careerbExpanded");
                        //bExpanded??? false??? ???(????????? ?????? ???) "?????????"??? ?????????
                        //?????????, ?????????, ????????????2, ????????????3, ?????????2, ??????2??? ??????????????? ??????.
                         if(bExpanded == false) {
                            PreferenceManager.setBoolean(mContext, "careerbExpanded", true);
                            university.setVisibility(View.VISIBLE);
                            master.setVisibility(View.VISIBLE);
                            formOfCareer2.setVisibility(View.VISIBLE);
                            formOfCareer3.setVisibility(View.VISIBLE);
//                            license1.setVisibility(View.VISIBLE);
                            license2.setVisibility(View.VISIBLE);
//                            award1.setVisibility(View.VISIBLE);
                            award2.setVisibility(View.VISIBLE);
                            Toast.makeText(doc_careerDescription_expandedScrn.this, "?????? ????????? ?????? ????????????.", Toast.LENGTH_SHORT).show();
                        }
                         //bExpanded??? true??? ???(????????? ?????? ???) "?????????"??? ?????????
                         //?????????, ?????????, ????????????2, ????????????3, ?????????2, ??????2??? ?????? ?????? ????????? ????????????
                         //?????? ?????? ????????? ???????????? ????????? ?????????.
                        else if(bExpanded = true) {
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
                            Toast.makeText(doc_careerDescription_expandedScrn.this, "???????????? ?????? ????????? ?????? ????????????.", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                        
                    //????????? ?????? ??????????????? ???????????? ?????? ???????????? ????????? ????????? ?????? ??????????????? ???????????? ????????? ??? ????????? ??????.
                    case R.id.applyToProfile:
                        name = name_EditText.getText().toString().trim();
                        email = email_EditText.getText().toString().trim();
                        phoneNumber = phoneNumber_EditText.getText().toString().trim();
                        address = address_EditText.getText().toString().trim();

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
                            Toast.makeText(mContext,"????????? ????????????!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            userID = mAuth.getCurrentUser().getUid();
//                            fStore = FirebaseFirestore.getInstance();
                            if (userID != null) {
                                documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
                                user = new HashMap<>();
                                user.put("name", name);
                                user.put("email", email);
                                user.put("phoneNumber", phoneNumber);
                                user.put("address", address);
                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(mContext,"???????????? ?????? ???????????????!",Toast.LENGTH_SHORT).show();
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
//                                        Toast.makeText(mContext,"?????? ???????????? ?????? ???????????????!",Toast.LENGTH_SHORT).show();
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
//                                        Toast.makeText(mContext,"?????? ???????????? ?????? ???????????????!",Toast.LENGTH_SHORT).show();
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
//                                        Toast.makeText(mContext,"?????? ???????????? ?????? ???????????????!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            Toast.makeText(mContext,"???????????? ?????? ???????????????!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                        //???????????? ??????
                    case R.id.moveToProfile:
                        moveProfile = new Intent(mContext, profile_screen.class);
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
//        Toast.makeText(profile_screen.this,account.toString(),Toast.LENGTH_SHORT).show();

        if (isSignedIn()) {
//            Toast.makeText(profile_screen.this,account.toString(),Toast.LENGTH_SHORT).show();
            mAuth = FirebaseAuth.getInstance();
            storageReference = fStorage.getInstance().getReference();
            userID = mAuth.getCurrentUser().getUid();

            //FirebaseFirestore??? collection("users").document(userID)?????? ??????, ?????????, ????????????, ????????? ????????????
            //EditText??? ????????????.
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
            documentReference.addSnapshotListener(doc_careerDescription_expandedScrn.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        name = value.getString("name");
                        email = value.getString("email");
                        phoneNumber = value.getString("phoneNumber");
                        address = value.getString("address");

                        name_EditText.setText(name);
                        email_EditText.setText(email);
                        phoneNumber_EditText.setText(phoneNumber);
                        address_EditText.setText(address);
                    }

                }
            });
            //FirebaseFirestore??? collection("users").document(userID).collection("profiles").document("eduBack")?????? ???????????? ???????????? ????????? ??????????????? ????????????
            //EditText??? ????????????.
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("eduBack");
            documentReference.addSnapshotListener(doc_careerDescription_expandedScrn.this, new EventListener<DocumentSnapshot>() {
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
                }
            });
            //FirebaseFirestore??? collection("users").document(userID).collection("profiles").document("licenses")?????? ???????????? ???????????? ????????? ??????????????????
            //EditText??? ????????????.
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
                }
            });
            //FirebaseFirestore??? collection("users").document(userID).collection("profiles").document("formOfCareer")?????? ???????????? ???????????? ????????? ??????????????? ????????????
            //EditText??? ????????????.
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("formOfCareer");
            documentReference.addSnapshotListener(doc_careerDescription_expandedScrn.this, new EventListener<DocumentSnapshot>() {
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
                }
            });

        }
    }




    //DownloadManager.ACTION_DOWNLOAD_COMPLETE, RUNIMG_CMPLT, DOC_DWNL_CMPLT ???????????? ?????? ??? ????????? ??????
    //broadcastReceiver??? ????????????.
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
    //?????? ??????????????? ???????????? ????????? broadcastReceiver????????? ????????????.
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    
    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // ?????? ?????? ?????? ????????? ???????????? ??? ????????? ?????? ????????? ????????? ?????? ??? (?????? else{..} ?????? ??????)
            // ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_CAMERA);

            // ?????? ???????????? if()?????? ????????? false??? ?????? ??? -> else{..}??? ???????????? ?????????
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("??????")
                        .setMessage("????????? ????????? ?????????????????????. ????????? ???????????? ???????????? ?????? ????????? ?????? ??????????????? ?????????.")
                        .setNeutralButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
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
        switch (requestCode) {
            case MY_PERMISSION_STORAGE:
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : ????????? ????????? 0, ????????? ????????? -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(mContext, "?????? ????????? ????????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // ??????????????? ??? ????????????..
                break;
        }
    }
    boolean checkString(String str) {
        return str == null || str.length() == 0;
    }
    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(getApplicationContext()) != null;
    }


}
