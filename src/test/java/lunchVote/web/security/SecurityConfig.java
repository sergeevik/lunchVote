package lunchVote.web.security;

import lunchVote.model.User;
import lunchVote.web.MvcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ContextConfiguration({"classpath:spring/spring-security.xml"})
public abstract class SecurityConfig extends MvcConfig {

    @Autowired
    private WebApplicationContext webApplicationContext;


    protected MockMvc mockToSecurity(){
        return MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }

    public static RequestPostProcessor fakeHttpBasic() {
        return SecurityMockMvcRequestPostProcessors.httpBasic("", "");
    }
}
