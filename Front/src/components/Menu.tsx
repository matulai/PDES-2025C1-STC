import { ChevronLeftIcon } from "@/icons";
import { useState } from "react";
import { Link } from "react-router-dom";
import Navbar from "./Navbar";
import "@/styles/Menu.css";

const Menu = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  return (
    <>
      <button
        className="menu-button"
        onClick={() => setIsMenuOpen(!isMenuOpen)}
      >
        <span className="menu-button-line" />
        <span className="menu-button-line" />
        <span className="menu-button-line" />
      </button>
      <div className={`menu-container ${isMenuOpen ? "open" : "close"}`}>
        <div className="menu-container-header">
          <h1 className="menu-container-header-title">
            <Link to="/">STC</Link>
          </h1>
          <button
            onClick={() => setIsMenuOpen(false)}
            className="menu-container-header-button"
          >
            <ChevronLeftIcon
              style={{ color: "d3d3d3", width: "36", height: "36" }}
            />
          </button>
        </div>
        <Navbar />
      </div>
      {isMenuOpen && (
        <button
          className="menu-container-block-background"
          onClick={() => setIsMenuOpen(false)}
        />
      )}
    </>
  );
};

export default Menu;
