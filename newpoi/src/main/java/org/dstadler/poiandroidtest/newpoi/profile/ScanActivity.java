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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Constant;
import org.dstadler.poiandroidtest.newpoi.cls.customMatcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanActivity extends AppCompatActivity{

    ViewPagerAdapter ViewPagerAdapter;
    TabLayout tablayout;
    ViewPager2 viewpager;

    private String[] fmTitles = new String[]{"프로필", "학력사항", "어학사항", "자격증"};

    //static vars
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final String TAG = "MAIN_TAG";

    //widgets

    private ImageButton backBtn;

    private LinearLayout inputImg;
    private Button recognizeText, complete;
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
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> engName = new ArrayList<String>();
    ArrayList<String> chName = new ArrayList<String>();

    ArrayList<String> rnn = new ArrayList<String>();
    ArrayList<String> email = new ArrayList<String>();
    ArrayList<String> addr = new ArrayList<String>();
    ArrayList<String> phoneNum = new ArrayList<String>();
    ArrayList<String> url = new ArrayList<String>();
    ArrayList<String> schl = new ArrayList<String>();

    public buttonCompleteListener buttonCompleteListener;


    public interface buttonCompleteListener{
        void buttonCompleteListen();
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        //setButtonCompleteListener();
        buttonCompleteListener = (buttonCompleteListener)mContext;

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
        complete = findViewById(R.id.button_complete);
        viewpager = findViewById(R.id.viewpager);
        tablayout = findViewById(R.id.tablayout);

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.viewpager);

        //EditText
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

        //init ViewPagerFragmentAdapter
        ViewPagerAdapter = new ViewPagerAdapter(this);

        //fm Manager
        viewpager.setAdapter(ViewPagerAdapter);
        new TabLayoutMediator(tablayout, viewpager,
                ((tab, position) -> tab.setText(fmTitles[position]))).attach();

        //add listener
        //setButtonCompleteListener((ScanProfile2Fragment)getSupportFragmentManager().findFragmentById(R.id.viewpager));

        //setting ImageView
        inputImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputImageDialog(inputImg);
            }
        });

        //recognizing Text
        recognizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri == null) {
                    Toast.makeText(mContext, "Pick image first...", Toast.LENGTH_SHORT).show();
                } else {
                    recognizeTextFromImage();
                }
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonCompleteListen();

            }
        });
    }
    public void buttonCompleteListen() {
        FirebaseAuth mAuth;
        FirebaseFirestore fStore;

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        String name = Constant.scanInfo.get("name");
        String engName = Constant.scanInfo.get("engName");
        String chName = Constant.scanInfo.get("chName");
        String rrn = Constant.scanInfo.get("rrn");
        String email = Constant.scanInfo.get("email");
        String addr = Constant.scanInfo.get("addr");
        String phoneNum = Constant.scanInfo.get("phoneNum");
        String age = Constant.scanInfo.get("age");

        if (mAuth.getCurrentUser() == null) {

        }
        else {
            String userID = mAuth.getCurrentUser().getUid();
            if (userID != null) {
                DocumentReference documentReference = fStore.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("name", name);
                user.put("engName", engName);
                user.put("chName", chName);
                user.put("rrn", rrn);
                user.put("age", age);
                user.put("phoneNumber", phoneNum);
                user.put("email", email);
                user.put("address", addr);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext,"완료했습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                documentReference.set(user).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
            }
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
                            stringProcess(recognizedText);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.e(TAG, "onFailure : ", e);
                            Toast.makeText(mContext,"Failed recognizing text due to "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            //Exception occurred while preparing InputImage, dismiss dialog, show reason in Toast
            progressDialog.dismiss();
            Log.e(TAG,"recognizeTextFromImage : ", e);
            Toast.makeText(mContext,"Failed preparing image due to "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }



    private void stringProcess(String str){


        //split string by a space
        String[] splitStr_n = str.split("\\n+");
        String[] splitStr_s = str.split("\\s+");
        Map<String,ArrayList<String>> m = new HashMap<String, ArrayList<String>>();

        //init customMatcher
        customMatcher cus = new customMatcher();

        int i = 0;
        String sp;

        //print strings splitted by enter
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
                        sp.contains("관리")||sp.contains("전공")||sp.contains("서울")||
                        sp.contains("대학"))) {
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
                //continue;
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
                //continue;
            }

            cus.set_phoneNum();
            if(cus.find()){
                sp = cus.group();
                Log.d(TAG, j+":phoneNum : "+sp);
                //phoneNum.add(sp);
                if(!phoneNum.contains(sp)) {phoneNum.add(sp);}
                //continue;
            }
            cus.set_email();
            if(cus.find()){
                sp = cus.group();
                Log.d(TAG, j+":email : "+sp);
                //email.add(sp);
                if(!email.contains(sp)) {email.add(sp);}
                //continue;
            }

            cus.set_addr();
            if(cus.find()){
                sp = cus.group();
                Log.d(TAG, j+":addr : "+sp);
                //addr.add(sp);
                if(!addr.contains(sp)) {addr.add(sp);}
                //continue;
            }

        }
        m.put("name",name);
        m.put("engName",engName);
        m.put("chName",chName);
        m.put("rnn",rnn);
        m.put("phoneNum",phoneNum);
        m.put("email",email);
        m.put("addr",addr);


        int channel = ViewPagerAdapter.getChannel();
        switch(channel){
            case 0:
                viewpager.setAdapter(new ViewPagerAdapter(this, true, m));
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
        Log.d(TAG, "Fi===========ni===========sh");


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
                        Toast.makeText(mContext, "Cancelled...", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mContext, "Cancelled", Toast.LENGTH_SHORT).show();
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
