package org.zkoss.handlers.mthkhaled.zangular.components;

import org.zkoss.zul.Div;

public class ZkMwlCalendar extends Div {

	private static final String MWLCALENDAR = "data-mwlcalendar";
	private static final String CALENDAR_TITLE = "\"calendarTitle\":";
	private static final String CALENDAR_VIEW = "\"calendarView\":";
	private String calendarView = "year";
	private String calendarTitle = "This my ZK Wml calendar TITLE";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ZkMwlCalendar() {
		final StringBuilder str = new StringBuilder(CALENDAR_VIEW);
		str.append("\""+getCalendarView()+"\"");
		str.append(",");
		str.append(CALENDAR_TITLE);
		str.append("\""+getCalendarTitle()+"\"");
		setClientAttribute(MWLCALENDAR, "{" + str.toString() + "}");
	}

	public String getCalendarView() {
		return calendarView;
	}

	public void setCalendarView(String calendarView) {
		this.calendarView = calendarView;
	}

	public String getCalendarTitle() {
		return calendarTitle;
	}

	public void setCalendarTitle(String calendarTitle) {
		this.calendarTitle = calendarTitle;
	}

}
