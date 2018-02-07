package com.gamsionworks.chris.storyboardworkshop.gui.app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.JRootPane;

import com.gamsionworks.chris.storyboardworkshop.gui.StoryBoardWindow;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.Point;

public class PointComponent extends AppPanel {
	Point p;
	boolean showThumbnail = true;
	public static int count = 0;
	public static final Color bg = Color.WHITE;
	public static final Color defaultColor = new Color(180, 180, 180);
	public static final Color[] colors = { new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255),
			new Color(255, 255, 0), new Color(0, 255, 255), new Color(255, 0, 255), new Color(128, 0, 0),
			new Color(128, 128, 0), new Color(128, 0, 128), new Color(0, 128, 0), new Color(0, 128, 128),
			new Color(0, 0, 128) };

	public PointComponent(Point p) {
		super(AppShape.FILLED_ELLIPSE);
		Color c;
		if (count < colors.length) {
			c = colors[count];
			count++;
		} else {
			c = defaultColor;
			count = 0;
		}

		this.setColor(c, bg);
		this.p = p;
		this.addMouseInputAdapterListener(new PointHandler());
	}

	public class PointHandler extends ItemImageHandler {
		@Override
		public void mouseClicked(MouseEvent e) {
			
			Component component = e.getComponent();
			if (component.getParent() instanceof JLayeredPane && component.getParent().getParent() != null) {
				if (component.getParent().getParent() instanceof JRootPane
						&& component.getParent().getParent().getParent() != null) {
					if (component.getParent().getParent().getParent() instanceof StoryBoardWindow) {
						StoryBoardWindow sbw = (StoryBoardWindow) PointComponent.this.getParent().getParent()
								.getParent();
						sbw.setActiveWindow(false);
						if (component instanceof PointComponent) {
							((PointComponent) component).p.edit(sbw, sbw.getStoryBoard());
						}
					}
				}

			} else {
				System.err.printf(
						"Warning: parent of component %s is not an instanceof StoryBoardWindow! Original window is not deactivated.\n",
						PointComponent.this.getName());
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Using algebra, find top left of circle by using circle formula assuming y=-x,
		// and h=k
		if (showThumbnail && p.getThumbnail() != null) {
			if (this.shape == AppShape.FILLED_ELLIPSE || this.shape == AppShape.HOLLOW_ELLIPSE) {
				double h = this.getWidth();
				double r = h / 2;
				double x = h - ((int) (r / Math.sqrt(2) + r));
				double ex = 2 * r - 2 * x;
				g.setColor(Color.WHITE);
				g.drawImage(p.getThumbnail(), (int) x, (int) x, (int) ex, (int) ex, null);
			} else {

			}
		}

	}

	private static final long serialVersionUID = 1L;

}
