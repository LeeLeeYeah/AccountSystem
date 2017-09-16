
package se.manage;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import se.manage.ManageStatus;


@Component
public class ManageFilter implements Filter {
    private Logger logger = Logger.getLogger(ManageFilter.class);

    public ManageFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rsp = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);
        String servletPath = req.getServletPath();
        if (session.getAttribute(Sessionable.Status) == null) {
            session.setAttribute(Sessionable.Status, new ManageStatus());
        }

        if (!servletPath.endsWith(".html") && !servletPath.endsWith(".do")) {
            chain.doFilter(request, response);
        } else if (!servletPath.equals("/login.html") && !servletPath.equals("/register.html") &&
                !servletPath.equals("/login.do") && !servletPath.equals("/register.do")) {
            if (session.getAttribute(Sessionable.LogginFlag) == null || !session.getAttribute(Sessionable.LogginFlag).equals("true")) {
                rsp.sendRedirect("/");
                //chain.doFilter(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }

    }

    public void destroy() {
    }
}
