package com.udacity.xyl.udacitybakingapp.recipeList;

import android.util.Log;

import com.udacity.xyl.udacitybakingapp.R;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by xyl on 2018/1/14 0014.
 */

public class RecipeListPresenterImpl implements RecipeListContract.RecipeListPresenter {
    private RecipeListContract.RecipeListView recipesView;
    private RecipeListContract.RecipeListBiz biz;
    private static final String TAG = "RecipeListPresenterImpl";

    public static final String JSON_URL = "https://s3.cn-north-1.amazonaws.com.cn/static-documents/nd801/ProjectResources/Baking/baking-cn.json";
    public static final String BASE_URL = "https://s3.cn-north-1.amazonaws.com.cn/static-documents/nd801/ProjectResources/Baking/";

    public RecipeListPresenterImpl(RecipeListContract.RecipeListView recipesView) {
        this.recipesView = recipesView;
        biz = new RecipeListBizImpl();
    }

    @Override
    public void refreshRecipe() {
        biz.refreshRecipe().subscribe(new Consumer<List<RecipeBean>>() {
            @Override
            public void accept(List<RecipeBean> recipes) throws Exception {
                Log.i(TAG, "refreshRecipe: recipes="+ recipes);
                recipesView.displayRecipes(recipes);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
                recipesView.displayLoadErrorMsg(recipesView.getContext().getString(R.string.load_failed));
                Log.e(TAG, "refreshRecipe: error==>"+throwable.getMessage());
            }
        });

    }

//    @Override
//    public void loadMoreRecipe() {
//
//    }

}
