package com.gamsionworks.chris.storyboardworkshop.storyboard.materials;

import java.util.ArrayList;
import java.util.List;

public class Arc extends Segment implements AppMaterial {

	private List<Segment> segments;

	public Arc(String name, String description, Point start, Point end, List<Segment> segments, String uid) {
		super(name, description, start, end, uid);
		segments = new ArrayList<Segment>();
		this.segments.addAll(segments);
	}

	public List<Segment> getSegments() {
		return segments;
	}
	public List<Segment> getInnerSegments(){
		return null;
	}
	
	@Override
	public String getTypeName() {
		return "Arc";
	}

}
