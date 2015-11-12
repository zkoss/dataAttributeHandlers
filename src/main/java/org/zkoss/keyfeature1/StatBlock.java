package org.zkoss.keyfeature1;

public class StatBlock {

	private String id;
	private String title;
	private String value;
	private String icon;
	private String style;

	public StatBlock(String id, String title, String value, String icon, String style) {
		super();
		this.id = id;
		this.title = title;
		this.value = value;
		this.icon = icon;
		this.style = style;
	}

	public String getTitle() {
		return title;
	}

	public String getValue() {
		return value;
	}

	public String getId() {
		return id;
	}

	public String getIcon() {
		return icon;
	}

	public String getStyle() {
		return style;
	}
}
