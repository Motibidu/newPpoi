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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class expanded_screen extends AppCompatActivity {

    public static int sCorner = 80;
    public static int sMargin = 1;
    public static int sBorder = 0;
    public static String sColor = "#34ace0";


    private ImageButton expanded_screen_backButton;
    private Button expanded_screen_download_without_modify, create;

    private TextInputEditText e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17;
    private String Se0, Se1, Se2, Se3, Se4, Se5, Se6, Se7, Se8, Se9, Se10, Se11, Se12, Se13, Se14, Se15, Se16, Se17;
    private String pSe0, pSe1, pSe2, pSe3, pSe4, pSe5, pSe6, pSe7, pSe8, pSe9, pSe10, pSe11, pSe12, pSe13, pSe14, pSe15, pSe16, pSe17;
    private EditText expanded_screen_name;

    private ReceiverManager rm;

    private ImageView expanded_screen_mainImageView;
    private String folder, fileName, filePath, title_growth;
    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    private StorageReference storageReference;
    private Uri fetchUri;
    public Uri return_retrievedURI;
    private static final int MY_PERMISSION_STORAGE = 1111;

    private Map<String, String> data = new HashMap<String, String>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expanded_screen);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.themeColor));
        rm = new ReceiverManager(expanded_screen.this);


        mAuth = FirebaseAuth.getInstance();
        storageReference = fStorage.getInstance().getReference();
        pSe0 = PreferenceManager.getString(expanded_screen.this, "Se0");
        pSe1 = PreferenceManager.getString(expanded_screen.this, "Se1");
        pSe2 = PreferenceManager.getString(expanded_screen.this, "Se2");
        pSe3 = PreferenceManager.getString(expanded_screen.this, "Se3");
        pSe4 = PreferenceManager.getString(expanded_screen.this, "Se4");
        pSe5 = PreferenceManager.getString(expanded_screen.this, "Se5");
        pSe6 = PreferenceManager.getString(expanded_screen.this, "Se6");
        pSe7 = PreferenceManager.getString(expanded_screen.this, "Se7");
        pSe8 = PreferenceManager.getString(expanded_screen.this, "Se8");
        pSe10 = PreferenceManager.getString(expanded_screen.this, "Se10");
        pSe11 = PreferenceManager.getString(expanded_screen.this, "Se11");
        pSe12 = PreferenceManager.getString(expanded_screen.this, "Se12");
        pSe13 = PreferenceManager.getString(expanded_screen.this, "Se13");
        pSe14 = PreferenceManager.getString(expanded_screen.this, "Se14");
        pSe15 = PreferenceManager.getString(expanded_screen.this, "Se15");
        pSe16 = PreferenceManager.getString(expanded_screen.this, "Se16");
        pSe17 = PreferenceManager.getString(expanded_screen.this, "Se17");


        e0 = findViewById(R.id.editText0);
        e0.setText(pSe0);
        e1 = findViewById(R.id.editText1);
        e1.setText(pSe1);
        e2 = findViewById(R.id.editText2);
        e2.setText(pSe2);
        e3 = findViewById(R.id.editText3);
        e3.setText(pSe3);
        e4 = findViewById(R.id.editText4);
        e4.setText(pSe4);
        e5 = findViewById(R.id.editText5);
        e5.setText(pSe5);
        e6 = findViewById(R.id.editText6);
        e6.setText(pSe6);
        e7 = findViewById(R.id.editText7);
        e7.setText(pSe7);
        e8 = findViewById(R.id.editText8);
        e8.setText(pSe8);
        e10 = findViewById(R.id.editText10);
        e10.setText(pSe10);
        e11 = findViewById(R.id.editText11);
        e11.setText(pSe11);
        e12 = findViewById(R.id.editText12);
        e12.setText(pSe12);
        e13 = findViewById(R.id.editText13);
        e13.setText(pSe13);
        e14 = findViewById(R.id.editText14);
        e14.setText(pSe14);
        e15 = findViewById(R.id.editText15);
        e15.setText(pSe15);
        e16 = findViewById(R.id.editText16);
        e16.setText(pSe16);
        e17 = findViewById(R.id.editText17);
        e17.setText(pSe17);


        expanded_screen_backButton = findViewById(R.id.expanded_screen_backButton);
        expanded_screen_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        expanded_screen_mainImageView = findViewById(R.id.expanded_screen_mainImageView);

        Intent intent = getIntent();
        String imgPath = intent.getStringExtra("imgPath");
        Uri imgUri = Uri.parse(imgPath);

