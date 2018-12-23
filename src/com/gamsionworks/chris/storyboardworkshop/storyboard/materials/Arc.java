package com.gamsionworks.chris.storyboardworkshop.storyboard.materials;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gamsionworks.chris.storyboardworkshop.gui.StoryBoardWindow;
import com.gamsionworks.chris.storyboardworkshop.storyboard.StoryBoard;
import com.gamsionworks.chris.storyboardworkshop.utility.ID;
import com.gamsionworks.chris.storyboardworkshop.utility.IDFactory;

public class Arc implements AppMaterial {
	protected ID id;
	protected String name;
	protected String description;
	protected Point start, end;
	protected List<Image> imgs = new ArrayList<Image>();
	private List<Segment> segments;

	public Arc(String name, String description, List<Segment> segments, ID uid) {
		this.setUID(uid == null ? IDFactory.getUID() : uid);
		segments = new ArrayList<Segment>();
		this.segments.addAll(segments);
		if (name == null) {
			StringBuilder sb = new StringBuilder("");
			for (Segment s : segments) {

			}
		} else {
			this.name = name;
		}
		this.name = name == null ? String.format("'%s' - '%s'", start.getTitle(), end.getTitle()) : name;
		this.description = description == null ? String.format("Beginning: '%s'\nBy the end: '%s'",
				start == null ? "null" : start.getDescription(), end == null ? "null" : end.getDescription())
				: description;
		this.start = start;
		this.end = end;

	}

	@Override
	public Map<String, ID> getAssociations() {
		Map<String, ID> assoc = new HashMap<String, ID>();
		segments.forEach((i) -> {

		});
		assoc.put("start", start.getUID());
		assoc.put("end", end.getUID());
		return assoc;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public List<Segment> getInnerSegments() {
		return null;
	}

	@Override
	public String getTypeName() {
		return "Arc";
	}

	@Override
	public Image[] getAttachedImgs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void attachImg(Image i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attachImgs(Image[] i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeImg(Image i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeImgs(Image[] i) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub

	}


	@Override
	public void setUID(String UID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void autoUID() {
		// TODO Auto-generated method stub

	}

	@Override
	public void edit(StoryBoardWindow sbw, StoryBoard sb) {
		// TODO Auto-generated method stub

	}
	//TODO: bfs
	public static Segment getEarliestSegment(List<Segment> segs) {

		Map<ID, Boolean> count = new HashMap<ID, Boolean>();
		Segment earlySeg = segs.get(0);
		for (Segment s : segs) {
			if (count.containsKey(s.getStart().getUID())) {
				earlySeg = s;
				count.remove(s.getStart().getUID());
			} else {
				count.put(s.getStart().getUID(), false);
			}
		}
		if (count.size() > 1)
			System.err.println("ERROR TOO MANY BROS");

		return earlySeg;
	}

	@Override
	public ID getUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUID(ID UID) {
		// TODO Auto-generated method stub
		
	}

}
