package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.os.Bundle;

import androidx.annotation.Nullable;

import org.dstadler.poiandroidtest.newpoi.R;

public class ContractExpirationLaborCatActivity extends OutterClass {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_contract_expiration_labor_category;
    }

    @Override
    protected String[] getImagesTitle() {
        return new String[]{"contract_expiration_labor0", "contract_expiration_labor1"};
    }

    @Override
    protected String getClassPath() {
        return "org.dstadler.poiandroidtest.newpoi.gnrtDoc.ContractExpirationLaborActivity";
    }
}
