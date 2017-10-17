package com._520it.wms.query;

import java.util.Calendar;
import java.util.Date;

public class OrderBillQueryObject extends QueryObject {

    private Date beginDate;
    private Date endDate;
    private Long supplierId = -1L;
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

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
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

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
