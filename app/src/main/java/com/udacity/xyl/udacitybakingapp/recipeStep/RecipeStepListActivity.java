package com.udacity.xyl.udacitybakingapp.recipeStep;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.udacity.xyl.udacitybakingapp.R;
import com.udacity.xyl.udacitybakingapp.RecipeUtils;
import com.udacity.xyl.udacitybakingapp.recipeIngredients.RecipeIngredientActivity;
import com.udacity.xyl.udacitybakingapp.recipeIngredients.RecipeIngredientsFragment;
import com.udacity.xyl.udacitybakingapp.recipeList.RecipeBean;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;

import static com.udacity.xyl.udacitybakingapp.RecipeUtils.steps;

/**
 * An activity representing a list of Recipe Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeStepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeStepListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static final String RECIPE_ITEM = "recipe_item";
    private static final String TAG = "RecipeStepListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipestep_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ButterKnife.bind(this);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        View recyclerView = findViewById(R.id.recipestep_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.recipestep_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Object obj = getIntent().getSerializableExtra(RECIPE_ITEM);
        if (obj instanceof RecipeBean){
            RecipeBean recipe = (RecipeBean) obj;
            steps = recipe.getSteps();
            List<RecipeBean.StepsBean> steps = recipe.getSteps();
            BaseQuickAdapter adapter = new RecipeStepAdapter(steps);
            View headerView = LayoutInflater.from(this).inflate(R.layout.recipestep_list_content,null);
            TextView tv_header = headerView.findViewById(R.id.id);
            headerView.findViewById(R.id.content).setVisibility(View.GONE);
            tv_header.setText(R.string.title_recipe_ingredient);
            adapter.addHeaderView(headerView);
            tv_header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showIngredients();
                }
            });
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL));
        }
    }


    void showIngredients(){
        Object obj = getIntent().getSerializableExtra(RECIPE_ITEM);
        if ( !(obj instanceof RecipeBean)) {
            return;
        }
        RecipeBean recipe = (RecipeBean) obj;
        if (mTwoPane) {
            //start Fragment
            Bundle arguments = new Bundle();
            arguments.putSerializable(RecipeIngredientsFragment.KEY_INGREDIENTS, (Serializable) recipe.getIngredients());
            RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipestep_detail_container, fragment)
                    .commit();
        } else {
            //start Activity
            Context context = this;
            Intent intent = new Intent(context, RecipeIngredientActivity.class);
            intent.putExtra(RecipeIngredientsFragment.KEY_INGREDIENTS, (Serializable) recipe.getIngredients());
            context.startActivity(intent);
        }
    }


    class RecipeStepAdapter extends BaseQuickAdapter<RecipeBean.StepsBean,BaseViewHolder>{


        public RecipeStepAdapter(@Nullable List<RecipeBean.StepsBean> data) {
            super(R.layout.recipestep_list_content,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final RecipeBean.StepsBean item) {
            if(item.getId() == 0){
                helper.setText(R.id.id,"Recipe Introduction ");
            }else{
                helper.setText(R.id.id,"Step "+(item.getId()));
            }
            helper.setText(R.id.content,item.getShortDescription());

            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "RecipeStepAdapter: step="+item.toString());
                    if (mTwoPane) {
                        //start Fragment
                        Bundle arguments = new Bundle();
                        arguments.putSerializable(RecipeStepDetailFragment.RECIPE_STEP,item);
//                        arguments.putString(RecipeStepDetailFragment.ARG_ITEM_ID, );
                        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipestep_detail_container, fragment)
                                .commit();
                    } else {
                        //start Activity
                        Context context = v.getContext();
                        Intent intent = new Intent(context, RecipeStepDetailActivity.class);
                        intent.putExtra(RecipeStepDetailFragment.RECIPE_STEP,item);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }



}
