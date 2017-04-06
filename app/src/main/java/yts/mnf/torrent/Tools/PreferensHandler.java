package yts.mnf.torrent.Tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by muneef on 06/04/17.
 */

public class PreferensHandler {
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context c;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "settings_pref";
    final String app_theme = "apptheme";

    @SuppressLint("CommitPrefEdits")
    public PreferensHandler(Context context) {
        this.c = context;
        pref = c.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setThemeDark(Boolean var){
        editor.putBoolean(app_theme, var);
        editor.commit();
    }

    public Boolean getThemeDark(){
        return pref.getBoolean(app_theme, false);
    }



}
