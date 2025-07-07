import { useEffect, useRef, useState } from "react";
import { ChevronDownIcon, CartIcon } from "@/icons";
import { Link, useLocation } from "react-router-dom";
import { useAuth, useCart } from "@/hooks";
import "@/styles/Navbar.css";

const navLinksNoRegistered = [
  { label: "Registrarse", pathname: "/register" },
  { label: "Ingresar", pathname: "/login" },
  {
    label: "Favoritos",
    pathname: "/user/favourites",
    pageQuery: "?page=1",
  },
  {
    label: "Compras",
    pathname: "/user/purchases",
    pageQuery: "?page=1",
  },
];

const navLinksRegistered = [
  {
    label: "Favoritos",
    pathname: "/user/favourites",
    pageQuery: "?page=1",
  },
  {
    label: "Compras",
    pathname: "/user/purchases",
    pageQuery: "?page=1",
  },
];

const adminOptions = [
  { label: "Usuarios", pathname: "/users", pageQuery: "?page=1" },
  {
    label: "Todos los favoritos",
    pathname: "/users/favourites",
    pageQuery: "?page=1",
  },
  {
    label: "Todas las Reseñas",
    pathname: "/users/qualifications",
    pageQuery: "?page=1",
  },
  {
    label: "Todas las Compras",
    pathname: "/users/purchases",
    pageQuery: "?page=1",
  },
  {
    label: "Top vendidos",
    pathname: "/products/topSellingProducts",
    pageQuery: "?page=1",
  },
  {
    label: "Top compradores",
    pathname: "/users/topBuyers",
    pageQuery: "?page=1",
  },
  {
    label: "Top favoritos",
    pathname: "/products/topFavouritesProducts",
    pageQuery: "?page=1",
  },
];

interface NavLink {
  label: string;
  pathname: string;
  pageQuery?: string;
}

const Navbar = () => {
  const { user, contextLogout } = useAuth();
  const { cart } = useCart();
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
    setShowDropdown(false);
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
                  to={`${option.pathname}${option.pageQuery ?? ""}`}
                  className={`navbar-link ${location.pathname.includes(option.pathname) ? "navbar-link-active" : "navbar-link-inactive"}`}
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
          to={`${link.pathname}${link.pageQuery ?? ""}`}
          className={`navbar-link ${location.pathname.includes(link.pathname) ? "navbar-link-active" : "navbar-link-inactive"}`}
        >
          {link.label}
        </Link>
      ))}
      {user?.role === "Client" ? (
        <Link
          to="/user/cart"
          className={`navbar-link ${location.pathname.includes("/cart") ? "navbar-link-active" : "navbar-link-inactive"}`}
        >
          <CartIcon />
          <span className="navbar-link-absolute-element">{cart.length}</span>
        </Link>
      ) : null}
    </nav>
  );
};

export default Navbar;
