package com.gamsionworks.chris.storyboardworkshop.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.AppMaterial;

public class Hotbar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public Hotbar(StoryBoardWindow sbw) {
		JMenu menu1 = new JMenu("New");
		JMenu menu2 = new JMenu("Edit");
		JMenuItem i1 = new JMenuItem("Point");
		i1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sbw.getStoryBoard().makePoint();
			}
		});
		menu1.add(i1);
		
		JMenuItem i2 = new JMenuItem("Segment");
		i2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sbw.getStoryBoard().makeSegment();
			}
		});
		menu1.add(i2);
		JMenuItem i3 = new JMenuItem("Arc");
		i3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sbw.getStoryBoard().makeArc();
			}
		});
		menu1.add(i3);
		List<AppMaterial> items = new ArrayList<AppMaterial>(sbw.getStoryBoard().getAllItems());
		items.forEach((s) -> {
			JMenuItem item = new JMenuItem(s.getTitle());
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					s.edit(sbw, sbw.getStoryBoard());
				}

			});
			menu2.add(item);

		});
		// add menus to bar
		this.add(menu1);
		this.add(menu2);
	}

}
