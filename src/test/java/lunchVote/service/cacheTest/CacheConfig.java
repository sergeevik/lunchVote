package lunchVote.service.cacheTest;

import lunchVote.SpringConfigOnTests;
import lunchVote.repository.queryCounter.CountInterceptor;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

public abstract class CacheConfig extends SpringConfigOnTests{

    @Autowired
    protected CacheManager cache;

    @Rule
    public CountInterceptor queryCounter = new CountInterceptor();
}
