package com.ids.webinar_batch.tasklets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
public class UserSleepNotiTasklet implements Tasklet {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserSleepNotiTasklet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        // 휴면처리 되기 30일 전 노티할 고객 타켓팅
        String selectSql = "SELECT user.usn, user.user_id, user.user_name, user.mobile" +
                " FROM user JOIN user_login_log ON user.usn = user_login_log.usn" +
                " WHERE 1=1" +
                " AND NOT EXISTS (SELECT 1 FROM user_sleep WHERE user_sleep.usn = user.usn)" +
                " GROUP BY user.usn HAVING MAX(user_login_log.login_dt) < DATE_SUB(NOW(), INTERVAL 1 YEAR)";
//        jdbcTemplate.queryForList(selectSql);

        return RepeatStatus.FINISHED;
    }

}
