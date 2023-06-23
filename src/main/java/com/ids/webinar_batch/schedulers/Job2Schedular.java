package com.ids.webinar_batch.schedulers;

import com.ids.webinar_batch.jobs.BatchConfiguration;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Job2Schedular {

    private final JobLauncher jobLauncher;
    private final BatchConfiguration batchConfiguration;

    public Job2Schedular(JobLauncher jobLauncher, BatchConfiguration batchConfiguration) {
        this.jobLauncher = jobLauncher;
        this.batchConfiguration = batchConfiguration;
    }

    @Scheduled(fixedDelay = 10 * 1000L)
    public void runJob2() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("currentTime", System.currentTimeMillis())
                .toJobParameters();

//        JobExecution jobExecution2 = jobLauncher.run(batchConfiguration.job2(), jobParameters);

//        System.out.println("Job 2 status: " + jobExecution2.getStatus());
    }
}
