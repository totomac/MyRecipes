package com.android.mobile.thomas.myrecipes.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.mobile.thomas.myrecipes.ui.fragments.IngredientFragment;
import com.android.mobile.thomas.myrecipes.ui.fragments.LaunchpadSectionFragment;
import com.android.mobile.thomas.myrecipes.ui.fragments.ShoppingFragment;

import java.util.List;

/**
 * Created by Thomas on 20/07/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private final int NUM_ITEMS = 3;
    private Context context;
    private List<Fragment> menuItems;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return LaunchpadSectionFragment.newLaunchPadSectionFragment(context);
            case 1:
                return IngredientFragment.newIngredientFragment(context);
            case 2:
                return ShoppingFragment.newInstance(context);
            default:
                return LaunchpadSectionFragment.newLaunchPadSectionFragment(context);
        }
    }
}
