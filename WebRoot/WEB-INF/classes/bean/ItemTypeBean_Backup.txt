package bean;

import java.util.ArrayList;

public class ItemTypeBean {
	String goodsName;
	String categoryName;
	String brandName;
	ArrayList<String> storageList = new ArrayList<String>();
	ArrayList<String> colorList = new ArrayList<String>();
	ArrayList<String> screenList = new ArrayList<String>();
	ArrayList<String> imgList = new ArrayList<String>();
	int stock;
	int goodsID;
	
	public int getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(int goodsID) {
		this.goodsID = goodsID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getBrandName() {
		return brandName;
	}
	public ArrayList<String> getImgList() {
		return imgList;
	}
	public void setImgList(ArrayList<String> imgList) {
		this.imgList = imgList;
	}
	public ArrayList<String> getStorageList() {
		return storageList;
	}
	public void setStorageList(ArrayList<String> storageList) {
		this.storageList = storageList;
	}
	public ArrayList<String> getColorList() {
		return colorList;
	}
	public void setColorList(ArrayList<String> colorList) {
		this.colorList = colorList;
	}
	public ArrayList<String> getScreenList() {
		return screenList;
	}
	public void setScreenList(ArrayList<String> screenList) {
		this.screenList = screenList;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
