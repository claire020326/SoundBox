/*
 Maxwell Hull, Dan Cavero, and Claire Li
 May 13, 2012
 Program to implement a panel class for "Now Playing" in Music Player. 
*/

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NowPlayingPanel extends JPanel 
{ 
    private PlayerFrame frame;
    private JLabel NowPlayingLabel;
    private JLabel textbox1;
    private JLabel textbox2;
    private JLabel textbox3;
    private JLabel textlabel1;
    private JLabel textlabel2;
    private JLabel textlabel3;
    
    public NowPlayingPanel(PlayerFrame aframe)
    {
	super();
        frame = aframe;
        setBackground(Color.WHITE);
        
        this.setPreferredSize(new Dimension(800,30));
        
        NowPlayingLabel = new JLabel();
        textlabel1 = new JLabel();
        textbox1 = new JLabel();
        textlabel2 = new JLabel();
        textbox2 = new JLabel();
        textlabel3 = new JLabel();
        textbox3 = new JLabel();
        
        this.add(NowPlayingLabel);
        this.add(textlabel1);
        this.add(textbox1);
        this.add(textlabel2);
        this.add(textbox2);
        this.add(textlabel3);
        this.add(textbox3);
    }
    
    public JLabel getText1()
    {
        return textbox1;
    }
    
    public JLabel getText2()
    {
        return textbox2;
    }
        
    public JLabel getText3()
    {
        return textbox3;
    }        
    
    public JLabel getLabel1()
    {
        return textlabel1;
    }
    
    public JLabel getLabel2()
    {
        return textlabel2;
    }
    
    public JLabel getLabel3()
    {
        return textlabel3;
    }
    
    public JLabel getNPLabel()
    {
        return NowPlayingLabel;
    }
}