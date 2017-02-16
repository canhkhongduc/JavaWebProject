package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class SubjectView_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Courses</title>\n");
      out.write("        <link href=\"http://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">\n");
      out.write("        <!--Import materialize.css-->\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/materialize.min.css\"/>\n");
      out.write("        <!--Import jQuery before materialize.js-->\n");
      out.write("        <script type=\"text/javascript\" src=\"https://code.jquery.com/jquery-2.1.1.min.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"js/materialize.min.js\"></script>\n");
      out.write("        <style>\n");
      out.write("            .collapsible-body {\n");
      out.write("                padding: 1rem;\n");
      out.write("            }\n");
      out.write("        </style>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        \n");
      out.write("        <div class=\"container\">\n");
      out.write("            <div class=\"row\">\n");
      out.write("                <div class=\"col s6\">\n");
      out.write("                </div>\n");
      out.write("                <div class=\"col s6\">\n");
      out.write("                    <form>\n");
      out.write("                        <div class=\"input-field\">\n");
      out.write("                            <input id=\"search\" type=\"search\" placeholder=\"Search\" required>\n");
      out.write("                            <label class=\"label-icon\" for=\"search\"><i class=\"material-icons\">search</i></label>\n");
      out.write("                            <i class=\"material-icons\">close</i>\n");
      out.write("                        </div>\n");
      out.write("                    </form>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("            <ul class=\"collapsible highlight\" data-collapsible=\"accordion\">\n");
      out.write("                <li>\n");
      out.write("                    <div class=\"collapsible-header teal text-darken-2\"><i class=\"material-icons\">subject</i>Java Web Application</div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Course creator:<a href=\"#\">Khổng Đức Cảnh</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Teacher:<a href=\"#\">Phan Đăng Lâm</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><a class=\"waves-effect waves-light btn\">Enter course</a></div>\n");
      out.write("                </li>\n");
      out.write("                <li>\n");
      out.write("                    <div class=\"collapsible-header teal text-darken-2\"><i class=\"material-icons\">subject</i>Introduction to Software Engineering</div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Course creator: <a href=\"#\"> Khổng Đức Cảnh</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Teacher: <a href=\"#\"> Phan Đăng Lâm</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><a class=\"waves-effect waves-light btn\">Enter course</a></div>\n");
      out.write("                </li>\n");
      out.write("                <li>\n");
      out.write("                    <div class=\"collapsible-header teal text-darken-2\"><i class=\"material-icons\">subject</i>OOP with Java</div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Course creator: <a href=\"#\"> Khổng Đức Cảnh</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Teacher: <a href=\"#\"> Phan Đăng Lâm</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><a class=\"waves-effect waves-light btn\" href=\"#\">Enter course</a></div>\n");
      out.write("                </li>\n");
      out.write("                <li>\n");
      out.write("                    <div class=\"collapsible-header teal text-darken-2\"><i class=\"material-icons\">subject</i>Programming with C</div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Course creator: <a href=\"#\"> Khổng Đức Cảnh</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Teacher: <a href=\"#\"> Phan Đăng Lâm</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><a class=\"waves-effect waves-light btn\">Enter course</a></div>\n");
      out.write("                </li>\n");
      out.write("                <li>\n");
      out.write("                    <div class=\"collapsible-header teal text-darken-2\"><i class=\"material-icons\">subject</i>OOP with Java</div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Course creator: <a href=\"#\"> Khổng Đức Cảnh</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Teacher: <a href=\"#\"> Phan Đăng Lâm</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><a class=\"waves-effect waves-light btn\" href=\"#\">Enter course</a></div>\n");
      out.write("                </li>\n");
      out.write("                <li>\n");
      out.write("                    <div class=\"collapsible-header teal text-darken-2\"><i class=\"material-icons\">subject</i>OOP with Java</div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Course creator: <a href=\"#\"> Khổng Đức Cảnh</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Teacher: <a href=\"#\"> Phan Đăng Lâm</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><a class=\"waves-effect waves-light btn\" href=\"#\">Enter course</a></div>\n");
      out.write("                </li>\n");
      out.write("                <li>\n");
      out.write("                    <div class=\"collapsible-header teal text-darken-2\"><i class=\"material-icons\">subject</i>OOP with Java</div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Course creator: <a href=\"#\"> Khổng Đức Cảnh</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><span>Teacher: <a href=\"#\"> Phan Đăng Lâm</a></span></div>\n");
      out.write("                    <div class=\"collapsible-body\"><a class=\"waves-effect waves-light btn\" href=\"#\">Enter course</a></div>\n");
      out.write("                </li>\n");
      out.write("            </ul>\n");
      out.write("        </div>\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "Footer.jsp", out, false);
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
