package com.ids.webinar_batch.schedulers;

import com.ids.webinar_batch.jobs.BatchConfiguration;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Job1Schedular {

    private final JobLauncher jobLauncher;
    private final BatchConfiguration batchConfiguration;

    public Job1Schedular(JobLauncher jobLauncher, BatchConfiguration batchConfiguration) {
        this.jobLauncher = jobLauncher;
        this.batchConfiguration = batchConfiguration;
    }

    @Scheduled(fixedDelay = 1000L)
    public void runJob1() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("currentTime", System.currentTimeMillis())
                .toJobParameters();

//        JobExecution jobExecution1 = jobLauncher.run(batchConfiguration.job1(), jobParameters);

//        System.out.println("Job 1 status: " + jobExecution1.getStatus());
    }
}
