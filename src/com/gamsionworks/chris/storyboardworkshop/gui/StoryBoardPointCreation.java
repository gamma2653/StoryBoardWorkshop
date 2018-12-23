package com.gamsionworks.chris.storyboardworkshop.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.gamsionworks.chris.storyboardworkshop.gui.FileSelecter.FilePanel;
import com.gamsionworks.chris.storyboardworkshop.gui.app.PointComponent;
import com.gamsionworks.chris.storyboardworkshop.storyboard.StoryBoard;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.Point;
import com.gamsionworks.chris.storyboardworkshop.utility.ID;
import com.gamsionworks.chris.storyboardworkshop.utility.IDFactory;

public class StoryBoardPointCreation extends JFrame {

	protected static final long serialVersionUID = 1L;
	protected Toolkit toolkit = Toolkit.getDefaultToolkit();
	SpringLayout layout = new SpringLayout();
	JTextField name = new JTextField(30);
	JTextField id = new JTextField(30);
	JTextArea description = new JTextArea(20, 30);

	JButton create = new JButton("Create");
	JButton cancel = new JButton("Cancel");
	JButton attachImg = new JButton("Attach an Image");
	List<Image> capturedImgs = new ArrayList<Image>();
	StoryBoardWindow sbw;
	Point p;

	private void setup() {
		this.setPreferredSize(new Dimension(toolkit.getScreenSize().width / 2, toolkit.getScreenSize().height / 2));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(300, 220));

		this.setLayout(layout);

		this.getRootPane().setDefaultButton(create);
		JScrollPane scrolling = new JScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JLabel idLabel = new JLabel("ID: ");
		this.add(idLabel);
		this.add(id);
		id.setText(IDFactory.getUID().toString());

		JLabel nameLabel = new JLabel("Name: ");
		this.add(nameLabel);
		this.add(name);
		name.setText(String.format("Point %d", this.sbw.sb.getComponentCount("Point") + 1));

		JLabel descriptionLabel = new JLabel("Description: ");
		this.add(descriptionLabel);
		this.add(scrolling);

		this.add(create);
		this.add(cancel);
		this.add(attachImg);
		// id label
		layout.putConstraint(SpringLayout.WEST, idLabel, 5, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, idLabel, 5, SpringLayout.NORTH, this.getContentPane());
		// id
		layout.putConstraint(SpringLayout.WEST, id, 5, SpringLayout.EAST, idLabel);
		layout.putConstraint(SpringLayout.NORTH, id, 0, SpringLayout.NORTH, idLabel);
		layout.putConstraint(SpringLayout.EAST, id, 200, SpringLayout.WEST, this.getContentPane());
		// name label
		layout.putConstraint(SpringLayout.WEST, nameLabel, 5, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 5, SpringLayout.SOUTH, idLabel);
		// name
		layout.putConstraint(SpringLayout.WEST, name, 5, SpringLayout.EAST, nameLabel);
		layout.putConstraint(SpringLayout.NORTH, name, 5, SpringLayout.SOUTH, id);
		layout.putConstraint(SpringLayout.EAST, name, -5, SpringLayout.EAST, this.getContentPane());

		// description label
		layout.putConstraint(SpringLayout.WEST, descriptionLabel, 5, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, descriptionLabel, 5, SpringLayout.SOUTH, nameLabel);
		// description
		layout.putConstraint(SpringLayout.WEST, scrolling, 5, SpringLayout.EAST, descriptionLabel);
		layout.putConstraint(SpringLayout.NORTH, scrolling, 5, SpringLayout.NORTH, descriptionLabel);
		layout.putConstraint(SpringLayout.EAST, scrolling, -5, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, scrolling, -20, SpringLayout.NORTH, create);

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
		// attachImg
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, attachImg, 0, SpringLayout.HORIZONTAL_CENTER,
				this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, attachImg, -45, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, attachImg, -15, SpringLayout.SOUTH, this.getContentPane());

		this.setVisible(true);
	}

	public StoryBoardPointCreation(StoryBoardWindow sbw, StoryBoard sb) {
		super("Point Creation...");
		this.sbw = sbw;
		this.setup();

		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Point p = new Point(name.getText().trim(), description.getText().trim(),
						id.getText().trim() == "" ? null : new ID(id.getText().trim()), capturedImgs);
				PointComponent pc = new PointComponent(p);
				int a = pc.getWidth() / 2;
				int b = pc.getHeight() / 2;
				int x = sbw.getLayeredPane().getWidth() / 2;
				int y = sbw.getLayeredPane().getHeight() / 2;
				pc.setLocation(x - a, y - b);
				sbw.layeredPane.add(pc, JLayeredPane.PALETTE_LAYER);
				sb.add(p);
				dispose();
			}

		});
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

			}

		});

		attachImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FileSelecter(
						String.format("%s's Image Select...", StoryBoardPointCreation.this.name.getText().trim()),
						new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {

								if (arg0.getActionCommand().equals("ApproveSelection")) {
									if (arg0.getSource() instanceof FilePanel) {
										FilePanel fp = (FilePanel) arg0.getSource();
										try {
											Image i = ImageIO.read(fp.getSelectedFile());
											if (!capturedImgs.contains(i))
												capturedImgs.add(i);
										} catch (IOException e) {
											e.printStackTrace();
										}
										fp.dispose();
									}
								} else if (arg0.getActionCommand().equals("CancelSelection")) {
									if (arg0.getSource() instanceof FilePanel) {
										((FilePanel) arg0.getSource()).dispose();
									}
								}

							}
						});
			}
		});

	}

	// public StoryBoardPointCreation(StoryBoardWindow sbw, StoryBoard sb,
	// boolean flag) {
	// super(flag ? "Create Start..." : "Create End...");
	// this.setup();
	// create.addActionListener(new ActionListener() {
	//
	// @Override
	// public void actionPerformed(ActionEvent arg0) {
	// if (flag) {
	// sb.add(new Start(name.getText(), description.getText(), null));
	// new StoryBoardPointCreation(sbw, sb, StoryBoardWindow.END);
	// StoryBoardPointCreation.super.dispose();
	// return;
	//
	// } else {
	// sb.add(new End(name.getText(), description.getText(), null));
	// }
	// dispose();
	// }
	//
	// });
	// }

	public Point toPoint() {
		return new Point(name.getText(), description.getText(), null);
	}

	@Override
	public void dispose() {
		sbw.setActiveWindow(true);
		super.dispose();
	}

}
