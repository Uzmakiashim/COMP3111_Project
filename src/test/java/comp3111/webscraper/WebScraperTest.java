package comp3111.webscraper;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;


public class WebScraperTest {
	
	
	@Test
	
	public void testSortItem() {
		Vector<Item> result = new Vector<Item>();
		for(int i=0;i<20;i++)
		{
			Item temp = new Item();
			temp.setPrice(i);
			result.add(temp);
		}
		 WebScraper temp = new WebScraper();
		 List<Item> sort = temp.SortItem(result);
		 
		 
		boolean truth = true;
		 
		for(int i=0;i<20;i++)
			 for(int j=i+1;j<20;j++)
			 {
				 if((sort.get(i).getPrice())>sort.get(j).getPrice())
					 truth =false;
			 
			}
		assertEquals(truth, true);
	}
	
	@Test
	public void testSortItem2() {
		Vector<Item> result = new Vector<Item>();
		for(int i=20;i>0;i--)
		{
			Item temp = new Item();
			temp.setPrice(i);
			result.add(temp);
		}
		
		Item unique_item1 = new Item();
		unique_item1.setPrice(21);
		result.add(unique_item1);
		
		Item unique_item = new Item();
		unique_item.setPrice(21);
		unique_item.setUrl("https://newyork.craigslist.org/");
		result.add(unique_item);
		
		
		 WebScraper temp = new WebScraper();
		 List<Item> sort = temp.SortItem(result);
		 
		 
		boolean truth = true;
		 
		for(int i=0;i<20;i++)
			 for(int j=i+1;j<20;j++)
			 {
				 if((sort.get(i).getPrice())>sort.get(j).getPrice())
					 truth =false;
			 
			}
		assertEquals(truth, true);
	}
	
	//Test price.com.hk only
	
	
//	@Test
//	public void test_scrape()
//	{
//		 //		/COMP3111_Project/src/test/java/comp3111/webscraper/newyork_cr.html
//		 //		/COMP3111_Project/src/test/java/comp3111/webscraper/price.html
//		 WebScraper temp = new WebScraper();
//		 
//		 List<Item> result =  temp.scrape("xyz","file://./new_york_cr.html" , "file://./price_hk.html");
//		 System.out.println(result.get(0).getTitle());
//		 assertEquals(result.get(0).getTitle(),"Antique Art Deco 1937 Philco Tube Radio - Must see! xyz*");
//	}
//	
	
	@Test
	public void test_scrape_basic()
	{
		WebScraper temp = new WebScraper();
		List<Item> result =  temp.scrape("apple","", "");
		
		boolean truth = true;
		 
		for(int i=0;i<20;i++)
			 for(int j=i+1;j<20;j++)
			 {
				 if((result.get(i).getPrice())>result.get(j).getPrice())
					 truth =false;
			 
			}
		assertEquals(truth, true);
	}
}
