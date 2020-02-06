package com.start.component;

import javax.swing.JPanel;

public class Chrono implements Runnable {

	private JPanel panel ;
	private boolean run = true  ;
	private final int PAUSE = 100 ;
	
	public Chrono(JPanel panel) {
		this.panel = panel ;
		
		Thread thread = new Thread(this);
		thread.start();
	}
	@Override
	public void run() {
		
		while(run) {
			panel.repaint();
			
			try {
				Thread.sleep(PAUSE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean isRun() {
		return run ;
	}
	public void setRun(boolean run) {
		this.run = run ;
	}

}
