/*
Maxwell Hull, Dan Cavero, and Claire Li
April 9, 2012
Program to implement a Player Listener class for a Music Player
*/

import java.io.File;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javazoom.jlgui.basicplayer.*;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

public class PlayerListener implements BasicPlayerListener
{
    private Long number;
    private Integer total;
    private Long time;
    private PlayerFrame frame;
    private JProgressBar progressbar;
    private JLabel label;
    private JLabel label2;
    private String song;
    private int SecondTotal = 1;
    private double dbtime = 0.0;
    private String Title;
    private String Artist;
    private String Album;
    private File filename;
    private PlayerListener pl;
    private JTable songtable;
    private Integer row;
    private Integer alp;
    private Integer ArrayPosition;
    private int ShuffledALP = 0;

    public PlayerListener(PlayerFrame aframe)
    {        
        frame = aframe;
    }
    
    /**
     * Open callback, stream is ready to play.
     *
     * properties map includes audio format dependant features such as
     * bitrate, duration, frequency, channels, number of frames, vbr flag,
     * id3v2/id3v1 (for MP3 only), comments (for Ogg Vorbis), ... 
     *
     * @param stream could be File, URL or InputStream
     * @param properties audio stream properties.
     */
    
    public void opened(Object stream, Map properties)
    {
        // Pay attention to properties. It's useful to get duration, 
        // bitrate, channels, even tag such as ID3v2.
        System.out.println("OPENED"+properties);
        //total = (Integer) properties.get("mp3.length.frames");
        song = (String) properties.get("title");
        label = frame.getPPanel2().getLabel1();
        label2 = frame.getPPanel2().getLabel2();
        progressbar = frame.getPPanel2().getProgress();
        try
        {
            if (frame.getFilename() != null)
            {
                MP3File f = (MP3File)AudioFileIO.read(frame.getFilename());
                MP3AudioHeader audioheader = f.getMP3AudioHeader();
                SecondTotal = audioheader.getTrackLength();
            }
            else
            {
                MP3File f = (MP3File)AudioFileIO.read(frame.getPPanel1().getFilename());
                MP3AudioHeader audioheader = f.getMP3AudioHeader();
                SecondTotal = audioheader.getTrackLength();
            }
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
        
        progressbar.setMaximum(100);
        
        Title = (String)properties.get("title");       
        Artist = (String)properties.get("author");
        Album = (String)properties.get("album");
        ArrayPosition = alp;
    }
    
    public Integer getArrayPosition()
    {
        return ArrayPosition;
    }

    /**
        * Progress callback while playing.
        * 
        * This method is called severals time per seconds while playing.
        * properties map includes audio format features such as
        * instant bitrate, microseconds position, current frame number, ... 
        * 
        * @param bytesread from encoded stream.
        * @param microseconds elapsed (<b>reseted after a seek !</b>).
        * @param pcmdata PCM samples.
        * @param properties audio stream parameters.
        */
    public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties)
    {
        // Pay attention to properties. It depends on underlying JavaSound SPI
        // MP3SPI provides mp3.equalizer.
        System.out.println("PROGRESS"+properties);
        number = (Long) properties.get("mp3.frame");                  
        time = (Long) properties.get("mp3.position.microseconds");
        dbtime = (double) time/1000000;      //number of seconds elapsed
        System.out.println("The song playing is " + '"' + song + '"' + ".");
        
        frame.getPPanel3().getText1().setText('"' + Title + '"' + "  ");
        frame.getPPanel3().getText2().setText(Artist + "  ");
        frame.getPPanel3().getText3().setText(Album + "  ");
        frame.getPPanel3().getLabel1().setText("Title:");
        frame.getPPanel3().getLabel2().setText("Artist:");
        frame.getPPanel3().getLabel3().setText("Album:");
        frame.getPPanel3().getNPLabel().setText("Now Playing--");
        
        //double dbnum = (double) number;
        progressbar.setValue((int)(dbtime*100)/SecondTotal);
        progressbar.setStringPainted(true);
        
        Integer progmin = (int) dbtime/60;
        Integer progsec = (int) (((dbtime/60)-progmin)*60);

        if (progsec < 10)
        {    
            label.setText(progmin + ":0" + progsec);
        }
        else 
        {
            label.setText(progmin + ":" + progsec);
        }
    }
        
    /**
        * Notification callback for basicplayer events such as opened, eom ...
        *  
        * @param event
        */
    
