package com.smartjobs2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import com.smartjobs2.entity.AppliedJobs;
import com.smartjobs2.entity.Jobs;

public class SmartJobsApplication {
	private static Scanner scanner = new Scanner(System.in);
	private static final String jobsFile = "src\\com\\smartjobs2\\resources\\jobs.tsv";
	private static final Path jobsPath = Path.of(jobsFile);
	private static final String appliedJobs = "src\\com\\smartjobs2\\resources\\appliedJobs.csv";
	private static final Path appliedJobsPath = Path.of(appliedJobs);
	private static final String usersFile = "src\\com\\smartjobs2\\resources\\users.txt";
	private static final Path usersPath = Path.of(usersFile);

	private static void print(String message) {
		System.out.println(message);
	}

	private static void print(String color, String message) {
		System.out.println(color + message);
	}

	private static List<Jobs> getJobs() {
		List<Jobs> jobs = new ArrayList<Jobs>();
		Jobs job;
		try {
			List<String> lines = Files.readAllLines(jobsPath);
			int count = lines.size();
			String[] jobEntry;
			for (int i = 1; i < count; i++) {
				jobEntry = lines.get(i).split("\t");
				if (4 == jobEntry.length) {
					job = new Jobs();
					job.setId(Integer.parseInt(jobEntry[0]));
					job.setTitle(jobEntry[1]);
					job.setDescription(jobEntry[2]);
					job.setCompany(jobEntry[3]);
					jobs.add(job);
				} else {
					continue;
				}

			}
		} catch (IOException e) {
			// handle IO Exception

		}
		return jobs;

	}

	private static void apply(String userName, List<Jobs> jobs) {
		String message = "Please enter the ID of a job to apply for it\nEnter Q to quit";
		// String input = "";
		int id = 0;
		boolean applied = false;
		do {
			String input = getInput(message);
			try {
				id = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				if (input.equalsIgnoreCase("Q")) {
					print("bye");
					System.exit(0);
				}
			}

			List<Integer> ids = jobs.stream().map(j -> j.getId()).toList();
			if (!ids.contains(id)) {
				message = "You have entered a job ID that does not exist or not listed here\nPlease enter an existing job ID or press Q to quit";
			} else {
				// check if candidate has already applied for this job
				try (Stream<String> lines = Files.lines(appliedJobsPath)) {
					long i = lines.filter(n -> n.startsWith(input) && n.contains(userName) && n.contains(input))
							.count();
					if (i > 0) {
						message = "you have already applied for this job\nPlease enter an ID of another job or press Q to quit";
					} else {
						// candidate can now submit application
						var job = jobs.get(id);
						String entry = "\n" + id + "#" + job.getTitle() + "#" + job.getDescription() + "#"
								+ job.getCompany() + "#" + userName;
						// add entry to applied jobs file
						Files.writeString(appliedJobsPath, entry, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
						applied = true;
						print("Thanks, Your application has been submitted");
					}
				} catch (IOException e) {
				}

			}
		} while (false == applied);

	}

	private static List<AppliedJobs> viewApplications(String userName) {
		List<AppliedJobs> jobs = new ArrayList<AppliedJobs>();
		AppliedJobs job;

		try {
			Stream<String> lines = Files.lines(appliedJobsPath).filter(j -> j.contains(userName));
			List<String> entries = lines.toList();
			int count = entries.size();
			if (count == 0) {
				print("you have not received any application");
				return null;
			} // end if
			else {
				String[] jobEntry;
				for (int i = 0; i < count; i++) {
					jobEntry = entries.get(i).split("#");
					if (jobEntry.length != 5) {
						continue;
					} else {
						job = new AppliedJobs();
						job.setId(Integer.parseInt(jobEntry[0]));
						job.setTitle(jobEntry[1]);
						job.setDescription(jobEntry[2]);
						job.setCompany(jobEntry[3]);
						job.setCandidate(jobEntry[4]);
						jobs.add(job);

						/*
						 * job = new AppliedJobs( job.setId(Integer.parseInt(jobEntry[0])),
						 * job.setTitle(jobEntry[1]), job.setDescription(jobEntry[2]),
						 * job.setCompany(jobEntry[3]), job.setCandidate(jobEntry[4]) );
						 * 
						 */

					}

				} // end for

			} // end else

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return jobs;

	}

	private static boolean verifyUser(String userName) {
		boolean userFound = false;
		try {
			List<String> users = Files.readAllLines(usersPath);
			// all data entry for usernames are in lower case
			if (users.contains(userName)) {
				userFound = true;
			}
		} catch (NoSuchFileException ex) {
			System.out.println("Job file cannot be located");
		} catch (IOException ex) {
			System.out.println("an error occured");
		}
		return userFound;

	}

	private static String getInput(String prompt) {
		String input = "";
		try {
			print(prompt);
			input = scanner.nextLine();
		} catch (Exception e) {
			print("Error reading input");
			e.printStackTrace();
			System.exit(0);
		}
		return input;
	}

	public static void main(String[] args) {
		// CONSOLE TEXT COLOR
		String color = "";
		final String userName;
		boolean isCandadate = true;
		String prompt = "";
		boolean userVerified = false;
		try {
			// SAMPLES
			// candidate1@email.com
			// company1@email.com
			userName = getInput("Please enter your username to login").toLowerCase();
			if (verifyUser(userName) == true) {
				if (userName.startsWith("candidate")) {
					color = "\u001B[33m";// YELLOW
					prompt = "Enter 1 to view all jobs\nEnter 2 to search jobs by keyword\nEnter any other key to quit\n";
				}
				if (userName.startsWith("company")) {
					isCandadate = false;
					color = "\u001B[31m";// RED
					prompt = "Enter 1 to view all your job listing\nEnter 2 view jobs candidates have applied for\nEnter any other key to quit\n";
				}

			}
			// when user cannot be verified
			else {
				print("You are not registered with us");
				System.exit(0);
			}

			// welcome verified users
			print(color + "Welcome " + userName + "\n");
			String input = "";
			input = getInput(prompt);
			List<Jobs> jobs;

			switch (input.trim()) {
			case "1": /* viewing all jobs */
				if (isCandadate) {
					jobs = getJobs();
					for (var job : jobs) {
						print(job.toString());
					}
					apply(userName, jobs);
				} else {
					jobs = getJobs().stream().filter(job -> job.getCompany().equalsIgnoreCase(userName)).toList();
					for (var job : jobs) {
						print(job.toString());
					}
				}

				break;

			case "2": /* enter search term or keyword to get jobs */
				/* example php, java */
				if (isCandadate) {
					String keyword = getInput("Please enter search keyword to view job listing");
					jobs = getJobs().stream().filter(job -> job.getTitle().contains(keyword)).toList();
					for (var job : jobs) {
						print(job.toString());
					}
					apply(userName, jobs);
				} else {
					List<AppliedJobs> appliedJobs = viewApplications(userName);
					if (appliedJobs != null) {
						for (var job : appliedJobs) {
							print(job.toString());
						}
					}

				}

				break;
			default: /* user is not interested to view jobs */
				print("Thanks for using our website\n");

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		// END
		print("THANK YOU");
		scanner.close();

	}

}
