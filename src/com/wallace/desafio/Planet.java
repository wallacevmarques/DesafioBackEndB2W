package com.wallace.desafio;

public class Planet {

	private String _id;
	private String name;
	private String climate;
	private String terrain;
	private int apparitionsNumber;
	
	public Planet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Planet(String name, String climate, String terrain) {
		super();
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClimate() {
		return climate;
	}
	public void setClimate(String climate) {
		this.climate = climate;
	}
	public String getTerrain() {
		return terrain;
	}
	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}
	public int getApparitionsNumber() {
		return apparitionsNumber;
	}
	public void setApparitionsNumber(int apparitionsNumber) {
		this.apparitionsNumber = apparitionsNumber;
	}
	
}
