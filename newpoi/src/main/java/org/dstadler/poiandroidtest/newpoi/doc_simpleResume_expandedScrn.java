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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
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

public class doc_simpleResume_expandedScrn extends AppCompatActivity {

    public static int sCorner = 80;
    public static int sMargin = 1;
    public static int sBorder = 0;

    private ImageButton expanded_screen_backButton;
    private TextInputEditText name, e_name, ch_name, rrn, age, email, SNS, phoneNumber, number, address;
    private String str_name, str_e_name, str_ch_name, str_rrn, str_age, str_email, str_SNS, str_phoneNumber, str_number, str_address;
    private Button download_without_modify, create;


    private DownloadEP downloadEP;
    private AppCompatActivity activity;
    private Context context;
    private String userID;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    private StorageReference storageReference;


    private String Se0, Se1, Se2, Se3, Se4, Se5, Se6, Se7, Se8, Se9, Se10, Se11, Se12, Se13, Se14, Se15, Se16, Se17;
    private String pSe0, pSe1, pSe2, pSe3, pSe4, pSe5, pSe6, pSe7, pSe8, pSe9, pSe10, pSe11, pSe12, pSe13, pSe14, pSe15, pSe16, pSe17;
    private String imgName;
    private EditText expanded_screen_name;


    private ImageView expanded_screen_mainImageView0, expanded_screen_mainImageView1;
    private String fileName, folder;

    private static final int MY_PERMISSION_STORAGE = 1111;

    private Map<String, String> data = new HashMap<String, String>();

