package ru.kroshchenko.ruven.portal;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PortalAuthenticationFilter implements Filter {
    private FilterConfig config;

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession();
        if (session.getAttribute(PortalAuthenticationBean.AUTH_KEY) == null
                || session.getAttribute(PortalAuthenticationBean.DEPARTMENT_KEY) == null) {
            ((HttpServletResponse) resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/portal/login.xhtml");
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void destroy() {
        config = null;
    }
}