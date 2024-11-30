package com.smartjobs2;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.smartjobs2.entity.Jobs;

class SmartJobsApplicationTest {
	private static final String jobsFile = "src\\com\\smartjobs2\\resources\\jobs.tsv";
	private static final Path jobsPath = Path.of(jobsFile);
	private static final String appliedJobs = "src\\com\\smartjobs2\\resources\\appliedJobs.csv";
	private static final Path appliedJobsPath = Path.of(appliedJobs);
	private static final String usersFile = "src\\com\\smartjobs2\\resources\\users.txt";
	private static final Path usersPath = Path.of(usersFile);

	// @Test
	@ParameterizedTest
	@CsvSource(value = { "0#junior java developer#company1@email.com",
			"4#junior SQL developer#company2@email.com" }, delimiter = '#')
	@DisplayName("Can read jobs csv file")
	final void testGetJobs(int id, String title, String company) {
		SmartJobsApplication app = new SmartJobsApplication();
		List<Jobs> jobs = app.getJobs();
		Jobs job = jobs.get(id);
		Assertions.assertEquals(title, job.getTitle());
		Assertions.assertEquals(company, job.getCompany());
	}

	@ParameterizedTest
	@CsvSource({ "candidate3@email.com, company1@email.com" })
	@DisplayName("Only allow registered users")
	final void testVerifyUser(String userName) {
		SmartJobsApplication app = new SmartJobsApplication();
		Assertions.assertTrue(app.verifyUser(userName));
	}

}
