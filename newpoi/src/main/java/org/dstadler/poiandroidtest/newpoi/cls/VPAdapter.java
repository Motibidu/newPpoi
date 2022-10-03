package org.dstadler.poiandroidtest.newpoi.cls;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.dstadler.poiandroidtest.newpoi.profile.ScanEduFragment;
import org.dstadler.poiandroidtest.newpoi.profile.ScanLangFragment;
import org.dstadler.poiandroidtest.newpoi.profile.ScanLicFragment;
import org.dstadler.poiandroidtest.newpoi.profile.ScanProfileFragment;

public class VPAdapter extends FragmentStateAdapter {

    private int channel;
    private String[] fmTitles = new String[]{"프로필", "학력사항", "어학사항", "자격증"};

    public VPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                setChannel(0);
                return new ScanProfileFragment();
            case 1:
                setChannel(1);
                return new ScanEduFragment();
            case 2:
                setChannel(2);
                return new ScanLangFragment();
            case 3:
                setChannel(3);
                return new ScanLicFragment();
        }
        setChannel(0);
        return new ScanProfileFragment();
    }

    @Override
    public int getItemCount() {
        return fmTitles.length;
    }

    private void setChannel(int i){channel = i;}
    public int getChannel(){return channel;}


}
