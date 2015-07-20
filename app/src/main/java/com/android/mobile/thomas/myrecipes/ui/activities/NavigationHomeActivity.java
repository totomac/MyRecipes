package com.android.mobile.thomas.myrecipes.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.ui.adapters.PagerAdapter;

/**
 * Created by Thomas on 19/07/2015.
 */
public class NavigationHomeActivity extends FragmentActivity {

    static final int NUM_ITEMS = 3;

    private PagerAdapter mAdapter;

    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_layout);


        mAdapter = new PagerAdapter(getSupportFragmentManager(), getApplication());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

    }
}
