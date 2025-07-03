interface Pagination {
  currentPage: number;
  lastVisiblePage: number;
  hasNextPage: boolean;
  hasPreviousPage: boolean;
  elementsPerPage: number;
  totalElements: number;
}

export default Pagination;
