package lunchVote.service.cacheTest;

import lunchVote.repository.SQLAnnotation;
import lunchVote.repository.queryCounter.CountInterceptor;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

public abstract class CacheConfig extends SQLAnnotation {

    @Autowired
    protected CacheManager cache;

    @Rule
    public CountInterceptor queryCounter = new CountInterceptor();
}
