package com.gamsionworks.chris.storyboardworkshop.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.AppMaterial;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.Point;

public class IDFactory {
	// Didn't feel like using a real encoding: this was faster
	static final char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static IDTree existingIDs = new IDTree();
	static Random r = new Random();
	public static boolean useStandard = true;
	private static Map<String, String> standards = new HashMap<String, String>();

	public static ID getUID(int start, int length, int pieceSize, boolean add) {
		StringBuilder idStr;
		ID id;
		do {
			idStr = new StringBuilder();
			for (int i = start; i < length; i++) {
				idStr.append(chars[r.nextInt(chars.length)]);
			}
			id = new ID(idStr.toString());
		} while (existingIDs.hasID(id));
		if (add)
			existingIDs.add(id);
		return id;
	}

	/**
	 * Same as getUID(0, length, pieceSice, add);
	 * 
	 * @param length
	 * @param pieceSize
	 * @param add
	 * @return
	 */
	public static ID getUID(int length, int pieceSize, boolean add) {
		return getUID(0, length, pieceSize, add);
	}

	/**
	 * same as getUID(0, length, pieceSize, true);
	 * 
	 * @param length
	 * @param pieceSize
	 * @return
	 */
	public static ID getUID(int length, int pieceSize) {
		return getUID(length, pieceSize, true);
	}

	/**
	 * Same as getUID(0, length, GUtilities.idSize, add);
	 * 
	 * @param length
	 * @param add
	 * @return
	 */
	public static ID getUID(int length, boolean add) {
		return getUID(length, GUtilities.idSize, add);
	}

	/**
	 * Same as getUID(0, length, GUtilities.idSize, true);
	 * 
	 * @param length
	 * @return
	 */
	public static ID getUID(int length) {
		return getUID(length, true);
	}

	/**
	 * Same as getUID(0, GUtilities.stdIDLength, GUtilities.idSize, true);
	 * 
	 * @return
	 */
	public static ID getUID() {
		return getUID(GUtilities.stdIdSize);
	}

	public static ID getUID(AppMaterial mat) {
		if (existingIDs.hasID(mat.getUID())) {
			return mat.getUID();
		} else {
			if (!useStandard) {
				return IDFactory.getUID();
			} else {
				String key = mat.getTypeName();
				String part;
				if (standards.containsKey(key)) {
					part = standards.get(key);
				} else {
					part = getUID(4, false).toString();
					standards.put(key, part);
				}
				ID id = new ID(part + getUID(GUtilities.stdIdSize - 4, false).toString());
				existingIDs.add(id);
				return id;

			}
		}

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
	public static void main(String[] args){
		System.out.println(getUID(4, false));
		System.out.println(getExistingIDs());
		System.out.println("\n\n\n\n");
		//test
		String key = "Point";
		String part;
		if (standards.containsKey(key)) {
			part = standards.get(key);
		} else {
			part = getUID(4, false).toString();
			standards.put(key, part);
		}
		System.out.println(part);
		System.out.println(getExistingIDs());
		System.out.println(standards);
	}

}
