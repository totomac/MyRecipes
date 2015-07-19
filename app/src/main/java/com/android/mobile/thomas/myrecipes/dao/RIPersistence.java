package com.android.mobile.thomas.myrecipes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.mobile.thomas.myrecipes.models.data.Ingredient;
import com.android.mobile.thomas.myrecipes.models.data.Recipe;
import com.android.mobile.thomas.myrecipes.utils.AppetitOpenHelper;

/**
 * Created by Thomas on 19/07/2015.
 */
public class RIPersistence {
    private AppetitOpenHelper mAppetitOpenHelper;

    public RIPersistence(Context context) {
        mAppetitOpenHelper =  AppetitOpenHelper.getInstance(context);
    }

    public long insert(Recipe recipe, Ingredient ingredient) {
        SQLiteDatabase db = mAppetitOpenHelper.getDataBase();

        ContentValues values = new ContentValues();

        values.put(mAppetitOpenHelper.COLUMN_RECIPEID, recipe.getId());
        values.put(mAppetitOpenHelper.COLUMN_INGREDIENTID, ingredient.getId());

        return db.insert(mAppetitOpenHelper.RECIPEINGREDIENT_TABLE_NAME, null, values);
    }
}
