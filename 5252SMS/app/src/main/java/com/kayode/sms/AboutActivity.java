package com.kayode.sms;



import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		InputStream ifile = getResources().openRawResource(R.raw.eboladef);
		try{
			TextView tx = (TextView) findViewById(R.id.textView1);
			String ne = InputStreamToString(ifile);
			tx.setText(ne);
		}catch(Exception e){
			Toast.makeText(AboutActivity.this, "unable to read file", Toast.LENGTH_SHORT).show();
		}
}

	private String InputStreamToString(InputStream ifil) throws IOException {
		StringBuffer sbuffer = new StringBuffer();
		DataInputStream dataio = new DataInputStream(ifil);
		String fil = null;
		
		while((dataio.readLine()) != null){
			sbuffer.append(fil + "\n");
		}
		dataio.close();
		ifil.close();
		// TODO Auto-generated method stub
		return sbuffer.toString();
	}
}