package unitClass;

public class Item {
	String iName;
	String image;
	String description;
	boolean isAvailable;
	
	public Item(String iName, String image, String description) {
		this(iName, image, description, true);
	}
	public Item(String iName, String image, String description,boolean isAvailable) {
		this.iName = iName;
		this.image = image;
		this.description = description;
		this.isAvailable = isAvailable;
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
	public String toString() {
		String str = "Item// Name : "+this.iName+", Description : "+this.description+", isAvailable : "+this.isAvailable;
		return str;
	}
}
