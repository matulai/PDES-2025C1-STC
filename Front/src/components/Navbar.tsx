import NavbarItem from "./NavbarItem";
import "@/styles/Navbar.css";

const navLinksNoRegistered = [
  { label: "Registrarse", pathname: "/register" },
  { label: "Ingresar", pathname: "/login" },
  { label: "Favoritos", pathname: "/favorites" },
  { label: "Compras", pathname: "/purchases" },
];

const navLinksRegisteredClient = [
  { label: "Perfil", pathname: "/profile" },
  { label: "Favoritos", pathname: "/favorites" },
  { label: "Compras", pathname: "/purchases" },
];

const navLinksRegisteredAdmin = [
  { label: "Usuarios", pathname: "/admin/users" },
  { label: "Todos los favoritos", pathname: "/admin/favorites" },
  { label: "Todas las Reseñas", pathname: "/admin/reviews" },
  { label: "Todas las Compras", pathname: "/admin/purchases" },
  { label: "Top vendidos", pathname: "/admin/top-sellers" },
  { label: "Top compradores", pathname: "/admin/top-buyers" },
  { label: "Top favoritos", pathname: "/admin/top-favorites" },
];

const Navbar = () => {
  return (
    <nav className="navbar-container">
      {navLinksNoRegistered.map(link => (
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
