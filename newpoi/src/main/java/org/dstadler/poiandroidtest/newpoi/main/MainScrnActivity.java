package org.dstadler.poiandroidtest.newpoi.main;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.SaveFormat;
import com.aspose.words.SaveOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Constant;
import org.dstadler.poiandroidtest.newpoi.cls.Method;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;
import org.dstadler.poiandroidtest.newpoi.cls.StorageUtil;
import org.dstadler.poiandroidtest.newpoi.gnrtDoc.DocCatActivity;
import org.dstadler.poiandroidtest.newpoi.profile.ProfileScrnActivity;

import java.io.File;
import java.util.List;

public class MainScrnActivity extends AppCompatActivity implements BottomSheetDialog.bottomSheetListener {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private MainRecentItemsFragment MainRecentItemsFragment;
    private main_examples main_examples;
    private main_open main_open;
    private main_bookmarked main_bookmarked;
    private DocCatActivity categoryScrn;
    MaterialToolbar toolbar;
    private long backBtnTime = 0;

    private Context mContext;

    private String[] allPath;
    private List<String> allPath2;
    private File storage;
    private BottomSheetDialog bottomSheetDialog;

    BottomSheetDialog.bottomSheetListener listener;

    private int filePosition;

    private final String TAG = "MAINSCRNACTIVITY";

    private String absolutePath, parentPath, fileName, fileNameWithoutExt;

    private ProgressBar progressBar;

//    private fileHandler handler = new fileHandler();

