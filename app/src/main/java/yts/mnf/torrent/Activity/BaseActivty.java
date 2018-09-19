package yts.mnf.torrent.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tapadoo.alerter.Alerter;

import yts.mnf.torrent.R;

/**
 * Created by muneef on 29/05/17.
 */

public class BaseActivty extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    protected void showAlert(String title, String message, View.OnClickListener listner,int color) {
        Alerter alertObj = Alerter.create(this)
                .setTitle(title)
                .setText(message)
                .setBackgroundColorRes(color);

        if(listner!=null)
            alertObj
                .setOnClickListener(listner)
                .show();
        else
            alertObj.show();

    }
}
