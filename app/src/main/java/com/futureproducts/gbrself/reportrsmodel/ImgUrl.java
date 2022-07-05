package com.futureproducts.gbrself.reportrsmodel;

import com.google.gson.annotations.SerializedName;

public class ImgUrl{

	@SerializedName("size")
	private int size;

	@SerializedName("name")
	private String name;

	@SerializedName("type")
	private String type;

	@SerializedName("error")
	private int error;

	@SerializedName("tmp_name")
	private String tmpName;

	public void setSize(int size){
		this.size = size;
	}

	public int getSize(){
		return size;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setError(int error){
		this.error = error;
	}

	public int getError(){
		return error;
	}

	public void setTmpName(String tmpName){
		this.tmpName = tmpName;
	}

	public String getTmpName(){
		return tmpName;
	}

	@Override
 	public String toString(){
		return 
			"ImgUrl{" + 
			"size = '" + size + '\'' + 
			",name = '" + name + '\'' + 
			",type = '" + type + '\'' + 
			",error = '" + error + '\'' + 
			",tmp_name = '" + tmpName + '\'' + 
			"}";
		}
}