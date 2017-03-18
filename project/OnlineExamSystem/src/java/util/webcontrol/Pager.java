/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.webcontrol;

/**
 *
 * @author Niles
 */
public class Pager {

    public static String render(int pageIndex, int totalPage, int steps,
            String page, String paramQuery, boolean isFirstParam) {

        String output = "";
        if (pageIndex - steps >= steps) {
            String firstUrl = isFirstParam ? (page + "?" + paramQuery + "=1") : (page + "&" + paramQuery + "=1");
            output += WebUtil.renderHyperlink(firstUrl, "First", "waves-effect");
            output += " ... ";
        }

        // Render page links before the current page
        for (int i = steps; i > 0; i--) {
            if (pageIndex - i > 0) {
                String pagesBeforeUrl = isFirstParam ? (page + "?" + paramQuery + "="
                        + (pageIndex - i)) : (page + "&" + paramQuery + "="
                        + (pageIndex - i));
                output += WebUtil.renderHyperlink(pagesBeforeUrl, (pageIndex - i) + "", "waves-effect");
            }
        }

        output += "<li class=\"active\"><a href=\"\\#\">" + pageIndex + "</a></li>";

        // Render page links after the current page
        for (int i = 1; i <= steps; i++) {
            if (pageIndex + i <= totalPage) {
                String pagesAfterUrl = isFirstParam ? (page + "?" + paramQuery + "="
                        + (pageIndex + i)) : (page + "&" + paramQuery + "="
                        + (pageIndex + i));
                output += WebUtil.renderHyperlink(pagesAfterUrl, (pageIndex + i) + "", "waves-effect");
            }
        }

        if (pageIndex + steps < totalPage) {
            String lastUrl = isFirstParam ? (page + "?" + paramQuery + "=" + totalPage) : (page + "&" + paramQuery + "=" + totalPage);
            output += " ... ";
            output += WebUtil.renderHyperlink(lastUrl, "Last", "waves-effect");
        }

        return output;
    }

}
