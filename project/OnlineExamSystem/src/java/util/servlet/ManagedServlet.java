/*
 * Copyright © 2017 Six Idiots Team
 */
package util.servlet;

import io.mikael.urlbuilder.UrlBuilder;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class enforces some constraints on servlet registration, as well as
 * providing methods for easier servlet management.
 * <h3>What is a 'managed' servlet?</h3>
 * A servlet considered to be 'managed' must satisfy the following requirements:
 * <ul>
 * <li>Servlet must be registered (either in web.xml or annotations).</li>
 * <li>Servlet's name must be the same as servlet's fully qualified class
 * name.</li>
 * <li>Servlet must be mapped to exactly one URL.</li>
 * </ul>
 * <h3>Recommendations</h3>
 * It is recommended that servlet's class name should be in the form of
 * <b><u>CategoryAction</u>Controller</b>, and the mapped URL should be
 * <i>.../category/action</i>. For example, a servlet class named
 * <b>SubjectAddController</b> should be mapped to <i>/subject/add</i> or
 * <i>/manage/subject/add</i>.<br><br>
 * Note: by setting the <i>strictServletNameAndUrl</i> context parameter value
 * to
 * <i>true</i>, the name and URL of the servlet will be checked against the
 * above recommendations at initialization phase.
 *
 * @author Le Cao Nguyen
 */
