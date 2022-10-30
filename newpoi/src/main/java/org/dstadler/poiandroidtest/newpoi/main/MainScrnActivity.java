package org.dstadler.poiandroidtest.newpoi.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
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
import java.util.ArrayList;
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
    private File f;
    private Uri photoURI;



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

        //initiation
        //contents
        mContext = getApplicationContext();

        //widgets
        progressBar= findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.topAppBar);

        //materials
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        //os
        handler1 = new Handler();
        handler2 = new Handler();

        //instances
        bottomSheetDialog = new BottomSheetDialog();
        MainRecentItemsFragment = new MainRecentItemsFragment();
        main_examples = new main_examples();
        main_open = new main_open();
        main_bookmarked = new main_bookmarked();
        categoryScrn = new DocCatActivity();

        //setStatusBarColor by themeColor
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.themeColor));

        //require read_external_storage permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
        //setProperty for duplicate classes
        setPropertyThread setPropertyThread = new setPropertyThread();
        setPropertyThread.start();


        //initialization recentItems
        loadDirectoryThread loadDirectoryThread = new loadDirectoryThread();
        loadDirectoryThread.start();
        loadDirectoryChecking loadDirectoryChecking = new loadDirectoryChecking();
        loadDirectoryChecking.start();




        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileScrnActivity.class);
                startActivity(intent);
            }
        });


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
        setFrag(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_screen){
            Intent intent = new Intent(mContext, DocCatActivity.class);
            startActivity(intent);

        }

        else if(item.getItemId() == R.id.refresh){
            loadDirectoryThread loadDirectoryThread = new loadDirectoryThread();
            loadDirectoryThread.start();

            loadDirectoryChecking loadDirectoryChecking = new loadDirectoryChecking();
            loadDirectoryChecking.start();
        }
        return false;
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

    @Override
    public void open() {

        ArrayList<String> pref_allFileNameList = PreferenceManager.loadData(mContext, "pref_allFileNameList");
        ArrayList<String> pref_allAbsolutePathList = PreferenceManager.loadData(mContext, "pref_allAbsolutePathList");
        ArrayList<String> pref_allParentPathList = PreferenceManager.loadData(mContext, "pref_allParentPathList");
        int i =PreferenceManager.getInt(mContext,"filePosition");
        Log.d(TAG, "open()/filePosition : "+ Integer.toString(i));

        if (pref_allFileNameList.isEmpty()){
            fileName = Constant.allFileList.get(i).getName();
            fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");
            absolutePath = Constant.allAbsolutePathList.get(i);
            parentPath = Constant.allParentPathList.get(i);
        }else{
            fileName = pref_allFileNameList.get(i);
            fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");
            absolutePath = pref_allAbsolutePathList.get(i);
            parentPath = pref_allParentPathList.get(i);
        }

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/msword";

        if(fileName.endsWith(".doc")) {
            f = new File(parentPath + "/" + fileNameWithoutExt + ".doc");
        }else{
            f = new File(parentPath + "/" + fileNameWithoutExt + ".docx");
        }

//        intent.setDataAndType(Uri.fromFile(f), type);
        Log.d(TAG, "open/parentPath/fileNameWithoutExt/.doc : " +parentPath + "/" + fileNameWithoutExt + ".doc");
        photoURI = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", f);
        intent.setDataAndType(photoURI, type);
        startActivity(intent);
    }

    @Override
    public File delete() {
        ArrayList<String> pref_allFileNameList = PreferenceManager.loadData(mContext, "pref_allFileNameList");
        ArrayList<String> pref_allAbsolutePathList = PreferenceManager.loadData(mContext, "pref_allAbsolutePathList");
        ArrayList<String> pref_allParentPathList = PreferenceManager.loadData(mContext, "pref_allParentPathList");
        int i =PreferenceManager.getInt(mContext,"filePosition");
        Log.d(TAG, "delete()/filePosition : "+ Integer.toString(i));

        if (pref_allFileNameList.isEmpty()){
            fileName = Constant.allFileList.get(i).getName();
            fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");
            absolutePath = Constant.allAbsolutePathList.get(i);
            parentPath = Constant.allParentPathList.get(i);
        }else{
            fileName = pref_allFileNameList.get(i);
            fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");
            absolutePath = pref_allAbsolutePathList.get(i);
            parentPath = pref_allParentPathList.get(i);
        }

//        pref_allFileNameList = PreferenceManager.loadData(mContext, "pref_allFileNameList");
        pref_allFileNameList.remove(PreferenceManager.getInt(mContext,"filePosition"));
        PreferenceManager.saveData(mContext,"pref_allFileNameList",pref_allFileNameList);

//        pref_allFileNameList = PreferenceManager.loadData(mContext, "pref_allAbsolutePathList");
        pref_allAbsolutePathList.remove(PreferenceManager.getInt(mContext,"filePosition"));
        PreferenceManager.saveData(mContext,"pref_allAbsolutePathList",pref_allAbsolutePathList);

//        pref_allParentPathList = PreferenceManager.loadData(mContext, "pref_allParentPathList");
        pref_allParentPathList.remove(PreferenceManager.getInt(mContext,"filePosition"));
        PreferenceManager.saveData(mContext,"pref_allParentPathList",pref_allParentPathList);

        if(fileName.endsWith(".doc")) {
            f = new File(parentPath + "/" + fileNameWithoutExt + ".doc");
        }else{
            f = new File(parentPath + "/" + fileNameWithoutExt + ".docx");
        }
        f.delete();
        loadDirectoryThread loadDirectoryThread = new loadDirectoryThread();
        loadDirectoryThread.start();

        loadDirectoryChecking loadDirectoryChecking = new loadDirectoryChecking();
        loadDirectoryChecking.start();

        return f;
    }

    class convert2PDFThread extends Thread{
        private String fileName;
        private String fileNameWithoutExt;
        private String absolutePath;
        private String parentPath;

        private int i;

        public convert2PDFThread(){
            ArrayList<String> pref_allFileNameList = PreferenceManager.loadData(mContext, "pref_allFileNameList");
            ArrayList<String> pref_allAbsolutePathList = PreferenceManager.loadData(mContext, "pref_allAbsolutePathList");
            ArrayList<String> pref_allParentPathList = PreferenceManager.loadData(mContext, "pref_allParentPathList");
            int i =PreferenceManager.getInt(mContext,"filePosition");

            if (pref_allFileNameList.isEmpty()){
                fileName = Constant.allFileList.get(i).getName();
                fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");
                absolutePath = Constant.allAbsolutePathList.get(i);
                parentPath = Constant.allParentPathList.get(i);
            }else{
                fileName = pref_allFileNameList.get(i);
                fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");
                absolutePath = pref_allAbsolutePathList.get(i);
                parentPath = pref_allParentPathList.get(i);
            }
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
            ArrayList<String> pref_allFileNameList = PreferenceManager.loadData(mContext, "pref_allFileNameList");
            ArrayList<String> pref_allAbsolutePathList = PreferenceManager.loadData(mContext, "pref_allAbsolutePathList");
            ArrayList<String> pref_allParentPathList = PreferenceManager.loadData(mContext, "pref_allParentPathList");
            int i =PreferenceManager.getInt(mContext,"filePosition");

            if (pref_allFileNameList.isEmpty()){
                fileName = Constant.allFileList.get(i).getName();
                fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");
                absolutePath = Constant.allAbsolutePathList.get(i);
                parentPath = Constant.allParentPathList.get(i);
            }else{
                fileName = pref_allFileNameList.get(i);
                fileNameWithoutExt = fileName.replaceFirst("[.][^.]+$", "");
                absolutePath = pref_allAbsolutePathList.get(i);
                parentPath = pref_allParentPathList.get(i);
            }

        }

        public void run() {
            handler1.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "^filePosition :"+i+", ^absolutePath: "+absolutePath+", ^parentPath: "+ parentPath + ", ^fileNameWithoutExt:" + fileNameWithoutExt);
//                    Log.d(TAG, "/storage/emulated/0/DCIM/Screenshots/"+fileNameWithoutExt+".pdf");
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

                MediaScannerConnection.scanFile(mContext, new String[] { "/storage/emulated/0/DCIM/Screenshots/"+fileNameWithoutExt+".jpg" }, new String[] { "image/jpeg" }, null);
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
                    Toast.makeText(mContext,"Converting word to jpg is completed",Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    class loadDirectoryThread extends Thread{
        public void run() {
            Constant.allFileList.clear();
            Constant.allFileNameList.clear();
            Constant.allAbsolutePathList.clear();
            Constant.allParentPathList.clear();
            handler1.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

            allPath = StorageUtil.getStorageDirectories(mContext);
            for (String path : allPath) {
                storage = new File(path);
                Method.load_Directory_Files(storage);
            }

            PreferenceManager.setBoolean(mContext,"loadDirectoryComplete", true);
        }
    }

    class loadDirectoryChecking extends Thread{
        public void run() {
            PreferenceManager.setBoolean(mContext,"loadDirectoryComplete", false);
            while(!PreferenceManager.getBoolean(mContext,"loadDirectoryComplete")) {
                try {
                    Log.d(TAG, "DDING DDONG");
                    Thread.sleep(500);
                } catch (Exception e) {}
            }

            handler2.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);

                    PreferenceManager.saveData(mContext, "pref_allFileNameList", Constant.allFileNameList);
                    PreferenceManager.saveData(mContext, "pref_allAbsolutePathList", Constant.allAbsolutePathList);
                    PreferenceManager.saveData(mContext, "pref_allParentPathList", Constant.allParentPathList);
                    for(String fileName : Constant.allFileNameList){
                        Log.d(TAG, "run/fileName "+fileName);
                    }


                    Fragment frg = getSupportFragmentManager().findFragmentByTag("0");
                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.detach(frg);
                    ft.attach(frg);
                    ft.commit();



                    Log.d(TAG, "run: loadDirectory is completed");
                    Toast.makeText(mContext,"loadDirectory is completed",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    static class setPropertyThread extends Thread{
        @Override
        public void run() {
            System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
            System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
            System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }
}

