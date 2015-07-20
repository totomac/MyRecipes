package com.android.mobile.thomas.myrecipes.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.dao.IngredientPersistence;
import com.android.mobile.thomas.myrecipes.models.data.Ingredient;

/**
 * Created by Thomas on 19/07/2015.
 */
public class CreateIngredientActivity extends Activity {

    public CreateIngredientActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_ingredient);


    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        Button validate = (Button) findViewById(R.id.buttonValidateIngredient);

        validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                EditText nameEdited = (EditText) findViewById(R.id.editTextIngredientName);

                String name = nameEdited.getText().toString();

                Ingredient ingredient = new Ingredient(-1, name);

                IngredientPersistence persistence = new IngredientPersistence(getApplicationContext());
                persistence.insert(ingredient);

                // TODO use fragment manager to display the right fragment in the main activity
                Intent intent = new Intent(CreateIngredientActivity.this, NavigationHomeActivity.class);
                startActivity(intent);

            }
        });
    }
}
