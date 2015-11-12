package org.zkoss.keyfeature2.catalog;

import java.math.BigDecimal;

import org.zkoss.bind.annotation.Immutable;


public class CatalogItem {
	private String image;
	private String title;
	private Seller seller;
	private BigDecimal price;
	
	public CatalogItem() {
		
	}

	public CatalogItem(String title, String image, Seller seller, BigDecimal price) {
		this.title = title;
		this.image = image;
		this.seller = seller;
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Immutable
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller.getItems().remove(this);
		this.seller = seller;
		this.seller.getItems().add(this);
	}

	public String getImage() {
		return image;
	}

	@Immutable
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
