package com._520it.wms.query;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Setter@Getter
public class StockOutcomeBillQueryObject extends QueryObject {

    private Date beginDate;
    private Date endDate;
    private Long depotId = -1L;
    private Long clientId = -1L;
    private int status = -1;

    public Date getBeginDate() {
        if (beginDate == null) {
            return null;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(beginDate);
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        return instance.getTime();

    }

    public Date getEndDate() {

        if (beginDate == null) {
            return null;
        }

        Calendar instance = Calendar.getInstance();
        instance.setTime(endDate);
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 59);

        return instance.getTime();

    }

}
