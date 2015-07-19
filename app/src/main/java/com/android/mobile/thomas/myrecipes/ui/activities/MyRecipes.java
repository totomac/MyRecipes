package com.android.mobile.thomas.myrecipes.ui.activities;

import android.app.Application;
import android.content.Context;

/**
 * Created by Thomas on 19/07/2015.
 */
public class MyRecipes extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        mContext = this.getApplicationContext();
    }

    public static Context getContextApplication() {
        return mContext;
    }
}
