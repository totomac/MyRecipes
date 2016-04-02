package com.android.mobile.thomas.myrecipes.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.dao.RecipePersistence;
import com.android.mobile.thomas.myrecipes.models.data.Recipe;
import com.android.mobile.thomas.myrecipes.ui.activities.CreateRecipesActivity;
import com.android.mobile.thomas.myrecipes.ui.activities.DisplayRecipeActivity;
import com.android.mobile.thomas.myrecipes.ui.adapters.RecipesAdapter;

import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class MyRecipesFragment extends Fragment {

    static Context mContext;
    private String TAG = "LaunchpadSectionFragment";
    View rootView;
    RecipesAdapter adapter;
    private List<Recipe> recipesList;

    public static final MyRecipesFragment newInstance() {

        return new MyRecipesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity().getApplicationContext();
        rootView = inflater.inflate(R.layout.fragment_recipes, container, false);

        //Utils.copyBDDAction();

        Log.v(TAG, "fragment refresh");
        RecipePersistence persistence = new RecipePersistence(mContext);
        this.recipesList = persistence.getAllRecipes();


        Log.v(TAG, "recipeList size retrieved = " + recipesList.size());
        adapter = new RecipesAdapter(mContext, recipesList);

        if (recipesList.size() != 0) {


            ListView view = (ListView) rootView.findViewById(R.id.listViewRecipes);
            view.setAdapter(adapter);

            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    Log.v(TAG, "Click on an item");
                    startActivity(DisplayRecipeActivity.getIntentToStartActivity(view.getContext(), recipesList.get(position)));
                }
            });
        }

        Button create = (Button) rootView.findViewById(R.id.buttonGoToCreate);
        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(CreateRecipesActivity.getIntentToStartActivity(v.getContext()));
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO remove it and set a listenner for after creating a recipe
        adapter.notifyDataSetChanged();
    }
}
