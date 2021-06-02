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
import android.view.Window;
import android.view.WindowManager;
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

public class careerDescription_expanded_screen extends AppCompatActivity {

    public static int sCorner = 80;
    public static int sMargin = 1;
    public static int sBorder = 0;

    private boolean bExpanded;
    private ImageButton backButton, expanded_screen_menu;
    private Button expanded_screen_download_without_modify, create, expand;

    private TextInputEditText highschool_enterYM_EditText, highschool_graYM_EditText, highschool_name_EditText, highschool_graIfy_EditText,
            university_enterYM_EditText, university_graYM_EditText, university_graIfy_EditText, university_name_EditText, university_major_EditText,
            master_enterYM_EditText, master_graYM_EditText, master_graIfy_EditText, master_name_EditText, master_major_EditText, master_graThe_EditText,master_LAB_EditText;
    private TextInputEditText name_EditText, email_EditText, phoneNumber_EditText, address_EditText;
    private String highschool_enterYM,  highschool_graYM, highschool_name, highschool_graIfy,
            university_enterYM, university_graYM, university_graIfy, university_name, university_major,
            master_enterYM, master_graYM, master_graIfy, master_name, master_major, master_graThe, master_LAB;
    private TextInputEditText formOfCareer0_name_EditText, formOfCareer0_enter_EditText ,formOfCareer0_office_EditText  ,formOfCareer0_task_EditText ,formOfCareer0_resign_EditText
            ,formOfCareer1_name_EditText, formOfCareer1_enter_EditText , formOfCareer1_office_EditText, formOfCareer1_task_EditText, formOfCareer1_resign_EditText
            ,formOfCareer2_name_EditText, formOfCareer2_enter_EditText, formOfCareer2_office_EditText, formOfCareer2_task_EditText, formOfCareer2_resign_EditText;
    private String formOfCareer0_name, formOfCareer0_enter, formOfCareer0_office , formOfCareer0_task, formOfCareer0_resign
            ,formOfCareer1_name, formOfCareer1_enter, formOfCareer1_office, formOfCareer1_task, formOfCareer1_resign
            ,formOfCareer2_name, formOfCareer2_enter, formOfCareer2_office, formOfCareer2_task, formOfCareer2_resign;
    private TextInputEditText license1_date_EditText, license1_content_EditText, license1_grade_EditText, license1_publication_EditText,
            license2_date_EditText, license2_content_EditText, license2_grade_EditText, license2_publication_EditText,
            award1_date_EditText, award1_content_EditText, award1_publication_EditText,
            award2_date_EditText, award2_content_EditText, award2_publication_EditText;

    private String license1_date, license1_content, license1_grade, license1_publication,
            license2_date, license2_content, license2_grade, license2_publication,
            award1_date, award1_content, award1_publication,
            award2_date, award2_content, award2_publication;

    private RelativeLayout master, university, formOfCareer0, formOfCareer1, formOfCareer2, license2, award2,license1, award1;

    private String name, email, phoneNumber, address;




    private String imgName, imgPath;
    private Uri imgUri;
    private Intent intent, moveProfile, documentProcess;
    private EditText expanded_screen_name;

    private DownloadEP downloadEP;
    private CustomXWPFDocument customXWPFDocument;


    private ImageView expanded_screen_mainImageView0, expanded_screen_mainImageView1, expanded_screen_mainImageView2;
    private String fileName, folder;
    private StorageReference storageReference;

    private static final int MY_PERMISSION_STORAGE = 1111;

    private Map<String, String> data = new HashMap<String, String>();

    private FirebaseStorage fStorage;
    private Context context;
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private String userID;
    private DocumentReference documentReference;

    private boolean bDocument = false;
    private boolean bImg = false;
    final String RUNIMGCOMPLETE = "org.dstadler.poiandroidtest.newpoi.runImgComplete";
    final String DOCUMENT_PROCESS_COMPLETE = "org.dstadler.poiandroidtest.newpoi.DOCUMENT_PROCESS_COMPLETE";
    private File imgFile, documentFile;
    private Map<String, Object> user;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_career_description_expanded_screen);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.themeColor));

        context = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
