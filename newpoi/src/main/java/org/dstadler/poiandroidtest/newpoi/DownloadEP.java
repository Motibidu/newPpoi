package org.dstadler.poiandroidtest.newpoi;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//다운로드 진입점
public class DownloadEP {
    private Context context;
    private AppCompatActivity activity;

    private StorageReference storageReference;
    private FirebaseStorage fStorage;
    private FirebaseAuth mAuth;

    private String userID;


    public DownloadEP(Context context){
        super();
        this.context = context;
    }

    //uri 불러오기
    public void download_without_modify(String fileName, String docName){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        storageReference = fStorage.getInstance().getReference();

        //파일명에 "_ori" append
        StorageReference profileRef = storageReference.child("Documents/"+docName+"_ori.docx");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                downloadFile_without_modify(context, fileName,".docx", uri.toString());
            }
        });
    }
    //양식파일 다운로드
    public void downloadFile_without_modify(Context context, String fileName, String fileExtension, String url){

        DownloadManager downloadManager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/ZN/"+fileName+fileExtension);
        downloadManager.enqueue(request);
    }
    //uri 불러오기
    public void download_with_modify(String fileName, String docName){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        storageReference = fStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("Documents/"+docName+".docx");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Toast.makeText(expanded_screen.this, fileName+"_download_with_modify", Toast.LENGTH_SHORT).show();
                downloadFile_with_modify(context, fileName,".docx", uri.toString());
            }
        });
    }
    //양식파일 다운로드하고 PreferenceManager의 doc_dwnlID에 downloadID 등록
    public void downloadFile_with_modify(Context context, String fileName, String fileExtension, String url){

        DownloadManager downloadManager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/ZN/."+fileName+fileExtension);


        //        Toast.makeText(promissory_expanded_screen.this, downloadID+"_downloadFile_with_modify", Toast.LENGTH_SHORT).show();
        long downloadID = downloadManager.enqueue(request);
        PreferenceManager.setLong(context, "doc_dwnlID", downloadID);
    }

    //프로필 이미지 다운로드
    public void download_picture(){
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ZN/profile.jpg");

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);

        mAuth = FirebaseAuth.getInstance();
        storageReference = fStorage.getInstance().getReference();
        userID = mAuth.getCurrentUser().getUid();


        StorageReference profileRef = storageReference.child("users/"+userID+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                DownloadManager downloadManager = (DownloadManager) context.
                        getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/ZN/profile.jpg");

                long downloadID = downloadManager.enqueue(request);
                PreferenceManager.setLong(context, "img_dwnlID", downloadID);

            }
        });
    }


}
