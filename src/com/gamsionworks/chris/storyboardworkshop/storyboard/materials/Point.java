package com.gamsionworks.chris.storyboardworkshop.storyboard.materials;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.gamsionworks.chris.storyboardworkshop.gui.FileSelecter;
import com.gamsionworks.chris.storyboardworkshop.gui.FileSelecter.FilePanel;
import com.gamsionworks.chris.storyboardworkshop.gui.JImageComponent;
import com.gamsionworks.chris.storyboardworkshop.gui.StoryBoardWindow;
import com.gamsionworks.chris.storyboardworkshop.storyboard.StoryBoard;
import com.gamsionworks.chris.storyboardworkshop.utility.ID;
import com.gamsionworks.chris.storyboardworkshop.utility.IDFactory;

public class Point implements AppMaterial {
	protected ID ID;
	protected String name;
	protected String description;
	protected List<Image> imgs = new ArrayList<Image>();
	protected Image thumbnail;

	public Point(String name, String description, ID uid) {
		this.name = name == null ? String.format("Point# %d", this.getUID()) : name;
		this.description = description == null ? String.format("Point# %d", this.getUID()) : description;
		this.setUID((uid == null || uid.equals("")) ? IDFactory.getUID() : uid);
	}

	public Point(String name, String description, ID uid, List<Image> capturedImgs) {
		this(name, description, uid);
		imgs = capturedImgs;
		if (!imgs.isEmpty()) {
			thumbnail = imgs.get(0);
		}
	}

	@Override
	public String getTypeName() {
		return "Point";
	}

