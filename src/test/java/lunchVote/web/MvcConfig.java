package lunchVote.web;

import lunchVote.SpringConfigOnTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


@WebAppConfiguration
@ContextConfiguration({"classpath:spring/spring-mvc.xml"})
public abstract class MvcConfig extends SpringConfigOnTests {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    protected MockMvc mockMvcWithController(Object controller){
        return MockMvcBuilders
                .standaloneSetup(controller)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }
}
