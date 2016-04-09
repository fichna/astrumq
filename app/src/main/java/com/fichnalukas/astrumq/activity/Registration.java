package com.fichnalukas.astrumq.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.fichnalukas.astrumq.R;

public class Registration extends Base {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initVariables();
        super.setupToolBar(mToolbar, R.string.reg_title, true);
    }

    private void initVariables() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
