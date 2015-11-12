package org.zkoss.keyfeature1;

public class User {
	public String username;
	public String picture;
	
	public String getUsername() {
		return username;
	}
	public String getPicture() {
		return picture;
	}
	public User(String username, String picture) {
		super();
		this.username = username;
		this.picture = picture;
	}

}
