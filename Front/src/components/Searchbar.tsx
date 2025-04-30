import SearchIcon from "./SearchIcon";
import "@/styles/Searchbar.css";

const Searchbar = () => {
  return (
    <section className="searchbar-container">
      <form action="#" method="get" className="searchbar-container-form">
        <label htmlFor="search-input" style={{ display: "none" }}>
          Buscar
        </label>
        <input
          id="search-input"
          type="text"
          name="search-input"
          placeholder="Buscar productos, marcas y más..."
          className="searchbar-container-form-input"
          autoComplete="off"
        />
        <button type="submit" className="searchbar-container-form-button">
          <SearchIcon />
        </button>
      </form>
    </section>
  );
};

export default Searchbar;
