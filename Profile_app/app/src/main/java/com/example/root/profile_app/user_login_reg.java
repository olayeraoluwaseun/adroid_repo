package com.example.root.profile_app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by root on 3/23/15.
 */
import  model.user_auth;
import  model.utility;
import utils.toaster;
import utils.shared_preference;

public class user_login_reg extends Activity
{
    SharedPreferences preferences;



    EditText email, pass;
    Button login, register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.user_details);




        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.phone);

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_auth auth = new user_auth(getApplicationContext());
                utility myUtility =  new utility();


                myUtility.email_address = email.getText().toString();
                myUtility.pass_word = pass.getText().toString();

                myUtility.username_id = auth.insertvalue(myUtility);

                toaster.show(user_login_reg.this, "saved");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_auth auth = new user_auth(getApplicationContext());
                utility myUtility =  new utility();

                if (auth.verify(email.getText().toString(), pass.getText().toString()))
                {
                    toaster.show(user_login_reg.this, "you re logged in");
                }

                else
                {
                    toaster.show(user_login_reg.this, "you re not logged in");
                }
            }

            });


        preferences = getSharedPreferences("storage" , 0);
        email.setText(preferences.getString("email", ""));
        pass.setText(preferences.getString("pass", ""));





    }

    @Override
    protected void onStop() {
        super.onStop();

        preferences = getSharedPreferences("storage", 0);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("email", email.getText().toString());
        editor.putString("pass", pass.getText().toString());

        editor.commit();



    }

}
