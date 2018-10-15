package com.app.dietplan.Util;

import android.app.Activity;
import android.os.Bundle;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class PopUpAds {

    public static void ShowInterstitialAds(Activity activity) {

        Constant_Api.AD_COUNT += 1;
        if (Constant_Api.AD_COUNT == Constant_Api.AD_COUNT_SHOW) {
            final InterstitialAd mInterstitial = new InterstitialAd(activity);
            AdRequest adRequest;
            if (Method.personalization_ad) {
                adRequest = new AdRequest.Builder()
                        .build();
            } else {
                Bundle extras = new Bundle();
                extras.putString("npa", "1");
                adRequest = new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build();
            }
            mInterstitial.setAdUnitId(Constant_Api.aboutUsList.getInterstital_ad_id());
            mInterstitial.loadAd(adRequest);
            mInterstitial.show();
            Constant_Api.AD_COUNT = 0;
            if (!mInterstitial.isLoaded()) {
                AdRequest adRequest1;
                if (Method.personalization_ad) {
                    adRequest1 = new AdRequest.Builder()
                            .build();
                } else {
                    Bundle extras = new Bundle();
                    extras.putString("npa", "1");
                    adRequest1 = new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                            .build();
                }
                mInterstitial.loadAd(adRequest1);
            }
            mInterstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    mInterstitial.show();
                }
            });
        }
    }
}
