package com.gamsionworks.chris.storyboardworkshop.save;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gamsionworks.chris.storyboardworkshop.storyboard.StoryBoard;
import com.gamsionworks.chris.storyboardworkshop.storyboard.materials.AppMaterial;
import com.gamsionworks.chris.storyboardworkshop.utility.ID;

public class DatabaseManager {
	Connection conn;
	private final String database;
	public static final String jdbc = "jdbc:sqlite:";

	public DatabaseManager(String dbname) {
		database = dbname;
		try {
			openConnection(jdbc + dbname);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void openConnection(String dbname) throws SQLException {
		conn = DriverManager.getConnection(jdbc + dbname);

	}

	public void save(StoryBoard sb) {
		SaveData sd = new SaveData();
		for (AppMaterial am : sb.getAllItems()) {
			ID id = am.getUID();
			String type = am.getTypeName();
			String title = am.getTitle();
			String desc = am.getDescription();
			List<String> associations = new ArrayList<String>();
		}
	}

	class SaveData {
		List<ID> ids = new ArrayList<ID>();
		List<String> types = new ArrayList<String>();
		List<String> titles = new ArrayList<String>();
		List<String> descs = new ArrayList<String>();
		List<List<String>> associations = new ArrayList<List<String>>();
		SaveData(List<ID> ids, List<String> types, List<String> titles, List<String> descs,
				List<List<String>> associations) {
			this.ids = ids;
			this.types = types;
			this.titles = titles;
			this.descs = descs;
			this.associations = associations;
		}
		SaveData() {
		}
		void add(ID id, String type, String title, String desc, List<String> associations) {
			ids.add(id);
			types.add(type);
			titles.add(title);
			descs.add(desc);
			this.associations.add(associations);
		}
	}

}
