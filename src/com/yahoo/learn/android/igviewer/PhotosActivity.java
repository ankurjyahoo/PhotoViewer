package com.yahoo.learn.android.igviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class PhotosActivity extends Activity {

	private static final String CLIENT_ID = "8ee64988e8e842dbb798bc3be261ecb1";
	private ArrayList<IGPhoto> photos = new ArrayList<IGPhoto>();
	private IGPhotoAdapter adapterPhotos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);
		
		adapterPhotos = new IGPhotoAdapter(this, photos);
		ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
		lvPhotos.setAdapter(adapterPhotos);

//		photos.add(new IGPhoto("foo",  "some caption", "url", 5, (int) (100 * Math.random())));
//		adapterPhotos.notifyDataSetChanged();
		
		fetchPopularPhotos();
		
	}
	
	

	private void fetchPopularPhotos() {
		String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
		AsyncHttpClient client = new AsyncHttpClient();
		Log.i("INFO", "URL:" + url);
	
		client.get(url, new IGPhotosJsonHandler());
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photos, menu);
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


	private final class IGPhotosJsonHandler extends JsonHttpResponseHandler {
		// success and failure callbacks
		
		@Override
		public void onSuccess(int statusCode, Header[] headers,	JSONObject response) {
			// response is the good json object

			JSONArray photosJSON = null;
			
			try {
				photosJSON = response.getJSONArray("data");
//				Log.i("INFO", "Photo Count " + photosJSON.length());
				
				for (int i=0, len=photosJSON.length(); i<len; i++) {
					JSONObject jPhoto = photosJSON.getJSONObject(i);

					IGPhoto photo = new IGPhoto(jPhoto);
					PhotosActivity.this.photos.add(photo);
//					Log.i("INFO", photo.toString());
					
					
				}
				PhotosActivity.this.adapterPhotos.notifyDataSetChanged();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			// TODO Auto-generated method stub
			super.onFailure(statusCode, headers, responseString, throwable);
		}
	}


}
