package org.zkoss.keyfeature4;

import java.util.List;

import org.zkoss.zk.ui.Session;

public class RestaurantService {

	private static final String RESTAURANT_SERVICE = "org.zkoss.keyfeature4.RestaurantService";
	private RestaurantDAO dao;

	private RestaurantService() {
		dao = new RestaurantDAO();
	}

	public static RestaurantService getInstance(Session sess) {
		RestaurantService service = (RestaurantService) sess.getAttribute(RESTAURANT_SERVICE);
		if (service == null) {
			sess.setAttribute(RESTAURANT_SERVICE, service = new RestaurantService());
		}
		return service;
	}

	public List<String> getAllCities() {
		return dao.findAllCities();
	}

	public List<RestaurantPreview> countGroupByCity() {
		return dao.getCountGroupByCity();
	}

	public List<Restaurant> getRestaurantsByCity(String city) {
		return dao.findByCity(city);
	}

	public List<Restaurant> getRestaurants(int begin, int end) {
		return dao.findSortedBetween("city", end - begin, begin);
	}

	public List<Restaurant> getAllRestaurants() {
		return dao.findAllSorted("city");
	}

	public int getRestaurantsCount() {
		return dao.getRestaurantsCount();
	}

}
