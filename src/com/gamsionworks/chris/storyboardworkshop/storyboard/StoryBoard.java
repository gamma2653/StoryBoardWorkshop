package com.gamsionworks.chris.storyboardworkshop.storyboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gamsionworks.chris.storyboardworkshop.gui.StoryBoardArcCreation;
import com.gamsionworks.chris.storyboardworkshop.gui.StoryBoardPointCreation;
import com.gamsionworks.chris.storyboardworkshop.gui.StoryBoardSegmentCreation;
import com.gamsionworks.chris.storyboardworkshop.gui.StoryBoardWindow;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.AppMaterial;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.Arc;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.End;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.Point;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.Segment;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.Start;

public class StoryBoard {
	Map<String, AppMaterial> items = new HashMap<String, AppMaterial>();
	public AppMaterial selected = null;
	public Start start;
	public End end;
	public StoryBoardWindow sbw;

	public StoryBoard(StoryBoardWindow sbw) {
		this.sbw = sbw;
	}

	@Deprecated
	public AppMaterial get(Long id) {
		return items.get(String.valueOf(id));
	}

	public AppMaterial get(String id) {
		return items.get(id);
	}

	public Collection<AppMaterial> getAllItems() {
		return items.values();
	}

	public void add(AppMaterial[] items) {
		for (AppMaterial item : items) {
			this.add(item);
		}
	}

	public void add(AppMaterial item) {
		items.put(item.getUID(), item);
		if (item instanceof Arc) {
			Arc arc = (Arc) item;
			// Get list as aray and cast to AppMaterial array
			this.add((AppMaterial[]) arc.getSegments().toArray());
		} else if (item instanceof Segment) {
			Segment seg = (Segment) item;
			this.add(seg.getStart());
			this.add(seg.getEnd());

		} else if (item instanceof Start) {
			start = (Start) item;
		} else if (item instanceof End) {
			end = (End) item;
		}
	}

	public Map<String, List<AppMaterial>> getAssociation(Point p) {
		Map<String, List<AppMaterial>> materials = new HashMap<String, List<AppMaterial>>();
		List<AppMaterial> segments = new ArrayList<AppMaterial>();
		List<AppMaterial> points = new ArrayList<AppMaterial>();
		for (AppMaterial mat : this.getAllItems()) {
			if (mat instanceof Segment) {
				Segment seg = (Segment) mat;
				segments.add((AppMaterial) seg);
				points.add((Point) seg.getStart());
				points.add((Point) seg.getEnd());
			}
		}
		materials.put("Point", points);
		materials.put("Segment", segments);

		return materials;
	}

	public void fork(Point p) {
		Segment seg1 = new Segment(null, null, p, new Point("Fork 1", "Please enter your description.", null), null);
		Segment seg2 = new Segment(null, null, p, new Point("Fork 2", "Please enter your description.", null), null);

		this.add(seg1);
		this.add(seg2);
	}

	public void branch(Point p) {
		Segment seg1 = new Segment(null, null, p, new Point("Branch", "Please enter your description.", null), null);

		this.add(seg1);
	}

	public void makePoint() {
		sbw.setActiveWindow(false);
		;
		new StoryBoardPointCreation(sbw, this);

	}

	public void makeSegment() {
		sbw.setActiveWindow(false);
		;
		new StoryBoardSegmentCreation(sbw, this);

	}

	public void makeArc() {
		sbw.setActiveWindow(false);
		;
		new StoryBoardArcCreation(sbw, this);

	}

	public AppMaterial getSelected() {
		return this.selected;
	}

	public boolean isSelected(AppMaterial am) {
		return am == selected;
	}

	public void setSelected(AppMaterial am) {
		this.selected = am;
	}

	public SegmentData getSegments(Point p) {
		return new SegmentData(p);
	}

	public class SegmentData {
		List<Segment> endPoint = new ArrayList<Segment>();
		List<Segment> startPoint = new ArrayList<Segment>();

		protected SegmentData(Point p) {
			for (AppMaterial am : StoryBoard.this.items.values()) {
				if (am instanceof Segment && !(am instanceof Arc)) {
					Segment seg = (Segment) am;
					if (seg.getStart().equals(p)) {
						endPoint.add(seg);
					}
					if (seg.getEnd().equals(p)) {
						startPoint.add(seg);
					}
				}
			}
		}
		public List<Segment> getStartPoints(){
			return startPoint;
		}
		public List<Segment> getEndPoints(){
			return endPoint;
		}
	}
	@Deprecated
	public int getComponentCount(AppMaterial comp) {
		int count = 0;
		for(AppMaterial am : this.getAllItems()) {
			if(am.getTypeName().equals(comp.getTypeName())) {
				count++;
			}
		}
		return count;
	}
	public int getComponentCount(String comp) {
		int count = 0;
		for(AppMaterial am : this.getAllItems()) {
			if(am.getTypeName().equalsIgnoreCase(comp)) {
				count++;
			}
		}
		return count;
	}
	public int getComponentCount(Class<?> cl) {
		int count = 0;
		for(AppMaterial am : this.getAllItems()) {
			if(am.getClass()==cl) {
				count++;
			}
		}
		return count;
	}
}
