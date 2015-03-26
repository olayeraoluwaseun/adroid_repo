package com.example.root.webo_cms;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by root on 2/4/15.
 */
public class page4 extends ActionBarActivity
{
    private TextView name;
    private TextView country;
    private TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page4);

        name = (TextView) findViewById(R.id.namevalue);
        country = (TextView) findViewById(R.id.countryvalue);
        email = (TextView) findViewById(R.id.emailvalue);


        name.setText(getIntent().getExtras().getString("name"));
        country.setText(getIntent().getExtras().getString("country"));
        email.setText(getIntent().getExtras().getString("email"));




    }
}
