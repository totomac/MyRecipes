package com.android.mobile.thomas.myrecipes.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.models.data.Trolley;
import com.android.mobile.thomas.myrecipes.ui.adapters.ShoppingAdapter;

/**
 * Created by Thomas on 19/07/2015.
 */
public class ShoppingFragment extends Fragment {

    Context mContext;
    View mRootView;
    final String TAG = "ShoppingFragment";
    private ListView mListView;


    @Override
    public void onResume() {
        super.onResume();


    }

    public ShoppingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        mContext = getActivity().getApplicationContext();
        if (mContext == null) Log.v(TAG, "context null in createView");
        mRootView = inflater.inflate(R.layout.fragment_shopping, container, false);

        ShoppingAdapter adapter = new ShoppingAdapter(mContext, Trolley.getInstance());
        ListView mListView = (ListView) mRootView.findViewById(R.id.listViewShopping);
        mListView.setAdapter(adapter);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mListView.setSelector(R.drawable.list_selector);


        return mRootView;
    }
}
