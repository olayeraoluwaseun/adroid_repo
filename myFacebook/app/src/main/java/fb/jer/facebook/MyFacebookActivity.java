package fb.jer.facebook;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MyFacebookActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    
    ImageView ifb,icall,icontact;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ifb=(ImageView) findViewById(R.id.imgfb);
        icall=(ImageView)findViewById(R.id.imgcall);
        icontact=(ImageView)findViewById(R.id.contact);
        
        ifb.setOnClickListener(this);
        icall.setOnClickListener(this);
        icontact.setOnClickListener(this);
    }
       
        public void onClick(View v) {
        	Intent myIntent;
        	switch(v.getId())
        	{
        	case R.id.imgfb:
        		//Following Intent uses built-in task (ACTION_VIEW) to explore a web page
        		myIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.facebook.com"));
        		startActivity(myIntent);
        		break;
        	case R.id.imgcall:
        		//Following Intent uses built-in task (ACTION_VIEW) to make a phone call
        		myIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:09999909394"));
        		startActivity(myIntent);
        		break;
        	case R.id.contact:
        		//Following fragments calls an Intent whose job is to invoke a built_in 
        		//task(ACTION_VIEW) and explore the Contacts available in the phone.
        		myIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("content://contacts/people"));
        		startActivity(myIntent);
        		break;
		}
        	
        }
             
}