package com.futureproducts.gbrself.reportrsmodel;

import com.google.gson.annotations.SerializedName;

public class DataRecieved{

	@SerializedName("user_mobile")
	private String userMobile;

	@SerializedName("heading")
	private String heading;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("description")
	private String description;

	@SerializedName("dse_id")
	private String dseId;

	public void setUserMobile(String userMobile){
		this.userMobile = userMobile;
	}

	public String getUserMobile(){
		return userMobile;
	}

	public void setHeading(String heading){
		this.heading = heading;
	}

	public String getHeading(){
		return heading;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setDseId(String dseId){
		this.dseId = dseId;
	}

	public String getDseId(){
		return dseId;
	}

	@Override
 	public String toString(){
		return 
			"DataRecieved{" + 
			"user_mobile = '" + userMobile + '\'' + 
			",heading = '" + heading + '\'' + 
			",user_name = '" + userName + '\'' + 
			",description = '" + description + '\'' + 
			",dse_id = '" + dseId + '\'' + 
			"}";
		}
}