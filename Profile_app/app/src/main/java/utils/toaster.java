package utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by root on 3/23/15.
 */
public class toaster extends Activity
{

    public static void show (Activity activity, String value)
    {

        Toast.makeText(activity, value, Toast.LENGTH_SHORT).show();

    }

}
