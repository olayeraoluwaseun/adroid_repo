package com.kayode.sms;



import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuActivity extends Activity {
	
	
	
	private String DownloadText(String URL) {
		int BUFFER_SIZE = 2000;
		InputStream in = null;
		try {			
			in = OpenHttpGETConnection(URL);
			//in = OpenHttpPOSTConnection(URL);
		} catch (Exception e) {
			Toast.makeText(MenuActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			Log.d("Networking", e.getLocalizedMessage());
			return "";
		}

		InputStreamReader isr = new InputStreamReader(in);
		int charRead;
		String str = "";
		char[] inputBuffer = new char[BUFFER_SIZE];
		try {
			while ((charRead = isr.read(inputBuffer)) > 0) {
				// convert the chars to a String
				String readString = String
						.copyValueOf(inputBuffer, 0, charRead);
				str += readString;
				inputBuffer = new char[BUFFER_SIZE];
			}
			in.close();
		} catch (IOException e) {
			Toast.makeText(MenuActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			Log.d("Networking", e.getLocalizedMessage());
			return "";
		}
		return str;		
	}

	private class DownloadTextTask extends AsyncTask<String, Void, String> {
		private ProgressDialog dialo;
		protected void onPreExecute(){
			super.onPreExecute();
			dialo =new ProgressDialog(MenuActivity.this);
			dialo.setTitle("Load");
			dialo.setMessage("Loading");
			dialo.setIndeterminate(false);
			dialo.setCancelable(true);
			dialo.show();
		}
		protected String doInBackground(String... urls) {
			return DownloadText(urls[0]);
		}

		@Override
		protected void onPostExecute(String result) {
						dialo.dismiss();
			Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
			Log.d("DownloadTextTask", result);

			AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
			builder.setTitle("BALANCE");
			
			if (Integer.parseInt(result) == 2904){
				builder.setMessage("SMS Sending Failed");
			}
			else if(Integer.parseInt(result) == 2905){
				builder.setMessage("Invalid Username or Password");
			}
			
			else{
				builder.setMessage("You have " +result+ " left");
			}
				
			builder.setCancelable(false);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
			public void onClick(DialogInterface dialog, int which) {
					MenuActivity.this.finish();
					
				}
			}); 
			AlertDialog alert = builder.create();
				alert.show();
				
				
				
			
		}
	}

	public static InputStream OpenHttpGETConnection(String url) {
		InputStream inputStream = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			inputStream = httpResponse.getEntity().getContent();
		} catch (Exception e) {
			
			Log.d("", e.getLocalizedMessage());
		}
		return inputStream;
	}

	



	
	EditText user;
	EditText pass;
	EditText sender;
	Button proceed;
	Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
              
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);
        user = (EditText) findViewById(R.id.usereditText);
        pass = (EditText) findViewById(R.id.passeditText);
        
        proceed = (Button) findViewById(R.id.loginbutton);
        register = (Button) findViewById(R.id.registerbutton);
        
        SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
        user.setText(settings.getString("tvalue", ""));
    }


    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putString("tvalue", user.getText().toString());
    	editor.commit();
    }
    public void register(View view){
    	startActivity(new Intent(MenuActivity.this, RegisterActivity.class));
    	
    	
    	
    	
    }
    
    public void login(View view){
//    	String usernam = user.getText().toString();
//    	String passwor = pass.getText().toString();
//    	new DownloadTextTask()
//		.execute("http://www.doncitysms.com/components/com_spc/smsapi.php?username="+usernam+"&password="+passwor+"&balance=true&");
    	Intent intent = new Intent(MenuActivity.this, MessageActivity.class);
    	intent.putExtra("thekey1", user.getText().toString());
    	intent.putExtra("thekey2", pass.getText().toString());
    	startActivity(intent);
				
    	
    	
    		
	
    }
}