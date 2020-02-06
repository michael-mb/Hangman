package com.start.main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.start.component.Chrono;


@SuppressWarnings("serial")
public class Rule extends JPanel{
		
	private final int maxWidth = 700 ; 
	
	private Chrono chrono;
	private final String title =  "HANGMAN GAME";
	
	private final String line1 =  "You have 7 moves to find the hidden word!";
	private final String line2 =  "And if you succeed ... well you will be rewarded";
	private final String linex =  "You can choose the language by clicking on: Dictionary";
	private final String line3 =  "POINTS COUNTS:";
	private final String line4 =  "Word found without error ..................... 100Pts";
	private final String line5 =  "Word found with 1 error ......................... 50Pts";
	private final String line6 =  "Word found with 2 error ......................... 35Pts";
	private final String line7 =  "Word found with 3 error ......................... 25Pts";
	private final String line8 =  "Word found with 4 error ......................... 15Pts";
	private final String line9 =  "Word found with 5 error ......................... 10Pts";
	private final String line10 = "Word found with 6 error ......................... 5Pts";
	private final String line11 = "I wish you a lot of fun ... :)";
	private final String line12 = "To play, you must enter a key on the keyboard and press Enter to validate.";
	private final String line13 = "My algorithm takes care of the rest :).";
	private Font fontTitle ;
	private Font fontText ;
	
	private Image logo;
	public Rule() {		
		chrono = new Chrono(this);
		this.stop();
		initScene();
	}
	
	private void initScene() {
		this.setBackground(Color.white);
		fontTitle = new Font ("Arial" , Font.BOLD, 25);
		fontText = new Font("Arial", Font.BOLD, 16);
		
		try {
			logo = ImageIO.read(getClass().getResource("/images/logo.jpg"));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	}
	
	public void paintComponent(Graphics g){
		 super.paintComponent(g);
		 Graphics2D g2 = (Graphics2D)g;
		 g.setFont(fontTitle);
		 g2.drawString(title, maxWidth - 470  , 40);
		 g.setFont(fontText);
		 g2.drawString(line1, maxWidth - 670  , 120);
		 g2.drawString(line2, maxWidth - 670  , 140);
		 g2.drawString(linex, maxWidth - 670  , 160);
		 g2.drawString(line3, maxWidth - 670  , 200);
		 
		 g2.drawString(line4, maxWidth - 600  , 240);
		 g2.drawString(line5, maxWidth - 600  , 260);
		 g2.drawString(line6, maxWidth - 600  , 280);
		 g2.drawString(line7, maxWidth - 600  , 300);
		 g2.drawString(line8, maxWidth - 600  , 320);
		 g2.drawString(line9, maxWidth - 600  , 340);
		 g2.drawString(line10,maxWidth - 600  , 360);
		 
		 g2.drawString(line12,maxWidth - 670 , 410);
		 g2.drawString(line13,maxWidth - 670 , 430);
		 g2.drawString(line11,maxWidth - 670  , 450);
		 
		 g2.drawImage(logo, this.getWidth()- 250, 190, this.getWidth()- 465, this.getHeight()-290, this);
		 
		 
	}
	
	public void stop() {
		chrono.setRun(false);
	}
	
	public void play() {
		chrono = new Chrono(this);
	}
	
	
}
