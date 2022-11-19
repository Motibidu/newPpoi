package org.dstadler.poiandroidtest.newpoi.cls;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class GoogleManager {

    private Context mContext;
    public GoogleManager(Context mContext){
        this.mContext = mContext;
    }

    public GoogleSignInAccount signInAccount(){
        return GoogleSignIn.getLastSignedInAccount(mContext);
    }
    public boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(mContext) != null;
    }
}
