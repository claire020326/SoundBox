/*
 Maxwell Hull, Dan Cavero, and Claire Li
 Apr 15, 2012
 Program to implement a panel class for a JProgressBar in Music Player.
*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;


public class ProgressPanel extends JPanel 
{ 
    private PlayerFrame frame;
    private JProgressBar progress;
    private JLabel label1;
    private JLabel label2; 
    
    public ProgressPanel(PlayerFrame aframe)
    {
	super();
        frame = aframe;
        setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        
        label1 = new JLabel("0:00");
        this.add(label1, BorderLayout.WEST);        

        progress = new JProgressBar();
        this.add(progress, BorderLayout.CENTER);
        
        label2 = new JLabel("0:00");
        this.add(label2, BorderLayout.EAST);       
    }
    
    public JLabel getLabel1()
    {
        return label1;
    }
    
    public JLabel getLabel2()
    {
        return label2;
    }
    
    public JProgressBar getProgress()
    {
        return progress;
    }
}
  
