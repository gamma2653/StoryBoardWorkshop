package com.gamsionworks.chris.storyboardworkshop.storyboard.materials;

public class Start extends Point implements AppMaterial {
	public Start(String name, String description, String uid) {
		super(name, description, uid);
	}

	@Override
	public String getTypeName() {
		return "Start Point";
	}

}
