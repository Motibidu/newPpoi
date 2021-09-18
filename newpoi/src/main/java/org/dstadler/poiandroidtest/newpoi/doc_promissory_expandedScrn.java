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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class doc_promissory_expandedScrn extends AppCompatActivity {

    public static int sCorner = 80;
    public static int sMargin = 1;
    public static int sBorder = 0;

    private ImageButton expanded_screen_backButton;
    private Button expanded_screen_download_without_modify, create;

    private TextInputEditText cre_name, cre_add, cre_rrn, deb_name, deb_add, deb_rrn, joi_name, joi_add, joi_rrn, ori, ara, in, gday, pri_rep, year, month, day;
    private TextInputLayout L_cre_name, L_cre_add, L_cre_rrn, L_deb_name, L_deb_add, L_deb_rrn, L_joi_name, L_joi_add, L_joi_rrn, L_ori, L_ara, L_in, L_gday, L_pri_rep, L_year, L_month, L_day;
    private String Scre_name, Scre_add, Scre_rrn, Sdeb_name, Sdeb_add, Sdeb_rrn, Sjoi_name, Sjoi_add, Sjoi_rrn, Sori, Sara, Sin, Sgday, Spri_rep, Syear, Smonth, Sday;
    private String pScre_name, pScre_add, pScre_rrn, pSdeb_name, pSdeb_add, pSdeb_rrn, pSjoi_name, pSjoi_add, pSjoi_rrn, pSori, pSara, pSin, pSgday, pSpri_rep, pSyear, pSmonth, pSday;
    private String imgName, imgPath;
    private Uri imgUri;
    private Intent intent;
    private EditText expanded_screen_name;

    private DownloadEP downloadEP;
    private CustomXWPFDocument customXWPFDocument;
    private FirebaseAuth mAuth;

    private ImageView expanded_screen_mainImageView;
    private String fileName, folder;
    private StorageReference storageReference;

    private static final int MY_PERMISSION_STORAGE = 1111;
    final String DOCUMENT_PROCESS_COMPLETE = "org.dstadler.poiandroidtest.newpoi.DOCUMENT_PROCESS_COMPLETE1";

    private Map<String, String> data = new HashMap<String, String>();

    private FirebaseStorage fStorage;

    private File f;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_promissory_expanded_screen);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.themeColor));

        mAuth= FirebaseAuth.getInstance();

        pScre_name = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Scre_name");
        pScre_add = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Scre_add");
        pScre_rrn = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Scre_rrn");
        pSdeb_name = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Sdeb_name");
        pSdeb_add = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Sdeb_add");
        pSdeb_rrn = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Sdeb_rrn");
        pSjoi_name = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Sjoi_name");
        pSjoi_add = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Sjoi_add");
        pSjoi_rrn = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Sjoi_rrn");
        pSori = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Sori");
        pSara = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Sara");
        pSin = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Sin");
        pSgday = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Sgday");
        pSpri_rep = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Spri_rep");
        pSyear = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Syear");
        pSmonth = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Smonth");
        pSday = PreferenceManager.getString(doc_promissory_expandedScrn.this, "Sday");

        cre_name = findViewById(R.id.editText0); cre_name.setText(pScre_name);
        cre_add = findViewById(R.id.editText1); cre_add.setText(pScre_add);
        cre_rrn = findViewById(R.id.editText2); cre_rrn.setText(pScre_rrn);
        deb_name = findViewById(R.id.editText3); deb_name.setText(pSdeb_name);
        deb_add = findViewById(R.id.editText4); deb_add.setText(pSdeb_add);
        deb_rrn = findViewById(R.id.editText5); deb_rrn.setText(pSdeb_rrn);
        joi_name = findViewById(R.id.editText6); joi_name.setText(pSjoi_name);
        joi_add = findViewById(R.id.editText7); joi_add.setText(pSjoi_add);
        joi_rrn = findViewById(R.id.editText8); joi_rrn.setText(pSjoi_rrn);
        ori = findViewById(R.id.editText10); ori.setText(pSori);
        ara = findViewById(R.id.editText11); ara.setText(pSara);
        in = findViewById(R.id.editText12); in.setText(pSin);
        gday = findViewById(R.id.editText13); gday.setText(pSgday);
        pri_rep = findViewById(R.id.editText14); pri_rep.setText(pSpri_rep);
        year = findViewById(R.id.editText15); year.setText(pSyear);
        month = findViewById(R.id.editText16); month.setText(pSmonth);
        day = findViewById(R.id.editText17); day.setText(pSday);

        L_cre_name = findViewById(R.id.text_input_layout0);
        L_cre_add= findViewById(R.id.text_input_layout1);
        L_cre_rrn= findViewById(R.id.text_input_layout2);
        L_deb_name= findViewById(R.id.text_input_layout3);
        L_deb_add= findViewById(R.id.text_input_layout4);
        L_deb_rrn= findViewById(R.id.text_input_layout5);
        L_joi_name= findViewById(R.id.text_input_layout6);
        L_joi_add= findViewById(R.id.text_input_layout7);
        L_joi_rrn= findViewById(R.id.text_input_layout8);
        L_ori= findViewById(R.id.text_input_layout10);
        L_ara= findViewById(R.id.text_input_layout11);
        L_in= findViewById(R.id.text_input_layout12);
        L_gday= findViewById(R.id.text_input_layout13);
        L_pri_rep= findViewById(R.id.text_input_layout14);
        L_year= findViewById(R.id.text_input_layout15);
        L_month= findViewById(R.id.text_input_layout16);
        L_day= findViewById(R.id.text_input_layout17);



        expanded_screen_backButton = findViewById(R.id.expanded_screen_backButton);
        expanded_screen_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        expanded_screen_mainImageView = findViewById(R.id.expanded_screen_mainImageView);
        intent = getIntent();
        imgPath = intent.getStringExtra("imgPath");
        imgName = intent.getStringExtra("imgName");
        imgUri = Uri.parse(imgPath);


