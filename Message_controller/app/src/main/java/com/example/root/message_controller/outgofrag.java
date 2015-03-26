package com.example.root.message_controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by root on 2/17/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class outgofrag extends ActionBarActivity implements View.OnClickListener
{
    private Button outgo;
    private EditText number;
    private EditText text;
    private View view;
    private Button speak;
    private final int SPEECH = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outgo_frag);

        number = (EditText)  findViewById(R.id.number);
        text = (EditText) findViewById(R.id.message);

        speak = (Button) findViewById(R.id.speak);

        outgo = (Button) findViewById(R.id.outgo_send);

        outgo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String strnum = number.getText().toString();
        String strText = text.getText().toString();

        if(v.getId() == R.id.outgo_send)
        {
            sendmsg (strnum, strText);
        }


        if(v.getId() == R.id.speak)
        {
            mySpech();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void mySpech()
    {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");

        try
        {
            startActivityForResult(intent, SPEECH);

        }

        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text.setText(result.get(0));
                }
                break;
            }
        }
    }


    protected void sendmsg(String number, String text){

        String send = "Message sent";
        String deliver = "Message delivered";
        String [] arrayNum = text.split("");

        /**
         * A PendingIntent specifies an action to take in the future.
         * It lets you pass a future Intent to another application and allow that application
         * to execute that Intent as if it had the same permissions as your application, whether
         * or not your application is still around when the Intent is eventually invoked.It is a
         * token that you give to a foreign application which allows the foreign application to use
         * your application’s permissions to execute a predefined piece of code.
         */

        PendingIntent sendIntent = PendingIntent.getBroadcast(this, 0, new Intent(send), 0);
        PendingIntent deliverIntent = PendingIntent.getBroadcast(this, 0, new Intent(deliver), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "No Service", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic Failure", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        }, new IntentFilter(send));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode())
                {
                    case Activity.RESULT_OK :
                        Toast.makeText(context, "Message Delivered", Toast.LENGTH_SHORT).show();
                        break;

                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "Result cancelled", Toast.LENGTH_SHORT).show();
                        break;
                }


            }
        }, new IntentFilter(deliver));







        //import the sms class
        SmsManager sms = SmsManager.getDefault();
        //destionation, scAddres, , text, sentIntent, deliveryIntent

        sms.sendTextMessage(number, null, text, sendIntent, deliverIntent);
    }
}