//        userID = mAuth.getCurrentUser().getUid();
        fStore = FirebaseFirestore.getInstance();
        expanded_screen_name = findViewById(R.id.expanded_screen_name);

        name_EditText = findViewById(R.id.name_EditText);
        email_EditText = findViewById(R.id.email_EditText);
        phoneNumber_EditText = findViewById(R.id.phoneNumber_EditText);
        address_EditText = findViewById(R.id.address_EditText);

        highschool_enterYM_EditText = findViewById(R.id.highschool_enterYM_EditText);
        highschool_graYM_EditText = findViewById(R.id.highschool_graYM_EditText);
        highschool_name_EditText = findViewById(R.id.highschool_name_EditText);
        highschool_graIfy_EditText = findViewById(R.id.highschool_graIfy_EditText);

        university_enterYM_EditText = findViewById(R.id.university_enterYM_EditText);
        university_graYM_EditText = findViewById(R.id.university_graYM_EditText);
        university_graIfy_EditText = findViewById(R.id.university_graIfy_EditText);
        university_name_EditText = findViewById(R.id.university_name_EditText);
        university_major_EditText = findViewById(R.id.university_major_EditText);
        university = findViewById(R.id.university);

        master_enterYM_EditText = findViewById(R.id.master_enterYM_EditText);
        master_graYM_EditText = findViewById(R.id.master_graYM_EditText);
        master_graIfy_EditText = findViewById(R.id.master_graIfy_EditText);
        master_name_EditText = findViewById(R.id.master_name_EditText);
        master_major_EditText = findViewById(R.id.master_major_EditText);
        master_graThe_EditText = findViewById(R.id.master_graThe_EditText);
        master_LAB_EditText = findViewById(R.id.master_LAB_EditText);
        master = findViewById(R.id.master);

        formOfCareer0_name_EditText = findViewById(R.id.formOfCareer0_name_EditText);
        formOfCareer0_enter_EditText = findViewById(R.id.formOfCareer0_enter_EditText);
        formOfCareer0_resign_EditText = findViewById(R.id.formOfCareer0_resign_EditText);
        formOfCareer0_office_EditText = findViewById(R.id.formOfCareer0_office_EditText);
        formOfCareer0_task_EditText = findViewById(R.id.formOfCareer0_task_EditText);
        formOfCareer0 = findViewById(R.id.formOfCareer0);

        formOfCareer1_name_EditText = findViewById(R.id.formOfCareer1_name_EditText);
        formOfCareer1_enter_EditText = findViewById(R.id.formOfCareer1_enter_EditText);
        formOfCareer1_resign_EditText = findViewById(R.id.formOfCareer1_resign_EditText);
        formOfCareer1_office_EditText = findViewById(R.id.formOfCareer1_office_EditText);
        formOfCareer1_task_EditText = findViewById(R.id.formOfCareer1_task_EditText);
        formOfCareer1 = findViewById(R.id.formOfCareer1);

        formOfCareer2_name_EditText = findViewById(R.id.formOfCareer2_name_EditText);
        formOfCareer2_enter_EditText = findViewById(R.id.formOfCareer2_enter_EditText);
        formOfCareer2_resign_EditText = findViewById(R.id.formOfCareer2_resign_EditText);
        formOfCareer2_office_EditText = findViewById(R.id.formOfCareer2_office_EditText);
        formOfCareer2_task_EditText = findViewById(R.id.formOfCareer2_task_EditText);
        formOfCareer2 = findViewById(R.id.formOfCareer2);

        license1_date_EditText = findViewById(R.id.license1_date_EditText);
        license1_content_EditText = findViewById(R.id.license1_content_Edittext);
        license1_grade_EditText = findViewById(R.id.license1_grade_EditText);
        license1_publication_EditText = findViewById(R.id.license1_publication_EditText);
        license1 = findViewById(R.id.license1);

        license2_date_EditText = findViewById(R.id.license2_date_EditText);
        license2_content_EditText = findViewById(R.id.license2_content_EditText);
        license2_grade_EditText = findViewById(R.id.license2_grade_EditText);
        license2_publication_EditText = findViewById(R.id.license2_publication_EditText);
        license2 = findViewById(R.id.license2);

        award1_date_EditText = findViewById(R.id.award1_date_EditText);
        award1_content_EditText = findViewById(R.id.award1_content_EditText);
        award1_publication_EditText = findViewById(R.id.award1_publication_EditText);
        award1 = findViewById(R.id.award1);

        award2_date_EditText = findViewById(R.id.award2_date_EditText);
        award2_content_EditText = findViewById(R.id.award2_content_EditText);
        award2_publication_EditText = findViewById(R.id.award2_publication_EditText);
        award2 = findViewById(R.id.award2);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//
        expanded_screen_mainImageView0 = findViewById(R.id.expanded_screen_mainImageView0);
        expanded_screen_mainImageView1 = findViewById(R.id.expanded_screen_mainImageView1);

        intent = getIntent();
        String imgPath1 = intent.getStringExtra("imgPath1");
        String imgPath2 = intent.getStringExtra("imgPath2");

        imgName = intent.getStringExtra("imgName");

        Uri imgUri1 = Uri.parse(imgPath1);
        Uri imgUri2 = Uri.parse(imgPath2);


        Glide.with(this).load(imgUri1)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.bitmapTransform(
                        new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(expanded_screen_mainImageView0);
        Glide.with(this).load(imgUri2)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.bitmapTransform(
                        new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(expanded_screen_mainImageView1);

//
//        if (imgName.equals("promissory1")){
//        }
//        if (imgName.equals("promissory2")){
//        }
//
//
        expanded_screen_download_without_modify = findViewById(R.id.expanded_screen_download_without_modify);
        expanded_screen_download_without_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();

//                expanded_screen_name = findViewById(R.id.expanded_screen_name);
                fileName = expanded_screen_name.getText().toString().trim();
                if(checkString(fileName)){
                    Toast.makeText(context,"제목을 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
                else{
                    imgName = intent.getStringExtra("imgName");

                    downloadEP = new DownloadEP(getApplicationContext());
                    downloadEP.download_without_modify(fileName, imgName);
//                    downloadEP.createThumbnail(fileName, imgName);
                }

            }
        });

//
        create = findViewById(R.id.expanded_screen_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();

                fileName = expanded_screen_name.getText().toString().trim();
                if (mAuth.getCurrentUser() == null) {
                    Toast.makeText(careerDescription_expanded_screen.this, "로그인 해주세요!", Toast.LENGTH_SHORT).show();
                } else {

                    if (checkString(fileName)) {
                        Toast.makeText(context, "제목을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Document processing Start!", Toast.LENGTH_SHORT).show();
                        expanded_screen_name = findViewById(R.id.expanded_screen_name);
                        fileName = expanded_screen_name.getText().toString().trim();
                        imgName = intent.getStringExtra("imgName");

                        highschool_enterYM = highschool_enterYM_EditText.getText().toString().trim();
                        highschool_graYM = highschool_graYM_EditText.getText().toString().trim();
                        highschool_name = highschool_name_EditText.getText().toString().trim();
                        highschool_graIfy = highschool_graIfy_EditText.getText().toString().trim();

                        university_enterYM = university_enterYM_EditText.getText().toString().trim();
                        university_graYM = university_graYM_EditText.getText().toString().trim();
                        university_graIfy = university_graIfy_EditText.getText().toString().trim();
                        university_name = university_name_EditText.getText().toString().trim();
                        university_major = university_major_EditText.getText().toString().trim();

                        master_enterYM = master_enterYM_EditText.getText().toString().trim();
                        master_graYM = master_graYM_EditText.getText().toString().trim();
                        master_graIfy = master_graIfy_EditText.getText().toString().trim();
                        master_name = master_name_EditText.getText().toString().trim();
                        master_major = master_major_EditText.getText().toString().trim();
                        master_graThe = master_graThe_EditText.getText().toString().trim();
                        master_LAB = master_LAB_EditText.getText().toString().trim();


                        name = name_EditText.getText().toString().trim();
                        email = email_EditText.getText().toString().trim();
                        phoneNumber = phoneNumber_EditText.getText().toString().trim();
                        address = address_EditText.getText().toString().trim();

                        formOfCareer0_name = formOfCareer0_name_EditText.getText().toString().trim();
                        formOfCareer0_enter = formOfCareer0_enter_EditText.getText().toString().trim();
                        formOfCareer0_resign = formOfCareer0_resign_EditText.getText().toString().trim();
                        formOfCareer0_office = formOfCareer0_office_EditText.getText().toString().trim();
                        formOfCareer0_task = formOfCareer0_task_EditText.getText().toString().trim();

                        formOfCareer1_name = formOfCareer1_name_EditText.getText().toString().trim();
                        formOfCareer1_enter = formOfCareer1_enter_EditText.getText().toString().trim();
                        formOfCareer1_resign = formOfCareer1_resign_EditText.getText().toString().trim();
                        formOfCareer1_office = formOfCareer1_office_EditText.getText().toString().trim();
                        formOfCareer1_task = formOfCareer1_task_EditText.getText().toString().trim();

                        formOfCareer2_name = formOfCareer2_name_EditText.getText().toString().trim();
                        formOfCareer2_enter = formOfCareer2_enter_EditText.getText().toString().trim();
                        formOfCareer2_resign = formOfCareer2_resign_EditText.getText().toString().trim();
                        formOfCareer2_office = formOfCareer2_office_EditText.getText().toString().trim();
                        formOfCareer2_task = formOfCareer2_task_EditText.getText().toString().trim();

                        license1_date = license1_date_EditText.getText().toString().trim();
                        license1_content = license1_content_EditText.getText().toString().trim();
                        license1_grade = license1_grade_EditText.getText().toString().trim();
                        license1_publication = license1_publication_EditText.getText().toString().trim();

                        license2_date = license2_date_EditText.getText().toString().trim();
                        license2_content = license2_content_EditText.getText().toString().trim();
                        license2_grade = license2_grade_EditText.getText().toString().trim();
                        license2_publication = license2_publication_EditText.getText().toString().trim();

                        award1_date = award1_date_EditText.getText().toString().trim();
                        award1_content = award1_content_EditText.getText().toString().trim();
                        award1_publication = award1_publication_EditText.getText().toString().trim();

                        award2_date = award2_date_EditText.getText().toString().trim();
                        award2_content = award2_content_EditText.getText().toString().trim();
                        award2_publication = award2_publication_EditText.getText().toString().trim();

                        downloadEP = new DownloadEP(getApplicationContext());
                        downloadEP.download_with_modify(fileName, imgName);
                    }
                }
            }
        });

        expanded_screen_menu = findViewById(R.id.expanded_screen_menu);
        expanded_screen_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(expanded_screen_menu);
            }
        });
        if(mAuth.getCurrentUser() == null){
            formOfCareer1.setVisibility(View.GONE);
            formOfCareer2.setVisibility(View.GONE);
            university.setVisibility(View.GONE);
            master.setVisibility(View.GONE);
            license2.setVisibility(View.GONE);
            award2.setVisibility(View.GONE);
        }


    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.expanded_menu);
        Menu menuOpts = popupMenu.getMenu();
        bExpanded = PreferenceManager.getBoolean(context, "careerbExpanded");
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
                        bExpanded = PreferenceManager.getBoolean(context, "careerbExpanded");
                        if(bExpanded == false) {
                            PreferenceManager.setBoolean(context, "careerbExpanded", true);
                            formOfCareer1.setVisibility(View.VISIBLE);
                            formOfCareer2.setVisibility(View.VISIBLE);
                            university.setVisibility(View.VISIBLE);
                            master.setVisibility(View.VISIBLE);
//                            license1.setVisibility(View.VISIBLE);
                            license2.setVisibility(View.VISIBLE);
//                            award1.setVisibility(View.VISIBLE);
                            award2.setVisibility(View.VISIBLE);
                            Toast.makeText(careerDescription_expanded_screen.this, "모든 항목이 펼쳐 졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else if(bExpanded = true) {
                            university_enterYM = university_enterYM_EditText.getText().toString().trim();
                            university_graYM = university_graYM_EditText.getText().toString().trim();
                            university_graIfy = university_graIfy_EditText.getText().toString().trim();
                            university_name = university_name_EditText.getText().toString().trim();
                            university_major = university_major_EditText.getText().toString().trim();

                            master_enterYM = master_enterYM_EditText.getText().toString().trim();
                            master_graYM = master_graYM_EditText.getText().toString().trim();
                            master_graIfy = master_graIfy_EditText.getText().toString().trim();
                            master_name = master_name_EditText.getText().toString().trim();
                            master_major = master_major_EditText.getText().toString().trim();
                            master_graThe = master_graThe_EditText.getText().toString().trim();
                            master_LAB = master_LAB_EditText.getText().toString().trim();

                            formOfCareer1_name = formOfCareer1_name_EditText.getText().toString().trim();
                            formOfCareer1_enter = formOfCareer1_enter_EditText.getText().toString().trim();
                            formOfCareer1_resign = formOfCareer1_resign_EditText.getText().toString().trim();
                            formOfCareer1_office = formOfCareer1_office_EditText.getText().toString().trim();
                            formOfCareer1_task = formOfCareer1_task_EditText.getText().toString().trim();

                            formOfCareer2_name = formOfCareer2_name_EditText.getText().toString().trim();
                            formOfCareer2_enter = formOfCareer2_enter_EditText.getText().toString().trim();
                            formOfCareer2_resign = formOfCareer2_resign_EditText.getText().toString().trim();
                            formOfCareer2_office = formOfCareer2_office_EditText.getText().toString().trim();
                            formOfCareer2_task = formOfCareer2_task_EditText.getText().toString().trim();

                            license1_date = license1_date_EditText.getText().toString().trim();
                            license1_content = license1_content_EditText.getText().toString().trim();
                            license1_grade = license1_grade_EditText.getText().toString().trim();
                            license1_publication = license1_publication_EditText.getText().toString().trim();

                            license2_date = license2_date_EditText.getText().toString().trim();
                            license2_content = license2_content_EditText.getText().toString().trim();
                            license2_grade = license2_grade_EditText.getText().toString().trim();
                            license2_publication = license2_publication_EditText.getText().toString().trim();

                            award1_date = award1_date_EditText.getText().toString().trim();
                            award1_content = award1_content_EditText.getText().toString().trim();
                            award1_publication = award1_publication_EditText.getText().toString().trim();

                            award2_date = award2_date_EditText.getText().toString().trim();
                            award2_content = award2_content_EditText.getText().toString().trim();
                            award2_publication = award2_publication_EditText.getText().toString().trim();

                            PreferenceManager.setBoolean(context, "careerbExpanded", false);

                            if (checkString(formOfCareer1_name) && checkString(formOfCareer1_enter) && checkString(formOfCareer1_resign) && checkString(formOfCareer1_office) &&
                                    checkString(formOfCareer1_task)) {
                                formOfCareer1.setVisibility(View.GONE);
                            }
                            if (checkString(formOfCareer2_name) && checkString(formOfCareer2_enter) && checkString(formOfCareer2_resign) && checkString(formOfCareer2_office) &&
                                    checkString(formOfCareer2_task)) {
                                formOfCareer2.setVisibility(View.GONE);
                            }
                            if (checkString(university_enterYM) && checkString(university_graYM) && checkString(university_graIfy) && checkString(university_name) &&
                                    checkString(university_major)) {
                                university.setVisibility(View.GONE);
                            }
                            if (checkString(master_enterYM) && checkString(master_graYM) && checkString(master_graIfy) && checkString(master_name) &&
                                    checkString(master_major) && checkString(master_graThe) && checkString(master_LAB)) {
                                master.setVisibility(View.GONE);
                            }
//                            if (checkString(license1_date) && checkString(license1_content) && checkString(license1_grade) && checkString(license1_publication)) {
//                                license1.setVisibility(View.GONE);
//                            }
                            if (checkString(license2_date) && checkString(license2_content) && checkString(license2_grade) && checkString(license2_publication)) {
                                license2.setVisibility(View.GONE);
                            }
//                            if (checkString(award1_date) && checkString(award1_content) && checkString(award1_publication)) {
//                                award1.setVisibility(View.GONE);
//                            }
                            if (checkString(award2_date) && checkString(award2_content) && checkString(award2_publication)) {
                                award2.setVisibility(View.GONE);
                            }
                            Toast.makeText(careerDescription_expanded_screen.this, "기록되지 않은 항목이 숨겨 졌습니다.", Toast.LENGTH_SHORT).show();
                        }
                        return true;

                    case R.id.applyToProfile:
                        name = name_EditText.getText().toString().trim();
                        email = email_EditText.getText().toString().trim();
                        phoneNumber = phoneNumber_EditText.getText().toString().trim();
                        address = address_EditText.getText().toString().trim();

                        license1_date = license1_date_EditText.getText().toString().trim();
                        license1_content = license1_content_EditText.getText().toString().trim();
                        license1_grade = license1_grade_EditText.getText().toString().trim();
                        license1_publication = license1_publication_EditText.getText().toString().trim();

                        license2_date = license2_date_EditText.getText().toString().trim();
                        license2_content = license2_content_EditText.getText().toString().trim();
                        license2_grade = license2_grade_EditText.getText().toString().trim();
                        license2_publication = license2_publication_EditText.getText().toString().trim();

                        award1_date = award1_date_EditText.getText().toString().trim();
                        award1_content = award1_content_EditText.getText().toString().trim();
                        award1_publication = award1_publication_EditText.getText().toString().trim();

                        award2_date = award2_date_EditText.getText().toString().trim();
                        award2_content = award2_content_EditText.getText().toString().trim();
                        award2_publication = award2_publication_EditText.getText().toString().trim();
                        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        highschool_enterYM = highschool_enterYM_EditText.getText().toString().trim();
                        highschool_graYM = highschool_graYM_EditText.getText().toString().trim();
                        highschool_name=highschool_name_EditText.getText().toString().trim();
                        highschool_graIfy = highschool_graIfy_EditText.getText().toString().trim();

                        university_enterYM = university_enterYM_EditText.getText().toString().trim();
                        university_graYM=university_graYM_EditText.getText().toString().trim();
                        university_graIfy = university_graIfy_EditText.getText().toString().trim();
                        university_name=university_name_EditText.getText().toString().trim();
                        university_major = university_major_EditText.getText().toString().trim();

                        master_enterYM = master_enterYM_EditText.getText().toString().trim();
                        master_graYM = master_graYM_EditText.getText().toString().trim();
                        master_graIfy = master_graIfy_EditText.getText().toString().trim();
                        master_name = master_name_EditText.getText().toString().trim();
                        master_major = master_major_EditText.getText().toString().trim();
                        master_graThe = master_graThe_EditText.getText().toString().trim();
                        master_LAB = master_LAB_EditText.getText().toString().trim();
                        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                        formOfCareer0_name = formOfCareer0_name_EditText.getText().toString().trim();
                        formOfCareer0_enter = formOfCareer0_enter_EditText.getText().toString().trim();
                        formOfCareer0_resign = formOfCareer0_resign_EditText.getText().toString().trim();
                        formOfCareer0_office = formOfCareer0_office_EditText.getText().toString().trim();
                        formOfCareer0_task = formOfCareer0_task_EditText.getText().toString().trim();

                        formOfCareer1_name = formOfCareer1_name_EditText.getText().toString().trim();
                        formOfCareer1_enter = formOfCareer1_enter_EditText.getText().toString().trim();
                        formOfCareer1_resign = formOfCareer1_resign_EditText.getText().toString().trim();
                        formOfCareer1_office = formOfCareer1_office_EditText.getText().toString().trim();
                        formOfCareer1_task = formOfCareer1_task_EditText.getText().toString().trim();

                        formOfCareer2_name = formOfCareer2_name_EditText.getText().toString().trim();
                        formOfCareer2_enter = formOfCareer2_enter_EditText.getText().toString().trim();
                        formOfCareer2_resign = formOfCareer2_resign_EditText.getText().toString().trim();
                        formOfCareer2_office = formOfCareer2_office_EditText.getText().toString().trim();
                        formOfCareer2_task = formOfCareer2_task_EditText.getText().toString().trim();
                        if(mAuth.getCurrentUser() == null){
                            Toast.makeText(context,"로그인 해주세요!",Toast.LENGTH_SHORT).show();
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
//                                        Toast.makeText(context,"프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                documentReference = fStore.collection("users").document(userID).collection("profiles").document("licenses");
                                user = new HashMap<>();
                                user.put("license1_date", license1_date);
                                user.put("license1_content", license1_content);
                                user.put("license1_grade", license1_grade);
                                user.put("license1_publication", license1_publication);

                                user.put("license2_date", license2_date);
                                user.put("license2_content", license2_content);
                                user.put("license2_grade", license2_grade);
                                user.put("license2_publication", license2_publication);

                                user.put("award1_date", award1_date);
                                user.put("award1_content", award1_content);
                                user.put("award1_publication", award1_publication);

                                user.put("award2_date", award2_date);
                                user.put("award2_content", award2_content);
                                user.put("award2_publication", award2_publication);
                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(context,"수상 프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                documentReference = fStore.collection("users").document(userID).collection("profiles").document("eduBack");
                                user = new HashMap<>();
                                user.put("highschool_enterYM", highschool_enterYM);
                                user.put("highschool_graYM", highschool_graYM);
                                user.put("highschool_name", highschool_name);
                                user.put("highschool_graIfy", highschool_graIfy);
                                user.put("university_enterYM", university_enterYM);
                                user.put("university_graYM", university_graYM);
                                user.put("university_graIfy", university_graIfy);
                                user.put("university_name", university_name);
                                user.put("university_major", university_major);
                                user.put("master_enterYM", master_enterYM);
                                user.put("master_graYM", master_graYM);
                                user.put("master_graIfy", master_graIfy);
                                user.put("master_name", master_name);
                                user.put("master_major", master_major);
                                user.put("master_graThe", master_graThe);
                                user.put("master_LAB", master_LAB);
                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(context,"학력 프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                documentReference = fStore.collection("users").document(userID).collection("profiles").document("formOfCareer");
                                user = new HashMap<>();
                                user.put("formOfCareer0_name", formOfCareer0_name);
                                user.put("formOfCareer0_enter", formOfCareer0_enter);
                                user.put("formOfCareer0_resign", formOfCareer0_resign);
                                user.put("formOfCareer0_office", formOfCareer0_office);
                                user.put("formOfCareer0_task", formOfCareer0_task);

                                user.put("formOfCareer1_name", formOfCareer1_name);
                                user.put("formOfCareer1_enter", formOfCareer1_enter);
                                user.put("formOfCareer1_resign", formOfCareer1_resign);
                                user.put("formOfCareer1_office", formOfCareer1_office);
                                user.put("formOfCareer1_task", formOfCareer1_task);

                                user.put("formOfCareer2_name", formOfCareer2_name);
                                user.put("formOfCareer2_enter", formOfCareer2_enter);
                                user.put("formOfCareer2_resign", formOfCareer2_resign);
                                user.put("formOfCareer2_office", formOfCareer2_office);
                                user.put("formOfCareer2_task", formOfCareer2_task);
                                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(context,"경력 프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            Toast.makeText(context,"프로필에 적용 되었습니다!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    case R.id.moveToProfile:
//                        if(mAuth.getCurrentUser() == null){
//                            moveProfile = new Intent(context, profile_screen.class);
//                            context.startActivity(moveProfile);
//                        }
//                        else {
//                            if (userID != null) {
//                                moveProfile = new Intent(context, profile_setting_resume.class);
//                                context.startActivity(moveProfile);
//                            }
//                        }
                        moveProfile = new Intent(context, profile_screen.class);
                        context.startActivity(moveProfile);

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


            bExpanded = PreferenceManager.getBoolean(context, "careerbExpanded");
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
            documentReference.addSnapshotListener(careerDescription_expanded_screen.this, new EventListener<DocumentSnapshot>() {
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
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("licenses");
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        license1_date = value.getString("license1_date");
                        license1_content = value.getString("license1_content");
                        license1_grade = value.getString("license1_grade");
                        license1_publication = value.getString("license1_publication");
                        if (!bExpanded && checkString(license1_date) && checkString(license1_content) && checkString(license1_grade) && checkString(license1_publication)) {
                            license1.setVisibility(View.GONE);
                        }
                        license2_date = value.getString("license2_date");
                        license2_content = value.getString("license2_content");
                        license2_grade = value.getString("license2_grade");
                        license2_publication = value.getString("license2_publication");
                        if (!bExpanded && checkString(license2_date) && checkString(license2_content) && checkString(license2_grade) && checkString(license2_publication)) {
                            license2.setVisibility(View.GONE);
                        }
                        award1_date = value.getString("award1_date");
                        award1_content = value.getString("award1_content");
                        award1_publication = value.getString("award1_publication");
                        if (!bExpanded &&checkString(award1_date) && checkString(award1_content) && checkString(award1_publication)) {
                            award1.setVisibility(View.GONE);
                        }
                        award2_date = value.getString("award2_date");
                        award2_content = value.getString("award2_content");
                        award2_publication = value.getString("award2_publication");
                        if (!bExpanded && checkString(award2_date) && checkString(award2_content) && checkString(award2_publication)) {
                            award2.setVisibility(View.GONE);
                        }

                        license1_date_EditText.setText(license1_date);
                        license1_content_EditText.setText(license1_content);
                        license1_grade_EditText.setText(license1_grade);
                        license1_publication_EditText.setText(license1_publication);
                        license2_date_EditText.setText(license2_date);
                        license2_content_EditText.setText(license2_content);
                        license2_grade_EditText.setText(license2_grade);
                        license2_publication_EditText.setText(license2_publication);
                        award1_date_EditText.setText(award1_date);
                        award1_content_EditText.setText(award1_content);
                        award1_publication_EditText.setText(award1_publication);
                        award2_date_EditText.setText(award2_date);
                        award2_content_EditText.setText(award2_content);
                        award2_publication_EditText.setText(award2_publication);
                    }
                }
            });
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("formOfCareer");
            documentReference.addSnapshotListener(careerDescription_expanded_screen.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        formOfCareer0_name = value.getString("formOfCareer0_name");
                        formOfCareer0_enter = value.getString("formOfCareer0_enter");
                        formOfCareer0_resign = value.getString("formOfCareer0_resign");
                        formOfCareer0_office = value.getString("formOfCareer0_office");
                        formOfCareer0_task = value.getString("formOfCareer0_task");
//                    if(checkString(formOfCareer0_name)&&checkString(formOfCareer0_enter)&&checkString(formOfCareer0_resign)&&checkString(formOfCareer0_office)&&
//                            checkString(formOfCareer0_task)){
//                        formOfCareer0.setVisibility(View.GONE);
//                    }

                        formOfCareer1_name = value.getString("formOfCareer1_name");
                        formOfCareer1_enter = value.getString("formOfCareer1_enter");
                        formOfCareer1_resign = value.getString("formOfCareer1_resign");
                        formOfCareer1_office = value.getString("formOfCareer1_office");
                        formOfCareer1_task = value.getString("formOfCareer1_task");
                        if (!bExpanded &&checkString(formOfCareer1_name) && checkString(formOfCareer1_enter) && checkString(formOfCareer1_resign) && checkString(formOfCareer1_office) &&
                                checkString(formOfCareer1_task)) {
                            formOfCareer1.setVisibility(View.GONE);
                        }

                        formOfCareer2_name = value.getString("formOfCareer2_name");
                        formOfCareer2_enter = value.getString("formOfCareer2_enter");
                        formOfCareer2_resign = value.getString("formOfCareer2_resign");
                        formOfCareer2_office = value.getString("formOfCareer2_office");
                        formOfCareer2_task = value.getString("formOfCareer2_task");
                        if (!bExpanded &&checkString(formOfCareer2_name) && checkString(formOfCareer2_enter) && checkString(formOfCareer2_resign) && checkString(formOfCareer2_office) &&
                                checkString(formOfCareer2_task)) {
                            formOfCareer2.setVisibility(View.GONE);
                        }

                        formOfCareer0_name_EditText.setText(formOfCareer0_name);
                        formOfCareer0_enter_EditText.setText(formOfCareer0_enter);
                        formOfCareer0_resign_EditText.setText(formOfCareer0_resign);
                        formOfCareer0_office_EditText.setText(formOfCareer0_office);
                        formOfCareer0_task_EditText.setText(formOfCareer0_task);

                        formOfCareer1_name_EditText.setText(formOfCareer1_name);
                        formOfCareer1_enter_EditText.setText(formOfCareer1_enter);
                        formOfCareer1_resign_EditText.setText(formOfCareer1_resign);
                        formOfCareer1_office_EditText.setText(formOfCareer1_office);
                        formOfCareer1_task_EditText.setText(formOfCareer1_task);

                        formOfCareer2_name_EditText.setText(formOfCareer2_name);
                        formOfCareer2_enter_EditText.setText(formOfCareer2_enter);
                        formOfCareer2_resign_EditText.setText(formOfCareer2_resign);
                        formOfCareer2_office_EditText.setText(formOfCareer2_office);
                        formOfCareer2_task_EditText.setText(formOfCareer2_task);
                    }
                }
            });
            documentReference = FirebaseFirestore.getInstance().collection("users").document(userID).collection("profiles").document("eduBack");
            documentReference.addSnapshotListener(careerDescription_expanded_screen.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {
                        highschool_enterYM = value.getString("highschool_enterYM");
                        highschool_graYM = value.getString("highschool_graYM");
                        highschool_name = value.getString("highschool_name");
                        highschool_graIfy = value.getString("highschool_graIfy");

                        university_enterYM = value.getString("university_enterYM");
                        university_graYM = value.getString("university_graYM");
                        university_graIfy = value.getString("university_graIfy");
                        university_name = value.getString("university_name");
                        university_major = value.getString("university_major");
                        if (!bExpanded &&checkString(university_enterYM) && checkString(university_graYM) && checkString(university_graIfy) && checkString(university_name) &&
                                checkString(university_major)) {
                            university.setVisibility(View.GONE);
                        }

                        master_enterYM = value.getString("master_enterYM");
                        master_graYM = value.getString("master_graYM");
                        master_graIfy = value.getString("master_graIfy");
                        master_name = value.getString("master_name");
                        master_major = value.getString("master_major");
                        master_graThe = value.getString("master_graThe");
                        master_LAB = value.getString("master_LAB");
                        if (!bExpanded &&checkString(master_enterYM) && checkString(master_graYM) && checkString(master_graIfy) && checkString(master_name) &&
                                checkString(master_major) && checkString(master_graThe) && checkString(master_LAB)) {
                            master.setVisibility(View.GONE);
                        }
                        highschool_enterYM_EditText.setText(highschool_enterYM);
                        highschool_graYM_EditText.setText(highschool_graYM);
                        highschool_name_EditText.setText(highschool_name);
                        highschool_graIfy_EditText.setText(highschool_graIfy);

                        university_enterYM_EditText.setText(university_enterYM);
                        university_graYM_EditText.setText(university_graYM);
                        university_graIfy_EditText.setText(university_graIfy);
                        university_name_EditText.setText(university_name);
                        university_major_EditText.setText(university_major);

                        master_enterYM_EditText.setText(master_enterYM);
                        master_graYM_EditText.setText(master_graYM);
                        master_graIfy_EditText.setText(master_graIfy);
                        master_name_EditText.setText(master_name);
                        master_major_EditText.setText(master_major);
                        master_graThe_EditText.setText(master_graThe);
                        master_LAB_EditText.setText(master_LAB);
                    }
                }
            });
        }
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            long document_downloadID = PreferenceManager.getLong(careerDescription_expanded_screen.this, "document_downloadID");
            long image_downloadID = PreferenceManager.getLong(careerDescription_expanded_screen.this, "image_downloadID");


            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (document_downloadID == completeDownloadId)) {
                bDocument = true;
                imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");
                if (imgFile.exists()){
                    Toast.makeText(context, "Profile Image File Exists!", Toast.LENGTH_SHORT).show();
                    bImg = true;
                    documentProcess = new Intent(DOCUMENT_PROCESS_COMPLETE);
                    sendBroadcast(documentProcess);
                }
                else{
                    downloadEP.download_picture();
                    Toast.makeText(context, "Downloading Profile Image", Toast.LENGTH_SHORT).show();
                    bImg = true;
                }

            }
