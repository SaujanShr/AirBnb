/**
 * Database storing a list of all airbnb locations, sorted into their respective boroughs.
 *
 * @author Saujan Shrestha
 * @version 1
 */

import java.util.ArrayList;
import java.util.HashMap;

public class AirbnbDatabase
{
    //Hashmap containing airbnb listings, seperated by their boroughs.
    private HashMap<String, ArrayList<AirbnbListing>> airbnbData;

    /**
     * Initialise the database of airbnb listings.
     */
    public AirbnbDatabase()
    {
        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        this.airbnbData = new HashMap<>();
        
        ArrayList<AirbnbListing> airbnbDataBuffer = dataLoader.load();
        
        // Convert the airbnb database from arraylist to hashmap of arraylists (more efficient to use).
        for (AirbnbListing airbnb : airbnbDataBuffer) {
            String borough = airbnb.getNeighbourhood();
            
            if (!airbnbData.containsKey(borough)) {
                airbnbData.put(borough, new ArrayList<AirbnbListing>());
            }
            airbnbData.get(borough).add(airbnb);
        }
    }
    
    /**
     * Get an array containing the minimum and maximum prices.
     * @return priceRange A 2 value array containing the minPrice and maxPrice.
     */
    public int[] getPriceRangeLimits() {
        // Select a random borough from the hashmap to get the initial values for minimum and maximum prices.
        ArrayList<AirbnbListing> airbnbListingArrayList = airbnbData.values().iterator().next();
        int minPrice = airbnbListingArrayList.get(0).getPrice();
        int maxPrice = minPrice;
        int roundingNumber = 1000;
        
        // Scans through all the listings in all the boroughs
        // Set minPrice and maxPrice to the highest and lowest values in the database
        for (ArrayList<AirbnbListing> borough : airbnbData.values()) {
            for (AirbnbListing listing : borough) {
                int listingPrice = listing.getPrice();
                
                if (listingPrice < minPrice) {
                    minPrice = listingPrice;
                }
                else if (listingPrice > maxPrice) {
                    maxPrice = listingPrice;
                }
            }
        }
        
        // Round minPrice down and maxPrice up to the nearest 1000
        minPrice = (minPrice / roundingNumber) * roundingNumber;
        maxPrice = ((maxPrice + (roundingNumber - 1)) / roundingNumber) * roundingNumber;
        
        int[] priceRange = {minPrice, maxPrice};
        return priceRange;
    }
    
    /**
     * Get the raw main airbnb database.
     * @return airbnbData The main airbnb database.
     */
    public HashMap<String, ArrayList<AirbnbListing>> getAirbnbData() {
        return airbnbData;
    }
    
    /**
     * Get all the properties in the main airbnb database within a certain price range.
     * @return 
     */
    public ArrayList<AirbnbListing> getAirbnbData(int minPrice, int maxPrice) {
        ArrayList<AirbnbListing> newAirbnbData = new ArrayList<>();
        
        // Adds property within the price range to the new arraylist
        for (ArrayList<AirbnbListing> borough : airbnbData.values()) {
            for (AirbnbListing property: borough) {
                int propertyPrice = property.getPrice();
                
                if (propertyPrice > minPrice && propertyPrice < maxPrice) {
                    newAirbnbData.add(property);
                }
            }
        }
        
        return newAirbnbData;
    }
    
    /**
     * Get all the properties from a certain borough.
     * @return
     */
    public ArrayList<AirbnbListing> getNeighbourhoodData(String borough) {
        return airbnbData.get(borough);
    }
    
    /**
     * Get all the propertyies from a certain borough within a certain price range.
     * @return
     */
    public ArrayList<AirbnbListing> getNeighbourhoodData(String borough, int minPrice, int maxPrice) {
        ArrayList<AirbnbListing> boroughData = airbnbData.get(borough);
        ArrayList<AirbnbListing> newAirbnbData = new ArrayList<>();
        
        // Adds property within the price range to the new arraylist.
        for (AirbnbListing listing : boroughData) {
            newAirbnbData.add(listing);
        }
        
        return newAirbnbData;
    }
}
