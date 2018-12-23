package com.gamsionworks.chris.storyboardworkshop.storyboard.materials;

import com.gamsionworks.chris.storyboardworkshop.utility.ID;

public class End extends Point implements AppMaterial {
	public End(String name, String description, ID uid) {
		super(name, description, uid);
	}

	@Override
	public String getTypeName() {
		return "End Point";
	}

}
