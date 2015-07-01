package holiday.model;

import java.util.Date;


public class Review {
	private int reviewId;
	private String review;
	private City cityName;
	private User userName;
	private Date date;
	
	
	
	public Review(int reviewId, String review, City cityName, User userName,
			Date date) {
		super();
		this.reviewId = reviewId;
		this.review = review;
		this.cityName = cityName;
		this.userName = userName;
		this.date = date;
	}

	public Review(String review, City cityName, User userName) {
		super();		
		this.review = review;
		this.cityName = cityName;
		this.userName = userName;	
	}
	
	

	public Review(int reviewId) {
		super();
		this.reviewId = reviewId;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public City getCityName() {
		return cityName;
	}

	public void setCityName(City cityName) {
		this.cityName = cityName;
	}

	public User getUserName() {
		return userName;
	}

	public void setUserName(User userName) {
		this.userName = userName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", review=" + review
				+ ", cityName=" + cityName + ", userName=" + userName
				+ ", date=" + date + "]";
	}	

}
