import javafx.scene.layout.Pane;

/**
 * Write a description of class Panel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Panel
{
    protected static AirbnbDatabase airbnbDatabase;
    protected static double minPrice = -1;
    protected static double maxPrice = -1;
    
    /**
     * Constructor for objects of class Panel
     */
    public Panel()
    {
    }
    
    public static void setAirbnbDatabase(AirbnbDatabase database) {
        airbnbDatabase = database;
    }
    
    abstract public Pane getPane();
    
    protected boolean validPrice() {
        if (maxPrice >= 0 && minPrice >= 0 && maxPrice < minPrice) return false;
        return true;
    }
    
    abstract public void fromBoxAction(double minPrice);
    
    abstract public void toBoxAction(double maxPrice);
    
    abstract public void reset();
    
}
