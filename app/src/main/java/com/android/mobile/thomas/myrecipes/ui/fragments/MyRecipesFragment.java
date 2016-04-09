package com.android.mobile.thomas.myrecipes.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private final String TAG = "LaunchpadSectionFragment";
    public static  final String EXTRA_RECIPE = "extra_recipe";
    private final int RECIPE_REQUEST = 1;

    private RecipesAdapter adapter;
    private List<Recipe> recipesList;

    public static final MyRecipesFragment newInstance() {

        return new MyRecipesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecipePersistence persistence = new RecipePersistence(getActivity());
        recipesList = persistence.getAllRecipes();


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listViewRecipes);
        recyclerView.setHasFixedSize(true);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(animator);

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new RecipesAdapter(getActivity(), recipesList);
        adapter.setListener(new RecipesAdapter.IRecipesAdapterListener() {
            @Override
            public void onItemClicked(Recipe recipe) {
                startActivity(DisplayRecipeActivity.getIntentToStartActivity(getActivity(), recipe));
            }
        });
        recyclerView.setAdapter(adapter);

        Button create = (Button) view.findViewById(R.id.buttonGoToCreate);
        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivityForResult(CreateRecipesActivity.getIntentToStartActivity(v.getContext()), RECIPE_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECIPE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                recipesList.add(data.<Recipe>getParcelableExtra(EXTRA_RECIPE));
                adapter.notifyItemInserted(adapter.getItemCount());
            }
        }
    }
}
