package com.udacity.xyl.udacitybakingapp.recipeIngredients;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.udacity.xyl.udacitybakingapp.R;
import com.udacity.xyl.udacitybakingapp.recipeList.RecipeBean;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xyl on 2018/2/16 0016.
 */

public class RecipeIngredientsFragment extends Fragment {
    @BindView(R.id.rv_ingredient)
    RecyclerView rv_ingredient;

    public static final String KEY_INGREDIENTS = "ingredients";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.frag_recipe_ingredient,container,false);
        ButterKnife.bind(this,mView);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments().containsKey(KEY_INGREDIENTS) && getArguments().getSerializable(KEY_INGREDIENTS) != null){
            Serializable serializable = getArguments().getSerializable(KEY_INGREDIENTS);
            if(serializable instanceof List){
                List<RecipeBean.IngredientsBean> ingredients = (List<RecipeBean.IngredientsBean>) serializable;
                RecyclerView.Adapter adapter = new RecipeIngredientAdapter(ingredients);
                rv_ingredient.setAdapter(adapter);
                rv_ingredient.addItemDecoration(new DividerItemDecoration(getActivity(),
                        DividerItemDecoration.VERTICAL));
            }
        }
    }

    class RecipeIngredientAdapter extends BaseQuickAdapter<RecipeBean.IngredientsBean,BaseViewHolder>{

        public RecipeIngredientAdapter(@Nullable List<RecipeBean.IngredientsBean> data) {
            super(R.layout.item_recipe_ingredient,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, RecipeBean.IngredientsBean item) {
            helper.setText(R.id.tv_ingredient_name,item.getIngredient()).
                    setText(R.id.tv_ingredient_measure_unit,item.getMeasureUnit()).
                    setText(R.id.tv_ingredient_amount,item.getQuantity());
        }
    }
}
