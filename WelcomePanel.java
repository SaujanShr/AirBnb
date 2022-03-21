/**
 * Write a description of class WelcomePanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javafx.scene.layout.*;
import javafx.scene.control.*;

public class WelcomePanel extends Panel
{
    Pane welcomePaneWrapper, welcomePane;
    Label priceRange;
    
    public WelcomePanel() {
        setWelcomePane();
    }
    
    public Pane getPane() {
        return welcomePaneWrapper;
    }
    
    private void setWelcomePane() {
        welcomePaneWrapper = new HBox();
        welcomePane = new VBox();
        Label welcomeLabel = new Label("Welcome!");
        Label welcomeDescription = new Label("Insert some description here");
        priceRange = new Label("");
        
        Pane spacer1 = new Pane();
        Pane spacer2 = new Pane();
        Pane spacer3 = new Pane();
        Pane spacer4 = new Pane();
        
        //spacers to set text to center of the screen
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        VBox.setVgrow(spacer3, Priority.ALWAYS);
        VBox.setVgrow(spacer4, Priority.ALWAYS);
        
        welcomePaneWrapper.getChildren().addAll(spacer1, welcomePane, spacer2);
        welcomePane.getChildren().addAll(spacer3, welcomeLabel, welcomeDescription, priceRange, spacer4);
    }
    
    private void updatePriceDescription() {
        String priceRangeDescription = "Current price range: ";
        
        if (minPrice < 0) priceRangeDescription += "N/A";
        else priceRangeDescription += minPrice;
        
        priceRangeDescription += " - ";
        if (maxPrice < 0) priceRangeDescription += "N/A";
        else priceRangeDescription += maxPrice;
        
        if (maxPrice > 0 && minPrice > 0 && maxPrice < minPrice) {
            priceRangeDescription += "\n This range is invalid!";
        }
        
        priceRange.setText(priceRangeDescription);
    }
    
    public void fromBoxAction(double minPrice) {
        this.minPrice = minPrice;
        updatePriceDescription();
    }
    
    public void toBoxAction(double maxPrice) {
        this.maxPrice = maxPrice;
        updatePriceDescription();
    }
    
    public void reset() {
        priceRange.setText("");
    }
    
}
