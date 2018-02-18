package com.udacity.xyl.udacitybakingapp.recipeList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by xyl on 2018/1/21 0021.
 */

public interface RecipeListApiService {
@GET("baking-cn.json")
    Observable<List<RecipeBean>> refreshRecipe();
}
