package com.kayode.sms;



import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RegisterActivity extends Activity {
	
	public class RegisterTask extends AsyncTask<String, Void, String>{
		private ProgressDialog regdialog;
		protected void onPreExecute(){
			super.onPreExecute();
			regdialog = new ProgressDialog(RegisterActivity.this);
			regdialog.setTitle("Registration Page Check");
			regdialog.setMessage("Loading Registration Page..");
			regdialog.setIndeterminate(false);
			regdialog.setCancelable(true);
			regdialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			WebView webview = (WebView) findViewById(R.id.webView1);
			webview.setWebViewClient(new Callback());
			WebSettings websettings = webview.getSettings();
			websettings.setBuiltInZoomControls(true);
			webview.loadUrl(params[0]);
			return null;
		}
		protected void onPostExecute(String result){
			regdialog.dismiss();
			
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		
		new RegisterTask().execute("http://www.5252worldsms.com/index.php/signup");
	}
	private class Callback extends WebViewClient {
		
		public boolean shouldOvverideUrlLoading(WebView view, String url){
			return (false);
		}
	}
}
