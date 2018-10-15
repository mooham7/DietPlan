package com.app.dietplan.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.dietplan.Activity.SubCategory;
import com.app.dietplan.Item.CategoryList;
import com.app.dietplan.R;
import com.app.dietplan.Util.Constant_Api;
import com.app.dietplan.Util.Method;
import com.app.dietplan.Util.PopUpAds;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by admin on 28-10-2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Activity activity;
    private Method method;
    private int columnWidth;
    private List<CategoryList> categoryLists;

    public CategoryAdapter(Activity activity, List<CategoryList> categoryLists) {
        this.activity = activity;
        this.categoryLists = categoryLists;
        method = new Method(activity);
        columnWidth = (int) ((method.getScreenWidth()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.category_adapter, parent, false);

        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth / 2, columnWidth / 4));
        holder.view.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth / 2, columnWidth / 4));
        Glide.with(activity).load(categoryLists.get(position).getCategory_image())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.smoothProgressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.smoothProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.imageView);
        holder.textView.setTypeface(method.scriptable);
        holder.textView.setText(categoryLists.get(position).getCategory_name());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant_Api.aboutUsList != null) {
                    if (Constant_Api.aboutUsList.isInterstital_ad()) {
                        PopUpAds.ShowInterstitialAds(activity);
                    }
                }
                activity.startActivity(new Intent(activity, SubCategory.class)
                        .putExtra("cid", categoryLists.get(position).getCid())
                        .putExtra("category_name", categoryLists.get(position).getCategory_name()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private View view;
        private RoundedImageView imageView;
        private SmoothProgressBar smoothProgressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.textView_category_adapter);
            view = (View) itemView.findViewById(R.id.view_category_adapter);
            imageView = (RoundedImageView) itemView.findViewById(R.id.imageView_category_adapter);
            smoothProgressBar = (SmoothProgressBar) itemView.findViewById(R.id.SmoothProgressBar_category_adapter);

        }

    }

}
