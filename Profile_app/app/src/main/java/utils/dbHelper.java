package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.utility;


/**
 * Created by root on 3/19/15.
 */
public  class dbHelper extends SQLiteOpenHelper {


    public  dbHelper(Context context) {
        super(context,  utility.database , null, utility.db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //all tables are being created here
        db.execSQL(utility.query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //upgrade table
        db.execSQL("DROP TABLE IF EXITS" + utility.table_name);

        //re-create table
        onCreate(db);
    }
}
