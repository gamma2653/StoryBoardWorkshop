package com.gamsionworks.chris.storyboardworkshop.gui.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.event.MouseInputAdapter;

import com.gamsionworks.chris.storyboardworkshop.gui.StoryBoardWindow;

public class AppPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected AppShape shape;
	private boolean dragging = false;
	Dimension maxSize, preferredSize, minSize;

	public AppPanel(AppShape shape, Color c, Color b) {
		this.setPreferredSize(new Dimension(75, 75));
		this.setSize(new Dimension(75, 75));
		this.setLayout(null);
		this.setColor(c, b);
		this.setShape(shape);

		// DEBUG
		// ItemImageHandler iih = new ItemImageHandler();
		// this.addMouseInputAdapterListener(iih);
	}

	protected AppPanel(AppShape shape) {
		this.setPreferredSize(new Dimension(75, 75));
		this.setSize(new Dimension(75, 75));
		this.setLayout(null);
		this.setShape(shape);

		// DEBUG
		// ItemImageHandler iih = new ItemImageHandler();
		// this.addMouseInputAdapterListener(iih);
	}

	public void setShape(AppShape shape) {
		this.shape = shape;
	}

	public class ItemImageHandler extends MouseInputAdapter {

		MouseEvent pressed;
		Point location;
		Point p1;
		Point p2;
		Dimension s1;
		Dimension s2;
		Rectangle r1 = new Rectangle(0, 0, 0, 0);
		Rectangle r2 = new Rectangle(0, 0, 0, 0);

		@Override
		public void mousePressed(MouseEvent arg0) {
			this.pressed = arg0;
			dragging = true;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Component component = e.getComponent();
			location = component.getLocation(location);
			int x = location.x - pressed.getX() + e.getX();
			int y = location.y - pressed.getY() + e.getY();
			int adjy = 0;
			StoryBoardWindow sbw = null;
			if (component.getParent() instanceof JLayeredPane && component.getParent().getParent() != null) {
				if (component.getParent().getParent() instanceof JRootPane
						&& component.getParent().getParent().getParent() != null) {
					if (component.getParent().getParent().getParent() instanceof StoryBoardWindow) {
						sbw = (StoryBoardWindow) component.getParent().getParent().getParent();
						adjy = sbw.getHotbar().getHeight();
					}
				}

			}
			x = x < 0 ? 0 : x;
			x = x > component.getParent().getWidth() - component.getWidth()
					? component.getParent().getWidth() - component.getWidth()
					: x;
			y = y < adjy ? adjy : y;
			y = y > component.getParent().getHeight() - component.getHeight()
					? component.getParent().getHeight() - component.getHeight()
					: y;
			if (p1 == null) {
				p1 = new Point(x, y);
			} else {
				p1.setLocation(x, y);
			}
			r1.setLocation(p1);
			s1 = component.getSize(s1);
			r1.setSize(s1);
			for (Component c : sbw.getLayeredPane().getComponentsInLayer(JLayeredPane.PALETTE_LAYER)) {
				if (c != component) {
					p2 = c.getLocation(p2);
					r2.setLocation(p2);
					s2 = component.getSize(s2);
					r2.setSize(s2);
					if (r1.intersects(r2)) {
						if (Math.abs(r1.getCenterX() - r2.getCenterX()) > Math.abs(r1.getCenterY() - r2.getCenterY())) {
							if (r2.getCenterX() < r1.getCenterX()) {
								x = p2.x + s2.width;
							} else {
								x = p2.x - s1.width;
							}
						} else {
							if (r2.getCenterY() < r1.getCenterY()) {
								y = p2.y + s2.height;
							} else {
								y = p2.y - s1.height;
							}
						}

					}
				}
			}

			component.setLocation(x, y);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			dragging = false;
			arg0.getComponent().repaint();
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Shape s;
		g2d.setStroke(new BasicStroke(5));

		switch (shape) {
		case FILLED_ELLIPSE:
			s = new Ellipse2D.Double(0, 0, this.getWidth(), this.getHeight());
			g2d.fill(s);
			break;
		case HOLLOW_ELLIPSE:
			s = new Ellipse2D.Double(0, 0, this.getWidth(), this.getHeight());
			g2d.draw(s);
			break;
		case FILLED_RECTANGLE:
			s = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
			g2d.fill(s);
			break;
		case HOLLOW_RECTANGLE:
			s = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
			g2d.draw(s);
			break;

		default:
			g.drawString("Error", 0, 0);

		}
		if (dragging) {
			Color oc = g.getColor();
			g.setColor(Color.BLACK);
			s = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
			g2d.draw(s);
			g.setColor(oc);
		}

	}

	public void addMouseInputAdapterListener(MouseInputAdapter mia) {
		this.addMouseListener(mia);
		this.addMouseMotionListener(mia);

	}

	public void setColor(Color color, Color background) {
		this.setForeground(color);
		this.setBackground(background);
	}

}