package com.gamsionworks.chris.storyboardworkshop.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Immutable class containing various combinations of Strings.
 */
public class ID {
	private int partSize = 4;
	private final List<IDPart> parts = new ArrayList<IDPart>();
	private char fillerChar = '_';

	public class IDPart {
		String part;

		public IDPart(String part) {
			if (part.length() > partSize) {
				Logger.getLogger(GUtilities.loggerName).log(Level.WARNING, "Part of id is greater than 5 characters!");
			}
			while (part.length() < partSize) {
				// Filling with filler
				part += fillerChar;
			}
			this.part = part.toLowerCase();
		}

		@Override
		public String toString() {
			return part;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof IDPart) {
				IDPart part = (IDPart) o;
				return part.part.equals(this.part);
			}
			return false;
		}
		@Override
		public int hashCode(){
			return this.part.hashCode();
		}
	}

	public ID(String id) {
		id = id.trim().replaceAll("-", "");
		for (int i = 0; i < id.length(); i += partSize) {
			int end = i + partSize;
			end = end > id.length() ? id.length() : end;
			parts.add(new IDPart(id.substring(i, end)));
		}
	}

	public ID(String id, int partSize) {
		id = id.trim().replaceAll("-", "");
		this.partSize = partSize;
		for (int i = 0; i < id.length(); i += partSize) {
			int end = i + partSize;
			end = end > id.length() ? id.length() : end;
			parts.add(new IDPart(id.substring(i, end)));
		}
	}

	public List<IDPart> getParts() {
		return new ArrayList<IDPart>(this.parts);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (IDPart i : parts) {
			sb.append(String.format("%s-", i.toString()));
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}
}
