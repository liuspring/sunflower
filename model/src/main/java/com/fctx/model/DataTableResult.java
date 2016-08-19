package com.fctx.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuJian on 2016/8/19.
 */
public class DataTableResult implements Serializable {

    public int draw;
    public int recordsTotal;
    public int recordsFiltered;
    public List<Object> data;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsTotal;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsTotal;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}