package com.smartjobs2.entity;

public class Jobs {
	private int id;
	private String title;
	private String description;
	private String company;

	public Jobs() {
	}

	public Jobs(int id, String title, String description, String company) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.company = company;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override()
	public String toString() {
		return "----------------------------------------\n" + "Job ID: " + this.getId() + "\n" + "Job Title: "
				+ this.getTitle() + "\n" + "Job Description: " + this.getDescription() + "\n" + "Posted by: "
				+ this.getCompany() + "\n" + "----------------------------------------\n";
	}
}
