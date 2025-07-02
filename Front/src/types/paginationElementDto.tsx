import { Pagination } from "./";

interface PaginationElementDto<T> {
  data: T[];
  pagination: Pagination;
}

export default PaginationElementDto;
