package com.start.main;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Window extends JFrame{
	
	private final int windowsWidth = 700 ;
	private final int windowsHeight = 522 ;
	
	private Game game ;
	private Home home ;
	private Rule rules ;
	
	private JMenuBar menuBar;
	
	private JMenu mFile ;
	private JMenuItem mPlay ;
	private JMenuItem mQuit ;
	
	private JMenu mInfo ; 
	private JMenuItem mRules;
	private JMenuItem mHome;
	
	private ButtonGroup dictionaryGroup ; 
	private JMenu mDictionary;
	private JRadioButtonMenuItem mEnglish;
	private JRadioButtonMenuItem mGerman;
	private JRadioButtonMenuItem mFrench;
	
	private char tippedChar ;

	public Window() {

		initParts();
		initWindow();
		initMenu();
		initListener();
		
		this.setVisible(true);
	}
	
	
	private void initParts() {
		
		home = new Home();
		game = new Game();
		rules = new Rule();
		
		menuBar = new JMenuBar();
		mFile = new JMenu("File");
		mPlay = new JMenuItem("Play");
		mQuit = new JMenuItem("Quit");
		
		mInfo = new JMenu("Infos");
		mHome = new JMenuItem("Home");
		mRules = new JMenuItem("?");
		
		mDictionary = new JMenu("Dictionary");
		dictionaryGroup = new ButtonGroup();
		mEnglish = new JRadioButtonMenuItem("English");
		mGerman = new JRadioButtonMenuItem("German");
		mFrench = new JRadioButtonMenuItem("French");
		
		
	}
	
	private void initMenu() {
		mQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
		
		mFile.add(mPlay);
		mFile.add(mQuit);
		mInfo.add(mHome);
		mInfo.add(mRules);
		
		dictionaryGroup.add(mEnglish);
		dictionaryGroup.add(mGerman);
		dictionaryGroup.add(mFrench);
		
		mEnglish.setSelected(true);
		
		mDictionary.add(mEnglish);
		mDictionary.add(mGerman);
		mDictionary.add(mFrench);
		
		menuBar.add(mFile);
		menuBar.add(mInfo);
		menuBar.add(mDictionary);
		this.setJMenuBar(menuBar);
	}
	private void initWindow() {
	
		this.setTitle("Hangman");
		this.setSize(windowsWidth,windowsHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		this.setLocationRelativeTo(null);
		this.add(home, BorderLayout.CENTER);
		
	}
	
	private void changePanel(JPanel panel) {
		
		if(panel == home) {
			game.stop();
			rules.stop();
			home.play();
		}
		else if (panel == game) {
			home.stop();
			rules.stop();
			game.play();
		}
		else {
			home.stop();
			game.stop();
			rules.play();
		}
		
		this.getContentPane().removeAll();
		this.add(panel , BorderLayout.CENTER);
		this.getContentPane().invalidate();
		this.getContentPane().validate();
		
	}
	private void initListener() {
		
		KeyboardListener keyboardListener = new KeyboardListener();
		this.addKeyListener(keyboardListener);
		
		mQuit.addActionListener(new ActionListener(){
		     public void actionPerformed(ActionEvent event){
		          System.exit(0);
		        }
		      });
		mPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				changePanel(game);
			}
		});
		mRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				changePanel(rules);
			}
		});
		mHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				changePanel(home);
			}
		});
		
		/*
		 	0 = English ; 
		 	1 = German ; 
		 	2 = French ;
		 */
		mEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.setDictionary(0);
			}
		});
		
		mGerman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.setDictionary(1);
			}
		});
		
		mFrench.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.setDictionary(2);
			}
		});
	}
	
	class KeyboardListener implements KeyListener{

	    public void keyPressed(KeyEvent event) {
	    }

	    public void keyReleased(KeyEvent event) {
	    	if(event.getKeyCode() != 10) {
	    		tippedChar = event.getKeyChar();
	    	}
	    	else {
	    		game.tippedCharacter(tippedChar);
	    		tippedChar = 'x';
	    	}
	    	pause();                
	    }

	    public void keyTyped(KeyEvent event) {}   	
	  }
	
	 private void pause(){
		    try {
		      Thread.sleep(10);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    }
		  } 
	  
}
