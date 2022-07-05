package com.futureproducts.gbrself.reportrsmodel;

import com.google.gson.annotations.SerializedName;

public class ImgRecieved{

	@SerializedName("img_url")
	private ImgUrl imgUrl;

	public void setImgUrl(ImgUrl imgUrl){
		this.imgUrl = imgUrl;
	}

	public ImgUrl getImgUrl(){
		return imgUrl;
	}

	@Override
 	public String toString(){
		return 
			"ImgRecieved{" + 
			"img_url = '" + imgUrl + '\'' + 
			"}";
		}
}