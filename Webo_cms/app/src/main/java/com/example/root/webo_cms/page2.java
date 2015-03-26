package com.example.root.webo_cms;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * Created by root on 2/4/15.
 */
public class page2 extends ActionBarActivity implements OnClickListener
{
    private TextView name;
    private TextView country;
    private TextView email;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page2);

        name = (TextView) findViewById(R.id.namevalue);


        email = (TextView) findViewById(R.id.emailvalue);

        country = (TextView) findViewById(R.id.countryvalue);

        name.setText(getIntent().getExtras().getString("name"));
        email.setText(getIntent().getExtras().getString("email"));
        country.setText(getIntent().getExtras().getString("country"));

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);

    }
    @Override
    public void onClick (View view)
    {
        if(view.getId() == R.id.next)
        {
            Intent intent = new Intent(page2.this, page3.class);
            startActivity(intent);
        }

    }
}
