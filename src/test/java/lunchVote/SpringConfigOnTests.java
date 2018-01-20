package lunchVote;

import lunchVote.repository.queryCounter.CountInterceptor;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(
        {"classpath:spring/spring-test-app.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class SpringConfigOnTests {
    @Rule
    public CountInterceptor countQueries = new CountInterceptor();
}
