package org.dstadler.poiandroidtest.newpoi.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.dstadler.poiandroidtest.newpoi.R;

public class ProfileScrnResumeFragment extends Fragment {

    private View v;

    private Button btn_eduBack, btn_formOfCareer, btn_lang, btn_selfIntroduction, btn_licenses;
    private FragmentActivity mFragmentActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile_scrn_resume, container, false);

        mFragmentActivity = getActivity();
        btn_eduBack = v.findViewById(R.id.btn_eduBack);
        btn_formOfCareer = v.findViewById(R.id.btn_formOfCareer);
        btn_lang = v.findViewById(R.id.btn_lang);
        btn_selfIntroduction = v.findViewById(R.id.btn_selfIntroduction);
        btn_licenses = v.findViewById(R.id.btn_licenses);

        btn_eduBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mFragmentActivity, ResumeEcubackActivity.class);
                startActivity(intent);
            }
        });

        btn_formOfCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mFragmentActivity, ResumeCareerActivity.class);
                startActivity(intent);
            }
        });

        btn_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mFragmentActivity, ResumeLangActivity.class);
                startActivity(intent);
            }
        });


        btn_licenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mFragmentActivity, ResumeLicActivity.class);
                startActivity(intent);
            }
        });

        btn_selfIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mFragmentActivity, SelfIntroductionActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
