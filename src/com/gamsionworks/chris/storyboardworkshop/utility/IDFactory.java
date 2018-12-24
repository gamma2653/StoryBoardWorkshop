package com.gamsionworks.chris.storyboardworkshop.utility;

import java.util.Random;

import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.AppMaterial;

public class IDFactory {
	// Didn't feel like using a real encoding: this was faster
	static final char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static IDTree existingIDs = new IDTree();
	static Random r = new Random();

	public static ID getUID(int length, int pieceSize) {
		StringBuilder idStr;
		ID id;
		do {
			idStr = new StringBuilder();
			for (int i = 0; i < length; i++) {
				idStr.append(chars[r.nextInt(chars.length)]);
			}
			id = new ID(idStr.toString());
		} while (existingIDs.hasID(id));
		existingIDs.add(id);
		return id;
	}

	public static ID getUID(int length) {
		return getUID(length, GUtilities.idSize);
	}

	public static ID getUID() {
		return getUID(GUtilities.stdIdSize);
	}

	public static void addID(ID id) {
		existingIDs.add(id);
	}

	public static void removeID(ID id) {
		if (id == null)
			return;
		existingIDs.remove(id);
	}

	public static void add(AppMaterial mat) {
		existingIDs.add(mat);
	}

	public static void remove(AppMaterial mat) {
		existingIDs.remove(mat.getUID());
	}

	public static boolean compareIDs(String id1, String id2) {
		return id1.replaceAll("-", "").equals(id2.replaceAll("-", ""));
	}

	public static boolean compareIDs(ID id1, ID id2) {
		return id1.equals(id2);
	}

	public static IDTree getExistingIDs() {
		return existingIDs;
	}

	public static void main(String[] args) {
		System.out.println(chars.length);
	}

}
