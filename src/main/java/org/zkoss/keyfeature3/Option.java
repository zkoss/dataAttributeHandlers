package org.zkoss.keyfeature3;

import org.zkoss.json.JSONObject;

public class Option extends JSONObject {

	private Boolean checked;

	public Option(String label) {
		this(label, label);
	}

	public Option(String checked_label, String unchecked_label) {
		put("checked_label", checked_label);
		put("unchecked_label", unchecked_label);
	}

	public void setMinimumWidth(String width) {
		put("minimum_width", width);
	}

	public void setClass(String className) {
		put("class", className);
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

}
