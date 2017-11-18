package unitClass;

public class Item {
	String iName;
	String image;
	String description;
	boolean isAvailable;
	
	public Item(String iName, String image, String description) {
		this.iName = iName;
		this.image = image;
		this.description = description;
		this.isAvailable = true;
	}
	
	public String getiName() {
		return iName;
	}
	public String getImage() {
		return image;
	}
	public String getDescription() {
		return description;
	}
	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}
