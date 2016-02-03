package org.zkoss.handlers.odaper.zangular.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/**
 * Singleton DAO class to manage the data in the user's session (the user
 * session is used as the storage system just for test, it should be changed by
 * either RDBMS or NOSQL database ...)
 * 
 * @author Odaper: Khaled Mathlouthi
 * @version 1.0
 * @category Data Access Object layer
 * @licence MIT Licence
 * **/
public final class CalendarDAO {

	private static final String USER_DATA = "userData";
	public static CalendarDAO instance;

	public static CalendarDAO get() {
		if (instance == null) {
			synchronized (CalendarDAO.class) {
				if (instance == null) {
					instance = new CalendarDAO();
				}
			}
		}
		return instance;
	}

	/**
	 * Create sample events and store them in the user's session
	 * 
	 * @return events list
	 * **/
	@SuppressWarnings("unchecked")
	public ArrayList<Map<String, Object>> getAllEvents() {
		ArrayList<Map<String, Object>> eventList = null;
		final Session sess = Sessions.getCurrent();
		// get the list of data from user's session
		if (sess.hasAttribute(USER_DATA)) {
			eventList = (ArrayList<Map<String, Object>>) sess
					.getAttribute(USER_DATA);

		} else {
			// create sample events
			eventList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < 10; i++) {
				eventList.add(createEvent("Event test:" + i, new Date(),
						new Date()));
			}
			sess.setAttribute(USER_DATA, eventList);
		}
		return eventList;
	}

	/**
	 * remove all the user's session data collection and create new one for the
	 * updated values from the angular model
	 * **/
	public void updateAll(final ArrayList<Map<String, Object>> eventList) {
		final Session sess = Sessions.getCurrent();
		sess.setAttribute(USER_DATA, eventList);
	}

	/**
	 * Create a sample event by the given parameters
	 * **/
	private Map<String, Object> createEvent(final String title,
			final Date startDate, final Date endDate) {
		final long randomDate = getRandomDate();
		
		final Map<String, Object> obj1 = new LinkedHashMap<String, Object>();
		obj1.put("title", title);
		obj1.put("type", "warning");
		obj1.put("startsAt", randomDate);
		obj1.put("endsAt", randomDate);
		obj1.put("draggable", "true");
		obj1.put("editable", "false");
		obj1.put("deletable", "false");
		return obj1;
	}
	
	/**
	 * Generate a random date between the current year and the after next one
	 * **/
	private long getRandomDate() {
		long result = 0;
		try {
			final DateFormat dateFormat = new SimpleDateFormat("yyyy");
			final int year = Calendar.getInstance().get(Calendar.YEAR);
			final Date dateFrom = dateFormat.parse(String.valueOf(year));
			final long timestampFrom = dateFrom.getTime();
			final Date dateTo = dateFormat.parse(String.valueOf(year + 2));
			final long timestampTo = dateTo.getTime();
			final Random random = new Random();
			final long timeRange = timestampTo - timestampFrom;
			result = timestampFrom + (long) (random.nextDouble() * timeRange);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

}
