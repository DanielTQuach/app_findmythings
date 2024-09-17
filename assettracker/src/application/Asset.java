package application;

public class Asset implements AssetInterface {
	// This is for java beans
	private int assetID;
	private String assetName;
	private String assetCategory;
	private String assetLocation;
	private String expirationDate;
	private String purchaseDate;
	private String purchaseValue;
	private String assetDescription;
	public Asset(String name, String cat, String loc, 
			String expDate, String purDate, String purVal, String desc) {
		//assetID = ID;
		assetName = name;
		assetCategory = cat;
		assetLocation = loc;
		expirationDate = expDate;
		purchaseDate = purDate;
		purchaseValue = purVal;
		assetDescription = desc;
	}
	
	public Asset(int ID, String name, String cat, String loc, 
			String expDate, String purDate, String purVal, String desc) {
		assetID = ID;
		assetName = name;
		assetCategory = cat;
		assetLocation = loc;
		expirationDate = expDate;
		purchaseDate = purDate;
		purchaseValue = purVal;
		assetDescription = desc;
	}
	@Override
	public int getAssetID() {
		return assetID;
	}
	@Override
	public String getAssetName() {
		// TODO Auto-generated method stub
		return assetName;
	}
	@Override
	public String getAssetCategory() {
		// TODO Auto-generated method stub
		return assetCategory;
	}
	@Override
	public String getAssetLocation() {
		// TODO Auto-generated method stub
		return assetLocation;
	}
	@Override
	public String getAssetPurDate() {
		// TODO Auto-generated method stub
		return purchaseDate;
	}
	@Override
	public String getAssetExpDate() {
		// TODO Auto-generated method stub
		return expirationDate;
	}
	@Override
	public String getAssetValue() {
		// TODO Auto-generated method stub
		return purchaseValue;
	}
	@Override
	public String getAssetDesc() {
		// TODO Auto-generated method stub
		return assetDescription;
	}
}
