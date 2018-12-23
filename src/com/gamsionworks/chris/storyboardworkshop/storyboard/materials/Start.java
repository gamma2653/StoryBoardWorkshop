package com.gamsionworks.chris.storyboardworkshop.storyboard.materials;

import com.gamsionworks.chris.storyboardworkshop.utility.ID;

public class Start extends Point implements AppMaterial {
	public Start(String name, String description, ID uid) {
		super(name, description, uid);
	}

	@Override
	public String getTypeName() {
		return "Start Point";
	}

}
