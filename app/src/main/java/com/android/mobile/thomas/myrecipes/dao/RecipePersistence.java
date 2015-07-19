package com.android.mobile.thomas.myrecipes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.mobile.thomas.myrecipes.models.data.Ingredient;
import com.android.mobile.thomas.myrecipes.models.data.Recipe;
import com.android.mobile.thomas.myrecipes.ui.activities.MyRecipes;
import com.android.mobile.thomas.myrecipes.utils.AppetitOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class RecipePersistence {
    private AppetitOpenHelper mAppetitOpenHelper;
    private final String TAG = "RecipePersistence";

    public RecipePersistence(Context context) {
        mAppetitOpenHelper =  AppetitOpenHelper.getInstance(context);
    }

    public long insert(Recipe recipe) {

        if (recipe == null) return -1;

        SQLiteDatabase db = mAppetitOpenHelper.getDataBase();

        Date dt = new Date();

        ContentValues values = new ContentValues();

        values.put(mAppetitOpenHelper.COLUMN_NAME_RECIPE, recipe.getName());
        values.put(mAppetitOpenHelper.COLUMN_PRICE_RECIPE, recipe.getPrice());
        values.put(mAppetitOpenHelper.COLUMN_IMAGE, recipe.getImageUri());
        values.put(mAppetitOpenHelper.COLUMN_DESCRIPTION, recipe.getDescription());
        values.put(mAppetitOpenHelper.COLUMN_RECIPE_CREATED_ON, dt.toString());
        values.put(mAppetitOpenHelper.COLUMN_RECIPE_UPDATED_ON, dt.toString());

        Log.v(TAG, "Name = " + recipe.getName() + "Price = " + recipe.getPrice());
        long rowId = db.insert(mAppetitOpenHelper.RECIPE_TABLE_NAME, null, values);
        recipe.setId(rowId);

        if (rowId == -1 ) {
            return -1;
        } else {
            Log.d(TAG, "Recipe insertion succeed");
        }

        RIPersistence persistence = new RIPersistence(MyRecipes.getContextApplication());
        if (recipe.getmIngredientsList().isEmpty()) return 1;

        for (Ingredient ingredient : recipe.getmIngredientsList()) {
            if (persistence.insert(recipe, ingredient) == -1) {
                Log.d(TAG, "insertion Error");
                return -1;
            }
        }

        return 1;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipesList = new ArrayList<Recipe>();
        SQLiteDatabase db = mAppetitOpenHelper.getDataBase();

        String query = "Select * from " + AppetitOpenHelper.RECIPE_TABLE_NAME + " a LEFT OUTER JOIN " + AppetitOpenHelper.RECIPEINGREDIENT_TABLE_NAME +" b ON a.ID=b.RECIPEID";
        Cursor cursor = db.rawQuery(query, new String[] {});

        Log.d(TAG, "amount of recipes = " + cursor.getCount());

        if (cursor.getCount() == 0) return recipesList;

        int k = -1; // a recipe can appears more than one time in the cursor, but we need to create it only one time
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (k != cursor.getInt(cursor.getColumnIndex(AppetitOpenHelper.COLUMN_ID_RECIPE)) && !cursor.isAfterLast()) {
                k = cursor.getInt(cursor.getColumnIndex(AppetitOpenHelper.COLUMN_ID_RECIPE));
                int position = cursor.getPosition();
                Recipe recipe = new Recipe(cursor);
                cursor.moveToPosition(position);
                Log.d(TAG, "recipe name =" + recipe.getName());
                recipesList.add(recipe);
                cursor.moveToNext();
            } else {
                if (!cursor.isAfterLast()) {
                    cursor.moveToNext();
                } else {
                    break;
                }
            }
        }

        Log.v(TAG, "recipeList size sent = " + recipesList.size());
        cursor.close();
        return recipesList;
    }

    public Recipe getRecipeById(long id) {
        SQLiteDatabase db = mAppetitOpenHelper.getDataBase();

        String query = "Select * from " + AppetitOpenHelper.RECIPE_TABLE_NAME + " a LEFT OUTER JOIN " + AppetitOpenHelper.RECIPEINGREDIENT_TABLE_NAME +" b ON a.ID=b.RECIPEID WHERE a.ID=?";
        Log.v(TAG, "query = " + query + " with ID = " + id);
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(id)});

        Recipe recipe = null;
        if (cursor != null && cursor.getCount()!=0) {
            cursor.moveToFirst();
            Log.d(TAG, "id wanted = " + id + "id retrieved = " + cursor.getInt(cursor.getColumnIndex(AppetitOpenHelper.COLUMN_ID_RECIPE)));
            recipe = new Recipe(cursor);
        }

        cursor.close();
        return recipe;
    }

    public void delete(Recipe recipe) {
        SQLiteDatabase db = mAppetitOpenHelper.getDataBase();

        String query = "DELETE FROM " + AppetitOpenHelper.RECIPEINGREDIENT_TABLE_NAME +
                " WHERE " + AppetitOpenHelper.COLUMN_ID_MATCH +
                " IN ( SELECT " +  AppetitOpenHelper.COLUMN_ID_MATCH +
                " FROM " + AppetitOpenHelper.RECIPEINGREDIENT_TABLE_NAME +
                " LEFT JOIN " + AppetitOpenHelper.RECIPE_TABLE_NAME +
                " ON " + AppetitOpenHelper.RECIPE_TABLE_NAME + "." + AppetitOpenHelper.COLUMN_ID_RECIPE + "=" + AppetitOpenHelper.RECIPEINGREDIENT_TABLE_NAME +"."+AppetitOpenHelper.COLUMN_RECIPEID +
                " WHERE " + AppetitOpenHelper.RECIPE_TABLE_NAME + "." + AppetitOpenHelper.COLUMN_ID_RECIPE + "= ? )";

        Log.v(TAG, "query delete = " + query);

        db.execSQL(query, new String[] {String.valueOf(recipe.getId())});

        query = "DELETE FROM " + AppetitOpenHelper.RECIPE_TABLE_NAME +
                " WHERE " + AppetitOpenHelper.COLUMN_ID_RECIPE + "= ?";

        Log.v(TAG, "query delete = " + query);

        db.execSQL(query, new String[] {String.valueOf(recipe.getId())});

    }
}
