package com.android.mobile.thomas.myrecipes.models.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class Trolley {
    private static Trolley instance = new Trolley();
    private List<Recipe> recipeList;
    private final String TAG = "Trolley";

    private Trolley() {
        recipeList = new ArrayList<Recipe>();
    }

    public void addRecipe(Recipe recipe) {
        if (recipe != null) {
            recipeList.add(recipe);
        }
    }

    public void removeRecipe(Recipe recipe) {
        if (recipe != null) {

            for (int i = recipeList.size() -1; i >= 0; i--) {
                if (recipe.getId() == recipeList.get(i).getId()) {
                    recipeList.remove(recipeList.get(i));
                }
            }
        }
    }

    public static Trolley getInstance() {
        return  instance;
    }

    public void displayContent() {
        for (Recipe recipe : recipeList) {
            Log.v(TAG, "recipe name = " + recipe.getName());
        }
    }

    public List<Ingredient> getIngredientList() {

        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        for (Recipe recipe : recipeList) {
            for (Ingredient ingredient : recipe.getmIngredientsList()) {
                Boolean isPresent = false;
                for (Ingredient mIngredient : ingredientList) {
                    if (mIngredient.getName().equals(ingredient.getName())) {
                        isPresent = true;
                    }
                }
                if (!isPresent) {
                    ingredientList.add(ingredient);
                }
                isPresent = false;
            }
        }

        return  ingredientList;
    }

    public boolean contains(Recipe recipe) {
        if (recipe != null) {
            return recipeList.contains(recipe);
        }
        return false;
    }

    public void displayTrolleyContent() {
        for (Recipe recipe : recipeList) {
            Log.v(TAG, "trolley recipe = " + recipe.getName());
        }
    }

    public int size() {
        return recipeList.size();
    }

    public boolean checkContainsById(Recipe recipe) {
        for (Recipe toCompare : recipeList) {
            if (toCompare.getId() == recipe.getId()) {
                return true;
            }
        }
        return false;
    }
}