public class ManagedServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagedServlet.class);

    /**
     * Return whether the name and URL of the servlet must be strictly checked.
     *
     * @return true/false.
     */
    private boolean isStrictServletNameAndUrl() {
        return Boolean.parseBoolean(getServletContext().getInitParameter("strictServletNameAndUrl"));
    }

    /**
     * Return the context path.<br>
     * This method is a shorthand of
     * {@link javax.servlet.ServletContext#getContextPath() ServletContext.getContextPath()}.
     *
     * @return The context path.
     */
    public String getContextPath() {
        return getServletContext().getContextPath();
    }

    /**
     * Return the absolute URL of a relative URL.<br>
     * This method will check if the URL is relative or not. If so, it will
     * concatenate the context path with the relative URL.
     *
     * @param relativeUrl The relative URL.
     * @return The absolute URL.
     */
    public String getAbsoluteUrl(String relativeUrl) {
        String absoluteUrl = "";
        if (!java.net.URI.create(relativeUrl).isAbsolute()) {
            absoluteUrl = getContextPath();
        }
        return absoluteUrl + relativeUrl;
    }

    /**
     * Return the root path where all the JSP files are stored.<br>
     * <h3>Details</h3>
     * This method will try to get the value of a context parameter named
     * <i>jspRootFolder</i> in web.xml. If the parameter does not exist, it will
     * return an empty string, which stands for the <i>web</i> folder of the
     * project.<br>
     * <h3>Recommendations</h3>
     * It is recommended to put all the JSP files inside a subfolder of the
     * <i>WEB-INF</i> folder (e.g: WEB-INF/jsp). The controller should be
     * implemented to use
     * {@link javax.servlet.RequestDispatcher RequestDispatcher} to include the
     * content of JSP file in the response.
     *
     * @return The root path of JSP files.
     */
    public String getJSPRootPath() {
        String jspRootPath = getServletContext().getInitParameter("jspRootPath");
        return Optional.ofNullable(jspRootPath).orElse("");
    }

    /**
     * Return the path of a specific JSP file from its relative path.<br>
     * <h3>Details</h3>
     * This method will return the concatenation of JSP root path and the
     * relative path. (e.g: If the relative path is /subject/list.jsp, and the
     * JSP root path is /WEB-INF/jsp, then the JSP full path is
     * /WEB-INF/jsp/subject/list.jsp).
     *
     * @param relativePath The relative path of the JSP file.
     * @return The full path of the JSP file.
     */
    public String getJSPPath(String relativePath) {
        return getJSPRootPath() + relativePath;
    }

    /**
     * Return the registration information of a specified servlet.
     * <h3>Details</h3>
     * This method is a shorthand of
     * {@link javax.servlet.ServletContext#getServletRegistration(java.lang.String) ServletContext.getServletRegistration(servletName)}.
     *
     * @param servletName The name of the servlet.
     * @return The registration information of the servlet.
     */
    public ServletRegistration getServletRegistration(String servletName) {
        return this.getServletContext().getServletRegistration(servletName);
    }

    /**
     * Return the URL mapped to this servlet.
     *
     * @return The URL mapped to this servlet.
     */
    public String getServletURL() {
        return ManagedServlet.this.getServletURL(getServletName());
    }

    /**
     * Return the URL mapped to a managed servlet.
     *
     * @param servletClass The class of the managed servlet.
     * @return The URL mapped to the servlet.
     */
    public String getServletURL(Class<? extends ManagedServlet> servletClass) {
        return ManagedServlet.this.getServletURL(servletClass.getName());
    }

    /**
     * Return the URL mapped to a specified servlet.
     * <h3>Details</h3>
     * This method will read the mapping from the corresponding
     * {@link javax.servlet.ServletRegistration ServletRegistration} of the
     * servlet.
     *
     * @param servletName The name of the servlet.
     * @return The URL mapped to the servlet.
     */
    public String getServletURL(String servletName) {
        ServletRegistration registration = getServletRegistration(servletName);
        if (registration == null) {
            return null;
        }
        return registration.getMappings().stream().findFirst().orElse(null);
    }

    /**
     * Return the {@link javax.servlet.RequestDispatcher RequestDispatcher}
     * object of this servlet.
     *
     * @return The request dispatcher of this servlet.
     */
    public RequestDispatcher getServletDispatcher() {
        return getServletDispatcher(getServletName());
    }

    /**
     * Return the {@link javax.servlet.RequestDispatcher RequestDispatcher}
     * object of a managed servlet.
     *
     * @param servletClass The class of the managed servlet.
     * @return The request dispatcher of the servlet.
     */
    public RequestDispatcher getServletDispatcher(Class<? extends ManagedServlet> servletClass) {
        return getServletDispatcher(servletClass.getName());
    }

    /**
     * Return the {@link javax.servlet.RequestDispatcher RequestDispatcher}
     * object of a specified servlet.
     * <h3>Details</h3>
     * This method is a shorthand of
     * {@link javax.servlet.ServletContext#getNamedDispatcher(java.lang.String) ServletContext.getNamedDispatcher(servletName)}.
     *
     * @param servletName The name of the servlet.
     * @return The request dispatcher of the specified servlet.
     */
    public RequestDispatcher getServletDispatcher(String servletName) {
        return getServletContext().getNamedDispatcher(servletName);
    }

    /**
     * Return the {@link javax.servlet.RequestDispatcher RequestDispatcher}
     * object of the view (JSP file) corresponding to this servlet.
     *
     * @return The request dispatcher of the corresponding view.
     */
    public RequestDispatcher getCorrespondingViewDispatcher() {
        return getCorrespondingViewDispatcher(getServletName());
    }

    /**
     * Return the {@link javax.servlet.RequestDispatcher RequestDispatcher}
     * object of the view (JSP file) corresponding to a managed servlet.
     *
     * @param servletClass The class of the managed servlet.
     * @return The request dispatcher of the corresponding view.
     */
    public RequestDispatcher getCorrespondingViewDispatcher(Class<? extends ManagedServlet> servletClass) {
        return getCorrespondingViewDispatcher(servletClass.getName());
    }

    /**
     * Return the {@link javax.servlet.RequestDispatcher RequestDispatcher}
     * object of the view (JSP file) corresponding to a specified servlet.
     * <h3>Details</h3>
     * This method will search for the JSP file to be used as a view for a
     * controller servlet:
     * <ol>
     * <li>By default, the relative path of the JSP file is the servlet's URL
     * plus the <i>.jsp</i> suffix (e.g: <i>/subject/list.jsp</i>).</li>
     * <li>If no JSP file exists at that path, then it will test another path,
     * which is is the servlet's URL plus the <i>/index.jsp</i> suffix (e.g:
     * <i>/subject/list/index.jsp</i>).</li>
     * </ol>
     * Then the method will return the request dispatcher of the JSP file.
     *
     * @param servletName The name of the servlet.
     * @return The request dispatcher of the corresponding view.
     */
    public RequestDispatcher getCorrespondingViewDispatcher(String servletName) {
        String servletUrl = ManagedServlet.this.getServletURL(servletName);
        String viewPath = getJSPPath(servletUrl + ".jsp");
        try {
            if (getServletContext().getResource(viewPath) == null) {
                String viewPath2 = getJSPPath(servletUrl + "/index.jsp");
                if (getServletContext().getResource(viewPath2) != null) {
                    viewPath = viewPath2;
                }
            }
        } catch (MalformedURLException ex) {
            LOGGER.error(null, ex);
        }
        return getServletContext().getRequestDispatcher(viewPath);
    }

    /**
     * Perform a redirection to a destination URL, with (or without) query
     * parameter(s).<br>
     * <h3>Details</h3>
     * This method will do the following steps:
     * <ol>
     * <li>Check if the destination URL is absolute or not. If not, then convert
     * it to absolute URL.</li>
     * <li>Use
     * {@link util.servlet.ManagedServlet#buildUrl(java.lang.String, java.lang.String...) buildUrl()}
     * method to build the query URL.</li>
     * <li>Perform redirection to the query URL, using the given response
     * object.</li>
     * </ol>
     *
     * @param response The response object.
     * @param destinationUrl The destination URL.
     * @param params The optional query parameters.
     */
    public void redirect(HttpServletResponse response, String destinationUrl, String... params) {
        if (!java.net.URI.create(destinationUrl).isAbsolute()) {
            destinationUrl = getAbsoluteUrl(destinationUrl);
        }
        String url = buildUrl(destinationUrl, params);
        try {
            response.sendRedirect(url);
        } catch (IOException ex) {
            LOGGER.error(null, ex);
        }
    }

    /**
     * Build query URL from a source URL and a list of query parameters.<br>
     * The parameters should be a sequence of name-value pair. For example, if
     * the source URL is <i>"/search"</i>, and the parameters are <i>"q",
     * "hello", "page", "1"</i>, then the result URL is
     * <i>/search?q=hello&page=1</i>
     *
     * @param sourceUrl The source URL.
     * @param params The query parameters.
     * @return The result URL.
     */
    public static String buildUrl(String sourceUrl, String... params) {
        UrlBuilder builder = UrlBuilder.fromString(sourceUrl);
        for (int i = 0; i < params.length / 2; i++) {
            builder = builder.addParameter(params[2 * i], params[2 * i + 1]);
        }
        return builder.toString();
    }

    /**
     * Verify if the servlet satisfies the requirements of a 'managed' servlet.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        String servletName = getServletName();
        String servletUrl = getServletURL();
        if (!this.getClass().getName().equals(servletName)) {
            throw new ServletException("Servlet's name must be the same as servlet's class name.");
        }
        ServletRegistration registration = getServletRegistration(servletName);
        if (registration == null) {
            throw new ServletException("Servlet must be registered.");
        }
        if (registration.getMappings().size() != 1) {
            throw new ServletException("Servlet must be mapped to exactly one URL.");
        }
        if (isStrictServletNameAndUrl()) {
            if (!servletName.endsWith("Controller")) {
                throw new ServletException("Servlet's name must end with 'Controller'.");
            }
            if (!servletUrl.matches("^(\\/[\\w-]+)+$")) {
                throw new ServletException("Servlet's URL must be in the form of .../category/action.");
            }
        }
    }
}
