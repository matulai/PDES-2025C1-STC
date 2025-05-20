import { useEffect, useRef, useState } from "react";
import { ChevronDownIcon } from "@/icons";
import { useAuth } from "@/hooks";
import NavbarItem from "./NavbarItem";
import "@/styles/Navbar.css";

const navLinksNoRegistered = [
  { label: "Registrarse", pathname: "/register" },
  { label: "Ingresar", pathname: "/login" },
  { label: "Favoritos", pathname: "/favorites" },
  { label: "Compras", pathname: "/purchases" },
];

const navLinksRegisteredClient = [
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

const extraAdminLinks = [
  { label: "Favoritos", pathname: "/favorites" },
  { label: "Compras", pathname: "/purchases" },
];

type NavLink = { label: string; pathname: string };

const Navbar = () => {
  const { user } = useAuth();
  const [showDropdown, setShowDropdown] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);

  let navLinks: NavLink[] = navLinksNoRegistered;

  if (user) {
    if (user.role === "Client") {
      navLinks = navLinksRegisteredClient;
    } else {
      navLinks = extraAdminLinks;
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

  return (
    <nav className="navbar-container">
      {user?.role === "Admin" && (
        <div className="dropdown" ref={dropdownRef}>
          <button
            className="dropdown-toggle"
            onClick={() => setShowDropdown(!showDropdown)}
          >
            Admin <ChevronDownIcon />
          </button>
          {showDropdown && (
            <div className="dropdown-menu">
              {adminOptions.map(option => (
                <NavbarItem
                  key={option.label}
                  label={option.label}
                  pathname={option.pathname}
                />
              ))}
            </div>
          )}
        </div>
      )}

      {navLinks.map(link => (
        <NavbarItem
          key={link.label}
          label={link.label}
          pathname={link.pathname}
        />
      ))}
    </nav>
  );
};

export default Navbar;
