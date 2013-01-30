/*
Maxwell Hull, Dan Cavero, and Claire Li
April 9, 2012
Program to implement a panel class for buttons in Music Player.
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class ControlsPanel extends JPanel 
{ 
    private PlayerFrame frame;
    private Icon iconPlay;
    private Icon iconPause;
    private Icon iconStop;
    private Icon iconMute;
    private Icon iconUnmute;
    private Icon iconRepeat;
    private Icon iconUnrepeat;
    private Icon iconPrevious;
    private Icon iconNext;
    private Icon iconShuffleOn;
    private Icon iconShuffleOff;
    private JButton previousbutton;
    private JButton pauseResumebutton;
    private JButton stopbutton;
    private Boolean pauseToggle = true;
    private Boolean repeatToggle = false;
    private Boolean shuffleToggle = false;
    private Boolean stopped = false;
    private JButton repeatbutton;
    private JButton shufflebutton;
    private JSlider volumecontrol;
    private JButton mute;
    private Boolean isMute = false;
    private int value = 50;
    private int PrevValue;
    private JLabel label;
    private PlayerListener plistener;
    private BasicPlayer player;
    private BasicController controls;
    private Integer NewALP;
    private ArrayList<Song> newList;
    private File filename;
    
    public ControlsPanel(PlayerFrame aframe)
    {
	super();
        frame = aframe;
        setBackground(Color.WHITE);

        player = new BasicPlayer();
        controls = (BasicController)player;
        plistener = new PlayerListener(frame);
        player.addBasicPlayerListener(plistener);
        
        String current = System.getProperty("user.dir");
        String opsys = System.getProperty("os.name");;


        if (opsys.equals("Windows 7"))
        {
            iconPause = new ImageIcon(current + "\\src\\images\\pause.jpg");
            iconPlay = new ImageIcon(current + "\\src\\images\\play.jpg");
            iconStop = new ImageIcon(current + "\\src\\images\\stop.jpg");
            iconMute = new ImageIcon(current + "\\src\\images\\unmute.jpg");
            iconUnmute = new ImageIcon(current + "\\src\\images\\mute.jpg");
            iconRepeat = new ImageIcon(current + "\\src\\images\\repeatOff.jpg");
            iconUnrepeat = new ImageIcon(current + "\\src\\images\\repeatOn.jpg");
            iconPrevious = new ImageIcon(current + "\\src\\images\\previous.jpg");
            iconNext = new ImageIcon(current + "\\src\\images\\next.jpg");
            iconShuffleOn = new ImageIcon(current + "\\src\\images\\shuffleOff.jpg");
            iconShuffleOff = new ImageIcon(current + "\\src\\images\\shuffleOn.jpg");
        }
            else
            {
                iconPause = new ImageIcon("./images/pause.JPG");
                iconPlay = new ImageIcon("./images/play.JPG");
                iconStop = new ImageIcon("./images/stop.JPG");
                iconMute = new ImageIcon("./images/unmute.JPG");
                iconUnmute = new ImageIcon("./images/mute.JPG");
                iconRepeat = new ImageIcon("./images/repeatOff.JPG");
                iconUnrepeat = new ImageIcon("./images/repeatOn.jpg");
                iconPrevious = new ImageIcon("./images/previous.JPG");
                iconNext = new ImageIcon("./images/next.JPG");
                iconShuffleOn = new ImageIcon("./images/shuffleOff.JPG");
                iconShuffleOff = new ImageIcon("./images/shuffleOn.jpg");
            }
        
        previousbutton = new JButton(iconPrevious);
        previousbutton.setPreferredSize(new Dimension(46, 45));
        this.add(previousbutton);
        PreviousButtonListener pbl = new PreviousButtonListener();
        previousbutton.addActionListener(pbl);
        
        pauseResumebutton = new JButton(iconPause);
        pauseResumebutton.setPreferredSize(new Dimension(46, 45));
        this.add(pauseResumebutton);
        PauseButtonListener pRbl = new PauseButtonListener();
        pauseResumebutton.addActionListener(pRbl); 
        
        stopbutton = new JButton(iconStop);
        stopbutton.setPreferredSize(new Dimension(46, 45));
        this.add(stopbutton);
        StopButtonListener sbl = new StopButtonListener();
        stopbutton.addActionListener(sbl);
        
        volumecontrol = new JSlider(0,100);
        volumecontrol.setValue(50);
        label = new JLabel("50");
        this.add(volumecontrol);
        this.add(label);
        VolumeListener vl = new VolumeListener();
        volumecontrol.addChangeListener(vl);
        
        mute = new JButton(iconMute);
        mute.setPreferredSize(new Dimension(46, 45));
        this.add(mute);
        MuteListener ml = new MuteListener();
        mute.addActionListener(ml);
        
        repeatbutton = new JButton(iconRepeat);
        repeatbutton.setPreferredSize(new Dimension(46, 45));
        this.add(repeatbutton);
        RepeatButtonListener rbl = new RepeatButtonListener();
        repeatbutton.addActionListener(rbl); 
        /*
        shufflebutton = new JButton(iconShuffleOn);
        shufflebutton.setPreferredSize(new Dimension(46, 45));
        this.add(shufflebutton);
        ShuffleListener sl = new ShuffleListener();
        shufflebutton.addActionListener(sl); */     
    }
    
    public BasicPlayer getBasicPlayer()
    {
        return player;
    }
    
    public BasicController getControls()
    {
        return controls;
    }
    
    public JButton getPauseButton()
    {
        return pauseResumebutton;
    }
    
    public void setPauseToggle(Boolean paused)
    {
        if (paused)
            pauseToggle = true;
        else
            pauseToggle = false;
    }
    
    public Boolean getRepeatToggle()
    {
        return repeatToggle;
    }
    
    public Boolean getShuffleToggle()
    {
        return shuffleToggle;
    }
    
    public File getFilename()
    {
        return filename;
    }
    
    public Boolean getStopped()
    {
        return stopped;
    }
    
    public void setStopToggle(Boolean stoptoggle)
    {
        if (stoptoggle)
            stopped = true;
        else
            stopped = false;
    }
    
    public Icon getPauseIcon()
    {
        return iconPause;
    }
    
    public JSlider getVolumeBar()
    {
        return volumecontrol;
    }
    
    public Integer getVolume()
    {
        return value;
    }
    
    public JLabel getVolumeLabel()
    {
        return label;
    }
    
    public Boolean getMute()
    {
        return isMute;
    }
    
    public ArrayList<Song> getNewList()
    {
        return newList;
    }
    
    private class PreviousButtonListener implements ActionListener
    {
        public PreviousButtonListener()
        {}
        
        public void actionPerformed(ActionEvent ae)
        {   

            try
            {  
                if (plistener.getArrayPosition() != null)
                {
                    NewALP = plistener.getArrayPosition();
                }
                else
                {
                    NewALP = frame.getArrayPosition();
                }
                
                if (frame.getFilename() != null)
                {      
                    controls.open(frame.getFilename());
                    controls.play();
                    frame.getPPanel2().getLabel2().setText(frame.getCurrentList().get(NewALP).getLength());
                    pauseToggle = true;
                    pauseResumebutton.setIcon(iconPause);

                    if (isMute)
                    {
                        try
                        {
                            controls.setGain(0);
                            volumecontrol.setValue(0);
                            label.setText("0");
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
                            volumecontrol.setValue(value);
                            String str = Integer.toString(value);
                            label.setText(str);                            
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
    
    private class PauseButtonListener implements ActionListener
    {
        public PauseButtonListener()
        {}
        
        public void actionPerformed(ActionEvent ae)
        {
            try
            {
                if (pauseToggle)
                {
                    controls.pause();
                    pauseResumebutton.setIcon(iconPlay);
                    pauseToggle = false;
                }
                    
                else
                {
                    controls.resume();
                    pauseResumebutton.setIcon(iconPause);
                    pauseToggle = true;
                }
            }
            catch (BasicPlayerException bpe)
            {
                bpe.printStackTrace();
            }            
        }
    }
    
    private class StopButtonListener implements ActionListener
    {
        public StopButtonListener()
        {}
        
        public void actionPerformed(ActionEvent ae)
        {
            try
            {    
                stopped = true;
                controls.stop();
            }
            catch (BasicPlayerException bpe)
            {
                bpe.printStackTrace();
            }            
        }
    }
    
    private class MuteListener implements ActionListener
    {
        public MuteListener()
        {}
        
        public void actionPerformed(ActionEvent ae)
        {
            if (isMute != true)
            {
                PrevValue = value;
                try
                {
                    controls.setGain(0);
                    volumecontrol.setValue(0);
                    label.setText("0");
                }             
                catch (BasicPlayerException bpe)
                {
                    bpe.printStackTrace();
                }

                mute.setIcon(iconUnmute);
                isMute = true;
            }

            else
            {
                try
                {
                    controls.setGain((double)PrevValue/100);
                    volumecontrol.setValue(PrevValue);
                    
                }             
                catch (BasicPlayerException bpe)
                {
                    bpe.printStackTrace();
                }

                String str = Integer.toString(PrevValue);
                label.setText(str);

                mute.setIcon(iconMute);
                isMute = false;
            }       
        }
    }       
    
    public class VolumeListener implements ChangeListener
    {
         public void stateChanged(ChangeEvent ce)
         {
             value = volumecontrol.getValue();
             
             if (value != 0)
             {
                 mute.setIcon(iconMute);
                 isMute = false;
             }
             
             try
             {
                controls.setGain((double)value/100);
             }             
             catch (BasicPlayerException bpe)
             {
                bpe.printStackTrace();
             }
             
             String str = Integer.toString(value);
             label.setText(str);
         }   
    }
    
    private class RepeatButtonListener implements ActionListener
    {
        public RepeatButtonListener()
        {}
        
        public void actionPerformed(ActionEvent ae)
        {
            if (repeatToggle)
            {
                repeatbutton.setIcon(iconRepeat);
                repeatToggle = false;
            }

            else
            {
                repeatbutton.setIcon(iconUnrepeat);
                repeatToggle = true;
            }          
        }
    }
    
    private class ShuffleListener implements ActionListener
    {
        public ShuffleListener()
        {}
        
        public void actionPerformed(ActionEvent ae)
        {   
            if (shuffleToggle)
            {
                shufflebutton.setIcon(iconShuffleOn);
                shuffleToggle = false;
                try
                {
                    controls.stop();
                }
                catch (BasicPlayerException bpe)
                {
                    bpe.printStackTrace();
                }
                
            }

            else
            {
            shufflebutton.setIcon(iconShuffleOff);
            newList = frame.getCurrentList();
            Collections.shuffle(newList);
            try
            {  
                String path = newList.get(0).getPath();

                filename = new File(path);

                System.out.println(filename);
                
                if (filename != null)
                {      
                    controls.open(filename);
                    controls.play();
                    frame.getPPanel2().getLabel2().setText(newList.get(0).getLength());
                    pauseToggle = true;
                    pauseResumebutton.setIcon(iconPause);

                    if (isMute)
                    {
                        try
                        {
                            controls.setGain(0);
                            volumecontrol.setValue(0);
                            label.setText("0");
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
                            volumecontrol.setValue(value);
                            String str = Integer.toString(value);
                            label.setText(str);                            
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
            shuffleToggle = true;
            }
        }
    }
}
