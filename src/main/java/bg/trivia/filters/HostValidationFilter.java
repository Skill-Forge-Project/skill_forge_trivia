package bg.trivia.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HostValidationFilter implements Filter {

    private static final String ALLOWED_HOST = "localhost";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String hostHeader = httpRequest.getHeader("Host");

        if (hostHeader == null || !hostHeader.startsWith(ALLOWED_HOST)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized Host");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
