package com.start.main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.start.component.Chrono;


@SuppressWarnings("serial")
public class Game extends JPanel{

	
	private Chrono chrono;
	
	private String line1 = "Number of words found: -----> 0" ;
	private String line2 = "Your current score is: ---------> 100" ;
	private char[] currentWordArray ;
	private String tippedWord = "" ;
	private String toFindWord ; 
	
	private Font fontText ;
	private Font fontHidden ;
	private Font fontTitle ;
	
	private Image currentImage ;
	private String currentImagePath ;
	
	private int guesses ;
	private int score ; 
	private int findWords ; 
	private int totalScore;
	
	private String playerName ;
	private boolean hallOfFame = false ;
	private boolean oneTimeUpdate = true ;
	
	private String dictionaryPath = "files/dictionary.txt";
	
	public Game() {		
		chrono = new Chrono(this);
		initScene();
		initWords();
		this.stop();
	}
	
	private void initScene() {
		this.setBackground(Color.white);
		fontTitle = new Font ("Arial" , Font.BOLD, 25);
		fontText = new Font("Arial", Font.BOLD, 16);
		currentImagePath = "/images/level1.jpg";
		fontHidden = new Font("Arial", Font.BOLD, 35);
		try {
			toFindWord = FindRandomWord();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		guesses = 1 ;
		totalScore = 0 ;
	}
	
	private void initWords() {
		char[] toFindWordArray = toFindWord.toCharArray();
		currentWordArray = new char[toFindWordArray.length];
		
		int i = 0 ;
		for (@SuppressWarnings("unused") char c : toFindWordArray) {
			currentWordArray[i] = '*';
			i++;
		}
	}
	
	public void paintComponent(Graphics g){
		 super.paintComponent(g);
		 Graphics2D g2 = (Graphics2D)g;	
		 
		 if(!hallOfFame) {
		
		 g.setFont(fontText);
		 g2.drawString(line1, 20  , 50);
		 g2.drawString(line2, 20  , 80);
		 
		 try {
				currentImage = ImageIO.read(getClass().getResource(currentImagePath));
			    } catch (IOException e) {
			      e.printStackTrace();
			 }
		 g2.drawImage(currentImage, 360, 15, this.getWidth()- 455, this.getHeight()-300, this);
		 
	
		 g2.drawString("To find Word: " , 120 , 265);
		 g.setFont(fontHidden);
		 g.setColor(Color.BLUE);
		 g2.drawString(arrayCharToString(currentWordArray), 230 , 270);
		 g.setColor(Color.RED);
		 g2.drawString(tippedWord, 230 , 330);
		 
		 }else {
			 g.setFont(fontTitle);
			 g2.drawString("HALL OF FAME", 255 , 60);
			 
			 int height = 125; 
			 g.setFont(fontText);
			 for(String str : readHallOfFame()) {
				 g2.drawString(str, 75  , height);
				 height += 20 ;
			 }
		 }
	}
	
	private String arrayCharToString(char[] charArray) {
		String str = "";
		for(char c : charArray) {
			str += c ;
		}
		return str ;
	}
	public void tippedCharacter(char character) {
		tippedWord += character ;
		updateView(character);
	}
	
	public void updateView(char character) {
		int i = 0 ;
		boolean characterFound = false ;
		
		
		// Actualise word if character is found 
		for(char c : toFindWord.toCharArray()) {
			if(c == character) {
				characterFound = true ; 
				currentWordArray [i] = c ;
			}
			i++ ;
		}
			
		if(characterFound == false && guesses <= 8 ) {
			guesses ++ ;
		}
		// Actualise score
		switch (guesses) {
			case 1:
				score = 100;
				break;
			case 2:
				score = 50;
				break;
			case 3:
				score = 35;
				break;
			case 4:
				score = 25;
				break;
			case 5:
				score = 15;
				break;
			case 6:
				score = 10;
				break;
			case 7:
				score = 5;
				break;
		default:
			score = 0;
		}
		
		
		// Actualise image
		if(guesses <=8) {
			currentImagePath = "/images/level"+guesses+".jpg";
		}else {
			currentImagePath = "/images/level8.jpg";
		}
		
		if(isWordFound()) {
			findWords ++ ;
			tippedWord="";
			try {
				toFindWord = validateWord(FindRandomWord());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initWords();
			totalScore += score ;
			guesses = 1 ;
		};
		
		line1 = "Number of words found: -----> "+findWords ;
		line2 = "Your current score is: ---------> "+score ;
		
		if(!isWordFound() && guesses > 8) {
			if(oneTimeUpdate) { // to execute this code one time ! 
				tippedWord = "** " +toFindWord+" **" ;
				JOptionPane.showMessageDialog(null,
						"Your number of chances is exhausted. "
						+ "Number of words found: ** "+findWords+" **"
						+ " and your score is: ** "+totalScore+ " **",
						"Information", JOptionPane.INFORMATION_MESSAGE);
				playerName = JOptionPane.showInputDialog(null, "Veuillez décliner votre identité !", "Gendarmerie nationale !", JOptionPane.QUESTION_MESSAGE);
				hallOfFame = true ;
				writeInHallOfFame();
				oneTimeUpdate = false ;
			}
			
		}
	}
	
	private boolean isWordFound() {
		
		for(char c : currentWordArray) {
			if(c == '*')
				return false;
		}	
			return true;
	}
	
	
	private String validateWord(String word) {
		String str = "";
		str = word.strip();
		str = str.toLowerCase();
		return str ;
	}
	
	private String FindRandomWord() throws FileNotFoundException {
		File file= new File(dictionaryPath);
	    Scanner lecture = new Scanner(file);
	    Random rand = new Random();
	    int number = 0 ;
	    String  word = "";
	    while(lecture.hasNextLine()){
	    	lecture.next();
	    	number ++ ;
	    }
	    lecture.close();

	    lecture = new Scanner(file);
	    int random = rand.nextInt(number);
	    
	    for(int i = 0 ; i< random ; i++) {
			word = lecture.nextLine();
		}
	    
	    lecture.close();
	    word = validateWord(word);
	     return word;
	}
	
	// used to write the score in the file at the end of the game
	private void writeInHallOfFame() {
		File file= new File("files/hallOfFame.txt");
		String str = playerName + " with a score of : "+totalScore+" | number of words found : "+ findWords + "; \n";
		try {
			Files.write(file.toPath(), str.getBytes(Charset.defaultCharset()) , StandardOpenOption.APPEND );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	private List<String> readHallOfFame() {
		
		Path path = Paths.get("files/hallOfFame.txt");
		List<String> str = null;
		try {
			str = Files.readAllLines(path, Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str ;
	}
		
	public void stop() {
		chrono.setRun(false);
	}
	
	public void play() {
		resetAll();
		chrono = new Chrono(this);
	}
	
	public void setDictionary(int dictionary) {
		
		switch (dictionary) {
			case 1:
				dictionaryPath = "files/dictionary-german.txt";
				break;
			case 2:
				dictionaryPath = "files/dictionary-french.txt";
				break;
	
			default:
				dictionaryPath = "files/dictionary.txt";
		}
		
	}
	
	public void resetAll() {
		line1 = "Number of words found: -----> 0";
		line2 = "Your current score is: ---------> 100" ;
		tippedWord = "" ;
		guesses= 1 ;
		totalScore = 0 ;
		currentImagePath = "/images/level1.jpg";
		hallOfFame = false ;
		oneTimeUpdate = true;
		
		try {
			toFindWord = validateWord(FindRandomWord());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initWords();
	}
	
	
}
