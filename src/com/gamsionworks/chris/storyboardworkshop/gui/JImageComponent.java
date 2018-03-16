package com.gamsionworks.chris.storyboardworkshop.gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class JImageComponent extends JPanel {
	private static final long serialVersionUID = 1L;
	Image i;

	public JImageComponent(Image i) {
		this.i = i;
	}

	public void setImage(Image i) {
		this.i = i;
	}

	public Image getImage() {
		return i;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (i == null) {
			g.drawRect(0, 0, this.getWidth(), this.getHeight());
		} else {
			g.drawImage(i, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

}
