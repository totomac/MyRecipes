package com.android.mobile.thomas.myrecipes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.mobile.thomas.myrecipes.models.data.Ingredient;
import com.android.mobile.thomas.myrecipes.utils.AppetitOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class IngredientPersistence {
    private AppetitOpenHelper mAppetitOpenHelper;

    public IngredientPersistence(Context context) {
        mAppetitOpenHelper =  AppetitOpenHelper.getInstance(context);
    }

    public long insert(Ingredient ingredient) {
        SQLiteDatabase db = mAppetitOpenHelper.getDataBase();

        Date dt = new Date();

        ContentValues values = new ContentValues();

        values.put(mAppetitOpenHelper.COLUMN_NAME_INGREDIENT, ingredient.getName());
        values.put(mAppetitOpenHelper.COLUMN_INGREDIENT_CREATED_ON, dt.toString());
        values.put(mAppetitOpenHelper.COLUMN_INGREDIENT_UPDATED_ON, dt.toString());

        return db.insert(mAppetitOpenHelper.INGREDIENT_TABLE_NAME, null, values);
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredientsList = new ArrayList<Ingredient>();

        SQLiteDatabase db = mAppetitOpenHelper.getDataBase();
        String[] projection = {mAppetitOpenHelper.COLUMN_ID_INGREDIENT, mAppetitOpenHelper.COLUMN_NAME_INGREDIENT};

        Cursor cursor = db.query(mAppetitOpenHelper.INGREDIENT_TABLE_NAME, projection,"",new String[] {},"","","");

        Log.d("[IngredientPersistence][getAllIngredients]", "taille curseur = " + cursor.getCount());
        Log.d("[IngredientPersistence][getAllIngredients]", "cursor column count = " + cursor.getColumnCount());

        if(cursor.getCount() == 0) return ingredientsList;

        cursor.moveToFirst();
        while (!cursor.isAfterLast() ) {
            Log.d("[IngredientPersistence][getAllIngredients]", "cursor 0 = " + cursor.getString(0) + "cursor 1 = " + cursor.getString(1));

            Ingredient ingredient = new Ingredient(cursor);
            Log.d("[persistence]", "ingredient name =" + ingredient.getName());
            ingredientsList.add(ingredient);
            cursor.moveToNext();
        }

        cursor.close();
        return ingredientsList;
    }

    public Ingredient getIngredientById(int id) {
        SQLiteDatabase db = mAppetitOpenHelper.getDataBase();

        String[] projection = {mAppetitOpenHelper.COLUMN_ID_INGREDIENT, mAppetitOpenHelper.COLUMN_NAME_INGREDIENT};
        Cursor cursor = db.query(mAppetitOpenHelper.INGREDIENT_TABLE_NAME, projection, mAppetitOpenHelper.COLUMN_ID_INGREDIENT + "= ?", new String[] { String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }


        Ingredient ingredient = new Ingredient(cursor);

        cursor.close();
        return ingredient;
    }
}
