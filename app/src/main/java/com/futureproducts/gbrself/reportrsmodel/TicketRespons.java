package com.futureproducts.gbrself.reportrsmodel;

import com.google.gson.annotations.SerializedName;

public class TicketRespons{

	@SerializedName("data_recieved")
	private DataRecieved dataRecieved;

	@SerializedName("img_recieved")
	private ImgRecieved imgRecieved;

	@SerializedName("error_code")
	private int errorCode;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setDataRecieved(DataRecieved dataRecieved){
		this.dataRecieved = dataRecieved;
	}

	public DataRecieved getDataRecieved(){
		return dataRecieved;
	}

	public void setImgRecieved(ImgRecieved imgRecieved){
		this.imgRecieved = imgRecieved;
	}

	public ImgRecieved getImgRecieved(){
		return imgRecieved;
	}

	public void setErrorCode(int errorCode){
		this.errorCode = errorCode;
	}

	public int getErrorCode(){
		return errorCode;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"TicketRespons{" + 
			"data_recieved = '" + dataRecieved + '\'' + 
			",img_recieved = '" + imgRecieved + '\'' + 
			",error_code = '" + errorCode + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}