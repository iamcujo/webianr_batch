package com.ids.webinar_batch;

import com.mcircle.email.WebinarEmailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebinarBatchApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

//	@Test
//	public void contextLoads() {
//	}

	@Test
	public void test_usersleep_noti_target() throws Exception {

		List<String> userIdList = new ArrayList<>();

		String notiTargetSql = "SELECT user.usn, user.user_id, user.user_name, user.mobile" +
				" FROM user JOIN user_login_log ON user.usn = user_login_log.usn" +
				" WHERE 1=1" +
				" AND NOT EXISTS (SELECT 1 FROM user_sleep WHERE user_sleep.usn = user.usn)" +
				" GROUP BY user.usn HAVING MAX(user_login_log.login_dt) < (NOW() - INTERVAL 1 YEAR - INTERVAL 30 DAY) limit 10";

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(notiTargetSql);

		for (Map<String, Object> row : rows) {
			String userId = (String) row.get("user_id");
			userIdList.add(userId);
			System.out.println("userId : " + userId);
		}

	}

}
