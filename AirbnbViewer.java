/**
 * 
 * Made by Saujan Shrestha
 * Version 1
 */

import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import java.util.Collection;
import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.application.Application;

public class AirbnbViewer extends Application
{
    private Stage stage;
    private Scene scene;
    private Pane topBox, bottomBox, contentPane;
    private Button leftButton, rightButton;
    private AirbnbDatabase airbnbDatabase;
    
    private Panel currentPanel;
    private PanelCatalog panelCatalog;
    
    private ArrayList<Double> priceRange;
    private boolean[] validPriceRange = {false, false};
    
    /**
     * Initialises the viewer.
     */
    public AirbnbViewer() {
        airbnbDatabase = new AirbnbDatabase();
        // Sets up all the panels when initialised
        panelCatalog = new PanelCatalog();
        panelCatalog.setup(airbnbDatabase);
        
        int[] priceRangeLimits = airbnbDatabase.getPriceRangeLimits();
        priceRange = new ArrayList<>();
        //sets values for the range of prices
        for (double i = priceRangeLimits[0]; i <= priceRangeLimits[1]; i += 100.0) {
            this.priceRange.add(i);
        }
    }
    
    /**
     * Initialises the javafx application.
     */
    public void start(Stage stage) {
        this.stage = stage;
        
        VBox root = new VBox();
        scene = new Scene(root);
        
        makeTopPane(root);
        makeContentPane(root);
        makeBottomPane(root);
        root.getStyleClass().add("root");
        
        scene.getStylesheets().add("airbnbViewer.css");
        
        stage.setTitle("AirbnbViewer");
        stage.setScene(scene);
        stage.show();
        
    }
    
    /**
     * Initialise the top pane.
     * This holds the 2 price ranges.
     */
    private void makeTopPane(Pane parent) {
        ObservableList<Double> observablePriceRange = FXCollections.observableArrayList(priceRange);
        topBox = new HBox();
        parent.getChildren().add(topBox);
        topBox.getStyleClass().add("topbox");
        
        Separator separator = new Separator();
        parent.getChildren().add(separator);
        
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label fromLabel = new Label("from: ");
        fromLabel.getStyleClass().add("label");
        ComboBox fromBox = new ComboBox(observablePriceRange);
        fromBox.setPromptText("-");
        fromBox.getStyleClass().add("combobox");
        Label toLabel = new Label("  to: ");
        toLabel.getStyleClass().add("label");
        ComboBox toBox = new ComboBox(observablePriceRange);
        toBox.setPromptText("-");
        toBox.getStyleClass().add("combobox");
        
        //sets actions of the comboboxes
        fromBox.setOnAction(e -> {fromBoxAction((double) fromBox.getValue());});
        toBox.setOnAction(e -> {toBoxAction((double) toBox.getValue());});
        
        topBox.getChildren().addAll(spacer, fromLabel, fromBox, toLabel, toBox);
    }
    
    /**
     * Initialise the content pane.
     * This holds the main content of the program.
     */
    private void makeContentPane(Pane parent) {
        contentPane = new BorderPane();
        parent.getChildren().add(contentPane);
        
        //begin in the starting panel
        changeContent(panelCatalog.getStartPanel());
        
        VBox.setVgrow(contentPane, Priority.ALWAYS);
        contentPane.getStyleClass().add("contentpane");
        
        //pushes all the content to the middle
    }
    
    /**
     * Initialise the bottom pane.
     * This holds 2 buttons for switching between properties.
     */
    private void makeBottomPane(Pane parent) {
        Separator separator = new Separator();
        parent.getChildren().add(separator);
        
        bottomBox = new HBox();
        parent.getChildren().add(bottomBox);
        bottomBox.getStyleClass().add("bottombox");
        
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        leftButton = new Button(" < ");
        leftButton.getStyleClass().add("button");
        rightButton = new Button(" > ");
        rightButton.getStyleClass().add("button");
        enablePanels(false);
        
        leftButton.setOnAction(e -> {leftButtonAction();});
        rightButton.setOnAction(e -> {rightButtonAction();});
        
        bottomBox.getChildren().addAll(leftButton, spacer, rightButton);
    }
    
    /**
     * Changes whats shown in the content pane.
     */
    private void changeContent(Panel contentPanel) {
        currentPanel = contentPanel;
        Pane content = contentPanel.getPane();
        ((BorderPane) contentPane).setCenter(content);
    }
    
    /**
     * Enables the left and right buttons on the application
     */
    private void enablePanels(boolean enabled) {
        leftButton.setDisable(!enabled);
        rightButton.setDisable(!enabled);
    }
    
    /**
     * When the from Combobox is used.
     */
    private void fromBoxAction(double minPrice) {
        currentPanel.fromBoxAction(minPrice);
        if (!currentPanel.validPrice()) {
             validPriceRange[0] = false;
             enablePanels(false);
             return;
         }
        validPriceRange[0] = true;
        
        if (validPriceRange[1]) {
            enablePanels(true);
        }
    }
    
    /**
     * When the to Combobox is used.
     */
    private void toBoxAction(double maxPrice) {
        currentPanel.toBoxAction(maxPrice);
         if (!currentPanel.validPrice()) {
             validPriceRange[1] = false;
             enablePanels(false);
             return;
         }
        validPriceRange[1] = true;
        
        if (validPriceRange[0]) {
            enablePanels(true);
        }
    }
    
    /**
     * When the left button is pressed.
     */
    private void leftButtonAction() {
        currentPanel.reset();
        changeContent(panelCatalog.getPreviousPanel());
    }
    
    /**
     * When the right button is pressed.
     */
    private void rightButtonAction() {
        currentPanel.reset();
        changeContent(panelCatalog.getNextPanel());
    }
}

