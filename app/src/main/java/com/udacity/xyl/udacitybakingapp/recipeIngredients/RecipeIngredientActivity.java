package com.udacity.xyl.udacitybakingapp.recipeIngredients;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.udacity.xyl.udacitybakingapp.R;

public class RecipeIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredient);
        Toolbar toolbar = findViewById(R.id.ingredient_toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putSerializable(RecipeIngredientsFragment.KEY_INGREDIENTS,
                    getIntent().getSerializableExtra(RecipeIngredientsFragment.KEY_INGREDIENTS));
//                        arguments.putString(RecipeStepDetailFragment.ARG_ITEM_ID, );
            RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredient_frag_container, fragment)
//                    .addToBackStack(null)
                    .commit();
        }
    }
}
