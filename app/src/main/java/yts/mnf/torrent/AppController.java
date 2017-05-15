package yts.mnf.torrent;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.appnext.ads.AdsError;
import com.appnext.ads.interstitial.Interstitial;
import com.appnext.ads.interstitial.InterstitialConfig;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.startapp.android.publish.adsCommon.AutoInterstitialPreferences;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import io.realm.Realm;
import yts.mnf.torrent.Models.DBModel.WishlistModel;
import yts.mnf.torrent.Tools.Config;
import yts.mnf.torrent.Tools.PreferensHandler;

/**
 * Created by muneef on 14/03/17.
 */

public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private FirebaseAnalytics mFirebaseAnalytics;

    private static AppController mInstance;
    Context c;
    PreferensHandler pref;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        c = this;
        pref = new PreferensHandler(mInstance);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
      //  MobileAds.initialize(getApplicationContext(), "ca-app-pub-7269223551241818~5494231080");
        StartAppSDK.init(this, "203903010", true);
        StartAppAd.disableSplash();
        StartAppAd.disableAutoInterstitial();

        Realm.init(getInstance());



        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                Log.e("AppController","activity name = "+activity.getLocalClassName());

                if(activity.getLocalClassName().equals("Activity.DetailsActivity")||activity.getLocalClassName().equals("Activity.PopcornDetailActivity")){

                    Log.e("AppController","inside if activity name = "+activity.getLocalClassName());

                    if(pref.getClicks()>= Config.getAdClickLimit()){
                        InterstitialConfig config = new InterstitialConfig();
                        config.setMute(true);
                        final Interstitial interstitial_Ad = new Interstitial(c, c.getResources().getString(R.string.appnext_placement_id),config);
                        interstitial_Ad.loadAd();
                        interstitial_Ad.setOnAdLoadedCallback(new OnAdLoaded() {
                            @Override
                            public void adLoaded() {
                                Log.e(TAG,"ad load callback");
                                interstitial_Ad.showAd();
                                pref.clearClicks();

                            }
                        });
                        interstitial_Ad.setOnAdErrorCallback(new OnAdError() {
                            @Override
                            public void adError(String error) {
                                switch (error){
                                    case AdsError.NO_ADS:
                                        Log.e("appnext", "no ads");
                                        break;
                                    case AdsError.CONNECTION_ERROR:
                                        Log.e("appnext", "connection problem");
                                        break;
                                    default:
                                        Log.e("appnext", "other error");
                                }
                            }
                        });
                    }
                }

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }



    public void startBrowser(String url, Context c){
        logFirebase("startBrowser = "+url);
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "https://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        c.startActivity(browserIntent);

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }





    public void logFirebase(String msg){
      /*  Bundle bundle = new Bundle();
        bundle.putString("Action", msg);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);*/
    }
}
