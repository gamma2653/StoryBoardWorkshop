package com.gamsionworks.chris.storyboardworkshop.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSelecter extends JFrame{
	private static final long serialVersionUID = 1L;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public FileSelecter(String title, ActionListener al) {
		super(title);
		this.add(new FilePanel(al));
		this.setPreferredSize(new Dimension((int)(screenSize.width/2.5),(int)(screenSize.height/2)));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	public class FilePanel extends JFileChooser{
		private static final long serialVersionUID = 1L;
		public FilePanel(ActionListener al) {
			FileNameExtensionFilter exts = new FileNameExtensionFilter("JPGs and/or PNGs", "jpg","jpeg","png");
			this.setAcceptAllFileFilterUsed(false);
			this.setMultiSelectionEnabled(false);
			this.setFileFilter(exts);
			if(System.getProperty("user.home")!=null) {
				this.setCurrentDirectory(new File(System.getProperty("user.home")));
			}
			this.addActionListener(al);
		}
		public void dispose() {
			FileSelecter.this.dispose();
		}
		
		
	}
}
