/*
 Maxwell Hull, Dan Cavero, and Claire Li
 May 8, 2012
 Program for writing to XML.
*/


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XMLWriter
{
    private File file;
    private Document doc;
    private ArrayList<Song> SongList; 
    private PlayerFrame frame;

    public XMLWriter(PlayerFrame aframe) 
    {
        frame = aframe;
        SongList = frame.getMusic().getSongs();
    }
    
    public void write() throws JDOMException, IOException
    {
        String current = System.getProperty("user.dir");
        file = new File(current  + "/src/musicplayer.xml");
        

        Element Root = new Element("MusicLibrary");
        doc = new Document(Root);

        for(Song song: SongList)
        {
            Element Songs = new Element("Song");
            
            Element Path = new Element("Path");
            Path.setText(song.getPath());
            Songs.addContent(Path);
            
            Element Title = new Element("Title");
            Title.setText(song.getTitle());
            Songs.addContent(Title);

            Element Artist = new Element("Artist");
            Artist.setText(song.getArtist());
            Songs.addContent(Artist);

            Element Album = new Element("Album");
            Album.setText(song.getAlbum());
            Songs.addContent(Album);

            Element Length = new Element("Length");
            Length.setText(song.getLength());
            Songs.addContent(Length);

            Element Year = new Element("Year");
            Year.setText(song.getYear());
            Songs.addContent(Year);
            
            Root.addContent(Songs);
        }
        
        try
        {
          XMLOutputter serializer = new XMLOutputter();
          serializer.output(doc, new FileWriter(file));
          serializer.setFormat(Format.getPrettyFormat());
        }
        catch (IOException e)
        {
          System.err.println(e);
        }

        System.exit(0);
    }
}