//        expanded_screen_mainImageView.setImageURI(null);
//        expanded_screen_mainImageView.setImageURI(imgUri);

        Glide.with(this).load(imgUri)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.skipMemoryCacheOf(true))
//                .apply(RequestOptions.signatureOf(new ObjectKey(System.currentTimeMillis())))
                .apply(RequestOptions.bitmapTransform(
                        new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(expanded_screen_mainImageView);

        if (imgName.equals("promissory1")){
            L_cre_rrn.setVisibility(View.GONE);
            L_joi_name.setVisibility(View.GONE);
            L_joi_add.setVisibility(View.GONE);
            L_joi_rrn.setVisibility(View.GONE);
            L_cre_rrn.setVisibility(View.GONE);
        }
        if (imgName.equals("promissory2")){
            L_joi_name.setVisibility(View.GONE);
            L_joi_add.setVisibility(View.GONE);
            L_joi_rrn.setVisibility(View.GONE);
        }

        expanded_screen_download_without_modify = findViewById(R.id.expanded_screen_download_without_modify);
        expanded_screen_download_without_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();

                    expanded_screen_name = findViewById(R.id.expanded_screen_name);
                    fileName = expanded_screen_name.getText().toString().trim();

                    if (checkString(fileName)) {
                        Toast.makeText(doc_promissory_expandedScrn.this, "제목을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    } else {
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
                    Toast.makeText(doc_promissory_expandedScrn.this, "로그인 해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    expanded_screen_name = findViewById(R.id.expanded_screen_name);
                    fileName = expanded_screen_name.getText().toString().trim();
                    if (checkString(fileName)) {
                        Toast.makeText(doc_promissory_expandedScrn.this, "제목을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    } else {
                        expanded_screen_name = findViewById(R.id.expanded_screen_name);
                        fileName = expanded_screen_name.getText().toString().trim();
                        imgName = intent.getStringExtra("imgName");

                        Scre_name = cre_name.getText().toString().trim();
                        Scre_add = cre_add.getText().toString().trim();
                        Scre_rrn = cre_rrn.getText().toString().trim();
                        Sdeb_name = deb_name.getText().toString().trim();
                        Sdeb_add = deb_add.getText().toString().trim();
                        Sdeb_rrn = deb_rrn.getText().toString().trim();
                        Sjoi_name = joi_name.getText().toString().trim();
                        Sjoi_add = joi_add.getText().toString().trim();
                        Sjoi_rrn = joi_rrn.getText().toString().trim();
                        Sori = ori.getText().toString().trim();
                        Sara = ara.getText().toString().trim();
                        Sin = in.getText().toString().trim();
                        Sgday = gday.getText().toString().trim();
                        Spri_rep = pri_rep.getText().toString().trim();
                        Syear = year.getText().toString().trim();
                        Smonth = month.getText().toString().trim();
                        Sday = day.getText().toString().trim();

                        downloadEP = new DownloadEP(getApplicationContext());
                        downloadEP.download_with_modify(fileName, imgName);
//                downloadEP.download_picture();
//
//
//                String img_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/증명사진0.jpg";
//                String doc_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/resume0.docx";
//                new CustomXWPFDocument().runImg("사진",doc_path, img_path, true, 1133475, 1510665, 0, 0);//Bookmark replacement picture
                    }
                }
            }
        });

    }


    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            long document_downloadID = PreferenceManager.getLong(doc_promissory_expandedScrn.this, "document_downloadID");


//            Toast.makeText(expanded_screen.this, completeDownloadId + "completeDownloadId", Toast.LENGTH_SHORT).show();
//            Toast.makeText(expanded_screen.this, r_downloadID + "_i_download__broadcast", Toast.LENGTH_SHORT).show();
//            Toast.makeText(promissory_expanded_screen.this, fileName, Toast.LENGTH_SHORT).show();

            if(intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE && (document_downloadID == completeDownloadId)) {
                fileName = fileName+".docx";
                f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);
                if (f.exists()) {
//                    Toast.makeText(promissory_expanded_screen.this, "The file exists!", Toast.LENGTH_SHORT).show();
                    try {
                        InputStream is = new FileInputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);
                        final FileOutputStream out = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName));

                        data.put("cre_name", Scre_name);
                        data.put("cre_add", Scre_add);
                        data.put("cre_rrn", Scre_rrn);
                        data.put("deb_name", Sdeb_name);
                        data.put("deb_add", Sdeb_add);
                        data.put("deb_rrn", Sdeb_rrn);
                        data.put("joi_name", Sjoi_name);
                        data.put("joi_add", Sjoi_add);
                        data.put("joi_rrn", Sjoi_rrn);
                        data.put("ori", Sori);
                        data.put("ara", Sara);
                        data.put("in", Sin);
                        data.put("gday", Sgday);
                        data.put("pri_rep", Spri_rep);
                        data.put("year", Syear);
                        data.put("month", Smonth);
                        data.put("day", Sday);
                        CustomXWPFDocument c = new CustomXWPFDocument();
                        c.replace(is,data,out);

                        f.delete();

                        Intent i = new Intent(DOCUMENT_PROCESS_COMPLETE);
                        sendBroadcast(i);

                        Toast.makeText(doc_promissory_expandedScrn.this, "Finished!", Toast.LENGTH_SHORT).show();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(doc_promissory_expandedScrn.this, "No File!", Toast.LENGTH_SHORT).show();
                }
            }
