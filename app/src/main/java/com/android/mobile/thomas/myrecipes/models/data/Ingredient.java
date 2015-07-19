package com.android.mobile.thomas.myrecipes.models.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android.mobile.thomas.myrecipes.utils.AppetitOpenHelper;

/**
 * Created by Thomas on 19/07/2015.
 */
public class Ingredient implements Parcelable {
    private String TAG = "Ingredient";
    protected long id;
    protected String mName;

    public Ingredient(long id, String name) {
        this.id = id;
        mName = name;
    }

    public Ingredient(Cursor cursor) {
        int indexId = cursor.getColumnIndex(AppetitOpenHelper.COLUMN_ID_INGREDIENT);
        int indexName = cursor.getColumnIndex(AppetitOpenHelper.COLUMN_NAME_INGREDIENT);

        Log.d(TAG, "populating the Ingredient with idIndex = " + indexId + " nameIndex= " + indexName);
        this.id = cursor.getInt(indexId);
        this.mName = cursor.getString(indexName);
        Log.d(TAG, "IngredientCreated");
    }

    /*
     * Getters/Setters
     */
    public long getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        this.mName = name;
    }

    protected Ingredient(Parcel in) {
        TAG = in.readString();
        id = in.readLong();
        mName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TAG);
        dest.writeLong(id);
        dest.writeString(mName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
