/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.client;

import dao.TestManager;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Test;
import util.datetime.TimeRange;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/client/dashboard")
public class DashboardController extends ManagedServlet {

    private TimeRange fromStartToEndOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date timeFrom = calendar.getTime();
        calendar.roll(Calendar.DATE, true);
        Date timeTo = calendar.getTime();
        return new TimeRange(timeFrom, timeTo);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TestManager testManager = new TestManager();
        TimeRange timeRange = fromStartToEndOfToday();
        List<Test> tests = testManager.getTests(timeRange.getTimeFrom(), timeRange.getTimeTo());
        request.setAttribute("tests", tests);
        getCorrespondingViewDispatcher().forward(request, response);
    }
}
