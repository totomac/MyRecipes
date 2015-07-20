package com.android.mobile.thomas.myrecipes.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.dao.IngredientPersistence;
import com.android.mobile.thomas.myrecipes.models.data.Ingredient;
import com.android.mobile.thomas.myrecipes.ui.adapters.ChooseIngredientsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class ChooseIngredientsActivity extends Activity{

    private final String TAG = "ChooseIngredients";
    ListView viewList;
    protected List<Ingredient> ingredientList;

    public static Intent getStartActivityIntent(Context context) {
        Intent intent = new Intent(context, ChooseIngredientsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> listIngredientId = new ArrayList<String>();

        setContentView(R.layout.choose_ingredients);



    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        IngredientPersistence persistence = new IngredientPersistence(this);
        List<Ingredient> listIngredient = persistence.getAllIngredients();

        if (listIngredient.size() != 0 ) {

            final ChooseIngredientsAdapter chooseAdapter = new ChooseIngredientsAdapter(this, listIngredient);

            viewList = (ListView) findViewById(R.id.listViewChooseIngredients);

            viewList.setAdapter(chooseAdapter);
            viewList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            viewList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View v, int item,
                                        long id) {

                    int intId = (int) id;

                    Log.v("onItemclick", "click on an item");
                    CheckBox checkbox = (CheckBox) v.getTag(R.id.checkBoxIngredient);

                    if (checkbox.isChecked()) {
                        checkbox.setChecked(false);
                    } else {
                        checkbox.setChecked(true);
                    }

                }
            });
        }

        Button validate = (Button) findViewById(R.id.buttonValidateIngredients1);
        validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Log.d(TAG, "checked items count = " + viewL)
                //Intent intent = new Intent(ChooseIngredientsActivity.this, CreateRecipesActivity.class);

                Intent result = new Intent();
                int cntChoice = viewList.getCount();
                ingredientList = new ArrayList<Ingredient>();

                for (Ingredient ing : ingredientList) {
                    Log.d(TAG, "nom ingredient dans la liste = " + ing.getName());
                }

                SparseBooleanArray sparseBooleanArray = viewList.getCheckedItemPositions();
                int k =0;
                for(int i = 0; i < cntChoice; i++)
                {

                    if(sparseBooleanArray.get(i) == true)
                    {
                        Ingredient ingredient = (Ingredient) viewList.getItemAtPosition(i);
                        Log.d(TAG, "name = " + ingredient.getName());
                        ingredientList.add(ingredient);
                    }


                }

                result.putParcelableArrayListExtra("arrayList",(ArrayList<Ingredient>) ingredientList);

                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
