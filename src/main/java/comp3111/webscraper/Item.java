package comp3111.webscraper;



public class Item {
	private String title ; 
	private double price ;
	private String url ;
	//-----------------Janice task1-------------------------
	private String date;
	//-----------------end of task1-------------------------
	
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
		this.url = url;
	}
	
	//-----------------Janice task1-------------------------
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	//-----------------end of task1-------------------------

}
