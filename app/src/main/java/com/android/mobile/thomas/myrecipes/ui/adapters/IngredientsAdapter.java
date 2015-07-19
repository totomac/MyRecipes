package com.android.mobile.thomas.myrecipes.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.models.data.Ingredient;

import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class IngredientsAdapter extends BaseAdapter {

    private List<Ingredient> mIngredientsList;
    LayoutInflater mInflater;

    public IngredientsAdapter(Context context, List<Ingredient> ingredientsList) {
        this.mIngredientsList = ingredientsList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mIngredientsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mIngredientsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mIngredientsList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView==null) {
            //if the convertView is null, we create it
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.ingredientitem, null);

            holder.name = (TextView) convertView.findViewById(R.id.textViewNameIngredient);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(mIngredientsList.get(position).getName());

        return convertView;
    }

    private class ViewHolder {
        TextView name;
    }
}
