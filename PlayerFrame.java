/*
Maxwell Hull, Dan Cavero, and Claire Li
April 9, 2012
Program to implement a frame class for a Music Player
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

public class PlayerFrame extends JFrame
{
	private ControlsPanel ppanel1;
        private ProgressPanel ppanel2;
        private NowPlayingPanel ppanel3;
        private MusicLibrary mymusic;
        private XMLWriter xmlwrit;
        private JTable songtable;
        private File filename;
        private BasicController controls;
        private BasicPlayer player;
        private JButton pausebutton;
        private String path;
        private Integer row;
        private Integer alp;
        private ArrayList<Song> CurrentList;
        private PlayerFrame theFrame;
        private MusicLibrary Currentmusic;
        private Boolean newClick;
        private DeleteListener dlistener;
        private int deletearow;
        private int deleteacolumn;
        private int deletealp;
        private  JPopupMenu deletePopupMenu;
        private JMenuItem deleteItem;
                
	public PlayerFrame()
	{
		super();
                theFrame = this;

		this.setSize(800, 450);                      //to set size of frame
		this.setTitle("Music Player");               //to set Title of frame
                this.setLayout(new BorderLayout());
                
		ppanel1 = new ControlsPanel(this);
		this.add(ppanel1, BorderLayout.NORTH);
                
                ppanel2 = new ProgressPanel(this);
                this.add(ppanel2, BorderLayout.SOUTH);
                
                ppanel3 = new NowPlayingPanel(this);
                ppanel2.add(ppanel3, BorderLayout.NORTH);
                
                player = ppanel1.getBasicPlayer();
                controls = (BasicController)player;
                PlayerListener pl = new PlayerListener(this); 
                
                pausebutton = ppanel1.getPauseButton();
                
                mymusic = new MusicLibrary(this);
                xmlwrit = new XMLWriter(this);
                
                CurrentList = mymusic.getSongs();
                
                songtable = new JTable(mymusic);
                this.add(songtable, BorderLayout.CENTER);
                songtable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                songtable.setAutoCreateRowSorter(true);
                songtable.getSelectionModel().addListSelectionListener(new TableListener());
                songtable.setUpdateSelectionOnSort(false);
                JScrollPane scpane = new JScrollPane(songtable);
                this.add(scpane);
                
                JMenuBar menuBar = new JMenuBar();
                this.setJMenuBar(menuBar);                  //to set up the menu bar
                
                JMenu fileMenu = new JMenu("File");
                menuBar.add(fileMenu);                      //to set up the file menu
                
                JMenuItem imp = new JMenuItem("Import");
                fileMenu.add(imp);                          //for button to import songs
                ImportListener il = new ImportListener();
                imp.addActionListener(il);
                
                JMenuItem exit = new JMenuItem("Save on Exit");
                fileMenu.add(exit);                         //for button to close the program
                ExitListener close = new ExitListener();
                exit.addActionListener(close); 
                
                RightClickListener rightclick = new RightClickListener();
                songtable.addMouseListener(rightclick);
                dlistener = new DeleteListener();
        }
        
    public ControlsPanel getPPanel1()
    {
        return ppanel1;
    }
    
    public ProgressPanel getPPanel2()
    {
        return ppanel2;
    }
    
    public NowPlayingPanel getPPanel3()
    {
        return ppanel3;
    }
    
    public MusicLibrary getMusic()
    {
        return mymusic;
    }
        
    public File getFilename()
    {
        return filename;
    }         
    
    public void setFilename(File file)
    {
        filename = file;
    }
    
    public Integer getRow()
    {
        return row;
    }
    
    public Integer getArrayListPos(Integer rowNo)
    {
        return songtable.convertRowIndexToModel(rowNo);
    }
    
    public Integer getArrayPosition()
    {
        return alp;
    }
    
    public ArrayList<Song> getCurrentList()
    {
        return CurrentList;
    }
    
    public JTable getSongtable()
    {
        return songtable;
    }
    
    public Boolean getClicked()
    {
        return newClick;
    }
    
    public void setClicked(Boolean bool)
    {
        newClick = bool;
    }
    
    private class RightClickListener implements MouseListener
    {
        public RightClickListener()
        {                    
        }
        public void mouseClicked(MouseEvent e)
        {
         
        }
        public void mouseReleased(MouseEvent e)
        {
            
        }
        public void mouseEntered(MouseEvent e)
        {
            
        }
        public void mousePressed(MouseEvent e)
        {
          if (e.getButton() == MouseEvent.BUTTON3)
          {
            Point p = e.getPoint();
            deletearow = songtable.rowAtPoint(p);
            System.out.println(deletearow);
            deleteacolumn = songtable.columnAtPoint(p);
            deletePopupMenu = new JPopupMenu();
            deleteItem= new JMenuItem("Delete");
            deletePopupMenu.add(deleteItem);
            deletePopupMenu.show(e.getComponent(), e.getX(), e.getY());
            deletealp = songtable.convertRowIndexToModel(deletearow);
            deleteItem.addActionListener(dlistener);
          }          
        }
            
        public void mouseExited(MouseEvent e)
        {}
    }
        
    private class DeleteListener implements ActionListener
    {
         public DeleteListener()
         {}

         public void actionPerformed(ActionEvent ae)
         {
            CurrentList.remove(deletealp);
            System.out.println(CurrentList);
            Currentmusic = new MusicLibrary(theFrame);
            songtable.setModel(Currentmusic);  
         }
    }
    
    private class ImportListener implements ActionListener
    {
        public ImportListener()
        {}
        
        public void actionPerformed(ActionEvent ae)
        {
            JFileChooser chooser = new JFileChooser();
            String current = System.getProperty("user.dir");
            String opsys = System.getProperty("os.name");
            String filepath;            
            
            if (opsys.equals("Windows 7"))
            {
                chooser.setCurrentDirectory(new File(current + "\\src\\MP3s"));
            }
                else
                {
                    chooser.setCurrentDirectory(new File("./MP3s"));
                }
            
            int result = chooser.showOpenDialog(PlayerFrame.this);
            
            if(result == JFileChooser.APPROVE_OPTION) 
            { 
                filepath = chooser.getSelectedFile().getPath();
                filename = new File(filepath);
                System.out.println(filepath);
                
                try 
                {   
                    MP3File f = (MP3File)AudioFileIO.read(filename);
                    MP3AudioHeader audioheader = f.getMP3AudioHeader();
                    int Second = audioheader.getTrackLength();
                    System.out.println(Second);
                    
                    AudioFileFormat Mp3FileFormat = AudioSystem.getAudioFileFormat(filename);                                      
                    Map properties = Mp3FileFormat.properties();
                    String title = (String)properties.get("title");
                    String artist = (String)properties.get("author");
                    String album = (String)properties.get("album");
                    String year = (String)properties.get("date");                   
                    /*Integer total = (Integer)properties.get("mp3.length.frames");
                    Float FrameRate = (Float)properties.get("mp3.framerate.fps");
                    Integer Second = total/Math.round(FrameRate);*/
                    Integer a = Second/60;
                    Integer b = Second - a*60;
                    String timelength = "0";
                    System.out.println(timelength);

                    if (b<=9)
                    {
                        timelength = (String)"0"+a+":"+"0"+b;
                    }
                    else
                    {
                        timelength = (String)"0"+a+":"+b;
                    }

                    Song addedsong = new Song(filepath, title, artist, album, timelength, year);
                    CurrentList.add(addedsong);
                    Currentmusic = new MusicLibrary(theFrame);
                    songtable.setModel(Currentmusic);                    
                }
                 catch(Exception e) 
                 {
                    e.printStackTrace();
                 }               
             }
        }  
    }        
    
    private class ExitListener implements ActionListener
    {
        public ExitListener()
        {}
        
        public void actionPerformed(ActionEvent ae)
        {
            try
            {
                    xmlwrit.write();
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
            }
        }
    }
    
    private class TableListener implements ListSelectionListener
    {
        public TableListener()
        {}

        public void valueChanged(ListSelectionEvent e)
        {
            if (!e.getValueIsAdjusting())
            {   
                ppanel1.setStopToggle(false);
                newClick = true;
                
                row = songtable.getSelectedRow();
                alp = songtable.convertRowIndexToModel(row);
                System.out.println(row);
                System.out.println(alp);

                path = CurrentList.get(alp).getPath();

                filename = new File(path);

                System.out.println(filename);
                
                try
                {  
                    if (filename != null)
                    {      
                        controls.open(filename);
                        controls.play();
                        ppanel2.getLabel2().setText(CurrentList.get(alp).getLength());
                        pausebutton.setIcon(ppanel1.getPauseIcon());
                        ppanel1.setPauseToggle(true);
                        
                        if (ppanel1.getMute())
                        {
                            try
                            {
                                controls.setGain(0);
                                ppanel1.getVolumeBar().setValue(0);
                                ppanel1.getVolumeLabel().setText("0");
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
                                Integer value = ppanel1.getVolume();
                                ppanel1.getVolumeBar().setValue(value);
                                String str = Integer.toString(value);
                                ppanel1.getVolumeLabel().setText(str);                            
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
            }
        }
    }
}