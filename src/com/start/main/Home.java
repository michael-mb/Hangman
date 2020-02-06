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
public class Home extends JPanel{

	
	private Chrono chrono;
	private Font fontIntro;
	private Font fontTitle ;
	
	private final String title =  "WELCOME TO HANGMAN";
	private final String description1 = "You have 7 moves to find the hidden word!";
	private final String description2 = " And if you succeed ... well you will be rewarded" ;
	
	private final int maxHeight = 520 ; 		
	private final int maxWidth = 700 ; 
	
	private Image logo;
	
	public Home() {		
		chrono = new Chrono(this);
		initScene();
	}
	
	private void initScene() {
		this.setBackground(Color.white);
		fontIntro = new Font("Arial", Font.BOLD, 16);
		fontTitle = new Font ("Arial" , Font.BOLD, 25);
		
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
		 g2.drawString(title, maxWidth - 515  , 60);
		 g.setFont(fontIntro);
		 g2.drawString(description1, maxWidth -505  , maxHeight - 100);
		 g2.drawString(description2, maxWidth -520  , maxHeight - 80);
		 g2.drawImage(logo, 150, 100, this.getWidth()- 305, this.getHeight()-200, this);
	}

	public void stop() {
		chrono.setRun(false);
	}
	
	public void play() {
		chrono = new Chrono(this);
	}
	
}
