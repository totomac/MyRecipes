package com.android.mobile.thomas.myrecipes.ui.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.mobile.thomas.myrecipes.ui.fragments.IngredientFragment;
import com.android.mobile.thomas.myrecipes.ui.fragments.LaunchpadSectionFragment;
import com.android.mobile.thomas.myrecipes.ui.fragments.ShoppingFragment;

/**
 * Created by Thomas on 19/07/2015.
 */
/*public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

    Context mContext;

    public AppSectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        // The first section of the app is the most interesting -- it offers
        // a launchpad into the other demonstrations in this example application.
        switch (i) {
            case 0:
                return LaunchpadSectionFragment.newLaunchPadSectionFragment(mContext);

            case 1:
                return IngredientFragment.newIngredientFragment(mContext);

            case 2:
                return new ShoppingFragment();

            default:
                return LaunchpadSectionFragment.newLaunchPadSectionFragment(mContext);
        }
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String result = null;

        switch (position) {
            case 0:
                result = mContext.getString(R.string.tab0);
                break;

            case 1:
                result = mContext.getString(R.string.tab1);
                break;

            case 2:
                result = mContext.getString(R.string.tab2);
                break;

            default:
                result = "error";
                break;
        }

        return result;
    }
}*/
