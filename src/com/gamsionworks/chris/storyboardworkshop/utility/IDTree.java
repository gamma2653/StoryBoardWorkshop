package com.gamsionworks.chris.storyboardworkshop.utility;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.AppMaterial;
import com.gamsionworks.chris.storyboardworkshop.utility.ID.IDPart;

/**
 * Represents all known IDs as a tree structure for searching purposes.
 */
public class IDTree {
	public IDTreeNode head = new IDTreeNode();

	class IDTreeNode {
		IDTreeNode parent = null;
		Set<IDTreeNode> children = new HashSet<IDTreeNode>();
		IDPart value = null;
		AppMaterial mat = null;

		/**
		 * @param parent
		 *            - Trees parent. If null, is designated as head.
		 * @param children
		 *            - if null defaults to empty.
		 * @param value
		 *            - IDPart value of node.
		 */
		public IDTreeNode(IDTreeNode parent, Set<IDTreeNode> children, IDPart value) {
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
		public IDTreeNode(IDTreeNode parent, Set<IDTreeNode> children, IDPart value, AppMaterial mat) {
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
		public IDTreeNode(IDTreeNode parent, IDPart value) {
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
		public IDTreeNode(IDTreeNode parent, IDPart value, AppMaterial mat) {
			this.parent = parent;
			this.value = value;
			this.mat = mat;
			enforceInv();
		}

		private IDTreeNode() {
		}

		/**
		 * If invariant is not kept, will throw a runtime exception.
		 * 
		 * @see IDTreeNode#checkInvariant()
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
		 * <li>If a node is a head, it should have no value.</li>
		 * <li>If a node has no children, it is a leaf. (No need to check)</li>
		 * </ol>
		 * 
		 * @return
		 */
		private boolean checkInvariant() {
			Set<IDPart> values = new HashSet<IDPart>();
			if (this.parent == null) // If parent is null, value should be null
				return value == null;
			for (IDTreeNode t : this.parent.children) {
				if (values.contains(t.value)) {
					Logger.getLogger(GUtilities.loggerName).log(Level.WARNING, "Invariant is not maintained");
					return false;
				}
			}
			return true;
		}

		/**
		 * Return whether this node is a leaf.
		 * 
		 * @return
		 */
		public boolean isLeaf() {
			return !this.hasChildren();
		}

		/**
		 * Return if this node is the top of a tree.
		 * 
		 * @return
		 */
		public boolean isHead() {
			return this.parent == null;
		}

		/**
		 * Returns all of the children as a set.
		 * 
		 * @return
		 */
		public Set<IDTreeNode> getChildren() {
			return this.children;
		}

		/**
		 * Returns value of this node.
		 * 
		 * @return
		 */
		public IDPart getValue() {
			return value;
		}

		/**
		 * Returns the child matching this IDPart. (Assumes not by Mem)
		 * 
		 * @param part
		 * @return
		 */
		public IDTreeNode getChild(IDPart part) {
			return getChild(part, false);
		}

		/**
		 * Returns the child matching this IDPart. If byMem is true, will use ==
		 * operator for search. If false, will use .equals to search.
		 * 
		 * @param part
		 * @param byMem
		 * @return
		 */
		public IDTreeNode getChild(IDPart part, boolean byMem) {
			for (IDTreeNode t : children) {
				if (byMem ? t.getValue() == part : t.getValue().part.equals(part.part)) {
					return t;
				}
			}
			Logger.getLogger(GUtilities.loggerName).log(Level.INFO,
					String.format("Child (%s) not found in %s", part.toString(), this.toString()));
			return null;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder((value == null ? "NaN" : this.value.part));
			sb.append(":{");
			for (IDTreeNode t : children) {
				sb.append(t.toString());
			}
			sb.append("}");
			return sb.toString();
		}

		/**
		 * Adds the List of IDParts to the tree, expanding it's branches when
		 * necessary.
		 * 
		 * @param id
		 */
		public void addID(List<IDPart> id) {
			if (id == null || id.isEmpty()) {
				return;
			}
			IDPart part = id.get(0);
			id.remove(0);
			IDTreeNode t = getChild(part);
			t = t == null ? new IDTreeNode(this, part) : t;
			t.addID(id);
			this.children.add(t);// For cool effect, add all the trees to the
									// set AFTER all have been constructed
			// if (this.isHead()) {
			// IDTreeNode t = new IDTreeNode(this, part);
			// } else {
			//
			//
			// }
		}

		public void removeChild(IDPart part, boolean byMem) {
			for (IDTreeNode t : children) {
				if (byMem ? t.getValue() == part : t.getValue().part.equals(part.part)) {
					children.remove(t);
					t.parent = null;
					return;
				}
			}
			Logger.getLogger(GUtilities.loggerName).log(Level.INFO,
					String.format("Child not found in %s", this.toString()));
		}

		public void removeChild(IDPart part) {
			removeChild(part, false);
		}

		/**
		 * Utility function, same as addID(id.getParts());
		 * 
		 * @param id
		 * @see IDTreeNode#addID(List)
		 */
		public void addID(ID id) {
			addID(id.getParts());
		}

		public boolean hasChildren() {
			return !this.children.isEmpty();
		}
	}

	public IDTree() {

	}

	public IDTree(ID id) {
		head.addID(id);
	}

	public void add(ID id) {
		head.addID(id);
	}

	public void add(List<IDPart> id) {
		head.addID(id);
	}

	public boolean remove(ID id) {
		List<IDPart> buffer = id.getParts();
		IDTreeNode crnt = head;
		while (!buffer.isEmpty()) {
			crnt = crnt.getChild(buffer.get(0));
			if (crnt == null) {
				return false;
			}
			buffer.remove(0);
		}
		IDTreeNode old = crnt;
		crnt = crnt.parent;
		crnt.removeChild(old.value);
		while (!crnt.hasChildren()) {
			old = crnt;
			crnt = crnt.parent;
			crnt.removeChild(old.value);
		}
		return true;
	}

	private IDTreeNode getNode(ID id) {
		List<IDPart> buffer = id.getParts();
		IDTreeNode crnt = head;
		while (!buffer.isEmpty()) {
			crnt = crnt.getChild(buffer.get(0));
			if (crnt == null) {
				return null;
			}
			buffer.remove(0);
		}
		return crnt;
	}

	public AppMaterial getAppMaterial(ID id) {
		IDTreeNode node = getNode(id);
		return node == null ? null : node.mat;
	}

	public void setAppMaterial(ID id, boolean createIfNotExist) {
		List<IDPart> buffer = id.getParts();
		IDTreeNode crnt = head;
		while (!buffer.isEmpty()) {
			crnt = crnt.getChild(buffer.get(0));
			if (crnt == null) {
				break;
			}
			buffer.remove(0);
		}
		if (!buffer.isEmpty() && createIfNotExist) {
			crnt.addID(buffer);
		}
	}

	public boolean hasID(ID id) {
		return getNode(id) != null;
	}

	@Override
	public String toString() {
		return "Tree:" + this.head.toString();
	}

	public static void main(String[] args) {
		int size = 4;
		ID id = new ID("exampleid2626", 1);
		ID id2 = new ID("exampleid5353", 1);
		IDTree t = new IDTree(id);
		System.out.println(t);
		t.add(id2);
		System.out.println(t);
		ID rid = new ID("exampleid2626", 1);
		t.remove(rid);
		System.out.println(t);
		System.out.println(t.hasID(new ID("exampleid5353", 1)));

		// IDTree t = initIDTree(id);
		// t.addID(id2);
		// System.out.println(t.getChildren());
		// System.out.println(t);
		// System.out.println(t.getChild(id.getParts().get(0)));
	}

}
// id1
// id2
// id3
