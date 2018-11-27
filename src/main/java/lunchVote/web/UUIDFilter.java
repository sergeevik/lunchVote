package lunchVote.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

public class UUIDFilter implements Filter {
    private Logger LOGGER = LoggerFactory.getLogger(UUIDFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.debug("Servlet init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MDC.put("myUuid", UUID.randomUUID().toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.debug("Servlet init");
    }
}
