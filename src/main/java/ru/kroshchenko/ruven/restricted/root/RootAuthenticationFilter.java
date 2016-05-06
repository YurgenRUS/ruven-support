package ru.kroshchenko.ruven.restricted.root;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RootAuthenticationFilter implements Filter {
    private FilterConfig config;

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession();
        if (session.getAttribute(RootAuthenticationBean.AUTH_KEY) == null) {
            ((HttpServletResponse) resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/root/login.xhtml");
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