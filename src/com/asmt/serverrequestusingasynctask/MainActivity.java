package com.asmt.serverrequestusingasynctask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import com.example.serverrequestusingasynctask.R;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public ProgressBar progressBar;
	public Button btnGo;
	public ImageView image;
	public WebView webview;
	public MainActivity obj;
	public TextView txtUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		obj = this;
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		btnGo = (Button) findViewById(R.id.btnOk);
		image = (ImageView) findViewById(R.id.imagethumb);
		txtUpdate = (TextView) findViewById(R.id.txtUpdate);
		webview = (WebView)findViewById(R.id.webView);
		webview.setInitialScale(80);
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient());
		webview.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
		btnGo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new MyAsyncTask()
						.execute("http://sujanmaharjan.com.np/bpl/json.php?query=news&v=202");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class MyAsyncTask extends AsyncTask<String, String, String> {

		private String[] arrayTeamName;

		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressBar.setVisibility(View.VISIBLE);
			txtUpdate.setText("AsyncTask Started");
			
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(params[0]);

		    try {
		        publishProgress("AsyncTask running");
		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
		        
		        int status = response.getStatusLine().getStatusCode();
				if (status == 200) {
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);
					return data;
				}

		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    }			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
			if (result != null) {
				webview.loadUrl(result);
			}
			progressBar.setVisibility(View.GONE);
			txtUpdate.setText("AsyncTask Completed");
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			txtUpdate.setText(values[0]);
		}

	}
}
