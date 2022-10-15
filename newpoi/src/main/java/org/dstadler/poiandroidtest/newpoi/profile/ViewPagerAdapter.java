package org.dstadler.poiandroidtest.newpoi.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.dstadler.poiandroidtest.newpoi.profile.ScanEduFragment;
import org.dstadler.poiandroidtest.newpoi.profile.ScanLangFragment;
import org.dstadler.poiandroidtest.newpoi.profile.ScanLicFragment;
import org.dstadler.poiandroidtest.newpoi.profile.ScanProfileFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private int channel;
    private boolean isReplace;
    private String[] fmTitles = new String[]{"프로필", "학력사항", "어학사항", "자격증"};
    private Map<String, ArrayList<String>> map;


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Boolean isReplace, Map<String, ArrayList<String>> map) {
        super(fragmentActivity);
        this.isReplace = isReplace;
        this.map = map;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                setChannel(0);
                if(!isReplace) {
                    return new ScanProfileFragment();
                }
                else if(isReplace){
                    return new ScanProfile2Fragment(map);
                }
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