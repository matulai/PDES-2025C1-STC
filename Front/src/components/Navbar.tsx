import { useEffect, useRef, useState } from "react";
import { ChevronDownIcon, CartIcon } from "@/icons";
import { Link, useLocation } from "react-router-dom";
import { useAuth } from "@/hooks";
import "@/styles/Navbar.css";

const navLinksNoRegistered = [
  { label: "Registrarse", pathname: "/register" },
  { label: "Ingresar", pathname: "/login" },
  { label: "Favoritos", pathname: "/favorites" },
  { label: "Compras", pathname: "/purchases" },
];

const navLinksRegistered = [
  { label: "Favoritos", pathname: "/favorites" },
  { label: "Compras", pathname: "/purchases" },
];

const adminOptions = [
  { label: "Usuarios", pathname: "/admin/users" },
  { label: "Todos los favoritos", pathname: "/admin/favorites" },
  { label: "Todas las Reseñas", pathname: "/admin/reviews" },
  { label: "Todas las Compras", pathname: "/admin/purchases" },
  { label: "Top vendidos", pathname: "/admin/top-sellers" },
  { label: "Top compradores", pathname: "/admin/top-buyers" },
  { label: "Top favoritos", pathname: "/admin/top-favorites" },
];

interface NavLink {
  label: string;
  pathname: string;
}

const Navbar = () => {
  const { user, contextLogout } = useAuth();
  const [showDropdown, setShowDropdown] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);
  const location = useLocation();

  let navLinks: NavLink[] = navLinksNoRegistered;
  let roleOptions: NavLink[] = [];

  if (user) {
    navLinks = navLinksRegistered;
    if (user.role === "Admin") {
      // El admin no debe poder hacer lo que un cliente puede
      navLinks = [];
      roleOptions = adminOptions;
    }
  }

  useEffect(() => {
    const handleClickOutside = (e: MouseEvent) => {
      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(e.target as Node)
      ) {
        setShowDropdown(false);
      }
    };

    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key === "Escape") {
        setShowDropdown(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, []);

  const handleLogout = () => {
    contextLogout();
  };

  return (
    <nav className="navbar-container">
      {user?.role !== undefined && (
        <div className="dropdown" ref={dropdownRef}>
          <button
            className="navbar-link navbar-link-inactive"
            onClick={() => setShowDropdown(!showDropdown)}
          >
            Options <ChevronDownIcon />
          </button>
          {showDropdown && (
            <div className="dropdown-menu">
              {roleOptions.map(option => (
                <Link
                  key={option.label}
                  to={option.pathname}
                  className={`navbar-link ${location.pathname === option.pathname ? "navbar-link-active" : "navbar-link-inactive"}`}
                >
                  {option.label}
                </Link>
              ))}
              <button
                className="navbar-link navbar-link-inactive"
                onClick={handleLogout}
              >
                Logout
              </button>
            </div>
          )}
        </div>
      )}

      {navLinks.map(link => (
        <Link
          key={link.label}
          to={link.pathname}
          className={`navbar-link ${location.pathname === link.pathname ? "navbar-link-active" : "navbar-link-inactive"}`}
        >
          {link.label}
        </Link>
      ))}
      {user?.role === "Client" ? (
        <Link
          to="/cart"
          className={`navbar-link ${location.pathname === "/cart" ? "navbar-link-active" : "navbar-link-inactive"}`}
        >
          <CartIcon />
        </Link>
      ) : null}
    </nav>
  );
};

export default Navbar;
