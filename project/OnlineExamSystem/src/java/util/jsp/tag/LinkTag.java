/*
 * Copyright © 2017 Six Idiots Team
 */
package util.jsp.tag;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Le Cao Nguyen
 */
public class LinkTag extends SimpleTagSupport {

    private String href;

    /**
     * Called by the container to invoke this tag. The implementation of this
     * method is provided by the tag library developer, and handles all tag
     * processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException, IOException {
        JspContext jspContext = getJspContext();
        String result;
        if (href != null) {
            result = href;
        } else {
            StringWriter sw = new StringWriter();
            getJspBody().invoke(sw);
            result = sw.toString();
        }
        if (href.startsWith("/")) {
            if (jspContext instanceof PageContext) {
                PageContext context = (PageContext) jspContext;
                String contextPath = context.getServletContext().getContextPath();
                result = contextPath + result;
            }
        }
        jspContext.getOut().print(result);

    }

    public void setHref(String href) {
        this.href = href;
    }
}
