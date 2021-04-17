package org.dstadler.poiandroidtest.newpoi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class expanded_screen extends AppCompatActivity {

    private ImageButton expanded_screen_backButton, plus;
    private ImageView expanded_screen_mainImageView;
    private String folder, fileName;
    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    private StorageReference storageReference;
    private Uri fetchUri;
    public Uri return_retrievedURI;

    public interface MyCallback {
        void onCallback(Uri value);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expanded_screen);

        mAuth = FirebaseAuth.getInstance();
        storageReference = fStorage.getInstance().getReference();


        expanded_screen_backButton = (ImageButton)findViewById(R.id.expanded_screen_backButton);
        expanded_screen_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        
        expanded_screen_mainImageView = (ImageView)findViewById(R.id.expanded_screen_mainImageView);
//        content_image = (ScrollView)findViewById(R.id.content_image);
//        imageView2 = (ImageView)findViewById(R.id.imageView2);



        Intent intent = getIntent();
        String imgPath = intent.getStringExtra("imgPath");
        Uri imgUri = Uri.parse(imgPath);

        expanded_screen_mainImageView.setImageURI(null);
        expanded_screen_mainImageView.setImageURI(imgUri);

        plus = (ImageButton)findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

//                File targetFile = new File(retrievedURI.getPath());
//                try{
//                    XWPFDocument doc = new XWPFDocument(OPCPackage.open(targetFile));
//                }catch(InvalidFormatException | IOException e){
//                    Log.e("expanded_screen", e.toString());
//                }
//                try {
//                    XWPFDocument doc = new XWPFDocument(OPCPackage.open(targetFile));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (InvalidFormatException e) {
//                    e.printStackTrace();
//                }

//                try{
//                    createDOCX();
//
//                } catch (FileNotFoundException e){
//                    e.printStackTrace();
//                }
            }
        });


//        if (getSoftButtonsBarHeight() == 0) {
//            height = size.y - getStatusBarHeight() - getSoftButtonsBarHeight() - (int) dipToPixels(expanded_screen.this, 104);
//
//        } else {
//            height = size.y - getStatusBarHeight() - getSoftButtonsBarHeight() - (int) dipToPixels(expanded_screen.this, 56);
//        }
//        ViewTreeObserver observer = expanded_screen_backButton.getViewTreeObserver();
//        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//            @Override
//            public void onGlobalLayout() {
//                lay_height = expanded_screen_backButton.getHeight();
//                int headerLayoutWidth = expanded_screen_backButton.getWidth();
//                expanded_screen_backButton.getViewTreeObserver().removeOnGlobalLayoutListener(
//                        this);
//
//                Log.v("height", lay_height + "");
//
//            }
//        });


//        imageView2.setImageURI(null);
//        imageView2.setImageURI(imgUri);
//
//        imageButton = (ImageButton)findViewById(R.id.back_button);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        bottomNavigationView = findViewById(R.id.expaned_screen_bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch(item.getItemId()){
//                    case R.id.create_action:
//                        break;
//                }
//                return true;
//            }
//        });
//        scroll_main = findViewById(R.id.scroll_main);
//        plus = findViewById(R.id.plus);
//        scroll_main.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                new Handler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        int scrollX = scroll_main.getScrollX(); //for horizontalScrollView
//                        int scrollY = scroll_main.getScrollY(); //for verticalScrollView
//
//
//                        int sc = scrollY + height;
//
//                        Log.v("bottom", lay_height + "  Y=" + sc + "  " + scrollY + "   " + height);
//
//                        if (sc >= lay_height) {
//                            plus.setVisibility(View.GONE);
//
//                        } else {
//                            plus.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
//            }
//        });
//        ll = (ScrollView)findViewById(R.id.ll);
//        ll.setOnTouchListener(new View.OnTouchListener() {
//            @Override public boolean onTouch(View v, MotionEvent event)
//            {
//                ll.requestDisallowInterceptTouchEvent(true);
//                return false;
//            } });
    }

    private void createDOCX() throws FileNotFoundException {

        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph tmpParagraph = doc.createParagraph();
        XWPFRun tmpRun = tmpParagraph.createRun();
        tmpRun.setText("DDDDDDDDDDDDDDDDDDDDDD");

        folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        fileName = "/newFile.docx";
        File f = new File(folder);

        if(!f.exists()) {
            Log.e("AAA","DDD");
        }
        FileOutputStream out = new FileOutputStream(new File(folder+fileName));
        try{
            doc.write(out);
            doc.close();
//            Toast.makeText(expanded_screen.this,"CREATED",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void retrieve_DOCX_URI(MyCallback myCallback){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        StorageReference profileRef = storageReference.child("Documents/self_introduction2.doc");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                File file = new File(uri.getPath());
                try {
                    FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                    XWPFDocument document = new XWPFDocument(fis);

                    List<XWPFParagraph> paragraphs = document.getParagraphs();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }


}
