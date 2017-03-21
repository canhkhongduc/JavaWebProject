/*
 * Copyright © 2017 Six Idiots Team
 */
package filter;

import dao.AccountManager;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import model.Account;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Le Cao Nguyen
 */
@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class CurrentUserUpdateFilter implements Filter {

    private static final boolean debug = false;

    private FilterConfig filterConfig = null;

    public CurrentUserUpdateFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AccountRetrievalFilter:DoBeforeProcessing");
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AccountRetrievalFilter:DoAfterProcessing");
        }
    }

    /**
     *
     * @param req The servlet request we are processing
     * @param res The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain)
            throws IOException, ServletException {
        if (debug) {
            log("AccountRetrievalFilter:doFilter()");
        }

        doBeforeProcessing(req, res);

        Throwable problem = null;
        try {
            if (req instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) req;
                AccountManager manager = new AccountManager();
                Account account = null;
                if (request.getUserPrincipal() != null) {
                    account = manager.getAccount(request.getUserPrincipal().getName(), true);
                }
                request.getSession().setAttribute("currentUser", account);
            }
            chain.doFilter(req, res);
        } catch (Throwable t) {
            problem = t;
        }

        doAfterProcessing(req, res);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            } else if (problem instanceof IOException) {
                throw (IOException) problem;
            } else {
                LoggerFactory.getLogger(this.getClass()).error(null, problem);
            }
        }
    }

    /**
     * Return the filter configuration object for this filter.
     *
     * @return The filter configuration object
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     *
     * @param filterConfig The filter configuration object
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AccountRetrievalFilter:Initializing filter");
            }
        }
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
