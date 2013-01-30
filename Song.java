/*
 Maxwell Hull, Dan Cavero, and Claire Li
 Apr 30, 2012
 Program to implement a song class.
 */

public class Song 
{
    private String path;
    private String title;
    private String length;
    private String artist;
    private String album;
    private String year;
    
    public Song(String filepath, String name, String art, String alb, String len, String yr)
    {
        path = filepath;
        title = name;
        artist = art;
        album = alb;
        length = len;
        year = yr;
    }
    
    public String getPath()
    {
        return path;
    }
    
    public String getTitle()
    {
        return title;
    }
    public String getArtist()
    {
        return artist;
    }
    
    public String getAlbum()
    {
        return album;
    }
    
    public String getLength()
    {
        return length;
    }
    
    public String getYear()
    {
        return year;
    }
    
}