	@Override
	public String getTitle() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setTitle(String title) {
		this.name = title;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public ID getUID() {
		return ID;
	}

	@Override
	public void setUID(ID UID) {
		if (!this.ID.equals(UID)) {
			IDFactory.removeID(this.ID);
			IDFactory.addID(UID);
			this.ID = UID;

		}
	}

	@Override
	public void autoUID() {
		this.setUID(String.valueOf(IDFactory.getUID()));
	}

	@Override
	public String toString() {
		return this.getTitle();
	}

	@Override
	public Map<String, ID> getAssociations() {
		return new HashMap<String, ID>();
	}

	@Override
	public void edit(StoryBoardWindow sbw, StoryBoard sb) {
		new PointMenu(sbw, sb);
	}

	protected void sortImages() {
		this.imgs.forEach((i) -> {
			if (this.thumbnail == i && imgs.indexOf(i) != 0) {
				imgs.remove(i);
				imgs.add(0, thumbnail);
			}

		});
	}

	// TODO: Fix image adding so that it properly cancels
	public class PointMenu extends JFrame {
		protected static final long serialVersionUID = 1L;
		protected Toolkit toolkit = Toolkit.getDefaultToolkit();
		SpringLayout layout = new SpringLayout();
		JTextField name = new JTextField(30);
		JTextField id = new JTextField(30);
		JTextArea description = new JTextArea(20, 30);

		List<Image> previewedImgs = new ArrayList<Image>();
		JButton save = new JButton("Save");
		JButton cancel = new JButton("Cancel");
		JButton attachImg = new JButton("Attach an Image");
		JLabel imgsLabel = new JLabel("Images:");
		JImageComponent jic = new JImageComponent(null);
		StoryBoardWindow sbw;
		Point p;

		private void loadJics() {
			for (Component c : this.getContentPane().getComponents()) {
				if (c instanceof JImageComponent) {
					this.remove(c);
					this.validate();
				}
			}
			jic = new JImageComponent(null);
			if (Point.this.imgs.size() == 0) {
				BufferedImage bi = null;
				try {
					bi = ImageIO.read(this.getClass().getResourceAsStream("../../resources/Empty_Image.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				jic.setImage(bi);
				this.add(jic);
				layout.putConstraint(SpringLayout.WEST, jic, 5, SpringLayout.EAST, imgsLabel);
				layout.putConstraint(SpringLayout.NORTH, jic, 0, SpringLayout.VERTICAL_CENTER, imgsLabel);
				layout.putConstraint(SpringLayout.EAST, jic, 100, SpringLayout.WEST, jic);
				layout.putConstraint(SpringLayout.SOUTH, jic, 100, SpringLayout.NORTH, jic);
			}
			JImageComponent lastC = null;
			if (Point.this.getThumbnail() != null) {
				jic.setImage(Point.this.getThumbnail());
				this.add(jic);
				layout.putConstraint(SpringLayout.WEST, jic, 5, SpringLayout.EAST, imgsLabel);
				layout.putConstraint(SpringLayout.NORTH, jic, 0, SpringLayout.VERTICAL_CENTER, imgsLabel);
				layout.putConstraint(SpringLayout.EAST, jic, 100, SpringLayout.WEST, jic);
				layout.putConstraint(SpringLayout.SOUTH, jic, 100, SpringLayout.NORTH, jic);
				lastC = jic;
			}
			for (Image i : Point.this.imgs) {
				if (!i.equals(jic.getImage())) {
					JImageComponent cur = new JImageComponent(i);
					this.add(cur);
					layout.putConstraint(SpringLayout.WEST, cur, 5, SpringLayout.EAST, lastC);
					layout.putConstraint(SpringLayout.NORTH, cur, 0, SpringLayout.NORTH, lastC);
					layout.putConstraint(SpringLayout.EAST, cur, 100, SpringLayout.WEST, cur);
					layout.putConstraint(SpringLayout.SOUTH, cur, 100, SpringLayout.NORTH, cur);
					lastC = cur;
				}
			}

			// jic

			this.validate();
		}

		private void setup() {
			previewedImgs.clear();
			previewedImgs.addAll(imgs);
			this.setPreferredSize(
					new Dimension(toolkit.getScreenSize().width / 2, (int) (toolkit.getScreenSize().height / 1.25)));
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setResizable(true);
			this.pack();
			this.setLocationRelativeTo(null);
			this.setMinimumSize(new Dimension(400, 400));

			this.setLayout(layout);

			this.getRootPane().setDefaultButton(save);
			JScrollPane scrolling = new JScrollPane(description, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			JLabel idLabel = new JLabel("ID: ");
			id.setText(Point.this.ID.toString());
			this.add(idLabel);
			this.add(id);

			JLabel nameLabel = new JLabel("Name: ");
			name.setText(Point.this.name);
			this.add(nameLabel);
			this.add(name);

			JLabel descriptionLabel = new JLabel("Description: ");
			description.setText(Point.this.description);
			this.add(descriptionLabel);
			this.add(scrolling);

			this.add(imgsLabel);
			loadJics();

			this.add(save);
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
			layout.putConstraint(SpringLayout.SOUTH, scrolling, -150, SpringLayout.NORTH, save);

			// imgsLabel
			layout.putConstraint(SpringLayout.WEST, imgsLabel, 5, SpringLayout.WEST, this.getContentPane());
			layout.putConstraint(SpringLayout.NORTH, imgsLabel, 5, SpringLayout.SOUTH, scrolling);

			// create
			layout.putConstraint(SpringLayout.WEST, save, 15, SpringLayout.WEST, this.getContentPane());
			layout.putConstraint(SpringLayout.NORTH, save, -45, SpringLayout.SOUTH, this.getContentPane());
			layout.putConstraint(SpringLayout.EAST, save, 115, SpringLayout.WEST, this.getContentPane());
			layout.putConstraint(SpringLayout.SOUTH, save, -15, SpringLayout.SOUTH, this.getContentPane());
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

		public PointMenu(StoryBoardWindow sbw, StoryBoard sb) {
			super("Point Edit...");
			this.sbw = sbw;
			this.setup();

			save.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Collection<ID> ids = Arrays.asList(sbw.getStoryBoard().getComponentIDs());

					// if(ids.contains(o))
//					if(Point.this.ID)
					Point.this.ID = new ID(id.getText());
					Point.this.name = name.getText();
					Point.this.description = description.getText();
					dispose();
					System.out.printf("%s and %s\n\n", sb.getSegments(Point.this).getStartPoints(),
							sb.getSegments(Point.this).getEndPoints());
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
					new FileSelecter(String.format("%s's Image Select...", PointMenu.this.name.getText().trim()),
							new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent arg0) {

									if (arg0.getActionCommand().equals("ApproveSelection")) {
										if (arg0.getSource() instanceof FilePanel) {
											FilePanel fp = (FilePanel) arg0.getSource();
											try {
												Image i = ImageIO.read(fp.getSelectedFile());
												Point.this.attachImg(i);
												loadJics();

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

		// public Menu(StoryBoardWindow sbw, StoryBoard sb, boolean flag) {
		// super(flag ? "Create Start..." : "Create End...");
		// this.setup();
		// save.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// if (flag) {
		// sb.add(new Start(name.getText(), description.getText(), null));
		// new StoryBoardPointCreation(sbw, sb, StoryBoardWindow.END);
		// Menu.super.dispose();
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

		@Override
		public void dispose() {
			sbw.setActiveWindow(true);
			super.dispose();
		}

	}

	@Override
	public Image[] getAttachedImgs() {
		return (Image[]) new ArrayList<Image>(imgs).toArray();
	}

	@Override
	public void attachImg(Image i) {
		if (!imgs.contains(i)) {
			this.imgs.add(i);
			if (thumbnail == null) {
				thumbnail = i;
			}
		}
	}

	@Override
	public void attachImgs(Image[] img) {
		for (Image i : img) {
			if (!imgs.contains(i)) {
				imgs.add(i);
			}
		}
		if (thumbnail == null && !imgs.isEmpty()) {
			thumbnail = imgs.get(0);
		}
	}

	@Override
	public void removeImg(Image i) {
		imgs.remove(i);
		if (thumbnail == i && !imgs.isEmpty()) {
			thumbnail = imgs.get(0);
		}
	}

	@Override
	public void removeImgs(Image[] img) {
		for (Image i : img) {
			imgs.remove(i);
			if (thumbnail.equals(i)) {
				thumbnail = null;
			}
		}
		if (thumbnail == null && !imgs.isEmpty()) {
			thumbnail = imgs.get(0);
		}
	}

	public Image getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Image i) {
		if (i == null)
			throw new RuntimeException("Cannot set thumbnail to null.");
		if (!imgs.contains(i)) {
			imgs.add(i);
		}
		thumbnail = i;
	}

	@Override
	public void setUID(String UID) {
		// TODO Auto-generated method stub
		
	}
}
