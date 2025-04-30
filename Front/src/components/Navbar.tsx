import { Link, useLocation } from "react-router-dom";
import "@/styles/Navbar.css";

const navLinksNoRegistered = [
  { label: "Registrarse", pathname: "/register" },
  { label: "Ingresar", pathname: "/login" },
  { label: "Mis favoritos", pathname: "/favorites" },
  { label: "Mis compras", pathname: "/purchases" },
];

const navLinksRegistered = [
  { label: "Mi perfil", pathname: "/profile" },
  { label: "Mis favoritos", pathname: "/favorites" },
  { label: "Mis compras", pathname: "/purchases" },
];

const Navbar = () => {
  const location = useLocation();

  return (
    <nav className="navbar-container">
      {navLinksNoRegistered.map(link => {
        const isActive = location.pathname === link.pathname;
        return (
          <Link
            key={link.label}
            to={link.pathname}
            className={`navbar-container-link ${isActive ? "navbar-container-link-active" : "navbar-container-link-inactive"}`}
          >
            {link.label}
          </Link>
        );
      })}
    </nav>
  );
};

export default Navbar;
