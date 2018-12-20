package com.gamsionworks.chris.storyboardworkshop.utility;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.AppMaterial;
import com.gamsionworks.chris.storyboardworkshop.utility.ID.IDPart;

public class IDTree {
	IDTree parent = null;
	Set<IDTree> children = new HashSet<IDTree>();
	IDPart value;
	AppMaterial mat = null;

	/**
	 * @param parent
	 *            - Trees parent. If null, is designated as head.
	 * @param children
	 *            - if null defaults to empty.
	 * @param value
	 *            - IDPart value of node.
	 */
	public IDTree(IDTree parent, Set<IDTree> children, IDPart value) {
		this.parent = parent;
		this.children = children != null ? children : this.children;
		this.value = value;
		enforceInv();
	}

	/**
	 * @param parent
	 *            - Trees parent. If null, is designated as head.
	 * @param children
	 *            - if null defaults to empty.
	 * @param value
	 *            - IDPart value of node.
	 * @param mat
	 *            - item stored at this ID.
	 */
	public IDTree(IDTree parent, Set<IDTree> children, IDPart value, AppMaterial mat) {
		this.parent = parent;
		this.children = children != null ? children : this.children;
		this.value = value;
		this.mat = mat;
		enforceInv();
	}

	/**
	 * @param parent
	 *            - Trees parent. If null, is designated as head.
	 * @param value
	 *            - IDPart value of node.
	 */
	public IDTree(IDTree parent, IDPart value) {
		this.parent = parent;
		this.value = value;
		enforceInv();
	}

	/**
	 * @param parent
	 *            - Trees parent. If null, is designated as head.
	 * @param value
	 *            - IDPart value of node.
	 * @param mat
	 *            - item stored at this ID.
	 */
	public IDTree(IDTree parent, IDPart value, AppMaterial mat) {
		this.parent = parent;
		this.value = value;
		this.mat = mat;
		enforceInv();
	}

	/**
	 * If invariant is not kept, will throw a runtime exception.
	 * 
	 * @see IDTree#checkInvariant()
	 */
	private void enforceInv() {
		if (!checkInvariant()) {
			throw new RuntimeException("Invariant not maintained!");
		}
	}

	/**
	 * Checks to see if invariant has been kept:
	 * <ol>
	 * <li>No siblings contain the same value.</li>
	 * <li>If a node has no parent, it is a head. (No need to check)</li>
	 * <li>If a node has no children, it is a leaf. (No need to check)</li>
	 * </ol>
	 * 
	 * @return
	 */
	private boolean checkInvariant() {
		Set<IDPart> values = new HashSet<IDPart>();
		for (IDTree t : this.parent.children) {
			if (values.contains(t.value)) {
				Logger.getLogger(Function.loggerName).log(Level.WARNING, "Invariant is not maintained");
				return false;
			}
		}
		return true;
	}

	public boolean isLeaf() {
		if (children.size() == 0)
			return true;
		return false;
	}

	public boolean isHead() {
		return this.parent == null;
	}

	public Set<IDTree> getChildren() {
		return this.children;
	}

	public IDPart getValue() {
		return value;
	}

	public IDTree getChild(IDPart part) {
		return getChild(part, false);
	}

	public IDTree getChild(IDPart part, boolean byMem) {
		for (IDTree t : children) {
			if (byMem ? t.getValue().equals(part) : t.getValue() == part) {
				return t;
			}
		}
		Logger.getLogger(Function.loggerName).log(Level.WARNING,
				String.format("Child not found in %s", this.toString()));
		return null;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.value.part);
		sb.append(":{");
		for(IDTree t : children) {
			sb.append(t.toString());
		}
		sb.append("}");
		return sb.toString();
	}

}
