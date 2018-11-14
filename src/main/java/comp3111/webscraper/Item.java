package comp3111.webscraper;

import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
	
	
	private String title ; 
	private double price ;
	private String url ;
	
	//task 4
	private Date date;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	/*
	private StringProperty title = new SimpleStringProperty(); 
	private DoubleProperty price = new SimpleDoubleProperty() ;
	private StringProperty url  = new SimpleStringProperty();


	
	public StringProperty titleproperty() {return title;}
	public DoubleProperty priceproperty() {return price;}
	public StringProperty urlproperty() {return url;}
	*/
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price; 
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url=url;
	}
	

}
