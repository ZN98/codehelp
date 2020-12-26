import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * This project implements a simple application. Properties from a fixed
 * file can be displayed. 
 * 
 * 
 * @author Michael Kölling and Josh Murphy
 * @version 1.0
 */
public class PropertyViewer
{    
    private PropertyViewerGUI gui;     // the Graphical User Interface
    private Portfolio portfolio;
    private int propertyIndex;
    private int propertyIndexMax;
    private int propertyViewed;

    private int currentPropertyValue;
    private int totalPropertyValue;
    private int averagePropertyValue;

    
    
    /**
     * Create a PropertyViewer and display its GUI on screen. Also, set the propIndex to 0 (the first property in the list) as we want that to appear upon opening the program.
     * Finalise the constructor by invoking the getCurrentProperty method, to display information in the GUI. 
     */
    public PropertyViewer()
    {
        gui = new PropertyViewerGUI(this);
        portfolio = new Portfolio("airbnb-london.csv");
        propertyViewed = 1;
        propertyIndex = 0;
        propertyIndexMax = portfolio.numberOfProperties() - 1;
        displayCurrentProperty();
        calcAveragePropertyPrice();
    }

    /**
     * Storing a property from the portfolio into an initial property object to be
     * passed through the showProperty and showID methods (which have a Property type parameter).
     */
    public void displayCurrentProperty(){
        Property temp = portfolio.getProperty(propertyIndex);
        gui.showProperty(temp);
        gui.showID(temp);
        gui.showFavourite(temp);
        gui.showStatistics(this);

    }

    /**
     *
     */
    public void nextProperty()
    {
        if(propertyIndex >= 0 && propertyIndex < propertyIndexMax){
            propertyIndex++;
            displayCurrentProperty();
            propertyViewed++;
            calcAveragePropertyPrice();
        }
    }

    /**
     * 
     */
    public void previousProperty()
    {
        if(propertyIndex > 0 && propertyIndex <= propertyIndexMax){
            propertyIndex--;
            displayCurrentProperty();
            propertyViewed++;
            calcAveragePropertyPrice();
        }
    }
    




    /**
     * 
     */
    public void toggleFavourite()
    {
        Property temp = portfolio.getProperty(propertyIndex);
        temp.toggleFavourite();
        displayCurrentProperty();
    }
    //----- methods for challenge tasks -----

    /**
     * This method opens the system's default internet browser
     * The Google maps page should show the current properties location on the map.
     */
    public void viewMap() throws Exception
    {
        Property temp = portfolio.getProperty(propertyIndex);
        double latitude = temp.getLatitude();
        double longitude = temp.getLongitude();

        URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
        java.awt.Desktop.getDesktop().browse(uri); 
    }

    private void calcAveragePropertyPrice()
    {
        Property temp = portfolio.getProperty(propertyIndex);
        currentPropertyValue = temp.getPrice();
        totalPropertyValue = totalPropertyValue + currentPropertyValue;
        averagePropertyValue = totalPropertyValue / getNumberOfPropertiesViewed();

    }
    /**
     * 
     */
    public int averagePropertyPrice()
    {
        return averagePropertyValue;
    }

    public int getNumberOfPropertiesViewed()
    {
        return propertyViewed;
    }
}
