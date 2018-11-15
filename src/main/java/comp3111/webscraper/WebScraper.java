package comp3111.webscraper;

import java.net.URLEncoder;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.Vector;


/**
 * WebScraper provide a sample code that scrape web content. After it is constructed, you can call the method scrape with a keyword, 
 * the client will go to the default url and parse the page by looking at the HTML DOM.  
 * <br/>
 * In this particular sample code, it access to craigslist.org. You can directly search on an entry by typing the URL
 * <br/>
 * https://newyork.craigslist.org/search/sss?sort=rel&amp;query=KEYWORD
 *  <br/>
 * where KEYWORD is the keyword you want to search.
 * <br/>
 * Assume you are working on Chrome, paste the url into your browser and press F12 to load the source code of the HTML. You might be freak
 * out if you have never seen a HTML source code before. Keep calm and move on. Press Ctrl-Shift-C (or CMD-Shift-C if you got a mac) and move your
 * mouse cursor around, different part of the HTML code and the corresponding the HTML objects will be highlighted. Explore your HTML page from
 * body &rarr; section class="page-container" &rarr; form id="searchform" &rarr; div class="content" &rarr; ul class="rows" &rarr; any one of the multiple 
 * li class="result-row" &rarr; p class="result-info". You might see something like this:
 * <br/>
 * <pre>
 * {@code
 *    <p class="result-info">
 *        <span class="icon icon-star" role="button" title="save this post in your favorites list">
 *           <span class="screen-reader-text">favorite this post</span>
 *       </span>
 *       <time class="result-date" datetime="2018-06-21 01:58" title="Thu 21 Jun 01:58:44 AM">Jun 21</time>
 *       <a href="https://newyork.craigslist.org/que/clt/d/green-star-polyp-gsp-on-rock/6596253604.html" data-id="6596253604" class="result-title hdrlnk">Green Star Polyp GSP on a rock frag</a>
 *       <span class="result-meta">
 *               <span class="result-price">$15</span>
 *               <span class="result-tags">
 *                   pic
 *                   <span class="maptag" data-pid="6596253604">map</span>
 *               </span>
 *               <span class="banish icon icon-trash" role="button">
 *                   <span class="screen-reader-text">hide this posting</span>
 *               </span>
 *           <span class="unbanish icon icon-trash red" role="button" aria-hidden="true"></span>
 *           <a href="#" class="restore-link">
 *               <span class="restore-narrow-text">restore</span>
 *               <span class="restore-wide-text">restore this posting</span>
 *           </a>
 *       </span>
 *   </p>
 *}
 *</pre>
 * <br/>
 * The code 
 * <pre>
 * {@code
 * List<?> items = (List<?>) page.getByXPath("//li[@class='result-row']");
 * }
 * </pre>
 * extracts all result-row and stores the corresponding HTML elements to a list called items. Later in the loop it extracts the anchor tag 
 * &lsaquo; a &rsaquo; to retrieve the display text (by .asText()) and the link (by .getHrefAttribute()). It also extracts  
 * 
 *
 */
public class WebScraper 
{

	private static final String DEFAULT_URL = "https://newyork.craigslist.org/";
	private static final String NEW_URL = "https://www.price.com.hk/";
	private int numOfPageScrapped = 0;
	
	private WebClient client;

	/**
	 * Default Constructor 
	 */
	public WebScraper() 
	{
		client = new WebClient();
		client.getOptions().setCssEnabled(false);// so that we only get HTML files
		client.getOptions().setJavaScriptEnabled(false);
	}

