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

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@StepScope
public class UserSleepTasklet implements Tasklet {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserSleepTasklet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        // 1년간 로그인 이력 없는 고객 데이터 이관처리
        String userSleepInsertSql = "INSERT INTO user_sleep (usn, user_id, password, user_name, mobile, role_cd, work_cd, work_etc, work_name, major_cd, license, is_sms, is_email, join_cd, is_use, reg_dt, upt_dt)" +
                " SELECT user.usn, user.user_id, user.password, user.user_name, user.mobile, user.role_cd, user.work_cd, user.work_etc, user.work_name, user.major_cd, user.license, user.is_sms, user.is_email, user.join_cd, 0 AS is_use, user.reg_dt, NOW() AS upt_dt" +
                " FROM user JOIN user_login_log ON user.usn = user_login_log.usn" +
                " WHERE 1=1" +
                " AND NOT EXISTS (SELECT 1 FROM user_sleep WHERE user_sleep.usn = user.usn)" +
                " GROUP BY user.usn HAVING MAX(user_login_log.login_dt) < DATE_SUB(NOW(), INTERVAL 1 YEAR)";
//        jdbcTemplate.queryForList(userSleepInsertSql);

        // 1년간 로그인 이력 없는 고객 데이터 이관 후 기존 USER 테이블에서 IS_USE = 0 (false) 처리
        String userFlagUpdateSql = "UPDATE user SET is_use = 0 WHERE usn IN (" +
                " SELECT user.usn FROM user JOIN user_login_log ON user.usn = user_login_log.usn WHERE 1=1" +
                " AND EXISTS ( SELECT 1 FROM user_sleep WHERE user_sleep.usn = user.usn )" +
                " GROUP BY user.usn HAVING MAX(user_login_log.login_dt) < DATE_SUB(NOW(), INTERVAL 1 YEAR) )";
//        jdbcTemplate.update(userFlagUpdateSql);

        return RepeatStatus.FINISHED;
    }
}
