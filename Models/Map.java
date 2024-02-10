Public class Map
{
    private String d_mapName;
    private boolean d_mapExists;

    /**
     * Default constructor for Map Class
     */
    public Map()
    {
        this.d_mapExists=False;
    }

    /**
     * Check if map exists
     * @return true or false
     */
    public boolean isD_mapExists()
    {
        return d_mapExists;
    }

    /**
     * Set if map exists
     * @param p_mapExists - new boolean value
     */
    public void setD_mapExists(boolean p_mapExists)
    {
        this.d_mapExists=p_mapExists;
    }

    /**
     * Gets the file name
     * @return file name
     */
    public String getD_mapName()
    {
        return d_mapName;
    }

    /**
     * Set file name
     * @param p_mapName - new file name
     */
    public void setD_mapName(String p_mapName)
    {
        this.d_mapName = p_mapName;
    }
}