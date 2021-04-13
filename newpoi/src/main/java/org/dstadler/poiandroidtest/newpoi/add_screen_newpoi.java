package org.dstadler.poiandroidtest.newpoi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class add_screen_newpoi extends Activity {
    private Button button;
    private String folder, fileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_screen_newpoi);

        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    createDOCX();

                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        });
    }
    private void createDOCX() throws FileNotFoundException{

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
            Toast.makeText(add_screen_newpoi.this,"CREATED",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
