package model;

/**
 * Created by root on 3/23/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import utils.dbHelper;


public class user_auth
{
    dbHelper myHelper;


    public user_auth(Context context)
    {
        myHelper = new dbHelper(context);

    }

    //verify if username and password exist

    public boolean verify (String email, String pass)
    {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(utility.user_auth, new String[]{email, pass});

        if (cursor !=null)
        {
            if (cursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }


    //insert username and password
    public int insertvalue(utility myUtility)
    {
        SQLiteDatabase db = myHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(utility.KEY_EMAIL, myUtility.email_address);
        values.put(utility.KEY_PASSWORD, myUtility.pass_word);

        long insertValue = db.insert(utility.table_name, null, values);

        return (int) insertValue;

    }



}
