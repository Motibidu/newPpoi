package org.dstadler.poiandroidtest.newpoi.profile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.customMatcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class oriScanActivity extends AppCompatActivity {
    //static vars
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final String TAG = "MAIN_TAG";

    //widgets

    private ImageButton backBtn;

    private LinearLayout inputImg;
    private Button recognizeText;
    private ImageView importedImg;

    private EditText edit_name, edit_engName, edit_chName, edit_rrn, edit_phoneNum,
            edit_addr, edit_email, edit_age;
    private ImageButton imageButton_name, imageButton_engName, imageButton_chName, imageButton_rrn, imageButton_phoneNum,
            imageButton_addr, imageButton_email;

    //contents
    private Context mContext;

    //Permissions
    private String[] cameraPermissions;
    private String[] storagePermissions;

    //vars
    private String p;
    private Uri imageUri = null;


    //apps
    private ProgressDialog progressDialog;

    //TextRecognizer
    private TextRecognizer textRecognizer;

    //ArrayList<String>
    List<String> name = new ArrayList<String>();
    List<String> engName = new ArrayList<String>();
    List<String> chName = new ArrayList<String>();

    List<String> rnn = new ArrayList<String>();
    List<String> email = new ArrayList<String>();
    List<String> addr = new ArrayList<String>();
    List<String> phoneNum = new ArrayList<String>();
    List<String> url = new ArrayList<String>();
    List<String> schl = new ArrayList<String>();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oriactivity_scan);

        //뒤로가기 버튼
        backBtn = findViewById(R.id.imagebutton_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //widgets
        inputImg = findViewById(R.id.inputImg);
        recognizeText = findViewById(R.id.button_recognizeText);
        importedImg = findViewById(R.id.image_importedImg);

        edit_name = findViewById(R.id.edit_name);
        edit_engName = findViewById(R.id.edit_engName);
        edit_chName = findViewById(R.id.edit_chName);
        edit_rrn = findViewById(R.id.edit_rrn);
        edit_age = findViewById(R.id.edit_age);
        edit_phoneNum = findViewById(R.id.edit_phoneNum);
        edit_email = findViewById(R.id.edit_email);
        edit_addr = findViewById(R.id.edit_addr);

        //contents
        mContext = getApplicationContext();

        //permissions
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //apps
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        //init TextRecognizer
        textRecognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());

        inputImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputImageDialog(inputImg);
            }
        });

        recognizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri == null){
                    Toast.makeText(mContext,"Pick image first...",Toast.LENGTH_SHORT).show();
                }
                else{
                    recognizeTextFromImage();
                }
            }
        });

        imageButton_name = findViewById(R.id.imagebutton_name);
        imageButton_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_name, name);
            }
        });
        imageButton_engName = findViewById(R.id.imagebutton_engName);
        imageButton_engName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_engName, engName);
            }
        });
        imageButton_chName = findViewById(R.id.imagebutton_chName);
        imageButton_chName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_chName, chName);
            }
        });
        imageButton_rrn = findViewById(R.id.imagebutton_rrn);
        imageButton_rrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_rrn, rnn);
            }
        });

        imageButton_phoneNum = findViewById(R.id.imagebutton_phoneNum);
        imageButton_phoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_phoneNum, phoneNum);
            }
        });
        imageButton_email = findViewById(R.id.imagebutton_email);
        imageButton_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_email, email);
            }
        });
        imageButton_addr = findViewById(R.id.imagebutton_addr);
        imageButton_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_addr, addr);
            }
        });
    }

    private void setNameMenu(View view, EditText editText,List<String> list){
        PopupMenu menu = new PopupMenu(mContext, view);
        for(int i=0; i< list.size();i++){
            menu.getMenu().add(Menu.NONE, i, i, list.get(i));
        }
        menu.show();
        if(view.getId() == R.id.imagebutton_rrn) {
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    editText.setText(menuItem.getTitle());
                    setAge();
                    return true;
                }
            });
        }
        else{
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    editText.setText(menuItem.getTitle());
                    return true;
                }
            });

        }
    }

    private void recognizeTextFromImage(){
        Log.d(TAG, "recognizeTextFromImage: ");
        progressDialog.setMessage("Preparing image...");
        progressDialog.show();

        try {
            InputImage inputImage = InputImage.fromFilePath(this, imageUri);

            progressDialog.setMessage("Recognizing text...");

            Task<Text> textTaskResult = textRecognizer.process(inputImage).
                    addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {
                            progressDialog.dismiss();;
                            String recognizedText = text.getText();
                            List<Text.TextBlock> recognizedTextBlock = text.getTextBlocks();

                            stringProcess(recognizedText);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.e(TAG, "onFailure : ", e);
                            Toast.makeText(oriScanActivity.this,"Failed recognizing text due to "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            //Exception occurred while preparing InputImage, dismiss dialog, show reason in Toast
            progressDialog.dismiss();
            Log.e(TAG,"recognizeTextFromImage : ", e);
            Toast.makeText(oriScanActivity.this,"Failed preparing image due to "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    private void stringProcess(String str){

        //clear Arraylist
//        rnn.clear();
//        email.clear();
//        addr.clear();
//        phoneNum.clear();
//        url.clear();
//        schl.clear();
//        name.clear();
        //split string by a space
        String[] splitStr_n = str.split("\\n+");
        String[] splitStr_s = str.split("\\s+");

        //regexes
        //주민번호 앞자리
        // (.*)\d{2}                    : 00 ~ 99 (출생년도)
        // (0[1-9]|1[0-2])              : 01 ~ 12(월)
        // (0[1-9]|[12][0-9]|[3][01])   : 01 ~ 31 (일)
        //주민번호 뒷자리
        // [1-4]                        : 1~4 (첫자리)
        // [0-9]{6}                     : (나머지 6자리)

        //rnn_regx1 테스트 :
        //1987년 01월 01일 (만 00세)
        //1999.10,
        //1999년 10월 06일,
        //90.01.01

        customMatcher cus = new customMatcher();

        int i = 0;
        String sp;


        i=0;
        for (String s: splitStr_n) {
            i++;
            Log.d(TAG, i + " : " +s);
        }
        Log.d(TAG, "Fi===========ni===========sh");
        int j = 0;
        for (String s: splitStr_n){
            j++;
            cus.set_splitStr(s);

            cus.set_name();
            if(cus.find()){
                sp = cus.group();
                if(!(sp.contains("이메일")||sp.contains("주소")||sp.contains("분야")||
                        sp.contains("이력서")||sp.contains("이수")||sp.contains("전공명")||
                        sp.contains("이름")||sp.contains("주민번호")||sp.contains("날짜")||
                        sp.contains("관리")||sp.contains("전공")||sp.contains("서울"))) {
                    Log.d(TAG, j+":name : " + sp);
                    if(!name.contains(sp)){name.add(sp);}
                }
            }
            cus.set_engName();
            if(cus.find()) {
                sp = cus.group();
                if(!engName.contains(sp)) {engName.add(sp);}
                Log.d(TAG, j+":engName : "+sp);
            }
            cus.set_chName();
            if(cus.find()) {
                sp = cus.group();
                if(!chName.contains(sp)) {chName.add(sp);}
                Log.d(TAG, j+":chName : "+sp);
                continue;
            }

            cus.set_rnn1();
            if(cus.find()) {
                sp = cus.group();
                if(!rnn.contains(sp)) {rnn.add(sp);}
                Log.d(TAG, j+":rnn1 : "+sp);
            }

            cus.set_rnn2();
            if(cus.find()) {
                sp = cus.group();
                if(!rnn.contains(sp)) {rnn.add(sp);}
                Log.d(TAG, j+":rnn2 : "+sp);
            }

            cus.set_rnn3();
            if(cus.find()) {
                sp = cus.group();
                //rnn.add(sp);
                if(!rnn.contains(sp)) {rnn.add(sp);}
                Log.d(TAG, j+":rnn3 : "+sp);
                continue;
            }

            cus.set_phoneNum();
            if(cus.find()){
                sp = cus.group();
                Log.d(TAG, j+":phoneNum : "+sp);
                //phoneNum.add(sp);
                if(!phoneNum.contains(sp)) {phoneNum.add(sp);}
                continue;
            }
            cus.set_email();
            if(cus.find()){
                sp = cus.group();
                Log.d(TAG, j+":email : "+sp);
                //email.add(sp);
                if(!email.contains(sp)) {email.add(sp);}
                continue;
            }

            cus.set_addr();
            if(cus.find()){
                sp = cus.group();
                Log.d(TAG, j+":addr : "+sp);
                //addr.add(sp);
                if(!addr.contains(sp)) {addr.add(sp);}
                continue;
            }

        }
        Log.d(TAG, "Fi===========ni===========sh");

        //edit_name, edit_rrn, edit_phoneNumber, edit_addr, edit_email, edit_age
        if (name.size() != 0){
            edit_name.setText(name.get(name.size()-1));
            Log.d(TAG, "edit_name : " + name.get(name.size()-1));
        }
        else{
            edit_name.setText("");
        }
        if (engName.size() != 0){
            edit_engName.setText(engName.get(engName.size()-1));
            Log.d(TAG, name.get(name.size()-1));
        }
        else{
            edit_engName.setText("");
        }
        if (chName.size() != 0){
            edit_chName.setText(chName.get(chName.size()-1));
            Log.d(TAG, name.get(chName.size()-1));
        }
        else{
            edit_chName.setText("");
        }

        if (rnn.size() != 0){
            edit_rrn.setText(rnn.get(rnn.size()-1));
            Log.d(TAG, rnn.get(rnn.size()-1));
        }
        else{
            edit_rrn.setText("");
        }

        if (phoneNum.size() != 0){
            edit_phoneNum.setText(phoneNum.get(phoneNum.size()-1));
            Log.d(TAG, phoneNum.get(phoneNum.size()-1));
        }
        else{
            edit_phoneNum.setText("");
        }

        if (email.size() != 0){
            edit_email.setText(email.get(email.size()-1));
            Log.d(TAG, email.get(email.size()-1));
        }
        else{
            edit_email.setText("");
        }

        if (addr.size() != 0){
            edit_addr.setText(addr.get(addr.size()-1));
            Log.d(TAG, addr.get(addr.size()-1));
        }
        else{
            edit_addr.setText("");
        }
        setAge();


    }

    private void setAge(){
        String prrn = edit_rrn.getText().toString();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String[] splitprrn = new String[3];

        if(prrn.length()>0) {
            //String[] splitprrn = prrn.split(".");
            if(prrn.contains(".")){
                splitprrn = prrn.split("\\.");
                Log.d(TAG, "splitrrn[0] : "+ splitprrn[0]);
            }
            else  if(prrn.contains("년")){
                splitprrn = prrn.split("년");
                Log.d(TAG, "splitrrn[0] : "+ splitprrn[0]);
            }
            else if(prrn.contains(" ")){
                splitprrn = prrn.split("\\s");
                Log.d(TAG, "splitrrn[0] : "+ splitprrn[0]);
            }
            else if(prrn.contains(",")){
                splitprrn = prrn.split(",");
                Log.d(TAG, "splitrrn[0] : "+ splitprrn[0]);
            }

            if(splitprrn[0].length() == 2){
                String firstChar = Character.toString(splitprrn[0].charAt(0));
                Log.d(TAG, "firshChar : "+ firstChar);
                //68, 71, 86, 92
                if(firstChar.equals("6")||firstChar.equals("7")||firstChar.equals("8") || firstChar.equals("9")) {
                    Log.d(TAG, "condition1 : "+ Integer.toString(year - Integer.parseInt(splitprrn[0]) - 1899));
                    edit_age.setText(Integer.toString(year - Integer.parseInt(splitprrn[0]) - 1899));
                }
                //05, 12, 13, 21
                else if(firstChar.equals("0")||firstChar.equals("1")|firstChar.equals("2")) {
                    Log.d(TAG, "condition2 : "+ Integer.toString(year - Integer.parseInt(splitprrn[0]) - 1999));
                    edit_age.setText(Integer.toString(year - Integer.parseInt(splitprrn[0]) - 1999));
                }
            }
            else if(splitprrn[0].length() == 4){
                Log.d(TAG, "condition3 : "+ Integer.toString(year - Integer.parseInt(splitprrn[0]) + 1 ));
                edit_age.setText(Integer.toString(year - Integer.parseInt(splitprrn[0]) + 1));
            }
        }
    }


    private void showInputImageDialog(View v) {
        //Toast.makeText(ScanActivity.this,"Take Image Clicked",Toast.LENGTH_SHORT).show();

        PopupMenu popupMenu = new PopupMenu(mContext, v);
        popupMenu.inflate(R.menu.activity_scan);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //get item id that is clicked from PopupMenu
                switch(menuItem.getItemId()) {
                    case R.id.camera:
                        //if camera is clicked ,check if camera permissions are granted or not
                        Log.d(TAG, "onMenuItemClick : Camera Clicked...");
                        if (checkCameraPermissions()) {
                            pickImageCamera();
                        } else {
                            requestCameraPermissions();
                        }
                        return true;
                    case R.id.gallery:
                        Log.d(TAG, "onMenuItemClick : Gallery Clicked...");
                        if (checkStoragePermission()) {
                            pickImageGallery();
                        } else {
                            requestStoragePermission();
                        }
                        return true;
                    default:
                        return false;
                    }

            }


        });
        popupMenu.show();
    }

    private void pickImageGallery() {
        Log.d(TAG, "pickImageGallery: ");
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");
        galleryActivityResultLauncher.launch(intent);
    }

    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        imageUri = data.getData();
                        Log.d(TAG, "onActivityResult: imageUri : "+imageUri);
                        importedImg.setImageURI(imageUri);
                    } else {
                        Log.d(TAG, "onActivityResult: ");
                        Toast.makeText(oriScanActivity.this, "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void pickImageCamera() {
        Log.d(TAG, "pickImageCamera: ");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Sample Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Sample Description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cameraActivityResultLauncher.launch(intent);
    }

    private ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //here we will receive the image, if taken from camera
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //image is taken from camera
                        //we already have the image in imageUri using function pickImageCamera()
                        Log.d(TAG, "onActivityResult: imageUri : "+imageUri);
                        importedImg.setImageURI(imageUri);
                    } else {
                        Log.d(TAG, "onActivityResult: cancelled");
                        Toast.makeText(oriScanActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }

                }
            }
    );

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermissions() {
        boolean cameraResult = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean storageResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return cameraResult && storageResult;
    }

    private void requestCameraPermissions(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    //handle permission results


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted&&storageAccepted){
                        pickImageCamera();
                    }
                    else{
                        Toast.makeText(this, "Camera & Stroage permissions are required", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Neither allowed not denied, rather cancelled
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted){
                        pickImageGallery();
                    }
                    else{
                        Toast.makeText(this, "Stroage permission is required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }
}
