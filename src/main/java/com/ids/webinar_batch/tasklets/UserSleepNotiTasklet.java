package com.ids.webinar_batch.tasklets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.mcircle.email.WebinarEmailSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@StepScope
public class UserSleepNotiTasklet implements Tasklet {

    private final JdbcTemplate jdbcTemplate;

    @Value("${module.apikey}")
    private String MODULE_API_KEY;

    @Autowired
    public UserSleepNotiTasklet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        List<String> userIdList = new ArrayList<>();

        // 휴면처리 되기 30일 전 노티할 고객 타켓팅
        String notiTargetSql = "SELECT user.usn, user.user_id, user.user_name, user.mobile" +
                " FROM user JOIN user_login_log ON user.usn = user_login_log.usn" +
                " WHERE 1=1" +
                " AND NOT EXISTS (SELECT 1 FROM user_sleep WHERE user_sleep.usn = user.usn)" +
                " GROUP BY user.usn HAVING MAX(user_login_log.login_dt) < (NOW() - INTERVAL 1 YEAR - INTERVAL 30 DAY)";
//        jdbcTemplate.queryForList(notiTargetSql);

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(notiTargetSql);
        for (Map<String, Object> row : rows) {
            String userId = (String) row.get("user_id");
            userIdList.add(userId);

            WebinarEmailSender emailSender = new WebinarEmailSender();
            System.out.println("userId : " + userId);
//            emailSender.sendPasswordTemp(MODULE_API_KEY, userId ,"111");
        }

//        String[] userId = userIdList.toArray(new String[0]);

//        WebinarEmailSender emailSender = new WebinarEmailSender();
//        emailSender.sendUserSleep(MODULE_API_KEY, userId[0]);

        return RepeatStatus.FINISHED;
    }

}
