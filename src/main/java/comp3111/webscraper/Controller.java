/**
 * 
 */
package comp3111.webscraper;


import javafx.fxml.FXML;
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
	@FXML private TableColumn<Item,Date> itemPostdate;
	
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
    private void initialize() 
    {
    	System.out.println("In bro");
        itemURL.setCellFactory(tc ->{
            TableCell<Item, String> urlcell = new TableCell<Item, String>() 
            {
            @Override
            protected void updateItem(String itemincell, boolean emptyorNot)
            {
            	super.updateItem(itemincell, emptyorNot);
                setText(emptyorNot ? null : itemincell);
            }
            };
            urlcell.setOnMouseClicked(e -> {
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
        refineButton.setDisable(true);
    }

    
    
    /**
     * Called when the search button is pressed.
     */
    @FXML
    private void actionSearch() {
    	//enabling the refine button when go is pressed(Sunny)task 5 
    	refineButton.setDisable(false);
    	System.out.println("actionSearch: " + textFieldKeyword.getText());
    	List<Item> result = scraper.scrape(textFieldKeyword.getText());
    	System.out.println(result.size());
    	String output = "";
    	for (Item item : result) {
    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
    	}    	
    	textAreaConsole.setText(output);
    	
    	//task 4
    	this.itemTitle.setCellValueFactory(new PropertyValueFactory<Item,String>("title"));
    	this.itemPrice.setCellValueFactory(new PropertyValueFactory<Item,String>("price"));
       	this.itemURL.setCellValueFactory(new PropertyValueFactory<Item,String>("url"));
    	this.itemPostdate.setCellValueFactory(new PropertyValueFactory<Item,Date>("date"));
       	this.searchTable.setItems(FXCollections.observableArrayList(result));
 
    	
    }
    
    /**
     * Called when the new button is pressed. Very dummy action - print something in the command prompt.
     */
    //Sunny task 4
    @FXML
    private void actionNew() {
    	String output="";
    	String refineSearch = textFieldKeyword.getText();
    	System.out.println("actionNew: " + refineSearch);
    	List<Item> tempresult= this.searchTable.getItems();
    	List<Item> refinedresults= new ArrayList<Item>();
    	for(Item item:tempresult)
    	{
    		if(item.getTitle().toLowerCase().contains(refineSearch.toLowerCase()))
    			refinedresults.add(item);
    	}
    	System.out.println(tempresult.size());
    	System.out.println(refinedresults.size());
    	
    	this.searchTable.setItems(FXCollections.observableArrayList(refinedresults));
    	for (Item item : refinedresults) {
    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
    	}    	
    	textAreaConsole.setText(output);
    	refineButton.setDisable(true);
    }
}

