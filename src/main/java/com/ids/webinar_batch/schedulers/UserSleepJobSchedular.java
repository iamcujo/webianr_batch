package com.ids.webinar_batch.schedulers;

import com.ids.webinar_batch.jobs.BatchConfiguration;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserSleepJobSchedular {

    private final JobLauncher jobLauncher;
    private final BatchConfiguration batchConfiguration;

    public UserSleepJobSchedular(JobLauncher jobLauncher, BatchConfiguration batchConfiguration) {
        this.jobLauncher = jobLauncher;
        this.batchConfiguration = batchConfiguration;
    }

//    @Scheduled(fixedDelay = 3600000)  // 1시간마다 실행
    @Scheduled(fixedDelay = 5000)  // 5초마다 실행
    public void runUserSleepJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("currentTime", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(batchConfiguration.userSleepJob(), jobParameters);

//        System.out.println("userSleepJob status: " + jobExecution.getStatus());
    }
}
