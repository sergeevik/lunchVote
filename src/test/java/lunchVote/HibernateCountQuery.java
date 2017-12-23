package lunchVote;

import org.hibernate.EmptyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class HibernateCountQuery extends EmptyInterceptor{
    private static final Logger LOG = LoggerFactory.getLogger(HibernateCountQuery.class);

    private static AtomicInteger countQuery = new AtomicInteger(0);
    @Override
    public String onPrepareStatement(String sql) {
        countQuery.incrementAndGet();
        return super.onPrepareStatement(sql);
        //вызывается на каждый запрос
    }

    public static int getCountQuery() {
        return countQuery.get();
    }

    public static void resetCounter(){
        countQuery.set(0);
    }
}
