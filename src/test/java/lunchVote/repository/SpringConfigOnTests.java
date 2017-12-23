package lunchVote.repository;

import lunchVote.DbProfileResolver;
import lunchVote.HibernateCountQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger LOG = LoggerFactory.getLogger(SpringConfigOnTests.class);

    private String className = "";
    private String methodName = "";

    @Rule
    public MethodRule method = (base, method, target) -> {
        className = method.getDeclaringClass().getSimpleName();
        methodName = method.getName();
        return base;
    };

    @Before
    public void before(){
        HibernateCountQuery.resetCounter();
    }

    @After
    public void after(){
        int countQuery = HibernateCountQuery.getCountQuery();
        LOG.info("\n{}.{}: Количество запросов = {}",className, methodName, countQuery);
    }


}
