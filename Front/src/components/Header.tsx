import Navbar from "./Navbar";
import "@/styles/Header.css";

const Header = () => {
  return (
    <header className="header-container">
      <h1 className="header-container-title">STC</h1>
      <Navbar />
    </header>
  );
};

export default Header;
