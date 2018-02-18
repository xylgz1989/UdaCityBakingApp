package com.udacity.xyl.udacitybakingapp.recipeList;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.udacity.xyl.udacitybakingapp.R;
import com.udacity.xyl.udacitybakingapp.recipeStep.RecipeStepListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

/**
 * Created by xyl on 2018/1/14 0014.
 */

public class MainRecipeFragment extends Fragment implements RecipeListContract.RecipeListView, OnLoadmoreListener, OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rv_main_recipes)
    RecyclerView rv;
    private BaseQuickAdapter<RecipeBean,BaseViewHolder> adapter;
    @BindView(R.id.main_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private RecipeListContract.RecipeListPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.frag_main,container,false);
        ButterKnife.bind(this,mView);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        return mView ;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter = new RecipeListPresenterImpl(this);
        presenter.refreshRecipe();
    }

    @Override
    public void displayRecipes(List<RecipeBean> recipes) {
        if(recipes != null){
            adapter = new RecipeListAdapter(R.layout.item_recipe,recipes);
            rv.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            smartRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void displayLoadErrorMsg(String msg) {
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void toRecipeDetails(RecipeBean recipe) {
        Intent itn = new Intent(getActivity(), RecipeStepListActivity.class);
        itn.putExtra(RecipeStepListActivity.RECIPE_ITEM,recipe);
        startActivity(itn);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        presenter.refreshRecipe();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Object selected = adapter.getItem(position);
        if(selected instanceof RecipeBean){
            toRecipeDetails((RecipeBean) selected);
        }
    }
}
