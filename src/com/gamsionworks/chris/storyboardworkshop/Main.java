package com.gamsionworks.chris.storyboardworkshop;

import javax.swing.SwingUtilities;

import com.gamsionworks.chris.storyboardworkshop.gui.StoryBoardWindow;

public class Main {
	public static void main(String[] args) {
		initialize();
	}
	
	public static void initialize() {
		openApp();
	}
	
	public static void openApp() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new StoryBoardWindow();

			}

		});
	}
}
