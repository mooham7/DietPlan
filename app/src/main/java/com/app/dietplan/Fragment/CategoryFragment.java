package com.app.dietplan.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.dietplan.Activity.MainActivity;
import com.app.dietplan.Adapter.CategoryAdapter;
import com.app.dietplan.Item.CategoryList;
import com.app.dietplan.R;
import com.app.dietplan.Util.Constant_Api;
import com.app.dietplan.Util.Method;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by admin on 27-10-2017.
 */

public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<CategoryList> categoryLists;
    private ProgressBar progressBar;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.category_fragment, container, false);

        MainActivity.toolbar.setTitle(getResources().getString(R.string.app_name));

        categoryLists = new ArrayList<>();

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_sub_category_detail);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_category_fragment);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        if (Method.isNetworkAvailable(getActivity())) {
            Category();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }

        return view;

    }

    public void Category() {

        progressBar.setVisibility(View.VISIBLE);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant_Api.category, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("DIET_APP");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String cid = object.getString("cid");
                        String category_name = object.getString("category_name");
                        String category_image = object.getString("category_image");
                        String category_image_thumb = object.getString("category_image_thumb");

                        categoryLists.add(new CategoryList(cid, category_name, category_image, category_image_thumb));

                    }
                    categoryAdapter = new CategoryAdapter(getActivity(), categoryLists);
                    recyclerView.setAdapter(categoryAdapter);
                    progressBar.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}
