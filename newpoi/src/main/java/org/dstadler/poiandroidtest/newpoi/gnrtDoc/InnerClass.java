package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.Edits;
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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.DownloadEP;
import org.dstadler.poiandroidtest.newpoi.cls.RoundedCornersTransformation;
import org.dstadler.poiandroidtest.newpoi.profile.ProfileScrnActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class InnerClass extends AppCompatActivity
{
    private static String TAG;
    protected static final int MY_PERMISSION_STORAGE = 1111;

    //contents
    protected Context mContext;
    protected Activity mActivity;

    //handler for Threads
    protected Handler handler1, handler2;

    //firebase
    protected FirebaseFirestore fStore;
    protected FirebaseAuth mAuth;

    //image attributes
    protected static int sCorner = 80;
    protected static int sMargin = 1;
    protected static int sBorder = 0;

    // vars
    protected String pagePath0, pagePath1, pagePath2;
    protected Uri imgUri0, imgUri1, imgUri2;
    
    //문서의 서순을 나타낸다.
    protected int docNum;

    protected String docName, fileName, fileNameWithExt;

    protected boolean bExpanded = false;


    protected String userID;
    protected File docFile;


    //widgets
    protected ImageButton imageBtn_back, imageBtn_more;
    protected Button btn_download, btn_create;
    protected ProgressBar progressBar;
    protected Calendar calendar;
    protected EditText editText_title;
    protected ImageView image_main0, image_main1, image_main2;



    //firebase
    protected FirebaseStorage fStorage;

    protected DocumentReference documentReference;

    protected Intent intent, moveProfile;
    protected DownloadEP downloadEP;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        imageBtn_back = findViewById(R.id.imageBtn_back);
        progressBar = findViewById(R.id.progressBar);

        image_main0 = findViewById(R.id.image_main0);
        image_main1 = findViewById(R.id.image_main1);
        image_main2 = findViewById(R.id.image_main2);

        editText_title = findViewById(R.id.editText_title);
        btn_download = findViewById(R.id.btn_download);
        btn_create = findViewById(R.id.btn_create);
        imageBtn_more = findViewById(R.id.imageBtn_more);

        intent = getIntentFromEachActivity();

        init_common_vars(getApplicationContext(), this, intent);

        //pagePath0에 값이 있을 때 첫번째 mainImageView에 rounded처리된 imgUri0의 이미지를 설정한다.
        if(!checkString(pagePath0)) {
            imgUri0 = Uri.parse(pagePath0);
            Glide.with(mActivity).load(imgUri0)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(mContext, sCorner, sMargin, "#34ace0", sBorder))).into(image_main0);
        }
        //
        if(!checkString(pagePath1)) {
            imgUri1 = Uri.parse(pagePath1);
            Glide.with(mActivity).load(imgUri1)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(mContext, sCorner, sMargin, "#34ace0", sBorder))).into(image_main1);
        }
        //
        if(!checkString(pagePath2)) {
            imgUri2 = Uri.parse(pagePath2);
            Glide.with(mActivity).load(imgUri2)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(mContext, sCorner, sMargin, "#34ace0", sBorder))).into(image_main2);
        }

        //뒤로가기
        imageBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
        //더보기
        imageBtn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(imageBtn_more);
            }
        });
        //양식만 저장
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
        //생성
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
                    createResultThread createResultThread = new createResultThread(getAllTextFromTextInputEditText());
                    checkingResultThread checkingResultThread = new checkingResultThread();

                    downloadTempFileThread.start();
                    downloadProfileImgThread.start();
                    createResultThread.start();
                    checkingResultThread.start();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
        setAllTextInTextInputEditText();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setAllPreferences();
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

    //init
    protected void init_common_vars(Context context, Activity activity, Intent intent){
        //Content
        mContext = context;
        mActivity = activity;

        //handler for Threads
        handler1 = new Handler();
        handler2 = new Handler();

        //firebase
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //for Calendar, TimePicker
        calendar = Calendar.getInstance();


        //메인 이미지 세팅
        pagePath0 = intent.getStringExtra("pagePath0");
        pagePath1 = intent.getStringExtra("pagePath1");
        pagePath2 = intent.getStringExtra("pagePath2");

        //
        docName = intent.getStringExtra("docName");

    }
    
    
    //Threads
    //프로필 이미지를 다운한다.
    protected class downloadProfileImgThread extends Thread{
        @Override
        public void run() {
            downloadEP = new DownloadEP(mContext);
            downloadEP.download_picture();
        }
    }

    //문서를 다운하기만 한다.
    protected class downloadFileThread extends Thread{
        public void run() {
            docName = intent.getStringExtra("docName");         //default이름
            fileName = editText_title.getText().toString().trim(); //사용자가 입력한 파일이름
            Log.d(TAG, "run: docName: "+docName+", fileName: "+fileName);

            //사용자가 파일 이름 입력을 하지 않았을 때, default이름으로 다운로드 한다.
            if(checkString(fileName)){
                downloadEP = new DownloadEP(mContext);
                downloadEP.download_without_modify(docName, docName);
            }
            //사용자가 파일이름을 입력했을 때
            else{
                downloadEP = new DownloadEP(mContext);
                downloadEP.download_without_modify(fileName, docName);
            }
        }
    }
    //문서 다운이 끝났는지 체크한다.
    protected class checkingDownloadFileThread extends Thread{
        boolean downloadComplete = false;
        File f;

        public void run() {
            docName = intent.getStringExtra("docName");         //default이름
            fileName = editText_title.getText().toString().trim(); //사용자가 입력한 파일이름
            if(fileName.isEmpty()) {
                Log.d(TAG, "run/absolutePath : "+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/"+docName+".docx");
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
    protected class downloadTempFileThread extends Thread{
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
                downloadEP = new DownloadEP(mContext);
                downloadEP.download_with_modify(docName, docName);
            } else {
                downloadEP = new DownloadEP(mContext);
                downloadEP.download_with_modify(fileName, docName);
            }
        }
    }
    //임시파일 다운로드가 끝나면 텍스트와 프로필 이미지를 문서 내에 대체시킨다.
    protected class createResultThread extends Thread{
        boolean downloadComplete = false;
        HashMap<String, String> attrs;
        File f;
        File profileImgFile;
        InputStream is = null;
        Document document = null;
        public createResultThread(HashMap<String, String> attrs){
            this.attrs = attrs;
        }

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
                fileNameWithExt = docName+".docx";
            }
            else{
                docFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/." + fileName + ".docx");
                fileNameWithExt = fileName+".docx";
            }

            Log.d(TAG, "onReceive/fileNameWithExt : "+fileNameWithExt);
            //임시파일이 존재할 때
            if (docFile.exists()) {
                try {
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

                    Set set = attrs.entrySet();
                    Iterator iterator = set.iterator();
                    Map.Entry entry;
                    //<11> Logd
//                    for(int i = 0; i<attrs.size(); i++){
//                        Log.d(TAG, "createResultThread/ "+ attrs.get() +": "+attrs[i]);
//                    }
//                    for(int j = 0; j<attrs.length; j++){
//                        if(attrs[j].isEmpty()){
//                            range.replace(attrs[j], "", new FindReplaceOptions());
//                        }else{
//                            range.replace(attrs[j], attrs[j], new FindReplaceOptions());}
//                        }
//                    } catch (Exception e) {
//                    e.printStackTrace();
//                }
                    while(iterator.hasNext()){
                        entry = (Map.Entry)iterator.next();
                        String key = (String)entry.getKey();
                        String value = (String)entry.getValue();
                        Log.d(TAG, "createResultThread/ "+ key +": "+value);
                        if(attrs.get(key).isEmpty()){
                            range.replace(key, "", new FindReplaceOptions());
                        }else{
                            range.replace(key, value, new FindReplaceOptions());}
                        }
                    } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    document.save(Environment.getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_DOWNLOADS) + "/ZN/" + fileNameWithExt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //임시파일을 삭제한다.
                    docFile.delete();
            }
        }
    }
    //텍스트, 프로필 이미지 대체가 끝났는지 체크한다.
    protected class checkingResultThread extends Thread{
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


    protected boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(getApplicationContext()) != null;
    }
    protected boolean checkString(String str) {
        return str == null || str.length() == 0;
    }
    private void showPopup(View v) {
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
                            //applyAll_Firestore();
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
    //단일 함수
    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 다시 보지 않기 버튼을 만드려면 이 부분에 바로 요청을 하도록 하면 됨 (아래 else{..} 부분 제거)
            // ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_CAMERA);

            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(mContext)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                intent.setData(Uri.parse("package:" + getPackageName()));
                                mActivity.startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mActivity.finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
            }
        }
    }

//    private void attachDatepickerDialog(ArrayList<LinearLayout, TextInputEditText> ){
//        final int year = calendar.get(Calendar.YEAR);
//        final int month = calendar.get(Calendar.MONTH);
//        final int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        final int[] hour = new int[1];
//        final int[] minute = new int[1];
//
//        for (LinearLayout LinearLayout : LinearLayout_list){
//            LinearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(
//                            mActivity, new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                            i1 = i1+1;
//                            String date = i+"년 "+i1+"월 "+i2+"일";
//                            TextInputEditText_tD.setText(date);
//                        }
//                    }, year, month, day);
//                    datePickerDialog.show();
//                }
//            });
//        }
//    }
    private void attachCalendarPickerListener(){

    }

    //가상함수
    protected abstract int getLayoutResourceId();
    protected abstract Intent getIntentFromEachActivity();
    protected abstract void updateUI();
    protected abstract void setAllTextInTextInputEditText();
    protected abstract HashMap<String, String> getAllTextFromTextInputEditText();
    protected abstract void setAllPreferences();


}
