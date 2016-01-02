package org.zkoss.handlers.jimp;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.ToServerCommand;

/**
 * @author Mathieu Piette
 */
@ToServerCommand({ "camanCalculationFinished" })
public class JimpViewModel {

	private int brightness = 3;
	private String color = "#B000B5"; // It's funny because boobs
	private int contrast = 1;
	
	/**
	 * The base-64 encoded jpeg data of the processed image.
	 */
	private String jpegData;

	@Command
	@NotifyChange("jpegData")
	public void camanCalculationFinished(@BindingParam("data") String jpegData) {
		setJpegData(jpegData);
	}

	public int getBrightness() {
		return brightness;
	}

	public String getColor() {
		return color;
	}

	public int getContrast() {
		return contrast;
	}

	public String getJpegData() {
		return jpegData;
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setContrast(int contrast) {
		this.contrast = contrast;
	}

	public void setJpegData(String jpegData) {
		this.jpegData = jpegData;
	}

}
