package org.zkoss.keyfeature2.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.zkoss.keyfeature2.ResourceLocation;

public class Catalog {

	private Random random = new Random();

	private List<CatalogItem> allItems;
	private List<Seller> allSellers;
	
	public Catalog() {
		loadSellers();
		loadItems();
	}

	public List<CatalogItem> getItems() {
		return allItems;
	}
	
	public List<Seller> getSellers() {
		return allSellers;
	}

	public List<Seller> getActiveSellers() {
		List<Seller> activeSellers = new ArrayList<Seller>(allSellers);
		Iterator<Seller> iterator = activeSellers.iterator();
		while(iterator.hasNext()) {
			Seller seller = iterator.next();
			if(seller.getItems().isEmpty()) {
				iterator.remove();
			}
		}
		return activeSellers;
	}
	
	private void loadItems() {
		allItems = new ArrayList<CatalogItem>();
		newItem("Briefcase", new BigDecimal("159.99"));
		newItem("BlueFolder", new BigDecimal("1.50"));
		newItem("USB", new BigDecimal("20.00"));
		newItem("Mailbox", new BigDecimal("129.50"));
		newItem("MagnifyingGlass", new BigDecimal("15.00"));
		newItem("Binoculars", new BigDecimal("245.30"));
	}
	
	private void newItem(String title, BigDecimal price) {
		CatalogItem item = new CatalogItem(title, imageLocation(title + ".svg"), randomSeller(), price);
		item.getSeller().getItems().add(item);
		allItems.add(item);
	}

	private void loadSellers() {
		allSellers = new ArrayList<Seller>();
		allSellers.add(new Seller("Buzzphilip", iconLocation("user1.svg"))); 
		allSellers.add(new Seller("Olliejeffery", iconLocation("user2.svg"))); 
		allSellers.add(new Seller("Claudia41", iconLocation("user3.svg"))); 
		allSellers.add(new Seller("Kirmorrison", iconLocation("user4.svg"))); 
	}
	
	private String iconLocation(String name) {
		return ResourceLocation.ICON_LOCATION + "/" + name;
	}

	private String imageLocation(String name) {
		return ResourceLocation.IMAGE_LOCATION + "/" + name;
	}
	
	private Seller randomSeller() {
		return allSellers.get(random.nextInt(allSellers.size()));
	}
}

