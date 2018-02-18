package com.udacity.xyl.udacitybakingapp.recipeList;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by xyl on 2017/11/19 0019.
 */

public interface RecipeListContract {

    interface RecipeListBiz{
        Observable<List<RecipeBean>> refreshRecipe();

//        Observable loadMoreRecipe();

    }

    interface RecipeListView{
        void displayRecipes(List<RecipeBean> recipes);

        void displayLoadErrorMsg(String msg);

        void toRecipeDetails(RecipeBean recipe);

        Context getContext();
    }

    interface RecipeListPresenter{
        void refreshRecipe();

//        void loadMoreRecipe();

    }

}
