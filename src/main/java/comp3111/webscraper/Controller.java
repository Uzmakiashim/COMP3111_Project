/**
 * 
 */
package comp3111.webscraper;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Hyperlink;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

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


import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.control.Button;




/**
 * 
 * @author kevinw
 *
 *
 * Controller class that manage GUI interaction. Please see document about JavaFX for details.
 * 
 */
public class Controller {
	/*
	 * task 4
	 */
	@FXML private TableView<Item> searchTable;
	@FXML private TableColumn<Item,String> itemTitle;
	@FXML private TableColumn<Item,String> itemPrice;
	@FXML private TableColumn<Item,String> itemURL;
	@FXML private TableColumn<Item,String> itemPostdate;

//Controller is the link between the GUI and the code part(we use scene builder for easier)
//scene builder generates an xml file -> This file generates the code or HTML version - >

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
    

    @FXML
    private Button refineButton;

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
    public Controller() 
    {
    	scraper = new WebScraper();
    
    }
    
    public void initializetableview()
    {
    	 itemURL.setCellFactory(tablecell ->{
             TableCell<Item, String> urlcell = new TableCell<Item, String>() 
             {
             @Override
             protected void updateItem(String itemincell, boolean emptyorNot)
             {
             	super.updateItem(itemincell, emptyorNot);
                 setText(emptyorNot ? null : itemincell);
             }
             };
             urlcell.setOnMouseClicked(click -> {
                 if (urlcell.isEmpty()==false) 
                 {
                 	
                     String clickableURL = urlcell.getItem();
                     if (Desktop.isDesktopSupported()) 
                     {
                         try 
                         {
                             Desktop.getDesktop().browse(new URI(clickableURL));
                         } catch (Exception exception) 
                         {
                             exception.printStackTrace();
                         }
                     }
                 }
             });
             return urlcell;
         });
    	this.itemTitle.setCellValueFactory(new PropertyValueFactory<Item,String>("title"));
     	this.itemPrice.setCellValueFactory(new PropertyValueFactory<Item,String>("price"));
        this.itemURL.setCellValueFactory(new PropertyValueFactory<Item,String>("url"));
     	this.itemPostdate.setCellValueFactory(new PropertyValueFactory<Item,String>("date"));
    }
    public void setRefinebutton(boolean enable) 
    {
    	refineButton.setDisable(enable);
    }
    
    /**
     * Default initializer. It is empty.
     */
    @FXML
    private void initialize() 
    {
    	initializetableview();
    	setRefinebutton(true);

    }

    public void fitdataintable(List<Item> result)
    {    	
       	this.searchTable.setItems(FXCollections.observableArrayList(result)); 	
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

    	List<Item> result = scraper.scrape(textFieldKeyword.getText(),"","");
    	System.out.println(result.size());
    	
    	if(result.size()!=0)
    		setRefinebutton(false);
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

    	
      
    	//task 4
    	fitdataintable(result);
    	/*
    	this.itemTitle.setCellValueFactory(new PropertyValueFactory<Item,String>("title"));
    	this.itemPrice.setCellValueFactory(new PropertyValueFactory<Item,String>("price"));
       	this.itemURL.setCellValueFactory(new PropertyValueFactory<Item,String>("url"));
    	this.itemPostdate.setCellValueFactory(new PropertyValueFactory<Item,String>("date"));
       	this.searchTable.setItems(FXCollections.observableArrayList(result));
    	 */
       	//Task2_(subtask3)
    	
   
    		
    	
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
    
    public List<Item> refiningitems(String refineSearch[],List<Item> tempresult)
    {
    	List<Item> refinedresults= new ArrayList<Item>();
    	for(Item item:tempresult)
  		{
    		int count=0;
    		for(int i = 0;i<refineSearch.length;i++)
        	{
    			if(item.getTitle().toLowerCase().contains(refineSearch[i].toLowerCase()))
    				count++;
        	}
    		if(count==refineSearch.length)
    			refinedresults.add(item);
    	}
    	System.out.println(tempresult.size());
    	System.out.println(refinedresults.size());
    	return refinedresults;
    	
    }
    
    /**
     * Called when the new button is pressed. Very dummy action - print something in the command prompt.
     */
    //Sunny task 4
    @FXML
    private void actionNew() 
    {
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

    	String output="";
    	;
    	String []refineSearch = textFieldKeyword.getText().trim().split("\\s+");
    	System.out.println("actionNew: " + refineSearch);
    	List<Item> refinedresults= new ArrayList<Item>();
    	System.out.print(refineSearch);
    	
    	
    	/*
    	List<Item> tempresult= this.searchTable.getItems();
    	for(Item item:tempresult)
  		{
    		int count=0;
    		for(int i = 0;i<refineSearch.length;i++)
        	{
    			if(item.getTitle().toLowerCase().contains(refineSearch[i].toLowerCase()))
    				count++;
        	}
    		if(count==refineSearch.length)
    			refinedresults.add(item);
    	}
    	
    	*/    	
    	//this.searchTable.setItems(FXCollections.observableArrayList(refinedresults));
    	List<Item> tempresult= this.searchTable.getItems();
    	refinedresults = refiningitems(refineSearch,tempresult);
    	fitdataintable(refinedresults);
    	for (Item item : refinedresults) {
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
    	setRefinebutton(true);
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
    	
    	searchTable.setItems(FXCollections.observableArrayList());
       	//update lastInput to be the input right before the close function
    	lastInput = currentInput;
    	currentInput = null;
    }
  //-----------------end of task6-------------------------
    

}
