package org.zkoss.handlers.odaper.zangular.vm;

import java.util.ArrayList;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.NotifyCommand;
import org.zkoss.bind.annotation.NotifyCommands;
import org.zkoss.bind.annotation.ToClientCommand;
import org.zkoss.bind.annotation.ToServerCommand;
import org.zkoss.handlers.odaper.zangular.services.CalendarService;

/**
 * ZK Calendar View Model which handles the events data changes between the
 * angular calendar model and ZK view model. All the events are stored in
 * the user's session
 * 
 * @author Odaper: Khaled Mathlouthi
 * @version 1.0
 * @category ZK Namespace: Client Attribute
 * @licence MIT Licence
 * **/
@NotifyCommands({ @NotifyCommand(value = "mwlcalendar$initEvent", onChange = "_vm_.eventList"), })
@ToClientCommand({ "mwlcalendar$initEvent" })
@ToServerCommand({ "mwlcalendar$getEvents", "mwlcalendar$updateEvents" })
public class CalendarVM {

	private ArrayList<Map<String, Object>> eventList;

	@NotifyChange("eventList")
	@Command("mwlcalendar$getEvents")
	public void getEvents() {
		this.eventList = CalendarService.get().getAllEvents();
	}

	@SuppressWarnings("unchecked")
	@Command("mwlcalendar$updateEvents")
	public void updateEvents(@BindingParam("events") Object events) {
		CalendarService.get()
				.updateAll((ArrayList<Map<String, Object>>) events);
	}

	public ArrayList<Map<String, Object>> getEventList() {
		return eventList;
	}

	public void setEventList(final ArrayList<Map<String, Object>> eventList) {
		this.eventList = eventList;
	}

}
