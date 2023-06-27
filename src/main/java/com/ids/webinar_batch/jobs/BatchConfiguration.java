package com.ids.webinar_batch.jobs;

import com.ids.webinar_batch.tasklets.UserSleepNotiTasklet;
import com.ids.webinar_batch.tasklets.UserSleepTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UserSleepTasklet userSleepTasklet;
    private final UserSleepNotiTasklet userSleepNotiTasklet;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, UserSleepTasklet userSleepTasklet, UserSleepNotiTasklet userSleepNotiTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.userSleepTasklet = userSleepTasklet;
        this.userSleepNotiTasklet = userSleepNotiTasklet;
    }

    @Bean
    public Job userSleepNotiJob() {
        return jobBuilderFactory.get("userSleepNotiJob")
                .start(userSleepNotiStep())
                .build();
    }

    @Bean
    public Step userSleepNotiStep() {
        return stepBuilderFactory.get("userSleepNotiStep")
                .tasklet(userSleepNotiTasklet)
                .build();
    }

    @Bean
    public Job userSleepJob() {
        return jobBuilderFactory.get("userSleepJob")
                .start(userSleepStep())
                .build();
    }

    @Bean
    public Step userSleepStep() {
        return stepBuilderFactory.get("userSleepStep")
                .tasklet(userSleepTasklet)
                .build();
    }

}
