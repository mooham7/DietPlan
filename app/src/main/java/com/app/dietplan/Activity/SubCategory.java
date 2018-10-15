package com.app.dietplan.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dietplan.Adapter.SubCategoryAdapter;
import com.app.dietplan.Item.SubCategoryList;
import com.app.dietplan.R;
import com.app.dietplan.Util.Constant_Api;
import com.app.dietplan.Util.Method;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SubCategory extends AppCompatActivity {

    public Toolbar toolbar;
    private String categoryName, category_id;
    private RecyclerView recyclerView;
    private TextView textView;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private SubCategoryAdapter subCategoryAdapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        Method.forceRTLIfSupported(getWindow(), SubCategory.this);

        Intent intent = getIntent();
        category_id = intent.getStringExtra("cid");
        categoryName = intent.getStringExtra("category_name");

        toolbar = (Toolbar) findViewById(R.id.toolbar_sub_category);
        toolbar.setTitle(categoryName);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_sub_category);

        if (Method.personalization_ad) {
            Method.showPersonalizedAds(linearLayout, SubCategory.this);
        } else {
            Method.showNonPersonalizedAds(linearLayout, SubCategory.this);
        }

        progressBar = (ProgressBar) findViewById(R.id.progressbar_sub_category);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_sub_category);
        textView = (TextView) findViewById(R.id.textView_sub_category);
        textView.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SubCategory.this);
        recyclerView.setLayoutManager(layoutManager);

        if (Method.isNetworkAvailable(SubCategory.this)) {
            SubCategory();
        } else {
            Toast.makeText(SubCategory.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void SubCategory() {

        progressBar.setVisibility(View.VISIBLE);

        Constant_Api.subCategoryLists.clear();

        String url = Constant_Api.sub_category + category_id;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("DIET_APP");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String cid = object.getString("cat_id");
                        String diet_title = object.getString("diet_title");
                        String diet_info = object.getString("diet_info");
                        String diet_image = object.getString("diet_image");

                        Constant_Api.subCategoryLists.add(new SubCategoryList(id, cid, diet_title, diet_info, diet_image));

                    }
                    if (Constant_Api.subCategoryLists.size() == 0) {
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        subCategoryAdapter = new SubCategoryAdapter(SubCategory.this, Constant_Api.subCategoryLists);
                        recyclerView.setAdapter(subCategoryAdapter);
                    }

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
