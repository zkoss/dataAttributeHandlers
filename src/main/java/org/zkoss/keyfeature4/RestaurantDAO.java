package org.zkoss.keyfeature4;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {

	private DBConnection dbconn = new DBConnection();
	private static final String TABLE = "example-restaurants-data";

	public List<Restaurant> findSortedBetween(String column, int limit, int offset) {
		List<Restaurant> results = null;
		try {
			String sql = "SELECT * FROM " + TABLE + " ORDER BY " + column + " ASC LIMIT " + limit + " OFFSET " + offset;
			PreparedStatement pstmt = dbconn.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			results = new ArrayList<Restaurant>();
			while (rs.next()) {
				results.add(mapping(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbconn.closeConnection();
		}
		return results;
	}

	public List<Restaurant> findByCity(String city) {
		List<Restaurant> results = null;
		try {
			String sql = "SELECT * FROM " + TABLE + " WHERE city = '" + city + "' ORDER BY city ASC";
			PreparedStatement pstmt = dbconn.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			results = new ArrayList<Restaurant>();
			while (rs.next()) {
				results.add(mapping(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbconn.closeConnection();
		}
		return results;
	}

	public List<String> findAllCities() {
		List<String> results = null;
		try {
			String sql = "SELECT DISTINCT city FROM " + TABLE + " ORDER BY city";
			PreparedStatement pstmt = dbconn.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			results = new ArrayList<String>();
			while (rs.next()) {
				results.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbconn.closeConnection();
		}
		return results;
	}

	public List<Restaurant> findAllSorted(String column) {
		List<Restaurant> results = null;
		try {
			String sql = "SELECT * FROM " + TABLE + " ORDER BY " + column + " ASC";
			PreparedStatement pstmt = dbconn.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			results = new ArrayList<Restaurant>();
			while (rs.next()) {
				results.add(mapping(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbconn.closeConnection();
		}
		return results;
	}

	public List<Restaurant> findAll() {
		List<Restaurant> results = null;
		try {
			String sql = "SELECT * FROM " + TABLE;
			PreparedStatement pstmt = dbconn.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			results = new ArrayList<Restaurant>();
			while (rs.next()) {
				results.add(mapping(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbconn.closeConnection();
		}
		return results;
	}

	public List<RestaurantPreview> getCountGroupByCity() {
		List<RestaurantPreview> results = null;
		try {
			String sql = "SELECT city, COUNT(*) AS count FROM " + TABLE + " GROUP BY city ORDER BY city ASC";
			PreparedStatement pstmt = dbconn.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			results = new ArrayList<RestaurantPreview>();
			while (rs.next()) {
				String cityName = rs.getString("city");
				RestaurantPreview p = new RestaurantPreview();
				p.setCityInitial(cityName.substring(0, 1));
				p.setNumberOfCity(rs.getInt("count"));
				p.setCityName(cityName);
				results.add(p);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbconn.closeConnection();
		}
		return results;
	}

	public int getRestaurantsCount() {
		int count = 0;
		try {
			String sql = "SELECT COUNT(*) AS count FROM " + TABLE;
			PreparedStatement pstmt = dbconn.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbconn.closeConnection();
		}
		return count;
	}

	private Restaurant mapping(ResultSet rs) {
		Restaurant r = new Restaurant();
		try {
			r.setName(rs.getString("name"));
			r.setType(rs.getString("type_of_food").toLowerCase());
			r.setAddress(rs.getString("address"));
			r.setCity(rs.getString("city"));
			r.setOutcode(rs.getString("outcode"));
			r.setPostcode(rs.getString("postcode"));
			r.setUrl(rs.getString("URL"));
			r.setRating(rs.getInt("rating"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

}
