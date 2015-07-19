package com.android.mobile.thomas.myrecipes.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.dao.RecipePersistence;
import com.android.mobile.thomas.myrecipes.models.data.Ingredient;
import com.android.mobile.thomas.myrecipes.models.data.Recipe;
import com.android.mobile.thomas.myrecipes.utils.Utils;

/**
 * Created by Thomas on 19/07/2015.
 */
public class DisplayRecipeActivity extends Activity {

    private final String TAG = "DisplayRecipeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_recipe);


    }

    @Override
    protected void onStart() {
        super.onStart();

        // TODO have to send the object through a bundle instead of using the db over and over again
        Intent intent = getIntent();
        long id = intent.getLongExtra("ID", -1);

        Log.v(TAG, "retrieved id value = " + id);

        RecipePersistence persistence = new RecipePersistence(getApplicationContext());
        final Recipe recipe = persistence.getRecipeById(id);

        TextView name = (TextView) findViewById(R.id.textViewRecipeDisplayName);
        TextView price = (TextView) findViewById(R.id.textViewRecipeDisplayPrice);
        TextView description = (TextView) findViewById(R.id.TextViewShowDescription);
        TextView ingredients = (TextView) findViewById(R.id.TextViewIngredientsList);
        ImageView image = (ImageView) findViewById(R.id.imageViewDisplayRecipe);
        ImageButton deleteButton = (ImageButton) findViewById(R.id.imageButtonDeleteRecipe);

        name.setText(recipe.getName());
        price.setText(Integer.toString(recipe.getPrice()));
        description.setText(recipe.getDescription());

        if (recipe.getImageUri() != null) {
            Bitmap recipeImage = Utils.convertUritoBitMap(recipe.getImageUri(), getApplicationContext());
            image.setImageBitmap(recipeImage);
        }

        Log.v(TAG, "list ingredients size = " + recipe.getmIngredientsList().size());

        String ingredientsText = "";
        for (Ingredient ingredient: recipe.getmIngredientsList()) {
            Log.v(TAG, "ingredient name = " + ingredient.getName());
            ingredientsText = ingredientsText + " " + ingredient.getName();
        }
        Log.v(TAG, "ingredients to display = " + ingredientsText);
        ingredients.setText(ingredientsText);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DisplayRecipeActivity.this);

                // set title
                alertDialogBuilder.setTitle("Delete Recipe");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure to delete this Recipe?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                RecipePersistence db = new RecipePersistence(DisplayRecipeActivity.this);

                                db.delete(recipe);
                                DisplayRecipeActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        Button video = (Button) findViewById(R.id.buttonVideo);
        video.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(DisplayRecipeActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });
    }

}
