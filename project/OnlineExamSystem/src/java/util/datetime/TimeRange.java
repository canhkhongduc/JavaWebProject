/*
 * Copyright © 2017 Six Idiots Team
 */
package util.datetime;

import java.util.Date;

/**
 *
 * @author Le Cao Nguyen
 */
public class TimeRange {
    private Date timeFrom;
    private Date timeTo;

    public TimeRange() {
    }

    public TimeRange(Date timeFrom, Date timeTo) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Date timeTo) {
        this.timeTo = timeTo;
    }
    
    
}
