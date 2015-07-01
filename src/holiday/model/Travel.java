package holiday.model;
/**
 * 
 */


import java.util.Date;

public class Travel {
 private int travelId;
 private int tripId;
 private User userName;
 private City cityName;
 private Hotel hotelId;
 private Date travelDate; 


/**
 * @param travelId
 * @param tripId
 * @param userName
 * @param cityName
 * @param hotelId
 * @param travelDate
 */
public Travel(int travelId, int tripId, User userName, City cityName,
		Hotel hotelId, Date travelDate) {
	super();
	this.travelId = travelId;
	this.tripId = tripId;
	this.userName = userName;
	this.cityName = cityName;
	this.hotelId = hotelId;
	this.travelDate = travelDate;
}

/**
 * Getters and Setters
 */
public int getTravelId() {
	return travelId;
}
public void setTravelId(int travelId) {
	this.travelId = travelId;
}
public int getTripId() {
	return tripId;
}
public void setTripId(int tripId) {
	this.tripId = tripId;
}
public User getUserName() {
	return userName;
}
public void setUserName(User userName) {
	this.userName = userName;
}
public City getCityName() {
	return cityName;
}
public void setCityName(City cityName) {
	this.cityName = cityName;
}
public Hotel getHotelId() {
	return hotelId;
}
public void setHotelId(Hotel hotelId) {
	this.hotelId = hotelId;
}
public Date getTravelDate() {
	return travelDate;
}
public void setTravelDate(Date travelDate) {
	this.travelDate = travelDate;
}
 
 
}
