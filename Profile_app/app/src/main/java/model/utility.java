package model;

/**
 * Created by root on 3/19/15.
 */
public class utility
{
    //db version
    public static final int db_version = 4;


    //db name
    public static final String database = "profile.db";

    //table names
    public static final String table_name = "contact";
    

    //create sql table
    public static final String query = "CREATE TABLE " + utility.table_name +
                                         "(" + utility.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                                            utility.KEY_USER + " TEXT, "+
                                            utility.KEY_EMAIL + " TEXT, " +
                                            utility.KEY_PASSWORD + " TEXT " +
                                          ")";
    //contact details


    //column fields

    public static final String KEY_ID = "user_id";
//    public static final String KEY_USER = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";



    //value
    public int username_id;
    public String user_name;
    public String email_address;
    public String pass_word;


    public static final String user_auth = "SELECT " + utility.KEY_EMAIL + ", "+
                                            utility.KEY_PASSWORD + " FROM " +
                                            utility.table_name + " WHERE " +
                                            utility.KEY_PASSWORD + " =? AND " +
                                            utility.KEY_EMAIL + " =?";

}
