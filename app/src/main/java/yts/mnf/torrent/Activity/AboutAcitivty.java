package yts.mnf.torrent.Activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;
import yts.mnf.torrent.AppController;
import yts.mnf.torrent.R;
import yts.mnf.torrent.Tools.Url;

public class AboutAcitivty extends AppCompatActivity {

    @BindView(R.id.btn_site)
    FloatingTextButton btnSite;

    @BindView(R.id.btn_apk)
    FloatingTextButton btnApk;

    @BindView(R.id.app_ver)
    TextView appVersionTv;

    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_acitivty);
        ButterKnife.bind(this);

        c = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            appVersionTv.setText("App Version  V "+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }




        btnSite.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AppController().startBrowser(Url.AppSiteUrl,c);
        }
    });

        btnApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AppController().startBrowser(Url.AppDownloadUrl,c);
            }
        });

    }

}
