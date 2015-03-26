package com.example.root.webo_cms;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity implements OnClickListener
{
    private TextView name;
    private TextView country;
    private TextView email;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        name = (TextView) findViewById(R.id.namevalue);


        email = (TextView) findViewById(R.id.emailvalue);

        country = (TextView) findViewById(R.id.country);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);




    }
    @Override
    public void onClick (View v)
    {
        if(v.getId() == R.id.submit)
        {
            Intent intent = new Intent(MainActivity.this, page2.class);
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("country", country.getText().toString());
            intent.putExtra("email", email.getText().toString());
            startActivity(intent);

        }

    }



}
