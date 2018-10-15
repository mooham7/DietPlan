package com.app.dietplan.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.dietplan.Activity.SubCategoryDetail;
import com.app.dietplan.Item.SubCategoryList;
import com.app.dietplan.R;
import com.app.dietplan.Util.Constant_Api;
import com.app.dietplan.Util.Method;
import com.app.dietplan.Util.PopUpAds;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by admin on 28-10-2017.
 */

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    private Activity activity;
    private Method method;
    private int columnWidth;
    String strings;
    StringBuilder sb;
    private List<SubCategoryList> subCategoryLists;

    public SubCategoryAdapter(Activity activity, List<SubCategoryList> subCategoryLists) {
        this.activity = activity;
        this.subCategoryLists = subCategoryLists;
        method = new Method(activity);
        columnWidth = (int) ((method.getScreenWidth()));
        sb = new StringBuilder(subCategoryLists.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.sub_category_adapter, parent, false);

        return new SubCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth, columnWidth / 6));
        holder.view.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth, columnWidth / 6));
        holder.textView.setTypeface(method.scriptable);
        holder.textView.setText(subCategoryLists.get(position).getDiet_title());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < subCategoryLists.size(); i++) {
                    sb.append(subCategoryLists.get(i).getDiet_title()).append(",,/");
                }
                strings = sb.toString();
                if (Constant_Api.aboutUsList != null) {
                    if (Constant_Api.aboutUsList.isInterstital_ad()) {
                        PopUpAds.ShowInterstitialAds(activity);
                    }
                }
                activity.startActivity(new Intent(activity, SubCategoryDetail.class)
                        .putExtra("position", position)
                        .putExtra("size", subCategoryLists.size())
                        .putExtra("day", strings)
                        .putExtra("type", "data"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategoryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private View view;
        private RoundedImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.textView_sub_category_adapter);
            view = (View) itemView.findViewById(R.id.view_sub_category_adapter);
            imageView = (RoundedImageView) itemView.findViewById(R.id.imageView_sub_category_adapter);
        }
    }
}
