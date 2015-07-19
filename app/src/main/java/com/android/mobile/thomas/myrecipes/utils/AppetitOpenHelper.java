package com.android.mobile.thomas.myrecipes.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Thomas on 19/07/2015.
 */
public class AppetitOpenHelper extends SQLiteOpenHelper {
    private static volatile AppetitOpenHelper instance = null;
    // Version de la base de donees
    private static final int DATABASE_VERSION = 18;

    // Nom de la base
    private static final String APPETIT_BASE_NAME = "bonAppetit.db";

    // Tbales list
    public static final String RECIPE_TABLE_NAME = "Recipe";
    public static final String INGREDIENT_TABLE_NAME = "Ingredient";
    public static final String RECIPEINGREDIENT_TABLE_NAME = "RecipeIngredientMatch";

    /********************************************************************************************************
     * Table Recipe
     ********************************************************************************************************/
    //columns descrition for recipe table
    public static final String COLUMN_ID_RECIPE = "ID";
    public static final String COLUMN_NAME_RECIPE = "NAME";
    public static final String COLUMN_PRICE_RECIPE = "PRICE";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_IMAGE = "IMAGE";
    public static final String COLUMN_RECIPE_CREATED_ON = "CREATED_ON";
    public static final String COLUMN_RECIPE_UPDATED_ON = "UPDATED_ON";

    //creation recipe table request
    private static final String BDD_CREATION_REQUEST_RECIPE = "CREATE TABLE "
            + RECIPE_TABLE_NAME + " (" + COLUMN_ID_RECIPE
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_RECIPE + " TEXT NOT NULL, "
            + COLUMN_PRICE_RECIPE + " INTEGER NOT NULL, "
            + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_IMAGE + " TEXT, "
            + COLUMN_RECIPE_CREATED_ON + " DATETIME NOT NULL, "
            + COLUMN_RECIPE_UPDATED_ON + " DATETIME NOT NULL);";

    /********************************************************************************************************
     * Table Ingredient
     ********************************************************************************************************/
    // description
    public static final String COLUMN_ID_INGREDIENT = "ID";
    public static final String COLUMN_NAME_INGREDIENT = "NAME";
    public static final String COLUMN_INGREDIENT_CREATED_ON = "CREATED_ON";
    public static final String COLUMN_INGREDIENT_UPDATED_ON = "UPDATED_ON";

    //table
    private static final String BDD_CREATION_REQUEST_INGREDIENT = "CREATE TABLE "
            + INGREDIENT_TABLE_NAME + " (" + COLUMN_ID_INGREDIENT
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_INGREDIENT + " TEXT NOT NULL, "
            + COLUMN_INGREDIENT_CREATED_ON + " DATETIME NOT NULL, "
            + COLUMN_INGREDIENT_UPDATED_ON + " DATETIME NOT NULL);";

    /********************************************************************************************************
     * Table RecipeIngredientMatch
     ********************************************************************************************************/
    public static final String COLUMN_ID_MATCH = "IDRI";
    public static final String COLUMN_RECIPEID = "RECIPEID";
    public static final String COLUMN_INGREDIENTID = "INGREDIENTID";

    //table
    private static final String BDD_CREATION_REQUEST_MATCH = "CREATE TABLE "
            + RECIPEINGREDIENT_TABLE_NAME + " (" + COLUMN_ID_MATCH
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RECIPEID + " INTEGER NOT NULL, "
            + COLUMN_INGREDIENTID + " INTEGER NOT NULL);";

    private AppetitOpenHelper(Context context) {
        super(context, APPETIT_BASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    public final static AppetitOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new AppetitOpenHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        Log.d("[OpenHelper][OnUpgrade]", "creating new tables");
        db.execSQL(BDD_CREATION_REQUEST_RECIPE);
        db.execSQL(BDD_CREATION_REQUEST_INGREDIENT);
        db.execSQL(BDD_CREATION_REQUEST_MATCH);

        db.execSQL("INSERT INTO " + INGREDIENT_TABLE_NAME + " (NAME, CREATED_ON, UPDATED_ON) VALUES ('salt', datetime(),datetime())");
        db.execSQL("INSERT INTO " + INGREDIENT_TABLE_NAME + " (NAME, CREATED_ON, UPDATED_ON) VALUES ('pepper', datetime(),datetime())");
        db.execSQL("INSERT INTO " + INGREDIENT_TABLE_NAME + " (NAME, CREATED_ON, UPDATED_ON) VALUES ('onion', datetime(),datetime())");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // Lorsque l'on change le numero de version de la base on supprime la
        // table puis on la recree

        //TODO see what happen, does the data are erased
        Log.d("[OpenHelper][OnUpgrade]", "debut");
        if (newVersion > oldVersion) {
            Log.d("[OpenHelper][OnUpgrade]", "upgrading");
            db.execSQL("DROP TABLE " + RECIPE_TABLE_NAME + ";");
            db.execSQL("DROP TABLE " + INGREDIENT_TABLE_NAME + ";");
            db.execSQL("DROP TABLE " + RECIPEINGREDIENT_TABLE_NAME + ";");
            onCreate(db);
        }
    }

    public SQLiteDatabase getDataBase() {
        SQLiteDatabase db = getWritableDatabase();

        return db;
    }
}
