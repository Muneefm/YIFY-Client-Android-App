package yts.mnf.torrent;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import io.realm.Realm;
import yts.mnf.torrent.Tools.NoSSLv3Factory;
import yts.mnf.torrent.Tools.NullHostNameVerifier;
import yts.mnf.torrent.Tools.NullX509TrustManager;
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
      //  StartAppSDK.init(this, "203903010", true);
     //   StartAppAd.disableSplash();
      //  StartAppAd.disableAutoInterstitial();
       /* HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
        SSLContext context = null;
        try {
            context = SSLContext.getInstance("TLSv1");
        } catch (NoSuchAlgorithmException e) {
            Log.e("ssl","ssl catch 2 "+e.getLocalizedMessage());

            e.printStackTrace();
        }
        try {
            context.init(null, new X509TrustManager[]{new NullX509TrustManager()}, new SecureRandom());
        } catch (KeyManagementException e) {
            Log.e("ssl","ssl catch 1 "+e.getLocalizedMessage());

            e.printStackTrace();


        }
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        HttpsURLConnection.setDefaultSSLSocketFactory(new NoSSLv3Factory());*/

        Realm.init(getInstance());

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
               activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                Log.e("AppController","activity name = "+activity.getLocalClassName());

             /*   if(activity.getLocalClassName().equals("Activity.DetailsActivity")||activity.getLocalClassName().equals("Activity.PopcornDetailActivity")){

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
                                        interstitial_Ad.destroy();
                                        break;
                                    case AdsError.CONNECTION_ERROR:
                                        Log.e("appnext", "connection problem");
                                        interstitial_Ad.destroy();
                                        break;
                                    default:
                                        Log.e("appnext", "other error");
                                        interstitial_Ad.destroy();
                                        break;
                                }
                            }
                        });
                    }
                }*/

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
    public void openMagneturi(String url, final Context c){
        Log.e("TAG","openMagneturi magnet");

        if(url.startsWith("magnet:")) {
            Log.e("TAG","url starts with magnet");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            PackageManager manager = c.getPackageManager();
            List<ResolveInfo> infos = manager.queryIntentActivities(browserIntent, 0);
            if (infos.size() > 0) {
                c.startActivity(browserIntent);
                Log.e("TAG","yes act to handle");
            } else {
                Log.e("TAG","No act to handle");
                if(new PreferensHandler(c).getThemeDark())
                    openDialogueDark(c);
                else
                    openDialogueWhite(c);

            }
        }else{
            Log.e("TAG","url does not starts with magnet");

        }
    }


    public void openDialogueWhite(final Context c){
        new MaterialDialog.Builder(c)
                .title("Install Torrent Downloader")
                .content(R.string.download_torrent_client_prompt)
                .positiveText("Download").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                startBrowser("https://play.google.com/store/apps/details?id=com.utorrent.client",c);
            }
        }).iconRes(R.mipmap.ic_launcher)
                .positiveColor(c.getResources().getColor(R.color.white))
                .contentColor(c.getResources().getColor(R.color.blue_grey800))
                .backgroundColor(c.getResources().getColor(R.color.white))
                .titleColorRes(R.color.black)
                .btnSelector(R.drawable.md_btn_selector_custom, DialogAction.POSITIVE)
                .show();
    }
    public void openDialogueDark(final Context c){
        new MaterialDialog.Builder(c)
                .title("Install Torrent Downloader")
                .content(R.string.download_torrent_client_prompt)
                .positiveText("Download").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                startBrowser("https://play.google.com/store/apps/details?id=com.utorrent.client",c);
            }
        }).iconRes(R.mipmap.ic_launcher)
                .positiveColor(c.getResources().getColor(R.color.white))
                .contentColor(c.getResources().getColor(R.color.white))
                .backgroundColor(c.getResources().getColor(R.color.blue_grey800))
                .titleColorRes(R.color.material_red_400)
                .btnSelector(R.drawable.md_btn_selector_custom, DialogAction.POSITIVE)
                .show();
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
