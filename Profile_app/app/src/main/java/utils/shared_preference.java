package utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Created by root on 3/24/15.
 */
public class shared_preference extends  Activity
{
    public static SharedPreferences  preference;

    public static SharedPreferences.Editor editor = preference.edit();



    //set the storage file of the shared preference
    public SharedPreferences storage(String storage, int value)
    {
        preference = getSharedPreferences(storage, value);

        return preference;

    }

    public static String getString_ (String key, String defaultKey)
    {
        String result;

        result = preference.getString(key, defaultKey);

        return result;
    }

    public static int getInt_ (String key, int defaultKey)
    {
        int result;

        result = preference.getInt(key, defaultKey);

        return result;
    }

    public static boolean getBoolean_ (String key, boolean defaultKey)
    {
        boolean result;

        result = preference.getBoolean(key, defaultKey);

        return result;
    }

    public static float getFloat_ (String key, float defaultKey)
    {
        float result;

        result = preference.getFloat(key, defaultKey);
        return result;

    }

    public void putString_ (String key, String value)
    {
        editor.putString(key, value);
    }

    public void putInt_ (String key, int value)
    {
        editor.putInt(key, value);
    }

   public void putFloat_ (String key, float value)
   {
       editor.putFloat(key, value);
   }

    public void putBoolean_ (String key, boolean value)
    {
        editor.putBoolean(key, value);
    }

    public void commitEditor_()
    {
        editor.commit();
    }

}
