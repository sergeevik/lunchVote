package lunchVote.repository.queryCounter;

import org.hibernate.EmptyInterceptor;
import org.junit.Assert;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CountInterceptor extends EmptyInterceptor implements TestRule{
    private static final Logger LOG = LoggerFactory.getLogger(CountInterceptor.class);

    private static int actualCount = 0;
    private int limit = -1;
    @Override
    public String onPrepareStatement(String sql) {
        actualCount++;
        return super.onPrepareStatement(sql);
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                resetCounter();
                base.evaluate();
                if (limit > 0) {
                    logCountQuery(description.getTestClass().getSimpleName(), description.getMethodName(), actualCount);
                    if (actualCount > limit)
                        Assert.fail(failString(description.getTestClass().getSimpleName(), description.getMethodName()));
                }
            }
        };
    }

    private static void resetCounter(){
        actualCount = 0;
    }


    private String failString(String className, String methodName) {
        return String.format("%s.%s: The number of requests to the database exceeded the limit. Limit=%d. In test=%d",
                className, methodName, limit, actualCount);
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    private void logCountQuery(String className, String methodName, int countQuery){
        LOG.info("{}.{}: Количество запросов = {}",className, methodName, countQuery);
    }

}
