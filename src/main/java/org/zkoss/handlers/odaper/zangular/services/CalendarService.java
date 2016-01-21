package org.zkoss.handlers.odaper.zangular.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/**
 * Singleton Service class to manage the data in user's memory as JSON objects.
 * Just to test the interaction between angular and ZK VM when updating the
 * client model with angular
 * 
 * @author Odaper: Khaled Mathlouthi
 * @version 1.0
 * @category Service
 * **/
public final class CalendarService {

	private static final String USER_DATA = "userData";
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
			for (int i = 0; i < 5; i++) {
				eventList.add(createEvent("Event test:" + i, new Date(),
						new Date()));
			}
			sess.setAttribute(USER_DATA, eventList);
		}
		return eventList;
	}

	private Map<String, Object> createEvent(final String title,
			final Date startDate, final Date endDate) {
		Map<String, Object> obj1 = new LinkedHashMap<String, Object>();
		obj1.put("title", title);
		obj1.put("type", "warning");
		obj1.put("startsAt", startDate.getTime());
		obj1.put("endsAt", endDate.getTime());
		obj1.put("draggable", "true");
		obj1.put("editable", "false");
		obj1.put("deletable", "false");
		return obj1;
	}

	/**
	 * remove all the user's session data collection and create new one for the
	 * updated values from the angular model
	 * **/
	public void updateAll(final ArrayList<Map<String, Object>> eventList) {
		final Session sess = Sessions.getCurrent();
		sess.setAttribute(USER_DATA, eventList);
	}

}
