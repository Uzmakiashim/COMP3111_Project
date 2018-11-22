package comp3111.webscraper;

import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item 
{

	private String title ; 
	private double price ;
	private String url ;
	private String portal;	// variable that identifies which portal the item came from
	private String Date;
	

	
	public String getDate() {
		return Date;
	}
	public void setDate(String Date) {
		this.Date = Date;
	}
	/*
	private StringProperty title = new SimpleStringProperty(); 
	private DoubleProperty price = new SimpleDoubleProperty() ;
	private StringProperty url  = new SimpleStringProperty();


	
	public StringProperty titleproperty() {return title;}
	public DoubleProperty priceproperty() {return price;}
	public StringProperty urlproperty() {return url;}
	*/

	
	public String getPortal() 
	{
		return portal;
	}
	public void setPortal(String portal) 
	{
		this.portal = portal;
	}
	
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price; 

	}
	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
	}
	

}
