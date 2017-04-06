package yts.mnf.torrent.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import yts.mnf.torrent.R;
import yts.mnf.torrent.Tools.PreferensHandler;

public class SettingsActivityCustom extends AppCompatActivity {
    @BindView(R.id.cb_theme)
    CheckBox darkThemeCB;
    PreferensHandler pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_custom);
        ButterKnife.bind(this);
        pref = new PreferensHandler(this);
        darkThemeCB.setChecked(pref.getThemeDark());
        darkThemeCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("Settings","theme checked = "+isChecked);
                    pref.setThemeDark(isChecked);
            }
        });


    }
}
