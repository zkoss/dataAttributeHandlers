package org.zkoss.keyfeature4;

public class Restaurant {

	private String _name;
	private String _type;
	private String _address;
	private String _city;
	private String _outcode;
	private String _postcode;
	private String _url;
	private int _rating;

	public Restaurant() {
		super();
	}

	public Restaurant(String name, String type, String address, String city, String outcode, String postcode,
			String url, int rating) {
		super();
		_name = name;
		_type = type;
		_address = address;
		_city = city;
		_outcode = outcode;
		_postcode = postcode;
		_url = url;
		_rating = rating;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getAddress() {
		return _address;
	}

	public void setAddress(String address) {
		_address = address;
	}

	public String getCity() {
		return _city;
	}

	public void setCity(String city) {
		_city = city;
	}

	public String getOutcode() {
		return _outcode;
	}

	public void setOutcode(String outcode) {
		_outcode = outcode;
	}

	public String getPostcode() {
		return _postcode;
	}

	public void setPostcode(String postcode) {
		_postcode = postcode;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public int getRating() {
		return _rating;
	}

	public void setRating(int rating) {
		_rating = rating;
	}

	@Override
	public String toString() {
		return "Restaurant [_name=" + _name + ", _type=" + _type + ", _address=" + _address + ", _address2=" + _city
				+ ", _outcode=" + _outcode + ", _postcode=" + _postcode + ", _url=" + _url + ", _rating=" + _rating
				+ "]";
	}

}