//            if(intent.getAction().equals(DOCUMENT_PROCESS_COMPLETE)){
//                Toast.makeText(promissory_expanded_screen.this, fileName, Toast.LENGTH_SHORT).show();
//                Log.i("fileName : ", fileName);
//                f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName);
//                if(f.exists()) {
//                    Log.e("thumbNail","file to create thumbnail exists");
//                    InputStream ios = null;
//                    try {
//                        ios = new FileInputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    CustomXWPFDocument wordDocument = null;
//                    try {
//                        wordDocument = new CustomXWPFDocument(ios);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                    ArrayList<PackagePart> packageParts= wordDocument.getPackage().getPartsByRelationshipType("http://schemas.openxmlformats.org/package/2006/relationships/metadata/thumbnail");
////                    PackagePart packagePart = packageParts.get(0);
////                    FileOutputStream fos = null;
////                    try {
////                        fos = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + "thumbnail.jpeg");
////                    } catch (FileNotFoundException e) {
////                        e.printStackTrace();
////                    }
////                    try {
////                        IOUtils.copy(packagePart.getInputStream(), fos);
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                    POIXMLProperties props = wordDocument.getProperties();
//                    POIXMLProperties props = wordDocument.getProperties();
//                    String thumbnail = props.getThumbnailFilename();
//                    if (thumbnail == null) {
//                        Log.i("thumbnail exists : ", "no thumbnail");
//                    } else {
//                        Log.i("thumbnail exists : ", "yes thumbnail");
//                    }
//                    FileOutputStream fos = null;
//                    try {
//                        fos = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + "thumbnail.jpeg");
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        IOUtils.copy(props.getThumbnailImage(), fos);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                else{
//                    Log.e("thumbNailError","file to create thumbnail doesn't exists");
//                }
//
//                Toast.makeText(promissory_expanded_screen.this, "thumbnailFinish", Toast.LENGTH_SHORT).show();
//            }
        }
    };





    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(DOCUMENT_PROCESS_COMPLETE);

        registerReceiver(broadcastReceiver, intentFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
    @Override
    protected void onPause() {
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Scre_name", cre_name.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Scre_add", cre_add.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Scre_rrn", cre_rrn.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Sdeb_name", deb_name.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Sdeb_add", deb_add.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Sdeb_rrn", deb_rrn.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Sjoi_name", joi_name.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Sjoi_add", joi_add.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Sjoi_rrn", joi_rrn.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Sori", ori.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Sara", ara.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Sin", in.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Sgday", gday.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Spri_rep", pri_rep.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Syear", year.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Smonth", month.getText().toString().trim());
        PreferenceManager.setString(doc_promissory_expandedScrn.this, "Sday", day.getText().toString().trim());
        super.onPause();
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
                        Toast.makeText(doc_promissory_expandedScrn.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
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

}
