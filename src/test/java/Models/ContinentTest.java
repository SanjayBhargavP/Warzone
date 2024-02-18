package Models;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.concordia.macs.Models.Continent;

/**
 * This class contains tests for the functionalities of the continent class.
 * @author Susmitha Mamula
 */


public class ContinentTest extends Continent {

    int d_continentId;
    String d_continentName;
    Continent d_Continent = new Continent();

    /**
     * Initialises the values before each test case execution
     * @throws Exception if initialisation fails
     */
    @Before
    public void setUp() throws Exception{
        d_continentId = 1;
        d_Continent.setD_continentId(d_continentId);
        d_continentName = "Asia";
        d_Continent.setD_continentName(d_continentName);
    }

    /**
     * Tests the continent Id functionality
     */
    @Test
    public void testContinentId(){
        int l_Id = d_Continent.getD_continentId();
        assertEquals(d_continentId, l_Id);
    }

    /**
     *
     * Tests the continent Name functionality
     */

    @Test
    public void testContinentName(){
        String l_Name = d_Continent.getD_continentName();
        assertEquals(d_continentName, l_Name);
    }

}
