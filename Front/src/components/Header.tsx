import { Link } from "react-router-dom";
import Searchbar from "./Searchbar";
import Navbar from "./Navbar";
import Menu from "./Menu";
import "@/styles/Header.css";

const Header = () => {
  return (
    <header className="header-container">
      <Menu />
      <h1 className="header-container-title">
        <Link to="/">STC</Link>
      </h1>
      <Searchbar />
      <div className="header-container-navbar-render">
        <Navbar />
      </div>
    </header>
  );
};

export default Header;
