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

    // 휴면처리 데이터이관 Job 스케쥴러
//    @Scheduled(cron = "0 0 9 * * *")  // 매일 오전 9시
    @Scheduled(fixedDelay = 300 * 1000)  // 이전 작업 종료 후 300초 후 실행
    public void runUserSleepJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("currentTime", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(batchConfiguration.userSleepJob(), jobParameters);
//        System.out.println("userSleepJob status: " + jobExecution.getStatus());

    }

    // 휴면처리 노티 Job 스케쥴러
    //    @Scheduled(cron = "0 0 9 * * *")  // 매일 오전 9시
    @Scheduled(fixedDelay = 300 * 1000)  // 이전 작업 종료 후 300초 후 실행
    public void runUserSleepNotiJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("currentTime", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(batchConfiguration.userSleepNotiJob(), jobParameters);
//        System.out.println("userSleepJob status: " + jobExecution.getStatus());

    }
}
