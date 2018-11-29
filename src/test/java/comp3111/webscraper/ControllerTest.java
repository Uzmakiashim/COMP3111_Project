package comp3111.webscraper;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.loadui.testfx.GuiTest;
//import static org.junit.Assert.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;



import static org.junit.Assert.*;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.Label; 

import java.util.List;
import java.util.Vector;

import javax.swing.text.InternationalFormatter;




public class ControllerTest extends ApplicationTest{

	private static final String UI_FILE = "/ui.fxml";  //It is very important that you put the file under folder src/main/resources/
	
	@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource(UI_FILE));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("WebScrapper");
   		stage.show();
    		

	}
	@Before

	public void setUp () throws Exception {
	
	}
	 @After
	 public void tearDown () throws Exception {
	    FxToolkit.hideStage();
	    release(new KeyCode[]{});
	    release(new MouseButton[]{});
	  }
 
	
	@Test
	public void testfitdatatable()
	{
		WebScraper test = new WebScraper();
		Controller control = new Controller();
		//List<Item> results = test.scrape("tommy", "", "");
//		Vector<Item> result = new Vector<Item>();
//		for(int i=0;i<20;i++)
//		{
//			Item temp = new Item();
//			temp.setPrice(i);
//			temp.setTitle("A");
//			temp.setDate("2000-11-11 00:00");
//			temp.setUrl("1234567890-=");
//			temp.setPortal(null);
//			result.add(temp);
//		}
		clickOn("#textFieldKeyword");
		write("tommy");
		moveTo("Go");
		clickOn("Go");
		//Label label = (Label) GuiTest.find("#labelCount");
		//control.fitdataintable(results);
		//int size = control.gettableviewsize();
		//String s =  Integer.toString(size);
		//if(s=="20")
		//System.out.println("works"+s);
		//assertEquals(size,  20);		
	}
	
	
	@Test
	public void refiningItems()
	{
		Controller test = new Controller();
		Item a = new Item();
		a.setTitle("A B C");
		a.setPrice(0.0);
		a.setDate("2000-11-11 00:00");
		a.setUrl("1234567890-=");
		a.setPortal(null);
		
		Item b = new Item();
		b.setTitle("B C");
		b.setPrice(100.0);
		b.setDate("2001-11-11 00:00");
		b.setUrl("qwertyuiop");
		b.setPortal(null);

		Item c = new Item();
		c.setTitle("AB");
		c.setPrice(200.0);
		c.setDate("2002-11-11 00:00");
		c.setUrl("asdfghjkl");
		c.setPortal(null);
		
		Item d = new Item();
		d.setTitle("ABDD");
		d.setPrice(300.0);
		d.setDate("2003-11-11 00:00");
		d.setUrl("zxcvbnm,.");
		d.setPortal(null);
		
		Vector<Item> result = new Vector<Item>();
		result.add(a);
		result.add(b);
		result.add(c);
		result.add(d);
		
		String []refineSearch = {"A","C"};
		List<Item> RF = test.refiningitems(refineSearch, result);
		int size = RF.size();
		assertEquals(size,  1);
	/*	
		Item e = new Item();
		e.setTitle("ABDDE");
		e.setPrice(300.0);
		e.setDate("2003-11-11 00:00");
		e.setUrl("zxcvbnm,.");
		e.setPortal(null);
		
		Item f = new Item();
		f.setTitle("AC DC");
		f.setPrice(300.0);
		f.setDate("2003-11-11 00:00");
		f.setUrl("zxcvbnm,.");
		f.setPortal(null);
		
		result.add(e);
		result.add(f);
		String []newrefine = {"A"};
		List<Item> newRF = test.refiningitems(newrefine, result);
		int newsize = newRF.size();
		assertEquals(newsize,5);
		
		*/
		
	}
	
	@Test
	public void testSummaryContent() {
		Controller controller = new Controller();
		Item a = new Item();
		a.setTitle("A");
		a.setPrice(0.0);
		a.setDate("2000-11-11 00:00");
		a.setUrl("1234567890-=");
		a.setPortal(null);
		
		Item b = new Item();
		b.setTitle("B");
		b.setPrice(100.0);
		b.setDate("2001-11-11 00:00");
		b.setUrl("qwertyuiop");
		b.setPortal(null);

		Item c = new Item();
		c.setTitle("C");
		c.setPrice(200.0);
		c.setDate("2002-11-11 00:00");
		c.setUrl("asdfghjkl");
		c.setPortal(null);
		
		Item d = new Item();
		d.setTitle("D");
		d.setPrice(300.0);
		d.setDate("2003-11-11 00:00");
		d.setUrl("zxcvbnm,.");
		d.setPortal(null);
		
		Vector<Item> result = new Vector<Item>();
		result.add(a);
		result.add(b);
		result.add(c);
		result.add(d);
		
		
		List<String> output = controller.summaryContent(result);
		
		assertEquals(output.get(0),"4");
		assertEquals(output.get(1),"200.0");
		assertEquals(output.get(2),"qwertyuiop");
		assertEquals(output.get(3),"zxcvbnm,.");
		
		//new search
		result = null;
		output = controller.summaryContent(result);
		
		assertEquals(output.get(0),"0");
		assertEquals(output.get(1),"-");
		assertEquals(output.get(2),"-");
		assertEquals(output.get(3),"-");
		
	}

}
