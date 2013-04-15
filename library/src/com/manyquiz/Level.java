package com.manyquiz;


public class Level {

	private final String id;
	private final String name;
	private final String level;

	public Level(String id, String name, String level) {
		this.id = id;
		this.name = name;
		this.level = level;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getLevel() {
		return level;
	}
	
	@Override
	public String toString() {
		return String.format("%s (level %s)", name, level);
	}
}
