package org.zkoss.handlers.odaper.zangular.vm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.NotifyCommand;
import org.zkoss.bind.annotation.NotifyCommands;
import org.zkoss.bind.annotation.ToClientCommand;
import org.zkoss.bind.annotation.ToServerCommand;

/**
 * ZK Chart View Model which initializes the charts for stats for the user's
 * events managed by the first component "Angular Calendar"
 * 
 * @author Odaper: Khaled Mathlouthi
 * @version 1.0
 * @category ZK Namespace: Client Attribute
 * **/
@NotifyCommands({
		@NotifyCommand(value = "charts$initLineChart", onChange = "_vm_.lineChartData"),
		@NotifyCommand(value = "charts$initBarChart", onChange = "_vm_.barChartData") })
@ToClientCommand({ "charts$initLineChart", "charts$initBarChart" })
@ToServerCommand({ "charts$getLineBarData", "charts$getBarData" })
public class ChartVM {

	private ArrayList<ArrayList<Integer>> lineChartData;
	private ArrayList<ArrayList<Integer>> barChartData;

	@NotifyChange("lineChartData")
	@Command("charts$getLineBarData")
	public void getLineBarData() {
		this.lineChartData = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> test = new ArrayList<Integer>();
		test.add(20);
		test.add(50);
		test.add(100);
		test.add(150);
		lineChartData.add(test);
	}

	@NotifyChange("barChartData")
	@Command("charts$getBarData")
	public void getBarData() {
		this.barChartData = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> test = new ArrayList<Integer>();
		test.add(20);
		test.add(50);
		test.add(100);
		test.add(150);
		barChartData.add(test);
		
		
		
		
	}

	public ArrayList<ArrayList<Integer>> getLineChartData() {
		return lineChartData;
	}

	public void setLineChartData(ArrayList<ArrayList<Integer>> lineChartData) {
		this.lineChartData = lineChartData;
	}

	public ArrayList<ArrayList<Integer>> getBarChartData() {
		return barChartData;
	}

	public void setBarChartData(ArrayList<ArrayList<Integer>> barChartData) {
		this.barChartData = barChartData;
	}
	
	

}
