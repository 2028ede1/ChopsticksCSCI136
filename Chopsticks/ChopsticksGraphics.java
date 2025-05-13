package Chopsticks; 

import java.util.ArrayList;
import java.util.Scanner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.image.BufferedImage;
import javax.swing.JFrame;

//Going to Go to Office Hours to talk about Approach With TA


//My initial attempt to understanding the JFrame... 

public class ChopsticksGraphics extends JFrame {

	public static final Color BackgroundColor = Color.WHITE; 
	public static final Color BoundaryColor = Color.BLACK; 
	public static final int DisplayWidth = 1000; 
	public static final int DisplayHeight = 1000; 
	public BufferedImage bi = new BufferedImage(DisplayWidth, DisplayHeight, BufferedImage.TYPE_INT_RGB);  

	//Main Class Constructor Method
	public void ChopsticksGraphics(){

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setSize(1000,1000); 
		setVisible(true); 

	}

	public void paint(Graphics g) { 
		Graphics2D g2d = (Graphics2D) bi.getGraphics(); 
		g2d.setPaint(BackgroundColor); 
		g.drawImage(bi,0,0,null); 
	}




	public static void main(String[] args){
		System.out.println("Hello world!"); 
		ChopsticksGraphics cg = new ChopsticksGraphics(); 
	}

}