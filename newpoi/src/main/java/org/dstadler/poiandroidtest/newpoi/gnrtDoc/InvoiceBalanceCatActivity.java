package org.dstadler.poiandroidtest.newpoi.gnrtDoc;

import android.os.Bundle;

import androidx.annotation.Nullable;

import org.dstadler.poiandroidtest.newpoi.R;

public class InvoiceBalanceCatActivity extends OutterClass{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_invoice_balance_category;
    }

    @Override
    protected String[] getImagesTitle() {
        return new String[]{"invoice_balance0"};
    }

    @Override
    protected String getClassPath() {
        return "org.dstadler.poiandroidtest.newpoi.gnrtDoc.InvoiceBalanceActivity";
    }
}
