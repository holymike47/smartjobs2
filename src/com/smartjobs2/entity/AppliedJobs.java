package com.smartjobs2.entity;

public class AppliedJobs {
	private int id;
	private String title;
	private String description;
	private String company;
	private String candidate;

	public AppliedJobs() {
	}

	public AppliedJobs(int id, String title, String description, String company, String candidate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.company = company;
		this.candidate = candidate;
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

	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}

	@Override()
	public String toString() {

		return "job Listing for : " + this.getCompany() + "\n" + "----------------------------------------\n"
				+ "Job ID: " + this.getId() + "\n" + "Job Title: " + this.getTitle() + "\n" + "Job Description: "
				+ this.getDescription() + "\n" + "Applied by: " + this.getCandidate() + "\n"
				+ "----------------------------------------\n";
	}

}
