package com.kayode.sms;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MessageActivity extends Activity {
	private static final int PICKFILE_RESULT_CODE = 1;
	
	private String DownloadText(String URL) {
		int BUFFER_SIZE = 2000;
		InputStream in = null;
		try {			
			in = OpenHttpGETConnection(URL);
			//in = OpenHttpPOSTConnection(URL);
		} catch (Exception e) {
			Toast.makeText(MessageActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
			Toast.makeText(MessageActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			Log.d("Networking", e.getLocalizedMessage());
			return "";
		}
		return str;		
	}

	private class DownloadTextTask extends AsyncTask<String, Void, String> {
		private ProgressDialog dialo;
		protected void onPreExecute(){
			super.onPreExecute();
			dialo =new ProgressDialog(MessageActivity.this);
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

			AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
			builder.setTitle("BALANCE");
			
			if (Integer.parseInt(result) == 2904){
				builder.setMessage("SMS Sending Failed");
			}
			else if(Integer.parseInt(result) == 2905){
				builder.setMessage("Invalid Username or Password");
			}
			else  if(result  == "OK"){
				builder.setMessage("Message Sent");
			}
			else{
				builder.setMessage("You have " +result+ " left");
			}
		//	iInteger.parseInt(result) == 2904){
			//	builder.setMessage ("SMS Sending Failed");
		//	}else {
						
			//	builder.setMessage("You have "+result+ " units left");
			//}
			builder.setCancelable(false);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
			public void onClick(DialogInterface dialog, int which) {
					MessageActivity.this.finish();
					
				}
			}); 
			AlertDialog alert = builder.create();
				alert.show();
				
				
				
			
		}
	}

	// ---Connects using HTTP GET---
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

	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.message);

		
					
					
				
			
	}
	public void file(View view){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("file/*");
		startActivityForResult(intent, PICKFILE_RESULT_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		TextView textFile = (TextView) findViewById(R.id.textView4);
		// TODO Auto-generated method stub
		switch(requestCode){
		case PICKFILE_RESULT_CODE:
			if(resultCode==RESULT_OK){
				String FilePath = data.getData().getPath();
				textFile.setText(FilePath);
			}
			break;
			
		}
	}
	public void phonebook(View view){
		startActivity(new Intent(MessageActivity.this, PhonebookActivity.class));
	}
	public void balance(View view){
		String usernam, passwor;
		
		usernam = getIntent().getExtras().getString("thekey1");	
		passwor = getIntent().getExtras().getString("thekey2");
		
		new DownloadTextTask()
		.execute("http://www.5252worldsms.com/components/com_spc/smsapi.php?username="+usernam+"&password="+passwor+"&balance=true&");
		
	}
	public void send(View view){
		EditText to;
		EditText message;
		EditText sender;
		to =(EditText) findViewById(R.id.toeditText);
		message = (EditText) findViewById(R.id.messageeditText);
		sender = (EditText) findViewById(R.id.editText1);
		String usernam, passwor,messag,senderid,recipient;
		usernam = getIntent().getExtras().getString("thekey1");
		passwor = getIntent().getExtras().getString("thekey2");
		messag = message.getText().toString();
		recipient = to.getText().toString();
		senderid = sender.getText().toString();
		new DownloadTextTask()
		.execute("http://www.5252worldsms.com/components/com_spc/smsapi.php?username="+usernam+"&password="+passwor+"&sender="+senderid+"&recipient="+recipient+"&message="+messag);
		
	}
	 
}