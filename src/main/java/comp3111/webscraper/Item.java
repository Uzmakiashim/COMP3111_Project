package comp3111.webscraper;

import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is for items from the website and stores values scraped from the website
 *
 * 
 *
 */
public class Item 
{

	private String title ; 
	private double price ;
	private String url ;

	private String portal;	// variable that identifies which portal the item came from
	private String Date;

	

	/**
	 * An accessor function that gives the Data of the item stored in the website
	 * @return Date of item stored in the website
	 */
	public String getDate() {
		return Date;
	}
	
	/**
	 * A mutator function that stores the date of the item stored in the website
	 * @param Date which tells the date when the item was stored in the website
	 * 
	 */
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

	
	/**
	 * An accessor function that returns the portal of the item
	 * @return portal which the link or URL the website was downloaded from
	 */
	public String getPortal() 
	{
		return portal;
	}
	
	/**
	 * A mutator function that sets the portal of item
	 * @param portal which is the likn or URL of the website the item is coming from
	 * 
	 */
	public void setPortal(String portal) 
	{
		this.portal = portal;
	}
	
	/**
	 * An accessor function that returns the title of the item
	 * @return title which returns the title of the item
	 */
	public String getTitle() 
	{
		return title;
	}
	
	/**
	 * A mutator function that sets the title of item
	 * @param title which is the title of the item in the website 
	 * 
	 */
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	/**
	 * An accessor function that returns the price of the item
	 * @return price which returns the price of the item
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * A mutator function that sets the price of item
	 * @param price which is the price of the item in the website 
	 * 
	 */
	public void setPrice(double price) {
		this.price = price; 

	}
	
	/**
	 * An accessor function that returns the url of the item
	 * @return url which returns the url of the item
	 */
	public String getUrl() 
	{
		return url;
	}

	/**
	 * A mutator function that sets the url of item
	 * @param url which is the url of the item 
	 * 
	 */
	public void setUrl(String url) 
	{
		this.url = url;
	}

}
