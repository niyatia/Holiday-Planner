package holiday.model;

import java.util.Date;

public class Place {
	private int placeId;
	private String placeName;
	private String address;
	private String zipCode;
	private String description;
	private String photo;
	private City city;
	private Admin admin;
	private Date timestamp;

	public Place(int placeId, String placeName, String address, String zipCode,
			String description, String photo, City city, Admin admin,
			Date timestamp) {
		super();
		this.placeId = placeId;
		this.placeName = placeName;
		this.address = address;
		this.zipCode = zipCode;
		this.description = description;
		this.photo = photo;
		this.city = city;
		this.admin = admin;
		this.timestamp = timestamp;
	}

	public Place(String placeName, String address, String zipCode,
			String description, String photo, City city, Admin admin) {
		super();
		this.placeName = placeName;
		this.address = address;
		this.zipCode = zipCode;
		this.description = description;
		this.photo = photo;
		this.city = city;
		this.admin = admin;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
