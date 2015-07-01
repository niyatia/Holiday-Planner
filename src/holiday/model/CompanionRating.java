package holiday.model;

import holiday.model.Enumerations.Companion;


public class CompanionRating {
	private int companionRatingId;
	private double rating;
	private Enumerations.Companion companion;
	private User user;
	private City city;
	
	public CompanionRating(int companionRatingId, double rating,
			Companion companion, User user, City city) {
		super();
		this.companionRatingId = companionRatingId;
		this.rating = rating;
		this.companion = companion;
		this.user = user;
		this.city = city;
	}
	
	public CompanionRating(double rating, Companion companion, User user,
			City city) {
		super();
		this.rating = rating;
		this.companion = companion;
		this.user = user;
		this.city = city;
	}

	public int getCompanionRatingId() {
		return companionRatingId;
	}

	public void setCompanionRatingId(int companionRatingId) {
		this.companionRatingId = companionRatingId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Enumerations.Companion getCompanion() {
		return companion;
	}

	public void setCompanion(Enumerations.Companion companion) {
		this.companion = companion;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}	

}
