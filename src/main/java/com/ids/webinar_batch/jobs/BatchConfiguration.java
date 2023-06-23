package com.ids.webinar_batch.jobs;

import com.ids.webinar_batch.tasklets.TutorialTasklet;
import com.ids.webinar_batch.tasklets.UserSleepTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
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

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, UserSleepTasklet userSleepTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.userSleepTasklet = userSleepTasklet;
    }

//    @Bean
//    public Job job1() {
//        return jobBuilderFactory.get("job1")
//                .incrementer(new RunIdIncrementer())
//                .start(step1())
//                .build();
//    }
//
//    @Bean
//    public Step step1() {
//        return stepBuilderFactory.get("step1")
//                .tasklet(new TutorialTasklet())
//                .build();
//    }
//
//    @Bean
//    public Job job2() {
//        return jobBuilderFactory.get("job2")
//                .incrementer(new RunIdIncrementer())
//                .start(step2())
//                .build();
//    }
//
//    @Bean
//    public Step step2() {
//        return stepBuilderFactory.get("step2")
//                .tasklet((contribution, chunkContext) -> {
//                    // 두 번째 작업의 실행 로직
//                    System.out.println("Job 2 is running...");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }

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