	/**
	 * The only method implemented in this class, to sort the items stored in the List
	 * 
	 * @param keyword - the keyword you want to search
	 * @return The sorted result
	 */
	public List<Item> SortItem(List<Item> result)
	{
		
		for(int i=0;i<result.size();i++)
		{	
			for(int j=i+1;j<result.size();j++)
			{
				if(result.get(i).getPrice() > result.get(j).getPrice())
				{					
					Item temp = result.get(i);
					 result.add(i, result.get(j));
					 result.remove(i+1);
					 result.add(j, temp);
					 result.remove(j+1);
				}
				
				else if(result.get(i).getPrice() == result.get(j).getPrice())
				{
					if(result.get(j).getUrl()=="https://newyork.craigslist.org/")
					{
						 Item temp = result.get(i);
						 result.add(i, result.get(j));
						 result.remove(i+1);
						 result.add(j, temp);
						 result.remove(j+1);
					}	
				}
				else
					continue;
				
			}
		}
		return result;
	}
	
/**
 * The only method implemented in this class, to scrape web content from the craigslist
 * 
 * @param keyword - the keyword you want to search
 * @return A list of Item that has found. A zero size list is return if nothing is found. Null if any exception (e.g. no connectivity)
 */
	
public List<Item> scrape(String keyword) 
	{

		try {
			int numberOfItems=0;
			int Craig_num_of_Items = 0;
			int numberOfPages = 0;
			int num_ItemsPerPage = 0;
			HtmlAnchor URL = null;
			
			
			String searchUrl = DEFAULT_URL + "search/sss?sort=rel&query=" + URLEncoder.encode(keyword, "UTF-8");
			HtmlPage page = client.getPage(searchUrl);
		
			List<?> items = (List<?>) page.getByXPath("//li[@class='result-row']");
			
			
			HtmlElement temp = (HtmlElement) items.get(0);
			HtmlElement ItemsPerPage = ((HtmlElement) temp.getFirstByXPath("//*[@id=\"searchform\"]/div[3]/div[3]/span[2]/span[3]/span[1]/span[2]"));
			num_ItemsPerPage = Integer.parseInt(ItemsPerPage.asText());
					
			//Changed from default Vector<Item> result = new Vector<Item>(); because return type is List
			//Vector<Item> result = new Vector<Item>();
			List<Item> result = scrape_new(keyword);
			
			
			for (int i = 0; i < num_ItemsPerPage; i++) {
				HtmlElement htmlItem = (HtmlElement) items.get(i);
				HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath(".//p[@class='result-info']/a"));
				//XML PATH = *[@id="searchform"]/div[3]/div[3]/span[2]/span[3]/span[2]
				if(i==0)
				{
					HtmlElement num_Items = ((HtmlElement) htmlItem.getFirstByXPath("//*[@id=\"searchform\"]/div[3]/div[3]/span[2]/span[3]/span[2]"));
					numberOfItems = Integer.parseInt(num_Items.asText());
					
					HtmlElement Craiglist_num_of_Items = ((HtmlElement) htmlItem.getFirstByXPath("//*[@id=\"searchform\"]/div[3]/div[3]/span[2]/span[3]/span[1]/span[2]"));
					Craig_num_of_Items = Integer.parseInt(Craiglist_num_of_Items.asText());
					//System.out.println(numberOfItems);
					//System.out.println(Craig_num_of_Items);
					
				}
				//System.out.println(itemAnchor.asText());
				//<span class="result-price">$5</span>
				HtmlElement spanPrice = ((HtmlElement) htmlItem.getFirstByXPath(".//a/span[@class='result-price']"));

				// It is possible that an item doesn't have any price, we set the price to 0.0
				// in this case
				String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText();
				Item item = new Item();
				item.setTitle(itemAnchor.asText());
				item.setUrl(DEFAULT_URL + itemAnchor.getHrefAttribute());
				
				//Task 2 subtask (ii) Modify the class Item so that it will also record which portal this item is coming from.
				item.setPortal(DEFAULT_URL);
				item.setPrice(new Double(itemPrice.replace("$", "")));

				result.add(item);
				
				//<a href="/search/sss?query=apple&amp;s=120&amp;sort=rel" class="button next" title="next page">next &gt; </a>
				URL = ((HtmlAnchor) htmlItem.getFirstByXPath("//a[@class='button next']"));
			
			}
			numOfPageScrapped++;
			System.out.println("Number of Pages Scraped: "+numOfPageScrapped);	
			numberOfPages = (numberOfItems/Craig_num_of_Items)-1;
			
			//case when 120 / 2211 where it gives a decimal val but after converting to integer loose the decimal place hence loosing its accuracy
			if(Craig_num_of_Items%numberOfItems!=0)
				numberOfPages++;
			
			if(numberOfPages>0)
				result = multiple_page(result,numberOfPages,URL);
			else
				 System.out.println("This is the last page that will be scraped");
	
		client.close();
		return SortItem(result);
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		
		
		return null;
}
	
	
/**
 * The only method implemented in this class, to scrape multiple pages if the result of the search has more than one page
 * 
 * @param result - the List of items scraped from other websites
 * @param numberOfPages - The number of Extra pages to scrape
 * @param URL - The URL of the next page to scrape
 * @return The result which contains the scraped data of the multiple web pages
 */
public List<Item> multiple_page(List<Item> result,int numberOfPages,HtmlAnchor URL)
	{
		
		for(int i=0;i<numberOfPages ;i++)
		{
			 try {
				 	numOfPageScrapped++;
					System.out.println("Number of Pages Scraped: "+numOfPageScrapped);
				 	HtmlPage next_page = client.getPage(DEFAULT_URL+URL.getHrefAttribute());
				 	List<?> next_items = (List<?>) next_page.getByXPath("//li[@class='result-row']");
				 	//System.out.println("items_size=="+next_items.size());
				 	for (int j = 0; j < next_items.size(); j++) 
				 	{
				 		HtmlElement htmlItem = (HtmlElement) next_items.get(j);
				 		HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath(".//p[@class='result-info']/a"));
						HtmlElement spanPrice = ((HtmlElement) htmlItem.getFirstByXPath(".//a/span[@class='result-price']"));
		
						String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText();
		
						Item item = new Item();
						item.setTitle(itemAnchor.asText());
						item.setUrl(DEFAULT_URL + itemAnchor.getHrefAttribute());
						
						
						item.setPortal(DEFAULT_URL);
						item.setPrice(new Double(itemPrice.replace("$", "")));
						result.add(item);
						URL = ((HtmlAnchor) htmlItem.getFirstByXPath("//a[@class='button next']"));	
						
				 	}
			 	}
			 catch (Exception e) 
			 {
				 System.out.println(e);
			 }
				
		}
		System.out.println("This is the last page that will be scraped");
		return result;
}
	
