package com.android.mobile.thomas.myrecipes.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.models.data.Recipe;
import com.android.mobile.thomas.myrecipes.models.data.Trolley;
import com.android.mobile.thomas.myrecipes.utils.Utils;

import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class RecipesAdapter extends BaseAdapter {

    private List<Recipe> mRecipesList;
    LayoutInflater mInflater;
    Context mContext;
    final String TAG = "RecipesAdapter";

    public RecipesAdapter(Context context, List<Recipe> recipesList) {
        this.mRecipesList = recipesList;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mRecipesList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mRecipesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return mRecipesList.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;

        if (convertView==null) {
            //if the convertView is null, we create it
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.recipeitem, null);

            holder.name = (TextView) convertView.findViewById(R.id.textViewNameRecipe);
            holder.image = (ImageView) convertView.findViewById(R.id.recipe_img);
            holder.trolley = (ImageButton) convertView.findViewById(R.id.imageButtonTrolley);
            holder.price = (TextView) convertView.findViewById(R.id.textViewPrice);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(mRecipesList.get(position).getName());

        if (mRecipesList.get(position).getImageUri() != null) {

            Bitmap recipeImage = Utils.convertUritoBitMap(mRecipesList.get(position).getImageUri(), mContext);
            holder.image.setImageBitmap(recipeImage);
        } else {
            holder.image.setImageResource(R.drawable.ic_action_picture);
        }

        Trolley trolley = Trolley.getInstance();
        holder.trolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trolley trolley = Trolley.getInstance();
                Recipe recipe = mRecipesList.get(position);
                ImageButton button = (ImageButton) v.findViewById(R.id.imageButtonTrolley);
                button.setSelected(!button.isSelected());

                if (button.isSelected()) {
                    trolley.addRecipe(recipe);
                    button.setImageResource(R.drawable.luggage_trolley_red);

                } else {
                    trolley.removeRecipe(recipe);
                    button.setImageResource(R.drawable.luggage_trolley);
                }
            }
        });

        switch (mRecipesList.get(position).getPrice()) {
            case 0:
                holder.price.setText(R.string.cheapPrice);
                break;

            case 1:
                holder.price.setText(R.string.MediumPrice);
                break;

            case 2:
                holder.price.setText(R.string.ExpensivePrice);
                break;

            default:
                holder.price.setText(R.string.cheapPrice);
        }

        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView price;
        ImageView image;
        ImageButton trolley;
    }
}
