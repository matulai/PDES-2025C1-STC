package SeguiTusCompras.Controller.dtos;

import SeguiTusCompras.Service.utils.Pagination;
import lombok.Data;

import java.util.List;

@Data
public class PaginationElementDto<T> {
    private Pagination<T> pagination;
    private List<T> data;

    public PaginationElementDto(List<T> data, Pagination<T> pagination) {
        this.pagination = pagination;
        this.data = data;
    }
}
