package lunchVote.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StreamUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Enumeration;

public class LoggerFilter implements Filter {
    /**
     * Логгер.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerFilter.class);

    /**
     * Обёртка для HttpServletResponse.
     */
    private static final class HttpServletResponseWrapperInner extends HttpServletResponseWrapper {
        /**
         * Логгер.
         */
        private final ByteArrayPrinterWrapper pw;

        HttpServletResponseWrapperInner(final HttpServletResponse rs, final ByteArrayPrinterWrapper pw) {
            super(rs);
            this.pw = pw;
        }

        @Override
        public ServletOutputStream getOutputStream() {
            return pw.getStream();
        }
    }

    /**
     * Логгер.
     */
    @Override
    public final void doFilter(final ServletRequest rq, final ServletResponse rs, final FilterChain filterChain) throws IOException, ServletException {
        if (rq instanceof HttpServletRequest && rs instanceof HttpServletResponse) {
            log((HttpServletRequest) rq, (HttpServletResponse) rs, filterChain);
        } else {
            filterChain.doFilter(rq, rs);
        }
    }

    private void log(final HttpServletRequest rq, final HttpServletResponse rs, final FilterChain filterChain) throws IOException, ServletException {
        try {
            long startTime = new Date().getTime();
            BufferedRequestWrapper w = new BufferedRequestWrapper(rq);
            final ByteArrayPrinterWrapper pw = new ByteArrayPrinterWrapper();
            final HttpServletResponse wrappedResp = new HttpServletResponseWrapperInner(rs, pw);

            filterChain.doFilter(w, wrappedResp);

            byte[] respBytes = pw.toByteArray();
            try {
                if (respBytes.length > 0) {
                    rs.getOutputStream().write(respBytes);
                }
            } catch (Exception e) {
                LOGGER.warn("Error write response: " + new String(respBytes, Charset.defaultCharset()), e);
            }
            String rqContentType = rq.getContentType();
            String respContentType = wrappedResp.getContentType();
            StringBuilder builder = new StringBuilder("\n======================= LOGGER ================\n");
            builder.append("URL: ").append(rq.getMethod()).append(" ")
                    .append(rq.getRequestURI()).append("\n")
                    .append(getExecuteTime(startTime))
                    .append("QUERY_PARAMS: \n").append(toStringQueryParam(rq))
                    .append("HEADER: \n").append(toStringHeader(rq)).append("\n");
            if (rqContentType != null && rqContentType.contains("json")) {
                builder.append("RQ: ").append(new String(w.getBuffer(), Charset.defaultCharset())).append("\n");
            }
            if (respContentType != null && respContentType.contains("json")) {
                builder.append("RS: ").append(new String(respBytes, Charset.defaultCharset())).append("\n");
            }

            if (rq.getSession(false) != null) {
                MDC.put("SessionId", rq.getSession().getId());
            } else {
                MDC.put("SessionId", "Not started yet");
            }

            LOGGER.debug(builder.toString());
        } finally {
            MDC.remove("SessionId");
        }
    }

    private String getExecuteTime(long startTime) {
        long nowTime = new Date().getTime();
        long executeTimeMS = nowTime - startTime;
        return "EXECUTE TIME: " + executeTimeMS + " ms \n";
    }

    private String toStringQueryParam(HttpServletRequest rq) {
        StringBuilder builder = new StringBuilder();
        if (rq.getQueryString() != null && rq.getQueryString().length() > 0) {
            String[] split = rq.getQueryString().split("&");
            for (String query : split) {
                builder.append("    ").append(query).append("\n");
            }
        }
        return builder.toString();
    }

    private String toStringHeader(final HttpServletRequest rq) {
        Enumeration<String> headerNames = rq.getHeaderNames();
        StringBuilder builder = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String headName = headerNames.nextElement();
            if (headName != null && !headName.contains("Cookie")) {
                builder.append("    ").append(headName).append(" : ").append(rq.getHeader(headName)).append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }

    /**
     * Логгер.
     */
    private static class ByteArrayServletStream extends ServletOutputStream {
        /**
         * Логгер.
         */
        private ByteArrayOutputStream baos;

        ByteArrayServletStream(final ByteArrayOutputStream baos) {
            this.baos = baos;
        }

        @Override
        public void write(final int b) {
            baos.write(b);
        }

        @Override
        public boolean isReady() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Логгер.
     */
    private static class ByteArrayPrinterWrapper {
        /**
         * Логгер.
         */
        private ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /**
         * Логгер.
         */
        private PrintStream pw = getPS();

        private PrintStream getPS() {
            try {
                return new PrintStream(baos, true, Charset.defaultCharset().name());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Логгер.
         */
        private ServletOutputStream sos = new ByteArrayServletStream(baos);

        public PrintStream getWriter() {
            return pw;
        }

        ServletOutputStream getStream() {
            return sos;
        }

        byte[] toByteArray() {
            return baos.toByteArray();
        }
    }

    /**
     * Логгер.
     */
    private static class BuffServletInputStream extends ServletInputStream {
        /**
         * Логгер.
         */
        private final ByteArrayInputStream is;

        /**
         * Логгер.
         */
        BuffServletInputStream(final ByteArrayInputStream is) {
            this.is = is;
        }

        @Override
        public int read() {
            return is.read();
        }

        @Override
        public boolean isFinished() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isReady() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Логгер.
     */
    private static class BufferedRequestWrapper extends HttpServletRequestWrapper {
        /**
         * Логгер.
         */
        private byte[] buffer;

        /**
         * Логгер.
         */
        BufferedRequestWrapper(final HttpServletRequest rq) throws IOException {
            super(rq);
            try (InputStream is = rq.getInputStream()) {
                buffer = StreamUtils.copyToByteArray(is);
            }
        }

        @Override
        public ServletInputStream getInputStream() {

            return new BuffServletInputStream(new ByteArrayInputStream(buffer));
        }

        byte[] getBuffer() {
            return buffer;
        }
    }
}