//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(expanded_screen.this)
//                // You can pass your own memory cache implementation
//                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
//                .build();
//
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .displayer(new RoundedBitmapDisplayer(10)) //rounded corner bitmap
//                .cacheInMemory(true)
//                .cacheOnDisc(true)
//                .build();
//
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.init(config);
//        imageLoader.displayImage(String.valueOf(imgUri),expanded_screen_mainImageView, options);

//        expanded_screen_mainImageView.setImageURI(null);
        Glide.with(this).load(imgUri)
                .apply(RequestOptions.bitmapTransform(
                        new RoundedCornersTransformation(this, sCorner, sMargin, "#34ace0", sBorder))).into(expanded_screen_mainImageView);
        expanded_screen_mainImageView.setImageURI(imgUri);

        expanded_screen_download_without_modify = findViewById(R.id.expanded_screen_download_without_modify);
        expanded_screen_download_without_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileName = "promissory0.docx";
                checkPermission();
                download_without_modify(fileName);
            }
        });


        create = findViewById(R.id.expanded_screen_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                fileName = "차용증(워드).docx";

                expanded_screen_name = findViewById(R.id.expanded_screen_name);
                fileName = expanded_screen_name.getText().toString().trim();

                Se0 = e0.getText().toString().trim();
                Se1 = e1.getText().toString().trim();
                Se2 = e2.getText().toString().trim();
                Se3 = e3.getText().toString().trim();
                Se4 = e4.getText().toString().trim();
                Se5 = e5.getText().toString().trim();
                Se6 = e6.getText().toString().trim();
                Se7 = e7.getText().toString().trim();
                Se8 = e8.getText().toString().trim();
                Se10 = e10.getText().toString().trim();
                Se11 = e11.getText().toString().trim();
                Se12 = e12.getText().toString().trim();
                Se13 = e13.getText().toString().trim();
                Se14 = e14.getText().toString().trim();
                Se15 = e15.getText().toString().trim();
                Se16 = e16.getText().toString().trim();
                Se17 = e17.getText().toString().trim();

                checkPermission();
                download_with_modify(fileName, rm);


//                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/ZN/"+fileName);
//                if(f.exists()){
//                    Toast.makeText(expanded_screen.this,"The file exists!",Toast.LENGTH_SHORT).show();
//                    try {
//                        InputStream is = new FileInputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/ZN/"+fileName);
//                    final FileOutputStream out = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/ZN/edited_"+fileName));
//                        data.put("cre_name",Se0);
//                        data.put("cre_add",Se1);
//                        data.put("cre_rrn",Se2);
//                        data.put("deb_name",Se3);
//                        data.put("deb_add",Se4);
//                        data.put("deb_rrn",Se5);
//                        data.put("joi_name",Se6);
//                        data.put("joi_add",Se7);
//                        data.put("joi_rrn",Se8);
//                        data.put("ori",Se10);
//                        data.put("ara",Se11);
//                        data.put("in",Se12);
//                        data.put("gday",Se13);
//                        data.put("pri_rep",Se14);
//                        data.put("year",Se15);
//                        data.put("month",Se16);
//                        data.put("day",Se17);
//
//                        replace(is,data,out);
//
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }   catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }else{
//                    Toast.makeText(expanded_screen.this,"Start Downloading!",Toast.LENGTH_SHORT).show();
//                    download_with_modify(fileName, rm);
//                }
//                Toast.makeText(expanded_screen.this,"Finished!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            long r_downloadID = PreferenceManager.getLong(expanded_screen.this, "r_downloadID");
//            String i_downloadID = intent.getExtras().getString("adsf");
            fileName = fileName+".docx";
//            Toast.makeText(expanded_screen.this, completeDownloadId + "completeDownloadId", Toast.LENGTH_SHORT).show();
            Toast.makeText(expanded_screen.this, r_downloadID + "_i_download__broadcast", Toast.LENGTH_SHORT).show();
            Toast.makeText(expanded_screen.this, fileName + "fileName", Toast.LENGTH_SHORT).show();

//            long r_downloadID = PreferenceManager.getLong(expanded_screen.this, "r_downloadID");
//            long i_downloadID = intent.getLongExtra("i_downloadID", -1);
//            Toast.makeText(expanded_screen.this, completeDownloadId+"_r_download_broadcast", Toast.LENGTH_SHORT).show();

            if(r_downloadID == completeDownloadId) {

                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);

                if (f.exists()) {
                    Toast.makeText(expanded_screen.this, "The file exists!", Toast.LENGTH_SHORT).show();
                    try {
                        InputStream is = new FileInputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName);
                        final FileOutputStream out = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileName));
                        data.put("cre_name", Se0);
                        data.put("cre_add", Se1);
                        data.put("cre_rrn", Se2);
                        data.put("deb_name", Se3);
                        data.put("deb_add", Se4);
                        data.put("deb_rrn", Se5);
                        data.put("joi_name", Se6);
                        data.put("joi_add", Se7);
                        data.put("joi_rrn", Se8);
                        data.put("ori", Se10);
                        data.put("ara", Se11);
                        data.put("in", Se12);
                        data.put("gday", Se13);
                        data.put("pri_rep", Se14);
                        data.put("year", Se15);
                        data.put("month", Se16);
                        data.put("day", Se17);

                        replace(is, data, out);
                        f.delete();
                        Toast.makeText(expanded_screen.this, "Finished!", Toast.LENGTH_SHORT).show();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(expanded_screen.this, "No File!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onPause() {
        PreferenceManager.setString(expanded_screen.this, "Se0", e0.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se1", e1.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se2", e2.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se3", e3.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se4", e4.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se5", e5.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se6", e6.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se7", e7.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se8", e8.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se10", e10.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se11", e11.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se12", e12.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se13", e13.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se14", e14.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se15", e15.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se16", e16.getText().toString().trim());
        PreferenceManager.setString(expanded_screen.this, "Se17", e17.getText().toString().trim());
        super.onPause();
    }

    private void replace(InputStream is, Map<String, String> data, OutputStream out) throws Exception, IOException {
        XWPFDocument docx = new XWPFDocument(OPCPackage.open(is));
        for (XWPFParagraph p : docx.getParagraphs()) {
            replace2(p, data);
        }
        for (XWPFTable tbl : docx.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        replace2(p, data);
                    }
                }
            }
        }
        docx.write(out);
        docx.close();
    }

    private void replace2(XWPFParagraph p, Map<String, String> data) {
        String pText = p.getText(); // complete paragraph as string
        if (pText.contains("${")) { // if paragraph does not include our pattern, ignore
            TreeMap<Integer, XWPFRun> posRuns = getPosToRuns(p);
            Pattern pat = Pattern.compile("\\$\\{(.+?)\\}");
            Matcher m = pat.matcher(pText);
            while (m.find()) { // for all patterns in the paragraph
                String g = m.group(1);  // extract key start and end pos
                int s = m.start(1);
                int e = m.end(1);
                String key = g;
                String x = data.get(key);
                if (x == null)
                    x = "";
                SortedMap<Integer, XWPFRun> range = posRuns.subMap(s - 2, true, e + 1, true); // get runs which contain the pattern
                boolean found1 = false; // found $
                boolean found2 = false; // found {
                boolean found3 = false; // found }
                XWPFRun prevRun = null; // previous run handled in the loop
                XWPFRun found2Run = null; // run in which { was found
                int found2Pos = -1; // pos of { within above run
                for (XWPFRun r : range.values())
                {
                    if (r == prevRun)
                        continue; // this run has already been handled
                    if (found3)
                        break; // done working on current key pattern
                    prevRun = r;
                    for (int k = 0;; k++) { // iterate over texts of run r
                        if (found3)
                            break;
                        String txt = null;
                        try {
                            txt = r.getText(k); // note: should return null, but throws exception if the text does not exist
                        } catch (Exception ex) {

                        }
                        if (txt == null)
                            break; // no more texts in the run, exit loop
                        if (txt.contains("$") && !found1) {  // found $, replace it with value from data map
                            txt = txt.replaceFirst("\\$", x);
                            found1 = true;
                        }
                        if (txt.contains("{") && !found2 && found1) {
                            found2Run = r; // found { replace it with empty string and remember location
                            found2Pos = txt.indexOf('{');
                            txt = txt.replaceFirst("\\{", "");
                            found2 = true;
                        }
                        if (found1 && found2 && !found3) { // find } and set all chars between { and } to blank
                            if (txt.contains("}"))
                            {
                                if (r == found2Run)
                                { // complete pattern was within a single run
                                    txt = txt.substring(0, found2Pos)+txt.substring(txt.indexOf('}'));
                                }
                                else // pattern spread across multiple runs
                                    txt = txt.substring(txt.indexOf('}'));
                            }
                            else if (r == found2Run) // same run as { but no }, remove all text starting at {
                                txt = txt.substring(0,  found2Pos);
                            else
                                txt = ""; // run between { and }, set text to blank
                        }
                        if (txt.contains("}") && !found3) {
                            txt = txt.replaceFirst("\\}", "");
                            found3 = true;
                        }
                        r.setText(txt, k);
                    }
                }
            }
            System.out.println(p.getText());

        }

    }

    private TreeMap<Integer, XWPFRun> getPosToRuns(XWPFParagraph paragraph) {
        int pos = 0;
        TreeMap<Integer, XWPFRun> map = new TreeMap<Integer, XWPFRun>();
        for (XWPFRun run : paragraph.getRuns()) {
            String runText = run.text();
            if (runText != null && runText.length() > 0) {
                for (int i = 0; i < runText.length(); i++) {
                    map.put(pos + i, run);
                }
                pos += runText.length();
            }

        }
        return map;
    }

    private void download_without_modify(String fileName){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        StorageReference profileRef = storageReference.child("Documents/"+"promissory0.docx");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                downloadFile_without_modify(expanded_screen.this, fileName,".docx", folder, uri.toString());
            }
        });
    }
    private void downloadFile_without_modify(Context context, String pfileName, String fileExtension, String destinationDirectory, String url){
//        int idx = pfileName.indexOf('.');
//        String fileName = pfileName.substring(0, idx);

        DownloadManager downloadManager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setVisibleInDownloadsUi (true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/ZN/"+fileName+fileExtension);

        downloadManager.enqueue(request);
    }
    private void download_with_modify(String fileName, ReceiverManager rm){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        StorageReference profileRef = storageReference.child("Documents/"+"promissory0.docx");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Toast.makeText(expanded_screen.this, fileName+"_download_with_modify", Toast.LENGTH_SHORT).show();
                downloadFile_with_modify(expanded_screen.this, fileName,".docx", folder, uri.toString(), rm);
            }
        });
    }

    private void downloadFile_with_modify(Context context, String pfileName, String fileExtension, String destinationDirectory, String url,
    ReceiverManager rm){
//        int idx = pfileName.indexOf('.');
//        String fileName = pfileName.substring(0, idx);
//        Toast.makeText(expanded_screen.this, fileName.toString()+"_downloadFile_with_modify", Toast.LENGTH_SHORT).show();

        DownloadManager downloadManager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setVisibleInDownloadsUi (true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/ZN/."+fileName+fileExtension);

        long downloadID = downloadManager.enqueue(request);
        Toast.makeText(expanded_screen.this, downloadID+"_downloadFile_with_modify", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intent.putExtra("fileName", fileName);
//        intent.putExtra("i_downloadID",downloadID);
        PreferenceManager.setLong(expanded_screen.this, "r_downloadID", downloadID);

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(broadcastReceiver, filter);
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
                        Toast.makeText(expanded_screen.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..

                break;
        }
    }

}
