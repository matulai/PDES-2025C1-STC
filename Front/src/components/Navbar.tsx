import NavbarItem from "./NavbarItem";
import "@/styles/Navbar.css";

const navLinksNoRegistered = [
  { label: "Registrarse", pathname: "/register" },
  { label: "Ingresar", pathname: "/login" },
  { label: "Favoritos", pathname: "/favorites" },
  { label: "Compras", pathname: "/purchases" },
];

const navLinksRegistered = [
  { label: "perfil", pathname: "/profile" },
  { label: "Favoritos", pathname: "/favorites" },
  { label: "Compras", pathname: "/purchases" },
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
