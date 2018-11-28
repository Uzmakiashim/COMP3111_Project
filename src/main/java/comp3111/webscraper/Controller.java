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
import java.util.Vector;


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

    /**
     * task 6
     */
    private String lastInput;
    private String currentInput;
    private List<Item> lsresult;
    private List<Item> cresult;
    @FXML
    private MenuItem lsButton;

    private WebScraperApplication app = new WebScraperApplication();
    
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
     * task 1
     * 
     * @author Janice Chan
     * 
     * Finding out the total number of result items, the average selling price, and the URL
     * of both the cheapest item and the latest item, the values are returned,
     * the function is used during search function or refine function
     * 
     * @param List<Item>	the list of result items
     * @exception ParseException
     * 				if the parsed text is not the same as the SimpleDateFormat
     * @return List<String>	the list of string listed in summary tab
     * 
     */

    protected List<String> summaryContent(List<Item> result) {
    	int itemNo = 0;
    	int itemNo_aboveZero = 0;
    	double totalPrice = 0.0;
    	
    	Item cheapestItem = new Item();
    	Item newestItem = new Item();
    	cheapestItem.setPrice(0.0);
    	cheapestItem.setTitle(null);
    	cheapestItem.setUrl(null);
    	cheapestItem.setDate(null);
    	newestItem.setPrice(0.0);
    	newestItem.setTitle(null);
    	newestItem.setUrl(null);
    	newestItem.setDate(null);
    	

    	Vector<String> output = new Vector<String>();
    	
    	if(result==null) {
        	output.add("0");
        	output.add("-");
        	output.add("-");
        	output.add("-");

        	return output;
    	}
    	for(Item item : result) {
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
    	
    	
    	output.add(Integer.toString(itemNo));
    	output.add((totalPrice==0||itemNo_aboveZero==0) ? "-":Double.toString(totalPrice/itemNo_aboveZero));
    	output.add(cheapestItem.getUrl()==null ? "-":cheapestItem.getUrl());
    	output.add(newestItem.getUrl()==null ? "-":newestItem.getUrl());
    	
    	return output;
    }
    
    
    /**
     * task 1 
     * 
     * @author Janice Chan
     * 
     * Fills in the summary tab using the content received and create links
     * @param output	summary content obtained
     */
    protected void fillSummary(List<String> output) {
    	//show results
    	labelCount.setText(output.get(0));
    	labelPrice.setText(output.get(1));
    	labelMin.setText(output.get(2));
    	labelLatest.setText(output.get(3));
    	
    	//only disable link if the url is usable
    	if(!labelMin.getText().equals("<Lowest>")||!labelMin.getText().equals("-"))
    		labelMin.setDisable(false);
    	if(!labelLatest.getText().equals("<Lowest>")||!labelLatest.getText().equals("-"))
    		labelLatest.setDisable(false);
    	
    	//open cheapest item in browswer when link clicked
    	labelMin.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e){
    				app.getHostServices().showDocument(output.get(2));
    		}
    	});
    	//open newest item in browswer when link clicked 
    	labelLatest.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e){
    			app.getHostServices().showDocument(output.get(3));
    		}
    	});    	
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
    	
    	List<Item> result = scraper.scrape(textFieldKeyword.getText(),"","");
//    	System.out.println(result.size());
    	
    	if(result.size()!=0)
    		refineButton.setDisable(false);
    	String output = "";
    	for (Item item : result) {
    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
       	}

    	
      
    	//task 4
    	this.itemTitle.setCellValueFactory(new PropertyValueFactory<Item,String>("title"));
    	this.itemPrice.setCellValueFactory(new PropertyValueFactory<Item,String>("price"));
       	this.itemURL.setCellValueFactory(new PropertyValueFactory<Item,String>("url"));
    	this.itemPostdate.setCellValueFactory(new PropertyValueFactory<Item,String>("date"));
       	this.searchTable.setItems(FXCollections.observableArrayList(result));
 
       	//Task2_(subtask3)
    	
   
    		
    	
    	textAreaConsole.setText(output);
    	
    	//task 6
    	if(cresult!=result) {
    		lsresult = cresult;
    		cresult = result;
    	}
    	
    	//task 1
    	fillSummary(summaryContent(result));
    	
    	//task 6
    	//update lastSearch button
    	lsButton.setDisable(false);
    	

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
    	String output="";
    	;
    	String []refineSearch = textFieldKeyword.getText().trim().split("\\s+");
    	System.out.println("actionNew: " + refineSearch);
    	List<Item> refinedresults= new ArrayList<Item>();
//    	System.out.print(refineSearch);
    	
    	
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
    	}    	
    	textAreaConsole.setText(output);

    	setRefinebutton(true);

    	fillSummary(summaryContent(refinedresults));
    	
    	
    	//task 6
    	//update lastSearch button
    	lsButton.setDisable(false);
    	
    }
    
    /**
     * task 6
     * 
     * @author Janice Chan
     * 
     * Revert the search result of the last search, but excluding the refine result
     */
    @FXML
    protected void lastSearch() {
    	System.out.println("Last Search: "+lastInput);
    	if(lastInput==null)
    		textFieldKeyword.setText(currentInput);
    	else
    		textFieldKeyword.setText(lastInput);
//    	actionSearch();
    	
    	if(lsresult==null)
    		lsresult = cresult;
    	
    	if(currentInput!=textFieldKeyword.getText()) {
    		lastInput = currentInput;
    		currentInput = textFieldKeyword.getText();
    	}
    	
    	System.out.println("LastactionSearch: " + textFieldKeyword.getText());

//    	System.out.println(lsresult.size());
    	
    	if(lsresult.size()!=0)
    		refineButton.setDisable(false);
    	String output = "";
    	for (Item item : lsresult) {
    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
    	}

    	//task 4
    	this.itemTitle.setCellValueFactory(new PropertyValueFactory<Item,String>("title"));
    	this.itemPrice.setCellValueFactory(new PropertyValueFactory<Item,String>("price"));
       	this.itemURL.setCellValueFactory(new PropertyValueFactory<Item,String>("url"));
    	this.itemPostdate.setCellValueFactory(new PropertyValueFactory<Item,String>("date"));
       	this.searchTable.setItems(FXCollections.observableArrayList(lsresult));
   
    	    	
    	textAreaConsole.setText(output);
    	
    	
    	fillSummary(summaryContent(lsresult));
    	
    	lsButton.setDisable(true);
    }
    
    /**
     * task 6
     * 
     * @author Janice Chan
     * 
     * Show team information after clicking "About the Team" button
     */
    @FXML
    protected void teamInfo() {
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
    
    /**
     * task 6
     * 
     * @author Janice Chan
     * 
     * Quit the program after clicking "Quit" button
     */
    @FXML
    protected void quit() {
    	System.out.println("Quit");
    	System.exit(0);
    }
    
    /**
     * task 6
     * 
     * @author Janice Chan
     * 
     * Close the current search and clear all the result of previous search
     */
    @FXML
    protected void close() {
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
    	lsresult = cresult;
    	cresult = null;
    }
}
