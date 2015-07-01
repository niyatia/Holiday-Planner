package holiday.model;

import holiday.model.Enumerations.Interest;

public class InterestRating {
	private int interestRatingId;
	private double rating;
	private Enumerations.Interest interest;
	private User user;
	private City city;

	public InterestRating(int interestRatingId, double rating,
			Interest interest, User user, City city) {
		super();
		this.interestRatingId = interestRatingId;
		this.rating = rating;
		this.interest = interest;
		this.user = user;
		this.city = city;
	}
	
	

	public InterestRating(double rating, Interest interest, User user, City city) {
		super();
		this.rating = rating;
		this.interest = interest;
		this.user = user;
		this.city = city;
	}



	public int getInterestRatingId() {
		return interestRatingId;
	}

	public void setInterestRatingId(int interestRatingId) {
		this.interestRatingId = interestRatingId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Enumerations.Interest getInterest() {
		return interest;
	}

	public void setInterest(Enumerations.Interest interest) {
		this.interest = interest;
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
