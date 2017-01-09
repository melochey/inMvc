package com.example.model;

public class Stu {

	private int id;
	private String name;
	private int fid;

	public Stu() {
	}

	public Stu(String name, int fid) {
		this.name = name;
		this.fid = fid;
		System.out.println("init a stu instance!");
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getFid() {
		return fid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
