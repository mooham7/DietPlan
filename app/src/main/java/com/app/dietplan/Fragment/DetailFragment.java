package com.app.dietplan.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.dietplan.R;
import com.app.dietplan.Util.Constant_Api;
import com.app.dietplan.Util.Method;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by admin on 30-10-2017.
 */

public class DetailFragment extends Fragment {

    private int position, columnWidth;
    private String type;
    private Method method;
    private TextView textViewDetail, textView_title;
    private View view_layout;
    private RoundedImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.detail_fragment, container, false);

        method = new Method(getActivity());
        position = getArguments().getInt("position");
        type = getArguments().getString("type");

        columnWidth = method.getScreenWidth();

        textView_title = (TextView) view.findViewById(R.id.textView_title_detail_fragment);
        textViewDetail = (TextView) view.findViewById(R.id.textView_detail_detail_fragment);
        imageView = (RoundedImageView) view.findViewById(R.id.imageView_detail_fragment);
        view_layout = (View) view.findViewById(R.id.view_detail_fragment);

        textView_title.setTypeface(method.scriptable);

        imageView.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth, columnWidth / 2));
        view_layout.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth, columnWidth / 2));

        if (type.equals("notification")) {
            if (Constant_Api.notificationSCL != null) {
                Glide.with(getActivity()).load(Constant_Api.notificationSCL.get(position).getDiet_image()).into(imageView);
                textView_title.setText(Html.fromHtml(Constant_Api.notificationSCL.get(position).getDiet_title()));
                textViewDetail.setText(Html.fromHtml(Constant_Api.notificationSCL.get(position).getDiet_info()));
            }

        } else {
            Glide.with(getActivity()).load(Constant_Api.subCategoryLists.get(position).getDiet_image()).into(imageView);
            textView_title.setText(Html.fromHtml(Constant_Api.subCategoryLists.get(position).getDiet_title()));
            textViewDetail.setText(Html.fromHtml(Constant_Api.subCategoryLists.get(position).getDiet_info()));

        }
        return view;

    }


}
