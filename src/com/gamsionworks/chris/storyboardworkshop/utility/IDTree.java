package com.gamsionworks.chris.storyboardworkshop.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.AppMaterial;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.Point;
import com.gamsionworks.chris.storyboardworkshop.utility.ID.IDPart;

/**
 * Represents all known IDs as a tree structure for searching purposes.
 */
public class IDTree {
	public IDTreeNode head = new IDTreeNode(null);

	class IDTreeNode {
		IDTreeNode parent;
		Map<IDPart, IDTreeNode> children = new HashMap<IDPart, IDTreeNode>();
		AppMaterial mat = null;
		boolean isWord = false;

		/**
		 * @param parent
		 *            - Trees parent. If null, is designated as head.
		 * @param children
		 *            - if null defaults to empty.
		 * @param value
		 *            - IDPart value of node.
		 */
		public IDTreeNode(IDTreeNode parent, Map<IDPart, IDTreeNode> children) {
			this.parent = parent;
			this.children = children != null ? children : this.children;
			// enforceInv();
		}

		public IDTreeNode(IDTreeNode parent) {
			this.parent = parent;
		}

		/**
		 * If invariant is not kept, will throw a runtime exception.
		 * 
		 * @see IDTreeNode#checkInvariant()
		 */
		// private void enforceInv() {
		// if (!checkInvariant()) {
		// throw new RuntimeException("Invariant not maintained!");
		// }
		// }

		/**
		 * Checks to see if invariant has been kept:
		 * <ol>
		 * <li>No siblings contain the same value.</li>
		 * <li>If a node has no parent, it is a head. (No need to check)</li>
		 * <li>If a node is a head, it should have no value.</li>
		 * <li>If a node has no children, it is a leaf. (No need to check)</li>
		 * <li>If a node has no app material, eventually a child does.
		 * <li>
		 * </ol>
		 * 
		 * @return
		 */
		// private boolean checkInvariant() {
		// Set<IDPart> values = new HashSet<IDPart>();
		// if (this.parent == null) // If parent is null, value should be null
		// return value == null;
		// for (IDTreeNode t : this.parent.children.values()) {
		// if (values.contains(t.value)) {
		// Logger.getLogger(GUtilities.loggerName).log(Level.WARNING, "Invariant
		// is not maintained");
		// return false;
		// }
		// }
		// return true;
		// }

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
		public Map<IDPart, IDTreeNode> getChildren() {
			return this.children;
		}

		/**
		 * Returns value of this node.
		 * 
		 * @return
		 */
		// public IDPart getValue() {
		// return value;
		// }

		/**
		 * Returns the child matching this IDPart. (Assumes not by Mem)
		 * 
		 * @param part
		 * @return
		 */
		public IDTreeNode getChild(IDPart part) {
			return children.get(part);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("[");
			sb.append(this.mat == null ? "" : this.mat.toString());
			sb.append("]");
			sb.append(this.children);
			return sb.toString();
		}

		/**
		 * Adds the List of IDParts to the tree, expanding it's branches when
		 * necessary.
		 * 
		 * @param id
		 */
		public IDTreeNode addID(List<IDPart> id) {
			id = new ArrayList<IDPart>(id);
			IDPart crnt = id.get(0);
			IDTreeNode t = addNode(crnt);
			id.remove(0);
			if (id.isEmpty()) {
				t.isWord = true;
				return t;
			} else {
				return t.addID(id);
			}
		}

		public IDTreeNode addNode(IDPart id) {
			if (this.children.containsKey(id)) {
				return this.children.get(id);
			} else {
				IDTreeNode node = new IDTreeNode(this);
				this.children.put(id, node);
				return node;
			}
		}

		public void removeChild(IDPart part) {
			if (!children.get(part).isPartOfWord()) {
				children.remove(part);
			} else {
				children.get(part).isWord = false;
			}
		}

		private boolean isPartOfWord() {
			for (IDTreeNode n : children.values()) {
				if (n.isWord)
					return true;
			}
			return false;
		}

		public IDTreeNode getParent() {
			return parent;
		}

		/**
		 * Utility function, same as addID(id.getParts());
		 * 
		 * @param id
		 * @see IDTreeNode#addID(List)
		 */
		public IDTreeNode addID(ID id) {
			return addID(id.getParts());
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

	public IDTree(AppMaterial mat) {
		IDTreeNode n = head.addID(mat.getUID());
		n.mat = mat;
	}

	public void add(ID id) {
		head.addID(id);
	}

	public void add(List<IDPart> id) {
		head.addID(id);
	}

	public void add(AppMaterial mat) {
		IDTreeNode n = head.addID(mat.getUID());
		n.mat = mat;
	}

	/**
	 * Returns false if id was not able to be removed (because it was not
	 * found).
	 * 
	 * @param id
	 * @return
	 */
	public boolean remove(ID id) {
		if (id == null)
			return false;
		IDTreeNode n = getNode(id);
		if (n == null) {
			return false;
		}
		// Remove child using the id's last element
		n.parent.removeChild(id.getParts().get(id.getParts().size() - 1));
		return true;
	}

	private IDTreeNode getNode(ID id) {
		if (id == null) {
			return null;
		}
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
		ID id0 = new ID("abcd-efgh-ijkl");
		ID id1 = new ID("abcd-efgh-ijkl-mnop");
		ID id2 = new ID("abcd-efgh-qrst");
		Point p0 = new Point("p0", null, id0);
		Point p1 = new Point("p1", null, id1);
		Point p2 = new Point("p2", null, id2);
		IDTree t = new IDTree(p0);
		t.add(p1);
		t.add(p2);

		System.out.println(t);
		IDTreeNode n = t.head.getChild(id0.new IDPart("abcd"));
		System.out.println(n.isWord);
		n = n.getChild(id0.new IDPart("efgh"));
		System.out.println(n);
		System.out.println(n.isWord);
		n = n.getChild(id0.new IDPart("ijkl"));
		System.out.println(n);
		System.out.println(n.isWord);
		n = n.getChild(id0.new IDPart("mnop"));
		System.out.println(n);
		System.out.println(n.isWord);
		n = n.parent.parent.getChild(id0.new IDPart("qrst"));
		System.out.println(n);
		System.out.println(n.isWord);
		ID rid = new ID(id0.toString());
		System.out.println("BAN HAMMER");
		t.remove(rid);
		System.out.println(t);
		System.out.println(t.hasID(new ID("abcdefghijkl", 4)));
		System.out.println(t.hasID(new ID("abcdefghijklmnop", 4)));

		t.add(id2);
		System.out.println(t.head.getChildren());
		System.out.println(t);
	}

}
// id1
// id2
// id3
