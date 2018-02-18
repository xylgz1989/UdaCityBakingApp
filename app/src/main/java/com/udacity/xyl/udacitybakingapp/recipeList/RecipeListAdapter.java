package com.udacity.xyl.udacitybakingapp.recipeList;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.udacity.xyl.udacitybakingapp.R;

import java.util.List;

import static com.udacity.xyl.udacitybakingapp.R.mipmap.ic_launcher;
import static java.lang.System.load;

/**
 * Created by xyl on 2018/1/14 0014.
 */

public class RecipeListAdapter extends BaseQuickAdapter<RecipeBean,BaseViewHolder> {
    public RecipeListAdapter(@LayoutRes int layoutResId, @Nullable List<RecipeBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, RecipeBean item) {
        helper.setText(R.id.item_tv_recipe_name,item.getName());
        ImageView iv_pic = helper.getView(R.id.iv_recipe_preview);
        Log.d(TAG, "pic url: item.getImageUrl()="+item.getImageUrl());
//        if(!TextUtils.isEmpty(item.getImageUrl())){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.ic_launcher).
                    error(R.mipmap.ic_launcher).override(200,200);
            Glide.with(iv_pic.getContext()).applyDefaultRequestOptions(requestOptions).
                    load(item.getImageUrl()).into(iv_pic);
//        }
    }
}
