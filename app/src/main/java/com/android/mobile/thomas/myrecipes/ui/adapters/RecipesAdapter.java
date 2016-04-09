package com.android.mobile.thomas.myrecipes.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.models.data.Recipe;
import com.android.mobile.thomas.myrecipes.models.data.Trolley;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    public interface IRecipesAdapterListener {
        void onItemClicked(Recipe recipe);
    }

    private  IRecipesAdapterListener listener;

    public void setListener(IRecipesAdapterListener listener) {
        this.listener = listener;
    }

    private List<Recipe> mRecipesList;
    Context mContext;
    final String TAG = "RecipesAdapter";

    public RecipesAdapter(Context context, List<Recipe> recipesList) {
        this.mRecipesList = recipesList;
        mContext = context;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipeitem, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {

        holder.name.setText(mRecipesList.get(position).getName());

        if (mRecipesList.get(position).getImageUri() != null) {

            Picasso.with(mContext)
                    .load(mRecipesList.get(position).getImageUri())
                    .fit()
                    .centerCrop()
                    .into(holder.image);

        } else {

            Picasso.with(mContext)
                    .load(R.drawable.ic_action_picture)
                    .fit()
                    .centerCrop()
                    .into(holder.image);
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

        holder.recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClicked(mRecipesList.get(position));
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
    }

    @Override
    public int getItemCount() {
        return mRecipesList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        ImageView image;
        ImageButton trolley;
        View recipeButton;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textViewNameRecipe);
            image = (ImageView) itemView.findViewById(R.id.recipe_img);
            trolley = (ImageButton) itemView.findViewById(R.id.imageButtonTrolley);
            price = (TextView) itemView.findViewById(R.id.textViewPrice);
            recipeButton = itemView.findViewById(R.id.recipe_item_button);
        }
    }
}
