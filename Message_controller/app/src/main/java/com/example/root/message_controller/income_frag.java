package com.example.root.message_controller;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by root on 2/17/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class income_frag extends BroadcastReceiver
{


    @Override
    public void onReceive(Context context, Intent intent) {

        //get message passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] msg =  null;

        String str = null;

        if (bundle != null)
        {
            Object [] pdus = (Object[]) bundle.get("pdus");
            msg = new SmsMessage[pdus.length];

            for (int i=0; i<msg.length; i++)
            {
                msg[i] = SmsMessage.createFromPdu((byte[])pdus[i]);

                str += "message sent by" + msg[i].getOriginatingAddress();


            }

            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }

    }
}
