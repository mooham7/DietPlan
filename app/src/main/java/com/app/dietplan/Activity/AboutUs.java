package com.app.dietplan.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.dietplan.R;
import com.app.dietplan.Util.Constant_Api;
import com.app.dietplan.Util.Method;
import com.bumptech.glide.Glide;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by admin on 29-06-2017.
 */

public class AboutUs extends AppCompatActivity {

    public Toolbar toolbar;
    private TextView textView_app_name, textView_app_version, textView_app_author, textView_app_contact, textView_app_email, textView_app_website, textView_app_description;
    private ImageView app_logo;
    private LinearLayout linearLayout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Method.forceRTLIfSupported(getWindow(), AboutUs.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_about_us);
        toolbar.setTitle(getResources().getString(R.string.about_us));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textView_app_name = (TextView) findViewById(R.id.textView_app_name_about_us);
        textView_app_version = (TextView) findViewById(R.id.textView_app_version_about_us);
        textView_app_author = (TextView) findViewById(R.id.textView_app_author_about_us);
        textView_app_contact = (TextView) findViewById(R.id.textView_app_contact_about_us);
        textView_app_email = (TextView) findViewById(R.id.textView_app_email_about_us);
        textView_app_website = (TextView) findViewById(R.id.textView_app_website_about_us);
        textView_app_description = (TextView) findViewById(R.id.textView_app_description_about_us);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_about_us);

        app_logo = (ImageView) findViewById(R.id.app_logo_about_us);


        if (Method.personalization_ad) {
            Method.showPersonalizedAds(linearLayout, AboutUs.this);
        } else {
            Method.showNonPersonalizedAds(linearLayout, AboutUs.this);
        }

        if (Constant_Api.aboutUsList != null) {

            Glide.with(getApplication()).load(Constant_Api.image + Constant_Api.aboutUsList.getApp_logo())
                    .into(app_logo);

            textView_app_name.setText(Constant_Api.aboutUsList.getApp_name());

            textView_app_version.setText(Constant_Api.aboutUsList.getApp_version());
            textView_app_author.setText(Constant_Api.aboutUsList.getApp_author());
            textView_app_contact.setText(Constant_Api.aboutUsList.getApp_contact());
            textView_app_email.setText(Constant_Api.aboutUsList.getApp_email());
            textView_app_website.setText(Constant_Api.aboutUsList.getApp_website());
            textView_app_description.setText(Html.fromHtml(Constant_Api.aboutUsList.getApp_description()));

        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
