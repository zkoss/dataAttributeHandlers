package org.zkoss.handlers.odaper.zangular.ui;

import org.zkoss.zul.Div;

/**
 * Custom ZK Div Component to initialize the angular calendar from server side.
 * Few parameters are handled here just for test (view type of the calendar),
 * full parameters will be supported soon when this project will be published
 * for the ZK Community in GitHub
 * 
 * @author Odaper: Khaled Mathlouthi
 * @version 1.0
 * @category ZK Namespace: Client Attribute
 * @licence MIT Licence
 * **/
public class CalendarUI extends Div {

	private static final String MWLCALENDAR = "data-mwlcalendar";
	private static final String CALENDAR_VIEW = "\"calendarView\":";
	private String calendarView = "year";
	private static final long serialVersionUID = 1L;

	public CalendarUI() {
		final StringBuilder str = new StringBuilder(CALENDAR_VIEW);
		str.append("\"" + getCalendarView() + "\"");
		setClientAttribute(MWLCALENDAR, "{" + str.toString() + "}");
	}

	public String getCalendarView() {
		return calendarView;
	}

	public void setCalendarView(String calendarView) {
		this.calendarView = calendarView;
	}

}
