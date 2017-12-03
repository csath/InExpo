package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Stalls implements Serializable{
	
	private int stallId;
	private String stallName;
    private String url;
    private String category;
    private String desctiption;
    private double rating;    
    private String imageCover;
    private String imageProfile;
    private String userFeedback;   
    private double userRating;
    private String tags;
    private String stallEmail;
    private String stallContactNo;
    private int reviewCount;
    
    
    
	public double getUserRating() {
		return userRating;
	}

	public void setUserRating(double userRating) {
		this.userRating = userRating;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getStallEmail() {
		return stallEmail;
	}

	public void setStallEmail(String stallEmail) {
		this.stallEmail = stallEmail;
	}

	public String getStallContactNo() {
		return stallContactNo;
	}

	public void setStallContactNo(String stallContactNo) {
		this.stallContactNo = stallContactNo;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public String getUserFeedback() {
		return userFeedback;
	}

	public void setUserFeedback(String userFeedback) {
		
			this.userFeedback = userFeedback;
		
	}

	public double getUserRationg() {
		return userRating;
	}

	public void setUserRationg(double userRationg) {
		this.userRating = userRationg;
	}

	public Stalls() {
    }
 
    public Stalls(String name, String thumbnailUrl, String year, double rating,
            ArrayList<String> genre) {
        this.stallName = name;
        this.url = thumbnailUrl;
        this.category = year;
        this.rating = rating;
       
    }
 
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImageCover() {
		return imageCover;
	}

	public void setImageCover(String imageCover) {
		this.imageCover = imageCover;
	}

	public String getImageProfile() {
		return imageProfile;
	}

	public void setImageProfile(String imageProfile) {
		this.imageProfile = imageProfile;
	}
 
  	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public int getStallId() {
		return stallId;
	}

	public void setStallId(int stallId) {
		this.stallId = stallId;
	}

	public String getDesctiption() {
		return desctiption;
	}

	public void setDesctiption(String desctiption) {
		this.desctiption = desctiption;
	}
  
    public double getRating() {
        return rating;
    }
 
    public void setRating(double rating) {
        this.rating = rating;
    }
 
   



	

	
 
}