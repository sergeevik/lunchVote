package lunchVote.repository;

import lunchVote.DbProfileResolver;
import lunchVote.repository.queryCounter.CountInterceptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(
        {"classpath:spring/spring-test-app.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDb.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = DbProfileResolver.class)
public abstract class SpringConfigOnTests {
    @Rule
    public CountInterceptor countQueries = new CountInterceptor();
}
