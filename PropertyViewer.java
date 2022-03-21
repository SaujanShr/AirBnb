import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;

/**
 * Write a description of class PropertyViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PropertyViewer
{
    private Stage stage;
    private String borough;
    private ArrayList<AirbnbListing> boroughList;
    
    public PropertyViewer()
    {
    }
    
    public void startListWindow(String borough, ArrayList<AirbnbListing> boroughList, double minPrice, double maxPrice) {
        this.borough = borough;
        this.boroughList = resizedBoroughList(boroughList, minPrice, maxPrice);
        
        stage = new Stage();
        stage.setTitle(borough);
        stage.setResizable(false);
        
        VBox root = new VBox();
        makeMenu(root);
        makeList(root);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("propertyViewer.css");
        stage.setScene(scene);
        stage.show();
        
        
    }
    
    private ArrayList<AirbnbListing> resizedBoroughList( ArrayList<AirbnbListing> boroughList, double minPrice, double maxPrice) {
        ArrayList<AirbnbListing> newBoroughList = new ArrayList<>();
        
        for (AirbnbListing airbnb: boroughList) {
            int price = airbnb.getPrice();
            if (price >= minPrice && price <= maxPrice) {
                newBoroughList.add(airbnb);
            }
        }
        
        return newBoroughList;
    }
    
    public void makeMenu(Pane parent) {
        HBox menu = new HBox();
        parent.getChildren().add(menu);
        
        ComboBox box = new ComboBox();
        box.setPromptText("Select order");
        box.getItems().addAll("Order by name", "Order by price", "Order by number of reviews", "Order by Minimum nights");
        
        menu.getChildren().addAll(box);
    }
    
    public void makeList(Pane parent) {
        //table of all the things
        VBox propertyListWrapper = new VBox();
        propertyListWrapper.getStyleClass().add("propertylistwrapper");
        
        TableView propertyList = new TableView();
        propertyList.getStyleClass().add("tableview");
        
        propertyListWrapper.getChildren().add(propertyList);
        parent.getChildren().add(propertyListWrapper);
        
        //puts each thing into each thing
        TableColumn<AirbnbListing, String> hostNameColumn = new TableColumn("Name");
        TableColumn<AirbnbListing, Integer> priceColumn = new TableColumn("Price");
        TableColumn<AirbnbListing, Integer> reviewNumberColumn = new TableColumn("Number of reviews");
        TableColumn<AirbnbListing, Integer> minimumNightsColumn = new TableColumn("Minimum nights");
        
        hostNameColumn.setCellValueFactory(new PropertyValueFactory<>("host_name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        reviewNumberColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfReviews"));
        minimumNightsColumn.setCellValueFactory(new PropertyValueFactory<>("minimumNights"));
        
        propertyList.getColumns().addAll(hostNameColumn, priceColumn, reviewNumberColumn, minimumNightsColumn);
        
        for (AirbnbListing airbnb : boroughList) {
            propertyList.getItems().add(airbnb);
        }
        
    }
}
