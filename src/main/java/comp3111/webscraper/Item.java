package comp3111.webscraper;



public class Item 
{
	private String title ; 
	private double price ;
	private String url ;
	private String portal;	// variable that identifies which portal the item came from
	
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
	public void setPrice(double price) 
	{
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
