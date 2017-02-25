package util.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author nguyen
 */
public interface ServletMethodInterface {
    default RequestDispatcher getServletDispatcher(ServletContext context, Class<? extends HttpServlet> servletClass) {
        return context.getNamedDispatcher(servletClass.getSimpleName());
    }
}
