/*
 Maxwell Hull, Dan Cavero, and Claire Li
 Apr 30, 2012
 Program to set and store table data 
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class MusicLibrary extends AbstractTableModel
{
    private PlayerFrame frame;
    private Document doc;
    private ArrayList<Song> songdata;
    private Element root;
    private List children;
    private String path;
    private String title;
    private String artist;
    private String album;
    private String length;
    private String year;
    private Song asong;
    
    
    public MusicLibrary(PlayerFrame aframe)
    {
        frame = aframe;

        if (frame.getCurrentList() == null)
        {
            songdata = new ArrayList<Song>();
        
               
        SAXBuilder builder = new SAXBuilder();
        
        try
        {
            String current = System.getProperty("user.dir");
            String opsys = System.getProperty("os.name");
            //System.out.println(current);
            //System.out.println(opsys);
            
            if (opsys.equals("Windows 7"))
            {
                doc = builder.build(new File(current + "\\src\\musicplayer.xml"));
            }
                else
                {
                    doc = builder.build(new File("./musicplayer.xml"));
                }

            root = doc.getRootElement();
            children = root.getChildren();
            
            for (Object child: children)
            {               
                 Element song = (Element) child;
                 path = song.getChildText("Path");
                 title = song.getChildText("Title");
                 artist = song.getChildText("Artist");
                 album = song.getChildText("Album");
                 length = song.getChildText("Length");
                 year = song.getChildText("Year");
                 asong = new Song(path, title, artist, album, length, year);
                 songdata.add(asong);
            }  
        }
        
        catch (Exception ex)
        {
            ex.printStackTrace();
        } 
        
        }
        
        else
        {
            songdata = frame.getCurrentList();
        }
    }
    
    public int getRowCount()
    {
        return songdata.size();
    }
    
    public int getColumnCount()
    {
        return 5;
    }
    
    public Object getValueAt(int row, int col)
    {

        if (col == 0)
            return songdata.get(row).getTitle();
        else
            if (col == 1)
                return songdata.get(row).getArtist();
            else
                if (col == 2)
                    return songdata.get(row).getAlbum();
                else 
                    if (col == 3)
                        return songdata.get(row).getLength();
                    else
                        if (col == 4)
                            return songdata.get(row).getYear();
                        else
                            return 0;

    }
    
    public String getColumnName(int col)
    {
        String[] cnames = {"TITLE", "ARTIST", "ALBUM", "LENGTH", "YEAR"};
        return cnames[col];
    }
    
    public ArrayList getSongs()
    {
        return songdata;
    }
}