/**
 * The only method implemented in this class, that scrapes data from NEW_URL
 * 
 * @param keyword - the key word used to search items
 * @return The result which contains the scraped data of NEW_URL
 */
	
public List<Item> scrape_new(String keyword)
	{
	numOfPageScrapped=0;
				try
				{
					//String searchUrl = NEW_URL + "s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=" + URLEncoder.encode(keyword, "UTF-8");
					String searchUrl = NEW_URL + "search.php?g=T&q=" + URLEncoder.encode(keyword, "UTF-8");
					HtmlPage page2 = client.getPage(searchUrl);
					
					List<?> items = (List<?>) page2.getByXPath("//div[@class='item']");
				
					Vector<Item> result = new Vector<Item>();
					
					//Scraping data from the second URL
					//System.out.println("items_size=="+items.size());
					for (int i = 0; i < items.size(); i++) //items_second_Url.size() -> number of items in the list 
					{
						
						HtmlElement second_htmlItem = (HtmlElement) items.get(i);
						
						//prints all the items and their attributes
						//System.out.println(second_htmlItem.asText());

						HtmlAnchor itemAnchor = ((HtmlAnchor) second_htmlItem.getFirstByXPath(".//div[@class='line line-01']/a"));
			
						HtmlElement spanPrice = ((HtmlElement) second_htmlItem.getFirstByXPath(".//span[@class='text-price-number']"));
				
						// It is possible that an item doesn't have any price, we set the price to 0.0
						// in this case
						String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText();

						Item item = new Item();
						item.setTitle(itemAnchor.asText());
						item.setUrl(NEW_URL + itemAnchor.getHrefAttribute());
						item.setPortal(NEW_URL);
						item.setPrice(new Double(itemPrice.replaceAll(",", "")));
						result.add(item);
						
					}
					client.close();
					numOfPageScrapped++;
					System.out.println("Number of Pages Scraped: "+numOfPageScrapped);
					return result;
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}		
				return null;
						
	}
}
