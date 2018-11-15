/**
 * 
 */
package comp3111.webscraper;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Hyperlink;
//-----------------Janice task1-------------------------
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javax.security.auth.callback.Callback;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.HostServices;
//-----------------end of task1-------------------------
//-----------------Janice task6-------------------------
import javafx.scene.control.MenuItem;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//-----------------end of task6-------------------------
import java.util.List;





/**
 * 
 * @author kevinw
 *
 *
 * Controller class that manage GUI interaction. Please see document about JavaFX for details.
 * 
 */
public class Controller {

    @FXML 
    private Label labelCount; 

    @FXML 
    private Label labelPrice; 

    @FXML 
    private Hyperlink labelMin; 

    @FXML 
    private Hyperlink labelLatest; 

    @FXML
    private TextField textFieldKeyword;
    
    @FXML
    private TextArea textAreaConsole;
    
//-----------------Janice task6-------------------------
    @FXML
    private MenuItem lsButton;
    
    private String lastInput;
    private String currentInput;
//-----------------end of task6-------------------------    
//-----------------Janice task1-------------------------
    private int itemNo;
    private int itemNo_aboveZero;
    private double totalPrice;
    private Item cheapestItem = new Item();
    private Item newestItem = new Item();
    private WebScraperApplication app = new WebScraperApplication();
//-----------------end of task1-------------------------    
    private WebScraper scraper;
    
    /**
     * Default controller
     */
    public Controller() {
    	scraper = new WebScraper();
    }

    /**
     * Default initializer. It is empty.
     */
    @FXML
    private void initialize() {
    	
    }
    
    /**
     * Called when the search button is pressed.
     */
    @FXML
    private void actionSearch() {
    	//-----------------Janice task6-------------------------
    	//updating lastInput
    	if(currentInput!=textFieldKeyword.getText()) {
    		lastInput = currentInput;
    		currentInput = textFieldKeyword.getText();
    	}
    	//-----------------end of task6-------------------------
    	
    	System.out.println("actionSearch: " + textFieldKeyword.getText());
    	
    	//-----------------Janice task1------------------------- 
    	itemNo = 0;
    	itemNo_aboveZero = 0;
    	totalPrice = 0.0;
    	cheapestItem.setPrice(0.0);
    	cheapestItem.setTitle(null);
    	cheapestItem.setUrl(null);
    	cheapestItem.setDate(null);
    	newestItem.setPrice(0.0);
    	newestItem.setTitle(null);
    	newestItem.setUrl(null);
    	newestItem.setDate(null);
    	//-----------------end of task1-------------------------

    	List<Item> result = scraper.scrape(textFieldKeyword.getText());
    	String output = "";
    	for (Item item : result) {
    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
    		
    		//-----------------Janice task1-------------------------
    		//update count
    		++itemNo;
    		if(item.getPrice()!=0.0) {
    			//update count for calculating average price
    			++itemNo_aboveZero;
    			//update total price for calculating average price
    			totalPrice += item.getPrice();
    			//update cheapest item
    			if(item.getPrice()<cheapestItem.getPrice()||cheapestItem.getPrice()==0.0) {
        			cheapestItem.setTitle(item.getTitle());
        			cheapestItem.setPrice(item.getPrice());
        			cheapestItem.setUrl(item.getUrl());	
        			cheapestItem.setDate(item.getDate());
        		}
    		}
    		//update newest item
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				if(newestItem.getDate()==null||sdf.parse(item.getDate()).after(sdf.parse(newestItem.getDate()))) {
					newestItem.setTitle(item.getTitle());
					newestItem.setPrice(item.getPrice());
					newestItem.setUrl(item.getUrl());
					newestItem.setDate(item.getDate());
				}
			} catch (ParseException e) {
				System.err.println("Error in date!");
				e.printStackTrace();
			}	
    		//-----------------end of task1-------------------------
    	}
    	
    	textAreaConsole.setText(output);
    	
    	//-----------------Janice task1-------------------------
    	//show results
    	labelCount.setText(Integer.toString(itemNo));
    	labelPrice.setText((totalPrice==0||itemNo_aboveZero==0) ? "-":Double.toString(totalPrice/itemNo_aboveZero));
    	labelMin.setText(cheapestItem.getUrl()==null ? "-":cheapestItem.getUrl());
    	labelLatest.setText(newestItem.getUrl()==null ? "-":newestItem.getUrl());
    	
    	//only disable link if the url is usable
    	if(!labelMin.equals("<Lowest>")||!labelMin.equals("-"))
    		labelMin.setDisable(false);
    	if(!labelLatest.equals("<Lowest>")||!labelLatest.equals("-"))
    		labelLatest.setDisable(false);
    	
    	//open cheapest item in browswer when link clicked
    	labelMin.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e){
    				app.getHostServices().showDocument(cheapestItem.getUrl());
    		}
    	});
    	//open newest item in browswer when link clicked 
    	labelLatest.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e){
    			app.getHostServices().showDocument(newestItem.getUrl());
    		}
    	});
    	//-----------------end of task1-------------------------
    	
    	//-----------------Janice task6-------------------------
    	//update lastSearch button
    	lsButton.setDisable(false);
    	//-----------------end of task6-------------------------
    }
    
    /**
     * Called when the new button is pressed. Very dummy action - print something in the command prompt.
     */
    @FXML
    private void actionNew() {
    	System.out.println("actionNew");
    }
    
  //-----------------Janice task6-------------------------
    //task6: last search function
    @FXML
    private void lastSearch() {
    	System.out.println("Last Search: "+lastInput);
    	if(lastInput==null)
    		textFieldKeyword.setText(currentInput);
    	else
    		textFieldKeyword.setText(lastInput);
    	actionSearch();
    	lsButton.setDisable(true);
    }
    
    //task6: About the Team
    @FXML
    private void teamInfo() {
    	System.out.println("TeamInfo");
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("About the Team");
    	alert.setHeaderText(null);
    	alert.setContentText("Team members:	ITSC:		Github:\n"
    					   + "Ashim SHAKYA	ashakya	Uzmakiashim\n"
    					   + "Chan Chui Hang	chchancl	chchancl\n"
    					   + "Gursharan SINGH	gsinghaa	Sunny879");
    	alert.showAndWait();
    }
    
    //task6: quit function
    @FXML
    private void quit() {
    	System.out.println("Quit");
    	System.exit(0);
    }
    
    //task6: close function
    @FXML
    private void close() {
    	System.out.println("Close");
    	textAreaConsole.clear();
    	textFieldKeyword.clear();
    	labelCount.setText("<total>");
    	labelPrice.setText("<AvgPrice>");
    	labelMin.setText("<Lowest>");
    	labelMin.setDisable(true);
    	labelLatest.setText("<Latest>");
    	labelLatest.setDisable(true);
    	
    	//update lastInput to be the input right before the close function
    	lastInput = currentInput;
    	currentInput = null;
    }
  //-----------------end of task6-------------------------
}

