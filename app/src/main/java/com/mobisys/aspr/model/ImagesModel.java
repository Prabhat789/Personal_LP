package com.mobisys.aspr.model;

public class ImagesModel {
	
	String imagePath;
	int id;
	
	public ImagesModel() {
		// TODO Auto-generated constructor stub
	}
	
	public ImagesModel(String _imagePath){
		this.imagePath = _imagePath;
		
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
