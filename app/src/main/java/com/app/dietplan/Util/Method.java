package com.app.dietplan.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.app.dietplan.R;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 08-08-2017.
 */

public class Method {

    public static Activity activity;
    public static boolean share = false, onBackPress = false, allowPermitionExternalStorage = false, personalization_ad = false;
    public static Bitmap mbitmap;
    private static File file;

    public static Typeface scriptable;

    public Method(Activity activity) {
        this.activity = activity;
        scriptable = Typeface.createFromAsset(activity.getAssets(), "font/latobold.ttf");
    }

    //rtl
    public static void forceRTLIfSupported(Window window, Activity activity) {
        if (activity.getResources().getString(R.string.isRTL).equals("true")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                window.getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }
    }

    public static void trackScreenView(Activity activity, String screenName) {
        YouApplication application = (YouApplication) activity.getApplication();
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    //network check
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();

        point.x = display.getWidth();
        point.y = display.getHeight();

        columnWidth = point.x;
        return columnWidth;
    }

    /*<---------------------- share & downlode and methodes ---------------------->*/

    public static void share_save(String link, String title, String description) {
        new DownloadImageTask().execute(link, title, description);
    }

    public static class DownloadImageTask extends AsyncTask<String, String, String> {

        String title, description;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                title = params[1];
                description = params[2];
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                mbitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                // Log exception
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (share) {
                ShareImage(mbitmap, title, description);
                share = false;
            }
            super.onPostExecute(s);
        }
    }

    private static void ShareImage(Bitmap finalBitmap, String title, String description) {

        String root = activity.getExternalCacheDir().getAbsolutePath();
        String fname = "Image_share" + ".jpg";
        file = new File(root, fname);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Uri contentUri = Uri.fromFile(file);
        Log.d("url", String.valueOf(contentUri));

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setData(contentUri);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(title) + "\n" + "\n" + Html.fromHtml(description));
            activity.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }

    }

    public static void isOnBackPress() {
        if (file != null) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void showPersonalizedAds(LinearLayout linearLayout, Activity activity) {

        if (Constant_Api.aboutUsList != null) {
            if (Constant_Api.aboutUsList.isBanner_ad()) {
                AdView adView = new AdView(activity);
                AdRequest adRequest = new AdRequest.Builder()
                        .build();
                adView.setAdUnitId(Constant_Api.aboutUsList.getBanner_ad_id());
                adView.setAdSize(AdSize.BANNER);
                linearLayout.addView(adView);
                adView.loadAd(adRequest);
            } else {
                linearLayout.setVisibility(View.GONE);
            }
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public static void showNonPersonalizedAds(LinearLayout linearLayout, Activity activity) {

        Bundle extras = new Bundle();
        extras.putString("npa", "1");
        if (Constant_Api.aboutUsList != null) {
            if (Constant_Api.aboutUsList.isBanner_ad()) {
                AdView adView = new AdView(activity);
                AdRequest adRequest = new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build();
                adView.setAdUnitId(Constant_Api.aboutUsList.getBanner_ad_id());
                adView.setAdSize(AdSize.BANNER);
                linearLayout.addView(adView);
                adView.loadAd(adRequest);
            } else {
                linearLayout.setVisibility(View.GONE);
            }
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }


}
