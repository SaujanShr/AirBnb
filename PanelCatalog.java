import javafx.scene.layout.*;
import java.util.ArrayList;
import javafx.scene.Scene;

/**
 * Write a description of class PanelCatalog here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PanelCatalog
{
    private ArrayList<Panel> panelList;
    private int currentPanel;
    
    /**
     * Constructor for objects of class PanelCatalog
     */
    public PanelCatalog() {
        panelList = new ArrayList<>();
    }
    
    public void setup(AirbnbDatabase airbnbDatabase) {
        Panel.setAirbnbDatabase(airbnbDatabase);
        
        setWelcomePanel();
        setMapPanel();
    }
    
    public Panel getStartPanel() {
        currentPanel = 0;
        return panelList.get(0);
    }
    
    public Panel getNextPanel() {
        currentPanel++;
        if (currentPanel > (panelList.size() - 1)) {
            currentPanel = 0;
        }
        
        return panelList.get(currentPanel);
    }
    
    public Panel getPreviousPanel() {
        currentPanel--;
        if (currentPanel < 0) {
            currentPanel = panelList.size() - 1;
        }
        
        return panelList.get(currentPanel);
    }
    
    private void setWelcomePanel() {
        Panel welcomePanel = new WelcomePanel();
        panelList.add(welcomePanel);
    }
    
    private void setMapPanel() {
        Panel mapPanel = new MapPanel();
        panelList.add(mapPanel);
    }
}
