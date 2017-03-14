package yts.mnf.com;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by muneef on 14/03/17.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }



    public void startBrowser(String url, Context c){
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "https://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        c.startActivity(browserIntent);
    }
}
