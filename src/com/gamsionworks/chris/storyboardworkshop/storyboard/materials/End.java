package com.gamsionworks.chris.storyboardworkshop.storyboard.materials;

public class End extends Point implements AppMaterial {
	public End(String name, String description, String uid) {
		super(name, description, uid);
	}

	@Override
	public String getTypeName() {
		return "End Point";
	}

}
