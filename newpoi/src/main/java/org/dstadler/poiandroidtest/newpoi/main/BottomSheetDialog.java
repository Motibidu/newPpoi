package org.dstadler.poiandroidtest.newpoi.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Constant;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;

import java.io.File;
import java.util.ArrayList;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    private TextView text_fileTitle;
    private LinearLayout layout_convertToPDF, layout_convertToJPG, layout_open, layout_delete;
    private int filePosition;
    private Fragment mFragment;
    private Context mContext;
    private ArrayList<String> pref_allFileNameList;

    public interface bottomSheetListener{
        void open();
        File delete();
        void convertToPDF() throws Exception;
        void convertToJPG() throws Exception;
    }

    private bottomSheetListener listener;

    private static final String TAG = "BottomSheetDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottomsheetdialog, container, false);

        mContext = getContext();


        text_fileTitle = v.findViewById(R.id.text_fileTitle);
        layout_convertToPDF = v.findViewById(R.id.layout_convertToPDF);
        layout_convertToJPG = v.findViewById(R.id.layout_convertToJPG);
        layout_open = v.findViewById(R.id.layout_open);
        layout_delete = v.findViewById(R.id.layout_delete);


        setBottomSheetTitle();

        layout_convertToPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    listener.convertToPDF();

                    //if sleep doesn't exist, ripple effect dismisses
                    Thread.sleep(100);
                    dismiss();

                }catch (NullPointerException e){
                    Log.d(TAG, e.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        layout_convertToJPG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    listener.convertToJPG();

                    //if sleep doesn't exist, ripple effect dismisses
                    Thread.sleep(100);
                    dismiss();

                }catch (NullPointerException e){
                    Log.d(TAG, e.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        layout_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.open();
            }
        });
        layout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

                File f= listener.delete();
                if(f.exists()){
                    Toast.makeText(mContext,"파일이 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mContext,"파일이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private void setBottomSheetTitle() {
        this.pref_allFileNameList = PreferenceManager.loadData(mContext, "pref_allFileNameList");
        if (pref_allFileNameList.isEmpty()){
            text_fileTitle.setText(Constant.allAbsolutePathList.get(filePosition));
        }else{
            pref_allFileNameList.size();
            text_fileTitle.setText(pref_allFileNameList.get(filePosition));
        }
    }

    public void setFilePosition(int position) {
        filePosition = position;
    }
    public int getFilePosition() {
        return filePosition;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (BottomSheetDialog.bottomSheetListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString());
        }
    }

}
