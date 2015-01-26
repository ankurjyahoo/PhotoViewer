package com.yahoo.learn.android.igviewer;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IGPhotoAdapter extends ArrayAdapter<IGPhoto> {

	public IGPhotoAdapter(Context context,  List<IGPhoto> objects) {
		super(context, android.R.layout.simple_list_item_1, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Data
		IGPhoto photo = getItem(position);
		
		// Recycled View
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
		}
		
		// Populate fields
		TextView tvHeader = (TextView) convertView.findViewById(R.id.tvHeader);
		tvHeader.setText(Html.fromHtml(photo.getFormattedHeaderString()));

		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
		tvCaption.setText(photo.getCaption());

		
		ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
//		ivPhoto.getLayoutParams().height = photo.getImageHeight();
		
		// Clear photo
		ivPhoto.setImageResource(0);
		
		// External lib call to load the photo (and manage caching)
		Picasso.with(getContext()).load(photo.getImageURL()).into(ivPhoto);
		

		// Return 
		return convertView;
		
	}

	
}
