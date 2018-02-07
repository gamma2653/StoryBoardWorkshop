package com.gamsionworks.chris.storyboardworkshop.storyboard.materials;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import com.gamsionworks.chris.storyboardworkshop.gui.StoryBoardWindow;
import com.gamsionworks.chris.storyboardworkshop.storyboard.StoryBoard;
import com.gamsionworks.chris.storyboardworkshop.utility.IDFactory;

public class Segment implements AppMaterial {
	protected String ID;
	protected String name;
	protected String description;
	protected Point start, end;
	protected List<Image> imgs = new ArrayList<Image>();

	public Segment(String name, String description, Point start, Point end, String uid) {
		this.name = name == null ? String.format("'%s' - '%s'", start.getTitle(), end.getTitle()) : name;
		this.description = description == null ? String.format("Beginning: '%s'\nBy the end: '%s'",
				start == null ? "null" : start.getDescription(), end == null ? "null" : end.getDescription())
				: description;
		this.start = start;
		this.end = end;
		this.setUID(uid == null ? String.valueOf(IDFactory.getUID()) : uid);

	}

	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}

	@Override
	public String getTypeName() {
		return "Segment";
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
	public String getUID() {
		return ID;
	}

	@Override
	public void setUID(String UID) {
		this.ID = UID;
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
	public void edit(StoryBoardWindow sbw, StoryBoard sb) {

	}
	@Override
	public Image[] getAttachedImgs() {
		return (Image[])new ArrayList<Image>(imgs).toArray();
	}

	@Override
	public void attachImg(Image i) {
		this.imgs.add(i);
	}

	@Override
	public void attachImgs(Image[] img) {
		for(Image i : img) {
			imgs.add(i);
		}
	}

	@Override
	public void removeImg(Image i) {
		imgs.remove(i);
	}

	@Override
	public void removeImgs(Image[] img) {
		for(Image i : img) {
			imgs.remove(i);
		}
	}
}
