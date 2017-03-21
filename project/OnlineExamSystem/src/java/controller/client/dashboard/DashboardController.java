/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.client.dashboard;

import dao.TestManager;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Test;
import util.datetime.TimeRange;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/client/dashboard")
@ServletSecurity(@HttpConstraint(rolesAllowed = "student"))
public class DashboardController extends ManagedServlet {

    private TimeRange fromStartToEndOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date timeFrom = calendar.getTime();
        calendar.roll(Calendar.DATE, true);
        Date timeTo = calendar.getTime();
        return new TimeRange(timeFrom, timeTo);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account currentUser = (Account) request.getSession().getAttribute("currentUser");
        if (!currentUser.hasRole("student")) {
            response.sendError(403, "You must be a student to view dashboard.");
            return;
        }
        TestManager testManager = new TestManager();
        TimeRange timeRange = fromStartToEndOfToday();
        List<Test> tests = testManager.getTestsByDateRange(timeRange.getTimeFrom(), timeRange.getTimeTo(), true);
        tests = tests.stream().filter(test -> Boolean.FALSE.equals(test.getRestricted()) 
                || test.getExaminees().contains(currentUser)).collect(Collectors.toList());
        request.setAttribute("tests", tests);
        getCorrespondingViewDispatcher().forward(request, response);
    }
}