//            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (image_downloadID == completeDownloadId)) {
//                bImg=true;
//            }
            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (image_downloadID == completeDownloadId) || intent.getAction().equals(DOCUMENT_PROCESS_COMPLETE)){
                fileName = fileName+".docx";
                File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");

                if (imgFile.exists()) {
//                    Toast.makeText(simpleResume_expanded_screen.this, "The file exists!", Toast.LENGTH_SHORT).show();
                    String img_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg";
                    String doc_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/."+fileName;

                    new CustomXWPFDocument().runImg("사진",doc_path, img_path, true, 1133475, 1510665, 0, 0);//Bookmark replacement picture
                }
                else {
                    Toast.makeText(careerDescription_expanded_screen.this, "No Image File!", Toast.LENGTH_SHORT).show();
                }
                bDocument = false;
                bImg = false;

                documentProcess = new Intent(RUNIMGCOMPLETE);
                sendBroadcast(documentProcess);
            }
//            if(intent.getAction().equals(DOCUMENT_PROCESS_COMPLETE)){
//                fileName = fileName+".docx";
//                File imgFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");
//
//                if (imgFile.exists()) {
////                    Toast.makeText(simpleResume_expanded_screen.this, "The file exists!", Toast.LENGTH_SHORT).show();
//                    String img_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg";
//                    String doc_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/."+fileName;
//
//                    new CustomXWPFDocument().runImg("사진",doc_path, img_path, true, 1133475, 1510665, 0, 0);//Bookmark replacement picture
//                }
//                else {
//                    Toast.makeText(careerDescription_expanded_screen.this, "No Image File!", Toast.LENGTH_SHORT).show();
//                }
//                bDocument = false;
//                bImg = false;
//
//                documentProcess = new Intent(RUNIMGCOMPLETE);
//                sendBroadcast(documentProcess);
//            }

            if(intent.getAction().equals(RUNIMGCOMPLETE)) {
                documentFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);
                if (documentFile.exists()) {
//                    Toast.makeText(context, "Document processing start!", Toast.LENGTH_SHORT).show();
                    try {
                        InputStream is = new FileInputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);
                        final FileOutputStream out = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName));
                        data.put("name", name);
                        data.put("email", email);
                        data.put("phoneNumber", phoneNumber);
                        data.put("address", address);

                        data.put("highschool_enterYM", highschool_enterYM);
                        data.put("highschool_graYM", highschool_graYM);
                        data.put("highschool_graIfy", highschool_graIfy);
                        data.put("highschool_name", highschool_name);

                        data.put("university_enterYM", university_enterYM);
                        data.put("university_graYM", university_graYM);
                        data.put("university_graIfy", university_graIfy);
                        data.put("university_name", university_name);
                        data.put("university_major", university_major);

                        data.put("master_enterYM", master_enterYM);
                        data.put("master_graYM", master_graYM);
                        data.put("master_graIfy", master_graIfy);
                        data.put("master_name", master_name);
                        data.put("master_major", master_major);
                        data.put("master_graThe", master_graThe);
                        data.put("master_LAB", master_LAB);

                        data.put("formOfCareer0_name", formOfCareer0_name);
                        data.put("formOfCareer0_enter", formOfCareer0_enter);
                        data.put("formOfCareer0_resign", formOfCareer0_resign);
                        data.put("formOfCareer0_office", formOfCareer0_office);
                        data.put("formOfCareer0_task", formOfCareer0_task);

                        data.put("formOfCareer1_name", formOfCareer1_name);
                        data.put("formOfCareer1_enter", formOfCareer1_enter);
                        data.put("formOfCareer1_resign", formOfCareer1_resign);
                        data.put("formOfCareer1_office", formOfCareer1_office);
                        data.put("formOfCareer1_task", formOfCareer1_task);

                        data.put("formOfCareer2_name", formOfCareer2_name);
                        data.put("formOfCareer2_enter", formOfCareer2_enter);
                        data.put("formOfCareer2_resign", formOfCareer2_resign);
                        data.put("formOfCareer2_office", formOfCareer2_office);
                        data.put("formOfCareer2_task", formOfCareer2_task);

                        data.put("license1_date", license1_date);
                        data.put("license1_content", license1_content);
                        data.put("license1_grade", license1_grade);
                        data.put("license1_publication", license1_publication);

                        data.put("license2_date", license2_date);
                        data.put("license2_content", license2_content);
                        data.put("license2_grade", license2_grade);
                        data.put("license2_publication", license2_publication);

                        data.put("award1_date", award1_date);
                        data.put("award1_content", award1_content);
                        data.put("award1_publication", award1_publication);

                        data.put("award2_date", award2_date);
                        data.put("award2_content", award2_content);
                        data.put("award2_publication", award2_publication);

                        CustomXWPFDocument c = new CustomXWPFDocument();
                        c.replace(is,data,out);

                        documentFile.delete();
                        Toast.makeText(careerDescription_expanded_screen.this, "Finished!", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(careerDescription_expanded_screen.this, "No Document File!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(RUNIMGCOMPLETE);
        intentFilter.addAction(DOCUMENT_PROCESS_COMPLETE);

        registerReceiver(broadcastReceiver, intentFilter);
//        PreferenceManager.setBoolean(context, "careerbExpanded", false);
        updateUI();
    }
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
        switch (requestCode) {
            case MY_PERMISSION_STORAGE:
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(context, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..
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
