package com.android.mobile.thomas.myrecipes.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.models.data.Ingredient;
import com.android.mobile.thomas.myrecipes.models.data.Trolley;

import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class ShoppingAdapter extends BaseAdapter {

    private List<Ingredient> mIngredientList;
    private LayoutInflater mInflater;
    private final String TAG = "ShoppingAdapter";

    public ShoppingAdapter(Context context, Trolley trolley) {
        if (context == null) Log.v(TAG, "context null");
        mInflater = LayoutInflater.from(context);
        mIngredientList = trolley.getIngredientList();
    }

    @Override
    public int getCount() {
        return mIngredientList.size();
    }

    @Override
    public Object getItem(int position) {
        return mIngredientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mIngredientList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView==null) {
            //if the convertView is null, we create it
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.shopping_item, null);

            holder.name = (TextView) convertView.findViewById(R.id.textViewShoppingName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(mIngredientList.get(position).getName());

        return convertView;
    }

    private class ViewHolder {
        TextView name;
    }
}
