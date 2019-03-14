package com.example.forev.vetappadmin.Models;

public class CampaignModel{
	private String image;
	private boolean tf;
	private String id;
	private String text;
	private String title;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"CampaignModel{" + 
			"image = '" + image + '\'' + 
			",tf = '" + tf + '\'' + 
			",id = '" + id + '\'' + 
			",text = '" + text + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}
