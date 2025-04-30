import { Link } from "react-router-dom";
import Searchbar from "./Searchbar";
import Navbar from "./Navbar";
import "@/styles/Header.css";

const Header = () => {
  return (
    <header className="header-container">
      <h1 className="header-container-title">
        <Link to="/" className="header-container-title-link">
          STC
        </Link>
      </h1>
      <Searchbar />
      <Navbar />
    </header>
  );
};

export default Header;
