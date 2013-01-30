/*
Maxwell Hull, Dan Cavero, and Claire Li
April 9, 2012
Program to run a Music Player
*/

import javax.swing.JFrame;

public class TestPlayer
{
    public static void main(String[] args)
    {
        PlayerFrame pframe = new PlayerFrame();
		
        pframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pframe.setVisible(true);
    }
}
