package org.zkoss.handlers.odaper.zangular.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.zkoss.handlers.odaper.zangular.dao.CalendarDAO;

/**
 * Singleton Service class which interacts directly with the DAO layer to manage
 * the user's data. Business rules should be implemented here in this layer
 * 
 * @author Odaper: Khaled Mathlouthi
 * @version 1.0
 * @category Service layer
 * @licence MIT Licence
 * **/
public final class CalendarService {

	private static final String STARTS_AT = "startsAt";
	public static CalendarService instance;

	public static CalendarService get() {
		if (instance == null) {
			synchronized (CalendarService.class) {
				if (instance == null) {
					instance = new CalendarService();
				}
			}
		}
		return instance;
	}

	/**
	 * Get the list of user's events
	 * 
	 * @return events list
	 * **/
	public ArrayList<Map<String, Object>> getAllEvents() {
		return CalendarDAO.get().getAllEvents();
	}

	/**
	 * Get the count of events by months for the given year
	 * 
	 * @return events list
	 * **/
	public Map<Integer, Integer> getCountEventsByYear(int year) {
		final Map<Integer, Integer> eventsByMonth = getAllMonth();
		final ArrayList<Map<String, Object>> eventList = getAllEvents();
		for (Map<String, Object> event : eventList) {
			int eventMonth = 0;
			int eventYear = year;
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date parsedDate = dateFormat.parse(event.get(STARTS_AT)
						.toString());
				long timeStamp = parsedDate.getTime();
				eventMonth = getMonth(timeStamp);
				eventYear = getYear(timeStamp);
			} catch (Exception e) {
				eventMonth = getMonth(Long.parseLong(event.get(STARTS_AT)
						.toString()));
				eventYear = getYear(Long.parseLong(event.get(STARTS_AT)
						.toString()));
			}

			if (eventYear == year) {
				final int count = eventsByMonth.get(eventMonth);
				eventsByMonth.put(eventMonth, count + 1);
			}
		}
		return eventsByMonth;
	}

	/**
	 * Get the count of events by type
	 * 
	 * @return events list
	 * **/
	public Map<String, Integer> getCountEventsByType(int year) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("warning", 150);
		result.put("error", 50);
		result.put("info", 75);
		return result;

	}

	private Map<Integer, Integer> getAllMonth() {
		final Map<Integer, Integer> eventsByMonth = new LinkedHashMap<Integer, Integer>();
		eventsByMonth.put(Calendar.JANUARY, 0);
		eventsByMonth.put(Calendar.FEBRUARY, 0);
		eventsByMonth.put(Calendar.MARCH, 0);
		eventsByMonth.put(Calendar.APRIL, 0);
		eventsByMonth.put(Calendar.MAY, 0);
		eventsByMonth.put(Calendar.JUNE, 0);
		eventsByMonth.put(Calendar.JULY, 0);
		eventsByMonth.put(Calendar.AUGUST, 0);
		eventsByMonth.put(Calendar.SEPTEMBER, 0);
		eventsByMonth.put(Calendar.OCTOBER, 0);
		eventsByMonth.put(Calendar.NOVEMBER, 0);
		eventsByMonth.put(Calendar.DECEMBER, 0);
		return eventsByMonth;
	}

	/**
	 * update the list of user's events
	 * **/
	public void updateAll(final ArrayList<Map<String, Object>> eventList) {
		CalendarDAO.get().updateAll(eventList);
	}

	/***
	 * get the year from a timestamp value
	 * **/
	private int getYear(long timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		return cal.get(Calendar.YEAR);
	}

	/***
	 * get the month from a timestamp value
	 * **/
	private int getMonth(long timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		return cal.get(Calendar.MONTH);
	}
}
