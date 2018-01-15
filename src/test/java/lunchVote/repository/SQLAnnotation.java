package lunchVote.repository;


import lunchVote.SpringConfigOnTests;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@Sql(scripts = "classpath:db/populateDb.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class SQLAnnotation extends SpringConfigOnTests {
}
