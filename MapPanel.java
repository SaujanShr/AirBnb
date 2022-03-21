import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.Scene;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Write a description of class MapPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MapPanel extends Panel
{
    Pane propertyWrapper, mapPane;
    AnchorPane boroughMap;
    HashMap<String, ArrayList<AirbnbListing>> boroughList;

    /**
     * Constructor for objects of class MapPanel
     */
    public MapPanel() {
        boroughList = new HashMap<>();
        setMapPane();
        generateBoroughButtons();
    }
    
    public Pane getPane() {
        return propertyWrapper;
    }
    
    public void setMapPane() {
        propertyWrapper = new VBox();
        mapPane = new HBox();
        boroughMap = new AnchorPane();
        
        //sets the buttons of the boroughs
        try {
            URL url = getClass().getResource("BoroughsMap.fxml");
            boroughMap = FXMLLoader.load(url);
        }
        catch (java.io.IOException e) {
            System.out.println("BoroughsMap not found!");
        }
        
        //spacers to set image to center of the screen
        Pane spacer1 = new Pane();
        Pane spacer2 = new Pane();
        Pane spacer3 = new Pane();
        Pane spacer4 = new Pane();
        VBox.setVgrow(spacer1, Priority.ALWAYS);
        VBox.setVgrow(spacer2, Priority.ALWAYS);
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        HBox.setHgrow(spacer4, Priority.ALWAYS);
        
        propertyWrapper.getChildren().addAll(spacer1, mapPane, spacer2);
        mapPane.getChildren().addAll(spacer3, boroughMap, spacer4);
    }
    
    public void generateBoroughButtons() {
        for (Node child : boroughMap.getChildren()) {
            Button borough = (Button) child;
            ArrayList<AirbnbListing> newAirbnbData = airbnbDatabase.getNeighbourhoodData(borough.getId());
            boroughList.put(borough.getId(), newAirbnbData);
            
            setButtonColour(borough, newAirbnbData);
        }
    }
    
    public void setButtonColour(Button button, ArrayList<AirbnbListing> airbnbData) {
        int size = airbnbData.size();
        button.getStyleClass().removeAll(".button");
        
        //sets colour depending on number of available properties
        //should be put in boroughbutton, not here but it wasnt working when i did it
        if (size >= 4000) button.setStyle("-fx-background-color: #33cc33");
        else if (size >= 3600) button.setStyle("-fx-background-color: #00ff00");
        else if (size >= 3200) button.setStyle("-fx-background-color: #99ff33");
        else if (size >= 2800) button.setStyle("-fx-background-color: #ccff66");
        else if (size >= 2400) button.setStyle("-fx-background-color: #ffff66");
        else if (size >= 2000) button.setStyle("-fx-background-color: #ffcc00");
        else if (size >= 1600) button.setStyle("-fx-background-color: #ff6600");
        else if (size >= 1200) button.setStyle("-fx-background-color: #ff0000");
        else if (size >= 800) button.setStyle("-fx-background-color: #cc0000");
        else if (size >= 400) button.setStyle("-fx-background-color: #990000");
        else if (size >= 0) button.setStyle("-fx-background-color: #800000");
        
        button.setOnAction(e -> {buttonAction(button.getId());});
        
    }
    
    public void buttonAction(String borough) {
        if (validPrice()) {
            PropertyViewer propertyViewer = new PropertyViewer();
            propertyViewer.startListWindow(borough, boroughList.get(borough), minPrice, maxPrice);
        }
    }
    
    public void fromBoxAction(double minPrice) {
        this.minPrice = minPrice;
    }
    
    public void toBoxAction(double maxPrice) {
        this.maxPrice = maxPrice;
    }
    
    public void reset() {
    }
}
