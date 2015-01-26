package com.yahoo.learn.android.igviewer;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateUtils;

public class IGPhoto {
	private String username;
	private String caption;
	private String imageURL;
	private int imageHeight;
	private int likesCount;
	private int commentCount;
	private long createdTimeMillis;
	
	
//	public IGPhoto(String username, String caption, String imageURL,
//			int imageHeight, int likesCount, int commentCount, int createdTime) {
//
//
//		this.username = username;
//		this.caption = caption;
//		this.imageURL = imageURL;
//		this.imageHeight = imageHeight;
//		this.likesCount = likesCount;
//		this.createdTimeMillis = (long) createdTime * (long) 1000;
//	}
	
	
	public IGPhoto(JSONObject jPhoto) throws JSONException
	{
		//	user -> username
		//	caption -> text
		//	impages -> standard_resolution -> url
		//	images -> standrd_res -> height
		//	likes -> count
		//  comments -> count
		//  created_time

		username = jPhoto.getJSONObject("user").getString("username");
		caption = jPhoto.isNull("caption") ? "" : jPhoto.getJSONObject("caption").getString("text");
		imageURL = jPhoto.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
		imageHeight = jPhoto.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
		likesCount = jPhoto.getJSONObject("likes").getInt("count");
		commentCount = jPhoto.getJSONObject("comments").getInt("count"); 
		createdTimeMillis = (long) jPhoto.getInt("created_time") * 1000l;
	
	}


//	public String getUsername() {
//		return username;
//	}


	public String getCaption() {
		return caption;
	}

	public String getImageURL() {
		return imageURL;
	}


//	public int getImageHeight() {
//		return imageHeight;
//	}
//
//
//	public int getLikesCount() {
//		return likesCount;
//	}

	private String getRelativeTimeSpan() {
		return (String) DateUtils.getRelativeTimeSpanString(createdTimeMillis, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
	}
	
	public String getFormattedHeaderString() {
		return "<b>" + username + "</b> - <i>" + getRelativeTimeSpan() + "<br>" + 
				likesCount + " likes - " + commentCount + " comments</i>";
				
	}

	public String getFormattedCommentString() {
		return "<b>" + username + "</b> - <i>" + getRelativeTimeSpan() + "<br>" + 
				likesCount + " likes - " + commentCount + " comments</i><br>" + 
				caption;
				
	}
	
	public String toString() {
		return username + ":" + caption + ":" + imageURL + ":" + imageHeight + ":" + likesCount;
	}
}

