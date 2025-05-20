import { useNavigate } from "react-router-dom";
import { useState } from "react";
import SearchIcon from "./SearchIcon";
import "@/styles/Searchbar.css";

const Searchbar = () => {
  const [query, setQuery] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (query.trim()) {
      navigate(`/search/${encodeURIComponent(query.trim())}`);
    }
  };

  return (
    <section className="searchbar-container">
      <form onSubmit={handleSubmit} className="searchbar-container-form">
        <label htmlFor="search-input" style={{ display: "none" }}>
          Buscar
        </label>
        <input
          id="search-input"
          type="search"
          name="search-input"
          placeholder="Buscar productos, marcas y más..."
          className="searchbar-container-form-input"
          autoComplete="off"
          value={query}
          onChange={e => setQuery(e.target.value)}
        />
        <button type="submit" className="searchbar-container-form-button">
          <SearchIcon />
        </button>
      </form>
    </section>
  );
};

export default Searchbar;
