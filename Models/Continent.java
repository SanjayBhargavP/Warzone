
/**
 *Continent class defines continentId,continentName
 */

public class Continent
{
    private int d_continentId;
    private String d_continentName;

    /**
     * Default constructor for Continent
     */
    public Continent()
    {

    }

    /**
     * Parametrized Constructor for Continent
     * Initalises a new continent with Id and name
     */
    public Continent(int p_continentID, String p_continentName)
    {
        this.d_continentId=p_continentID;
        this.d_continentName=p_continentName;
    }

    /**
     * Get Continent ID
     * @return continent ID
     */
    public int getD_continentId()
    {
        return d_continentId;
    }

    /**
     * Set Continent ID
     * @param p_continentId - new continentId
     */
    public void setD_continentId(int p_continentId)
    {
        this.d_continentId = p_continentId;
    }

    /**
     * Get Continent Name
     * @return Continent Name
     */
    public String getD_continentName()
    {
        return d_continentName;
    }



    /**
     * Set Continent Name
     * @param p_continentName - new Continent Name
     */
    public void setD_continentName(String p_continentName)
    {
        this.d_continentName = p_continentName;
    }
}