package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.customImageView;

public class ResumeStandardCatActivity extends OutterClass{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_resume_standard_category;
    }

    @Override
    protected String[] getImagesTitle() {
        return new String[]{"resume_standard0", "resume_standard1", "resume_standard2"};
    }

    @Override
    protected String getClassPath() {
        return "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ResumeStandardActivity";
    }
}
