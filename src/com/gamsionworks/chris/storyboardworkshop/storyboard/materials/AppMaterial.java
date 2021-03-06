package com.gamsionworks.chris.storyboardworkshop.storyboard.materials;

import java.awt.Image;
import java.util.Map;

import com.gamsionworks.chris.storyboardworkshop.gui.StoryBoardWindow;
import com.gamsionworks.chris.storyboardworkshop.storyboard.StoryBoard;
import com.gamsionworks.chris.storyboardworkshop.utility.ID;

public interface AppMaterial {
	/**
	 * Returns images associated with this material.
	 * @return
	 */
	public Image[] getAttachedImgs();
	
	/**
	 * Attach an image.
	 * @param i
	 */
	public void attachImg(Image i);
	/**
	 * Attaches all images in i.
	 * @param i
	 */
	public void attachImgs(Image[] i);
	/**
	 * Removes image i.
	 * @param i
	 */
	public void removeImg(Image i);
	/**
	 * Removes all images in i.
	 * @param i
	 */
	public void removeImgs(Image[] i);
	/**
	 * Returns name of part. Not unique.
	 * @return
	 */
	public String getTypeName();
	
	/**
	 * Returns name of the specific part. Not necessarily unique.
	 * @return
	 */
	public String getTitle();
	
	/**
	 * Returns the description given by user for part.
	 * @return
	 */
	public String getDescription();
	
	/**
	 * Sets the title to the given String.
	 * 
	 * @param title
	 * @return
	 */
	public void setTitle(String title);
	
	/**
	 * Sets the description to the given String
	 * 
	 * @param description
	 * @return
	 */
	public void setDescription(String description);
	
	/**
	 * ID of part. Unique.
	 * @return
	 */
	public ID getUID();
	
	/**
	 * Sets the ID.
	 * @param UID
	 */
	public void setUID(String UID);

	/**
	 * Sets the ID.
	 * @param UID
	 */
	public void setUID(ID UID);
	
	/**
	 * Auto-generates the ID.
	 */
	public void autoUID();
	
	/**
	 * Opens the edit menu for the part.
	 * @param sbw
	 * @param sb
	 */
	public void edit(StoryBoardWindow sbw, StoryBoard sb);
	
	/**
	 * Returns list of IDs this material is associated to.
	 * Returns an empty map if no associations or type does not have associations.
	 * Never null.
	 * @return
	 */
	public Map<String, ID> getAssociations();
	
}
