package com.example.root.webo_cms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

/**
 * Created by root on 2/4/15.
 */
public class page3 extends ActionBarActivity implements OnClickListener
{
    private EditText name;
    private EditText country;
    private EditText email;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);

        name = (EditText) findViewById(R.id.namevalue);
        country= (EditText) findViewById(R.id.countryvalue);
        email = (EditText) findViewById(R.id.emailvalue);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);


    }

    @Override
    public void onClick (View view)
    {
        if (view.getId() == R.id.submit)
        {
            Intent intent = new Intent(page3.this, page4.class);
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("email", email.getText().toString());
            intent.putExtra("country", country.getText().toString());
            startActivity(intent);
        }

    }
}
