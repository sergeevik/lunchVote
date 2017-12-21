package lunchVote;

import org.springframework.test.context.ActiveProfilesResolver;

public class DbProfileResolver implements ActiveProfilesResolver {
    @Override
    public String[] resolve(Class<?> testClass) {
        return new String[]{Profiles.getActiveDbProfile()};
    }
}
