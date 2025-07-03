import { Link, useLocation } from "react-router-dom";
import { Pagination } from "@/types";
import "@/styles/PaginationNav.css";

interface PaginationProps {
  pagination: Pagination;
}

const PaginationNav = ({ pagination }: PaginationProps) => {
  const location = useLocation();

  let actualOptionStartPage = Math.floor(pagination.currentPage / 5) * 5;
  let pageOptionsNumber =
    actualOptionStartPage + 5 <= pagination.lastVisiblePage
      ? 5
      : pagination.lastVisiblePage - actualOptionStartPage;
  let numbersList = Array.from(
    { length: pageOptionsNumber },
    (_, i) => actualOptionStartPage + 1 + i
  );

  return (
    <section className="pagination">
      {actualOptionStartPage > 0 ? (
        <nav className="pagination-nav">
          <Link
            to={`${location.pathname}?page=${1}`}
            className="pagination-nav-link"
          >
            1
          </Link>
          <span>...</span>
        </nav>
      ) : null}
      <nav className="pagination-nav">
        {numbersList.map((number, index) => {
          const isActive = location.search.endsWith(String(number));
          return (
            <Link
              key={index}
              to={`${location.pathname}?page=${number}`}
              className={`pagination-nav-link${isActive ? " pagination-nav-link-active" : ""}`}
            >
              {number}
            </Link>
          );
        })}
      </nav>
      {actualOptionStartPage + 5 < pagination.lastVisiblePage ? (
        <nav className="pagination-nav">
          <span>...</span>
          <Link
            to={`${location.pathname}?page=${pagination.lastVisiblePage}`}
            className="pagination-nav-link"
          >
            {pagination.lastVisiblePage}
          </Link>
        </nav>
      ) : null}
    </section>
  );
};

export default PaginationNav;
