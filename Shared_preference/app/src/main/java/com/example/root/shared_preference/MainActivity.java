package com.example.root.shared_preference;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        text= (EditText) findViewById(R.id.sharedText);

        SharedPreferences preferences =getSharedPreferences("storage", 0);

        text.setText(preferences.getString("key", ""));
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences preferences = getSharedPreferences("storage", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("key", text.getText().toString());
        editor.commit();
    }
}
