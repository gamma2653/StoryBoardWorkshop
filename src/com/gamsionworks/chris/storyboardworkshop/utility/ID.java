package com.gamsionworks.chris.storyboardworkshop.utility;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Chris
 *	Immutable class containing various combinations of Strings.
 */
public class ID {
	private int partSize = 4;
	private final List<IDPart> parts = new LinkedList<IDPart>();
	public class IDPart {
		String part;
		public IDPart(String part) {
			if (part.length() > partSize) {
				Logger.getLogger(Function.loggerName).log(Level.WARNING, "Part of id is greater than 5 characters!");
			}
			this.part = part.toLowerCase();
		}
		@Override
		public String toString() {
			return part;
		}
	}

	public ID(String id) {
		for(int i=0; i<id.length(); i+=partSize) {
			int end = i+partSize;
			end = end>id.length()?id.length():end;
			parts.add(new IDPart(id.substring(i, end)));
		}
	}
	public ID(String id, int partSize) {
		this.partSize=partSize;
		for(int i=0; i<id.length(); i+=partSize) {
			int end = i+partSize;
			end = end>id.length()?id.length():end;
			parts.add(new IDPart(id.substring(i, end)));
		}
	}
	
	public List<IDPart> getParts(){
		return new LinkedList<IDPart>(this.parts);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(IDPart i : parts) {
			sb.append(String.format("%s-", i.toString()));
		}
		sb.delete(sb.length()-1, sb.length());
		return sb.toString();
	}
}
