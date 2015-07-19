package com.android.mobile.thomas.myrecipes.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.models.data.Ingredient;

import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class ChooseIngredientsAdapter extends BaseAdapter {

    List<Ingredient> mListIngredients;
    LayoutInflater mInflater;
    //public HashMap<String,String> checked = new HashMap<String,String>();

    public ChooseIngredientsAdapter(Context context, List<Ingredient> listIngredients) {
        mListIngredients = listIngredients;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mListIngredients.size();
    }

    @Override
    public Object getItem(int position) {
        return mListIngredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mListIngredients.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView==null) {
            //if the convertView is null, we create it
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.choose_ingredient_item, null);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxIngredient);
            holder.hiddenId = (TextView) convertView.findViewById(R.id.textViewHiddenIdIngredient);

            convertView.setTag(holder);
            convertView.setTag(R.id.checkBoxIngredient, holder.checkBox);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.checkBox.setText(mListIngredients.get(position).getName());

        return convertView;
    }

    private class ViewHolder {
        CheckBox checkBox;
        TextView hiddenId;
    }
}
