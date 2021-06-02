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

    public void download_without_modify(String fileName, String documentName){
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        storageReference = fStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("Documents/"+documentName+"_ori.docx");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                downloadFile_without_modify(context, fileName,".docx", uri.toString());
            }
        });
    }
    public void downloadFile_without_modify(Context context, String fileName, String fileExtension, String url){

        DownloadManager downloadManager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/ZN/"+fileName+fileExtension);
        downloadManager.enqueue(request);
    }

    public void download_with_modify(String fileName, String documentName){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        storageReference = fStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("Documents/"+documentName+".docx");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Toast.makeText(expanded_screen.this, fileName+"_download_with_modify", Toast.LENGTH_SHORT).show();
                downloadFile_with_modify(context, fileName,".docx", uri.toString());
            }
        });
    }
    public void downloadFile_with_modify(Context context, String fileName, String fileExtension, String url){

        DownloadManager downloadManager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/ZN/."+fileName+fileExtension);


        //        Toast.makeText(promissory_expanded_screen.this, downloadID+"_downloadFile_with_modify", Toast.LENGTH_SHORT).show();
        long downloadID = downloadManager.enqueue(request);
        Intent intent = new Intent(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        PreferenceManager.setLong(context, "document_downloadID", downloadID);
    }

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
                Intent intent = new Intent(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                PreferenceManager.setLong(context, "image_downloadID", downloadID);

            }
        });
    }
//    public void createThumbnail(String fileName, String documentName){
//        storageReference = fStorage.getInstance().getReference();
//
//        StorageReference profileRef = storageReference.child("Documents/"+documentName+"_ori.docx");
//        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
////                String path = uri.getPath();
//                try {
//                    CustomXWPFDocument f = new CustomXWPFDocument(OPCPackage.open(new FileInputStream(new File(uri.getPath()))));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (InvalidFormatException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

}
