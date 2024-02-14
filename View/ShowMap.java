import Models.Continent;
import Models.Country;
/**
 * Class ShowMap displays all continents and countries and their respective neighbors along with the army count
 */

Public class ShowMap
{
    /**
     * This method displays all the continets , its countries ,its neighbors and its army count
     */
    public static void showMap(Continent[] continents,Country[] countries)
    {
        if(continents.length==0)
        {
            System.out.println("Continents or Country does not exist");
            return;
        }

        // calculate total number of lines needed for map
        int noOfLines=0;
        for(Continent continent:continents)
        {
            noOfLines+=Math.max(continent.getD_countries().length,1);
        }

    }
}