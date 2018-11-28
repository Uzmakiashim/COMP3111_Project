package comp3111.webscraper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;

import java.util.List;
import java.util.Vector;

import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;


public class ControllerTest extends ApplicationTest{

//	private static final String UI_FILE = "/ui.fxml";  //It is very important that you put the file under folder src/main/resources/
//	
//	@Override
//	public void start(Stage stage) throws Exception {
//    	FXMLLoader loader = new FXMLLoader();
//    	loader.setLocation(getClass().getResource(UI_FILE));
//   		VBox root = (VBox) loader.load();
//   		Scene scene =  new Scene(root);
//   		stage.setScene(scene);
//   		stage.setTitle("WebScrapper");
//   		stage.show();
//    		
//
//	}
	
	
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
