package yts.mnf.torrent.Activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import yts.mnf.torrent.R;
import yts.mnf.torrent.Tools.Config;

public class ImageViewActivity extends AppCompatActivity {


    private static final String TAG = "ImageViewActivity" ;
    @BindView(R.id.image_viewer)
    ImageView imageViewer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        if(getIntent().hasExtra("img_url")) {
            Log.e(TAG,"activity has extra ");
            String url = getIntent().getStringExtra("img_url");
            Log.e(TAG,"activity has extra url =  "+url);
            Config.loadImage(imageViewer,url);



        }else{
            Log.e(TAG,"activity has no extra ");

        }
    }
}
