package cn.dreamreality.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

/**
 * Created by liuhaibao on 15/3/8.
 */
public class SettingsUtils {


    public static String SETTINGS = "settings";

    public static String getSettings(Context context,String key){
        SharedPreferences settings = getPreferences(context);
        return settings.getString(key,null);
    }

    public static void putSettings(Context context,String key,String value){
        SharedPreferences settings = getPreferences(context);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static void removeString(Context context,String key){
        SharedPreferences settings = getPreferences(context);

        settings.edit().remove(key).commit();
    }

    private static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences(SETTINGS, 0);
    }
}
