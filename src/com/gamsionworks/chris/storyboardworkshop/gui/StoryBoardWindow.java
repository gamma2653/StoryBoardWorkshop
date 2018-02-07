package com.gamsionworks.chris.storyboardworkshop.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.event.MouseInputAdapter;

import com.gamsionworks.chris.storyboardworkshop.storyboard.StoryBoard;

public class StoryBoardWindow extends JFrame {
	protected static final boolean START = true;
	protected static final boolean END = false;
	public StoryBoard sb = new StoryBoard(this);
	protected static final long serialVersionUID = 1L;
	protected Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Hotbar hotbar;
	protected JLayeredPane layeredPane;

	public StoryBoardWindow(String name) {
		super(name);
		this.setup();
		// checkStartEnd();
	}

	private void setup() {
		layeredPane = this.getLayeredPane();
		this.setPreferredSize(new Dimension((int) (((double) (toolkit.getScreenSize().width)) / 1.2),
				(int) (((double) (toolkit.getScreenSize().height)) / 1.2)));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.getContentPane().setBackground(Color.WHITE);
		layeredPane.setLayout(null);
		hotbar = new Hotbar(this);
		this.setJMenuBar(hotbar);
		this.getLayeredPane().setLayer(hotbar, new Integer(101));
		this.setVisible(true);
		BackgroundHandler bh = new BackgroundHandler();
		layeredPane.addMouseListener(bh);
		layeredPane.addMouseMotionListener(bh);
	}

	/**
	 * Dev-use only
	 */
	public StoryBoardWindow() {
		super("Development");
		this.setup();
	}

	protected void checkStartEnd() {
		if (sb.start == null) {
			this.setFocusableWindowState(false);
//			new StoryBoardPointCreation(this, sb, StoryBoardWindow.START);
		} else if (sb.end == null) {
			this.setFocusableWindowState(false);
//			new StoryBoardPointCreation(this, sb, StoryBoardWindow.END);
		}
	}

	public class BackgroundHandler extends MouseInputAdapter {
		MouseEvent pressed;
		Point location;

		@Override
		public void mousePressed(MouseEvent e) {
			this.pressed = e;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (e.getComponent() instanceof JLayeredPane) {
				JLayeredPane cont = ((JLayeredPane) e.getComponent());
				for (Component c : cont.getComponentsInLayer(JLayeredPane.PALETTE_LAYER)) {
					location = c.getLocation(location);
					int x = location.x + e.getX() - pressed.getX();
					int y = location.y + e.getY() - pressed.getY();
					c.setLocation(x, y);
					StoryBoardWindow.this.repaint();
				}
				pressed = e;
			}
		}
	}

	public void setActiveWindow(boolean flag) {
		this.setFocusableWindowState(flag);
		this.setEnabled(flag);
	}

	public Hotbar getHotbar() {
		return hotbar;
	}
	public StoryBoard getStoryBoard() {
		return sb;
	}

}
