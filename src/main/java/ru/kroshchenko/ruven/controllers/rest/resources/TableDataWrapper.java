package ru.kroshchenko.ruven.controllers.rest.resources;

import java.util.List;

/**
 * http://www.datatables.net/manual/server-side
 *
 * @author Aleksandr Streltsov
 *         2016.03.31
 */
public class TableDataWrapper {

    private Integer draw;
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private List<TableDataResource> data;
    private String error;

    public TableDataWrapper() {
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<TableDataResource> getData() {
        return data;
    }

    public void setData(List<TableDataResource> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