    private Handler handler1, handler2;




    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scrn);

        progressBar= findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        handler1 = new Handler();
        handler2 = new Handler();

        //require read_external_storage permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }

        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");


        bottomSheetDialog = new BottomSheetDialog();


        mContext = getApplicationContext();


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.themeColor));


        toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileScrnActivity.class);
                startActivity(intent);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_screen: {
                        Intent intent = new Intent(mContext, DocCatActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.search: {
//                        //init allPath
//                        allPath = StorageUtil.getStorageDirectories(mContext);
//
//                        //load allFileList
//                        //     allAbsolutePathList
//                        //     allParentPathList on Constant
//                        for (String path : allPath) {
//                            storage = new File(path);
//                            Method.load_Directory_Files(storage);
//                        }
                        Fragment frg = getSupportFragmentManager().findFragmentByTag("0");
                        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.detach(frg);
                        ft.attach(frg);
                        ft.commit();
                    }
                }
                return false;
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recentitems:
                        setFrag(0);
                        break;
                    case R.id.action_examples:
                        setFrag(1);
                        break;
                    case R.id.action_open:
                        setFrag(2);
                        break;
                    case R.id.action_bookmarked:
                        setFrag(3);
                        break;
                    case R.id.home:
                        finish();
                        break;
                }
                return true;
            }
        });
        MainRecentItemsFragment = new MainRecentItemsFragment();
        main_examples = new main_examples();
        main_open = new main_open();
        main_bookmarked = new main_bookmarked();
        categoryScrn = new DocCatActivity();
        setFrag(0);

    }

    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, MainRecentItemsFragment, "0");
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, main_examples, "1");
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, main_open, "2");
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, main_bookmarked, "3");
                ft.commit();
                break;
        }
    }
    @Override
    public void convertToPDF() throws Exception {
        //convert word file to pdf file, progressbar Start.
        convert2PDFThread convert2PDFThread = new convert2PDFThread();
        convert2PDFThread.start();

        //every 0.5seconds try to make File object. if File object is not null, stop progressbar
        checking2PDFThread checking2PDFThread = new checking2PDFThread();
        checking2PDFThread.start();
    }
    @Override
    public void convertToJPG() throws Exception {
        //convert word file to pdf file, progressbar Start.
        convert2JPGThread convert2JPGThread = new convert2JPGThread();
        convert2JPGThread.start();

        //every 0.5seconds try to make File object. if File object is not null, stop progressbar
        checking2JPGThread checking2JPGThread = new checking2JPGThread();
        checking2JPGThread.start();
    }

    class convert2PDFThread extends Thread{
        private String fileName;
        private String fileNameWithoutExt;
        private String absolutePath;
        private String parentPath;

        private int i;

        public convert2PDFThread(){
            i = PreferenceManager.getInt(mContext,"filePosition");

            fileName = Constant.allFileList.get(i).getName();
            fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");
            absolutePath = Constant.allAbsolutePathList.get(i);
            parentPath = Constant.allParentPathList.get(i);
        }

        public void run() {
            handler1.post(new Runnable() {
                @Override
                public void run() {
//                    Log.d(TAG, "^filePosition :"+i+", ^absolutePath: "+absolutePath+", ^parentPath: "+ parentPath + ", ^fileNameWithoutExt:" + fileNameWithoutExt);
//                    Log.d(TAG, "absolutePath that pdf File will be saved: "+parentPath+"/"+fileNameWithoutExt+".pdf");
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

            Document doc = null;
            try {
                doc = new Document(absolutePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                doc.save(parentPath+"/"+fileNameWithoutExt+".pdf");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class checking2PDFThread extends Thread{
        boolean convertComplete = false;

        public void run() {
            while(!convertComplete) {
                //if can make File object, jump out of a loop
                try {
                    File f = new File(parentPath + "/" + fileNameWithoutExt + ".pdf");
                    convertComplete = true;
                }catch (NullPointerException e){
                    Log.d(TAG, "Converting is not completed yet");
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
                    Toast.makeText(mContext,"Converting word to pdf is completed",Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    class convert2JPGThread extends Thread{
        private String fileName;
        private String fileNameWithoutExt;
        private String absolutePath;
        private String parentPath;
        private SaveOptions saveOptions;

        private int i;

        public convert2JPGThread(){
            i = PreferenceManager.getInt(mContext,"filePosition");

            fileName = Constant.allFileList.get(i).getName();
            fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");
            absolutePath = Constant.allAbsolutePathList.get(i);
            parentPath = Constant.allParentPathList.get(i);

        }

        public void run() {
            handler1.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "^filePosition :"+i+", ^absolutePath: "+absolutePath+", ^parentPath: "+ parentPath + ", ^fileNameWithoutExt:" + fileNameWithoutExt);
                    Log.d(TAG, "/storage/emulated/0/DCIM/Screenshots/"+fileNameWithoutExt+".pdf");
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

            Document doc = null;
            try {
                doc = new Document(absolutePath);
                DocumentBuilder builder = new DocumentBuilder(doc);
                saveOptions = new ImageSaveOptions(SaveFormat.JPEG);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                doc.save("/storage/emulated/0/DCIM/Screenshots/"+fileNameWithoutExt+".jpg", saveOptions);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class checking2JPGThread extends Thread{
        boolean convertComplete = false;
        private File f;

        public void run() {
            while(!convertComplete) {
                //if can make File object, jump out of a loop
                try {
                    f = new File("/storage/emulated/0/DCIM/Screenshots/" + fileNameWithoutExt + ".jpg");

                    convertComplete = true;
                }catch (NullPointerException e){
                    Log.d(TAG, "Converting is not completed yet");
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
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                        final Uri contentUri = Uri.fromFile(new File("/storage/emulated/0/DCIM/Screenshots/" + fileNameWithoutExt + ".jpg"));
//                        scanIntent.setData(contentUri);
//                        sendBroadcast(scanIntent);
//                    } else {
//                        final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("/storage/emulated/0/DCIM/Screenshots/" + fileNameWithoutExt + ".jpg"));
//                        sendBroadcast(intent);
//                    }
                    Toast.makeText(mContext,"Converting word to jpg is completed",Toast.LENGTH_SHORT).show();

                }
            });
        }
    }




}