    private boolean bDocument = false;
    private boolean bImg = false;
    final String RUNIMGCOMPLETE = "org.dstadler.poiandroidtest.newpoi.runImgComplete";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_simple_resume_expanded_screen);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.themeColor));
        activity = doc_simpleResume_expandedScrn.this;
        context = getApplicationContext();

        expanded_screen_backButton = findViewById(R.id.expanded_screen_backButton);
        expanded_screen_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        expanded_screen_mainImageView0 = findViewById(R.id.expanded_screen_mainImageView0);
        Intent intent = getIntent();
        String imgPath = intent.getStringExtra("imgPath");
        Uri imgUri = Uri.parse(imgPath);

        Glide.with(this).load(imgUri)
                .apply(RequestOptions.bitmapTransform(
                        new RoundedCornersTransformation(
                                this, sCorner, sMargin, "#34ace0", sBorder))).into(expanded_screen_mainImageView0);
        expanded_screen_mainImageView0.setImageURI(imgUri);

        name = findViewById(R.id.name);
        e_name = findViewById(R.id.e_name);
        ch_name = findViewById(R.id.ch_name);
        rrn = findViewById(R.id.rrn);
        age = findViewById(R.id.age);
        phoneNumber = findViewById(R.id.phoneNumber);
        number = findViewById(R.id.number);
        email = findViewById(R.id.email);
        SNS = findViewById(R.id.SNS);
        address = findViewById(R.id.address);
        updateUI();

        download_without_modify = findViewById(R.id.expanded_screen_download_without_modify);
        download_without_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
                if(checkString(fileName)){
                    Toast.makeText(context,"제목을 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
                else {

                    expanded_screen_name = findViewById(R.id.expanded_screen_name);
                    fileName = expanded_screen_name.getText().toString().trim();
                    imgName = intent.getStringExtra("imgName");

                    downloadEP = new DownloadEP(getApplicationContext());
                    downloadEP.download_without_modify(fileName, imgName);
                }
            }
        });

        create = findViewById(R.id.expanded_screen_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
                if (mAuth.getCurrentUser() == null) {
                    Toast.makeText(doc_simpleResume_expandedScrn.this, "로그인 해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkString(fileName)) {
                        Toast.makeText(context, "제목을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    } else {
                        expanded_screen_name = findViewById(R.id.expanded_screen_name);
                        fileName = expanded_screen_name.getText().toString().trim();
                        imgName = intent.getStringExtra("imgName");

                        downloadEP = new DownloadEP(getApplicationContext());
                        Toast.makeText(context, "Downloading Document File!", Toast.LENGTH_SHORT).show();
                        downloadEP.download_with_modify(fileName, imgName);
                    }
                }


            }
        });
    }
    protected void onStart() {
        super.onStart();
//        IntentFilter Downloadfilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//        IntentFilter runImgCompletefilter = new IntentFilter(RUNIMGCOMPLETE);
//        registerReceiver(broadcastReceiver, Downloadfilter);
//        registerReceiver(broadcastReceiver, runImgCompletefilter);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(RUNIMGCOMPLETE);

        registerReceiver(broadcastReceiver, intentFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            long document_downloadID = PreferenceManager.getLong(doc_simpleResume_expandedScrn.this, "document_downloadID");
            long image_downloadID = PreferenceManager.getLong(doc_simpleResume_expandedScrn.this, "image_downloadID");


            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (document_downloadID == completeDownloadId)) {
                bDocument = true;
                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");
                if (f.exists()){
                    Toast.makeText(context, "Profile Image File Exists!", Toast.LENGTH_SHORT).show();
                    bImg = true;
                }
                else{
                    downloadEP.download_picture();
                    Toast.makeText(context, "Downloading Profile Image", Toast.LENGTH_SHORT).show();
                }
            }
            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (image_downloadID == completeDownloadId)) {
                bImg=true;
            }
            if(bDocument&&bImg){
                fileName = fileName+".docx";
                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);

                if (f.exists()) {
//                    Toast.makeText(simpleResume_expanded_screen.this, "The file exists!", Toast.LENGTH_SHORT).show();
                    String img_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg";
                    String doc_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/."+fileName;

                    new CustomXWPFDocument().runImg("사진",doc_path, img_path, true, 1133475, 1510665, 0, 0);//Bookmark replacement picture
                }
                else {
                    Toast.makeText(doc_simpleResume_expandedScrn.this, "No File!", Toast.LENGTH_SHORT).show();
                }
                bDocument = false;
                bImg = false;
                Intent i = new Intent(RUNIMGCOMPLETE);
                sendBroadcast(i);
            }
            if(intent.getAction().equals(RUNIMGCOMPLETE)) {
                Toast.makeText(context, "Document processing start!", Toast.LENGTH_SHORT).show();
                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);
                if (f.exists()) {
//                    Toast.makeText(context, "The file exists!", Toast.LENGTH_SHORT).show();
                    try {
                        InputStream is = new FileInputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);
                        final FileOutputStream out = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName));
                        new CustomXWPFDocument().replace(is, data, out);
                        f.delete();
                        Toast.makeText(context, "Finished!", Toast.LENGTH_SHORT).show();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
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
                        Toast.makeText(doc_simpleResume_expandedScrn.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..
                break;
        }
    }

    private void updateUI(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
//        Toast.makeText(profile_screen.this,account.toString(),Toast.LENGTH_SHORT).show();

        if(account != null){
//            Toast.makeText(profile_screen.this,account.toString(),Toast.LENGTH_SHORT).show();
            mAuth = FirebaseAuth.getInstance();
            storageReference = fStorage.getInstance().getReference();
            if(mAuth.getCurrentUser() != null) {
                userID = mAuth.getCurrentUser().getUid();
            }
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(userID);
            documentReference.addSnapshotListener(doc_simpleResume_expandedScrn.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                    str_name = value.getString("name");
                    str_e_name = value.getString("e_name");
                    str_ch_name = value.getString("ch_name");
                    str_rrn = value.getString("rrn");
                    str_age = value.getString("age");
                    str_phoneNumber = value.getString("phoneNumber");
                    str_number = value.getString("number");
                    str_email = value.getString("email");
                    str_SNS = value.getString("SNS");
                    str_address = value.getString("address");

                    name.setText(str_name);
                    e_name.setText(str_e_name);
                    ch_name.setText(str_ch_name);
                    rrn.setText(str_rrn);
                    age.setText(str_age);
                    email.setText(str_email);
                    SNS.setText(str_SNS);
                    phoneNumber.setText(str_phoneNumber);
                    number.setText(str_number);
                    address.setText(str_address);
                }
            });
        }
        else if (account == null){
            name.setText("");
            e_name.setText("");
            ch_name.setText("");
            rrn.setText("");
            age.setText("");
            email.setText("");
            SNS.setText("");
            phoneNumber.setText("");
            number.setText("");
            address.setText("");
        }
    }
    boolean checkString(String str) {
        return str == null || str.length() == 0;
    }

}