    public void stateUpdated(BasicPlayerEvent event)
    {
        // Notification of BasicPlayer states (opened, playing, end of media, ...)
        System.out.println("UPDATE:"+event);

        if (event.getCode()==BasicPlayerEvent.STOPPED)
        {            
            Boolean stopped = frame.getPPanel1().getStopped();            
            
            if (stopped)
            {
                progressbar.setValue(100);
                progressbar.setStringPainted(true);
                label.setText("0:00");
                label2.setText("0:00");
                System.out.println("Song was manually stopped.");
            }
            else
            {
                
                BasicPlayer player = frame.getPPanel1().getBasicPlayer();
                BasicController controls = (BasicController)player;
                pl = new PlayerListener(frame);
                
                frame.getPPanel1().setStopToggle(false);
                int inttime = (int) dbtime;
                if (inttime==SecondTotal)
                {
                    if (! frame.getPPanel1().getShuffleToggle())
                    {
                        if (frame.getClicked())
                        {
                            row = frame.getRow();
                            alp = frame.getArrayListPos(row);

                            if (! frame.getPPanel1().getRepeatToggle())
                            { 
                                row = row + 1;
                                alp = frame.getArrayListPos(row);
                            }
                        }
                        else
                        {
                            if (! frame.getPPanel1().getRepeatToggle())
                            {   
                                row = row + 1;
                                alp = frame.getArrayListPos(row);
                            }
                        }

                        String path = frame.getCurrentList().get(alp).getPath();

                        filename = new File(path);
                        frame.setFilename(filename);

                        try
                        {  
                            if (filename != null)
                            {      
                                controls.open(filename);
                                controls.play();
                                frame.getPPanel2().getLabel2().setText(frame.getCurrentList().get(alp).getLength());
                                frame.getPPanel1().setPauseToggle(true);

                                if (frame.getPPanel1().getMute())
                                {
                                    try
                                    {
                                        controls.setGain(0);
                                        frame.getPPanel1().getVolumeBar().setValue(0);
                                        frame.getPPanel1().getVolumeLabel().setText("0");
                                    }             
                                    catch (BasicPlayerException bpe)
                                    {
                                        bpe.printStackTrace();
                                    }
                                }

                                else
                                {
                                    try
                                    {
                                        Integer value = frame.getPPanel1().getVolume();
                                        frame.getPPanel1().getVolumeBar().setValue(value);
                                        String str = Integer.toString(value);
                                        frame.getPPanel1().getVolumeLabel().setText(str);                            
                                        controls.setGain((double) value/100);
                                    }             
                                    catch (BasicPlayerException bpe)
                                    {
                                        bpe.printStackTrace();
                                    }
                                }

                                controls.setPan(0.0);

                            }
                        }
                        catch (BasicPlayerException bpe)
                        {
                            bpe.printStackTrace();
                        } 

                        frame.setClicked(false);
                    }
                }
                /*
                else
                {

                    if (! frame.getPPanel1().getRepeatToggle())
                    {   
                        ShuffledALP = ShuffledALP + 1;
                    }

                    String path = frame.getPPanel1().getNewList().get(ShuffledALP).getPath();

                    filename = new File(path);
                    frame.setFilename(filename);
                    
                    try
                    {  
                        if (filename != null)
                        {      
                            controls.open(filename);
                            controls.play();
                            frame.getPPanel2().getLabel2().setText(frame.getPPanel1().getNewList().get(ShuffledALP).getLength());
                            frame.getPPanel1().setPauseToggle(true);

                            if (frame.getPPanel1().getMute())
                            {
                                try
                                {
                                    controls.setGain(0);
                                    frame.getPPanel1().getVolumeBar().setValue(0);
                                    frame.getPPanel1().getVolumeLabel().setText("0");
                                }             
                                catch (BasicPlayerException bpe)
                                {
                                    bpe.printStackTrace();
                                }
                            }

                            else
                            {
                                try
                                {
                                    Integer value = frame.getPPanel1().getVolume();
                                    frame.getPPanel1().getVolumeBar().setValue(value);
                                    String str = Integer.toString(value);
                                    frame.getPPanel1().getVolumeLabel().setText(str);                            
                                    controls.setGain((double) value/100);
                                }             
                                catch (BasicPlayerException bpe)
                                {
                                    bpe.printStackTrace();
                                }
                            }

                            controls.setPan(0.0);

                        }
                    }
                    catch (BasicPlayerException bpe)
                    {
                        bpe.printStackTrace();
                    } 
                } */
            }               
        }
    }

    public void setController(BasicController controller)
    {
        System.out.println("SET CONTROLLER:"+controller);
    }
}