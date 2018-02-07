package com.gamsionworks.chris.storyboardworkshop.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.gamsionworks.chris.storyboardworkshop.storyboard.StoryBoard;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.AppMaterial;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.Arc;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.Segment;

public class StoryBoardArcCreation extends JFrame {

	protected static final long serialVersionUID = 1L;
	protected Toolkit toolkit = Toolkit.getDefaultToolkit();
	SpringLayout layout = new SpringLayout();
	JTextField name = new JTextField(30);
	JTextArea description = new JTextArea(20, 30);

	JComboBox<Segment> point1 = new JComboBox<Segment>();
	JComboBox<Segment> point2 = new JComboBox<Segment>();

	JButton create = new JButton("Create");
	JButton cancel = new JButton("Cancel");
	
	private static final Segment NONE = new Segment("None", null, null, null, null);
	
	StoryBoard sb;
	StoryBoardWindow sbw;
	private void setup() {
		this.setPreferredSize(new Dimension(toolkit.getScreenSize().width / 2,
				(int) (((double) toolkit.getScreenSize().height) / 1.5)));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.pack();
		this.setLocationRelativeTo(null);

		this.setLayout(layout);
		
		this.getRootPane().setDefaultButton(create);
		
		point1.setEditable(false);
		point2.setEditable(false);
		setSegmentSelect();
		JScrollPane scrolling = new JScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JLabel nameLabel = new JLabel("Name: ");
		this.add(nameLabel);
		this.add(name);

		JLabel descriptionLabel = new JLabel("Description: ");
		this.add(descriptionLabel);
		this.add(scrolling);

		this.add(point1);
		this.add(point2);

		this.add(create);
		this.add(cancel);

		// name label
		layout.putConstraint(SpringLayout.WEST, nameLabel, 5, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 5, SpringLayout.NORTH, this.getContentPane());
		// name
		layout.putConstraint(SpringLayout.WEST, name, 5, SpringLayout.EAST, nameLabel);
		layout.putConstraint(SpringLayout.NORTH, name, 0, SpringLayout.NORTH, nameLabel);
		layout.putConstraint(SpringLayout.EAST, name, -5, SpringLayout.EAST, this.getContentPane());

		// description label
		layout.putConstraint(SpringLayout.WEST, descriptionLabel, 5, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, descriptionLabel, 5, SpringLayout.SOUTH, nameLabel);
		// description
		layout.putConstraint(SpringLayout.WEST, scrolling, 5, SpringLayout.EAST, descriptionLabel);
		layout.putConstraint(SpringLayout.NORTH, scrolling, 5, SpringLayout.NORTH, descriptionLabel);
		layout.putConstraint(SpringLayout.EAST, scrolling, -5, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, scrolling, -50, SpringLayout.NORTH, create);

		// point1
		layout.putConstraint(SpringLayout.WEST, point1, 50, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, point1, 15, SpringLayout.SOUTH, scrolling);
		layout.putConstraint(SpringLayout.EAST, point1, -20, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, point1, -10, SpringLayout.NORTH, create);

		// point2
		layout.putConstraint(SpringLayout.EAST, point2, -30, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, point2, 15, SpringLayout.SOUTH, scrolling);
		layout.putConstraint(SpringLayout.WEST, point2, 20, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, point2, -10, SpringLayout.NORTH, create);

		// create
		layout.putConstraint(SpringLayout.WEST, create, 15, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, create, -45, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, create, 115, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, create, -15, SpringLayout.SOUTH, this.getContentPane());
		// cancel
		layout.putConstraint(SpringLayout.WEST, cancel, -115, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, cancel, -45, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, cancel, -15, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, cancel, -15, SpringLayout.SOUTH, this.getContentPane());

		point1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object p1 = point1.getSelectedItem();
				Object p2 = point2.getSelectedItem();
				setSegmentSelect();
				if(p1 == p2) {
					p2=NONE;
				}
				point1.setSelectedItem(p1);
				point2.setSelectedItem(p2);
				
			}
			
		});
		point2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object p1 = point1.getSelectedItem();
				Object p2 = point2.getSelectedItem();
				setSegmentSelect();
				if(p1 == p2) {
					p1=NONE;
				}
				point1.setSelectedItem(p1);
				point2.setSelectedItem(p2);
				
			}
			
		});
		
		
		
		this.setVisible(true);
	}

	private void setSegmentSelect() {
		point1.removeAllItems();
		point2.removeAllItems();
		for (AppMaterial item : sb.getAllItems()) {
			if (item instanceof Segment) {
				point1.addItem((Segment) item);
				point2.addItem((Segment) item);
			}
		}
		
		point1.addItem(NONE);
		point2.addItem(NONE);
	}

	public StoryBoardArcCreation(StoryBoardWindow sbw, StoryBoard sb) {
		super("Create Segment...");
		this.sbw = sbw;
		this.sb = sb;
		setup();

		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (point1.getSelectedItem() == point2.getSelectedItem()) {
					JOptionPane.showMessageDialog(sbw, "Please select different starting/ending points.");
					return;
				}
				sb.add(new Arc(name.getText(), description.getText(), ((Segment)point1.getSelectedItem()).getStart(), ((Segment)point2.getSelectedItem()).getEnd(), null, null));
				dispose();
			}

		});
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

			}

		});

	}
	@Override
	public void dispose() {
		sbw.setActiveWindow(true);
		super.dispose();
	}



}
