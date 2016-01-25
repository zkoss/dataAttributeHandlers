package org.zkoss.handlers.odaper.zangular.services;

import java.util.ArrayList;
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
	 * update the list of user's events
	 * **/
	public void updateAll(final ArrayList<Map<String, Object>> eventList) {
		CalendarDAO.get().updateAll(eventList);
	}

}
