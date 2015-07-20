package com.android.mobile.thomas.myrecipes.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.mobile.thomas.myrecipes.R;
import com.android.mobile.thomas.myrecipes.dao.RecipePersistence;
import com.android.mobile.thomas.myrecipes.models.data.Ingredient;
import com.android.mobile.thomas.myrecipes.models.data.Recipe;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 19/07/2015.
 */
public class CreateRecipesActivity extends Activity {

    private static final int ANDROID_LESS_19 = 1;
    private final int CHOOSE_INGREDIENT = 0;
    private static final int ANDROID_MORE_19 = 2;
    private String mImage = null;
    private final String TAG = "CreateRecipesActivity";
    private List<Ingredient> mIngredient = new ArrayList<Ingredient>();

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_recipe);


    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        //set the spinner
        Spinner spinnerPrice = (Spinner) findViewById(R.id.spinnerPrice);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.price_choice, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerPrice.setAdapter(adapter);

		/*
		 *  Button validate management
		 */
        Button validate = (Button) findViewById(R.id.buttonValidCreate);

        validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name = null;
                int price = -1;
                long id = -1;
                String description = null;

                EditText nameEdited = (EditText) findViewById(R.id.editTextNameRecipeCreate);
                EditText descriptionEdited = (EditText) findViewById(R.id.editTextRecipeDescription);
                Spinner spinnerPrice = (Spinner) findViewById(R.id.spinnerPrice);

                name = nameEdited.getText().toString();
                price = spinnerPrice.getSelectedItemPosition();
                description = descriptionEdited.getText().toString();

                Log.d(TAG, "name = " + name);

                if (name.isEmpty()){
                    Log.d(TAG, "No title");
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateRecipesActivity.this);

                    builder.setMessage(R.string.dialog_no_title)
                            .setTitle(R.string.dialog_Title)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });


                    AlertDialog dialog = builder.create();

                    dialog.show();
                } else {

                    Recipe recipe = new Recipe(id, name, price, mImage, description);
                    recipe.setmIngredientsList(mIngredient);

                    RecipePersistence persistence = new RecipePersistence(getApplicationContext());
                    persistence.insert(recipe);

                    Intent intent = new Intent(CreateRecipesActivity.this, NavigationHomeActivity.class);
                    startActivity(intent);
                }

            }
        });

        Button chooseButton = (Button) findViewById(R.id.buttonChooseIngredients);
        chooseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateRecipesActivity.this, ChooseIngredientsActivity.class);
                startActivityForResult(intent, CHOOSE_INGREDIENT);
            }
        });
		/*
		 * *************************************
		 */

        ImageButton picture = (ImageButton) findViewById(R.id.buttonPicture);
        picture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //the images can always been charged (permissions) on api 19
                Intent intent;
                if (Build.VERSION.SDK_INT < 19){
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, ANDROID_LESS_19);
                } else {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, ANDROID_MORE_19);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case ANDROID_LESS_19:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    mImage = selectedImage.toString();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();


                    InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);

                        ImageButton mealPicture = (ImageButton) findViewById(R.id.buttonPicture);
                        mealPicture.setImageBitmap(yourSelectedImage);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return;

            case ANDROID_MORE_19:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    mImage = selectedImage.toString();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // this part means to keep permanent permissions, so no exception after rebooting the device
                    // TODO try to do it with android studio
                    final int takeFlags = data.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                    getContentResolver().takePersistableUriPermission(selectedImage, takeFlags);

                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();


                    InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);

                        ImageButton mealPicture = (ImageButton) findViewById(R.id.buttonPicture);
                        mealPicture.setImageBitmap(yourSelectedImage);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return;

            //TODO
            // please god forgive me (should pass an object as extra instead of just the id)
            case CHOOSE_INGREDIENT:
                if(resultCode == RESULT_OK){
                    //				IngredientPersistence db = new IngredientPersistence(getApplicationContext());
                    //
                    //				Bundle extras = data.getExtras();
                    //				if (extras == null ) {
                    //					return;
                    //				}
                    //				Set<String> ks = extras.keySet();
                    //				Iterator<String> iterator = ks.iterator();
                    //				String key;
                    //				while (iterator.hasNext()) {
                    //					key = iterator.next();
                    //					Log.d(TAG, "key = " + key);
                    //					Log.d(TAG, "id = " + extras.getInt(key));
                    //					Ingredient ingredient = db.getIngredientById(extras.getInt(key));
                    //					mIngredient.add(ingredient);
                    //				}

                    mIngredient = data.getParcelableArrayListExtra("arrayList");

                    if(!mIngredient.isEmpty()) {
                        String sIngredients = "";
                        TextView ingredientsField = (TextView) findViewById(R.id.textViewRecipesDisplayed);
                        for (Ingredient ingredient : mIngredient) {
                            sIngredients += ("- " + ingredient.getName() + "\n");
                        }
                        ingredientsField.setText(sIngredients);
                    }
                }
                return;
        }
    }
}
