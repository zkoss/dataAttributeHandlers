package org.zkoss.handlers.odaper.zangular.vm;

import java.util.Date;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.NotifyCommand;
import org.zkoss.bind.annotation.NotifyCommands;
import org.zkoss.bind.annotation.ToClientCommand;
import org.zkoss.bind.annotation.ToServerCommand;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;

/**
 * ZK Calendar View Model which handles the events between the angular bootstrap
 * calendar and server ZK view model
 * 
 * @author Odaper: Khaled Mathlouthi
 * @version 1.0
 * @category ZK Namespace: Client Attribute
 * **/
@NotifyCommands({ @NotifyCommand(value = "mwlcalendar$initEvent", onChange = "_vm_.eventList"), })
@ToClientCommand({ "mwlcalendar$initEvent" })
@ToServerCommand({ "mwlcalendar$getEvents" })
public class CalendarVM {

	private String test = "This is just data inserted in a label.";
	private JSONArray eventList;

	@NotifyChange("eventList")
	@Command("mwlcalendar$getEvents")
	public void getEvents() {
		this.eventList = new JSONArray();
		eventList.add(getEvent("Event test 1", new Date(), new Date()));
		eventList.add(getEvent("Event test 2", new Date(), new Date()));
	}

	private JSONObject getEvent(final String title, final Date startDate,
			final Date endDate) {
		JSONObject obj1 = new JSONObject();
		obj1.put("title", title);
		obj1.put("type", "warning");
		obj1.put("startsAt", startDate.getTime());
		obj1.put("endsAt", endDate.getTime());
		obj1.put("draggable", "true");
		obj1.put("editable", "false");
		obj1.put("deletable", "false");
		return obj1;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public JSONArray getEventList() {
		return eventList;
	}

	public void setEventList(JSONArray eventList) {
		this.eventList = eventList;
	}

}
