package com.udacity.xyl.udacitybakingapp.recipeList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.udacity.xyl.udacitybakingapp.R;

public class RecipeActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ButterKnife.apply();

       toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        getSupportFragmentManager().beginTransaction().add(R.id.main_frag_container,
                new MainRecipeFragment()).commit();

    }


}
