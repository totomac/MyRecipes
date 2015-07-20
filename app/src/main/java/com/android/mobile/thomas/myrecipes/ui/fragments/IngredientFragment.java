package com.android.mobile.thomas.myrecipes.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.dao.IngredientPersistence;
import com.android.mobile.thomas.myrecipes.models.data.Ingredient;
import com.android.mobile.thomas.myrecipes.ui.activities.CreateIngredientActivity;
import com.android.mobile.thomas.myrecipes.ui.adapters.IngredientsAdapter;

import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class IngredientFragment extends Fragment {

    Context mContext;

    public static final IngredientFragment newIngredientFragment(Context context) {
        IngredientFragment fragment = new IngredientFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView view = null;
        // TODO Auto-generated method stub
        mContext = getActivity().getApplicationContext();
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);

        List<Ingredient> ingredientsList = null;
        IngredientPersistence persistence = new IngredientPersistence(mContext);
        ingredientsList = persistence.getAllIngredients();

        if (ingredientsList.size() != 0) {
            IngredientsAdapter adapter = new IngredientsAdapter(mContext, ingredientsList);

            view = (ListView) rootView.findViewById(R.id.listViewIngredients);
            view.setAdapter(adapter);
        }

        Button create = (Button) rootView.findViewById(R.id.buttonSaveIngredient);
        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intentCreate = new Intent(mContext, CreateIngredientActivity.class);
                startActivity(intentCreate);
            }
        });


        return rootView;
    }
}
