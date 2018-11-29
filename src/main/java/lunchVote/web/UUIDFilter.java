package lunchVote.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

public class UUIDFilter implements Filter {
    private static final String UUID_KEY = "uuid";
    private Logger LOGGER = LoggerFactory.getLogger(UUIDFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.debug("Servlet init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            MDC.put(UUID_KEY, UUID.randomUUID().toString());
            filterChain.doFilter(servletRequest, servletResponse);
        }finally {
            MDC.remove(UUID_KEY);
        }
    }

    @Override
    public void destroy() {
        LOGGER.debug("Servlet destroy");
    }
}
