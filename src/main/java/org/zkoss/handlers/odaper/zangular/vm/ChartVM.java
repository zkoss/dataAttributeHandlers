package org.zkoss.handlers.odaper.zangular.vm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.NotifyCommand;
import org.zkoss.bind.annotation.NotifyCommands;
import org.zkoss.bind.annotation.ToClientCommand;
import org.zkoss.bind.annotation.ToServerCommand;
import org.zkoss.handlers.odaper.zangular.services.CalendarService;

/**
 * ZK Chart View Model which handles the data of the charts based on the user's
 * events managed by the first component "Angular Bootstrap Calendar"
 * 
 * @author Odaper: Khaled Mathlouthi
 * @version 1.0
 * @category ZK Namespace: Client Attribute
 * @licence MIT Licence
 * **/
@NotifyCommands({
		@NotifyCommand(value = "charts$initLineChart", onChange = "_vm_.lineChartData"),
		@NotifyCommand(value = "charts$initBarChart", onChange = "_vm_.barChartData"),
		@NotifyCommand(value = "charts$initPieChart", onChange = "_vm_.pieChartData") })
@ToClientCommand({ "charts$initLineChart", "charts$initBarChart",
		"charts$initPieChart" })
@ToServerCommand({ "charts$getLineBarData", "charts$getBarData",
		"charts$getPieData" })
public class ChartVM {

	private List<Collection<Integer>> lineChartData;
	private List<Collection<Integer>> barChartData;
	private List<Integer> pieChartData;

	/**
	 * get the count of events by month for the current year
	 * 
	 * **/
	@NotifyChange("lineChartData")
	@Command("charts$getLineBarData")
	public void getLineBarData() {
		this.lineChartData = new ArrayList<Collection<Integer>>();
		lineChartData.add(CalendarService.get().getCountEventsByYear(2016)
				.values());
	}

	@NotifyChange("barChartData")
	@Command("charts$getBarData")
	public void getBarData() {
		this.barChartData = new ArrayList<Collection<Integer>>();
		barChartData.add(CalendarService.get().getCountEventsByYear(2017)
				.values());
	}

	@NotifyChange("pieChartData")
	@Command("charts$getPieData")
	public void getPieData() {
		this.pieChartData = new ArrayList<Integer>();
		pieChartData.addAll(CalendarService.get().getCountEventsByType(2017)
				.values());
	}

	public List<Collection<Integer>> getLineChartData() {
		return lineChartData;
	}

	public void setLineChartData(List<Collection<Integer>> lineChartData) {
		this.lineChartData = lineChartData;
	}

	public List<Collection<Integer>> getBarChartData() {
		return barChartData;
	}

	public void setBarChartData(List<Collection<Integer>> barChartData) {
		this.barChartData = barChartData;
	}

	public List<Integer> getPieChartData() {
		return pieChartData;
	}

	public void setPieChartData(List<Integer> pieChartData) {
		this.pieChartData = pieChartData;
	}

}
