package SeguiTusCompras.Service.utils;

import lombok.Data;

@Data
public class Pagination<T> {
    private int currentPage;
    private int lastVisiblePage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;

    private int elementsPerPage;
    private long totalElements;

    public Pagination(int currentPage, int elementsPerPage, long totalElements) {
        if (elementsPerPage <= 0) throw new IllegalArgumentException("elementsPerPage must be > 0");
        if (currentPage < 1) throw new IllegalArgumentException("currentPage must be >= 1");

        this.currentPage = currentPage;
        this.elementsPerPage = elementsPerPage;
        this.totalElements = totalElements;
        this.lastVisiblePage = (int) Math.ceil((double) totalElements / elementsPerPage);
        this.hasNextPage = currentPage < this.lastVisiblePage;
        this.hasPreviousPage = currentPage > 1;
    }
}
