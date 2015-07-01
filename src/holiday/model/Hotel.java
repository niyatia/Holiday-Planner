package holiday.model;

import java.util.Date;

public class Hotel {

	private int hotelId;
	private String hotelName;
	private String address;
	private String zipCode;
	private String phone;
	private String website;
	private City city;
	private Admin admin;
	private Date timestamp;

	public Hotel(int hotelId, String hotelName, String address, String zipCode,
			String phone, String website, City city, Admin admin, Date timestamp) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.address = address;
		this.zipCode = zipCode;
		this.phone = phone;
		this.website = website;
		this.city = city;
		this.admin = admin;
		this.timestamp = timestamp;
	}

	public Hotel(String hotelName, String address, String zipCode,
			String phone, String website, City city, Admin admin) {
		super();
		this.hotelName = hotelName;
		this.address = address;
		this.zipCode = zipCode;
		this.phone = phone;
		this.website = website;
		this.city = city;
		this.admin = admin;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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
