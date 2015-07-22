package com.android.mobile.thomas.myrecipes.models.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android.mobile.thomas.myrecipes.dao.IngredientPersistence;
import com.android.mobile.thomas.myrecipes.ui.activities.MyRecipes;
import com.android.mobile.thomas.myrecipes.utils.AppetitOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class Recipe implements Parcelable {
    private static String TAG = "Recipe";

    private long id;
    private String name;
    private String description;
    private int price;
    private String imageURI;
    private List<Ingredient> mIngredientsList = new ArrayList<Ingredient>();

    public Recipe(long id, String name, int price, String uri, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageURI = uri;
        this.description = description;
    }

    // construct a recipe from a cursor.
    // the cursor is made from Recipe and RecipeIngredientMatch
    // Have to be sure that we add the ingredient from only one recipe
    public Recipe(Cursor cursor) {
        int indexId = cursor.getColumnIndex(AppetitOpenHelper.COLUMN_ID_RECIPE);
        int indexName = cursor.getColumnIndex(AppetitOpenHelper.COLUMN_NAME_RECIPE);
        int indexPrice = cursor.getColumnIndex(AppetitOpenHelper.COLUMN_PRICE_RECIPE);
        int indexImage = cursor.getColumnIndex(AppetitOpenHelper.COLUMN_IMAGE);
        int indexDescription = cursor.getColumnIndex(AppetitOpenHelper.COLUMN_DESCRIPTION);
        int indexIngredientId = cursor.getColumnIndex(AppetitOpenHelper.COLUMN_INGREDIENTID);

        IngredientPersistence db = new IngredientPersistence(MyRecipes.getContextApplication());
        int ingredientId;

        this.id = cursor.getInt(indexId);
        this.name = cursor.getString(indexName);
        this.price = cursor.getInt(indexPrice);
        this.imageURI = cursor.getString(indexImage);
        this.description = cursor.getString(indexDescription);

        while (!cursor.isAfterLast()) {
            if (!cursor.isNull(indexIngredientId) && cursor.getInt(indexId) == this.id) {
                ingredientId = cursor.getInt(indexIngredientId);
                mIngredientsList.add(db.getIngredientById(ingredientId));
            }

            cursor.moveToNext();
        }

        Log.v(TAG, "Recipe create : ID= " + this.id + " NAME = " + this.name);
        for(Ingredient ingredient : mIngredientsList) {
            Log.v(TAG, "Recipe "+ this.name + "ingredient = " + ingredient.getName());
        }
    }

    /**
     * getters/setters
     */

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUri() {
        return imageURI;
    }

    public void setImageUri(String imageUri) {
        this.imageURI = imageUri;
    }

    public List<Ingredient> getmIngredientsList() {
        return mIngredientsList;
    }

    public void setmIngredientsList(List<Ingredient> mIngredientsList) {
        this.mIngredientsList = mIngredientsList;
    }

    protected Recipe(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        price = in.readInt();
        imageURI = in.readString();
        if (in.readByte() == 0x01) {
            mIngredientsList = new ArrayList<Ingredient>();
            in.readList(mIngredientsList, Ingredient.class.getClassLoader());
        } else {
            mIngredientsList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(price);
        dest.writeString(imageURI);
        if (mIngredientsList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mIngredientsList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
