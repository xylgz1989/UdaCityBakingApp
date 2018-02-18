package com.udacity.xyl.udacitybakingapp.recipeList;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.udacity.xyl.udacitybakingapp.recipeList.RecipeListPresenterImpl.BASE_URL;

/**
 * Created by xyl on 2018/1/21 0021.
 */

public class RecipeListBizImpl implements RecipeListContract.RecipeListBiz {

    RecipeListApiService service;

    public RecipeListBizImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RecipeListApiService.class);
//        service = Re
    }

    @Override
    public Observable<List<RecipeBean>> refreshRecipe() {
        return service.refreshRecipe().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }

}
