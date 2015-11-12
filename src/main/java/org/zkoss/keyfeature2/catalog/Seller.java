package org.zkoss.keyfeature2.catalog;

import java.util.ArrayList;
import java.util.List;

public class Seller {
	private String name;
	private String icon;
	private List<CatalogItem> items;

	public Seller() {}

	public Seller(String name, String icon) {
		this.name = name;
		this.icon = icon;
		items = new ArrayList<CatalogItem>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<CatalogItem> getItems() {
		return items;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}