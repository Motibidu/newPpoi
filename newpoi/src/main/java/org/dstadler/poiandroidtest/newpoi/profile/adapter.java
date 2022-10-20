package org.dstadler.poiandroidtest.newpoi.profile;

import android.content.Context;

import org.dstadler.poiandroidtest.newpoi.cls.RecyclerViewAdapter;

public class adapter {
    private Context mContext;

    public adapter(Context mContext, RecyclerViewAdapter.clickListener clickListener){
        this.mContext = mContext;
//        this.clickListener = clickListener;
    }

    public interface clickListener{
        //position is the same position of file in arrayList
        void onIconMoreClick(int position);
    }
}
